package com.hfepay.scancode.service.operator.impl;

import java.math.BigDecimal;
import java.util.Date;

/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */

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
import com.hfepay.scancode.commons.BatchNoUtil;
import com.hfepay.scancode.commons.TransCodeEnum;
import com.hfepay.scancode.commons.condition.ChangeLogCondition;
import com.hfepay.scancode.commons.condition.MerchantAuthDetailCondition;
import com.hfepay.scancode.commons.condition.MerchantBankcardChangeCondition;
import com.hfepay.scancode.commons.condition.MerchantBankcardCondition;
import com.hfepay.scancode.commons.condition.MerchantInfoCondition;
import com.hfepay.scancode.commons.contants.MerchantBankcardStatus;
import com.hfepay.scancode.commons.contants.MerchantStatus;
import com.hfepay.scancode.commons.contants.ParamsType;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.contants.ScanCodeConstants;
import com.hfepay.scancode.commons.dao.MerchantBankcardDAO;
import com.hfepay.scancode.commons.entity.MerchantBankcard;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.ParamsInfo;
import com.hfepay.scancode.service.operator.ChangeLogService;
import com.hfepay.scancode.service.operator.MerchantAuthDetailService;
import com.hfepay.scancode.service.operator.MerchantBankcardChangeService;
import com.hfepay.scancode.service.operator.MerchantBankcardService;
import com.hfepay.scancode.service.operator.MerchantInfoService;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

@Service("merchantBankcardService")
public class MerchantBankcardServiceImpl implements MerchantBankcardService {
	public static final Logger logger = LoggerFactory.getLogger(MerchantBankcardServiceImpl.class);

	@Autowired
    private MerchantBankcardDAO merchantBankcardDAO;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private ChangeLogService changeLogService;
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	@Autowired
	private MerchantBankcardChangeService merchantBankcardChangeService;
    @Autowired
    private MerchantAuthDetailService merchantAuthDetailService;
    @Autowired
    private PayCallBackOperatorService payCallBackOperatorService;
	@Autowired
	private RedisSharedCache redisSharedCache;
	
    /**
	 * 列表(分页)
	 * @param merchantBankcardCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
    @Override
	public PagingResult<MerchantBankcard> findPagingResult(MerchantBankcardCondition merchantBankcardCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantBankcard.class);
		if(!Strings.isEmpty(merchantBankcardCondition.getId())){
			cb.andEQ("id", merchantBankcardCondition.getId());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantBankcardCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getMerchantName())){
			cb.andLike("merchantName", merchantBankcardCondition.getMerchantName());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getIdCard())){
			cb.andEQ("idCard", merchantBankcardCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getName())){
			cb.andEQ("name", merchantBankcardCondition.getName());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getBankCode())){
			cb.andEQ("bankCode", merchantBankcardCondition.getBankCode());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getBankName())){
			cb.andEQ("bankName", merchantBankcardCondition.getBankName());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getBankCard())){
			cb.andEQ("bankCard", merchantBankcardCondition.getBankCard());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getMobile())){
			cb.andEQ("mobile", merchantBankcardCondition.getMobile());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getIsRealAccount())){
			cb.andEQ("isRealAccount", merchantBankcardCondition.getIsRealAccount());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getAccountType())){
			cb.andEQ("accountType", merchantBankcardCondition.getAccountType());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getStatus())){
			cb.andEQ("status", merchantBankcardCondition.getStatus());
		}
		if(null != merchantBankcardCondition.getCreateTime()){
			cb.andEQ("createTime", merchantBankcardCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantBankcardCondition.getOperator())){
			cb.andEQ("operator", merchantBankcardCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantBankcardCondition.getRemark())){
			cb.andLike("remark", merchantBankcardCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getTemp1())){
			cb.andEQ("temp1", merchantBankcardCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getTemp2())){
			cb.andEQ("temp2", merchantBankcardCondition.getTemp2());
		}
		if(Strings.isNotEmpty(merchantBankcardCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantBankcardCondition.getNodeSeq());
		}
		cb.andEQ("recordStatus", ScanCodeConstants.RECORD_STATUS_YES);
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(merchantBankcardCondition.getOrderBy())){
			if(merchantBankcardCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = merchantBankcardCondition.getOrderBy().split(",");
				String[] orders = merchantBankcardCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(merchantBankcardCondition.getOrderBy(), Order.valueOf(merchantBankcardCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantBankcardCondition.getFirst()), Long.valueOf(merchantBankcardCondition.getLast()));
		return merchantBankcardDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param merchantBankcard 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
	@Override
	public List<MerchantBankcard> findAll(MerchantBankcardCondition merchantBankcardCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantBankcard.class);
		if(!Strings.isEmpty(merchantBankcardCondition.getId())){
			cb.andEQ("id", merchantBankcardCondition.getId());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantBankcardCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getMerchantName())){
			cb.andLike("merchantName", merchantBankcardCondition.getMerchantName());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getIdCard())){
			cb.andEQ("idCard", merchantBankcardCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getName())){
			cb.andEQ("name", merchantBankcardCondition.getName());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getBankCode())){
			cb.andEQ("bankCode", merchantBankcardCondition.getBankCode());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getBankName())){
			cb.andEQ("bankName", merchantBankcardCondition.getBankName());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getBankCard())){
			cb.andEQ("bankCard", merchantBankcardCondition.getBankCard());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getMobile())){
			cb.andEQ("mobile", merchantBankcardCondition.getMobile());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getIsRealAccount())){
			cb.andEQ("isRealAccount", merchantBankcardCondition.getIsRealAccount());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getAccountType())){
			cb.andEQ("accountType", merchantBankcardCondition.getAccountType());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getStatus())){
			cb.andEQ("status", merchantBankcardCondition.getStatus());
		}
		if(null != merchantBankcardCondition.getCreateTime()){
			cb.andEQ("createTime", merchantBankcardCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantBankcardCondition.getOperator())){
			cb.andEQ("operator", merchantBankcardCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantBankcardCondition.getRemark())){
			cb.andLike("remark", merchantBankcardCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getTemp1())){
			cb.andEQ("temp1", merchantBankcardCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getTemp2())){
			cb.andEQ("temp2", merchantBankcardCondition.getTemp2());
		}
		if(Strings.isNotEmpty(merchantBankcardCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantBankcardCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantBankcardDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 商户银行账户id
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
	@Override
	public MerchantBankcard findById(String id){
		return merchantBankcardDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param merchantBankcardCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
	@Override
	public long insert(MerchantBankcardCondition merchantBankcardCondition){
		MerchantBankcard merchantBankcard = new MerchantBankcard();
		try {
			MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantBankcardCondition.getMerchantNo()));
			merchantBankcardCondition.setChannelNo(merchantInfo.getChannelNo());
			merchantBankcardCondition.setAgentNo(merchantInfo.getAgentNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		BeanUtils.copyProperties(merchantBankcardCondition, merchantBankcard);
		return merchantBankcardDAO.insert(merchantBankcard);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
	@Override
	public long deleteById(String id){
		return merchantBankcardDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return merchantBankcardDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return merchantBankcardDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
	@Override
	public long update(MerchantBankcardCondition merchantBankcardCondition){
		MerchantBankcard merchantBankcard = new MerchantBankcard();
		BeanUtils.copyProperties(merchantBankcardCondition, merchantBankcard);
		return merchantBankcardDAO.update(merchantBankcard);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
	@Override
	public long updateByCriteria(MerchantBankcardCondition merchantBankcardCondition,Criteria criteria){
		MerchantBankcard merchantBankcard = new MerchantBankcard();
		BeanUtils.copyProperties(merchantBankcardCondition, merchantBankcard);
		return merchantBankcardDAO.updateByCriteria(merchantBankcard,criteria);
	}
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 13:50:11
	 */
	@Override
	public long updateStatus(String id,String status){
		return merchantBankcardDAO.updateStatus(id,status);
	}	
	
	
	@Override
	public MerchantBankcard findByMerchantNo(String merchantNo) {
		if(Strings.isEmpty(merchantNo)){
			throw new RuntimeException("商户编号不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(MerchantBankcard.class);
		cb.andEQ("merchantNo", merchantNo);
		Criteria buildCriteria = cb.buildCriteria();
		return merchantBankcardDAO.findOneByCriteria(buildCriteria);
	}
	
	@Override
	public long updateByMerchantNo(MerchantBankcardCondition condition) {
		//"bankName":bankName,"bankCard":bankCard,"mobile":mobile,"bankCode":clearBankChannelNo,
		MerchantBankcard existBean = findByMerchantNo(condition.getMerchantNo());//不存在则是新增
		if(existBean!=null){//修改银行卡
			logger.info("个人资料中修改银行卡........");
			condition.setId(existBean.getId());
			condition.setIdCard(existBean.getIdCard());
			condition.setName(existBean.getName());
			condition.setAccountType(existBean.getAccountType());
		}
		logger.info("个人资料中修改银行卡........"+condition.toString());
		try {
			save(condition);//新增或者修改，根据id来取
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("个人资料中修改银行卡失败....",e);
			e.printStackTrace();
		}
		return -1;
	}
	
	@Override
	public long updateBalance(String merchantNo, BigDecimal balance) {
		// TODO Auto-generated method stub
		return merchantBankcardDAO.updateBalance(merchantNo,balance);
	}
	
	@Override
	public long applyStoreStep3(MerchantBankcardCondition condition) {
		MerchantBankcard MerchantBankcard = new MerchantBankcard();
		MerchantBankcard existBean = findByMerchantNo(condition.getMerchantNo());
		//MerchantInfo info = merchantInfoService.findByMerchantNo(condition.getMerchantNo());
		condition.setStatus("3");
		condition.setIsRealAccount("Y");
//		if(null!=info){
//			condition.setIdCard(info.getIdCard());
//			condition.setName(info.getName());
//		}
		long result = 0L;
		MerchantInfoCondition minfo = new MerchantInfoCondition();
		minfo.setMerchantNo(condition.getMerchantNo());
		minfo.setMobile(condition.getMobile());
		minfo.setStatus(MerchantStatus.MERCHANT_STATUS_0.getCode());
		minfo.setCheck(false);
		result = merchantInfoService.updateByMerchantNo(minfo);
		checkResult(result);
		if(existBean==null){//不存在插入
			BeanUtils.copyProperties(condition, MerchantBankcard);
			MerchantBankcard.setId(Strings.getUUID());
			MerchantBankcard.setCreateTime(new Date());
			MerchantBankcard.setRecordStatus(ScanCodeConstants.Y);
			result = merchantBankcardDAO.insert(MerchantBankcard);
			checkResult(result);
		}else{
			if(!isUpdate(condition)){//没修改直接返回
				return 1;
			}
			BeanUtils.copyProperties(condition, MerchantBankcard);
			MerchantBankcard.setId(existBean.getId());
			MerchantBankcard.setMerchantNo(condition.getMerchantNo());
			MerchantBankcard.setUpdateTime(new Date());
			result = merchantBankcardDAO.update(MerchantBankcard);
			checkResult(result);
		}
		return result;
	}
	private void checkResult(long result){
		logger.info("0-------------update result is "+ result);
		if(result<0){
			throw new RuntimeException("失败了..");
		}
	}

	private boolean isUpdate(MerchantBankcardCondition condition) {
		MerchantBankcard card = this.findByMerchantNo(condition.getMerchantNo());
		boolean isUpdate = false;
		if(!condition.getBankName().equals(card.getBankName())){
			isUpdate = true;
		}
		if(!condition.getBankCard().equals(card.getBankCard())){
			isUpdate = true;
		}
		if(!condition.getMobile().equals(card.getMobile())){
			isUpdate = true;
		}
		if(!condition.getBankCode().equals(card.getBankCode())){
			isUpdate = true;
		}
		if(!condition.getAccountType().equals(card.getAccountType())){
			isUpdate = true;
		}
		if(condition.getAccountType().equals("0")&&!condition.getIdCard().equals(card.getIdCard())){//个人检查idCard，企业的检查企业名称
			isUpdate = true;
		}
		if(condition.getAccountType().equals("1")&&!condition.getTemp1().equals(card.getTemp1())){//个人检查idCard，企业的检查企业名称
			isUpdate = true;
		}
		if(!condition.getName().equals(card.getName())){
			isUpdate = true;
		}
		return isUpdate;
	}
	
	/** 保存银行卡 */
	@Override
	@Transactional
	public Map<String,String> save(MerchantBankcardCondition merchantBankcardCondition) throws Exception{
		if(Strings.isEmpty(merchantBankcardCondition.getId())){
			MerchantBankcardCondition bankcard = new MerchantBankcardCondition();
			bankcard.setMerchantNo(merchantBankcardCondition.getMerchantNo());
			List<MerchantBankcard> merchantList = this.findAll(bankcard);
			if(null!=merchantList && merchantList.size() == 1){
				throw new Exception("商户银行卡信息已存在，不可以重复设置");
			}
			//新增
			merchantBankcardCondition.setCreateTime(new Date());
			merchantBankcardCondition.setId(Strings.getUUID());
			merchantBankcardCondition.setStatus("3");
			merchantBankcardCondition.setRecordStatus(ScanCodeConstants.Y);
			this.insert(merchantBankcardCondition);
		}else{
			MerchantInfo merchantInfo = merchantInfoService.findByMerchantNo(merchantBankcardCondition.getMerchantNo());
			merchantBankcardCondition.setUpdateTime(new Date());
			
			
			MerchantBankcard entity = this.findById(merchantBankcardCondition.getId());
			//有变更才写入数据变更日志
			if(entity != null && 
					!(entity.getBankCode().equals(merchantBankcardCondition.getBankCode())
							&&entity.getBankName().equals(merchantBankcardCondition.getBankName())
							&&entity.getBankCard().equals(merchantBankcardCondition.getBankCard())
							&&entity.getIdCard().equals(merchantBankcardCondition.getIdCard())
							&&entity.getName().equals(merchantBankcardCondition.getName())
							&&entity.getMobile().equals(merchantBankcardCondition.getMobile())
							&&entity.getAccountType().equals(merchantBankcardCondition.getAccountType()))){	
					merchantBankcardCondition.setIsRealAccount(entity.getIsRealAccount());
					ChangeLogCondition changeLogCondition = new ChangeLogCondition();
					String tradeNo = entity.getId();//MerchantBankcard表Id关联
					String batchNo = BatchNoUtil.getBatchNo();
					changeLogCondition.setTradeNo(tradeNo);
					changeLogCondition.setBatchNo(batchNo);
					changeLogCondition.setTransCode(String.valueOf(TransCodeEnum.MERCHANT_BANKCARD_CODE.getValue()));
					changeLogCondition.setTransName(TransCodeEnum.MERCHANT_BANKCARD_CODE.getDesc());
					changeLogCondition.setOwnersNo(merchantBankcardCondition.getMerchantNo());
					changeLogCondition.setBefore(JSONSerializer.toJSON(entity).toString());
//					changeLogCondition.setAfter(JSONSerializer.toJSON(merchantBankcardCondition).toString());
					changeLogCondition.setStatus("0");
					changeLogCondition.setOperator(merchantBankcardCondition.getOperator());
					changeLogCondition.setCreateTime(new Date());
					changeLogService.insert(changeLogCondition);
					MerchantBankcardChangeCondition merchantBankcardChangeCondition = new  MerchantBankcardChangeCondition();
					BeanUtils.copyProperties(merchantBankcardCondition, merchantBankcardChangeCondition);
					merchantBankcardChangeCondition.setCreateTime(new Date());
					if("3".equals(merchantInfo.getStatus())){
//						merchantBankcardCondition.setStatus(MerchantBankcardStatus.MERCHANTBANKCARD_STATUS_3.getCode());
//						this.updateStatus(merchantBankcardCondition.getId(),merchantBankcardCondition.getStatus());	
						if("0".equals(merchantBankcardChangeCondition.getAccountType())){//个人账户
							//商户要素验证
							BankCardAuthVo auth = new BankCardAuthVo();
							auth.setRealName(merchantBankcardChangeCondition.getName());
							auth.setBankCard(merchantBankcardChangeCondition.getBankCard());
							auth.setIdCard(merchantBankcardChangeCondition.getIdCard());
							auth.setMobile(merchantBankcardChangeCondition.getMobile());
							Map<String,String> res = merchantBusinessService.bankCardAuth(auth,"3");
							String respCode = res.get("respCode");
							String respDesc = res.get("respDesc");
							//商户实名认证记录
							this.saveMerchantAutoInfo(auth, merchantInfo, respCode, respDesc);							
							if(ScanCodeConstants.INTERFACE_SUCCESS_STATUS.equals(respCode)){
								MerchantUpdateCardVo merchantUpdateCardVo =new MerchantUpdateCardVo();
								merchantUpdateCardVo.setMerchantNo(merchantInfo.getPlatformMerchantNo());//传转接平台商户编号
								merchantUpdateCardVo.setMobile(merchantBankcardCondition.getMobile());
								merchantUpdateCardVo.setNewBankCode(merchantBankcardCondition.getBankCode());
								merchantUpdateCardVo.setNewBankCard(merchantBankcardCondition.getBankCard());
								merchantUpdateCardVo.setNewBankName(merchantBankcardCondition.getBankName());
								merchantUpdateCardVo.setAccountType(merchantBankcardCondition.getAccountType());
								
								ParamsInfo info = payCallBackOperatorService.findParamsKey(ScanCodeConstants.SYSTEM, ParamsType.PARAMS_CALLBACK_INFO.getCode());
								JSONObject callbackJson = JSONObject.fromObject(info.getParamsValue());
								merchantUpdateCardVo.setReturnURL(callbackJson.getString("bankChangeUrl"));
								
								//merchantUpdateCardVo.setReturnURL(callbackConfig.getBankChangeUrl());//回调地址
								Map<String, String> returnMap = merchantBusinessService.merchantUpdateCard(merchantUpdateCardVo);
								if(ScanCodeConstants.INTERFACE_SUCCESS_STATUS.equals(returnMap.get("respCode"))){
									MerchantBankcard merchantBankcard = this.findByMerchantNo(merchantBankcardCondition.getMerchantNo());
									merchantBankcardCondition.setId(merchantBankcard.getId());
									merchantBankcardCondition.setStatus(MerchantBankcardStatus.MERCHANTBANKCARD_STATUS_5.getCode());//渠道审批中
									merchantBankcardCondition.setUpdateTime(new Date());
									this.updateStatus(merchantBankcardCondition.getId(),merchantBankcardCondition.getStatus());
									
									merchantBankcardChangeCondition.setStatus(MerchantBankcardStatus.MERCHANTBANKCARD_STATUS_5.getCode());//渠道审批中
									merchantBankcardChangeService.insert(merchantBankcardChangeCondition);
								}else{
									merchantBankcardChangeCondition.setStatus(MerchantBankcardStatus.MERCHANTBANKCARD_STATUS_4.getCode());//审核不通过
									merchantBankcardChangeCondition.setRemark(returnMap.get("respDesc"));
									merchantBankcardChangeService.insert(merchantBankcardChangeCondition);
									return returnMap;
								}								
								
							}else{	
								merchantBankcardChangeCondition.setStatus(MerchantBankcardStatus.MERCHANTBANKCARD_STATUS_4.getCode());//审核不通过
								merchantBankcardChangeCondition.setRemark(respDesc);
								merchantBankcardChangeService.insert(merchantBankcardChangeCondition);
								return res;
							}
						}else{
							merchantBankcardChangeCondition.setStatus(MerchantBankcardStatus.MERCHANTBANKCARD_STATUS_3.getCode());//审核中
							merchantBankcardChangeService.insert(merchantBankcardChangeCondition);
						}
					}else{							
						merchantBankcardChangeCondition.setStatus(MerchantBankcardStatus.MERCHANTBANKCARD_STATUS_1.getCode());//有效
						merchantBankcardChangeCondition.setRemark(MerchantBankcardStatus.MERCHANTBANKCARD_STATUS_1.getDesc());
						merchantBankcardChangeService.insert(merchantBankcardChangeCondition);
						//不是审核通过的情况下，允许修改商户的状态为审核中
						merchantInfoService.updateStatus(merchantInfo.getId(), "0");
						this.update(merchantBankcardCondition);
					}	
			}			
		}
		return null;
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
	@Override
	public List<MerchantBankcard> findByPhone(String mobile) {
		return merchantBankcardDAO.findByPhone(mobile);
	}
}

