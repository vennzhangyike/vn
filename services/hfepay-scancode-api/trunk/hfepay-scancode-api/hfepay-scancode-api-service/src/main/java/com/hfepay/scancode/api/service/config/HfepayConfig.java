package com.hfepay.scancode.api.service.config;

public class HfepayConfig {
	
	//prd
	public static String gateway_uri = "http://api.huaepay.com/gateway/";//u转接平台url地址
	
	//test
//	public static String gateway_uri = "http://172.16.2.203:29080/connector/gateway/";//u转接平台url地址
	
	public static String version="1.0.0";//u转接平台默认版本号
	
	//银行卡四要素验证地址
	public static String bank_card_auth_uri_4 = "http://api.hfdatas.com/superapi/super/auth/smrz4";//u
	//银行卡三要素验证地址
	public static String bank_card_auth_uri_3="http://api.hfdatas.com/superapi/super/auth/smrz3";//u
	//银行卡三要素验证地址
	public static String bank_card_auth_uri_2="http://api.hfdatas.com/superapi/super/auth/idcard";//u
	//appKey 四要素
	public static String appKey_four = "g7BwCAGDGmG1JXzlxMFwV2TT";//u
	// appKey 三要素
	public static String appKey_three = "x8vaupGXrgvl0xaKFSN2JZF5";//u
	// appKey 二要素
	public static String appKey_two = "XhAahdaKKpiSlCCweSr+gIka";//u
	//userCode 商户编号
	public static String userCode="HFJK20161019000017";//u金融数据平台默认商户号
	//应用编码
	public static String sysCode= "HFJKAPP20161019000003";//u金融数据平台默认应用编码
	
	//服务方法ID
	public static String join_service_method="merchant.join";//商户入驻
	public static String trade_order="trade.order";//扫码付预订单
	public static String trade_barcode="trade.barcode";//条码支付
	public static String trade_refund="trade.refund";//退款
	public static String trade_order_official="trade.order.official";//微信公众号预订单
	public static String update_rate_service_method="merchant.rate.update";//费率变更
	public static String merchant_wallet_query_method="merchant.wallet.query";//余额查询
	public static String trade_query_method = "trade.query";//订单查询
	public static String merchant_card_update_method ="merchant.card.update";//商户银行卡信息变更
	public static String get_settle_file="channel.file.get";//获取对账文件
	public static String get_withdraws="merchant.withdrawals";//提现
	public static String get_join_change="merchant.info.update";//商户基本信息修改
	public static String trade_query="trade.query";//查询订单信息
	public static String draw_query="merchant.draw.query";//查询提现信息
	
	public static String input_charset ="utf-8";
	
	//平台提供给渠道方的公钥
	public final static String publickey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuzSv63X49gsI1rQBHL+cO7gZyeUQKRFXY4oQ4AUpkXkt7u29r9R6rcpk5Pk3Gd6OR4+/C4mXcb1aVR17bPkhJXiVT4Yv9BfzMjURn42kzvSU/+ZKrWaphsEpXQAkwaXSvEDAo3GyWZmOfiAu5ayxGklWSsz1QXByAmm/WwS+mYclBQ/KXiRZsBlPIe2/vAavlGDSuLW8ggtd4eyPMfohu16hWrTUK3mv/t05W4aTHvng3Q2i9u6G/8s+2BbcDV4ysYh6uwWfL/tyVXV/0WeHo2so400yY39aNQ2Q63rRVTIvk8ba0EpXIsjSD0Py81pk3k+M4ewecsyFhTs1a66PNQIDAQAB";
	//渠道本身的私钥
	public final static String privatekey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC0U+ID9JCrRisvnJP9QNGgoWC0revS+6I16Qf58qtl0lbXxTF8SIeY93dDnV3bcZRL6xG+MypSHu5AMxHX7YNUWU+HMvqwwNdDJWpbY349GeGRYBoLYJ3e863LdcAkW//3etyeDgurFbr16/njta2K7xendUniAT0uXQvM14tP+jHSmib5rb3tgVnt1W0qrwund53+aY9rwDCgaFsZCzrDe6gXBN7CsU13yjHAnz8M7CWL0gwNlQmoK/na9zbrqKbmkxU17PawnIzUwMDd7aKodpQhZxCRbZT2MDntvGYvFYTaJnFJLuqdgtZ3zOFxpqokHZMkSRmqe0qEnec8CGL3AgMBAAECggEASPOafwEkubWEaRmOISlk5PgEWHP38H3OTML+cBbFUkb6zQXAlEf/W1nZzOfP/aQAW4o4vlqSp3BN8Hj4ogM7WxdafhaYpoMNUuPDGD+y5WMgByD6yFmI5JZuW95CIjN55RO2Rtblz4vV/AFAMefg0lERn7RqdF0N9EC+MluMx4iBSWDFaKGuxCcnLSWJlSVtPL2rHjZZNvM3pvm+UyH1lnREZ1JDxzGaBrspbPuBM67ieJutAPuCiCTfeDhXJaGVrUJz/gdlUxLbbpV1LOVA/YzGCJZWGFQeqyqwk/mEu5CJXwP01n2wxYc234GPpkJIPRSaK3Ats1c2mqT63ch8oQKBgQDeBpqaXjAvOoJb2JxC+B/gyNj/ccspGMVWyNNifHC9kd9ImVRxdzyDH7FgtOJdywr8LP95yz2xCfkqTp2tBe8R1nxP5HORMXEqkw/rgPrDzLaJRNvVsVHSxhCY6W4JRkS2IRw23j7yvP9T4zgGJGh7tXHIJuBr/2RcM4kHtZAVcQKBgQDP69eMSOyX7ANuUTJ+0pUD2/SfnNEANAUNF6cmWgjk8wY9I+KgKngIVvwW7B3DSM+7T4bOqJH16WSIByvdKvpPizJ18bmhzSWMA1N3OhGCTSN9aZv05QLsnLKhADEeatX+QwkDU/YlNUfdAhdwIrDvEYjHdxX+7NhmyqF5QUiq5wKBgQCPalqhTif3yeZ8p33AcIA+9d3dm0atBDgsZ8rLejBk8330aj62kFvBI06zdgQmq8orvHFF3fe6jQOqxV9qbUPRFOf1v067QeGkq5wD9quEoI5kOtt5vHFrIc+Exnvyd7ZYoyiTdJnauCSBrmr45Lms9zZJIHukpzLPr+Za2Xf5sQKBgQCb1khX/NBXja2EoZcXNv45RkKy3vZHELY4eeHt/M1tzDiA2sShBzOwDIIlLBOiqpAH9DM18WJ0zdlrmvRlcDDsLYhEnkhj3T6nElccpYk6AFb5SI57nqnybf/0bBBkm+6IA/ZdaDC08ppKK6clhNrYVGwQFFGIIDyRzo34M9YjQQKBgDbCKHfEc6eRBL6FuqWWJk9N5cWjNZZskejQ7QTkStS3Vm0TSfWVBxU4d+8A85kphMur5J+JaLTq9VY8tHq7z0wBu6Lmqb3PcK7GmLDggpTjLtpHga3WdP4eGOZWKIZe2XAqeRqIW7AjAe4C9Uq7lgW11WfT1SVRcEmURR3dMEsw";
	
	//微信公众号APPID
	//public final static String appid="wx8aea7001a9cc84e8";//u
	
	//微信公众号SECRET
	//public final static String secret="b186679ce5425eb59b0f428f1d60fcf6";//u
	
	//access_token redisKey
	public final static String ACCESS_TOKEN="access_token";
	//jsapi_ticket redisKey
	public final static String JSAPI_TICKET="jsapi_ticket";
	
	public final static String ALIPAY_TICKET="alipay_ticket";
	public final static String ALIPAY_ACCESS_TOKEN="alipay_access_token";
	//缓存时间（秒）
	public final static int ACCESS_TOKEN_TIME=7000;
	//jsapi_ticket缓存时间（秒）,为了防止accessToken和ticket的时间差，缓存设置短一点
	//public final static int JSAPI_TOKEN_TIME=7140;
	
	/**持久化到缓存的微信url key**/
	public final static String[] DIC_KEY= new String[]{"WPUSH","WTOKEN","WAPITICKET","WAUTHOR","WDOWNLOADURL","CHANNELNO","WUNIONID","WECHATBACKGO"};
	public final static String WPUSH_KEY = "WPUSH";//消息推送 key
	public final static String WTOKEN_KEY = "WTOKEN";//获取tocken  key
	public final static String WAPITICKET_KEY = "WAPITICKET";//获取jsapi key
	public final static String WDOWNLOADURL_KEY = "WDOWNLOADURL";//获取jsapi key
	public final static String WAUTHOR_KEY = "WAUTHOR";//静默授权 key
	public final static String WUNIONID_KEY = "WUNIONID";//静默授权 key
	public final static String WECHAT_BACK_GO_KEY = "WECHATBACKGO";//微信授权地址
	public final static String CHANNEL_REDIS_FAMILY = "admin:channel:";//redis key family
	public final static String CHANNEL_REDIS_WX_KEY = "wxParams:";//redis key 微信配置
	public final static String CHANNEL_REDIS_DC_KEY = "dictionary:";//redis key 字典
	public final static String CHANNEL_REDIS_CHANNEL_KEY = "CHANNELNO";
	public final static String CHANNEL_REDIS_LF_KEY = "liquidationFeeParams:";//redis key 清算手续费配置
	
	public final static String MERCHANT_REDIS_AGENT_FAMILY = "admin:agent:parent:";//redis key family
	
	public final static String BANK_AUTH_KEY = "bank.auth";
}
