/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.hfepay.scancode.commons.condition.MerchantQrcodeCondition;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.MerchantQrcodeDAO;
import com.hfepay.scancode.commons.entity.MerchantCashierQr;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantQrcode;
import com.hfepay.scancode.commons.entity.MerchantStore;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.MerchantCashierQrService;
import com.hfepay.scancode.service.operator.MerchantQrcodeService;

import net.sf.json.JSONObject;

@Service("merchantQrcodeService")
public class MerchantQrcodeServiceImpl implements MerchantQrcodeService {
	public static final Logger log = LoggerFactory.getLogger(MerchantQrcodeServiceImpl.class);

	@Autowired
    private MerchantQrcodeDAO merchantQrcodeDAO;
	@Autowired
	private RedisSharedCache redisSharedCache;
    @Autowired
    private MerchantCashierQrService merchantCashierQrService;
    /**
	 * 列表(分页)
	 * @param merchantQrcodeCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 10:47:29
	 */
    @Override
	public PagingResult<MerchantQrcode> findPagingResult(MerchantQrcodeCondition merchantQrcodeCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantQrcode.class);
		if(!Strings.isEmpty(merchantQrcodeCondition.getId())){
			cb.andEQ("id", merchantQrcodeCondition.getId());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrCode())){
			cb.andLike("qrCode", merchantQrcodeCondition.getQrCode());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantQrcodeCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getMerchantName())){
			cb.andLike("merchantName", merchantQrcodeCondition.getMerchantName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantQrcodeCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getAgentNo())){
			cb.andEQ("agentNo", merchantQrcodeCondition.getAgentNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrName())){
			cb.andLike("qrName", merchantQrcodeCondition.getQrName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrDesc())){
			cb.andEQ("qrDesc", merchantQrcodeCondition.getQrDesc());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getImage())){
			cb.andEQ("image", merchantQrcodeCondition.getImage());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getStoreId())){
			cb.andEQ("storeId", merchantQrcodeCondition.getStoreId());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getStoreName())){
			cb.andLike("storeName", merchantQrcodeCondition.getStoreName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrStatus())){
			cb.andEQ("qrStatus", merchantQrcodeCondition.getQrStatus());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantQrcodeCondition.getRecordStatus());
		}
		if(null != merchantQrcodeCondition.getCreateTime()){
			cb.andEQ("createTime", merchantQrcodeCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantQrcodeCondition.getOperator())){
			cb.andEQ("operator", merchantQrcodeCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantQrcodeCondition.getRemark())){
			cb.andLike("remark", merchantQrcodeCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp1())){
			cb.andEQ("temp1", merchantQrcodeCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp2())){
			cb.andEQ("temp2", merchantQrcodeCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp3())){
			cb.andEQ("temp3", merchantQrcodeCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp4())){
			cb.andEQ("temp4", merchantQrcodeCondition.getTemp4());
		}
		if(Strings.isNotEmpty(merchantQrcodeCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantQrcodeCondition.getNodeSeq());
		}
		cb.andEQ("recordStatus", ScanCodeConstants.RECORD_STATUS_YES);
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(merchantQrcodeCondition.getOrderBy())){
			if(merchantQrcodeCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = merchantQrcodeCondition.getOrderBy().split(",");
				String[] orders = merchantQrcodeCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(merchantQrcodeCondition.getOrderBy(), Order.valueOf(merchantQrcodeCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantQrcodeCondition.getFirst()), Long.valueOf(merchantQrcodeCondition.getLast()));
		long begin=System.currentTimeMillis();
		PagingResult<MerchantQrcode> page = merchantQrcodeDAO.findPagingResult(buildCriteria);
		log.info("商户二维码查询执行耗时:" + (System.currentTimeMillis()-begin)/1000f+" 秒 ");
		for (MerchantQrcode merchantQrcode : page.getResult()) {			
			try {
//				ChannelBase channelBase = (ChannelBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+merchantQrcode.getChannelNo()));
//				if(channelBase != null){
//					merchantQrcode.setChannelName(channelBase.getChannelName());
//				}			
//				AgentBaseCondition agentBaseCondition = (AgentBaseCondition) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+merchantQrcode.getAgentNo()));
//				if(agentBaseCondition != null){
//					merchantQrcode.setAgentName(agentBaseCondition.getAgentName());
//				}
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantQrcode.getMerchantNo()));
				log.info("#######merchantInfoCondition["+JSONObject.fromObject(merchantInfo)+"]######");
				if(merchantInfo != null){
					merchantQrcode.setMerchantName(merchantInfo.getMerchantName());
				}
				MerchantStore merchantStore = (MerchantStore) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.STORE_BASE.getGroup(), RedisKeyEnum.STORE_BASE.getKey()+merchantQrcode.getStoreId()));
				if(merchantStore != null){
					merchantQrcode.setStoreName(merchantStore.getStoreName());
				}
			} catch (Exception e) {
				log.error("获取redis数据异常："+e.getMessage());
			}
		}
		return page;
	}
	
    
	/**
	 * 列表
	 * @param merchantQrcode 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 10:47:29
	 */
	@Override
	public List<MerchantQrcode> findAll(MerchantQrcodeCondition merchantQrcodeCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantQrcode.class);
		if(!Strings.isEmpty(merchantQrcodeCondition.getId())){
			cb.andEQ("id", merchantQrcodeCondition.getId());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrCode())){
			cb.andEQ("qrCode", merchantQrcodeCondition.getQrCode());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantQrcodeCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getMerchantName())){
			cb.andLike("merchantName", merchantQrcodeCondition.getMerchantName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantQrcodeCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getAgentNo())){
			cb.andEQ("agentNo", merchantQrcodeCondition.getAgentNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrName())){
			cb.andLike("qrName", merchantQrcodeCondition.getQrName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrDesc())){
			cb.andEQ("qrDesc", merchantQrcodeCondition.getQrDesc());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getImage())){
			cb.andEQ("image", merchantQrcodeCondition.getImage());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getStoreId())){
			cb.andEQ("storeId", merchantQrcodeCondition.getStoreId());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getStoreName())){
			cb.andLike("storeName", merchantQrcodeCondition.getStoreName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrStatus())){
			cb.andEQ("qrStatus", merchantQrcodeCondition.getQrStatus());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantQrcodeCondition.getRecordStatus());
		}
		if(null != merchantQrcodeCondition.getCreateTime()){
			cb.andEQ("createTime", merchantQrcodeCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantQrcodeCondition.getOperator())){
			cb.andEQ("operator", merchantQrcodeCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantQrcodeCondition.getRemark())){
			cb.andLike("remark", merchantQrcodeCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp1())){
			cb.andEQ("temp1", merchantQrcodeCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp2())){
			cb.andEQ("temp2", merchantQrcodeCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp3())){
			cb.andEQ("temp3", merchantQrcodeCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp4())){
			cb.andEQ("temp4", merchantQrcodeCondition.getTemp4());
		}
		if(Strings.isNotEmpty(merchantQrcodeCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantQrcodeCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantQrcodeDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 10:47:29
	 */
	@Override
	public MerchantQrcode findById(String id){
		return merchantQrcodeDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param merchantQrcodeCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 10:47:29
	 */
	@Override
	public long insert(MerchantQrcodeCondition merchantQrcodeCondition,Map<String,String> map){
		MerchantQrcode merchantQrcode = new MerchantQrcode();
		try {
			MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantQrcodeCondition.getMerchantNo()));
			merchantQrcodeCondition.setChannelNo(merchantInfo.getChannelNo());
			merchantQrcodeCondition.setAgentNo(merchantInfo.getAgentNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		BeanUtils.copyProperties(merchantQrcodeCondition, merchantQrcode);
//		try {
////			QRCodeUtil.encode(map.get("temp1"), icon, map.get("temp3"));
//			
//			QRCodeUtil.encode(map.get("content"), map.get("imgPath"), map.get("destPath"),true,map.get("qrName"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info("insert error..."+e);
//		}
		return merchantQrcodeDAO.insert(merchantQrcode);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 10:47:29
	 */
	@Override
	public long deleteById(String id){
		return merchantQrcodeDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 10:47:29
	 */
	@Override
	public long deleteByCriteria(MerchantQrcodeCondition merchantQrcodeCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantQrcode.class);
		if(!Strings.isEmpty(merchantQrcodeCondition.getId())){
			cb.andEQ("id", merchantQrcodeCondition.getId());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrCode())){
			cb.andEQ("qrCode", merchantQrcodeCondition.getQrCode());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantQrcodeCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getMerchantName())){
			cb.andEQ("merchantName", merchantQrcodeCondition.getMerchantName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantQrcodeCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getAgentNo())){
			cb.andEQ("agentNo", merchantQrcodeCondition.getAgentNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrName())){
			cb.andEQ("qrName", merchantQrcodeCondition.getQrName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrDesc())){
			cb.andEQ("qrDesc", merchantQrcodeCondition.getQrDesc());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getImage())){
			cb.andEQ("image", merchantQrcodeCondition.getImage());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getStoreId())){
			cb.andEQ("storeId", merchantQrcodeCondition.getStoreId());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getStoreName())){
			cb.andEQ("storeName", merchantQrcodeCondition.getStoreName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrStatus())){
			cb.andEQ("qrStatus", merchantQrcodeCondition.getQrStatus());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantQrcodeCondition.getRecordStatus());
		}
		if(null != merchantQrcodeCondition.getCreateTime()){
			cb.andEQ("createTime", merchantQrcodeCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantQrcodeCondition.getOperator())){
			cb.andEQ("operator", merchantQrcodeCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantQrcodeCondition.getRemark())){
			cb.andLike("remark", merchantQrcodeCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp1())){
			cb.andEQ("temp1", merchantQrcodeCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp2())){
			cb.andEQ("temp2", merchantQrcodeCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp3())){
			cb.andEQ("temp3", merchantQrcodeCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp4())){
			cb.andEQ("temp4", merchantQrcodeCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		MerchantQrcode merchantQrcode = new MerchantQrcode();
		BeanUtils.copyProperties(merchantQrcodeCondition, merchantQrcode);
		return merchantQrcodeDAO.deleteByCriteria(buildCriteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 10:47:29
	 */
	@Override
	public long countByCriteria(MerchantQrcodeCondition merchantQrcodeCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantQrcode.class);
		if(!Strings.isEmpty(merchantQrcodeCondition.getId())){
			cb.andEQ("id", merchantQrcodeCondition.getId());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrCode())){
			cb.andEQ("qrCode", merchantQrcodeCondition.getQrCode());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantQrcodeCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getMerchantName())){
			cb.andEQ("merchantName", merchantQrcodeCondition.getMerchantName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantQrcodeCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getAgentNo())){
			cb.andEQ("agentNo", merchantQrcodeCondition.getAgentNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrName())){
			cb.andEQ("qrName", merchantQrcodeCondition.getQrName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrDesc())){
			cb.andEQ("qrDesc", merchantQrcodeCondition.getQrDesc());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getImage())){
			cb.andEQ("image", merchantQrcodeCondition.getImage());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getStoreId())){
			cb.andEQ("storeId", merchantQrcodeCondition.getStoreId());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getStoreName())){
			cb.andEQ("storeName", merchantQrcodeCondition.getStoreName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrStatus())){
			cb.andEQ("qrStatus", merchantQrcodeCondition.getQrStatus());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantQrcodeCondition.getRecordStatus());
		}
		if(null != merchantQrcodeCondition.getCreateTime()){
			cb.andEQ("createTime", merchantQrcodeCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantQrcodeCondition.getOperator())){
			cb.andEQ("operator", merchantQrcodeCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantQrcodeCondition.getRemark())){
			cb.andLike("remark", merchantQrcodeCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp1())){
			cb.andEQ("temp1", merchantQrcodeCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp2())){
			cb.andEQ("temp2", merchantQrcodeCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp3())){
			cb.andEQ("temp3", merchantQrcodeCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp4())){
			cb.andEQ("temp4", merchantQrcodeCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		MerchantQrcode merchantQrcode = new MerchantQrcode();
		BeanUtils.copyProperties(merchantQrcodeCondition, merchantQrcode);
		return merchantQrcodeDAO.countByCriteria(buildCriteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 10:47:29
	 */
	@Override
	public long update(MerchantQrcodeCondition merchantQrcodeCondition){
		MerchantQrcode merchantQrcode = new MerchantQrcode();
		BeanUtils.copyProperties(merchantQrcodeCondition, merchantQrcode);
		return merchantQrcodeDAO.update(merchantQrcode);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 10:47:29
	 */
	@Override
	public long updateByCriteria(MerchantQrcodeCondition merchantQrcodeCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantQrcode.class);
		if(!Strings.isEmpty(merchantQrcodeCondition.getId())){
			cb.andEQ("id", merchantQrcodeCondition.getId());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrCode())){
			cb.andEQ("qrCode", merchantQrcodeCondition.getQrCode());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantQrcodeCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getMerchantName())){
			cb.andEQ("merchantName", merchantQrcodeCondition.getMerchantName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantQrcodeCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getAgentNo())){
			cb.andEQ("agentNo", merchantQrcodeCondition.getAgentNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrName())){
			cb.andEQ("qrName", merchantQrcodeCondition.getQrName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrDesc())){
			cb.andEQ("qrDesc", merchantQrcodeCondition.getQrDesc());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getImage())){
			cb.andEQ("image", merchantQrcodeCondition.getImage());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getStoreId())){
			cb.andEQ("storeId", merchantQrcodeCondition.getStoreId());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getStoreName())){
			cb.andEQ("storeName", merchantQrcodeCondition.getStoreName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrStatus())){
			cb.andEQ("qrStatus", merchantQrcodeCondition.getQrStatus());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantQrcodeCondition.getRecordStatus());
		}
		if(null != merchantQrcodeCondition.getCreateTime()){
			cb.andEQ("createTime", merchantQrcodeCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantQrcodeCondition.getOperator())){
			cb.andEQ("operator", merchantQrcodeCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantQrcodeCondition.getRemark())){
			cb.andLike("remark", merchantQrcodeCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp1())){
			cb.andEQ("temp1", merchantQrcodeCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp2())){
			cb.andEQ("temp2", merchantQrcodeCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp3())){
			cb.andEQ("temp3", merchantQrcodeCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp4())){
			cb.andEQ("temp4", merchantQrcodeCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		MerchantQrcode merchantQrcode = new MerchantQrcode();
		BeanUtils.copyProperties(merchantQrcodeCondition, merchantQrcode);
		return merchantQrcodeDAO.updateByCriteria(merchantQrcode,buildCriteria);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 10:47:29
	 */
	@Override
	public long updateByCriteria(MerchantQrcodeCondition merchantQrcodeCondition,Criteria buildCriteria){
		MerchantQrcode merchantQrcode = new MerchantQrcode();
		BeanUtils.copyProperties(merchantQrcodeCondition, merchantQrcode);
		return merchantQrcodeDAO.updateByCriteria(merchantQrcode,buildCriteria);
	}
	
	@Override
	public MerchantQrcode findQrcodeByIdentityNo(String identityNo) {
		// TODO Auto-generated method stub
		return merchantQrcodeDAO.findQrcodeByIdentityNo(identityNo);
	}
	
	@Override
	public List<MerchantQrcode> findAllMerchantQrcode(MerchantQrcodeCondition merchantQrcodeCondition) {
		CriteriaBuilder cb = Cnd.builder(MerchantQrcode.class);
		if(!Strings.isEmpty(merchantQrcodeCondition.getId())){
			cb.andEQ("id", merchantQrcodeCondition.getId());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrCode())){
			cb.andEQ("qrCode", merchantQrcodeCondition.getQrCode());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantQrcodeCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getMerchantName())){
			cb.andLike("merchantName", merchantQrcodeCondition.getMerchantName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantQrcodeCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getAgentNo())){
			cb.andEQ("agentNo", merchantQrcodeCondition.getAgentNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrName())){
			cb.andLike("qrName", merchantQrcodeCondition.getQrName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrDesc())){
			cb.andEQ("qrDesc", merchantQrcodeCondition.getQrDesc());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getImage())){
			cb.andEQ("image", merchantQrcodeCondition.getImage());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getStoreId())){
			cb.andEQ("storeId", merchantQrcodeCondition.getStoreId());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getStoreName())){
			cb.andLike("storeName", merchantQrcodeCondition.getStoreName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrStatus())){
			cb.andEQ("qrStatus", merchantQrcodeCondition.getQrStatus());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantQrcodeCondition.getRecordStatus());
		}
		if(null != merchantQrcodeCondition.getCreateTime()){
			cb.andEQ("createTime", merchantQrcodeCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantQrcodeCondition.getOperator())){
			cb.andEQ("operator", merchantQrcodeCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantQrcodeCondition.getRemark())){
			cb.andLike("remark", merchantQrcodeCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp1())){
			cb.andEQ("temp1", merchantQrcodeCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp2())){
			cb.andEQ("temp2", merchantQrcodeCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp3())){
			cb.andEQ("temp3", merchantQrcodeCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp4())){
			cb.andEQ("temp4", merchantQrcodeCondition.getTemp4());
		}
		if(Strings.isNotEmpty(merchantQrcodeCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantQrcodeCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantQrcodeDAO.findAllMerchantQrcode(buildCriteria);
	}
	
	@Override
	public void delMerchantQrcode(MerchantQrcodeCondition merchantQrcodeCondition) {
		//删除商户自二维码
		merchantQrcodeCondition.setRecordStatus("N");
		merchantQrcodeCondition.setUpdateTime(new Date());
		update(merchantQrcodeCondition);
		//收银员二维码停止收银同时修改状态为无效，删除
		MerchantCashierQrCondition merchantCashierQrCondition = new MerchantCashierQrCondition();
		merchantCashierQrCondition.setQrCode(merchantQrcodeCondition.getQrCode());//where 
		merchantCashierQrCondition.setRecordStatus("N");
		merchantCashierQrCondition.setIsCashier("1");
		merchantCashierQrCondition.setStatus("2");
		merchantCashierQrCondition.setUpdateTime(new Date());
		merchantCashierQrCondition.setOperator(merchantCashierQrCondition.getOperator());
		merchantCashierQrService.updateByQrCode(merchantCashierQrCondition);
	}
	
	/**
	 * 
	 */
	@Override
	public PagingResult<MerchantQrcode> findPagingResultSelf(MerchantQrcodeCondition merchantQrcodeCondition) {
		CriteriaBuilder cb = Cnd.builder(MerchantQrcode.class);
		if(!Strings.isEmpty(merchantQrcodeCondition.getId())){
			cb.andEQ("id", merchantQrcodeCondition.getId());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrCode())){
			cb.andEQ("qrCode", merchantQrcodeCondition.getQrCode());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantQrcodeCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getMerchantName())){
			cb.andLike("merchantName", merchantQrcodeCondition.getMerchantName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantQrcodeCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getAgentNo())){
			cb.andEQ("agentNo", merchantQrcodeCondition.getAgentNo());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrName())){
			cb.andLike("qrName", merchantQrcodeCondition.getQrName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrDesc())){
			cb.andEQ("qrDesc", merchantQrcodeCondition.getQrDesc());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getImage())){
			cb.andEQ("image", merchantQrcodeCondition.getImage());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getStoreId())){
			cb.andEQ("storeId", merchantQrcodeCondition.getStoreId());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getStoreName())){
			cb.andLike("storeName", merchantQrcodeCondition.getStoreName());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getQrStatus())){
			cb.andEQ("qrStatus", merchantQrcodeCondition.getQrStatus());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantQrcodeCondition.getRecordStatus());
		}
		if(null != merchantQrcodeCondition.getCreateTime()){
			cb.andEQ("createTime", merchantQrcodeCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantQrcodeCondition.getOperator())){
			cb.andEQ("operator", merchantQrcodeCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantQrcodeCondition.getRemark())){
			cb.andLike("remark", merchantQrcodeCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp1())){
			cb.andEQ("temp1", merchantQrcodeCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp2())){
			cb.andEQ("temp2", merchantQrcodeCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp3())){
			cb.andEQ("temp3", merchantQrcodeCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantQrcodeCondition.getTemp4())){
			cb.andEQ("temp4", merchantQrcodeCondition.getTemp4());
		}
		if(Strings.isNotEmpty(merchantQrcodeCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantQrcodeCondition.getNodeSeq());
		}
		cb.andEQ("recordStatus", ScanCodeConstants.RECORD_STATUS_YES);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantQrcodeCondition.getFirst()), Long.valueOf(merchantQrcodeCondition.getLast()));
		PagingResult<MerchantQrcode> page = merchantQrcodeDAO.findPagingResultSelf(buildCriteria);
		return page;
	}
}

