package com.hfepay.scancode.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.scancode.commons.BaseErrorMsg;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.ChannelAdminCondition;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.service.channel.ChannelAdminService;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.order.ProfitService;
import com.hfepay.scancode.service.utils.PasswordHelper;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/user")
public class ChannelAdminResgisterController extends BaseController{
	public static final Logger logger = LoggerFactory.getLogger(ChannelAdminResgisterController.class);
	
	@Autowired
	private ChannelAdminService channelAdminService;
	@Autowired
	private ProfitService profitService;
	/**
	 * 发送动态验证码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/send/validate/code")
	@ResponseBody
	public JSON getDynamicVerificationVode(ChannelAdminCondition user) throws Exception{
		logger.info("*****进行发送验证码*****");
		Map<Object, Object> map = null;
		//发送验证码
//		boolean sendFlag = channelAdminService.getDynamicVerificationVode(user.getPhone(),user.getMerchantNo());
		boolean sendFlag = true;
		if (sendFlag) {
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS);
			return JSONSerializer.toJSON(map);
		}else {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"发送失败");
			logger.info("*****发送验证码fail*****");
			return JSONSerializer.toJSON(map);
		}
	}
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	@RequestMapping("/do/register")
	@ResponseBody
	public JSON doRegister(ChannelAdminCondition user,HttpServletRequest request)throws Exception{
		logger.info("*****进入注册,参数详情:userName:"+user.getUserName()+",userPhone:"+user.getPhone()+"*****");
		//设置渠道
		String number = ScanCodeConstants.DEFAULT_CHANNELNO;
		
		user.setChannelCode(number);
		Map<Object, Object> map = null;
		//验证二次密码
		if(!user.getPassword().equals(user.getAgainPwd())){
			logger.info("二次输入密码不一致");
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"二次输入密码不一致");
			logger.info("*****注册fail,二次输入密码不一致*****");
			return JSONSerializer.toJSON(map);
		}
		//根据用户名和渠道编号查询是否已被注册
		ChannelAdmin userRegister = channelAdminService.findByUsername(user.getUserName(),number);
		if (null != userRegister) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"该账号已存在");
			logger.info("*****注册fail,该账号已存在*****");
			return JSONSerializer.toJSON(map);
		}
		
		//校验动态验证码
		boolean flag = channelAdminService.validateRedisValidateCode(user.getPhone(), user.getValidateCode());
		if(!flag){
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"验证码不正确");
			logger.info("*****注册fail,验证码不正确*****");
			return JSONSerializer.toJSON(map);
		}
		
		//保存
		channelAdminService.doRegister(user);
		map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS);
		return JSONSerializer.toJSON(map);
	}
	
	/**
	 * 商户注册验证用户名是否存在
	 * @param user
	 * @return
	 */
	@RequestMapping("/validate/user/name")
	@ResponseBody
	public JSON validateUserName(String userName)throws Exception{
		logger.info("*****进入验证用户名是否存在*****");
		//获取渠道编码
		String number = ScanCodeConstants.DEFAULT_CHANNELNO;
		
		Map<Object, Object> map = null;
		ChannelAdmin userByPhone =channelAdminService.findByPhone(userName, number);
		if(userByPhone != null){
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"false");
			logger.info("*****验证用户名是否存在,fail,已存在*****");
			return JSONSerializer.toJSON(map);
		}else{
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,"true");
			return JSONSerializer.toJSON(map);
		}
	}
	
	/**
	 * 验证手机是否存在
	 * @param phone
	 * @param type 类型 00 注册 11找回密码
	 * @return
	 */
	@RequestMapping("/validate/phone")
	@ResponseBody
	public JSON validatePhone(String phone,String type){
		logger.info("*****进入验证手机是否存在,参数详情:phone:"+phone+"*****");
		//获取渠道编码
		String number = ScanCodeConstants.DEFAULT_CHANNELNO;
		
		ChannelAdmin userRegister = channelAdminService.findByPhone(phone,number);
		Map<Object, Object> map = null;
		if(userRegister != null){
			if(Constants.VALIDATE_PHONE_TYPE_REGISTER.equals(type)){
				map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"false");
				logger.info("*****注册验证手机是否存在fail,手机已存在*****");
			}else if(Constants.VALIDATE_PHONE_TYPE_FORGET.equals(type)){
				map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,"true");
			}
		}
		else{
			if(Constants.VALIDATE_PHONE_TYPE_REGISTER.equals(type)){
				map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,"true");
				logger.info("*****注册验证手机是否存在fail,手机已存在*****");
			}else if(Constants.VALIDATE_PHONE_TYPE_FORGET.equals(type)){
				map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"false");
			}
		}
		return JSONSerializer.toJSON(map);
	}
	
	@RequestMapping("/do/login")
	@ResponseBody
	public BaseErrorMsg dologin(HttpServletRequest request,ChannelAdmin user) {
		if (Strings.isEmpty(user.getUserName())) {
			return new BaseErrorMsg("请输入用户名");
		}
		String username = user.getUserName();
		String password = user.getPassword();
		ChannelAdmin admin = channelAdminService.findByUsername(username,"QDXX20161026000001");//用户信息
		if(admin == null){
			return new BaseErrorMsg("用户名不存在");
		}
		String adpassword=admin.getPassword();
		String adsalt = admin.getSalt();
		String pwd = PasswordHelper.getEncryptPassword(username, password, adsalt);
		if(!pwd.equals(adpassword)){
			return new BaseErrorMsg("密码错误");
		}
		request.getSession().setAttribute(Constants.CURRENTUSER,admin);
		return new BaseErrorMsg("0","登录成功"); 
	}
	
	@RequestMapping("/reset/password")
	@ResponseBody
	public BaseErrorMsg resetPassword(ChannelAdminCondition channelAdminCondition){
		logger.info("*****进入重置密码,id:"+channelAdminCondition.getId()+"*****");
		if(!channelAdminCondition.getPassword().equals(channelAdminCondition.getAgainPwd())){
			logger.info("*****重置密码fail,两次输入密码不一致*****");
			return new BaseErrorMsg("两次输入密码不一致");
		}
		ChannelAdmin user = channelAdminService.findByPhone(channelAdminCondition.getPhone(), ScanCodeConstants.DEFAULT_CHANNELNO);
		if(null == user){
			logger.info("*****重置密码fail,用户不存在*****");
			return new BaseErrorMsg("用户不存在");
		}
		
		channelAdminCondition.setChannelCode(ScanCodeConstants.DEFAULT_CHANNELNO);
		channelAdminCondition.setId(user.getId());
		long result = channelAdminService.updatePassword(channelAdminCondition);
		
		if(result<0){
			return new BaseErrorMsg("重置密码失败");
		}
		return new BaseErrorMsg("0","修改成功");
	}
	
	
	@RequestMapping("/login")
	public String login(ModelMap model,HttpServletRequest request){
		//判断请求来源，如果是手机端，则出发关闭事件
		String browerType = request.getHeader("User-Agent").toLowerCase();
		if(browerType.toLowerCase().indexOf("micromessenger")!=-1){//微信进入session过期
			request.setAttribute("error", "登录超时,请重新进入.");
			return "scancode/wechat/scan_error";
		}
		return "scancode/wechat/login";
	}
	@RequestMapping("/redirectError")
	public String redirectError(ModelMap model,HttpServletRequest request){
		//判断请求来源，如果是手机端，则出发关闭事件
		request.setAttribute("error", "登录超时,请重新进入.");
		return "scancode/wechat/scan_error";
	}
	@RequestMapping("/register")
	public String register(ModelMap model,HttpServletRequest request){
		return "scancode/wechat/register";
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public BaseErrorMsg logout(ModelMap model,HttpServletRequest request){
		request.getSession().removeAttribute(Constants.CURRENTUSER);
		logger.info("-------------直销成功");
		return new BaseErrorMsg("0","注销成功");
	}
	
	
	@RequestMapping("/rediskeys")
	@ResponseBody
	public BaseErrorMsg getKeyValue(ModelMap model,HttpServletRequest request){
		profitService.showRedisRateDiff();
		return new BaseErrorMsg("0","成功");
	}
	
	@RequestMapping("/reDoProfit")
	@ResponseBody
	public BaseErrorMsg redoProfit(ModelMap model,HttpServletRequest request){
		//触发定时任务,此处也是作为测试用
		profitService.doSaveProfit();
		return new BaseErrorMsg("0","成功");
	}
}
