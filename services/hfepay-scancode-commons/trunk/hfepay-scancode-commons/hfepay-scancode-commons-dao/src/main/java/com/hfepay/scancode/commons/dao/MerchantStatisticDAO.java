package com.hfepay.scancode.commons.dao;

import java.io.Serializable;
import java.util.List;

import com.hfepay.scancode.commons.condition.MerchantOrderStatisticCondition;
import com.hfepay.scancode.commons.dto.MerchantOrderStatisticDTO;


    @com.hfepay.commons.base.annotation.Generated("2016-04-22 11:16")
public interface MerchantStatisticDAO extends Serializable {

    /**
     * 订单笔数统计
     * @param condition
     * @return
     */
    List<MerchantOrderStatisticDTO> orderPayStatistic(MerchantOrderStatisticCondition condition);


    /*List<MerchantOrderStatisticDTO> orderRefundStatistic(MerchantOrderStatisticCondition condition);*/

    /**
     * 订单金额统计
     * @param condition
     * @return
     */
	List<MerchantOrderStatisticDTO> orderPayAmtStatistic(MerchantOrderStatisticCondition condition);


	/*List<MerchantOrderStatisticDTO> orderRefundAmtStatistic(MerchantOrderStatisticCondition condition);*/
    	
}