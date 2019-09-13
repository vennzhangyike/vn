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
<title>编辑二维码</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart>

<div class="weui-panel weui-panel_access" style="display:">
	<div class="weui-panel__hd" id="storeName">${storeName }</div>
	<div class="weui-panel__bd" id="list">
		<%-- <a href="javascript:void(0);" id="cashier01" class="weui-media-box weui-media-box_appmsg">
			<div class="weui-media-box__hd">
				<img class="weui-media-box__thumb" src="${path}/resources/images/wechat/editCode.png" alt="收银员">
			</div>
			<div class="weui-media-box__bd">
				<h4 class="weui-media-box__title">二维码1</h4>
				<p class="weui-media-box__desc">已绑定收银员</p>
				<p class="skz"><span class="yellow">收款中</span></p>
			</div>
		</a>
		<a href="javascript:void(0);" id="cashier01" class="weui-media-box weui-media-box_appmsg">
			<div class="weui-media-box__hd">
				<img class="weui-media-box__thumb" src="${path}/resources/images/wechat/editCode.png" alt="收银员">
			</div>
			<div class="weui-media-box__bd">
				<h4 class="weui-media-box__title">二维码2</h4>
				<p class="weui-media-box__desc">未绑定收银员</p>
			</div>
		</a> --%>
	</div>
</div>
<input type="hidden" id="storeId" value="${storeId }"/>
<script src="${path}/resources/scripts/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/wechat/editCode.js" type="text/javascript" charset="utf-8"></script>
</body></html>