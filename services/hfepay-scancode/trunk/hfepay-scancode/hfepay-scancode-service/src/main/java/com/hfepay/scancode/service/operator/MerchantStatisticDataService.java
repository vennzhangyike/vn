package com.hfepay.scancode.service.operator;

import com.hfepay.scancode.commons.condition.MerchantOrderStatisticCondition;
import com.hfepay.scancode.commons.vo.MerchantOrderStatisticVo;

public interface MerchantStatisticDataService {
	/**
	 * 订单交易笔数统计
	 * @param condition
	 * @author tanbiao
	 * @return
	 */
	MerchantOrderStatisticVo orderStatistic(MerchantOrderStatisticCondition condition);

	/**
	 * 订单交易金额统计
	 * @param condition
	 * @author tanbiao
	 * @return
	 */
	MerchantOrderStatisticVo orderAmtStatistic(MerchantOrderStatisticCondition condition);
}
