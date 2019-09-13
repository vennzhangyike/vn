<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<html>
<head>
<title></title>
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
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
<style type="text/css">
.weui-icon-warn,.weui-icon-success {
    font-size: 50px;
}
</style>
</head>
<body ontouchstart class="applyBg">
<input type="hidden" value="${returnInfo.returnCode}" id="payResult" />
<div class="weui-msg">
	<div class="success" style="display: none;">
		<div class="weui-icon-area"><i class="weui-icon-success weui-icon-msg"></i></div>
		<div class="weui-textarea">
			<h2 class="weui-msg__title">操作完成</h2>
			<%-- <p class="weui_msg_desc">订单号：${returnInfo.tradeNo}</p> --%>
		</div>
	</div>
	
	<div class="failure" style="display: none;">
		<div class="weui-icon-area"><i class="weui-icon-warn weui-icon-msg"></i></div>
		<div class="weui-textarea">
			<h2 class="weui-msg__title">对不起，支付失败</h2>
			<p class="weui-msg__desc">${returnInfo.returnMsg}</p>
			<p class="weui-msg__desc">订单号：${returnInfo.tradeNo}</p>
		</div>
	</div>
	
	<c:if test="${!empty adviertisementImg && adviertisementImg !='' }">
		<div class="ad">
		   <a href="${adviertisementUrl }" target="_blank"><img src="${adviertisementImg }" alt="广告" width="100%"></a>
		</div>
	</c:if>

	<div class="weui-btn-area" style="padding-bottom:20px">
		<a href="javascript:closed();" class="weui-btn weui-btn_primary">关闭</a>
	</div>
</div>
</body>
</html>
<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="${path}/resources/scripts/paymentResult.js?rnd=${version}"></script> 