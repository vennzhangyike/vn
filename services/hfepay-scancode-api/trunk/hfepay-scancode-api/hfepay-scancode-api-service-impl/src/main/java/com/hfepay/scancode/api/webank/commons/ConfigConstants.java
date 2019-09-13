package com.hfepay.scancode.api.webank.commons;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value="configConstants")
public class ConfigConstants {
	//版本号
	@Value("${version}")
	private String version;
	//appid
	@Value("${app_id}")
	private String app_id;
	//秘钥
	@Value("${secret}")
	private String secret;
	//授权类型
	@Value("${grant_type}")
	private String grant_type;
	//获取token
	@Value("${access_token_url}")
	private String access_token_url;
	//获取ticket
	@Value("${ticket_url}")
	private String ticket_url;
	//商户入驻
	@Value("${merchant_join_url}")
	private String merchant_join_url;
	//支付宝预订单
	@Value("${precreate_trade_url}")
	private String precreate_trade_url;
	//查询订单信息
	@Value("${query_trade_url}")
	private String query_trade_url;
	//退款
	@Value("${refund_url}")
	private String refund_url;
	//退款查询
	@Value("${query_refund_url}")
	private String query_refund_url;
	//支付宝条码支付
	@Value("${scan_pay_url}")
	private String scan_pay_url;
	//撤销
	@Value("${cancel_url}")
	private String cancel_url;
	//客户端证书
	@Value("${client_cet}")
	private String client_cet;
	//服务器证书
	@Value("${trust_cet}")
	private String trust_cet;
	//客户端证书密码
	@Value("${key_store_password}")
	private String key_store_password;
	//服务器端证书密码
	@Value("${key_store_trust_password}")
	private String key_store_trust_password;
	
	//------------------------------------微信扫码支付秘钥---------------------------------------------
	//初始秘钥
	@Value("${join_key}")
	private String join_key;
	//进件秘钥
	@Value("${merchant_key}")
	private String merchant_key;
	
	//------------------------------------微信扫码支付地址---------------------------------------------
	//微信扫码支付  商户入驻
	@Value("${wechat_merchant_join_url}")
	private String wechat_merchant_join_url;
	//微信扫码支付 商户信息查询
	@Value("${wechat_select_merchant_info_url}")
	private String wechat_select_merchant_info_url;
	//微信扫码支付 商户信息变更
	@Value("${wechat_update_merchant_info_url}")
	private String wechat_update_merchant_info_url;
	//微信扫码支付 下单
	@Value("${wechat_nao_url}")
	private String wechat_nao_url;
	//微信扫码支付 查询订单
	@Value("${wechat_ngos_url}")
	private String wechat_ngos_url;
	//微信扫码支付 刷卡支付
	@Value("${wechat_mao_url}")
	private String wechat_mao_url;
	//微信扫码支付 刷卡支付订单查询
	@Value("${wechat_mgos_url}")
	private String wechat_mgos_url;
	//微信扫码支付 撤销订单
	@Value("${wechat_ro_url}")
	private String wechat_ro_url;
	//微信扫码支付 退款
	@Value("${wechat_nro_url}")
	private String wechat_nro_url;
	//微信扫码支付 退款查询
	@Value("${wechat_nros_url}")
	private String wechat_nros_url;
	
	//------------------------------------微信公众号支付地址---------------------------------------------
	
	//微信公众号支付 下单
	@Value("${wechat_jsapi_add_order_url}")
	private String wechat_jsapi_add_order_url;
	//微信公众号支付 查询订单状态
	@Value("${wechat_jsapi_order_status_url}")
	private String wechat_jsapi_order_status_url;
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getGrant_type() {
		return grant_type;
	}

	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}

	public String getAccess_token_url() {
		return access_token_url;
	}

	public void setAccess_token_url(String access_token_url) {
		this.access_token_url = access_token_url;
	}

	public String getTicket_url() {
		return ticket_url;
	}

	public void setTicket_url(String ticket_url) {
		this.ticket_url = ticket_url;
	}

	public String getMerchant_join_url() {
		return merchant_join_url;
	}

	public void setMerchant_join_url(String merchant_join_url) {
		this.merchant_join_url = merchant_join_url;
	}

	public String getPrecreate_trade_url() {
		return precreate_trade_url;
	}

	public void setPrecreate_trade_url(String precreate_trade_url) {
		this.precreate_trade_url = precreate_trade_url;
	}

	public String getQuery_trade_url() {
		return query_trade_url;
	}

	public void setQuery_trade_url(String query_trade_url) {
		this.query_trade_url = query_trade_url;
	}

	public String getRefund_url() {
		return refund_url;
	}

	public void setRefund_url(String refund_url) {
		this.refund_url = refund_url;
	}

	public String getQuery_refund_url() {
		return query_refund_url;
	}

	public void setQuery_refund_url(String query_refund_url) {
		this.query_refund_url = query_refund_url;
	}

	public String getScan_pay_url() {
		return scan_pay_url;
	}

	public void setScan_pay_url(String scan_pay_url) {
		this.scan_pay_url = scan_pay_url;
	}

	public String getCancel_url() {
		return cancel_url;
	}

	public void setCancel_url(String cancel_url) {
		this.cancel_url = cancel_url;
	}

	public String getClient_cet() {
		return client_cet;
	}

	public void setClient_cet(String client_cet) {
		this.client_cet = client_cet;
	}

	public String getTrust_cet() {
		return trust_cet;
	}

	public void setTrust_cet(String trust_cet) {
		this.trust_cet = trust_cet;
	}

	public String getKey_store_password() {
		return key_store_password;
	}

	public void setKey_store_password(String key_store_password) {
		this.key_store_password = key_store_password;
	}

	public String getKey_store_trust_password() {
		return key_store_trust_password;
	}

	public void setKey_store_trust_password(String key_store_trust_password) {
		this.key_store_trust_password = key_store_trust_password;
	}

	public String getWechat_merchant_join_url() {
		return wechat_merchant_join_url;
	}

	public void setWechat_merchant_join_url(String wechat_merchant_join_url) {
		this.wechat_merchant_join_url = wechat_merchant_join_url;
	}

	public String getWechat_select_merchant_info_url() {
		return wechat_select_merchant_info_url;
	}

	public void setWechat_select_merchant_info_url(String wechat_select_merchant_info_url) {
		this.wechat_select_merchant_info_url = wechat_select_merchant_info_url;
	}

	public String getWechat_update_merchant_info_url() {
		return wechat_update_merchant_info_url;
	}

	public void setWechat_update_merchant_info_url(String wechat_update_merchant_info_url) {
		this.wechat_update_merchant_info_url = wechat_update_merchant_info_url;
	}

	public String getWechat_nao_url() {
		return wechat_nao_url;
	}

	public void setWechat_nao_url(String wechat_nao_url) {
		this.wechat_nao_url = wechat_nao_url;
	}

	public String getWechat_ngos_url() {
		return wechat_ngos_url;
	}

	public void setWechat_ngos_url(String wechat_ngos_url) {
		this.wechat_ngos_url = wechat_ngos_url;
	}

	public String getWechat_mao_url() {
		return wechat_mao_url;
	}

	public void setWechat_mao_url(String wechat_mao_url) {
		this.wechat_mao_url = wechat_mao_url;
	}

	public String getWechat_mgos_url() {
		return wechat_mgos_url;
	}

	public void setWechat_mgos_url(String wechat_mgos_url) {
		this.wechat_mgos_url = wechat_mgos_url;
	}

	public String getWechat_ro_url() {
		return wechat_ro_url;
	}

	public void setWechat_ro_url(String wechat_ro_url) {
		this.wechat_ro_url = wechat_ro_url;
	}

	public String getWechat_nro_url() {
		return wechat_nro_url;
	}

	public void setWechat_nro_url(String wechat_nro_url) {
		this.wechat_nro_url = wechat_nro_url;
	}

	public String getWechat_nros_url() {
		return wechat_nros_url;
	}

	public void setWechat_nros_url(String wechat_nros_url) {
		this.wechat_nros_url = wechat_nros_url;
	}

	public String getWechat_jsapi_add_order_url() {
		return wechat_jsapi_add_order_url;
	}

	public void setWechat_jsapi_add_order_url(String wechat_jsapi_add_order_url) {
		this.wechat_jsapi_add_order_url = wechat_jsapi_add_order_url;
	}

	public String getJoin_key() {
		return join_key;
	}

	public void setJoin_key(String join_key) {
		this.join_key = join_key;
	}

	public String getMerchant_key() {
		return merchant_key;
	}

	public void setMerchant_key(String merchant_key) {
		this.merchant_key = merchant_key;
	}

	public String getWechat_jsapi_order_status_url() {
		return wechat_jsapi_order_status_url;
	}

	public void setWechat_jsapi_order_status_url(String wechat_jsapi_order_status_url) {
		this.wechat_jsapi_order_status_url = wechat_jsapi_order_status_url;
	}

}
