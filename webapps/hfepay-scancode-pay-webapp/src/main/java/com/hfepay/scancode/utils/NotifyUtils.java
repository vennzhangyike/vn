package com.hfepay.scancode.utils;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.hfepay.commons.base.ciphers.CipherAES;
import com.hfepay.commons.base.ciphers.Encrypter;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Springs;
import com.hfepay.pay.service.ChannelSecretKeyService;
import com.hfepay.pay.service.PayOrderPaymentService;
import com.hfepay.scancode.bean.ResponseHeader;
import com.hfepay.scancode.bean.ResponseMessage;
import com.hfepay.scancode.commons.MsgResponse;
import com.hfepay.scancode.commons.ScanCodeGetWayErrorCode;
import com.hfepay.scancode.commons.SuccMsgResponse;
import com.hfepay.scancode.commons.bo.MerchantNotifyMessage;
import com.hfepay.scancode.commons.condition.ChannelSecretKeyCondition;
import com.hfepay.scancode.commons.contants.OrderStatus;
import com.hfepay.scancode.commons.contants.ScanCodeErrorCode;
import com.hfepay.scancode.commons.entity.ChannelSecretKey;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.commons.exception.ApiErrorCode;
import com.hfepay.scancode.commons.exception.ValidatException;
import com.hfepay.scancode.httpclient.HttpProtocolHandler;
import com.hfepay.scancode.httpclient.HttpRequest;
import com.hfepay.scancode.httpclient.HttpResponse;
import com.hfepay.scancode.httpclient.HttpResultType;
import com.hfepay.scancode.result.PayResponseMessage;

import net.sf.json.JSONObject;

/**
 * 
 * @author husain
 *
 */
public class NotifyUtils {
	 //阻塞队列，FIFO
    private static final LinkedBlockingQueue<MerchantNotifyMessage> concurrentLinkedQueue = new LinkedBlockingQueue<MerchantNotifyMessage>(); 
    private static final ExecutorService executorService = Executors.newFixedThreadPool(5); 
    private static final Logger log = LoggerFactory.getLogger(NotifyUtils.class);
    private static final String input_charset ="utf-8";
    private static PayOrderPaymentService payOrderPaymentService = Springs.getBean("payOrderPaymentService");
    private static ChannelSecretKeyService channelSecretKeyService = Springs.getBean("channelSecretKeyService");
    /**
     * 队列中添加数据
     * @param message
     */
    public static void setToQueue(MerchantNotifyMessage message){
    	concurrentLinkedQueue.add(message);
    	executorService.submit(new Executor());
    }
    
    
    static class Executor implements Runnable {  
        public void run() { 
        	try {
				MerchantNotifyMessage message = concurrentLinkedQueue.take();
				handleCall(message);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }  
    }   
    
    
    private static void handleCall(MerchantNotifyMessage bo){
		log.info("++++++++++++++++to execute handleCall to next channel。。。。++++++++++");
		try {
			//获取原交易订单信息
			OrderPayment orderPayment = payOrderPaymentService.findOrderPaymentByTradeNo(bo.getUserOrderNo());
			String url = orderPayment.getExtraCallBack();//下游上传的回调地址;
			if(Strings.isEmpty(url)){//没有上送url无需回调下游
				return;
			}
			log.info("++++++++++++++++call back url is ++++++++++"+url);
			//获取秘钥加密报文
			ChannelSecretKey secretKey = getChannelSecretKey(orderPayment.getChannelNo(), null);//暂不做ip限制
			//4.封装返回参数
			PayResponseMessage message = new PayResponseMessage();
			PayResponseMessage.Body bodyResp = message.getBody();
			//body:支付返回的map作为body
			if (bo.getStatus().equals("02")) {//交易成功
				bodyResp.setReturnCode(ApiErrorCode.PAY_400000.getCode());
				bodyResp.setReturnMsg(ApiErrorCode.PAY_400000.getDesc());
				bodyResp.setMerchantName(orderPayment.getMerchantName());
				bodyResp.setTradeNo(orderPayment.getTradeNo());
				bodyResp.setUserOrderNo(orderPayment.getExtraTradeNo());//上送的流水号
//				bodyResp.setPayPlatOrderNo(bo.getChannelNo());//微信或者支付宝返回的交易流水号
				//加密报文体
				MsgResponse resp = encryptMsg(secretKey, message);
				String str = JSON.toJSONString(resp);
				JSONObject obj = JSONObject.fromObject(str);
				//请求下游
				buildRequestJsonPost(url,obj.toString());
			}else{//交易失败
				log.error("============handleCall method the current order is not success,so do not have to callback...."+bo.getUserOrderNo());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("============handleCall method error",e);
		}
		
	}
    
    /**
     * 根据渠道编号查询秘钥信息
     * @param channelNo
     * @param method
     * @param ip
     * @return
     */
    private static ChannelSecretKey getChannelSecretKey(String channelNo,String ip) {
    	ChannelSecretKeyCondition condition = new ChannelSecretKeyCondition();
 		condition.setChannelNo(channelNo);
 		ChannelSecretKey channelSecretKey  = channelSecretKeyService.query(condition);
 		if (channelSecretKey == null || !"1".equals(channelSecretKey.getStatus())) {// 密钥不存在或不可用
 			throw new ValidatException(
 					ApiErrorCode.VALIDAT_100005.getCode(),
 					ApiErrorCode.VALIDAT_100005.getDesc());
 		}
 		//如果没有配置IP地址，默认所有IP都能访问
 		if (Strings.isNotBlank(channelSecretKey.getBoundIp()) && channelSecretKey.getBoundIp().indexOf(ip) < 0) {// IP地址未绑定
 			throw new ValidatException(
 					ApiErrorCode.VALIDAT_100009.getCode(),
 					ApiErrorCode.VALIDAT_100009.getDesc());
 		}
 		return channelSecretKey;
 	}
    
    /**
     * 加密报文
     */
    @SuppressWarnings("rawtypes")
    private static MsgResponse encryptMsg(ChannelSecretKey secretKey, ResponseMessage message) {
        try {
        	Object body = message.getBody();
        	String signData = CipherAES.getSign(JSON.toJSONString(body), secretKey.getJoinUserPublicKey(), secretKey.getJoinPrivateKey());
        	String aesKey = Encrypter.getRandomString(16);
            String encrtptKey = CipherAES.getKey(secretKey.getJoinUserPublicKey(), secretKey.getJoinPrivateKey(),aesKey);
            String encryptData = CipherAES.encryptMsg(JSON.toJSONString(body), secretKey.getJoinUserPublicKey(), secretKey.getJoinPrivateKey(),aesKey);
            ResponseHeader head = message.getHead();
            head.setSign(signData);
            head.setEncryptKey(encrtptKey);
            head.setRespCode(ApiErrorCode.SYSTEM_000001.getCode());
            head.setRespDesc(ApiErrorCode.SYSTEM_000001.getDesc());
            log.info("报文体："+JSON.toJSONString(body));
            return new SuccMsgResponse(message.getHead(), encryptData);
        } catch (RuntimeException e) {
        	log.error(e.getMessage(), e);
            throw new ValidatException(ApiErrorCode.SYSTEM_999998.getCode(), ScanCodeErrorCode.SYSTEM_999998.getDesc());
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
            throw new ValidatException(ApiErrorCode.SYSTEM_999998.getCode(), ScanCodeErrorCode.SYSTEM_999998.getDesc());
        }
    }
    
    /**
	 * POST 请求
	 * @param URL
	 * @param objectString
	 * @return
	 * @throws Exception
	 */
	private static String buildRequestJsonPost(String URL,String objectString) throws Exception {
        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        //设置编码集
        request.setMethod("POST");
        request.setCharset(input_charset);
        request.setUrl(URL);
        HttpResponse response = httpProtocolHandler.execute(request,objectString,"","");
        if (response == null) {
            return null;
        }
        String strResult = response.getStringResult();
        return strResult;
    }
    
    public static void main(String[] args) {
    	NotifyUtils service = new NotifyUtils();
    	for (int i = 0; i < 20; i++) {
    		MerchantNotifyMessage message = new MerchantNotifyMessage();
        	message.setAccountType("1"+i);
        	service.setToQueue(message);
		}
	}
}

