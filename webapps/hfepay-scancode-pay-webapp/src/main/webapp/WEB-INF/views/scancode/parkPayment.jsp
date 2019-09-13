<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<html>
<head>
<title>确认缴费</title>
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
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<style>
html, body {
    height: auto;
}
</style>
</head>
<body>
	
	<%-- <div class="store"><p>停车费</p></div>
	<div class="money"><span>￥</span><em id="amount">${paramsMap.price }</em></div>
	<div class="div_white">
		<div class="money_t"><span class="span_left">已停时长：</span><span class="span_right">${paramsMap.duration }</span></div>
		<div class="money_t"><span class="span_left">入场时间：</span><span class="span_right">${paramsMap.enterTime}</span></div>
		<div class="money_t"><span class="span_left">车牌号码：</span><span class="span_right">${paramsMap.carNumber }</span></div>
		<div class="money_t"><span class="span_left">停车场：</span><span class="span_right">${paramsMap.place }</span></div>
	</div> --%>
	<h3 style="text-align: center;margin:10px 0;font-size:20px">停车费</h3>
	<h1 style="text-align: center;font-size:46px;color:#1aad19;margin-bottom:15px">${paramsMap.price }</h1>
	
	<div class="weui-cells weui-cell_access" style="margin-top:0.5em">
		<div class="weui-cell">
			<div class="weui-cell__bd weui-cell_primary"><p style="width:120px">已停时长</p></div>
			<div class="">${paramsMap.duration }</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd weui-cell_primary"><p style="width:120px">入场时间</p></div>
			<div class="">${paramsMap.enterTime}</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd weui-cell_primary"><p style="width:120px">车牌号码</p></div>
			<div class="">${paramsMap.carNumber }</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd weui-cell_primary"><p style="width:120px">停车场</p></div>
			<div class="">${paramsMap.place }</div>
		</div>
	</div>

<form action="${path }/scan/pay" method="post" id="subForm">
	<input type="hidden" value="${paramsMap.qrcode}" id="qrCode" name="qrCode"/>
	<input type="hidden" value="${paramsMap.identityNo}" id="identityNo" name="identityNo"/>
	<input type="hidden" value="${paramsMap.storeNo}" id="storeNo" name="storeNo"/>
	<input type="hidden" value="${paramsMap.price }" id="amt" name="price"/>
	<input type="hidden" value="${paramsMap.userOrderNo }" id="userOrderNo" name="userOrderNo"/>
	<div class="weui-btn-area" style="padding:20px 0">
		<button type="submit" href="javascript:void(0);" class="weui-btn weui-btn_primary" id="next">立即缴费</button>	
	</div>
</form>
<%-- <div style="text-align: center;color:#666">${paramsMap.storeName }</div><br/> --%>
<input type="hidden" value="${paramsMap.payCode}" id="payCode" name="payCode"/>
<input type="hidden" value="${paramsMap.merchantNo}" id="merchantNo" name="merchantNo"/>
<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
</body>
</html>

