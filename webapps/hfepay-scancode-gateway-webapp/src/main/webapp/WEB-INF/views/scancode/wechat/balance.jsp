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
<title>账户总览</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart>

<div class="weui-panel">
	<div class="jrsr">
		今日收入(元)<p><fmt:formatNumber type="number" value="${todayTotalIn le 0?0:todayTotalIn }" pattern="0.00" maxFractionDigits="2"/></p>
	</div>
</div>

<div class="weui-panel">
	<div class="balance_sr clearfix">
		<div class="balance_info">
			总收入(元)<p><fmt:formatNumber type="number" value="${totalIn le 0?0:totalIn }" pattern="0.00" maxFractionDigits="2"/></p>
		</div>
		<div class="balance_info">
			本月收入(元)<p><fmt:formatNumber type="number" value="${monthTotalIn le 0?0:monthTotalIn }" pattern="0.00" maxFractionDigits="2"/></p>
		</div>
	</div>
</div>

<div class="weui-panel" style="margin-bottom:80px">
	<div class="balance_ktxye">
		<img src="${path}/resources/images/wechat/txye.png" height="60px">
		<h2>可提现余额(元)</h2>
		<h3><fmt:formatNumber type="number" value="${canWithDraws le 0?0:canWithDraws }" pattern="0.00" maxFractionDigits="2"/></h3>
	</div>
</div>

<div class="weui-footer weui-footer_fixed-bottom weui-footer-a">
	<div class="weui-btn-area">
		<a href="withdrawpage" class="weui-btn weui-btn_primary open-popup" data-target="#cashier">提现</a>
	</div>
</div>


<script src="${path}/resources/scripts/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js" type="text/javascript" charset="utf-8"></script>

</body></html>