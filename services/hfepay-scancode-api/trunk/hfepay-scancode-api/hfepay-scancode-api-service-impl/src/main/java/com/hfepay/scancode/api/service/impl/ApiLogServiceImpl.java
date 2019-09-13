/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.api.condition.ApiLogCondition;
import com.hfepay.scancode.api.dao.ApiLogDAO;
import com.hfepay.scancode.api.entity.ApiLog;
import com.hfepay.scancode.api.service.ApiLogService;
import com.hfepay.scancode.api.service.config.HfepayConfig;

@Service("apiLogService")
public class ApiLogServiceImpl implements ApiLogService {
	public static final Logger log = LoggerFactory.getLogger(ApiLogServiceImpl.class);

	@Autowired
    private ApiLogDAO apiLogDAO;
    
    /**
	 * 列表(分页)
	 * @param apiLogCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
    @Override
	public PagingResult<ApiLog> findPagingResult(ApiLogCondition apiLogCondition){
		CriteriaBuilder cb = Cnd.builder(ApiLog.class);
		if(!Strings.isEmpty(apiLogCondition.getId())){
			cb.andEQ("id", apiLogCondition.getId());
		}
		if(!Strings.isEmpty(apiLogCondition.getPayCode())){
			cb.andLike("payCode", apiLogCondition.getPayCode());
		}
		if(!Strings.isEmpty(apiLogCondition.getTradeNo())){
			cb.andLike("tradeNo", apiLogCondition.getTradeNo());
		}
		if(!Strings.isEmpty(apiLogCondition.getChannelNo())){
			cb.andLike("channelNo", apiLogCondition.getChannelNo());
		}
		if(!Strings.isEmpty(apiLogCondition.getAgentNo())){
			cb.andLike("agentNo", apiLogCondition.getAgentNo());
		}
		if(!Strings.isEmpty(apiLogCondition.getMerchantNo())){
			cb.andLike("merchantNo", apiLogCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(apiLogCondition.getRequestParams())){
			cb.andLike("requestParams", apiLogCondition.getRequestParams());
		}
		if(!Strings.isEmpty(apiLogCondition.getResponseParams())){
			cb.andEQ("responseParams", apiLogCondition.getResponseParams());
		}
		if(!Strings.isEmpty(apiLogCondition.getNotifyParams())){
			cb.andEQ("notifyParams", apiLogCondition.getNotifyParams());
		}
		if(null != apiLogCondition.getCreateTime()){
			cb.andEQ("createTime", apiLogCondition.getCreateTime());
		}

		if(null != apiLogCondition.getNotifyTime()){
			cb.andEQ("notifyTime", apiLogCondition.getNotifyTime());
		}

		if(!Strings.isEmpty(apiLogCondition.getRemark())){
			cb.andLike("remark", apiLogCondition.getRemark());
		}
		if(!Strings.isEmpty(apiLogCondition.getTemp1())){
			cb.andEQ("temp1", apiLogCondition.getTemp1());
		}
		if(!Strings.isEmpty(apiLogCondition.getTemp2())){
			cb.andEQ("temp2", apiLogCondition.getTemp2());
		}
		if(!Strings.isEmpty(apiLogCondition.getQueryStartDate())){
			cb.andGE("createTime", getDate(apiLogCondition.getQueryStartDate(),0));
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(apiLogCondition.getOrderBy())){
			if(apiLogCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = apiLogCondition.getOrderBy().split(",");
				String[] orders = apiLogCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(apiLogCondition.getOrderBy(), Order.valueOf(apiLogCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(apiLogCondition.getFirst()), Long.valueOf(apiLogCondition.getLast()));
		return apiLogDAO.findPagingResult(buildCriteria);
	}
    
    private String getDate(String date,int step){
		Date d = null;
		Calendar ca = Calendar.getInstance();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			d = format.parse(date);
			ca.setTime(d);
			if(step!=0){
				 ca.add(Calendar.DAY_OF_MONTH, step);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ca.getTime());
    }
	
	
	/**
	 * 列表
	 * @param apiLog 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	@Override
	public List<ApiLog> findAll(ApiLogCondition apiLogCondition){
		CriteriaBuilder cb = Cnd.builder(ApiLog.class);
		if(!Strings.isEmpty(apiLogCondition.getId())){
			cb.andEQ("id", apiLogCondition.getId());
		}
		if(!Strings.isEmpty(apiLogCondition.getPayCode())){
			cb.andEQ("payCode", apiLogCondition.getPayCode());
		}
		if(!Strings.isEmpty(apiLogCondition.getTradeNo())){
			cb.andEQ("tradeNo", apiLogCondition.getTradeNo());
		}
		if(!Strings.isEmpty(apiLogCondition.getChannelNo())){
			cb.andEQ("channelNo", apiLogCondition.getChannelNo());
		}
		if(!Strings.isEmpty(apiLogCondition.getAgentNo())){
			cb.andEQ("agentNo", apiLogCondition.getAgentNo());
		}
		if(!Strings.isEmpty(apiLogCondition.getMerchantNo())){
			cb.andEQ("merchantNo", apiLogCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(apiLogCondition.getRequestParams())){
			cb.andEQ("requestParams", apiLogCondition.getRequestParams());
		}
		if(!Strings.isEmpty(apiLogCondition.getResponseParams())){
			cb.andEQ("responseParams", apiLogCondition.getResponseParams());
		}
		if(!Strings.isEmpty(apiLogCondition.getNotifyParams())){
			cb.andEQ("notifyParams", apiLogCondition.getNotifyParams());
		}
		if(null != apiLogCondition.getCreateTime()){
			cb.andEQ("createTime", apiLogCondition.getCreateTime());
		}

		if(null != apiLogCondition.getNotifyTime()){
			cb.andEQ("notifyTime", apiLogCondition.getNotifyTime());
		}

		if(!Strings.isEmpty(apiLogCondition.getRemark())){
			cb.andLike("remark", apiLogCondition.getRemark());
		}
		if(!Strings.isEmpty(apiLogCondition.getTemp1())){
			cb.andEQ("temp1", apiLogCondition.getTemp1());
		}
		if(!Strings.isEmpty(apiLogCondition.getTemp2())){
			cb.andEQ("temp2", apiLogCondition.getTemp2());
		}
		
		Criteria buildCriteria = cb.buildCriteria();
		return apiLogDAO.findByCriteria(buildCriteria);
	}
	
	
	
	/**
	 * 列表
	 * @param apiLog 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	@Override
	public List<ApiLog> findByTradeNo(ApiLogCondition apiLogCondition){
		CriteriaBuilder cb = Cnd.builder(ApiLog.class);
		if(!Strings.isEmpty(apiLogCondition.getId())){
			cb.andEQ("id", apiLogCondition.getId());
		}
		cb.andNotEQ("payCode", HfepayConfig.trade_query);
		cb.andNotEQ("payCode", HfepayConfig.draw_query);
		
		if(!Strings.isEmpty(apiLogCondition.getTradeNo())){
			cb.andEQ("tradeNo", apiLogCondition.getTradeNo());
		}
		if(!Strings.isEmpty(apiLogCondition.getChannelNo())){
			cb.andEQ("channelNo", apiLogCondition.getChannelNo());
		}
		if(!Strings.isEmpty(apiLogCondition.getAgentNo())){
			cb.andEQ("agentNo", apiLogCondition.getAgentNo());
		}
		if(!Strings.isEmpty(apiLogCondition.getMerchantNo())){
			cb.andEQ("merchantNo", apiLogCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(apiLogCondition.getRequestParams())){
			cb.andEQ("requestParams", apiLogCondition.getRequestParams());
		}
		if(!Strings.isEmpty(apiLogCondition.getResponseParams())){
			cb.andEQ("responseParams", apiLogCondition.getResponseParams());
		}
		if(!Strings.isEmpty(apiLogCondition.getNotifyParams())){
			cb.andEQ("notifyParams", apiLogCondition.getNotifyParams());
		}
		if(null != apiLogCondition.getCreateTime()){
			cb.andEQ("createTime", apiLogCondition.getCreateTime());
		}

		if(null != apiLogCondition.getNotifyTime()){
			cb.andEQ("notifyTime", apiLogCondition.getNotifyTime());
		}

		if(!Strings.isEmpty(apiLogCondition.getRemark())){
			cb.andLike("remark", apiLogCondition.getRemark());
		}
		if(!Strings.isEmpty(apiLogCondition.getTemp1())){
			cb.andEQ("temp1", apiLogCondition.getTemp1());
		}
		if(!Strings.isEmpty(apiLogCondition.getTemp2())){
			cb.andEQ("temp2", apiLogCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return apiLogDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	@Override
	public ApiLog findById(String id){
		return apiLogDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param apiLogCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	@Override
	public long insert(ApiLogCondition apiLogCondition){
		ApiLog apiLog = new ApiLog();
		BeanUtils.copyProperties(apiLogCondition, apiLog);
		return apiLogDAO.insert(apiLog);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	@Override
	public long deleteById(String id){
		return apiLogDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return apiLogDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return apiLogDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	@Override
	public long update(ApiLogCondition apiLogCondition){
		ApiLog apiLog = new ApiLog();
		BeanUtils.copyProperties(apiLogCondition, apiLog);
		return apiLogDAO.update(apiLog);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	@Override
	public long updateByCriteria(ApiLogCondition apiLogCondition,Criteria criteria){
		ApiLog apiLog = new ApiLog();
		BeanUtils.copyProperties(apiLogCondition, apiLog);
		return apiLogDAO.updateByCriteria(apiLog,criteria);
	}

	@Override
	public long updateByTradeNo(ApiLogCondition apiLogCondition) {
		ApiLog apiLog = new ApiLog();
		BeanUtils.copyProperties(apiLogCondition, apiLog);
		return apiLogDAO.updateByTradeNo(apiLog);
	}

	@Override
	public ApiLog findByTradeNo(String tradeNo) {
		log.info("########findByTradeNo tradeNo:"+tradeNo+"#######");
		ApiLogCondition apiLogCondition = new ApiLogCondition();
		apiLogCondition.setTradeNo(tradeNo);
		List<ApiLog> list = findByTradeNo(apiLogCondition);
		if (null != list && list.size() > 0) {
			log.info("########findByTradeNo list:"+list.size()+"#######");
			return list.get(0);
		}
		return null;
	}
}

