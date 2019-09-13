<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<html>
<head>
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=1.0">
<meta name="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta name="format-detection" content="email=no">
<link href="${path}/css/style.css" rel="stylesheet" type="text/css"/>
<title></title>
<link rel="shortcut icon" href="${path}/images/favicon.ico"/>
</head>
<body>
<div id="content" class="get-browser-type-content">
<h1 id="title">提醒</h1>
<div id="icon"></div>
<p id="notify">
<span>请使用</span>
<span class="high-light font-big">&nbsp;微信、支付宝</span>
<span class="high-light">&nbsp;"扫一扫"</span><span>&nbsp;来完成付款！</span></p>
</body>
<input type="hidden" value= "${platformQrcode.qrCode}" id="qrCode"/>
<input type="hidden" value= "${platformQrcode.merchantNo}" id="merchantCode"/>
<input type="hidden" value= "${returnInfo.merchantSettledUrl}" id="merchantSettledUrl"/>
<input type="hidden" value= "${returnInfo.returnCode}" id="returnCode"/>
<input type="hidden" value= "${returnInfo.returnDesc}" id="returnDesc"/>
</div>
</body>
</html>
<script src="${path}/js/jquery-2.2.3.min.js?rnd=${version}" type="text/javascript"></script>
<script src="${path}/js/jquery.validate.js?rnd=${version}" type="text/javascript"></script>
<script src="${path}/js/base.js?rnd=${version}" type="text/javascript"></script>
<script type="text/javascript" src="${path}/js/scancode/getBrowserType.js?rnd=${version}"></script> 