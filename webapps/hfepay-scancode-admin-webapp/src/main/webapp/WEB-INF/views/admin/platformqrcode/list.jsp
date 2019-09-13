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
<title>二维码管理</title>
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
<!-- BEGIN PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL STYLES -->
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
						<a href="${path }/adminManage/index">首页</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">二维码管理</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">二维码管理列表</a>
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
<!-- 							<h3 class="form-section">查询</h3> -->
							<!--/row-->
							<div class="row">
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">渠道编号</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="channelNo" name="channelNo">
										</div>
									</div>
								</div>
								<!-- <div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">渠道名称</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="channelName" name="channelName">
										</div>
									</div>
								</div> -->
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">代理商编号</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="agentNo" name="agentNo">
										</div>
									</div>
								</div>
								<!-- <div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">代理商名称</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="agentName" name="agentName">
										</div>
									</div>
								</div> -->
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">商户编号</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="merchantNo" name="merchantNo">
										</div>
									</div>
								</div>
								<!-- <div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">商户名称</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="merchantName" name="merchantName">
										</div>
									</div>
								</div> -->
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">使用状态</label>
										<div class="col-md-8">
											<select id = "useStatus" class="form-control" name="useStatus">
												<option value = "">  --  请选择  --  </option>
												<option value = "2">未使用</option>
												<option value = "1">已使用</option>
											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<!-- <div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">商户编号</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="merchantNo" name="merchantNo">
										</div>
									</div>
								</div> -->
								<!-- <div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">商户名称</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="merchantName" name="merchantName">
										</div>
									</div>
								</div> -->
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">二维码编号</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="qrCode" name="qrCode">
										</div>
									</div>
								</div>
								<!-- <div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">使用状态</label>
										<div class="col-md-8">
											<select id = "useStatus" class="form-control" name="useStatus">
												<option value = "">  --  请选择  --  </option>
												<option value = "2">未使用</option>
												<option value = "1">已使用</option>
											</select>
										</div>
									</div>
								</div> -->
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">状态</label>
										<div class="col-md-8">
											<select id = "qrStatus" class="form-control" name="qrStatus">
												<option value = "">  --  请选择  --  </option>
												<option value = "2">禁用</option>
												<option value = "1">启用</option>
											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="portlet box green-haze">
													
							</div>
							<div class="row">
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4"></label>
										<div class="col-md-8">
											<button type="button" class="btn green" id="query">查询</button>
											<button type="button" class="btn default" id="cancle">重置</button>
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
								<i class="fa fa-globe"></i>二维码管理列表
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
<script src="${path}/resources/scripts/qrcode/jquery.qrcode.js?rnd=${version}" type="text/javascript"></script>
<script src="${path}/resources/scripts/qrcode/qrcode.js?rnd=${version}" type="text/javascript"></script>
<script src="${path}/resources/scripts/qrcode/drawQrCode.js?rnd=${version}" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
<script src="${path}/resources/scripts/admin/platformqrcode/list.js?rnd=${version}" type="text/javascript"></script>
</body>
<!-- END BODY -->
</html>