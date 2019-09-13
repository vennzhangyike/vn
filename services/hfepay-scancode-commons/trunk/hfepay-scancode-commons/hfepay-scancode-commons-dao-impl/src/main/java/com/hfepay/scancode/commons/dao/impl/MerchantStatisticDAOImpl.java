package com.hfepay.scancode.commons.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hfepay.commons.dao.impl.MybatisDaoSupport;
import com.hfepay.commons.dao.impl.MybatisDaoTemplate;
import com.hfepay.scancode.commons.condition.MerchantOrderStatisticCondition;
import com.hfepay.scancode.commons.dao.MerchantStatisticDAO;
import com.hfepay.scancode.commons.dto.MerchantOrderStatisticDTO;

@Repository(value="merchantStatisticDAO")
public class MerchantStatisticDAOImpl  extends MybatisDaoSupport implements MerchantStatisticDAO,MybatisDaoTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4471051457056794706L;
	
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
	    super.setSqlSessionFactory(sqlSessionFactory);
	}

	/**
	 * 支付订单交易笔数统计
	 */
	@Override
	public List<MerchantOrderStatisticDTO> orderPayStatistic(MerchantOrderStatisticCondition condition) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("MerchantStatistic.orderPayStatistic",condition);
	}
	
	/**
	 * 退款订单交易笔数统计
	 */
	/*@Override
	public List<MerchantOrderStatisticDTO> orderRefundStatistic(MerchantOrderStatisticCondition condition) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("MerchantStatistic.orderRefundStatistic",condition);
	}*/

	/**
	 * 支付订单交易金额统计
	 */
	@Override
	public List<MerchantOrderStatisticDTO> orderPayAmtStatistic(MerchantOrderStatisticCondition condition) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("MerchantStatistic.orderPayAmtStatistic",condition);
	}

	/**
	 * 退款订单交易金额统计
	 */
	/*@Override
	public List<MerchantOrderStatisticDTO> orderRefundAmtStatistic(MerchantOrderStatisticCondition condition) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("MerchantStatistic.orderRefundAmtStatistic",condition);
	}*/

	
}
