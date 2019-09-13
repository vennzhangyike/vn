<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="page" uri="page" %> 
<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<head>
<meta charset="utf-8"/>
<title>MerchantWallet Manager</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<jsp:include page="../include/commoncss.jsp"></jsp:include>
<link rel="shortcut icon" href="../favicon.ico"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/select2/select2.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/datatables/extensions/Scroller/css/dataTables.scroller.min.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/datatables/extensions/ColReorder/css/dataTables.colReorder.min.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css"/>
<link rel="shortcut icon" href="../favicon.ico"/>
<style type="text/css">
.td0{
	text-align: right;
	font-weight: bolder;
}
</style>
</head>
<body class="page-header-fixed page-quick-sidebar-over-content">
<%-- <jsp:include page="../include/head.jsp"></jsp:include> --%>
<!-- BEGIN CONTAINER -->
<div class="page-container">
<%-- <jsp:include page="../include/menu.jsp"></jsp:include> --%>
<section id="ucenterSection">
			<%-- <div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="${path}/adminManage/index">首页</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">统计管理</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">交易次数统计</a>
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
										<label class="control-label col-md-4">日期</label>
										<div class="col-md-8">
											<input type="text" class="form-control date-picker" id="startDate" name="startDate" readonly="readonly">
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">渠道编号</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="channelNo" name="channelNo">
										</div>
									</div>
								</div>
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
								<!--/span-->
							</div>
							<div class="portlet box green-haze">
													
						</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2"></label>
										<div class="col-md-10">
											<button type="button" class="btn green" id="query">查询</button>
											<button type="button" class="btn default" id="cancle">重置</button>
										<!--  	<button type="button" class="btn btn-default" onClick="javascript:window.location.href='${path}/adminManage/merchantwallet/0'">新增-MerchantWallet-</a></button>
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
								<i class="fa fa-globe"></i>商户余额列表
							</div>
						</div>
						<div class="row">
							<div class="col-md-2"><label class="control-label col-md-6">&nbsp;</label><label class="control-label">日期纬度</label></div>
							<div class="col-md-10">
								<span class="label label-success dateStr" style="cursor: pointer;" val="D">日</span>&nbsp;&nbsp;&nbsp;
								<span class="label label-info dateStr" style="cursor: pointer;" val="W">周</span>&nbsp;&nbsp;&nbsp;
								<span class="label label-info dateStr" style="cursor: pointer;" val="M">月</span>&nbsp;&nbsp;&nbsp;
								<span class="label label-info dateStr" style="cursor: pointer;" val="Y">年</span>
								<span class="label label-success export" style="cursor: pointer;float: right;">导出excel</span>
							</div>
					</div>
					<div id="main" style="width: 100%;height:400px;"></div>
						<!-- <div class="portlet-body" id="tablec1">
							
						</div> -->
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
			<!-- END PAGE CONTENT-->
	</section>
			<!-- END PAGE CONTENT-->
	<!-- END CONTENT -->
	<jsp:include page="../include/footerjs.jsp"></jsp:include>
	<div style="display: none;" id="baseUrl">${path}</div>
</div>
</body>
<%-- <jsp:include page="../include/footer.jsp"></jsp:include> --%>
<script src="${path}/resources/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/common.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/base.js" type="text/javascript"></script>
<script type="text/javascript" src="${path}/resources/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="${path}/resources/assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js" charset="UTF-8"></script>
<script src="${path}/resources/scripts/echarts/echarts.min.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/echarts/macarons.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/admin/statistic/orderStatic.js?rnd=${version}" type="text/javascript"></script>
