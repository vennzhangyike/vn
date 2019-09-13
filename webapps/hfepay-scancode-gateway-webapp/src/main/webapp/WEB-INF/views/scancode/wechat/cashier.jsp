<%@ page language="java" contentType="text/html; charset=UTF-8"  import="java.util.Date" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
   String url=request.getScheme()+"://"+request.getServerName();
   request.setAttribute("url", url+path);
   request.setAttribute("time", new Date().getTime());
%>
<html lang="zh-cmn-Hans">
<head>
<title>收银员管理</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart>
	<div class="weui-msg" style="display:none;" id="noCashier">
		<img src="${path}/resources/images/wechat/cashierNo.png" alt="请添加收银员">
	</div>
	
	<div class="weui-panel weui-panel_access" style="display:none;" id="hasCashier">
		<div class="weui-panel__bd">
		<div id="list">
			<%-- <a href="javascript:void(0);" id="cashier01" class="weui-media-box weui-media-box_appmsg">
			<div class="weui-media-box__hd">
				<img class="weui-media-box__thumb" src="${path}/resources/images/wechat/cashier2.png" alt="收银员">
			</div>
			<div class="weui-media-box__bd">
				<h4 class="weui-media-box__title">文静(15508669852)</h4>
				<p class="weui-media-box__desc">面点王（深圳店）</p>
				<p class="skz"><span>待绑定</span></p>
			</div>
		</a>
		<a href="javascript:void(0);" id="cashier01" class="weui-media-box weui-media-box_appmsg">
			<div class="weui-media-box__hd">
				<img class="weui-media-box__thumb" src="${path}/resources/images/wechat/cashier2.png" alt="收银员">
			</div>
			<div class="weui-media-box__bd">
				<h4 class="weui-media-box__title">钟文姬(155086523456)</h4>
				<p class="weui-media-box__desc">面点王（上海店）</p>
				<p class="skz"><span class="yellow">收款中</span></p>
			</div>
		</a>--%>
		</div>
			<a href="javascript:void(0);" id="more" class="weui-media-box weui-media-box_appmsg" style="text-align: center;">更多...</a>
		</div>
	</div>



<div class="weui-footer weui-footer_fixed-bottom weui-footer-a">
	<div class="weui-btn-area clearfix">
		<a href="javascript:;" class="weui-btn weui-btn_primary open-popup" style="float:left;width:45%" data-target="#cashier">添加收银员</a>
		<a href="store" class="weui-btn weui-btn_default" style="color:#333;margin-top:0;float:right;width:45%">门店管理</a>
		<a id="clickSee" href="javascript:;" class="weui-btn weui-btn_primary" data-target="#seeCashier" style="display:none;">查看收银员信息</a>
	</div>
</div>


<!-- 添加收银员弹窗 -->
<div id="cashier" class="weui-popup__container">
	<div class="weui-popup__overlay"></div>
	<div class="weui-popup__modal swskFull">
		<div class="toolbar">
          <div class="toolbar-inner">
            <a href="javascript:;" class="picker-button close-popup">关闭</a>
            <h1 class="title">添加收银员</h1>
          </div>
        </div>
		<div class="sh_code">
			<h2>${info.merchantName }</h2>
			<%-- <input type="hidden" id="qrCodeUrl" value="${url}/cashier/toregister/${info.channelNo}/${info.merchantNo}"> --%>
			<!-- <div id="code" style="text-align: certer;"></div> -->
			<%-- <img src="${path}/resources/images/wechat/sh_code.jpg"> --%>
			<div id="qrcode">
		        <div id="codeico"><img src="${icon }" height="60px" width="60px"></div>
		    </div>
			<p>打开微信，扫一扫成为收银员</p>
		</div>
	</div>
</div>

<!-- 查看收银员弹窗 -->
<div id="seeCashier" class="weui-popup__container">
	<div class="weui-popup__overlay"></div>
	<div class="weui-popup__modal">
		<div class="toolbar">
          <div class="toolbar-inner">
            <a href="javascript:;" class="picker-button close-popup">关闭</a>
            <h1 class="title">查看收银员</h1>
          </div>
        </div>
		<div class="weui-cells weui-cells__form" style="margin-top:44px" id="detail">
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<p class="gray">姓名：</p>
					<div class="cashier_list userName">文静</div>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<p class="gray">联系电话：</p>
					<div class="cashier_list mobile">15508669852</div>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<p class="gray">所属门店：</p>
					<div class="cashier_list storeName">面点王（深圳店）</div>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<p class="gray">收款二维码：</p>
					<div class="cashier_list qrcodeName">二维码1、二维码2、二维码3、二维码4、二维码5、二维码6</div>
				</div>
			</div>
		</div>
		<div class="weui-btn-area" style="margin-bottom:20px">
			<a href="javascript:;" id="sureChange" class="weui-btn weui-btn_primary close-popup">关闭</a>
		</div>
	</div>
</div>

<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/qrcode/jquery.qrcode.js?rnd=${version}" type="text/javascript"></script>
<script src="${path}/resources/scripts/qrcode/qrcode.js?rnd=${version}" type="text/javascript"></script>
<script src="${path}/resources/scripts/qrcode/drawQrCode.js?rnd=${version}&t=${time}" type="text/javascript"></script>
<script src="${path}/resources/scripts/wechat/cashier.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
init('${url}/cashier/toregister/${info.agentNo}/${info.merchantNo}');
</script>
</body></html>