/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

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
import com.hfepay.scancode.commons.condition.ChannelExpandCondition;
import com.hfepay.scancode.commons.condition.MerchantInfoCondition;
import com.hfepay.scancode.commons.condition.MerchantStoreCondition;
import com.hfepay.scancode.commons.condition.PlatformQrcodeCondition;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.MerchantStoreDAO;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantStore;
import com.hfepay.scancode.commons.entity.PlatformQrcode;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.contants.ConfigPreCode;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.IdCreateService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MerchantStoreService;
import com.hfepay.scancode.service.operator.PlatformQrcodeService;

@Service("merchantStoreService")
public class MerchantStoreServiceImpl implements MerchantStoreService {
	public static final Logger logger = LoggerFactory.getLogger(MerchantStoreServiceImpl.class);

	@Autowired
    private MerchantStoreDAO merchantStoreDAO;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private IdCreateService idCreateService;
	@Autowired
	private ChannelExpandService channelExpandService;
	@Autowired
	private PlatformQrcodeService platformQrcodeService;
	@Autowired
	private RedisSharedCache redisSharedCache;
    /**
	 * 列表(分页)
	 * @param merchantStoreCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:58
	 */
    @Override
	public PagingResult<MerchantStore> findPagingResult(MerchantStoreCondition merchantStoreCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantStore.class);
		if(!Strings.isEmpty(merchantStoreCondition.getId())){
			cb.andEQ("id", merchantStoreCondition.getId());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantStoreCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreNo())){
			cb.andEQ("storeNo", merchantStoreCondition.getStoreNo());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreName())){
			cb.andLike("storeName", merchantStoreCondition.getStoreName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreType())){
			cb.andEQ("storeType", merchantStoreCondition.getStoreType());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServicePhone())){
			cb.andEQ("servicePhone", merchantStoreCondition.getServicePhone());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServiceBegin())){
			cb.andEQ("serviceBegin", merchantStoreCondition.getServiceBegin());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServiceEnd())){
			cb.andEQ("serviceEnd", merchantStoreCondition.getServiceEnd());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreAddress())){
			cb.andEQ("storeAddress", merchantStoreCondition.getStoreAddress());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreAddressImg())){
			cb.andEQ("storeAddressImg", merchantStoreCondition.getStoreAddressImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreDesc())){
			cb.andEQ("storeDesc", merchantStoreCondition.getStoreDesc());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getLicenseName())){
			cb.andEQ("licenseName", merchantStoreCondition.getLicenseName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getName())){
			cb.andEQ("name", merchantStoreCondition.getName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdCard())){
			cb.andEQ("idCard", merchantStoreCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg1())){
			cb.andEQ("idcardImg1", merchantStoreCondition.getIdcardImg1());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg2())){
			cb.andEQ("idcardImg2", merchantStoreCondition.getIdcardImg2());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg3())){
			cb.andEQ("idcardImg3", merchantStoreCondition.getIdcardImg3());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantLicense())){
			cb.andEQ("merchantLicense", merchantStoreCondition.getMerchantLicense());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantLicenseImg())){
			cb.andEQ("merchantLicenseImg", merchantStoreCondition.getMerchantLicenseImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStorePhotosImg())){
			cb.andEQ("storePhotosImg", merchantStoreCondition.getStorePhotosImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getGroupPhotoImg())){
			cb.andEQ("groupPhotoImg", merchantStoreCondition.getGroupPhotoImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreImg())){
			cb.andEQ("storeImg", merchantStoreCondition.getStoreImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIsPhoto())){
			cb.andEQ("isPhoto", merchantStoreCondition.getIsPhoto());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getOperator())){
			cb.andEQ("operator", merchantStoreCondition.getOperator());
		}
		if(null != merchantStoreCondition.getCreateTime()){
			cb.andEQ("createTime", merchantStoreCondition.getCreateTime());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getAuditOperator())){
			cb.andEQ("auditOperator", merchantStoreCondition.getAuditOperator());
		}
		if(null != merchantStoreCondition.getAuditDate()){
			cb.andEQ("auditDate", merchantStoreCondition.getAuditDate());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getAuditReson())){
			cb.andEQ("auditReson", merchantStoreCondition.getAuditReson());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreStatus())){
			cb.andEQ("storeStatus", merchantStoreCondition.getStoreStatus());
		}

		if(!Strings.isEmpty(merchantStoreCondition.getRemark())){
			cb.andLike("remark", merchantStoreCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp1())){
			cb.andEQ("temp1", merchantStoreCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp2())){
			cb.andEQ("temp2", merchantStoreCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp3())){
			cb.andEQ("temp3", merchantStoreCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp4())){
			cb.andEQ("temp4", merchantStoreCondition.getTemp4());
		}
		if(Strings.isNotEmpty(merchantStoreCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantStoreCondition.getNodeSeq());
		}
		cb.andEQ("recordStatus", ScanCodeConstants.RECORD_STATUS_YES);
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(merchantStoreCondition.getOrderBy())){
			if(merchantStoreCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = merchantStoreCondition.getOrderBy().split(",");
				String[] orders = merchantStoreCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(merchantStoreCondition.getOrderBy(), Order.valueOf(merchantStoreCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantStoreCondition.getFirst()), Long.valueOf(merchantStoreCondition.getLast()));
		return merchantStoreDAO.findPagingResult(buildCriteria);
	}
    
	/**
	 * 列表
	 * @param merchantStore 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:58
	 */
	@Override
	public List<MerchantStore> findAll(MerchantStoreCondition merchantStoreCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantStore.class);
		if(!Strings.isEmpty(merchantStoreCondition.getId())){
			cb.andEQ("id", merchantStoreCondition.getId());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantStoreCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreNo())){
			cb.andEQ("storeNo", merchantStoreCondition.getStoreNo());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreName())){
			cb.andLike("storeName", merchantStoreCondition.getStoreName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreType())){
			cb.andEQ("storeType", merchantStoreCondition.getStoreType());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServicePhone())){
			cb.andEQ("servicePhone", merchantStoreCondition.getServicePhone());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServiceBegin())){
			cb.andEQ("serviceBegin", merchantStoreCondition.getServiceBegin());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServiceEnd())){
			cb.andEQ("serviceEnd", merchantStoreCondition.getServiceEnd());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreAddress())){
			cb.andEQ("storeAddress", merchantStoreCondition.getStoreAddress());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreAddressImg())){
			cb.andEQ("storeAddressImg", merchantStoreCondition.getStoreAddressImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreDesc())){
			cb.andEQ("storeDesc", merchantStoreCondition.getStoreDesc());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getLicenseName())){
			cb.andEQ("licenseName", merchantStoreCondition.getLicenseName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getName())){
			cb.andEQ("name", merchantStoreCondition.getName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdCard())){
			cb.andEQ("idCard", merchantStoreCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg1())){
			cb.andEQ("idcardImg1", merchantStoreCondition.getIdcardImg1());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg2())){
			cb.andEQ("idcardImg2", merchantStoreCondition.getIdcardImg2());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg3())){
			cb.andEQ("idcardImg3", merchantStoreCondition.getIdcardImg3());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantLicense())){
			cb.andEQ("merchantLicense", merchantStoreCondition.getMerchantLicense());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantLicenseImg())){
			cb.andEQ("merchantLicenseImg", merchantStoreCondition.getMerchantLicenseImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStorePhotosImg())){
			cb.andEQ("storePhotosImg", merchantStoreCondition.getStorePhotosImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getGroupPhotoImg())){
			cb.andEQ("groupPhotoImg", merchantStoreCondition.getGroupPhotoImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreImg())){
			cb.andEQ("storeImg", merchantStoreCondition.getStoreImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIsPhoto())){
			cb.andEQ("isPhoto", merchantStoreCondition.getIsPhoto());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getOperator())){
			cb.andEQ("operator", merchantStoreCondition.getOperator());
		}
		if(null != merchantStoreCondition.getCreateTime()){
			cb.andEQ("createTime", merchantStoreCondition.getCreateTime());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getAuditOperator())){
			cb.andEQ("auditOperator", merchantStoreCondition.getAuditOperator());
		}
		if(null != merchantStoreCondition.getAuditDate()){
			cb.andEQ("auditDate", merchantStoreCondition.getAuditDate());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getAuditReson())){
			cb.andEQ("auditReson", merchantStoreCondition.getAuditReson());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreStatus())){
			cb.andEQ("storeStatus", merchantStoreCondition.getStoreStatus());
		}

		if(!Strings.isEmpty(merchantStoreCondition.getRemark())){
			cb.andLike("remark", merchantStoreCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp1())){
			cb.andEQ("temp1", merchantStoreCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp2())){
			cb.andEQ("temp2", merchantStoreCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp3())){
			cb.andEQ("temp3", merchantStoreCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp4())){
			cb.andEQ("temp4", merchantStoreCondition.getTemp4());
		}
		if(Strings.isNotEmpty(merchantStoreCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantStoreCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantStoreDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:58
	 */
	@Override
	public MerchantStore findById(String id){
		return merchantStoreDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param merchantStoreCondition
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:58
	 */
	@Override
	public long insert(MerchantStoreCondition merchantStoreCondition){
		MerchantStore merchantStore = new MerchantStore();
		try {
			MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantStoreCondition.getMerchantNo()));
			merchantStoreCondition.setChannelNo(merchantInfo.getChannelNo());
			merchantStoreCondition.setAgentNo(merchantInfo.getAgentNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		BeanUtils.copyProperties(merchantStoreCondition, merchantStore);
		long result = merchantStoreDAO.insert(merchantStore);
		try {
			List<MerchantStore> list = this.findAll(merchantStoreCondition);
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.STORE_BASE.getGroup(), RedisKeyEnum.STORE_BASE.getKey()+list.get(0).getStoreNo()), list.get(0));
		} catch (Exception e) {
			logger.error("#######保存门店数据到redis失败######");
		}
		return result;
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:58
	 */
	@Override
	public long deleteById(String id){
		return merchantStoreDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:58
	 */
	@Override
	public long deleteByCriteria(MerchantStoreCondition merchantStoreCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantStore.class);
		if(!Strings.isEmpty(merchantStoreCondition.getId())){
			cb.andEQ("id", merchantStoreCondition.getId());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantStoreCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreNo())){
			cb.andEQ("storeNo", merchantStoreCondition.getStoreNo());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreName())){
			cb.andEQ("storeName", merchantStoreCondition.getStoreName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreType())){
			cb.andEQ("storeType", merchantStoreCondition.getStoreType());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServicePhone())){
			cb.andEQ("servicePhone", merchantStoreCondition.getServicePhone());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServiceBegin())){
			cb.andEQ("serviceBegin", merchantStoreCondition.getServiceBegin());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServiceEnd())){
			cb.andEQ("serviceEnd", merchantStoreCondition.getServiceEnd());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreAddress())){
			cb.andEQ("storeAddress", merchantStoreCondition.getStoreAddress());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreAddressImg())){
			cb.andEQ("storeAddressImg", merchantStoreCondition.getStoreAddressImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreDesc())){
			cb.andEQ("storeDesc", merchantStoreCondition.getStoreDesc());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getLicenseName())){
			cb.andEQ("licenseName", merchantStoreCondition.getLicenseName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getName())){
			cb.andEQ("name", merchantStoreCondition.getName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdCard())){
			cb.andEQ("idCard", merchantStoreCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg1())){
			cb.andEQ("idcardImg1", merchantStoreCondition.getIdcardImg1());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg2())){
			cb.andEQ("idcardImg2", merchantStoreCondition.getIdcardImg2());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg3())){
			cb.andEQ("idcardImg3", merchantStoreCondition.getIdcardImg3());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantLicense())){
			cb.andEQ("merchantLicense", merchantStoreCondition.getMerchantLicense());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantLicenseImg())){
			cb.andEQ("merchantLicenseImg", merchantStoreCondition.getMerchantLicenseImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStorePhotosImg())){
			cb.andEQ("storePhotosImg", merchantStoreCondition.getStorePhotosImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getGroupPhotoImg())){
			cb.andEQ("groupPhotoImg", merchantStoreCondition.getGroupPhotoImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreImg())){
			cb.andEQ("storeImg", merchantStoreCondition.getStoreImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIsPhoto())){
			cb.andEQ("isPhoto", merchantStoreCondition.getIsPhoto());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getOperator())){
			cb.andEQ("operator", merchantStoreCondition.getOperator());
		}
		if(null != merchantStoreCondition.getCreateTime()){
			cb.andEQ("createTime", merchantStoreCondition.getCreateTime());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getAuditOperator())){
			cb.andEQ("auditOperator", merchantStoreCondition.getAuditOperator());
		}
		if(null != merchantStoreCondition.getAuditDate()){
			cb.andEQ("auditDate", merchantStoreCondition.getAuditDate());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getAuditReson())){
			cb.andEQ("auditReson", merchantStoreCondition.getAuditReson());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreStatus())){
			cb.andEQ("storeStatus", merchantStoreCondition.getStoreStatus());
		}

		if(!Strings.isEmpty(merchantStoreCondition.getRemark())){
			cb.andLike("remark", merchantStoreCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp1())){
			cb.andEQ("temp1", merchantStoreCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp2())){
			cb.andEQ("temp2", merchantStoreCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp3())){
			cb.andEQ("temp3", merchantStoreCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp4())){
			cb.andEQ("temp4", merchantStoreCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		MerchantStore merchantStore = new MerchantStore();
		BeanUtils.copyProperties(merchantStoreCondition, merchantStore);
		return merchantStoreDAO.deleteByCriteria(buildCriteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:58
	 */
	@Override
	public long countByCriteria(MerchantStoreCondition merchantStoreCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantStore.class);
		if(!Strings.isEmpty(merchantStoreCondition.getId())){
			cb.andEQ("id", merchantStoreCondition.getId());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantStoreCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreNo())){
			cb.andEQ("storeNo", merchantStoreCondition.getStoreNo());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreName())){
			cb.andEQ("storeName", merchantStoreCondition.getStoreName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreType())){
			cb.andEQ("storeType", merchantStoreCondition.getStoreType());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServicePhone())){
			cb.andEQ("servicePhone", merchantStoreCondition.getServicePhone());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServiceBegin())){
			cb.andEQ("serviceBegin", merchantStoreCondition.getServiceBegin());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServiceEnd())){
			cb.andEQ("serviceEnd", merchantStoreCondition.getServiceEnd());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreAddress())){
			cb.andEQ("storeAddress", merchantStoreCondition.getStoreAddress());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreAddressImg())){
			cb.andEQ("storeAddressImg", merchantStoreCondition.getStoreAddressImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreDesc())){
			cb.andEQ("storeDesc", merchantStoreCondition.getStoreDesc());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getLicenseName())){
			cb.andEQ("licenseName", merchantStoreCondition.getLicenseName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getName())){
			cb.andEQ("name", merchantStoreCondition.getName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdCard())){
			cb.andEQ("idCard", merchantStoreCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg1())){
			cb.andEQ("idcardImg1", merchantStoreCondition.getIdcardImg1());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg2())){
			cb.andEQ("idcardImg2", merchantStoreCondition.getIdcardImg2());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg3())){
			cb.andEQ("idcardImg3", merchantStoreCondition.getIdcardImg3());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantLicense())){
			cb.andEQ("merchantLicense", merchantStoreCondition.getMerchantLicense());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantLicenseImg())){
			cb.andEQ("merchantLicenseImg", merchantStoreCondition.getMerchantLicenseImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStorePhotosImg())){
			cb.andEQ("storePhotosImg", merchantStoreCondition.getStorePhotosImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getGroupPhotoImg())){
			cb.andEQ("groupPhotoImg", merchantStoreCondition.getGroupPhotoImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreImg())){
			cb.andEQ("storeImg", merchantStoreCondition.getStoreImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIsPhoto())){
			cb.andEQ("isPhoto", merchantStoreCondition.getIsPhoto());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getOperator())){
			cb.andEQ("operator", merchantStoreCondition.getOperator());
		}
		if(null != merchantStoreCondition.getCreateTime()){
			cb.andEQ("createTime", merchantStoreCondition.getCreateTime());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getAuditOperator())){
			cb.andEQ("auditOperator", merchantStoreCondition.getAuditOperator());
		}
		if(null != merchantStoreCondition.getAuditDate()){
			cb.andEQ("auditDate", merchantStoreCondition.getAuditDate());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getAuditReson())){
			cb.andEQ("auditReson", merchantStoreCondition.getAuditReson());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreStatus())){
			cb.andEQ("storeStatus", merchantStoreCondition.getStoreStatus());
		}

		if(!Strings.isEmpty(merchantStoreCondition.getRemark())){
			cb.andLike("remark", merchantStoreCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp1())){
			cb.andEQ("temp1", merchantStoreCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp2())){
			cb.andEQ("temp2", merchantStoreCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp3())){
			cb.andEQ("temp3", merchantStoreCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp4())){
			cb.andEQ("temp4", merchantStoreCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		MerchantStore merchantStore = new MerchantStore();
		BeanUtils.copyProperties(merchantStoreCondition, merchantStore);
		return merchantStoreDAO.countByCriteria(buildCriteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:58
	 */
	@Override
	public long update(MerchantStoreCondition merchantStoreCondition){
		MerchantStore merchantStore = new MerchantStore();
		BeanUtils.copyProperties(merchantStoreCondition, merchantStore);
		long result =merchantStoreDAO.update(merchantStore);
		try {
			List<MerchantStore> list = this.findAll(merchantStoreCondition);
			redisSharedCache.del(new RedisKey(RedisKeyEnum.STORE_BASE.getGroup(), RedisKeyEnum.STORE_BASE.getKey()+list.get(0).getStoreNo()));
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.STORE_BASE.getGroup(), RedisKeyEnum.STORE_BASE.getKey()+list.get(0).getStoreNo()), list.get(0));
		} catch (Exception e) {
			logger.error("#######保存门店数据到redis失败######");
		}
		return result;
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:58
	 */
	@Override
	public long updateByCriteria(MerchantStoreCondition merchantStoreCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantStore.class);
		if(!Strings.isEmpty(merchantStoreCondition.getId())){
			cb.andEQ("id", merchantStoreCondition.getId());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantStoreCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreNo())){
			cb.andEQ("storeNo", merchantStoreCondition.getStoreNo());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreName())){
			cb.andEQ("storeName", merchantStoreCondition.getStoreName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getName())){
			cb.andEQ("name", merchantStoreCondition.getName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdCard())){
			cb.andEQ("idCard", merchantStoreCondition.getIdCard());
		}
		Criteria buildCriteria = cb.buildCriteria();
		MerchantStore merchantStore = new MerchantStore();
		BeanUtils.copyProperties(merchantStoreCondition, merchantStore);
		return merchantStoreDAO.updateByCriteria(merchantStore,buildCriteria);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:58
	 */
	@Override
	public long updateByCriteria(MerchantStoreCondition merchantStoreCondition,Criteria buildCriteria){
		MerchantStore merchantStore = new MerchantStore();
		BeanUtils.copyProperties(merchantStoreCondition, merchantStore);
		return merchantStoreDAO.updateByCriteria(merchantStore,buildCriteria);
	}
	
	@Override
	public long applyStoreStep2(MerchantStoreCondition condition) {
		long result = 0L;
		MerchantStore store = findByMerchantNo(condition.getMerchantNo());
		MerchantStore merchantStore = new MerchantStore();
		condition.setStoreStatus("2");
		condition.setStoreType("0");
		if(null!=store){//存在更新
			if(!isUpdate(condition,store)){//没修改直接返回
				return 1;
			}
			BeanUtils.copyProperties(condition, merchantStore);
			merchantStore.setId(store.getId());
			merchantStore.setMerchantNo(store.getMerchantNo());
			merchantStore.setStoreNo(store.getStoreNo());
			result = merchantStoreDAO.update(merchantStore);
			MerchantInfoCondition merchantInfoCondition = new MerchantInfoCondition();
			merchantInfoCondition.setMerchantNo(store.getMerchantNo());
			merchantInfoCondition.setMerchantName(condition.getMerchantName());
			merchantInfoCondition.setShortName(condition.getMerchantName());
			merchantInfoService.updateInfoByMerchantNo(merchantInfoCondition);
		}
		else{
			isUpdate(condition,new MerchantStore());//图片问题
			BeanUtils.copyProperties(condition, merchantStore);
			merchantStore.setId(Strings.getUUID());
			ChannelExpandCondition channelExpandCondition = new ChannelExpandCondition();
			channelExpandCondition.setChannelNo(condition.getChannelNo());
			ChannelExpand channelExpand = channelExpandService.findAll(channelExpandCondition).get(0);
			merchantStore.setStoreNo(idCreateService.createParamNo(channelExpand.getChannelPreCode() + ConfigPreCode.STORE_PRE_CODE));
			merchantStore.setCreateTime(new Date());
			merchantStore.setRecordStatus(ScanCodeConstants.Y);
			result = merchantStoreDAO.insert(merchantStore);
			//商户姓名
			MerchantInfoCondition merchantInfoCondition = new MerchantInfoCondition();
			merchantInfoCondition.setMerchantNo(condition.getMerchantNo());
			merchantInfoCondition.setMerchantName(condition.getMerchantName());
			merchantInfoCondition.setShortName(condition.getMerchantName());
			merchantInfoService.updateInfoByMerchantNo(merchantInfoCondition);
		}
		
		//新增门店内容存放到redis
		try {
			redisSharedCache.del(new RedisKey(RedisKeyEnum.STORE_BASE.getGroup(), RedisKeyEnum.STORE_BASE.getKey()+merchantStore.getStoreNo()));
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.STORE_BASE.getGroup(), RedisKeyEnum.STORE_BASE.getKey()+merchantStore.getStoreNo()), merchantStore);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result>0){
			MerchantInfoCondition minfo = new MerchantInfoCondition();
			minfo.setMerchantNo(condition.getMerchantNo());
			minfo.setBusType(condition.getBusType());
			minfo.setMerchantLicenseImg(condition.getMerchantLicenseImg());
			minfo.setPhone(condition.getServicePhone());
			minfo.setAddress(condition.getStoreAddress());
			minfo.setMerchantLicense(condition.getMerchantLicense());
			minfo.setCheck(false);
			result = merchantInfoService.updateByMerchantNo(minfo);
			
			//更新门店ID到二维码表
			MerchantInfo info = merchantInfoService.findByMerchantNo(condition.getMerchantNo());
			PlatformQrcodeCondition platformQrcodeCondition = new PlatformQrcodeCondition();
			CriteriaBuilder cb = Cnd.builder(PlatformQrcode.class);
			logger.info("=======qrcode=="+info.getQrCode()+"  storeNo=="+merchantStore.getStoreNo());
			cb.andEQ("qrCode", info.getQrCode());
			platformQrcodeCondition.setStoreId(merchantStore.getStoreNo());
			Criteria buildCriteria = cb.buildCriteria();
			platformQrcodeService.updateByCriteria(platformQrcodeCondition, buildCriteria);
			
		}
		else{
			throw new RuntimeException("操作失败");
		}
		return result;
	}
	
	private boolean isUpdate(MerchantStoreCondition condition,MerchantStore store){
		String filePath = ScanCodeConstants.FILE_UPLOAD_ROOT_PATH+ScanCodeConstants.SPT+
				ScanCodeConstants.WECHAT_IMGUPLOAD_TYPE+ScanCodeConstants.SPT+condition.getMerchantNo()+ScanCodeConstants.SPT;
		MerchantInfo info = merchantInfoService.findByMerchantNo(condition.getMerchantNo());
		boolean isUpdate = false;
		if(!condition.getStoreName().equals(store.getStoreName())){
			isUpdate=true;
		}
		if(!condition.getStoreAddress().equals(store.getStoreAddress())){
			isUpdate=true;
		}
		if(!condition.getServicePhone().equals(store.getServicePhone())){
			isUpdate=true;
		}
		if(!condition.getMerchantLicense().equals(store.getMerchantLicense())){
			isUpdate=true;
		}
		if(!condition.getMerchantName().equals(info.getMerchantName())){//商户名称
			isUpdate=true;
		}
		if(!condition.getGroupPhotoImg().equals(store.getGroupPhotoImg())){
			logger.info("condition.getGroupPhotoImg() == "+condition.getGroupPhotoImg());
			String keyTail = RedisKeyEnum.MERCHANT_IMAGE_WECHAT.getKey()+":"+condition.getMerchantNo()+ConfigPreCode.KEY_GROUPPHONEIMG;
			RedisKey imgKey = new RedisKey(RedisKeyEnum.MERCHANT_IMAGE_WECHAT.getGroup(), keyTail);
			redisSharedCache.set(imgKey, condition.getGroupPhotoImg(),ConfigPreCode.KEY_LIVE_TIME);
			String img = merchantInfoService.downLoadImg(condition.getGroupPhotoImg(), filePath, info.getAgentNo());
			condition.setGroupPhotoImg(img);
			isUpdate=true;
		}
		if(!condition.getStoreImg().equals(store.getStoreImg())){
			logger.info("condition.getStoreImg() == "+condition.getStoreImg());
			String keyTail = RedisKeyEnum.MERCHANT_IMAGE_WECHAT.getKey()+":"+condition.getMerchantNo()+ConfigPreCode.KEY_STOREIMG;
			RedisKey imgKey = new RedisKey(RedisKeyEnum.MERCHANT_IMAGE_WECHAT.getGroup(), keyTail);
			redisSharedCache.set(imgKey, condition.getStoreImg(),ConfigPreCode.KEY_LIVE_TIME);
			String img = merchantInfoService.downLoadImg(condition.getStoreImg(), filePath, info.getAgentNo());
			condition.setStoreImg(img);		
			isUpdate=true;
		}
		if(!condition.getStorePhotosImg().equals(store.getStorePhotosImg())){
			logger.info("condition.getStorePhotosImg() == "+condition.getStorePhotosImg());
			String keyTail = RedisKeyEnum.MERCHANT_IMAGE_WECHAT.getKey()+":"+condition.getMerchantNo()+ConfigPreCode.KEY_STOREPHONESIMG;
			RedisKey imgKey = new RedisKey(RedisKeyEnum.MERCHANT_IMAGE_WECHAT.getGroup(), keyTail);
			redisSharedCache.set(imgKey, condition.getStorePhotosImg(),ConfigPreCode.KEY_LIVE_TIME);
			String img = merchantInfoService.downLoadImg(condition.getStorePhotosImg(), filePath, info.getAgentNo());
			condition.setStorePhotosImg(img);
			isUpdate=true;
		}
		if(!condition.getMerchantLicenseImg().equals(store.getMerchantLicenseImg())){
			logger.info("condition.getMerchantLicenseImg() == "+condition.getMerchantLicenseImg());
			String keyTail = RedisKeyEnum.MERCHANT_IMAGE_WECHAT.getKey()+":"+condition.getMerchantNo()+ConfigPreCode.KEY_MERCHANTLICENSEIMG;
			RedisKey imgKey = new RedisKey(RedisKeyEnum.MERCHANT_IMAGE_WECHAT.getGroup(), keyTail);
			redisSharedCache.set(imgKey, condition.getMerchantLicenseImg(),ConfigPreCode.KEY_LIVE_TIME);
			String img = merchantInfoService.downLoadImg(condition.getMerchantLicenseImg(), filePath, info.getAgentNo());
			condition.setMerchantLicenseImg(img);
			isUpdate=true;
		}
		
		return isUpdate;
	}
	
	@Override
	public MerchantStore findByMerchantNo(String merchantNo) {
		if(Strings.isEmpty(merchantNo)){
			throw new RuntimeException("商户编号不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(MerchantStore.class);
		cb.andEQ("merchantNo", merchantNo);
		Criteria buildCriteria = cb.buildCriteria();
		return merchantStoreDAO.findOneByCriteria(buildCriteria);
	}
	
	@Override
	public List<MerchantStore> findMerchantStore(String merchantNo) {
		if(Strings.isEmpty(merchantNo)){
			throw new RuntimeException("商户编号不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(MerchantStore.class);
		cb.andEQ("merchantNo", merchantNo);
		cb.orderBy("createTime", Order.ASC);
		Criteria buildCriteria = cb.buildCriteria();
		return merchantStoreDAO.findByCriteria(buildCriteria);
	}
	
	@Override
	public long updateByMerchantNo(MerchantStoreCondition condition) {
		MerchantStore store = new MerchantStore();
		BeanUtils.copyProperties(condition, store);
		long result = merchantStoreDAO.updateByMerchantNo(store);
		return result;
	}
	
	@Override
	public List<MerchantStore> findAllMerchantStore(MerchantStoreCondition merchantStoreCondition) {
		CriteriaBuilder cb = Cnd.builder(MerchantStore.class);
		if(!Strings.isEmpty(merchantStoreCondition.getId())){
			cb.andEQ("id", merchantStoreCondition.getId());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantStoreCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreNo())){
			cb.andEQ("storeNo", merchantStoreCondition.getStoreNo());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreName())){
			cb.andLike("storeName", merchantStoreCondition.getStoreName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreType())){
			cb.andEQ("storeType", merchantStoreCondition.getStoreType());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServicePhone())){
			cb.andEQ("servicePhone", merchantStoreCondition.getServicePhone());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServiceBegin())){
			cb.andEQ("serviceBegin", merchantStoreCondition.getServiceBegin());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServiceEnd())){
			cb.andEQ("serviceEnd", merchantStoreCondition.getServiceEnd());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreAddress())){
			cb.andEQ("storeAddress", merchantStoreCondition.getStoreAddress());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreAddressImg())){
			cb.andEQ("storeAddressImg", merchantStoreCondition.getStoreAddressImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreDesc())){
			cb.andEQ("storeDesc", merchantStoreCondition.getStoreDesc());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getLicenseName())){
			cb.andEQ("licenseName", merchantStoreCondition.getLicenseName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getName())){
			cb.andEQ("name", merchantStoreCondition.getName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdCard())){
			cb.andEQ("idCard", merchantStoreCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg1())){
			cb.andEQ("idcardImg1", merchantStoreCondition.getIdcardImg1());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg2())){
			cb.andEQ("idcardImg2", merchantStoreCondition.getIdcardImg2());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg3())){
			cb.andEQ("idcardImg3", merchantStoreCondition.getIdcardImg3());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantLicense())){
			cb.andEQ("merchantLicense", merchantStoreCondition.getMerchantLicense());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantLicenseImg())){
			cb.andEQ("merchantLicenseImg", merchantStoreCondition.getMerchantLicenseImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStorePhotosImg())){
			cb.andEQ("storePhotosImg", merchantStoreCondition.getStorePhotosImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getGroupPhotoImg())){
			cb.andEQ("groupPhotoImg", merchantStoreCondition.getGroupPhotoImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreImg())){
			cb.andEQ("storeImg", merchantStoreCondition.getStoreImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIsPhoto())){
			cb.andEQ("isPhoto", merchantStoreCondition.getIsPhoto());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getOperator())){
			cb.andEQ("operator", merchantStoreCondition.getOperator());
		}
		if(null != merchantStoreCondition.getCreateTime()){
			cb.andEQ("createTime", merchantStoreCondition.getCreateTime());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getAuditOperator())){
			cb.andEQ("auditOperator", merchantStoreCondition.getAuditOperator());
		}
		if(null != merchantStoreCondition.getAuditDate()){
			cb.andEQ("auditDate", merchantStoreCondition.getAuditDate());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getAuditReson())){
			cb.andEQ("auditReson", merchantStoreCondition.getAuditReson());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreStatus())){
			cb.andEQ("storeStatus", merchantStoreCondition.getStoreStatus());
		}

		if(!Strings.isEmpty(merchantStoreCondition.getRemark())){
			cb.andLike("remark", merchantStoreCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp1())){
			cb.andEQ("temp1", merchantStoreCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp2())){
			cb.andEQ("temp2", merchantStoreCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp3())){
			cb.andEQ("temp3", merchantStoreCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp4())){
			cb.andEQ("temp4", merchantStoreCondition.getTemp4());
		}
		if(Strings.isNotEmpty(merchantStoreCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantStoreCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantStoreDAO.findAllMerchantStore(buildCriteria);
	}
	
	/**设置门店redis
	 * @throws Exception */
	@Override
	public void setStoreRedis() throws Exception{
		MerchantStoreCondition merchantStoreCondition = new MerchantStoreCondition();
		List<MerchantStore> list = this.findAll(merchantStoreCondition);
		for (MerchantStore merchantStore : list) {
			redisSharedCache.del(new RedisKey(RedisKeyEnum.STORE_BASE.getGroup(), RedisKeyEnum.STORE_BASE.getKey()+merchantStore.getStoreNo()));
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.STORE_BASE.getGroup(), RedisKeyEnum.STORE_BASE.getKey()+merchantStore.getStoreNo()), merchantStore);
		}
	}
	
	@Override
	public MerchantStore findByCondition(MerchantStoreCondition merchantStoreCondition) {
		CriteriaBuilder cb = Cnd.builder(MerchantStore.class);
		if(!Strings.isEmpty(merchantStoreCondition.getId())){
			cb.andEQ("id", merchantStoreCondition.getId());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantStoreCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreNo())){
			cb.andEQ("storeNo", merchantStoreCondition.getStoreNo());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreName())){
			cb.andLike("storeName", merchantStoreCondition.getStoreName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreType())){
			cb.andEQ("storeType", merchantStoreCondition.getStoreType());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServicePhone())){
			cb.andEQ("servicePhone", merchantStoreCondition.getServicePhone());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServiceBegin())){
			cb.andEQ("serviceBegin", merchantStoreCondition.getServiceBegin());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServiceEnd())){
			cb.andEQ("serviceEnd", merchantStoreCondition.getServiceEnd());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreAddress())){
			cb.andEQ("storeAddress", merchantStoreCondition.getStoreAddress());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreAddressImg())){
			cb.andEQ("storeAddressImg", merchantStoreCondition.getStoreAddressImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreDesc())){
			cb.andEQ("storeDesc", merchantStoreCondition.getStoreDesc());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getLicenseName())){
			cb.andEQ("licenseName", merchantStoreCondition.getLicenseName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getName())){
			cb.andEQ("name", merchantStoreCondition.getName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdCard())){
			cb.andEQ("idCard", merchantStoreCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg1())){
			cb.andEQ("idcardImg1", merchantStoreCondition.getIdcardImg1());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg2())){
			cb.andEQ("idcardImg2", merchantStoreCondition.getIdcardImg2());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg3())){
			cb.andEQ("idcardImg3", merchantStoreCondition.getIdcardImg3());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantLicense())){
			cb.andEQ("merchantLicense", merchantStoreCondition.getMerchantLicense());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantLicenseImg())){
			cb.andEQ("merchantLicenseImg", merchantStoreCondition.getMerchantLicenseImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStorePhotosImg())){
			cb.andEQ("storePhotosImg", merchantStoreCondition.getStorePhotosImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getGroupPhotoImg())){
			cb.andEQ("groupPhotoImg", merchantStoreCondition.getGroupPhotoImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreImg())){
			cb.andEQ("storeImg", merchantStoreCondition.getStoreImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIsPhoto())){
			cb.andEQ("isPhoto", merchantStoreCondition.getIsPhoto());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getOperator())){
			cb.andEQ("operator", merchantStoreCondition.getOperator());
		}
		if(null != merchantStoreCondition.getCreateTime()){
			cb.andEQ("createTime", merchantStoreCondition.getCreateTime());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getAuditOperator())){
			cb.andEQ("auditOperator", merchantStoreCondition.getAuditOperator());
		}
		if(null != merchantStoreCondition.getAuditDate()){
			cb.andEQ("auditDate", merchantStoreCondition.getAuditDate());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getAuditReson())){
			cb.andEQ("auditReson", merchantStoreCondition.getAuditReson());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreStatus())){
			cb.andEQ("storeStatus", merchantStoreCondition.getStoreStatus());
		}

		if(!Strings.isEmpty(merchantStoreCondition.getRemark())){
			cb.andLike("remark", merchantStoreCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp1())){
			cb.andEQ("temp1", merchantStoreCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp2())){
			cb.andEQ("temp2", merchantStoreCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp3())){
			cb.andEQ("temp3", merchantStoreCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp4())){
			cb.andEQ("temp4", merchantStoreCondition.getTemp4());
		}
		if(Strings.isNotEmpty(merchantStoreCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantStoreCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantStoreDAO.findOneByCriteria(buildCriteria);
	}
}

