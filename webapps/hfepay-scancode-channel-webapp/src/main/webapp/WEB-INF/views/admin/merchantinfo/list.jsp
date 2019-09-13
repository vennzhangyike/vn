<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="page" uri="page" %> 
<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>MerchantInfo Manager</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<jsp:include page="../include/commoncss.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/select2/select2.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/datatables/extensions/Scroller/css/dataTables.scroller.min.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/datatables/extensions/ColReorder/css/dataTables.colReorder.min.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/styles/imgZoom.css"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL STYLES -->
<link rel="shortcut icon" href="${currentChannelInfo.icon }"/>
<style type="text/css">
.td0{
	text-align: right;
	font-weight: bolder;
}
.portlet span{
	line-height: 14px;
	margin-right: 2px;
	margin-bottom: 2px;
}
</style>
</head>
<body class="page-header-fixed page-quick-sidebar-over-content">
<%-- <jsp:include page="../include/head.jsp"></jsp:include> --%>
<!-- BEGIN CONTAINER -->
<div class="page-container">
<%-- <jsp:include page="../include/menu.jsp"></jsp:include> --%>
	<!-- BEGIN CONTENT -->
	<section id="ucenterSection">
			<%-- <div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="${path}/adminManage/index">首页</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">商户管理</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">商户基本信息列表</a>
					</li>
				</ul>
			</div> --%>
			<!-- END PAGE HEADER-->
			
			<!-- BEGIN SEARCH CONDITION -->
			<div class="portlet box ">
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal" id="form">
						<div class="form-body">
							<div class="row">
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">代理商编号</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="agentNo" name="agentNo">
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">商户编号</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="merchantNo" name="merchantNo">
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">商户名称</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="merchantName" name="merchantName">
										</div>
									</div>
								</div>
								<%-- <div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">渠道名称</label>
										<div class="col-md-8">
											<select id = "channelNo" class="form-control" name="channelNo">
												<option value = "">---   请选择   ---</option>
												<c:forEach var="obj" items="${channels}">
													<option value = "${obj.channelNo}">${obj.channelName}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div> --%>
								<%-- <div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">代理商名称</label>
										<div class="col-md-8">
											<select id = "agentNo" class="form-control" name="agentNo">
												<option value = "">---   请选择   ---</option>
												<c:forEach var="obj" items="${agents}">
													<option val ="${obj.channelNo}" value = "${obj.agentNo}">${obj.agentName}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div> --%>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">商户状态</label>
										<div class="col-md-8">
											<select id = "status" class="form-control" name="status">
												<option value = "">  --  请选择  --  </option>
												<option value = "6">初始</option>
												<option value = "0">申请中</option>
												<option value = "1">上级审核通过</option>
												<option value = "2">上级审核拒绝</option>
												<option value = "3">平台审核通过</option>
												<option value = "4">平台审核拒绝</option>
												<option value = "5">禁用</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">实名认证</label>
										<div class="col-md-8">
											<select id = "authenStatus" class="form-control" name="authenStatus">
												<option value = "">  --  请选择  --  </option>
												<option value = "0">未认证</option>
												<option value = "1">已认证</option>
												<option value = "2">认证失败</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">身份证号码</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="idCard" name="idCard">
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">法人姓名</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="name" name="name">
										</div>
									</div>
								</div>
							</div>
							<div  class="row">
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">开始时间</label>
										<div class="col-md-8">
											<input type="text" class="form-control date-picker" id="beginTimeStr" name="beginTimeStr">
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">结束时间</label>
										<div class="col-md-8">
											<input type="text" class="form-control date-picker" id="endTimeStr" name="endTimeStr" >
										</div>
									</div>
								</div>	
								</div>
							<div class="portlet box green-haze">
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-1"></label>
										<div class="col-md-10">
											<button type="button" class="btn green" id="query">查询</button>
											<button type="button" class="btn default" id="cancle">重置</button>
											<%-- <button type="button" class="btn btn-default" onClick="javascript:window.location.href='${path}/adminManage/merchantinfo/0'">新增商户</a></button> --%>
										</div>
									</div>
								</div>
								<!--/span-->
							</div>
							
						</div>
					</form>
					<!-- END FORM-->
				</div>
			</div>
			<!-- END SEARCH CONDITION -->
			
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box green-haze">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-globe"></i>基本信息列表
							</div>
						</div>
						<div class="portlet-body" id="tablec">
							
						</div>
						<!-- <div class="portlet-body" id="tablec1">
							
						</div> -->
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
			<!-- END PAGE CONTENT-->
	</section>
	<!-- END CONTENT -->
</div>
<!-- END CONTAINER -->
<jsp:include page="../include/footerjs.jsp"></jsp:include>
<!-- BEGIN PAGE LEVEL PLUGINS -->

<script src="${path}/resources/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/ajaxfileupload.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/common.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/base.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/imgZoom.js" type="text/javascript"></script>

<script type="text/javascript" src="${path}/resources/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="${path}/resources/assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js" charset="UTF-8"></script>

<script src="${path}/resources/scripts/admin/merchantinfo/list.js?rnd=${version}" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>