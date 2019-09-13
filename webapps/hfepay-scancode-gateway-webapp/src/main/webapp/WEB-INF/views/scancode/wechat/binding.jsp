<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<html lang="zh-cmn-Hans">
<head>
<title>绑定手机号码</title>
<meta charset="UTF-8">
<meta http-equive="Cache-Control" content="no-cache"/>
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart class="applyBg">

<div class="register">
	<h1>绑定手机号码</h1>
</div>
	<input type="hidden" id="merchantNo" value="${currentUser.merchantNo }">
<div class="weui-cells weui-cells_form">
	<div class="weui-cell">
		<div class="weui-cell__hd"><label class="weui-label">手机号</label></div>
		<div class="weui-cell__bd weui-cell_primary">
			<input class="weui-input" type="tel" placeholder="请输入手机号" id="phone">
		</div>
	</div>
	<div class="weui-cell">
		<div class="weui-cell__hd"><label class="weui-label">验证码</label></div>
		<div class="weui-cell__bd weui-cell_primary">
			<input class="weui-input" type="number" placeholder="请输入验证码" id="validateCode" maxlength="6">
		</div>
		<div class="weui_vcode">
			<a href="#" style="display:block" id="code" class="aweui_vcode">获取验证码</a>
			<!-- <p style="display:none">等待60秒</p> -->
		</div>
	</div>
	
	<div class="weui-cell" style="display: none;" id="errorDiv">
		<div class="weui-cell__hd"></div>
		<div class="weui-cell__bd weui-cell_primary">
			<span id="errorMsg" style="color: red;">手机号码有误</span>
		</div>
	</div>
</div>

<div class="weui-btn-area">
	<a href="javascript:;" id="show-confirm" class="weui-btn weui-btn_primary">确认</a>
</div>


<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/wechat/binding.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
</body></html>