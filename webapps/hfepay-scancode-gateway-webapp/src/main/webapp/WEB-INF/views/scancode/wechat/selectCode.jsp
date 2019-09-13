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
<title>关联二维码进行收款</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart>

<div class="weui-msg" style="display:">
	<img src="${path}/resources/images/wechat/selectCode.png" height="120px" alt="收银员选择二维码收款">
	<div class="weui-text_area">
		<h2 class="weui-msg_title">请关联二维码进行收款</h2>
	</div>
</div>

<div class="weui-cells weui-cells_form" id="codes">
	<%-- <div class="weui_cell weui_cell_switch">
		<div class="weui_cell_hd weui_cell_primary">二维码1<span class="codeSkz"></span></div>
		<div class="weui_cell_ft">
			<input class="weui_switch" type="checkbox" value="${item.qrCode }">
		</div>
	</div>
	<div class="weui_cell weui_cell_switch">
		<div class="weui_cell_hd weui_cell_primary">二维码2</div>
		<div class="weui_cell_ft">
			<input class="weui_switch" type="checkbox">
		</div>
	</div>
	<div class="weui_cell weui_cell_switch">
		<div class="weui_cell_hd weui_cell_primary">二维码3</div>
		<div class="weui_cell_ft">
			<input class="weui_switch" type="checkbox">
		</div>
	</div>
	<div class="weui_cell weui_cell_switch">
		<div class="weui_cell_hd weui_cell_primary">二维码4</div>
		<div class="weui_cell_ft">
			<input class="weui_switch" type="checkbox">
		</div>
	</div> --%>
</div>

<div class="weui-btn-area">
	<a href="${path }/scancode/my" class="weui-btn weui-btn_primary">确定</a>
</div>


<%-- <div class="weui_extra_area">
    <div class="copy">©${channelName }</div>
</div> --%>

<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/wechat/selectCode.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
</body></html>