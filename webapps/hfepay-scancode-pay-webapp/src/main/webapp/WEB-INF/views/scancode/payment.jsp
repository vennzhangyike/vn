<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<html>
<head>
<title>扫码收款</title>
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=1.0">
<meta name="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta name="format-detection" content="email=no">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css?rnd=${date}">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body>
	
	<%-- <input type="hidden" value="${paymentType}" id="payType"/> --%>
<!-- 	<div class="mid">深圳市华付信息技术有限公司 / 1000048821</div> -->
	<div class="store"><p><img src="${path}/resources/images/wechat/progress2.png" height="40px">${storeName }</p></div>
	<div class="money_c">
		<div class="money_t">输入收款金额</div>
		<div class="money"><span>￥</span><em id="amount">0.00</em></div>
	</div>
	<!-- <div class="tip">单笔交易金额2.5元-3000元</div> -->
	<!-- <div id="discount" class="tip"></div> -->
	<!-- <div class="footer">&copy; 2016 小二买单</div>
	<div class="tip"><c:if test="${browerType == 'WXZF'}">微信支付安全由中国人保财产公司(PICC)全额承保</c:if></div> -->
	<%-- <div class="technical"><img src="${path}/resources/images/wechat/technical.png" height="40px"></div> --%>
	<div class="message tip" style="clear:both;"></div>
	<div class="keypad">
		<div class="col">
			<div class="key digit">1</div>
			<div class="key digit">4</div>
			<div class="key digit">7</div>
			<div class="key back"></div>
		</div>
		<div class="col">
			<div class="key digit">2</div>
			<div class="key digit">5</div>
			<div class="key digit">8</div>
			<div class="key digit">0</div>
		</div>
		<div class="col">
			<div class="key digit">3</div>
			<div class="key digit">6</div>
			<div class="key digit">9</div>
			<div class="key point">.</div>
		</div>
		<div class="col">
			<div class="keys empty"><img src="${path}/resources/images/wechat/del.png" height="22px"></div>
			<div class="keys enter"><div class="sure"><p>付款</p></div></div>
		</div>
	</div>
</body>
<form action="${path }/scan/pay" method="post" id="subForm">
	<input type="hidden" value="${qrCode}" id="qrCode" name="qrCode"/>
	<input type="hidden" value="${identityNo}" id="identityNo" name="identityNo"/>
	<input type="hidden" value="${storeNo}" id="storeNo" name="storeNo"/>
	
	<input type="hidden" value="" id="amt" name="price"/>
</form>
<input type="hidden" value="${payCode}" id="payCode" name="payCode"/>
<input type="hidden" value="${merchantNo}" id="merchantNo" name="merchantNo"/>
<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script type="application/javascript" src="${path}/resources/scripts/payment.js?rnd=${version}"></script> 

</html>

