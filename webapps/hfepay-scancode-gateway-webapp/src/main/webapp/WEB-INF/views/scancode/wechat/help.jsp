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
<title>使用帮助</title>
<meta charset="UTF-8">
<meta http-equive="Cache-Control" content="no-cache"/>
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body>

<div class="help">
<%-- 	<h2>小二买单入驻流程</h2>
	<ul>
		<li>1、扫码关注公众号</li>
		<li>2、申请商户入驻</li>
		<li>3、打印收款二维码</li>
		<li>4、开始使用小二买单收单</li>
	</ul>
	<img src="${path}/resources/images/wechat/process.jpg">
	<br/><br/>
	<h2>小二买单提现流程</h2>
	<ul>
		<li>1、选择提现</li>
		<li>2、输入提现金额并确认</li>
		<li>3、提现成功</li>
	</ul>
	<img src="${path}/resources/images/wechat/txlc.png"> --%>
	
	${helpInfo }
	
</div>

<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>

	  
</body></html>