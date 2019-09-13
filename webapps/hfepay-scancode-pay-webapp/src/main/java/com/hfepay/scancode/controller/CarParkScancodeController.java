package com.hfepay.scancode.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.pay.service.ChannelSecretKeyService;
import com.hfepay.pay.service.PayCallBackOperatorService;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.ChannelSecretKeyCondition;
import com.hfepay.scancode.commons.condition.MerchantStoreCondition;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.entity.ChannelSecretKey;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantStore;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.commons.exception.ApiErrorCode;
import com.hfepay.scancode.commons.exception.ValidatException;

/**
 * @ClassName: CarParkScancodeController
 * @Description: 验签并封装参数到支付页面
 * @author: husain
 * @date: 2017年7月17日 下午3:02:13
 */
@Controller
@RequestMapping
public class CarParkScancodeController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ChannelSecretKeyService channelSecretKeyService;
	@Autowired
	private PayCallBackOperatorService payCallBackOperatorService;
	
	@RequestMapping(value = "/parkpayPage", method= {RequestMethod.GET})
    public String parkpayPage(HttpServletRequest request){
		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put("payCode", "ZFBZF");
		paramsMap.put("storeNo", "TESTMER20170320000016");
		paramsMap.put("storeName", "沃尔玛商场");
		paramsMap.put("channelNo", "QDXX20170220000001");
		paramsMap.put("qrcode","QRXX20170220173059072094");
		paramsMap.put("identityNo","TESTMER2017022050000035");
		
		paramsMap.put("price", "12.00");
		paramsMap.put("duration", "0天5小时2分");
		paramsMap.put("enterTime","2017-07-17 15:03");
		paramsMap.put("carNumber","粤B GT5200C");
		paramsMap.put("place","深圳市南山区高新南七道深圳软件园T2-B");
		request.setAttribute("paramsMap", paramsMap);
		return "scancode/parkPayment";	
	}
	
    /**
     * 针对停车场的扫码支付
     * @param request
     * @return
     */
	@RequestMapping(value = "/parkpay", method= {RequestMethod.GET})
    public String carParkScanCode(HttpServletRequest request){
    	String params = request.getQueryString();//参数列表形如a=b&c=d&e=f
    	logger.info("in carParkScanCode,params is "+params);
    	//客户端私钥签名，这里进行公钥验签，可需要根据渠道编号查找用户公钥进行验签
    	Map<String,String> paramMap = null;
    	try {
    		//对参数进行URLDecode操作
        	params=URLDecoder.decode(params,"utf-8");
        	paramMap = getUrlParams(params);
        	//1.参数校验
    		checkParams(paramMap);
	    	//2。验签
			boolean verfy = checkSign(paramMap.get("channelNo"), params,paramMap);
			if(!verfy){
				request.setAttribute("error", "验证签名失败，请检查参数重试");
				return "scancode/scan_error";
			}
		} catch (RuntimeException e) {
			request.setAttribute("error", e.getMessage());
			return "scancode/scan_error";
		} catch (UnsupportedEncodingException e) {
			request.setAttribute("error","不支持的编码方式");
			return "scancode/scan_error";
		}
    	//3.组装参数到支付页面
		String browerType = request.getHeader("User-Agent").toLowerCase();
		String payCode = "";
		//支付方式识别
		if (browerType.toLowerCase().indexOf("micromessenger")!=-1) {	//微信
			payCode = Constants.WXGZHZF;
		}else if(browerType.toLowerCase().indexOf("alipayclient")!=-1)  {//支付宝
			payCode = Constants.ZFBSMZF;
		}else if(browerType.toLowerCase().indexOf("qq")!=-1)  {//qq支付
			payCode = Constants.QQZF;
		}else {
			request.setAttribute("error", "暂不支持的扫码方式");
			return "scancode/scan_error";
		}
		paramMap.put("payCode", payCode);
		logger.info("=========payCode：" + payCode+"=========");
//		request.setAttribute("qrCode", paramMap.get("qrcode"));
//		request.setAttribute("storeName", paramMap.get("storeName"));
//		request.setAttribute("storeNo", paramMap.get("storeNo"));//门店编号
//		request.setAttribute("payCode", payCode);
//		request.setAttribute("merchantNo", paramMap.get("merchantNo"));
//		request.setAttribute("identityNo", paramMap.get("identityNo"));
//		request.setAttribute("price", paramMap.get("price"));
		request.setAttribute("paramsMap",paramMap);
		return "scancode/parkPayment";	
	}
    
    
    /**
     * 根据渠道编号查询秘钥信息
     * @param channelNo
     * @param method
     * @param ip
     * @return
     */
    private  ChannelSecretKey getChannelSecretKey(String channelNo,String ip) {
    	ChannelSecretKeyCondition condition = new ChannelSecretKeyCondition();
 		condition.setChannelNo(channelNo);
 		ChannelSecretKey channelSecretKey  = channelSecretKeyService.query(condition);
 		if (channelSecretKey == null || !"1".equals(channelSecretKey.getStatus())) {// 密钥不存在或不可用
 			throw new RuntimeException("合作方公钥未提供或不可用");
 		}
 		//如果没有配置IP地址，默认所有IP都能访问
 		if (Strings.isNotBlank(channelSecretKey.getBoundIp()) && channelSecretKey.getBoundIp().indexOf(ip) < 0) {// IP地址未绑定
 			throw new RuntimeException("IP地址未绑定");
 		}
 		return channelSecretKey;
 	}
    
    private boolean checkSign(String channelNo,String params,Map<String,String> paramsMap) throws RuntimeException{
        try {
        	ChannelSecretKey channelSecretKey = getChannelSecretKey(channelNo, null);
        	X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decodeBase64(channelSecretKey.getJoinUserPublicKey()));
            KeyFactory keyFactoryPublic = KeyFactory.getInstance("RSA");
            PublicKey publicKey  = keyFactoryPublic.generatePublic(pubX509);
            String plain = paramsMap.get("plain");
            String sign = paramsMap.get("sign");
            byte[] encryptDatah = Base64.decodeBase64(sign);
            boolean isValid = false;
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(publicKey);
            signature.update(plain.getBytes("utf-8"));
            isValid = signature.verify(encryptDatah);
            return isValid;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(String.format("验证数字签名时没有[%s]此类算法", "SHA1WithRSA"));
        } catch (InvalidKeyException e) {
            throw new RuntimeException("验证数字签名时公钥无效");
        } catch (SignatureException e) {
        	e.printStackTrace();
            throw new RuntimeException("验证数字签名时出现异常");
        } catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new RuntimeException("验证数字签名时出现异常");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("签名编码错误");
		}
    }
    
	private Map<String, String> getUrlParams(String param) {  
	    Map<String, String> map = new HashMap<>(0);  
	    if (StringUtils.isBlank(param)) {  
	        return map;  
	    }  
	    String[] params = param.split("&");  
	    for (int i = 0; i < params.length; i++) {  
	        String[] p = params[i].split("=");  
	        if (p.length == 2) {  
	            map.put(p[0], p[1]);  
	        }  
	    }  
	    int index = param.indexOf("&sign");
	    map.put("plain", param.substring(0, index));
	    return map;  
	}
	
	/**
	 * @param paramsMap
	 */
	private void checkParams(Map<String,String> paramsMap){
		//1. 是否齐整：商户编号，支付金额，签名，停车时长：入场时间：车牌号码：停车场
		//2：商户是否否存在，是否审核通过，是否存在门店
		String duration = paramsMap.get("duration");//停车时长
		String enterTime = paramsMap.get("enterTime");//入场时间
		String carNumber = paramsMap.get("carNumber");//车牌号
		String place = paramsMap.get("place");//停车场
		String price = paramsMap.get("price");//支付金额 两位小数
		String merchantNo = paramsMap.get("merchantNo");
		String sign = paramsMap.get("sign");
		String userOrderNo = paramsMap.get("userOrderNo");//用户上送的支付流水号，用作查询用
		
		//商户编号
		if(Strings.isEmpty(merchantNo)){
			throw new RuntimeException("商户编号不能为空");
		}else{
			MerchantInfo info = payCallBackOperatorService.findByMerchantNo(merchantNo);
			if(info==null||Strings.isEmpty(info.getChannelNo())){
				throw new RuntimeException("商户不存在");
			}
			if(!"3".equals(info.getStatus())){
				throw new RuntimeException("商户不是审核通过状态不能执行收款操作");
			}
			MerchantStoreCondition storeCondition = new MerchantStoreCondition();
			storeCondition.setMerchantNo(info.getMerchantNo());
			storeCondition.setStoreType("0");//总店
			storeCondition.setRecordStatus("Y");//有效的
			MerchantStore store = payCallBackOperatorService.findByCondition(storeCondition);
			if(null==store){
				throw new RuntimeException("商户门店为空不能支付");
			}
			paramsMap.put("storeNo", store.getStoreNo());
			paramsMap.put("storeNo", store.getStoreName());
			paramsMap.put("channelNo", info.getChannelNo());
			paramsMap.put("qrcode",info.getQrCode());
			paramsMap.put("identityNo",merchantNo);
		}
		
		//金额格式
		if(Strings.isEmpty(price)){
			throw new RuntimeException("支付金额不能为空");
		}
		else{
			String regx = "([1-9]\\d*(\\.\\d{0,1}[0-9])?)|(0\\.\\d{0,1}[1-9])";
			Pattern pattern = Pattern.compile(regx);
			Matcher matcher = pattern.matcher(price);
		    // 字符串是否与正则表达式相匹配
		    boolean rs = matcher.matches();
		    if(!rs){
		    	throw new RuntimeException("支付金额必须是最多保留两位小数且大于0的正数");
		    }
		}
		if(Strings.isEmpty(sign)){
			throw new RuntimeException("参数sign数字签名不能为空");
		}
		
		if(Strings.isEmpty(sign)){
			throw new RuntimeException("参数sign数字签名不能为空");
		}
		if(Strings.isEmpty(userOrderNo)){
			throw new RuntimeException("请求流水号不能为空");
		}
		else{
			validateUserOrderNo(paramsMap.get("channelNo"), userOrderNo);
		}
		
		if(Strings.isEmpty(place)||Strings.isEmpty(carNumber)||Strings.isEmpty(duration)||Strings.isEmpty(enterTime)){
			throw new RuntimeException("停车场收费参数缺失，请检查");
		}
//		if(enterTime.length()!=12){
//			throw new RuntimeException("入场时间格式必须为yyyyMMddhhmm,格式错误");
//		}
//		//天 小时 分的格式显示停车时长
//		int parkTime = Integer.parseInt(duration);
//		int day = parkTime/(60*24);
//		int hour= parkTime/60;
//		int min = parkTime%60;
//		paramsMap.put("duration",day+"天"+hour+"小时"+min+"分");
//		//进场时间 转换为XXXX年XX月XX日XX时XX分
//		String date = enterTime.substring(0, 4)+"年"+enterTime.substring(4, 6)+"月"+enterTime.substring(6, 8)+"日"+enterTime.substring(8, 10)+"时"+enterTime.substring(10, 12)+"分";
//		paramsMap.put("enterTime",date);
	}
	
	private void validateUserOrderNo(String channelNo,String userOrderNo) throws RuntimeException{
		if(userOrderNo.length()<16||userOrderNo.length()>32){
			throw new RuntimeException("请求流水号长度为16-32");
		}
    	OrderPaymentCondition condition = new OrderPaymentCondition();
    	condition.setChannelNo(channelNo);
    	condition.setExtraTradeNo(userOrderNo);
    	OrderPayment payment = payCallBackOperatorService.findByCondition(condition);
    	if(null!=payment){
    		throw new RuntimeException(ApiErrorCode.PAY_400005.getDesc());
    	}
    }
	
}
