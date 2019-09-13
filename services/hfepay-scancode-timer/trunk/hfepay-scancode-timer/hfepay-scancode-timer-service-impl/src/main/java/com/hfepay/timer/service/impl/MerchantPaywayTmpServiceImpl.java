/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.timer.service.impl;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.api.entity.vo.MerchantRateVo;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.condition.MerchantPaywayCondition;
import com.hfepay.scancode.commons.condition.MerchantPaywayTmpCondition;
import com.hfepay.scancode.commons.contants.ScanCodeConstants;
import com.hfepay.scancode.commons.dao.MerchantPaywayDAO;
import com.hfepay.scancode.commons.dao.MerchantPaywayTmpDAO;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.commons.entity.MerchantPaywayTmp;
import com.hfepay.timer.service.MerchantPaywayTmpService;

import net.sf.json.JSONSerializer;

@Service("merchantPaywayTmpService")
public class MerchantPaywayTmpServiceImpl implements MerchantPaywayTmpService {
	
	public static final Logger log = LoggerFactory.getLogger(MerchantPaywayTmpServiceImpl.class);
	
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	
	@Autowired
    private MerchantPaywayTmpDAO merchantPaywayTmpDAO;
	@Autowired
	private MerchantPaywayDAO merchantPaywayDAO;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param merchantPaywayTmpCondition
	 * @return: PagingResult<MerchantPaywayTmp>
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
    @Override
	public PagingResult<MerchantPaywayTmp> findPagingResult(MerchantPaywayTmpCondition merchantPaywayTmpCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantPaywayTmp.class);
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getId())){
			cb.andEQ("id", merchantPaywayTmpCondition.getId());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantPaywayTmpCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getPayCode())){
			cb.andEQ("payCode", merchantPaywayTmpCondition.getPayCode());
		}
		if(null != merchantPaywayTmpCondition.getT0Rate()){
			cb.andEQ("t0Rate", merchantPaywayTmpCondition.getT0Rate());
		}
		if(null != merchantPaywayTmpCondition.getT1Rate()){
			cb.andEQ("t1Rate", merchantPaywayTmpCondition.getT1Rate());
		}
		if(null != merchantPaywayTmpCondition.getRate()){
			cb.andEQ("rate", merchantPaywayTmpCondition.getRate());
		}
		if(null != merchantPaywayTmpCondition.getRateAmount()){
			cb.andEQ("rateAmount", merchantPaywayTmpCondition.getRateAmount());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantPaywayTmpCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getStatus())){
			cb.andEQ("status", merchantPaywayTmpCondition.getStatus());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getAcceptStatus())){
			cb.andEQ("acceptStatus", merchantPaywayTmpCondition.getAcceptStatus());
		}
		if(null != merchantPaywayTmpCondition.getCreateTime()){
			cb.andEQ("createTime", merchantPaywayTmpCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantPaywayTmpCondition.getOperator())){
			cb.andEQ("operator", merchantPaywayTmpCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantPaywayTmpCondition.getRemark())){
			cb.andLike("remark", merchantPaywayTmpCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getTemp1())){
			cb.andEQ("temp1", merchantPaywayTmpCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getTemp2())){
			cb.andEQ("temp2", merchantPaywayTmpCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantPaywayTmpCondition.getFirst()), Long.valueOf(merchantPaywayTmpCondition.getLast()));
		return merchantPaywayTmpDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param merchantPaywayTmpCondition
	 * @return: List<MerchantPaywayTmp>
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public List<MerchantPaywayTmp> findAll(MerchantPaywayTmpCondition merchantPaywayTmpCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantPaywayTmp.class);
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getId())){
			cb.andEQ("id", merchantPaywayTmpCondition.getId());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantPaywayTmpCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getPayCode())){
			cb.andEQ("payCode", merchantPaywayTmpCondition.getPayCode());
		}
		if(null != merchantPaywayTmpCondition.getT0Rate()){
			cb.andEQ("t0Rate", merchantPaywayTmpCondition.getT0Rate());
		}
		if(null != merchantPaywayTmpCondition.getT1Rate()){
			cb.andEQ("t1Rate", merchantPaywayTmpCondition.getT1Rate());
		}
		if(null != merchantPaywayTmpCondition.getRate()){
			cb.andEQ("rate", merchantPaywayTmpCondition.getRate());
		}
		if(null != merchantPaywayTmpCondition.getRateAmount()){
			cb.andEQ("rateAmount", merchantPaywayTmpCondition.getRateAmount());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantPaywayTmpCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getStatus())){
			cb.andEQ("status", merchantPaywayTmpCondition.getStatus());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getAcceptStatus())){
			cb.andEQ("acceptStatus", merchantPaywayTmpCondition.getAcceptStatus());
		}
		if(null != merchantPaywayTmpCondition.getCreateTime()){
			cb.andEQ("createTime", merchantPaywayTmpCondition.getCreateTime());
		}		
		if(null != merchantPaywayTmpCondition.getUpdateTime()){
			cb.andGE("updateTime", merchantPaywayTmpCondition.getUpdateTime());
		}
		if(null != merchantPaywayTmpCondition.getUpdateTime()){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(merchantPaywayTmpCondition.getUpdateTime());
			calendar.add(calendar.DATE, 1);
			cb.andLE("updateTime", calendar.getTime());
		}

		if(!Strings.isEmpty(merchantPaywayTmpCondition.getOperator())){
			cb.andEQ("operator", merchantPaywayTmpCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantPaywayTmpCondition.getRemark())){
			cb.andLike("remark", merchantPaywayTmpCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getTemp1())){
			cb.andEQ("temp1", merchantPaywayTmpCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getTemp2())){
			cb.andEQ("temp2", merchantPaywayTmpCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantPaywayTmpDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: MerchantPaywayTmp
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public MerchantPaywayTmp findById(String id){
		return merchantPaywayTmpDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param merchantPaywayTmpCondition
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public long insert(MerchantPaywayTmpCondition merchantPaywayTmpCondition){
		MerchantPaywayTmp merchantPaywayTmp = new MerchantPaywayTmp();
		BeanUtils.copyProperties(merchantPaywayTmpCondition, merchantPaywayTmp);
		merchantPaywayTmp.setCreateTime(new Date());
		merchantPaywayTmp.setUpdateTime(new Date());
		return merchantPaywayTmpDAO.insert(merchantPaywayTmp);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public long deleteById(String id){
		return merchantPaywayTmpDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return merchantPaywayTmpDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return merchantPaywayTmpDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param merchantPaywayTmpCondition
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public long update(MerchantPaywayTmpCondition merchantPaywayTmpCondition){
		MerchantPaywayTmp merchantPaywayTmp = new MerchantPaywayTmp();
		BeanUtils.copyProperties(merchantPaywayTmpCondition, merchantPaywayTmp);
		merchantPaywayTmp.setUpdateTime(new Date());
		return merchantPaywayTmpDAO.update(merchantPaywayTmp);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param merchantPaywayTmpCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public long updateByCriteria(MerchantPaywayTmpCondition merchantPaywayTmpCondition,Criteria criteria){
		MerchantPaywayTmp merchantPaywayTmp = new MerchantPaywayTmp();
		BeanUtils.copyProperties(merchantPaywayTmpCondition, merchantPaywayTmp);
		return merchantPaywayTmpDAO.updateByCriteria(merchantPaywayTmp,criteria);
	}
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public long updateStatus(String id,String status){
		return merchantPaywayTmpDAO.updateStatus(id,status);
	}	
	
	/**
	 * @Title: save
	 * @Description: 新增、更新
	 * @author: Ricky
	 * @param merchantPaywayTmpCondition
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public void save(MerchantPaywayTmpCondition merchantPaywayTmpCondition){
		MerchantPaywayTmp merchantPaywayTmp = this.findByPayCode(merchantPaywayTmpCondition.getPayCode(), merchantPaywayTmpCondition.getMerchantNo());
		if(merchantPaywayTmp == null){			
			this.insert(merchantPaywayTmpCondition);
		}else{
			merchantPaywayTmpCondition.setId(merchantPaywayTmp.getId());
			this.update(merchantPaywayTmpCondition);
		}
	}	
	
	/**
	 * 根据支付通道查询
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public MerchantPaywayTmp findByPayCode(String payCode, String merchantNo) {
		CriteriaBuilder cb = Cnd.builder(MerchantPaywayTmp.class);
		cb.andEQ("payCode", payCode);
		cb.andEQ("merchantNo", merchantNo);
		Criteria buildCriteria = cb.buildCriteria();
		return merchantPaywayTmpDAO.findOneByCriteria(buildCriteria);
	}
	
	/** 更新商户费率JOB
	 * @throws ParseException */
	@Override
	public void updatePaywayJob() throws ParseException{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);		
		String yesterday = Dates.format(Dates.DF.yyyy_MM_dd, cal.getTime());
		log.info("更新商户费率的时间为："+yesterday);
		MerchantPaywayTmpCondition merchantPaywayTmpCondition = new MerchantPaywayTmpCondition();
		MerchantPaywayCondition merchantPaywayCondition = new MerchantPaywayCondition();
		merchantPaywayTmpCondition.setUpdateTime(Dates.parse("yyyy-MM-dd", yesterday));
		List<MerchantPaywayTmp> merchantPaywayTmps = this.findAll(merchantPaywayTmpCondition);
		log.info("共更新商户费率："+merchantPaywayTmps.size() + "条");
		for (MerchantPaywayTmp merchantPaywayTmp : merchantPaywayTmps) {
			CriteriaBuilder cbPayway = Cnd.builder(MerchantPayway.class);
			cbPayway.andEQ("payCode", merchantPaywayTmp.getPayCode());
			cbPayway.andEQ("merchantNo", merchantPaywayTmp.getMerchantNo());
			cbPayway.andEQ("recordStatus", "Y");
			Criteria buildCriteriaPayway = cbPayway.buildCriteria();
			MerchantPayway merchantPayway =  merchantPaywayDAO.findOneByCriteria(buildCriteriaPayway);
			if(merchantPayway != null && !Strings.equals(merchantPaywayTmp.getAcceptStatus(), ScanCodeConstants.SUCCESS_STATE)){
				MerchantPaywayTmpCondition tmp = new MerchantPaywayTmpCondition();
				tmp.setId(merchantPaywayTmp.getId());
				merchantPaywayTmp.setId(merchantPayway.getId());
				merchantPaywayTmp.setCreateTime(null);
				BeanUtils.copyProperties(merchantPaywayTmp, merchantPaywayCondition);
				merchantPaywayCondition.setRateAmount(merchantPayway.getRateAmount());
				log.info("更新商户费率入参："+JSONSerializer.toJSON(merchantPaywayCondition));
				
				MerchantRateVo payway = new MerchantRateVo();
				payway.setPayCode(merchantPaywayCondition.getPayCode());
				payway.setTradeRate(this.objToString(String.valueOf(merchantPaywayCondition.getT1Rate())));
				payway.setWithdrawAmt(this.objToString(String.valueOf(merchantPaywayCondition.getRate())));
				payway.setWithdrawRate(this.objToString(String.valueOf(merchantPaywayCondition.getT0Rate())));
				payway.setSettleAmt(this.objToString(String.valueOf(merchantPaywayCondition.getRateAmount())));
				//调用商户费率变更接口
				try {
					Map<String, String> map = merchantBusinessService.merchantUpdateRate(merchantPaywayCondition.getMerchantNo(), payway);
					log.info("调用商户费率变更接口成功："+map);
					if(Strings.equals(ScanCodeConstants.INTERFACE_SUCCESS_STATUS, map.get("respCode"))){
						MerchantPayway updatePayway = new MerchantPayway();
						BeanUtils.copyProperties(merchantPaywayCondition, updatePayway);
						merchantPaywayDAO.update(updatePayway);
						log.info("更新商户费率成功："+JSONSerializer.toJSON(merchantPaywayCondition));
						CriteriaBuilder cb = Cnd.builder(MerchantPayway.class);
						cb.andEQ("id", tmp.getId());
						Criteria buildCriteria = cb.buildCriteria();
						tmp.setAcceptStatus(ScanCodeConstants.SUCCESS_STATE);
						this.updateByCriteria(tmp, buildCriteria);
						log.info("更新商户费率临时表成功："+JSONSerializer.toJSON(tmp));
					}
					
				} catch (Exception e) {
					log.info("调用商户费率变更接口失败："+e.getMessage());
				}
			}else{
				log.info("更新商户费率失败："+JSONSerializer.toJSON(merchantPaywayTmp));
			}
		}
	}
	/** 将null对象转换成空字符串 */
	public String objToString(String obj){
		if(obj == null){
			return "";
		}
		return obj;
	}
}

