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
<title>结算账户管理</title>
<meta charset="UTF-8">
<meta http-equive="Cache-Control" content="no-cache"/>
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>

<body ontouchstart>
	<input type="hidden" id="merchantNo" value="${currentUser.merchantNo }">

<div class="weui-cells">
	<div class="weui-cell">
		<div class="weui-cell__bd"><p>法人姓名</p></div>
		<div class="weui-cell__ft">${info.name }</div>
	</div>
	<div class="weui-cell">
		<div class="weui-cell__bd"><p>身份证号码</p></div>
		<div class="weui-cell__ft">${info.idCard }</div>
	</div>
	<div class="weui-cell">
		<div class="weui-cell__bd"><p>银行卡开户行</p></div>
		<div class="weui-cell__ft">${info.bankName }</div>
	</div>
	<div class="weui-cell">
		<div class="weui-cell__bd"><p>银行卡号</p></div>
		<div class="weui-cell__ft">${info.bankCard }</div>
	</div>
	<div class="weui-cell">
		<div class="weui-cell__bd"><p>手机号码</p></div>
		<div class="weui-cell__ft">${info.mobile }</div>
	</div>
</div>

<div class="weui-btn-area" style="margin-bottom:20px">
	<a href="javascript:;" class="weui-btn weui-btn_primary open-popup" data-target="#editCard">变更结算账号</a>
</div>

<!-- 修改绑定银行卡相关信息弹窗 -->
<div id="editCard" class="weui-popup__container">
	<div class="weui-popup__overlay"></div>
	<div class="weui-popup__modal">
		<div class="toolbar">
		  <div class="toolbar-inner">
			<a href="javascript:;" class="picker-button close-popup">取消</a>
			<h1 class="title">变更结算账号</h1>
		  </div>
		</div>
			<div class="weui-cells weui-cells__form" style="margin-top:44px">
				<div class="weui-cell">
					<div class="weui-cell__bd">
						<p>银行卡号*</p>
						<div class="enterAmount"><input class="weui-input" type="text" placeholder="请输入新的银行卡号" style="width:90%" id="bankCard" onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"></div>
					</div>
				</div>
				<div class="weui-cell">
					<div class="weui-cell__bd">
						<p>银行卡开户行*(<span style="color:red;">由卡号自动识别</span>)</p>
						<div class="enterAmount"><input class="weui-input" type="tel" placeholder="请输入新的银行卡开户行" style="width:90%" id="bankName"></div>
					</div>
				</div>
				<input type="hidden" id="clearBankChannelNo">
				<div class="weui-cell">
					<div class="weui-cell__bd">
						<p>银行预留手机号码*</p>
						<div class="enterAmount"><input class="weui-input" type="tel" placeholder="请输入新的银行预留手机号" style="width:90%" id="mobile"></div>
					</div>
				</div>
				<div class="weui-cell">
					<div class="weui-cell__bd">
						<p>手机验证码*</p>
						<div class="enterAmount"><input class="weui-input" type="tel" placeholder="请输入验证码" style="width:60%;display: inline;" id="validateCode">&nbsp;&nbsp;<a href="#" class="hqyzm" id="code">获取验证码</a></div>
					</div>
				</div>
			</div>
		
		<div class="weui-btn-area" style="margin-bottom:20px">
			<a href="javascript:;" id="sureChange" class="weui-btn weui-btn_primary close-popup">确认</a>
		</div>
		
	</div>
</div>


<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/wechat/settlement.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>

</body></html>