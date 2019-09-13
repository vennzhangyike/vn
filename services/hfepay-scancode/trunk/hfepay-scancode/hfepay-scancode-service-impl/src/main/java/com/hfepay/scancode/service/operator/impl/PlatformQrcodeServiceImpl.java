package com.hfepay.scancode.service.operator.impl;

import java.util.Date;

/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.PlatformQrcodeCondition;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.PlatformQrcodeDAO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantStore;
import com.hfepay.scancode.commons.entity.PlatformQrcode;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.PlatformQrcodeService;

import net.sf.json.JSONObject;

@Service("platformQrcodeService")
public class PlatformQrcodeServiceImpl implements PlatformQrcodeService {
	
	public static final Logger log = LoggerFactory.getLogger(MerchantQrcodeServiceImpl.class);
	
	@Autowired
    private PlatformQrcodeDAO platformQrcodeDAO;
	@Autowired
	private RedisSharedCache redisSharedCache;
    
    /**
	 * 列表(分页)
	 * @param platformQrcodeCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
    @Override
	public PagingResult<PlatformQrcode> findPagingResult(PlatformQrcodeCondition platformQrcodeCondition){
		CriteriaBuilder cb = Cnd.builder(PlatformQrcode.class);
		if(!Strings.isEmpty(platformQrcodeCondition.getId())){
			cb.andEQ("id", platformQrcodeCondition.getId());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getQrCode())){
			cb.andEQ("qrCode", platformQrcodeCondition.getQrCode());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getChannelNo())){
			cb.andEQ("channelNo", platformQrcodeCondition.getChannelNo());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getChannelName())){
			cb.andLike("channelName", platformQrcodeCondition.getChannelName());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getAgentNo())){
			cb.andEQ("agentNo", platformQrcodeCondition.getAgentNo());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getAgentName())){
			cb.andLike("agentName", platformQrcodeCondition.getAgentName());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getMerchantNo())){
			cb.andEQ("merchantNo", platformQrcodeCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getMerchantName())){
			cb.andLike("merchantName", platformQrcodeCondition.getMerchantName());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getQrName())){
			cb.andEQ("qrName", platformQrcodeCondition.getQrName());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getImage())){
			cb.andEQ("image", platformQrcodeCondition.getImage());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getQrDesc())){
			cb.andEQ("qrDesc", platformQrcodeCondition.getQrDesc());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getStoreId())){
			cb.andEQ("storeId", platformQrcodeCondition.getStoreId());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getStoreName())){
			cb.andEQ("storeName", platformQrcodeCondition.getStoreName());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getUseStatus())){
			cb.andEQ("useStatus", platformQrcodeCondition.getUseStatus());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getQrStatus())){
			cb.andEQ("qrStatus", platformQrcodeCondition.getQrStatus());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getRecordStatus())){
			cb.andEQ("recordStatus", platformQrcodeCondition.getRecordStatus());
		}
		if(null != platformQrcodeCondition.getCreateTime()){
			cb.andEQ("createTime", platformQrcodeCondition.getCreateTime());
		}

		if(!Strings.isEmpty(platformQrcodeCondition.getOperator())){
			cb.andEQ("operator", platformQrcodeCondition.getOperator());
		}

		if(!Strings.isEmpty(platformQrcodeCondition.getRemark())){
			cb.andLike("remark", platformQrcodeCondition.getRemark());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getTemp1())){
			cb.andEQ("temp1", platformQrcodeCondition.getTemp1());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getTemp2())){
			cb.andEQ("temp2", platformQrcodeCondition.getTemp2());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(platformQrcodeCondition.getOrderBy())){
			if(platformQrcodeCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = platformQrcodeCondition.getOrderBy().split(",");
				String[] orders = platformQrcodeCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(platformQrcodeCondition.getOrderBy(), Order.valueOf(platformQrcodeCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(platformQrcodeCondition.getFirst()), Long.valueOf(platformQrcodeCondition.getLast()));
		
		long begin=System.currentTimeMillis();
		PagingResult<PlatformQrcode> page = platformQrcodeDAO.findPagingResult(buildCriteria);
		log.info("二维码查询执行耗时:" + (System.currentTimeMillis()-begin)/1000f+" 秒 ");
		for (PlatformQrcode platformQrcode : page.getResult()) {			
			try {
				ChannelBase channelBase = (ChannelBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+platformQrcode.getChannelNo()));
				if(channelBase != null){
					platformQrcode.setChannelName(channelBase.getChannelName());
				}			
				AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+platformQrcode.getAgentNo()));
				if(agentBase != null){
					platformQrcode.setAgentName(agentBase.getAgentName());
				}
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+platformQrcode.getMerchantNo()));
				log.info("#######merchantInfoCondition["+JSONObject.fromObject(merchantInfo)+"]######");
				if(merchantInfo != null){
					platformQrcode.setMerchantName(merchantInfo.getMerchantName());
				}
				MerchantStore merchantStore = (MerchantStore) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.STORE_BASE.getGroup(), RedisKeyEnum.STORE_BASE.getKey()+platformQrcode.getStoreId()));
				if(merchantStore != null){
					platformQrcode.setStoreName(merchantStore.getStoreName());
				}
			} catch (Exception e) {
				log.error("获取redis数据异常："+e.getMessage());
			}
		}
		return page;
	}
	
	/**
	 * 列表
	 * @param platformQrcode 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	@Override
	public List<PlatformQrcode> findAll(PlatformQrcodeCondition platformQrcodeCondition){
		CriteriaBuilder cb = Cnd.builder(PlatformQrcode.class);
		if(!Strings.isEmpty(platformQrcodeCondition.getId())){
			cb.andEQ("id", platformQrcodeCondition.getId());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getQrCode())){
			cb.andEQ("qrCode", platformQrcodeCondition.getQrCode());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getChannelNo())){
			cb.andEQ("channelNo", platformQrcodeCondition.getChannelNo());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getChannelName())){
			cb.andEQ("channelName", platformQrcodeCondition.getChannelName());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getAgentNo())){
			cb.andEQ("agentNo", platformQrcodeCondition.getAgentNo());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getAgentName())){
			cb.andEQ("agentName", platformQrcodeCondition.getAgentName());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getMerchantNo())){
			cb.andEQ("merchantNo", platformQrcodeCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getMerchantName())){
			cb.andEQ("merchantName", platformQrcodeCondition.getMerchantName());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getQrName())){
			cb.andEQ("qrName", platformQrcodeCondition.getQrName());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getImage())){
			cb.andEQ("image", platformQrcodeCondition.getImage());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getQrDesc())){
			cb.andEQ("qrDesc", platformQrcodeCondition.getQrDesc());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getStoreId())){
			cb.andEQ("storeId", platformQrcodeCondition.getStoreId());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getStoreName())){
			cb.andEQ("storeName", platformQrcodeCondition.getStoreName());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getUseStatus())){
			cb.andEQ("useStatus", platformQrcodeCondition.getUseStatus());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getQrStatus())){
			cb.andEQ("qrStatus", platformQrcodeCondition.getQrStatus());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getRecordStatus())){
			cb.andEQ("recordStatus", platformQrcodeCondition.getRecordStatus());
		}
		if(null != platformQrcodeCondition.getCreateTime()){
			cb.andEQ("createTime", platformQrcodeCondition.getCreateTime());
		}

		if(!Strings.isEmpty(platformQrcodeCondition.getOperator())){
			cb.andEQ("operator", platformQrcodeCondition.getOperator());
		}

		if(!Strings.isEmpty(platformQrcodeCondition.getRemark())){
			cb.andLike("remark", platformQrcodeCondition.getRemark());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getTemp1())){
			cb.andEQ("temp1", platformQrcodeCondition.getTemp1());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getTemp2())){
			cb.andEQ("temp2", platformQrcodeCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return platformQrcodeDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	@Override
	public PlatformQrcode findById(String id){
		return platformQrcodeDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param platformQrcodeCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	@Override
	public long insert(PlatformQrcodeCondition platformQrcodeCondition){
		PlatformQrcode platformQrcode = new PlatformQrcode();
		BeanUtils.copyProperties(platformQrcodeCondition, platformQrcode);
		return platformQrcodeDAO.insert(platformQrcode);
	}
	
	/**
	 * 批量新增
	 * @param qrList
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	@Override
	public void batchInsert(List<PlatformQrcodeCondition> qrList){
		platformQrcodeDAO.batchInsert(qrList);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	@Override
	public long deleteById(String id){
		return platformQrcodeDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return platformQrcodeDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return platformQrcodeDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	@Override
	public long update(PlatformQrcodeCondition platformQrcodeCondition){
		PlatformQrcode platformQrcode = new PlatformQrcode();
		BeanUtils.copyProperties(platformQrcodeCondition, platformQrcode);
		return platformQrcodeDAO.update(platformQrcode);
	}
	
	/**删除商户后更新二维码*/
	@Override
	public long updateByMerchantNo(String merchantNo){
		return platformQrcodeDAO.updateByMerchantNo(merchantNo);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	@Override
	public long updateByCriteria(PlatformQrcodeCondition platformQrcodeCondition,Criteria criteria){
		PlatformQrcode platformQrcode = new PlatformQrcode();
		BeanUtils.copyProperties(platformQrcodeCondition, platformQrcode);
		return platformQrcodeDAO.updateByCriteria(platformQrcode,criteria);
	}
	
	/**
	 * 条件取当前代理商没有用过的二维码
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	@Override
	public PlatformQrcode findPlatformQrcode(String id) {
		return platformQrcodeDAO.findPlatformQrcode(id) ;
	}
	
	/**
	 * 生成编码
	 * @param map
	 */
	@Override
	public String getQrcodeCode(){
		StringBuffer sb = new StringBuffer();
		sb.append(ScanCodeConstants.QRCODE_PRE_NAME);
		sb.append(Dates.format("yyyyMMddHHmmssSSS", new Date()));
		for (int i = 0; i < 3; i++) {
			sb.append((int)(Math.random()*10));
		}
		return sb.toString();
	}
	
	/**
	 * 生成编码(保证每次生成的不会重复)
	 * @param map
	 */
	@Override
	public String getQrcodeCode(Set<String> set,String qr) {
		String qrcodeCode = qr;
		if(!set.contains(qr)){
			set.add(qr) ;
		}else{
			qrcodeCode = getQrcodeCode(set,getQrcodeCode());
		}
		return qrcodeCode;
	}
	
	@Override
	public PlatformQrcode findByQrcode(String qrCode) {
		CriteriaBuilder cb = Cnd.builder(PlatformQrcode.class);
		if(Strings.isEmpty(qrCode)){
			throw new RuntimeException("二维码编号不能为空");
		}
		cb.andEQ("qrCode", qrCode);
		Criteria buildCriteria = cb.buildCriteria();
		return platformQrcodeDAO.findOneByCriteria(buildCriteria);
	}
	
	@Override
	public long update(PlatformQrcode platformQrcode) {
		return platformQrcodeDAO.update(platformQrcode);
	}
	
	
	/**
	 * 列表
	 * @param platformQrcode 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-14 16:27:56
	 */
	@Override
	public PlatformQrcode findByCondition(PlatformQrcodeCondition platformQrcodeCondition){
		CriteriaBuilder cb = Cnd.builder(PlatformQrcode.class);
		if(!Strings.isEmpty(platformQrcodeCondition.getId())){
			cb.andEQ("id", platformQrcodeCondition.getId());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getQrCode())){
			cb.andEQ("qrCode", platformQrcodeCondition.getQrCode());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getChannelNo())){
			cb.andEQ("channelNo", platformQrcodeCondition.getChannelNo());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getChannelName())){
			cb.andEQ("channelName", platformQrcodeCondition.getChannelName());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getAgentNo())){
			cb.andEQ("agentNo", platformQrcodeCondition.getAgentNo());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getAgentName())){
			cb.andEQ("agentName", platformQrcodeCondition.getAgentName());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getMerchantNo())){
			cb.andEQ("merchantNo", platformQrcodeCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getMerchantName())){
			cb.andEQ("merchantName", platformQrcodeCondition.getMerchantName());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getQrName())){
			cb.andEQ("qrName", platformQrcodeCondition.getQrName());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getImage())){
			cb.andEQ("image", platformQrcodeCondition.getImage());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getQrDesc())){
			cb.andEQ("qrDesc", platformQrcodeCondition.getQrDesc());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getStoreId())){
			cb.andEQ("storeId", platformQrcodeCondition.getStoreId());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getStoreName())){
			cb.andEQ("storeName", platformQrcodeCondition.getStoreName());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getQrStatus())){
			cb.andEQ("qrStatus", platformQrcodeCondition.getQrStatus());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getUseStatus())){
			cb.andEQ("useStatus", platformQrcodeCondition.getUseStatus());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getRecordStatus())){
			cb.andEQ("recordStatus", platformQrcodeCondition.getRecordStatus());
		}
		if(null != platformQrcodeCondition.getCreateTime()){
			cb.andEQ("createTime", platformQrcodeCondition.getCreateTime());
		}

		if(!Strings.isEmpty(platformQrcodeCondition.getOperator())){
			cb.andEQ("operator", platformQrcodeCondition.getOperator());
		}

		if(!Strings.isEmpty(platformQrcodeCondition.getRemark())){
			cb.andLike("remark", platformQrcodeCondition.getRemark());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getTemp1())){
			cb.andEQ("temp1", platformQrcodeCondition.getTemp1());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getTemp2())){
			cb.andEQ("temp2", platformQrcodeCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return platformQrcodeDAO.findOneByCriteria(buildCriteria);
	}

	@Override
	public PagingResult<PlatformQrcode> findPagingResultByChannelNoAndAgentNo(PlatformQrcodeCondition platformQrcodeCondition) {
		CriteriaBuilder cb = Cnd.builder(PlatformQrcode.class);
		if(!Strings.isEmpty(platformQrcodeCondition.getChannelNo())){
			cb.andEQ("channelNo", platformQrcodeCondition.getChannelNo());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getAgentNo())){
			cb.andEQ("agentNo", platformQrcodeCondition.getAgentNo());
		}else{
			cb.andIsNull("agentNo");
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getUseStatus())){
			cb.andEQ("useStatus", platformQrcodeCondition.getUseStatus());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getQrStatus())){
			cb.andEQ("qrStatus", platformQrcodeCondition.getQrStatus());
		}
		if(!Strings.isEmpty(platformQrcodeCondition.getRecordStatus())){
			cb.andEQ("recordStatus", platformQrcodeCondition.getRecordStatus());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(platformQrcodeCondition.getOrderBy())){
			if(platformQrcodeCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = platformQrcodeCondition.getOrderBy().split(",");
				String[] orders = platformQrcodeCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(platformQrcodeCondition.getOrderBy(), Order.valueOf(platformQrcodeCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(platformQrcodeCondition.getFirst()), Long.valueOf(platformQrcodeCondition.getLast()));
		return platformQrcodeDAO.findPagingResult(buildCriteria);
	}
	
	/** 重置二维码 */
	@Override
	public void resetQrcode(){
		List<PlatformQrcode> platformQrcodes =  platformQrcodeDAO.findRepeatQrcode();
		for (PlatformQrcode platformQrcode : platformQrcodes) {
			platformQrcode.setQrCode(this.getQrcodeCode());
			try {
				platformQrcodeDAO.updateRepeatQrcode(platformQrcode);
			} catch (Exception e) {
				log.info("重置二维码失败：" + e.getMessage());
				e.printStackTrace();
			}
		}
	}

}

