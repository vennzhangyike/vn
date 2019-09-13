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
<title>门店管理</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart>

<div class="weui-panel weui-panel_access" style="display:">
	<div class="weui-panel__bd" id="list">
		<%-- <a href="javascript:void(0);" id="cashier01" class="weui-media-box weui-media-box_appmsg">
			<div class="weui-media-box__hd">
				<img class="weui-media-box__thumb" src="${path}/resources/images/wechat/progress2.png" alt="收银员">
			</div>
			<div class="weui-media-box__bd">
				<h4 class="weui-media-box__title">面点王（深圳店）</h4>
				<p class="weui-media-box__desc">0755-65465656</p>
				<p class="skz"><span class="yellow">默认门店</span></p>
			</div>
		</a>
		<a href="javascript:void(0);" id="cashier01" class="weui-media-box weui-media-box_appmsg">
			<div class="weui-media-box__hd">
				<img class="weui-media-box__thumb" src="${path}/resources/images/wechat/progress2.png" alt="收银员">
			</div>
			<div class="weui-media-box__bd">
				<h4 class="weui-media-box__title">面点王（上海店）</h4>
				<p class="weui-media-box__desc">021-65465656</p>
			</div>
		</a> --%>
	</div>
</div>


<div class="weui-footer weui-footer_fixed-bottom weui-footer-a">
	<div class="weui-btn-area clearfix">
		<a href="javascript:;" class="weui-btn weui-btn_primary open-popup" data-target="#store">添加门店</a>
		<a id="clickSee" href="javascript:;" class="weui-btn weui-btn_primary open-popup" data-target="#seeStore" style="display:none;">查看门店</a>
		<a id="clickEdit" href="javascript:;" class="weui-btn weui-btn_primary open-popup" data-target="#editStore" style="display:none;">编辑门店</a>
	</div>
</div>


<!-- 添加门店弹窗 -->
<div id="store" class="weui-popup__container">
	<div class="weui-popup__overlay"></div>
	<div class="weui-popup__modal">
		<div class="toolbar">
          <div class="toolbar-inner">
            <a href="javascript:;" class="picker-button close-popup">关闭</a>
            <h1 class="title">添加门店</h1>
          </div>
        </div>
		<div class="weui-cells weui-cells__form" style="margin-top:44px">
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<p class="gray">门店名称<span class="red">*</span></p>
					<div class="enterAmount"><input class="weui-input storeName" type="text" placeholder="请输入店铺名称" style="width:90%"></div>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<p class="gray">门店电话</p>
					<div class="enterAmount"><input class="weui-input servicePhone" type="tel" placeholder="请输入店铺电话" style="width:90%"></div>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<p class="gray">门店地址</p>
					<div class="enterAmount"><input class="weui-input storeAddress" type="text" placeholder="请输入店铺地址" style="width:90%"></div>
				</div>
			</div>
		</div>
		<div class="weui-btn-area" style="margin-bottom:20px">
			<a href="javascript:;" id="sureAddChange" class="weui-btn weui-btn_primary close-popup">确认</a>
		</div>
	</div>
</div>

<!-- 查看门店弹窗 -->
<div id="seeStore" class="weui-popup__container">
	<div class="weui-popup__overlay"></div>
	<div class="weui-popup__modal">
		<div class="toolbar">
          <div class="toolbar-inner">
            <a href="javascript:;" class="picker-button close-popup">关闭</a>
            <h1 class="title">查看门店</h1>
          </div>
        </div>
		<div class="weui-cells weui-cells__form" style="margin-top:44px">
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<p class="gray">门店名称：</p>
					<div class="cashier_list storeName"></div>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<p class="gray">门店电话：</p>
					<div class="cashier_list servicePhone"></div>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<p class="gray">门店地址：</p>
					<div class="cashier_list storeAddress"></div>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<p class="gray">门店二维码：</p>
					<div class="cashier_list qrcodeName"></div>
				</div>
			</div>
		</div>
		<div class="weui-btn-area" style="margin-bottom:20px">
			<a href="javascript:;" id="sureChanges" class="weui-btn weui-btn_primary close-popup">关闭</a>
		</div>
	</div>
</div>

<!-- 编辑门店弹窗 -->
<div id="editStore" class="weui-popup__container">
	<div class="weui-popup__overlay"></div>
	<div class="weui-popup__modal">
		<div class="toolbar">
          <div class="toolbar-inner">
            <a href="javascript:;" class="picker-button close-popup">关闭</a>
            <h1 class="title">编辑门店</h1>
          </div>
        </div>
		<div class="weui-cells weui-cells__form" style="margin-top:44px">
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<p class="gray">门店名称<span class="red">*</span></p>
					<div class="enterAmount"><input class="weui-input storeName" type="text" value="面点王（深圳店）" style="width:90%;color:#333"></div>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<p class="gray">门店电话</p>
					<div class="enterAmount"><input class="weui-input servicePhone" type="tel" value="0755-65465656" style="width:90%;color:#333"></div>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<p class="gray">门店地址</p>
					<div class="enterAmount"><input class="weui-input storeAddress" type="text" value="深圳市南山区" style="width:90%;color:#333"></div>
				</div>
			</div>
			<input type="hidden" class="idid" id="idid" value=""/>
		</div>
		<div class="weui-btn-area" style="margin-bottom:20px">
			<a href="javascript:;" id="sureChange" class="weui-btn weui-btn_primary close-popup">确认</a>
		</div>
	</div>
</div>


<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/wechat/store.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>

<script>
 
</script>

</body></html>