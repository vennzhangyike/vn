package com.hfepay.scancode.service.channel;

import java.util.List;
import java.util.Map;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ChannelAdminCondition;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.MerchantInfo;

public interface ChannelAdminService {
	ChannelAdmin findByUsername(String username,String channelCode);
	
	ChannelAdmin findByUsernameAndAgentNo(String username,String agentNo);
	
	/**
	 * 列表(分页)
	 * @param channelAdminCondition 
	 * @param roleLevel  角色类型
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	public PagingResult<ChannelAdmin> findPagingResult(ChannelAdminCondition channelAdminCondition,String roleLevel);
	
	/**
	 * 列表
	 * @param channelAdminCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	public List<ChannelAdmin> findAll(ChannelAdminCondition channelAdminCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	ChannelAdmin findById(String id);
	
	/**
	 * 新增
	 * @param channelAdminCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	long insert(ChannelAdminCondition channelAdminCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	long update(ChannelAdminCondition channelAdminCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	long updateByCriteria(ChannelAdminCondition channelAdminCondition,Criteria criteria);
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	long updateStatus(String id,Integer status);
	
	
	/**
	 * 新增或者更新账号信息，并修改角色关系
	 *
	 * @author panq
	 * @param number 
	 * @date CreateDate : 2016-06-27 15:35:32
	 */
	long updateFix(ChannelAdminCondition channelAdminCondition, String roleId, String number);

	/**
	 * 发送验证码
	 * @Title: getDynamicVerificationVode
	 * @Description: TODO
	 * @author: husain
	 * @param phone
	 * @return
	 * @return: boolean
	 */
//	boolean getDynamicVerificationVode(String phone,String merchantNo);

	/**
	 * 校验验证码
	 * @Title: validateRedisValidateCode
	 * @Description: TODO
	 * @author: husain
	 * @param phone
	 * @param validateCode
	 * @return
	 * @return: boolean
	 */
	boolean validateRedisValidateCode(String phone, String validateCode);

	/**
	 * 根据手机查询用户信息
	 * @Title: findByPhone
	 * @Description: TODO
	 * @author: husain
	 * @param userName
	 * @param number
	 * @return
	 * @return: ChannelAdmin
	 */
	ChannelAdmin findByPhone(String userName, String number);

	/**
	 * 注册
	 * @Title: doRegister
	 * @Description: TODO
	 * @author: husain
	 * @param user
	 * @return: void
	 */
	void doRegister(ChannelAdminCondition user);

	/**
	 * 
	 * @Title: updatePassword
	 * @Description: 重置密码
	 * @author: husain
	 * @param channelAdminCondition
	 * @return
	 * @return: long
	 */
	long updatePassword(ChannelAdminCondition channelAdminCondition);

	/**
	 * 根据条件查询商户
	 * @Title: findByCondition
	 * @Description: TODO
	 * @author: husain
	 * @param condition
	 * @return
	 * @return: ChannelAdmin
	 */
	ChannelAdmin findByCondition(ChannelAdminCondition condition);

	/**
	 * 通过二维码扫描注册商户
	 * @Title: createMerchantByQrCode
	 * @author: husain
	 * @param qrCode
	 * @param openId
	 * @return: long
	 */
	long createMerchantByQrCode(String qrCode, String openId);
	

	/**
	 * @Title: findByChannelNo
	 * @Description: 渠道编码查找
	 * @param channelNo
	 * @return: ChannelAdmin
	 */
	public ChannelAdmin findByChannelNo(String channelNo);
	
	
	/**
	 * @Title: findByMerchantNo
	 * @Description: 商户编码查找
	 * @param channelNo
	 * @return: ChannelAdmin
	 */
	public ChannelAdmin findByMerchantNo(String merchantNo);
	
	/**
	 * @Title: findByMerchantNo
	 * @Description: 商户编码查找
	 * @param channelNo
	 * @return: ChannelAdmin
	 */
	public ChannelAdmin findByMerchantNoByIdentity(String merchantNo);
	

	/**
	 * 列表(分页)
	 * @param channelAdminCondition 
	 * @param channelCode  角色类型
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	public PagingResult<ChannelAdmin> findPagingResultByAgentNo(ChannelAdminCondition channelAdminCondition);

	 /**
     * 根据渠道编号查询渠道信息
     * @param channelNo
     * @return
     * @throws Exception 
     */
    public ChannelBase findByChannelBaseChannelNo(String channelNo);
    
    /**
     * 根据代理商编号查询代理商信息
     * @param channelNo
     * @return
     * @throws Exception 
     */
    public AgentBase findByAgentBaseAgentNo(String agentNo);
    
    /**
     * 根据商户编号查询商户信息
     * @param merchantNo
     * @return
     * @throws Exception 
     */
    public MerchantInfo findByMerchantInfoMerchantNo(String merchantNo);
    
    /**
     * 获取微信用户的信息
     * @param merchantNo
     * @return
     * @throws Exception 
     */
    public void toSaveWechatUser(ChannelAdminCondition conditon);

    /*查出没有获取微信用户个人信息的用户*/
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
	 * 查询可以推送消息的商户，也就是open_id!=id
	 * @param merchantNo
	 * @return
	 */
	public ChannelAdmin findPushMsgAdmin(String merchantNo,boolean isMerchant);
}
