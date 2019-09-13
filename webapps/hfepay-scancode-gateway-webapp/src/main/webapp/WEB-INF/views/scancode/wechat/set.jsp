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
<title>设置</title>
<meta charset="UTF-8">
<meta http-equive="Cache-Control" content="no-cache"/>
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart>
<div class="weui-cells">
	<a class="weui-cell weui-cell_access" href="basic">
		<div class="weui-cell__hd"><img src="${path}/resources/images/wechat/progress1.png" style="width:40px;margin-right:10px;display:block"></div>
		<div class="weui-cell__bd">
		<p>基本信息管理</p>
		</div>
		<div class="weui-cell__ft">
			<c:if test="${status=='0' or status=='1' }">
				<span class="red">审核中</span>
			</c:if>
			<c:if test="${status=='4' or status=='2'}">
				<span class="red">审核不通过</span>
			</c:if>
			<c:if test="${status=='3' }">
				<span class="green">已认证</span>
			</c:if>
			<c:if test="${status=='5' }">
				<span class="green">已停用</span>
			</c:if>
			<c:if test="${status=='6' }">
				<span class="green">资料待完善</span>
			</c:if>
		</div>
	</a>
	<a class="weui-cell weui-cell_access" href="merchants">
		<div class="weui-cell__hd"><img src="${path}/resources/images/wechat/progress2.png" style="width:40px;margin-right:10px;display:block"></div>
		<div class="weui-cell__bd">
		<p>商户信息管理</p>
		</div>
		<div class="weui-cell__ft">
			<c:if test="${status=='0' or status=='1' }">
				<span class="red">审核中</span>
			</c:if>
			<c:if test="${status=='4' or status=='2'}">
				<span class="red">审核不通过</span>
			</c:if>
			<c:if test="${status=='3' }">
				<span class="green">已认证</span>
			</c:if>
			<c:if test="${status=='5' }">
				<span class="green">已停用</span>
			</c:if>
			<c:if test="${status=='6' }">
				<span class="green">资料待完善</span>
			</c:if>
		</div>
	</a>
	<a class="weui-cell weui-cell_access" href="merchantRate">
		<div class="weui-cell__hd"><img src="${path}/resources/images/wechat/progress6.png" style="width:40px;margin-right:10px;display:block"></div>
		<div class="weui-cell__bd">
		<p>商户费率管理</p>
		</div>
		<div class="weui-cell__ft">
		<c:choose> 
		  <c:when test="${empty merchantRate}">   
		    	<span class="green">未设置</span>
		  </c:when> 
		  <c:otherwise>   
		    <span class="green">已添加</span>
		  </c:otherwise> 
		</c:choose> 
		</div>
	</a>
	<a class="weui-cell weui-cell_access" href="settlement">
		<div class="weui-cell__hd"><img src="${path}/resources/images/wechat/progress5.png" style="width:40px;margin-right:10px;display:block"></div>
		<div class="weui-cell__bd">
		<p>结算账户管理</p>
		</div>
		<div class="weui-cell__ft">
			<c:if test="${status=='0' or status=='1' }">
				<span class="red">审核中</span>
			</c:if>
			<c:if test="${status=='4' or status=='2'}">
				<span class="red">审核不通过</span>
			</c:if>
			<c:if test="${status=='3' }">
				<span class="green">已认证</span>
			</c:if>
			<c:if test="${status=='5' }">
				<span class="green">已停用</span>
			</c:if>
			<c:if test="${status=='6' }">
				<span class="green">资料待完善</span>
			</c:if>
		</div>
	</a>
	<a class="weui-cell weui-cell_access" href="progress">
		<div class="weui-cell__hd"><img src="${path}/resources/images/wechat/progress7.png" style="width:40px;margin-right:10px;display:block"></div>
		<div class="weui-cell__bd">
		<p>审核进度查询</p>
		</div>
		<div class="weui-cell__ft">
			<c:if test="${status=='0' or status=='1' }">
				<span class="red">审核中</span>
			</c:if>
			<c:if test="${status=='4' or status=='2'}">
				<span class="red">审核不通过</span>
			</c:if>
			<c:if test="${status=='3' }">
				<span class="green">已认证</span>
			</c:if>
			<c:if test="${status=='5' }">
				<span class="green">已停用</span>
			</c:if>
			<c:if test="${status=='6' }">
				<span class="green">资料待完善</span>
			</c:if>
		</div>
	</a>
	<%-- <a class="weui-cell weui-cell_access" href="binding">
		<div class="weui-cell__hd"><img src="${path}/resources/images/wechat/progress4.png" style="width:40px;margin-right:10px;display:block"></div>
		<div class="weui-cell__bd">
		<p>绑定手机号</p>
		</div>
		<div class="weui-cell__ft"><span class="red">未绑定</span></div>
	</a> --%>
</div>

<div class="weui-msg__opr-area">
	<p class="weui-btn-area">
	  <a href="javascript:void(0);" class="weui-btn weui-btn_primary" id="logout">退出账号</a>
	</p>
</div>


<%-- <div class="weui_cells weui_cells_access">
	<a class="weui_cell" href="basic">
		<div class="weui_cell_bd weui_cell_primary">
		<p>基本信息管理</p>
		</div>
		<div class="weui_cell_ft"></div>
	</a>
	<a class="weui_cell" href="settlement">
		<div class="weui_cell_bd weui_cell_primary">
		<p>结算账户管理</p>
		</div>
		<div class="weui_cell_ft"></div>
	</a>
	<a class="weui_cell" href="merchantRate">
		<div class="weui_cell_bd weui_cell_primary">
		<p>商户费率管理</p>
		</div>
		<div class="weui_cell_ft"></div>
	</a>
	<a class="weui_cell" href="progress">
		<div class="weui_cell_bd weui_cell_primary">
		<p>入驻认证</p>
		</div>
		<div class="weui_cell_ft">
		<!-- 0 申请中 1 上级审核通过 2 上级审核拒绝 3平台审核通过 4 平台审核拒绝 5 停用 -->
		<c:if test="${status=='0' or status=='1' }">
			<span class="red">审核中</span>
		</c:if>
		<c:if test="${status=='4' or status=='2'}">
			<span class="red">审核不通过</span>
		</c:if>
		<c:if test="${status=='3' }">
			<span class="green">已认证</span>
		</c:if>
		<c:if test="${status=='5' }">
			<span class="green">已停用</span>
		</c:if>
		<c:if test="${status=='6' }">
			<span class="green">资料待完善</span>
		</c:if>
		</div>
	</a>
	<!-- <a class="weui_cell" href="binding">
		<div class="weui_cell_bd weui_cell_primary">
		<p>绑定手机号</p>
		</div>
		<div class="weui_cell_ft"></div>
	</a> -->
</div> --%>





<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/wechat/set.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>

</body></html>