<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
   long date = new Date().getTime();
   request.setAttribute("date", date);
%>
<html lang="zh-cmn-Hans">
<head>
<title>申请入驻</title>
<meta charset="UTF-8">
<meta http-equive="Cache-Control" content="no-cache"/>
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart class="applyBg">

<div class="apply">
	<!-- <div class="register_logo"><img src="${path}/resources/images/wechat/logo.png" height="40px" alt="维码付"></div> -->
	<h1>商户申请表</h1>
	<p class="introduce">非常感谢您选择${channelName }！您填写的资料审核通过后，当天即可免费开通使用。</p>
	<!-- <ul>
		<li><b>开通${channelName }后，您将享有以下权益；</b></li>
		<li><i>1</i>0成本开通微信、支付宝、京东钱包、QQ钱包等主流支付方式；</li>
		<li><i>2</i>支持实时结算和隔日结算两种方式；</li>
		<li><i>3</i>${channelName }祝您收得多，收获更多。</li>
	</ul> -->
	
	<h2 class="zc_title">1、申请人基本信息</h2>
	<dl>
		<dt>姓名*</dt>
		<dd><input class="zc_input" type="text" id="realName" value="${info.name }"></dd>
		<%-- <dt>商户名称*</dt>
		<dd><input class="zc_input" type="text" id="merchantName" value="${info.merchantName }"></dd> --%>
		<dt>身份证号码*</dt>
		<dd><input class="zc_input" type="text" id="idCard" value="${info.idCard }"></dd>
		
		<div class="file_upload clearfix">
		<div class="requirements">请使用本人身份证&nbsp;&nbsp;<a href="javascript:;" class="open-popup" data-target="#zpyq">照片要求></a></div>
			<ul class="id-photo2  clearfix">
				<li>
					<p>上传身份证正面*</p>
					<c:if test="${!empty info.idcardImg1 }">
						<img style="height:100px" src="${info.idcardImg1 }" id="imgIdentityUpCli">
					</c:if>
					<c:if test="${empty info.idcardImg1 }">
						<img style="height:100px" src="${path}/resources/images/wechat/id_up.png" id="imgIdentityUpCli">
					</c:if>
				</li>
				<li>
					<p>上传身份证反面*</p>
					<c:if test="${!empty info.idcardImg2 }">
						<img style="height:100px" src="${info.idcardImg2}" id="imgIdentityCoverCli">
					</c:if>
					<c:if test="${empty info.idcardImg2 }">
						<img style="height:100px" src="${path}/resources/images/wechat/id_over.png" id="imgIdentityCoverCli">
					</c:if>
				</li>
				<li style="width:100%">
					<c:if test="${!empty info.idcardImg3 }">
						<img style="height:100px;width:45%" src="${info.idcardImg3}" id="imgIdentityCli">
					</c:if>
					<c:if test="${empty info.idcardImg3 }">
						<img style="height:100px;width:45%" src="${path}/resources/images/wechat/hand_id.png" id="imgIdentityCli">
					</c:if>
					<p>本人手持身份证正面照片*</p>
				</li>
			</ul>
			<input type="hidden" name="imgIdentityUp" id="imgIdentityUp" value="${info.idcardImg1 }">
			<input type="hidden" name="imgIdentityCover" id="imgIdentityCover" value="${info.idcardImg2 }">
			<input type="hidden" name="imgIdentity" id="imgIdentity" value="${info.idcardImg3 }">
		</div>
		
		<!-- <div class="file_upload">
			<p>本人手持身份证正面照片*</p>	
			<p>
			<c:if test="${!empty info.idcardImg3 }">
				<img style="height:100px;width:45%" src="${info.idcardImg3}" id="imgIdentityCli">
			</c:if>
			<c:if test="${empty info.idcardImg3 }">
				<img style="height:100px;width:45%" src="${path}/resources/images/wechat/hand_id.png" id="imgIdentityCli">
			</c:if>
			</p>
			<div class="requirements">照片内身份证信息需清晰可辨</div>
			<input type="hidden" name="imgIdentity" id="imgIdentity" value="${info.idcardImg3 }">
		</div> -->

	 <dd style="display: none;" id="errorDiv">
			<div class="weui_cell_hd"></div>
			<div class="weui_cell_bd weui_cell_primary">
				<span id="errorMsg" style="color: red;">手机号码有误</span>
			</div>
		</dd>
	</dl>
	
</div>
<input type="hidden" id="merchantNo" value="${currentUser.merchantNo }">
<input type="hidden" id="timestamp" value="${map.timestamp }">
<input type="hidden" id="nonceStr" value="${map.nonceStr }">
<input type="hidden" id="signature" value="${map.signature }">
<input type="hidden" id="appId" value="${map.appId }">
<input type="hidden" id="jsapiTicket" value="${map.jsapi_ticket }">

<div class="weui-btn-area" style="padding-bottom:20px">
		<button href="javascript:void(0);" class="weui-btn weui-btn_primary" id="next">下一步</button>	
</div>

<div id="zpyq" class="weui-popup__container"><!-- 照片要求弹出层 -->
	<div class="weui-popup__overlay"></div>
	<div class="weui-popup__modal">
		<div class="toolbar">
          <div class="toolbar-inner">
            <a href="javascript:;" class="picker-button close-popup">取消</a>
            <h1 class="title">照片要求</h1>
          </div>
        </div>
		<article class="weui-article balanceC">
			<div class="weui-cells weui-cells__form" style="margin-top:50px;background:#EFEFF4">
			
				<div class="weui-panel weui-panel_access">
					<div class="weui-panel__hd"><h4>身份证照片要求</h4></div>
					<div class="weui_panel__bd">
						<div class="weui-media-box">
							<p class="weui-media-box__desc">1.经营人本人持有效二代身份证；</p>
							<p class="weui-media-box__desc">2.拍照时确保身份证边框完整，文字清晰，无破损。</p>
						</div>
					</div>
				</div>

				<div class="weui-panel weui-panel_access">
					<div class="weui-panel__hd"><h4>拍照示例</h4></div>
					<div class="weui-panel__bd">
						<div class="weui-media-box">
							<p class="weui-media-box__desc"><img style="width:100%" src="${path}/resources/images/wechat/tem.png"></p>
						</div>
					</div>
				</div>
				
				<div class="weui-panel weui-panel_access">
					<div class="weui-panel__hd"><h4>手持身份证照片示例</h4></div>
					<div class="weui-panel__bd">
						<div class="weui-media-box">
							<p class="weui-media-box__desc"><img style="width:100%" src="${path}/resources/images/wechat/id_hold.png"></p>
						</div>
					</div>
				</div>
				
			</div>
		</article>
		
		<div class="weui-btn-area" style="padding-bottom:20px">
			<a href="javascript:;" class="weui-btn weui-btn_primary close-popup">返回</a>
		</div>
		
	</div>
</div>

<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jweixin-1.0.0.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/wechat/apply1.js?rnd=${date}" type="text/javascript" charset="utf-8"></script>

</body></html>