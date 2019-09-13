<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
   request.setAttribute("date", new Date().getTime());
%>
<html lang="zh-cmn-Hans">
<head>
<title>注册收银员</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart class="applyBg">

<div class="register">
	<h1>注册收银员</h1>
</div>

<div class="weui-cells weui-cells_form">
	<div class="weui-cell">
		<div class="weui-cell__hd"><label class="weui-label">姓名</label></div>
		<div class="weui-cell__bd">
			<input class="weui-input" type="text" placeholder="请输入姓名" id="realName">
		</div>
	</div>
	<input type="hidden" id="openId" value="${openId }"/>
	<input type="hidden" id="organNo" value="${organNo }"/>
	<div class="weui-cell">
		<div class="weui-cell__hd"><label class="weui-label">身份证号</label></div>
		<div class="weui-cell__bd">
			<input class="weui-input" type="text" id="idCardNo" placeholder="请输入身份证号">
		</div>
	</div>
	<div class="weui-cell">
		<div class="weui-cell__hd"><label class="weui-label">手机号</label></div>
		<div class="weui-cell__bd">
			<input class="weui-input" type="tel" placeholder="请输入手机号" id="mobile">
		</div>
	</div>
</div>

<div class="weui-btn-area" style="padding-bottom:20px">
		<a href="javascript:void(0);" class="weui-btn weui-btn_primary" id="sure">提交</a>
</div>

<%-- <div class="weui-footer weui-footer_fixed-bottom">
    <p class="weui-footer__text">©${channelName }</p>
</div> --%>


<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/wechat/registerCashier.js?rnd=${date}" type="text/javascript" charset="utf-8"></script>


</body></html>