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
<title>提现失败</title>
<meta charset="UTF-8">
<meta http-equive="Cache-Control" content="no-cache"/>
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart class="applyBg">

<div class="weui-msg">
	<div class="weui-icon-area"><i class="weui-icon-warn weui-icon_msg"></i></div>
	<div class="weui-textarea" style="padding-top:10%;">
		<c:if test="${!empty errorMsg }">
			<p class="weui-msg__desc">${errorMsg }</p>
		</c:if>
		<c:if test="${empty errorMsg }">
			<p class="weui-msg__desc">提现失败</p>
		</c:if>
		
	</div>
</div>

<div class="weui-btn-area" style="margin-top: 80%;">
	<a href="my" class="weui-btn weui-btn_primary close-popup">关闭</a>
</div>


<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>

</body></html>