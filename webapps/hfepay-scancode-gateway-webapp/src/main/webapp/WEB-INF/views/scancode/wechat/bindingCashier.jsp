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
<title>绑定收银员</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart>

<div class="weui-cells weui-cells_form bdsyy">
	<div class="weui-cell">
		<div class="weui-cell__hd"><label for="name" class="weui-label">选择门店</label></div>
		<div class="weui-cell__bd weui-cell__primary">
			<input class="weui-input" id="xzmd" type="text" value="${storeName }" placeholder="请选择门店" data-values="${storeNo }" readonly="readonly">
		</div>
	</div>
  <input type="hidden" value="${cashier }" id="cashier">
	<div class="weui-cell">
		<div class="weui-cell__hd"><label for="name" class="weui-label">选择二维码</label></div>
		<div class="weui-cell__bd weui-cell__primary">
			<input class="weui-input" id="xzewm" type="text" value="${qrcodeName }" placeholder="请选择二维码" data-values="${qrcode }"  readonly="readonly">
		</div>
	</div>
</div>

<div class="weui-btn-area" style="margin-bottom:20px">
		<a class="weui-btn weui-btn_primary" id="sure">确定</a>
</div>

<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/wechat/bindingCashier.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>


</body></html>