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
<title>关于我们</title>
<meta charset="UTF-8">
<meta http-equive="Cache-Control" content="no-cache"/>
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">

<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">

</head>
<body>

<div class="about">
	${aboutUs }
	<!-- <p>小二买单是一款专注移动支付的产品，连接银行、发卡机构、移动营运商、城市小额支付营运 商、商家和消费者之间的一个多元化支付系统平台。专门负责处理来自消费者在商家刷卡消费 时的支付请求信息，提供消费者选择微信、支付宝等网络支付、资金清算、查询统计等功能， 实现多渠道、跨区域的网上、线下支付交易服务。商家及消费者通过使用小二买单结算方式真 正的实现了缩短交易支付周期，减少环节，提高工作效率。</p>
	<p>小二买单具有以下优势：</p>
	<p>1、无需改造收银系统，无需开通支付宝，微信支付账号，开通即用；</p>
	<p>2、离线也可用，无需网络，不用插电，便捷收银；适用于任何环境；</p>
	<p>3、T+1/T+0银行到账；</p>
	<p>4、特有营销导引和核销改价功能；</p>
	<p>5、极高的安全性:</p>
	<p>6、支持多平台支付，支持微信支付，支付宝支付</p> </p>-->

</div>

<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>

	  
</body></html>