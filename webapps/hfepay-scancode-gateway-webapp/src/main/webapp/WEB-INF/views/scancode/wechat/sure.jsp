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
<title>确定支付</title>
<meta charset="UTF-8">
<meta http-equive="Cache-Control" content="no-cache"/>
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart>

<div class="weui-msg">
  <div class="weui-msg__text-area">
    <p class="weui-msg__desc">商品金额</p><h1 class="doc-head amount">&yen; 100.00</h1>
  </div>
</div>

<div class="weui-cells">
	<div class="weui-cell">
		<div class="weui-cell__bd">
			<p>商品名称</p>
		</div>
		<div class="weui-cell__ft">
			珍珠奶茶
		</div>
	</div>
	<div class="weui-cell">
		<div class="weui-cell__bd">
			<p>商户名称</p>
		</div>
		<div class="weui-cell__ft">
			皇茶
		</div>
	</div>
	<div class="weui-cell">
		<div class="weui-cell__bd">
			<p>订单编号</p>
		</div>
		<div class="weui-cell__ft">
			20161011532688
		</div>
	</div>
	<div class="weui-cell">
		<div class="weui-cell__bd">
			<p>创建时间</p>
		</div>
		<div class="weui-cell__ft">
			2016/10/11
		</div>
	</div>
</div>

<div class="flex_b10">
  <div class="weui-btn-area">
      <a href="javascript:;" class="weui-btn weui-btn_primary">确定</a>
      <a href="javascript:;" class="weui-btn weui-btn_default">取消</a>
  </div>
</div>

<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>

</body></html>