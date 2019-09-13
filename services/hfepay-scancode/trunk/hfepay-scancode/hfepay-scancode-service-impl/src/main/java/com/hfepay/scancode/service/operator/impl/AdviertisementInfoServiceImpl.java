/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.operator.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.bo.AdviertismentBo;
import com.hfepay.scancode.commons.condition.AdviertisementInfoCondition;
import com.hfepay.scancode.commons.condition.AuditLogCondition;
import com.hfepay.scancode.commons.condition.CityCondition;
import com.hfepay.scancode.commons.contants.AuditType;
import com.hfepay.scancode.commons.contants.MerchantStatus;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.AdviertisementInfoDAO;
import com.hfepay.scancode.commons.entity.AdviertisementInfo;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.City;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.Province;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.AdviertisementInfoService;
import com.hfepay.scancode.service.operator.AuditLogService;
import com.hfepay.scancode.service.operator.CityService;
import com.hfepay.scancode.service.operator.ProvinceService;
import com.hfepay.scancode.service.utils.DateUtils;

import net.sf.json.JSONObject;

@Service("adviertisementInfoService")
public class AdviertisementInfoServiceImpl implements AdviertisementInfoService {
	
	public static final Logger log = LoggerFactory.getLogger(AdviertisementInfoServiceImpl.class);
	
	@Autowired
    private AdviertisementInfoDAO adviertisementInfoDAO;
	@Autowired
	private RedisSharedCache redisSharedCache;
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	@Autowired
	private AuditLogService auditLogService;
	
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param adviertisementInfoCondition
	 * @return: PagingResult<AdviertisementInfo>
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
    @Override
	public PagingResult<AdviertisementInfo> findPagingResult(AdviertisementInfoCondition adviertisementInfoCondition){
		CriteriaBuilder cb = Cnd.builder(AdviertisementInfo.class);
		if(!Strings.isEmpty(adviertisementInfoCondition.getId())){
			cb.andEQ("id", adviertisementInfoCondition.getId());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getAdviertNo())){
			cb.andEQ("adviertNo", adviertisementInfoCondition.getAdviertNo());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getOrganNo())){
			cb.andEQ("organNo", adviertisementInfoCondition.getOrganNo());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getAdviertScope())){
			cb.andEQ("adviertScope", adviertisementInfoCondition.getAdviertScope());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getAdviertPosition())){
			cb.andEQ("adviertPosition", adviertisementInfoCondition.getAdviertPosition());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getImgUrl())){
			cb.andEQ("imgUrl", adviertisementInfoCondition.getImgUrl());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getAdviertLink())){
			cb.andEQ("adviertLink", adviertisementInfoCondition.getAdviertLink());
		}
		if(null != adviertisementInfoCondition.getStartDate()){
			cb.andEQ("startDate", adviertisementInfoCondition.getStartDate());
		}
		if(null != adviertisementInfoCondition.getEndDate()){
			cb.andEQ("endDate", adviertisementInfoCondition.getEndDate());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getPriority())){
			cb.andEQ("priority", adviertisementInfoCondition.getPriority());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getStatus())){
			cb.andEQ("status", adviertisementInfoCondition.getStatus());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getRecordStatus())){
			cb.andEQ("recordStatus", adviertisementInfoCondition.getRecordStatus());
		}
		if(null != adviertisementInfoCondition.getCreateTime()){
			cb.andEQ("createTime", adviertisementInfoCondition.getCreateTime());
		}

		if(!Strings.isEmpty(adviertisementInfoCondition.getOperator())){
			cb.andEQ("operator", adviertisementInfoCondition.getOperator());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getNodeSeq())){
			cb.addParam("nodeSeq", adviertisementInfoCondition.getNodeSeq());
		}

		if(!Strings.isEmpty(adviertisementInfoCondition.getRemark())){
			cb.andLike("remark", adviertisementInfoCondition.getRemark());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getTemp1())){
			cb.andEQ("temp1", adviertisementInfoCondition.getTemp1());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getTemp2())){
			cb.andEQ("temp2", adviertisementInfoCondition.getTemp2());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getTemp3())){
			cb.andEQ("temp3", adviertisementInfoCondition.getTemp3());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getTemp4())){
			cb.andEQ("temp4", adviertisementInfoCondition.getTemp4());
		}
		
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(adviertisementInfoCondition.getFirst()), Long.valueOf(adviertisementInfoCondition.getLast()));
		
		PagingResult<AdviertisementInfo> page = adviertisementInfoDAO.findPagingResult(buildCriteria);
		for (AdviertisementInfo adviertisementInfo : page.getResult()) {			
			try {
				if (Strings.isNotEmpty(adviertisementInfo.getOrganNo()) && !Strings.equals(adviertisementInfo.getOrganNo(), ScanCodeConstants.UNLIMITED)) {
					ChannelBase channelBase = (ChannelBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+adviertisementInfo.getOrganNo()));
					if(channelBase != null){
						adviertisementInfo.setOrganName(channelBase.getChannelName());
					}			
					AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+adviertisementInfo.getOrganNo()));
					if(agentBase != null){
						adviertisementInfo.setOrganName(agentBase.getAgentName());
					}
				}else{
					adviertisementInfo.setOrganName(ScanCodeConstants.SYSTEM_UNLIMITED);
				}
				
			} catch (Exception e) {
				log.error("获取redis数据异常："+e.getMessage());
			}
			
			if (Strings.isNotEmpty(adviertisementInfo.getAdviertScope()) && !Strings.equals(adviertisementInfo.getAdviertScope(), ScanCodeConstants.UNLIMITED)) {
				City city = cityService.findById(Integer.parseInt(adviertisementInfo.getAdviertScope()));
				if(city != null){
					adviertisementInfo.setAdviertScope(city.getCname());
				}else{
					Province province = provinceService.findById(Integer.parseInt(adviertisementInfo.getAdviertScope()));
					adviertisementInfo.setAdviertScope(province.getPname());
				}
			}else{
				adviertisementInfo.setAdviertScope(ScanCodeConstants.SYSTEM_UNLIMITED);
			}
		}
		return page;
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param adviertisementInfoCondition
	 * @return: List<AdviertisementInfo>
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	@Override
	public List<AdviertisementInfo> findAll(AdviertisementInfoCondition adviertisementInfoCondition){
		CriteriaBuilder cb = Cnd.builder(AdviertisementInfo.class);
		if(!Strings.isEmpty(adviertisementInfoCondition.getId())){
			cb.andEQ("id", adviertisementInfoCondition.getId());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getAdviertNo())){
			cb.andEQ("adviertNo", adviertisementInfoCondition.getAdviertNo());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getOrganNo())){
			cb.andEQ("organNo", adviertisementInfoCondition.getOrganNo());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getAdviertScope())){
			cb.andEQ("adviertScope", adviertisementInfoCondition.getAdviertScope());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getAdviertPosition())){
			cb.andEQ("adviertPosition", adviertisementInfoCondition.getAdviertPosition());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getImgUrl())){
			cb.andEQ("imgUrl", adviertisementInfoCondition.getImgUrl());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getAdviertLink())){
			cb.andEQ("adviertLink", adviertisementInfoCondition.getAdviertLink());
		}
		if(null != adviertisementInfoCondition.getStartDate()){
			cb.andEQ("startDate", adviertisementInfoCondition.getStartDate());
		}
		if(null != adviertisementInfoCondition.getEndDate()){
			cb.andEQ("endDate", adviertisementInfoCondition.getEndDate());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getPriority())){
			cb.andEQ("priority", adviertisementInfoCondition.getPriority());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getStatus())){
			cb.andEQ("status", adviertisementInfoCondition.getStatus());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getRecordStatus())){
			cb.andEQ("recordStatus", adviertisementInfoCondition.getRecordStatus());
		}
		if(null != adviertisementInfoCondition.getCreateTime()){
			cb.andEQ("createTime", adviertisementInfoCondition.getCreateTime());
		}

		if(!Strings.isEmpty(adviertisementInfoCondition.getOperator())){
			cb.andEQ("operator", adviertisementInfoCondition.getOperator());
		}

		if(!Strings.isEmpty(adviertisementInfoCondition.getRemark())){
			cb.andLike("remark", adviertisementInfoCondition.getRemark());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getTemp1())){
			cb.andEQ("temp1", adviertisementInfoCondition.getTemp1());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getTemp2())){
			cb.andEQ("temp2", adviertisementInfoCondition.getTemp2());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getTemp3())){
			cb.andEQ("temp3", adviertisementInfoCondition.getTemp3());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getTemp4())){
			cb.andEQ("temp4", adviertisementInfoCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return adviertisementInfoDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findByOrgan
	 * @Description: 列表
	 * @author: Ricky
	 * @param adviertisementInfoCondition
	 * @return: List<AdviertisementInfo>
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	@Override
	public List<AdviertisementInfo> findByOrgan(AdviertisementInfoCondition adviertisementInfoCondition){
		CriteriaBuilder cb = Cnd.builder(AdviertisementInfo.class);
		if(!Strings.isEmpty(adviertisementInfoCondition.getAdviertNo())){
			cb.andEQ("adviertNo", adviertisementInfoCondition.getAdviertNo());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getOrganNo())){
			cb.andEQ("organNo", adviertisementInfoCondition.getOrganNo());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getAdviertScope())){
			cb.andEQ("adviertScope", adviertisementInfoCondition.getAdviertScope());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getAdviertPosition())){
			cb.andEQ("adviertPosition", adviertisementInfoCondition.getAdviertPosition());
		}
		if(null != adviertisementInfoCondition.getStartDate()){
			cb.andLE("startDate", adviertisementInfoCondition.getStartDate());
			cb.andGE("endDate", adviertisementInfoCondition.getStartDate());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getPriority())){
			cb.andEQ("priority", adviertisementInfoCondition.getPriority());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getStatus())){
			cb.andEQ("status", adviertisementInfoCondition.getStatus());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getRecordStatus())){
			cb.andEQ("recordStatus", adviertisementInfoCondition.getRecordStatus());
		}
		if(null != adviertisementInfoCondition.getCreateTime()){
			cb.andEQ("createTime", adviertisementInfoCondition.getCreateTime());
		}

		if(!Strings.isEmpty(adviertisementInfoCondition.getOperator())){
			cb.andEQ("operator", adviertisementInfoCondition.getOperator());
		}

		if(!Strings.isEmpty(adviertisementInfoCondition.getRemark())){
			cb.andLike("remark", adviertisementInfoCondition.getRemark());
		}
		
		Criteria buildCriteria = cb.buildCriteria();
		return adviertisementInfoDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: AdviertisementInfo
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	@Override
	public AdviertisementInfo findById(String id){
		return adviertisementInfoDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param adviertisementInfoCondition
	 * @return: long
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	@Override
	public long insert(AdviertisementInfoCondition adviertisementInfoCondition){
		AdviertisementInfo adviertisementInfo = new AdviertisementInfo();
		BeanUtils.copyProperties(adviertisementInfoCondition, adviertisementInfo);
		return adviertisementInfoDAO.insert(adviertisementInfo);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	@Override
	public long deleteById(String id){
		return adviertisementInfoDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return adviertisementInfoDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return adviertisementInfoDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param adviertisementInfoCondition
	 * @return: long
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	@Override
	public long update(AdviertisementInfoCondition adviertisementInfoCondition){
		AdviertisementInfo adviertisementInfo = new AdviertisementInfo();
		BeanUtils.copyProperties(adviertisementInfoCondition, adviertisementInfo);
		adviertisementInfo.setUpdateTime(new Date());
		return adviertisementInfoDAO.update(adviertisementInfo);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param adviertisementInfoCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	@Override
	public long updateByCriteria(AdviertisementInfoCondition adviertisementInfoCondition,Criteria criteria){
		AdviertisementInfo adviertisementInfo = new AdviertisementInfo();
		BeanUtils.copyProperties(adviertisementInfoCondition, adviertisementInfo);
		return adviertisementInfoDAO.updateByCriteria(adviertisementInfo,criteria);
	}
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	@Override
	public long updateStatus(String id,String status){
		return adviertisementInfoDAO.updateStatus(id,status);
	}	
	
	/** 获取机构列表 */
	@Override
	public List<AdviertisementInfo> getOrganList(Map<String, String> map){
		return adviertisementInfoDAO.getOrganList(map);
	}	
	
	/**
	 * 生成编码
	 */
	@Override
	public String getAdviertNo(){
		StringBuffer sb = new StringBuffer();
		sb.append(ScanCodeConstants.ADVIERT_PRE_NAME);
		sb.append(Dates.format("yyyyMMddHHmmssSSS", new Date()));
		for (int i = 0; i < 3; i++) {
			sb.append((int)(Math.random()*10));
		}
		return sb.toString();
	}
	
	/** 广告信息审核 */
	@Transactional
	@Override
	public void auditPlatform(AdviertisementInfoCondition adviertisementInfoCondition,AuditLogCondition auditLogCondition) throws Exception{
		String auditStatus= "";
		if(Strings.equals(adviertisementInfoCondition.getStatus(), ScanCodeConstants.AD_STATUS_SUCCESS)){
			auditStatus = MerchantStatus.MERCHANT_STATUS_3.getCode();
		}else if(Strings.equals(adviertisementInfoCondition.getStatus(), ScanCodeConstants.AD_STATUS_FAIL)){
			auditStatus = MerchantStatus.MERCHANT_STATUS_4.getCode();
		}		
		auditLogCondition.setCreateTime(new Date());
		auditLogCondition.setAuditId(adviertisementInfoCondition.getId());
		auditLogCondition.setAuditType(AuditType.ADVIERTISEMENT_INFO.getCode());
		auditLogCondition.setAuditStatus(auditStatus);
		auditLogCondition.setId(Strings.getUUID());
		auditLogService.insert(auditLogCondition);
		
		this.update(adviertisementInfoCondition);
	}
	
	/**
	 * 根据登录人信息获取对应广告
	 * @param 用户ID user
	 * @param 广告类型 adviertPosition
	 * @return List<AdviertisementInfo>
	 */
	@Override
	public List<AdviertisementInfo> getAdviertisInfoByUser(AdviertismentBo adviertismentBo){
		MerchantInfo merchantInfo;
		try {
			merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+adviertismentBo.getMerchantNo()));
			log.info("#######merchantInfoCondition["+JSONObject.fromObject(merchantInfo)+"]######");
			
			//
			CityCondition cityCondition = new CityCondition();
			cityCondition.setCname(adviertismentBo.getCity());
			List<City> cityList = cityService.findByCname(cityCondition);
			City city = cityList.get(0);
			
			//平台优先级最高
			List<AdviertisementInfo> adviertList = getAdviertInfo(city.getCid().toString(),ScanCodeConstants.UNLIMITED,adviertismentBo.getAdviertPosition());
			if (adviertList != null && adviertList.size() > 0 ) {
				return adviertList;
			}
			
			adviertList = getAdviertInfo(city.getPid().toString(),ScanCodeConstants.UNLIMITED,adviertismentBo.getAdviertPosition());
			if (adviertList != null && adviertList.size() > 0 ) {
				return adviertList;
			}
			
			adviertList = getAdviertInfo(ScanCodeConstants.UNLIMITED,ScanCodeConstants.UNLIMITED,adviertismentBo.getAdviertPosition());
			if (adviertList != null && adviertList.size() > 0 ) {
				return adviertList;
			}
			
			//渠道优先级为次级
			adviertList = getAdviertInfo(city.getCid().toString(),merchantInfo.getChannelNo(),adviertismentBo.getAdviertPosition());
			if (adviertList != null && adviertList.size() > 0 ) {
				return adviertList;
			}
			
			adviertList = getAdviertInfo(city.getPid().toString(),merchantInfo.getChannelNo(),adviertismentBo.getAdviertPosition());
			if (adviertList != null && adviertList.size() > 0 ) {
				return adviertList;
			}
			
			adviertList = getAdviertInfo(ScanCodeConstants.UNLIMITED,merchantInfo.getChannelNo(),adviertismentBo.getAdviertPosition());
			if (adviertList != null && adviertList.size() > 0 ) {
				return adviertList;
			}
			
			//代理商优先级最低
			adviertList = getAdviertInfo(city.getCid().toString(),merchantInfo.getAgentNo(),adviertismentBo.getAdviertPosition());
			if (adviertList != null && adviertList.size() > 0 ) {
				return adviertList;
			}
			
			adviertList = getAdviertInfo(city.getPid().toString(),merchantInfo.getAgentNo(),adviertismentBo.getAdviertPosition());
			if (adviertList != null && adviertList.size() > 0 ) {
				return adviertList;
			}
			
			adviertList = getAdviertInfo(ScanCodeConstants.UNLIMITED,merchantInfo.getAgentNo(),adviertismentBo.getAdviertPosition());
			if (adviertList != null && adviertList.size() > 0 ) {
				return adviertList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private List<AdviertisementInfo> getAdviertInfo(String cityInfo,String organ,String adviertPosition) {
		AdviertisementInfoCondition adviertisementInfoCondition = new AdviertisementInfoCondition();
		adviertisementInfoCondition.setStartDate(new Date());
		adviertisementInfoCondition.setStartDate(new Date());
		adviertisementInfoCondition.setStatus(ScanCodeConstants.AD_STATUS_SUCCESS);
		adviertisementInfoCondition.setAdviertPosition(adviertPosition);
		adviertisementInfoCondition.setOrganNo(organ);
		adviertisementInfoCondition.setAdviertScope(cityInfo);
		List<AdviertisementInfo> adviertList = this.findByOrgan(adviertisementInfoCondition);
		return adviertList;
	}
	
	public static void main(String[] args) {
		String now = DateUtils.formatDateFmt(new Date(),"yyyy-MM-dd HH:mm:ss");
		try {
			Date nowDate = Dates.parse("yyyy-MM-dd HH:mm", now);
			System.out.println(nowDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

