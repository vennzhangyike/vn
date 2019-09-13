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
<title>注册收银员成功</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart class="applyBg">

<div class="weui-msg">
	<div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
	<div class="weui-msg__text-area">
		<h2 class="weui-msg__title">注册收银员成功</h2>
		<p class="weui-msg__desc">请长按屏幕二维码，识别并关注公众号</p>
	</div>
	<div class="weui-msg__opr-area cashierSuccess">
		<img src="${img }" width="280px" height="280px">
	</div>
</div>

<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>

</body></html>