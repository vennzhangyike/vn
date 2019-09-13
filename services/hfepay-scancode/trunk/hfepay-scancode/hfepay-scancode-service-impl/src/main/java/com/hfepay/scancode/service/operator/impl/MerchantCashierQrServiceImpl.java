/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantCashierQrCondition;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.MerchantCashierQrDAO;
import com.hfepay.scancode.commons.entity.MerchantCashierQr;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.MerchantCashierQrService;
import com.hfepay.scancode.service.operator.MerchantCashierService;

import net.sf.json.JSONObject;

@Service("merchantCashierQrService")
public class MerchantCashierQrServiceImpl implements MerchantCashierQrService {
	
	public static final Logger log = LoggerFactory.getLogger(MerchantCashierQrServiceImpl.class);
	
	@Autowired
    private MerchantCashierQrDAO merchantCashierQrDAO;
	@Autowired
    private MerchantCashierService merchantCashierService;
	@Autowired
	private RedisSharedCache redisSharedCache;
    
    /**
	 * 列表(分页)
	 * @param merchantCashierQrCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
    @Override
	public PagingResult<MerchantCashierQr> findPagingResult(MerchantCashierQrCondition merchantCashierQrCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantCashierQr.class);
		if(!Strings.isEmpty(merchantCashierQrCondition.getId())){
			cb.andEQ("id", merchantCashierQrCondition.getId());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getCashierNo())){
			cb.andLike("cashierNo", merchantCashierQrCondition.getCashierNo());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantCashierQrCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getQrCode())){
			cb.andEQ("qrCode", merchantCashierQrCondition.getQrCode());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getIsCashier())){
			cb.andEQ("isCashier", merchantCashierQrCondition.getIsCashier());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getStatus())){
			cb.andEQ("status", merchantCashierQrCondition.getStatus());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantCashierQrCondition.getRecordStatus());
		}
		if(null != merchantCashierQrCondition.getCreateTime()){
			cb.andEQ("createTime", merchantCashierQrCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantCashierQrCondition.getOperator())){
			cb.andEQ("operator", merchantCashierQrCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantCashierQrCondition.getRemark())){
			cb.andLike("remark", merchantCashierQrCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getTemp1())){
			cb.andEQ("temp1", merchantCashierQrCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getTemp2())){
			cb.andEQ("temp2", merchantCashierQrCondition.getTemp2());
		}
		if(Strings.isNotEmpty(merchantCashierQrCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantCashierQrCondition.getNodeSeq());
		}
		cb.andEQ("recordStatus", ScanCodeConstants.RECORD_STATUS_YES);
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(merchantCashierQrCondition.getOrderBy())){
			if(merchantCashierQrCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = merchantCashierQrCondition.getOrderBy().split(",");
				String[] orders = merchantCashierQrCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(merchantCashierQrCondition.getOrderBy(), Order.valueOf(merchantCashierQrCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantCashierQrCondition.getFirst()), Long.valueOf(merchantCashierQrCondition.getLast()));
		PagingResult<MerchantCashierQr> page = merchantCashierQrDAO.findPagingResult(buildCriteria);
		for (MerchantCashierQr merchantCashierQr : page.getResult()) {			
			try {
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantCashierQr.getMerchantNo()));
				log.info("#######merchantInfoCondition["+JSONObject.fromObject(merchantInfo)+"]######");
				if(merchantInfo != null){
					merchantCashierQr.setMerchantName(merchantInfo.getMerchantName());
				}
				
			} catch (Exception e) {
				log.error("获取redis数据异常："+e.getMessage());
			}
		}
		return page;
	}
	
	/**
	 * 列表
	 * @param merchantCashierQr 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	@Override
	public List<MerchantCashierQr> findAll(MerchantCashierQrCondition merchantCashierQrCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantCashierQr.class);
		if(!Strings.isEmpty(merchantCashierQrCondition.getId())){
			cb.andEQ("id", merchantCashierQrCondition.getId());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getCashierNo())){
			cb.andEQ("cashierNo", merchantCashierQrCondition.getCashierNo());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantCashierQrCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getQrCode())){
			cb.andEQ("qrCode", merchantCashierQrCondition.getQrCode());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getIsCashier())){
			cb.andEQ("isCashier", merchantCashierQrCondition.getIsCashier());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getStatus())){
			cb.andEQ("status", merchantCashierQrCondition.getStatus());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantCashierQrCondition.getRecordStatus());
		}
		if(null != merchantCashierQrCondition.getCreateTime()){
			cb.andEQ("createTime", merchantCashierQrCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantCashierQrCondition.getOperator())){
			cb.andEQ("operator", merchantCashierQrCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantCashierQrCondition.getRemark())){
			cb.andLike("remark", merchantCashierQrCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getTemp1())){
			cb.andEQ("temp1", merchantCashierQrCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getTemp2())){
			cb.andEQ("temp2", merchantCashierQrCondition.getTemp2());
		}
		if(Strings.isNotEmpty(merchantCashierQrCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantCashierQrCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantCashierQrDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	@Override
	public MerchantCashierQr findById(String id){
		return merchantCashierQrDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param merchantCashierQrCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	@Override
	public long insert(MerchantCashierQrCondition merchantCashierQrCondition){
		MerchantCashierQr merchantCashierQr = new MerchantCashierQr();
		try {
			MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantCashierQrCondition.getMerchantNo()));
			merchantCashierQrCondition.setChannelNo(merchantInfo.getChannelNo());
			merchantCashierQrCondition.setAgentNo(merchantInfo.getAgentNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		BeanUtils.copyProperties(merchantCashierQrCondition, merchantCashierQr);
		return merchantCashierQrDAO.insert(merchantCashierQr);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	@Override
	public long deleteById(String id){
		return merchantCashierQrDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	@Override
	public long deleteByCriteria(MerchantCashierQrCondition merchantCashierQrCondition){
		MerchantCashierQr merchantCashierQr = new MerchantCashierQr();
		BeanUtils.copyProperties(merchantCashierQrCondition, merchantCashierQr);
		CriteriaBuilder cb = Cnd.builder(MerchantCashierQr.class);
		if(!Strings.isEmpty(merchantCashierQrCondition.getCashierNo())){
			cb.andEQ("cashierNo", merchantCashierQrCondition.getCashierNo());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantCashierQrCondition.getMerchantNo());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantCashierQrDAO.deleteByCriteria(buildCriteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return merchantCashierQrDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	@Override
	public long update(MerchantCashierQrCondition merchantCashierQrCondition){
		MerchantCashierQr merchantCashierQr = new MerchantCashierQr();
		BeanUtils.copyProperties(merchantCashierQrCondition, merchantCashierQr);
		return merchantCashierQrDAO.update(merchantCashierQr);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	@Override
	public long updateByCriteria(MerchantCashierQrCondition merchantCashierQrCondition){
		MerchantCashierQr merchantCashierQr = new MerchantCashierQr();
		BeanUtils.copyProperties(merchantCashierQrCondition, merchantCashierQr);
		CriteriaBuilder cb = Cnd.builder(MerchantCashierQr.class);
		if(!Strings.isEmpty(merchantCashierQrCondition.getId())){
			cb.andEQ("id", merchantCashierQrCondition.getId());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getCashierNo())){
			cb.andEQ("cashierNo", merchantCashierQrCondition.getCashierNo());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantCashierQrCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getQrCode())){
			cb.andEQ("qrCode", merchantCashierQrCondition.getQrCode());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantCashierQrDAO.updateByCriteria(merchantCashierQr,buildCriteria);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	@Override
	public long updateByCriteria(MerchantCashierQrCondition merchantCashierQrCondition,Criteria buildCriteria){
		MerchantCashierQr merchantCashierQr = new MerchantCashierQr();
		BeanUtils.copyProperties(merchantCashierQrCondition, merchantCashierQr);
		return merchantCashierQrDAO.updateByCriteria(merchantCashierQr,buildCriteria);
	}
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	@Override
	public long updateStatus(String id,String status){
		return merchantCashierQrDAO.updateStatus(id,status);
	}	
	
	@Override
	public long bindCasiher(MerchantCashierQrCondition condition) {
		//先删除之前的绑定
		merchantCashierQrDAO.deleteByCashier(condition.getCashierNo());//删除之前的绑定
		merchantCashierService.bindStore(condition.getStoreNo(),condition.getCashierNo());//绑定新的门店
		List<MerchantCashierQr> list = new ArrayList<>();
		String qrCode = condition.getQrCode();
		if(null==qrCode||Strings.isEmpty(qrCode)){
			throw new RuntimeException("数据有误，没有二维码编号传入");
		}
		String[] qrcodes = qrCode.split(",");
		Date date = new Date();
		for (String code : qrcodes) {
			MerchantCashierQr codeqr = new MerchantCashierQr();
			codeqr.setMerchantNo(condition.getMerchantNo());
			codeqr.setCashierNo(condition.getCashierNo());
			codeqr.setCreateTime(date);
			codeqr.setQrCode(code);
			codeqr.setIsCashier("1");//默认停止收银
			codeqr.setStatus("1");//有效
			codeqr.setRecordStatus("Y");
			codeqr.setOperator(condition.getMerchantNo());
			codeqr.setId(Strings.getUUID());
			list.add(codeqr);
		}
		// TODO Auto-generated method stub
		merchantCashierQrDAO.bindCasiher(list);
		return 1;
	}
	
	@Override
	public PagingResult<MerchantCashierQr> findPagingResultSelf(MerchantCashierQrCondition merchantCashierQrCondition) {
		CriteriaBuilder cb = Cnd.builder(MerchantCashierQr.class);
		if(!Strings.isEmpty(merchantCashierQrCondition.getId())){
			cb.andEQ("id", merchantCashierQrCondition.getId());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getCashierNo())){
			cb.andEQ("cashierNo", merchantCashierQrCondition.getCashierNo());
			cb.addParam("cashierNo", merchantCashierQrCondition.getCashierNo());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantCashierQrCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getQrCode())){
			cb.andEQ("qrCode", merchantCashierQrCondition.getQrCode());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getIsCashier())){
			cb.andEQ("isCashier", merchantCashierQrCondition.getIsCashier());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getStatus())){
			cb.andEQ("status", merchantCashierQrCondition.getStatus());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantCashierQrCondition.getRecordStatus());
		}
		if(null != merchantCashierQrCondition.getCreateTime()){
			cb.andEQ("createTime", merchantCashierQrCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantCashierQrCondition.getOperator())){
			cb.andEQ("operator", merchantCashierQrCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantCashierQrCondition.getRemark())){
			cb.andLike("remark", merchantCashierQrCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getTemp1())){
			cb.andEQ("temp1", merchantCashierQrCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getTemp2())){
			cb.andEQ("temp2", merchantCashierQrCondition.getTemp2());
		}
		/*if(merchantCashierQrCondition.getChannelAdmin() != null){
			ChannelAdmin admin = merchantCashierQrCondition.getChannelAdmin();
			channelFilter(cb, admin);
		}*/
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(merchantCashierQrCondition.getOrderBy())){
			if(merchantCashierQrCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = merchantCashierQrCondition.getOrderBy().split(",");
				String[] orders = merchantCashierQrCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(merchantCashierQrCondition.getOrderBy(), Order.valueOf(merchantCashierQrCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantCashierQrCondition.getFirst()), Long.valueOf(merchantCashierQrCondition.getLast()));
		return merchantCashierQrDAO.findPagingResultSelf(buildCriteria);
	}
	
	
	@Override
	public long bindSelectCode(MerchantCashierQrCondition qrCondition) {
		// TODO Auto-generated method stub
		if(qrCondition.getIsCashier().equals("2")){
			merchantCashierQrDAO.unbind(qrCondition.getCashierNo());//解除之前的绑定
		}
		return this.update(qrCondition);//修改当前点击的二位状态
	}
	
	@Override
	public long updateByQrCode(MerchantCashierQrCondition merchantCashierQrCondition) {
		MerchantCashierQr merchantCashierQr = new MerchantCashierQr();
		BeanUtils.copyProperties(merchantCashierQrCondition, merchantCashierQr);
		CriteriaBuilder cb = Cnd.builder(MerchantCashierQr.class);
		cb.andEQ("qrCode", merchantCashierQrCondition.getQrCode());
		return merchantCashierQrDAO.updateByCriteria(merchantCashierQr,cb.buildCriteria());
	}
}

