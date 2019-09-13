/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mortbay.log.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ChannelBaseCondition;
import com.hfepay.scancode.commons.condition.PlatformQrcodeCondition;
import com.hfepay.scancode.commons.condition.QrcodeAssignedLogCondition;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.ChannelBaseDAO;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.ChannelBaseService;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.ParamsInfoService;
import com.hfepay.scancode.service.operator.PlatformQrcodeService;
import com.hfepay.scancode.service.operator.QrcodeAssignedLogService;
import com.hfepay.scancode.service.utils.QRCodeUtil;

@Service("channelBaseService")
public class ChannelBaseServiceImpl implements ChannelBaseService {
	
	@Autowired
    private ChannelBaseDAO channelBaseDAO;
	
	@Autowired
	private RedisSharedCache redisSharedCache;
	
	@Autowired
	private ChannelExpandService channelExpandService;
	@Autowired
	private PlatformQrcodeService platformQrcodeService;
	@Autowired
	private QrcodeAssignedLogService qrcodeAssignedLogService;
	@Autowired
	private ParamsInfoService paramsInfoService;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param channelBaseCondition
	 * @return: PagingResult<ChannelBase>
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
    @Override
	public PagingResult<ChannelBase> findPagingResult(ChannelBaseCondition channelBaseCondition){
		CriteriaBuilder cb = Cnd.builder(ChannelBase.class);
		if(!Strings.isEmpty(channelBaseCondition.getId())){
			cb.andEQ("id", channelBaseCondition.getId());
		}
		if(!Strings.isEmpty(channelBaseCondition.getChannelNo())){
			cb.andEQ("channelNo", channelBaseCondition.getChannelNo());
		}
		if(!Strings.isEmpty(channelBaseCondition.getChannelName())){
			cb.andLike("channelName", channelBaseCondition.getChannelName());
		}
		if(!Strings.isEmpty(channelBaseCondition.getChannelType())){
			cb.andEQ("channelType", channelBaseCondition.getChannelType());
		}
		if(null != channelBaseCondition.getQrTotal()){
			cb.andEQ("qrTotal", channelBaseCondition.getQrTotal());
		}
		if(null != channelBaseCondition.getUseTotal()){
			cb.andEQ("useTotal", channelBaseCondition.getUseTotal());
		}
		if(null != channelBaseCondition.getLessTotal()){
			cb.andEQ("lessTotal", channelBaseCondition.getLessTotal());
		}
		if(!Strings.isEmpty(channelBaseCondition.getStatus())){
			cb.andEQ("status", channelBaseCondition.getStatus());
		}
		if(!Strings.isEmpty(channelBaseCondition.getRecordStatus())){
			cb.andEQ("recordStatus", channelBaseCondition.getRecordStatus());
		}
		if(null != channelBaseCondition.getCreateTime()){
			cb.andEQ("createTime", channelBaseCondition.getCreateTime());
		}

		if(!Strings.isEmpty(channelBaseCondition.getOperator())){
			cb.andEQ("operator", channelBaseCondition.getOperator());
		}

		if(!Strings.isEmpty(channelBaseCondition.getRemark())){
			cb.andLike("remark", channelBaseCondition.getRemark());
		}
		if(!Strings.isEmpty(channelBaseCondition.getTemp1())){
			cb.andEQ("temp1", channelBaseCondition.getTemp1());
		}
		if(!Strings.isEmpty(channelBaseCondition.getTemp2())){
			cb.andEQ("temp2", channelBaseCondition.getTemp2());
		}
		if(!Strings.isEmpty(channelBaseCondition.getTemp3())){
			cb.andEQ("temp3", channelBaseCondition.getTemp3());
		}
		if(!Strings.isEmpty(channelBaseCondition.getTemp4())){
			cb.andEQ("temp4", channelBaseCondition.getTemp4());
		}
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(channelBaseCondition.getFirst()), Long.valueOf(channelBaseCondition.getLast()));
		return channelBaseDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param channelBaseCondition
	 * @return: List<ChannelBase>
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	@Override
	public List<ChannelBase> findAll(ChannelBaseCondition channelBaseCondition){
		CriteriaBuilder cb = Cnd.builder(ChannelBase.class);
		if(!Strings.isEmpty(channelBaseCondition.getId())){
			cb.andEQ("id", channelBaseCondition.getId());
		}
		if(!Strings.isEmpty(channelBaseCondition.getChannelNo())){
			cb.andEQ("channelNo", channelBaseCondition.getChannelNo());
		}
		if(!Strings.isEmpty(channelBaseCondition.getChannelName())){
			cb.andLike("channelName", channelBaseCondition.getChannelName());
		}
		if(!Strings.isEmpty(channelBaseCondition.getChannelType())){
			cb.andEQ("channelType", channelBaseCondition.getChannelType());
		}
		if(null != channelBaseCondition.getQrTotal()){
			cb.andEQ("qrTotal", channelBaseCondition.getQrTotal());
		}
		if(null != channelBaseCondition.getUseTotal()){
			cb.andEQ("useTotal", channelBaseCondition.getUseTotal());
		}
		if(null != channelBaseCondition.getLessTotal()){
			cb.andEQ("lessTotal", channelBaseCondition.getLessTotal());
		}
		if(!Strings.isEmpty(channelBaseCondition.getStatus())){
			cb.andEQ("status", channelBaseCondition.getStatus());
		}
		if(!Strings.isEmpty(channelBaseCondition.getRecordStatus())){
			cb.andEQ("recordStatus", channelBaseCondition.getRecordStatus());
		}
		if(null != channelBaseCondition.getCreateTime()){
			cb.andEQ("createTime", channelBaseCondition.getCreateTime());
		}

		if(!Strings.isEmpty(channelBaseCondition.getOperator())){
			cb.andEQ("operator", channelBaseCondition.getOperator());
		}

		if(!Strings.isEmpty(channelBaseCondition.getRemark())){
			cb.andLike("remark", channelBaseCondition.getRemark());
		}
		if(!Strings.isEmpty(channelBaseCondition.getTemp1())){
			cb.andEQ("temp1", channelBaseCondition.getTemp1());
		}
		if(!Strings.isEmpty(channelBaseCondition.getTemp2())){
			cb.andEQ("temp2", channelBaseCondition.getTemp2());
		}
		if(!Strings.isEmpty(channelBaseCondition.getTemp3())){
			cb.andEQ("temp3", channelBaseCondition.getTemp3());
		}
		if(!Strings.isEmpty(channelBaseCondition.getTemp4())){
			cb.andEQ("temp4", channelBaseCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return channelBaseDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ChannelBase
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	@Override
	public ChannelBase findById(String id){
		return channelBaseDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param channelBaseCondition
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	@Override
	public long insert(ChannelBaseCondition channelBaseCondition){
		ChannelBase channelBase = new ChannelBase();
		channelBase.setId(Strings.getUUID());
		BeanUtils.copyProperties(channelBaseCondition, channelBase);
		try {
			redisSharedCache.del(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+channelBase.getChannelNo()));
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+channelBase.getChannelNo()), channelBase);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return channelBaseDAO.insert(channelBase);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	@Override
	public long deleteById(String id){
		return channelBaseDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return channelBaseDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return channelBaseDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param channelBaseCondition
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	@Override
	public long update(ChannelBaseCondition channelBaseCondition){
		ChannelBase channelBase = new ChannelBase();
		BeanUtils.copyProperties(channelBaseCondition, channelBase);
		channelBase.setUpdateTime(new Date());
		
		try {
			redisSharedCache.del(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+channelBase.getChannelNo()));
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+channelBase.getChannelNo()), channelBase);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return channelBaseDAO.update(channelBase);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param channelBaseCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	@Override
	public long updateByCriteria(ChannelBaseCondition channelBaseCondition){
		ChannelBase channelBase = new ChannelBase();
		channelBase.setId(channelBaseCondition.getId());
		channelBase.setUseTotal(channelBaseCondition.getUseTotal());
		channelBase.setLessTotal(channelBaseCondition.getLessTotal());
		CriteriaBuilder cb = Cnd.builder(ChannelBase.class);
		if(!Strings.isEmpty(channelBaseCondition.getId())){
			cb.andEQ("id", channelBaseCondition.getId());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return channelBaseDAO.updateByCriteria(channelBase,buildCriteria);
	}
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	@Override
	public long updateStatus(String id,String status){
		return channelBaseDAO.updateStatus(id,status);
	}	
	
	/**
	 * @Title: findByChannelNo
	 * @Description: 渠道编码查找
	 * @author: Ricky
	 * @param channelNo
	 * @return: ChannelBase
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	@Override
	public ChannelBase findByChannelNo(String channelNo){
		ChannelBase channelBase= null;
		if(Strings.isEmpty(channelNo)){
			throw new RuntimeException("渠道编号不能为空");
		}
		
		try {
			//商户信息从缓存中获取
//			channelBase = (ChannelBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+channelNo));
			if (channelBase == null) {				
				CriteriaBuilder cb = Cnd.builder(ChannelBase.class);
				cb.andEQ("channelNo", channelNo);
				cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
				Criteria buildCriteria = cb.buildCriteria();
				channelBase = channelBaseDAO.findOneByCriteria(buildCriteria);
				redisSharedCache.setObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+channelNo),channelBase);
				return channelBase;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return channelBase;
	}
	
	/**
	 * 生成二维码(内嵌LOGO)
	 * 
	 * @param content
	 *            内容
	 * @param imgPath
	 *            LOGO地址
	 * @param destPath
	 *            存放目录
	 * @param needCompress
	 *            是否压缩LOGO
	 * @throws Exception
	 */
	public void encode(String content, String imgPath, String destPath,
			boolean needCompress,String qrName) throws Exception{
		QRCodeUtil.encode(content, imgPath, destPath,
				needCompress,qrName);
	}
	
	/** 添加二维码 */
	@Override
	@Transactional
	public void addQrcode(String channelNo ,String qrcodeNum,Admin user){
		ChannelExpand channelExpand = channelExpandService.findByChannelNo(channelNo);
		net.sf.json.JSONObject paramsInfoJson = paramsInfoService.getParamsInfoByDomain(channelNo);
		Log.info(channelNo +"需要生成二维码编号的个数：" + qrcodeNum);
		int qrNumber = Integer.parseInt(qrcodeNum);
		if(channelExpand != null){
			Set<String> setQr = new HashSet<String>();
			List<PlatformQrcodeCondition> qrList = new ArrayList<PlatformQrcodeCondition>();
			for(int i=1;i<=qrNumber;i++ ){
				String qrCode = platformQrcodeService.getQrcodeCode(setQr,platformQrcodeService.getQrcodeCode());
				String qrName = qrCode + ".png";//二维码名称
				StringBuffer content = new StringBuffer();//二维码扫码地址+参数(渠道编码+二维码编码)
				
				content.append(paramsInfoJson.getString("qrcodePath"));
				content.append("?channelNo=");
				content.append(channelExpand.getChannelNo());
				content.append("&qrCode=");
				content.append(qrCode);
				
				PlatformQrcodeCondition platformQrcodeCondition = new PlatformQrcodeCondition();
				platformQrcodeCondition.setId(Strings.getUUID());
				platformQrcodeCondition.setChannelNo(channelExpand.getChannelNo());
				platformQrcodeCondition.setQrName(qrName);
				platformQrcodeCondition.setQrCode(qrCode);
				platformQrcodeCondition.setImage(content.toString());
				platformQrcodeCondition.setQrDesc("");
				platformQrcodeCondition.setUseStatus(ScanCodeConstants.STATUS_NOT_USE);
				platformQrcodeCondition.setQrStatus(ScanCodeConstants.STATUS_ACTIVE);
				platformQrcodeCondition.setRecordStatus(ScanCodeConstants.Y);
				platformQrcodeCondition.setCreateTime(new Date());
				platformQrcodeCondition.setOperator(user.getUserName());
				qrList.add(platformQrcodeCondition);
				if(i%100==0){//每一百条批量插入一次
					platformQrcodeService.batchInsert(qrList);
					qrList.clear();
					Log.info(channelNo +"生成二维码编号的个数：" + setQr.size());
				}
			}
			if(!qrList.isEmpty()){
				platformQrcodeService.batchInsert(qrList);
				qrList.clear();
				Log.info(channelNo +"最终生成二维码编号的个数：" + setQr.size());
			}
			
			ChannelBaseCondition channelBaseCondition = new ChannelBaseCondition();
			ChannelBase channelBase = this.findByChannelNo(channelNo);
			BeanUtils.copyProperties(channelBase, channelBaseCondition);
			int qrTotal = qrNumber + channelBase.getQrTotal();
			int lessTotal = qrNumber + channelBase.getLessTotal();
			channelBaseCondition.setQrTotal(qrTotal);
			channelBaseCondition.setLessTotal(lessTotal);
			this.update(channelBaseCondition);//更新二维码数量
			QrcodeAssignedLogCondition qrcodeAssignedLogCondition = new QrcodeAssignedLogCondition();
			qrcodeAssignedLogCondition.setId(Strings.getUUID());
			qrcodeAssignedLogCondition.setAssignment(ScanCodeConstants.SYSTEM);
			qrcodeAssignedLogCondition.setRecipients(channelExpand.getChannelNo());				
			qrcodeAssignedLogCondition.setQrNumber(qrNumber);
			qrcodeAssignedLogCondition.setAssignedType(ScanCodeConstants.ASSIGNEDTYPE_ACTIVE);
			qrcodeAssignedLogCondition.setCreateTime(new Date());
			qrcodeAssignedLogCondition.setOperator(user.getUserName());
			qrcodeAssignedLogService.insert(qrcodeAssignedLogCondition);//二维码流水日志
			Log.info(channelNo +"二维码流水日志插入完成");
		}
	};
}

