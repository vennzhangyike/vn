package com.hfepay.scancode.commons.dao.channel;

import java.util.List;
import java.util.Map;

import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.ChannelAdmin;


    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
public interface ChannelAdminDAO extends EntityDAO<ChannelAdmin, String> {
    	/**
    	 * 状态更新
    	 */
    	long updateStatus(String id,Integer status);
    	
    	/**
    	 * 查询
    	 */
    	ChannelAdmin findByMerchantNoByIdentity(String merchantNo,String identityFlag);
    	
    	/**
    	 * 查询
    	 */
    	ChannelAdmin findByMerchantNoByIdentityGetOpenId(String merchantNo,String identityFlag);

		List<ChannelAdmin> findUnregsit(int start,int end);

		int countUnregsit();
		
		/**
		 * 获取商户待完善账户
		 * @Title: findUnperfectByChannelNo
		 * @author: Ricky
		 * @param map
		 * @return: List<ChannelAdmin>
		 */
		public List<ChannelAdmin> findMerchantInfoUnperfect(Map<String, Object> map) ;

		/**
		 * 查询可以消息推送的商户
		 * @param merchantNo
		 * @return
		 */
		ChannelAdmin findPushMsgAdmin(String merchantNo,boolean isMerchant);

		ChannelAdmin findByIdentityNo(String identityNo);
}