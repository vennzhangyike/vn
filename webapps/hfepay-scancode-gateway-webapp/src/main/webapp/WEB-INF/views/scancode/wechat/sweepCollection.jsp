

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<html>
<head>
<title>扫我付款</title>
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
	<!-- <div class="tip">请输入收款金额</div>
	<div class="amount">￥ 0.00</div> -->
	<div class="store"><p><img src="${path}/resources/images/wechat/progress2.png" height="40px">${storeName }</p></div>
	<div class="money_c">
		<div class="money_t">输入收款金额</div>
		<div class="money"><span>￥</span><em id="amount">0.00</em></div>
	</div>
	<!-- <div class="footer">&copy; 2016 小二买单</div> -->
	<div class="tip">请选择支付方式</div>
	<ul class="zffs">
	<c:forEach var="payway" items="${payWays }" varStatus="payWaysStatus">
		<li class="" payCode="${payway.key }"><img src="${path}/resources/images/${payway.key }.png" height="24px"><span></span></li>
	</c:forEach>
	</ul>
	<%-- <div class="technical technical2"><img src="${path}/resources/images/wechat/technical.png" height="40px"></div> --%>
	<div class="message tip" style="clear:both;"></div>
	<div class="keypad keypad2">
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
			<div class="keys enter"><div class="sure"><p>下一步</p></div></div>
		</div>
	</div>
</body>
<form action="${path }/scancode/sweeppay" method="post" id="subForm">
	<input type="hidden" value="" id="payCode" name="payCode"/>
	<input type="hidden" value="" id="orderAmt" name="orderAmt"/>
	<input type="hidden" value="0" id="minLimit" name="minLimit"/>
</form>

<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script type="application/javascript" src="${path}/resources/scripts/wechat/sweepCollection.js?rnd=${version}"></script> 

</html>

