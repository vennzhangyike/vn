package com.hfepay.scancode.service.operator.impl;

import java.util.Date;
import java.util.HashMap;

/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.pay.service.PayCallBackOperatorService;
import com.hfepay.scancode.api.entity.vo.BankCardAuthVo;
import com.hfepay.scancode.api.entity.vo.MerchantUpdateCardVo;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.condition.AuditLogCondition;
import com.hfepay.scancode.commons.condition.MerchantAuthDetailCondition;
import com.hfepay.scancode.commons.condition.MerchantBankcardChangeCondition;
import com.hfepay.scancode.commons.condition.MerchantBankcardCondition;
import com.hfepay.scancode.commons.condition.MerchantInfoCondition;
import com.hfepay.scancode.commons.contants.MerchantBankcardStatus;
import com.hfepay.scancode.commons.contants.ParamsType;
import com.hfepay.scancode.commons.dao.AuditLogDAO;
import com.hfepay.scancode.commons.entity.AuditLog;
import com.hfepay.scancode.commons.entity.MerchantBankcard;
import com.hfepay.scancode.commons.entity.MerchantBankcardChange;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.ParamsInfo;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.AuditLogService;
import com.hfepay.scancode.service.operator.MerchantAuthDetailService;
import com.hfepay.scancode.service.operator.MerchantBankcardChangeService;
import com.hfepay.scancode.service.operator.MerchantBankcardService;
import com.hfepay.scancode.service.operator.MerchantInfoService;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

@Service("auditLogService")
public class AuditLogServiceImpl implements AuditLogService {
	
	@Autowired
    private AuditLogDAO auditLogDAO;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private MerchantBankcardService merchantBankcardService;
	@Autowired
	private MerchantAuthDetailService merchantAuthDetailService;
	@Autowired
	private MerchantBankcardChangeService merchantBankcardChangeService;
	@Autowired
	private PayCallBackOperatorService payCallBackOperatorService;
    
    /**
	 * 列表(分页)
	 * @param auditLogCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 10:14:46
	 */
    @Override
	public PagingResult<AuditLog> findPagingResult(AuditLogCondition auditLogCondition){
		CriteriaBuilder cb = Cnd.builder(AuditLog.class);
		if(!Strings.isEmpty(auditLogCondition.getId())){
			cb.andEQ("id", auditLogCondition.getId());
		}
		if(!Strings.isEmpty(auditLogCondition.getAuditId())){
			cb.andEQ("auditId", auditLogCondition.getAuditId());
		}
		if(!Strings.isEmpty(auditLogCondition.getAuditType())){
			cb.andEQ("auditType", auditLogCondition.getAuditType());
		}
		if(!Strings.isEmpty(auditLogCondition.getMerchantNo())){
			cb.andEQ("merchantNo", auditLogCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(auditLogCondition.getMerchantName())){
			cb.andLike("merchantName", auditLogCondition.getMerchantName());
		}
		if(!Strings.isEmpty(auditLogCondition.getAuditStatus())){
			cb.andEQ("auditStatus", auditLogCondition.getAuditStatus());
		}
		if(!Strings.isEmpty(auditLogCondition.getReason())){
			cb.andEQ("reason", auditLogCondition.getReason());
		}
		if(!Strings.isEmpty(auditLogCondition.getOperatorOrganizations())){
			cb.andEQ("operatorOrganizations", auditLogCondition.getOperatorOrganizations());
		}
		if(null != auditLogCondition.getCreateTime()){
			cb.andEQ("createTime", auditLogCondition.getCreateTime());
		}
		if(!Strings.isEmpty(auditLogCondition.getOperator())){
			cb.andEQ("operator", auditLogCondition.getOperator());
		}

		if(!Strings.isEmpty(auditLogCondition.getRemark())){
			cb.andLike("remark", auditLogCondition.getRemark());
		}
		if(!Strings.isEmpty(auditLogCondition.getTemp1())){
			cb.andEQ("temp1", auditLogCondition.getTemp1());
		}
		if(!Strings.isEmpty(auditLogCondition.getTemp2())){
			cb.andEQ("temp2", auditLogCondition.getTemp2());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(auditLogCondition.getOrderBy())){
			if(auditLogCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = auditLogCondition.getOrderBy().split(",");
				String[] orders = auditLogCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(auditLogCondition.getOrderBy(), Order.valueOf(auditLogCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(auditLogCondition.getFirst()), Long.valueOf(auditLogCondition.getLast()));
		return auditLogDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param auditLog 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 10:14:46
	 */
	@Override
	public List<AuditLog> findAll(AuditLogCondition auditLogCondition){
		CriteriaBuilder cb = Cnd.builder(AuditLog.class);
		if(!Strings.isEmpty(auditLogCondition.getId())){
			cb.andEQ("id", auditLogCondition.getId());
		}
		if(!Strings.isEmpty(auditLogCondition.getAuditId())){
			cb.andEQ("auditId", auditLogCondition.getAuditId());
		}
		if(!Strings.isEmpty(auditLogCondition.getAuditType())){
			cb.andEQ("auditType", auditLogCondition.getAuditType());
		}
		if(!Strings.isEmpty(auditLogCondition.getMerchantNo())){
			cb.andEQ("merchantNo", auditLogCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(auditLogCondition.getMerchantName())){
			cb.andLike("merchantName", auditLogCondition.getMerchantName());
		}
		if(!Strings.isEmpty(auditLogCondition.getAuditStatus())){
			cb.andEQ("auditStatus", auditLogCondition.getAuditStatus());
		}
		if(!Strings.isEmpty(auditLogCondition.getReason())){
			cb.andEQ("reason", auditLogCondition.getReason());
		}
		if(!Strings.isEmpty(auditLogCondition.getOperatorOrganizations())){
			cb.andEQ("operatorOrganizations", auditLogCondition.getOperatorOrganizations());
		}
		if(null != auditLogCondition.getCreateTime()){
			cb.andEQ("createTime", auditLogCondition.getCreateTime());
		}
		if(!Strings.isEmpty(auditLogCondition.getOperator())){
			cb.andEQ("operator", auditLogCondition.getOperator());
		}

		if(!Strings.isEmpty(auditLogCondition.getRemark())){
			cb.andLike("remark", auditLogCondition.getRemark());
		}
		if(!Strings.isEmpty(auditLogCondition.getTemp1())){
			cb.andEQ("temp1", auditLogCondition.getTemp1());
		}
		if(!Strings.isEmpty(auditLogCondition.getTemp2())){
			cb.andEQ("temp2", auditLogCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return auditLogDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 10:14:46
	 */
	@Override
	public AuditLog findById(String id){
		return auditLogDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param auditLogCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 10:14:46
	 */
	@Override
	public long insert(AuditLogCondition auditLogCondition){
		AuditLog auditLog = new AuditLog();
		BeanUtils.copyProperties(auditLogCondition, auditLog);
		return auditLogDAO.insert(auditLog);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 10:14:46
	 */
	@Override
	public long deleteById(String id){
		return auditLogDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 10:14:46
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return auditLogDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 10:14:46
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return auditLogDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 10:14:46
	 */
	@Override
	public long update(AuditLogCondition auditLogCondition){
		AuditLog auditLog = new AuditLog();
		BeanUtils.copyProperties(auditLogCondition, auditLog);
		return auditLogDAO.update(auditLog);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 10:14:46
	 */
	@Override
	public long updateByCriteria(AuditLogCondition auditLogCondition,Criteria criteria){
		AuditLog auditLog = new AuditLog();
		BeanUtils.copyProperties(auditLogCondition, auditLog);
		return auditLogDAO.updateByCriteria(auditLog,criteria);
	}
	
	/**商户银行卡审核 */
	@Override
	public Map<String,String> auditPlatform(AuditLogCondition auditLogCondition) throws Exception{
		MerchantBankcardCondition merchantBankcardCondition = new MerchantBankcardCondition();
		
		MerchantBankcardChange merchantBankcardChange = merchantBankcardChangeService.findById(auditLogCondition.getId());
		BeanUtils.copyProperties(merchantBankcardChange, merchantBankcardCondition);
		//通过审核，修改银行账户表字段
		if(("3").equals(auditLogCondition.getAuditStatus())){
			//商户基本信息
			MerchantInfo entity = merchantInfoService.findByMerchantNo(merchantBankcardCondition.getMerchantNo());
			merchantBankcardCondition.setStatus("1");//审核通过变成有效状态
//				if(!("").equals(bankCode)){
//					merchantBankcardCondition.setBankCode(bankCode);//添加银行行号
//				}
			
			merchantBankcardCondition.setUpdateTime(new Date());
			//审核通过，调用银行帐号变更接口
			MerchantUpdateCardVo merchantUpdateCardVo =new MerchantUpdateCardVo();
			merchantUpdateCardVo.setMerchantNo(entity.getPlatformMerchantNo());//传转接平台商户编号
			merchantUpdateCardVo.setMobile(merchantBankcardCondition.getMobile());
			merchantUpdateCardVo.setNewBankCode(merchantBankcardCondition.getBankCode());
			merchantUpdateCardVo.setNewBankCard(merchantBankcardCondition.getBankCard());
			merchantUpdateCardVo.setNewBankName(merchantBankcardCondition.getBankName());
			merchantUpdateCardVo.setAccountType(merchantBankcardCondition.getAccountType());
			
			ParamsInfo info = payCallBackOperatorService.findParamsKey(ScanCodeConstants.SYSTEM, ParamsType.PARAMS_CALLBACK_INFO.getCode());
			JSONObject callbackJson = JSONObject.fromObject(info.getParamsValue());
			merchantUpdateCardVo.setReturnURL(callbackJson.getString("bankChangeUrl"));
			
			//merchantUpdateCardVo.setReturnURL(callbackConfig.getBankChangeUrl());//回调地址
			
			//商户四要素验证
			/*Map<String,String> res = this.merchantBankCard4(map, entity,merchantBankcardCondition);
			if(!res.isEmpty()){
				return res;
			}*/
			
			//调用商户账户变更接口
			Map<String, String> returnMap = merchantBusinessService.merchantUpdateCard(merchantUpdateCardVo);
			String respCode = returnMap.get("respCode");
			if("000000".equals(respCode)){
				MerchantBankcard merchantBankcard = merchantBankcardService.findByMerchantNo(merchantBankcardCondition.getMerchantNo());
				merchantBankcardCondition.setId(merchantBankcard.getId());
				merchantBankcardCondition.setStatus(MerchantBankcardStatus.MERCHANTBANKCARD_STATUS_5.getCode());//渠道审批中
				merchantBankcardCondition.setUpdateTime(new Date());
				merchantBankcardService.updateStatus(merchantBankcardCondition.getId(),merchantBankcardCondition.getStatus());
				
				merchantBankcardChange.setStatus(MerchantBankcardStatus.MERCHANTBANKCARD_STATUS_5.getCode());//渠道审批中
				merchantBankcardChangeService.updateStatus(merchantBankcardChange.getId(),merchantBankcardChange.getStatus());
			}else{
				merchantBankcardChange.setStatus(MerchantBankcardStatus.MERCHANTBANKCARD_STATUS_4.getCode());//审核不通过
				merchantBankcardChange.setRemark(returnMap.get("respDesc"));
				MerchantBankcardChangeCondition merchantBankcardChangeCondition = new MerchantBankcardChangeCondition();
				BeanUtils.copyProperties(merchantBankcardChange, merchantBankcardChangeCondition);
				merchantBankcardChangeService.update(merchantBankcardChangeCondition);
				return returnMap;
			}
			
//				merchantBankcardService.update(merchantBankcardCondition);//在回调接口更新商户银行卡信息
		}else{
			MerchantBankcard merchantBankcard = merchantBankcardService.findByMerchantNo(merchantBankcardCondition.getMerchantNo());
			merchantBankcardCondition.setId(merchantBankcard.getId());
			merchantBankcardCondition.setStatus(MerchantBankcardStatus.MERCHANTBANKCARD_STATUS_1.getCode());//有效
			merchantBankcardCondition.setUpdateTime(new Date());
			merchantBankcardService.updateStatus(merchantBankcardCondition.getId(),merchantBankcardCondition.getStatus());
			
			merchantBankcardChange.setStatus(MerchantBankcardStatus.MERCHANTBANKCARD_STATUS_4.getCode());//渠道审批中
			merchantBankcardChangeService.updateStatus(merchantBankcardChange.getId(),merchantBankcardChange.getStatus());
//				
		}	
		
		//通过不通过，新增审核信息表数据
		//新增
		auditLogCondition.setCreateTime(new Date());
		auditLogCondition.setAuditId(auditLogCondition.getId());
		auditLogCondition.setAuditType("1");//银行账户变更
		auditLogCondition.setMerchantNo(merchantBankcardCondition.getMerchantNo());
		auditLogCondition.setId(Strings.getUUID());
		auditLogService.insert(auditLogCondition);
		return null;
	}

	@SuppressWarnings("unused")
	private Map<String,String> merchantBankCard4(Map<Object, Object> map, MerchantInfo entity,MerchantBankcardCondition bankcard) throws Exception {
		Map<String,String> result = new HashMap<String, String>();
		//获取银行卡列表
		MerchantBankcardCondition merchantBankcardCondition = new MerchantBankcardCondition();
		merchantBankcardCondition.setMerchantNo(entity.getMerchantNo());
		merchantBankcardCondition.setStatus("3");
		BankCardAuthVo auth = new BankCardAuthVo();
		auth.setRealName(bankcard.getName());
		auth.setBankCard(bankcard.getBankCard());
		auth.setIdCard(bankcard.getIdCard());
		auth.setMobile(bankcard.getMobile());
		Map<String,String> res = merchantBusinessService.bankCardAuth(auth,"3");
		String respCode = res.get("respCode");
		String respDesc = res.get("respDesc");
		if("000000".equals(respCode)){
			entity.setAuthenStatus(Integer.parseInt(ScanCodeConstants.STATUS_ACTIVE));
		}else{
			entity.setAuthenStatus(Integer.parseInt(ScanCodeConstants.STATUS_DISABLE));
			result.put("respCode", respCode);
			result.put("respDesc", respDesc);
		}
		
		this.saveMerchantAutoInfo(auth,entity,respCode,respDesc);
		
		String remark = entity.getRemark().split("==")[0];
		entity.setRemark(remark + "==auth--"+Dates.yyyyMMddHHmmss(new Date())+":"+respDesc);
		MerchantInfoCondition merchantInfoCondition = new MerchantInfoCondition();
		BeanUtils.copyProperties(entity, merchantInfoCondition);
		merchantInfoService.update(merchantInfoCondition);
		return result;
	}

	private void saveMerchantAutoInfo(BankCardAuthVo bankCardAuthVo, MerchantInfo entity, String respCode, String respDesc) {
		MerchantAuthDetailCondition merchantAuthDetailCondition = new MerchantAuthDetailCondition();
		merchantAuthDetailCondition.setId(Strings.getUUID());
		merchantAuthDetailCondition.setChannelNo(entity.getChannelNo());
		merchantAuthDetailCondition.setAgentNo(entity.getAgentNo());
		merchantAuthDetailCondition.setMerchantNo(entity.getMerchantNo());
		merchantAuthDetailCondition.setAuthInterface("bank_auth");
		merchantAuthDetailCondition.setAuthParams(JSONSerializer.toJSON(bankCardAuthVo).toString());
		merchantAuthDetailCondition.setReturnAuthCode(respCode);
		merchantAuthDetailCondition.setReturnAuthMsg(respDesc);
		merchantAuthDetailCondition.setCreateTime(new Date());
		
		merchantAuthDetailService.insert(merchantAuthDetailCondition);
		
	}
}
