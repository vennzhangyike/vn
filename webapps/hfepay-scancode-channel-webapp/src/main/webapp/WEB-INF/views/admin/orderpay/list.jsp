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
<title>支付订单查询</title>
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
						<a href="#">支付订单查询</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">支付订单列表</a>
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
										<label class="control-label col-md-4">交易订单编号</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="tradeNo" name="tradeNo">
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
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">支付订单编号</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="payNo" name="payNo">
										</div>
									</div>
								</div>
							</div>
							<div class="row">								
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
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">支付状态</label>
										<div class="col-md-8">
											<select id = "payStatus" class="form-control" name="payStatus">
												<option value = "">  --  请选择  --  </option>
												<option value = "00">等待支付</option>
												<option value = "01">渠道支付中</option>
												<option value = "02">支付成功</option>
												<option value = "03">支付失败</option>
											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="portlet box green-haze">
									<select id = "payCode" class="form-control" name="payCode" style="display:none;">
										<option value = "">  --  请选择  --  </option>
										<c:forEach var="item" items="${mappingDicionList}">
											<option value = "${item.paraVal}">${item.paraName}</option>
										</c:forEach>
									</select>				
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2"></label>
										<div class="col-md-10">
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
								<i class="fa fa-globe"></i>支付订单列表
								<span class="label label-success export" style="cursor: pointer;float: right;">导出excel</span>
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
<script type="text/javascript" src="${path}/resources/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="${path}/resources/assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js" charset="UTF-8"></script>
<script type="text/javascript">
	var baseurl = "${path}";
</script>
<script src="${path}/resources/scripts/admin/orderpay/list.js?rnd=${version}" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>