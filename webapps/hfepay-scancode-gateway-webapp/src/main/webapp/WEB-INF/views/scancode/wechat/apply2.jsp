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
<title>申请入驻</title>
<meta charset="UTF-8">
<meta http-equive="Cache-Control" content="no-cache"/>
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
<style type="text/css">
.widthCss{width:100%;}
</style>
</head>
<body ontouchstart class="applyBg">

<div class="apply">
	<h1>商户申请表</h1>
	
	<h2 class="zc_title">2、申请人商铺信息</h2>
	<dl>
		<dt>商户名称*</dt>
		<dd><input class="zc_input" type="text" id="merchantName" value="${merchantName }"></dd>
		<dt>门店名称*</dt>
		<dd><input class="zc_input" type="text" id="storeName" value="${store.storeName }"></dd>
		<dt>服务电话*</dt>
		<dd><input class="zc_input" type="tel" id="servicePhone" value="${store.servicePhone }"></dd>
		<dt>店铺地址</dt>
		<dd><input class="zc_input" type="text" id="address" value="${store.storeAddress }"></dd>
		<!-- <dl>
			<dt>营业执照</dt>
			<dd>
				<div class="weui_cells weui_cells_form" style="color:#555;font-size:14px;margin-bottom:15px">
					<div class="weui_cell weui_cell_switch">
						<div class="weui_cell_hd weui_cell_primary"><span id="license">无营业执照</span></div>
						<div class="weui_cell_ft">
							<input class="weui_switch" type="checkbox" <c:if test="${!empty store.merchantLicense }">checked="checked"</c:if>/>
						</div>
					</div>
				</div>
			</dd>-->
		</dl>
		
	<%-- <dt class="haszz">营业执照号</dt>
	<dd class="haszz"><input class="zc_input" type="text" id="merchantLicense" value="${store.merchantLicense }"></dd>
	<div class="file_upload haszz"><!-- 有营业执照 -->
		<p>店铺营业执照原件照片*</p>	
		<p>
			<c:if test="${!empty store.merchantLicenseImg }">
				<img style="height:100px;width:45%" src="${store.merchantLicenseImg}" id="idlicenceimgCli">
			</c:if>
			<c:if test="${empty store.merchantLicenseImg }">
				<img style="height:100px;width:45%" src="${path}/resources/images/wechat/id_up.png" id="idlicenceimgCli">
			</c:if>
		</p>
		<div class="requirements">照片内店铺营业执照需清晰可辨</div>
		<input type="hidden" name="idlicenceimg" id="idlicenceimg" value="${store.merchantLicenseImg }">
	</div>
	
	<!-- 无营业执照 -->
	<div id="haszzhand" class="file_upload">
		<p>本人手持身份证正面在店铺收银台照片*</p>	
		<p>
		<c:if test="${!empty store.storePhotosImg }">
			<img style="height:100px;width:45%" src="${store.storePhotosImg}" id="idhandimgCli">
		</c:if>
		<c:if test="${empty store.storePhotosImg }">
			<img style="height:100px;width:45%" src="${path}/resources/images/wechat/hand_id.png" id="idhandimgCli">
		</c:if>	
		</p>
		<div class="requirements">照片内身份证信息需清晰可辨</div>
		<input type="hidden" name="idhandimg" id="idhandimg" value="${store.storePhotosImg }">
	</div>
	
	<div id="haszzgroup" class="file_upload">
		<p>本人与店铺门头合照*</p>	
		<p>
		<c:if test="${!empty store.groupPhotoImg }">
			<img style="height:100px;width:45%" src="${store.groupPhotoImg}" id="idgroupimgCli">
		</c:if>
		<c:if test="${empty store.groupPhotoImg }">
			<img style="height:100px;width:45%" src="${path}/resources/images/wechat/id_dphz.png" id="idgroupimgCli">
		</c:if>
		</p>
		<div class="requirements">照片内店铺名需清晰可辨</div>
		<input type="hidden" name="idgroupimg" id="idgroupimg" value="${store.groupPhotoImg }">
	</div>
	
	<div id="haszzin" class="file_upload">
		<p>店铺内景照*</p>	
		<p>
		<c:if test="${!empty store.storeImg }">
			<img style="height:100px;width:45%" src="${store.storeImg}" id="idinimgCli">
		</p>
		</c:if>
		<c:if test="${empty store.storeImg }">
			<img style="height:100px;width:45%" src="${path}/resources/images/wechat/id_neijing.png" id="idinimgCli">
		</p>
		</c:if>
		<div class="requirements">照片内店铺内景需清晰可辨</div>
		<input type="hidden" name="idinimg" id="idinimg" value="${store.storeImg }">
	</div>
 --%>	

<div class="weui-tab sm-tab">
		<div class="weui-navbar" id="tabChange">
			<a class="weui-navbar__item weui-bar__item--on">有营业执照</a>
			<a class="weui-navbar__item">无营业执照</a>
		</div>
		<div class="weui-tab__bd">
			<div class="weui-tab__bd-item weui-tab__bd-item--active file_upload">
				<dl id="licenceLi" style="margin:0 15px">
					<dt>营业执照号*</dt>
					<dd><input class="zc_input" type="text" id="merchantLicense" value="${store.merchantLicense }"></dd>
				</dl>
			
				<ul class="id-photo2 clearfix">
					<li id="licencePic">
						<c:if test="${!empty store.merchantLicenseImg }">
							<img style="height:100px;" src="${store.merchantLicenseImg}" id="idlicenceimgCli">
						</c:if>
						<c:if test="${empty store.merchantLicenseImg }">
							<img height="100px" src="${path}/resources/images/wechat/id_up.png" id="idlicenceimgCli">
						</c:if>
						<p>商铺营业执照原件照片*</p>
						<input type="hidden" name="idlicenceimg" id="idlicenceimg" value="${store.merchantLicenseImg }">
					</li>
					<li id="idgroupimgLi">
						<c:if test="${!empty store.groupPhotoImg }">
							<img style="height:100px;" src="${store.groupPhotoImg}" id="idgroupimgCli">
						</c:if>
						<c:if test="${empty store.groupPhotoImg }">
							<img height="100px" src="${path}/resources/images/wechat/id_dphz.png" id="idgroupimgCli">
						</c:if>
						<input type="hidden" name="idgroupimg" id="idgroupimg" value="${store.groupPhotoImg }">
						<p>本人与商铺门头合照</p>
					</li>
					<li id="idinimgLi">
						<c:if test="${!empty store.storeImg }">
							<img style="height:100px;" src="${store.storeImg}" id="idinimgCli">
						</c:if>
						<c:if test="${empty store.storeImg }">
							<img height="100px" src="${path}/resources/images/wechat/id_neijing.png" id="idinimgCli">
						</c:if>
						<input type="hidden" name="idinimg" id="idinimg" value="${store.storeImg }">
						<p>商铺内景照</p>
					</li>
					<li id="idhandimgLi">
						<c:if test="${!empty store.storePhotosImg }">
							<img style="height:100px;" src="${store.storePhotosImg}" id="idhandimgCli">
						</c:if>
						<c:if test="${empty store.storePhotosImg }">
							<img height="100px" src="${path}/resources/images/wechat/hand_id.png" id="idhandimgCli">
						</c:if>	
						<input type="hidden" name="idhandimg" id="idhandimg" value="${store.storePhotosImg }">
						<p>本人手持身份证正面在商铺收银台照片</p>
					</li>
				</ul>
			</div>
			<%-- <div id="tab2" class="weui-tab__bd-item file_upload">
				<ul class="id-photo2 clearfix">
					<li>
						<c:if test="${!empty store.groupPhotoImg }">
							<img style="height:100px;width:45%" src="${store.groupPhotoImg}" id="idgroupimgCli">
						</c:if>
						<c:if test="${empty store.groupPhotoImg }">
							<img height="100px" src="${path}/resources/images/wechat/id_dphz.png" id="idgroupimgCli">
						</c:if>
						<input type="hidden" name="idgroupimg" id="idgroupimg" value="${store.groupPhotoImg }">
						<p>本人与商铺门头合照*</p>
					</li>
					<li>
						<c:if test="${!empty store.storeImg }">
							<img style="height:100px;width:45%" src="${store.storeImg}" id="idinimgCli">
						</p>
						</c:if>
						<c:if test="${empty store.storeImg }">
							<img height="100px" src="${path}/resources/images/wechat/id_neijing.png" id="idinimgCli">
						</p>
						</c:if>
						<input type="hidden" name="idinimg" id="idinimg" value="${store.storeImg }">
						<p>商铺内景照*</p>
					</li>
					<li style="width:100%">
						<c:if test="${!empty store.storePhotosImg }">
							<img style="height:100px;width:45%" src="${store.storePhotosImg}" id="idhandimgCli">
						</c:if>
						<c:if test="${empty store.storePhotosImg }">
							<img height="100px" src="${path}/resources/images/wechat/hand_id.png" id="idhandimgCli">
						</c:if>	
						<input type="hidden" name="idhandimg" id="idhandimg" value="${store.storePhotosImg }">
						<p>本人手持身份证正面在商铺收银台照片*</p>
					</li>
				</ul>
			</div> --%>
		</div>
	</div>
		
<input type="hidden" id="merchantNo" value="${currentUser.merchantNo }">
<input type="hidden" id="timestamp" value="${map.timestamp }">
<input type="hidden" id="nonceStr" value="${map.nonceStr }">
<input type="hidden" id="signature" value="${map.signature }">
<input type="hidden" id="appId" value="${map.appId }">
<input type="hidden" id="jsapiTicket" value="${map.jsapi_ticket }">

		<dd style="display: none;" id="errorDiv">
			<div class="weui_cell_hd"></div>
			<div class="weui_cell_bd weui_cell_primary">
				<span id="errorMsg" style="color: red;"></span>
			</div>
		</dd>
		
	<!--</dl>-->
	
</div>

<div class="weui-btn-area" style="padding-bottom:20px">
		<a href="apply1" class="weui-btn weui-btn_primary" style="width:45%;float:left;">上一步</a>
		<button href="javascript:void(0);" class="weui-btn weui-btn_primary" id="next" style="width:45%;">下一步</button>
</div>


<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jweixin-1.0.0.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/wechat/apply2.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>

</body></html>