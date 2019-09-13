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
<title>结算对账单</title>
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
<link rel="stylesheet" type="text/css" href="${path}/resources/styles/bootstrap-select.css"/>
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
#formDetail .portlet.box.green-haze{
	background-color: #ccc;
	height: 1px;
	line-height: 1px;
	margin: 10px 0;
}
</style>
</head>
<body class="page-header-fixed page-quick-sidebar-over-content">
<!-- BEGIN CONTAINER -->
<div class="page-container">
			<!-- BEGIN SEARCH CONDITION -->
			<div class="portlet box ">
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal"  id="formDetail">
						<input type="hidden" class="form-control" id="identityNo" name="identityNo" value="${organNo}">
						<input type="hidden" class="form-control" id="transDate" name="transDate" value="${settleDate}">
						<input type="hidden" class="form-control" id="transDateEnd" name="transDateEnd" value="${settleDate}">
						<div class="form-body">
							<!-- <h3 class="form-section">查询</h3> -->
							<!--/row-->
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">商户名称</label>
										<div class="col-md-8">
											<select id="merchantNo" name="merchantNo" class="form-control selectpicker show-tick" data-live-search="true">
												<option value=''>  --  请选择  --  </option>
									        </select>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">交易类型</label>
										<div class="col-md-8">
											<select id = "tradeType" class="form-control" name="tradeType">
												<option value = "">  --  请选择  --  </option>
												<option value = "02">交易</option>
												<option value = "01">提现</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-4">支付方式</label>
										<div class="col-md-8">
											<select id = "payCode" class="form-control" name="payCode">
												<option value = "">  --  请选择  --  </option>
												<c:forEach var="item" items="${map}">
													<option value = "${item.key}">${item.value}</option>
												</c:forEach>
											</select>		
										</div>
									</div>
								</div>								
								<!--/span-->
							</div>
							<div class="portlet box green-haze">
													
							</div>
							<div class="row">
								<div class="col-md-8">
									<div class="form-group">
										<label class="control-label col-md-2"></label>
										<div class="col-md-10">
											<button type="button" class="btn green" id="queryDetail">查询</button>
											<!-- <button type="button" class="btn default" id="cancle1">重置</button> -->
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
								<i class="fa fa-globe"></i>分润明细列表
							</div>
						</div>
						<div class="portlet-body" id="tablecDetail">
							
						</div>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
			<!-- END PAGE CONTENT-->
	<!-- END CONTENT -->
</div>
<!-- END CONTAINER -->
<!-- BEGIN PAGE LEVEL PLUGINS -->

<script src="${path}/resources/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/ajaxfileupload.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${path}/resources/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="${path}/resources/assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js" charset="UTF-8"></script>
<div style="display: none;" id="baseUrl">${path}</div>
<script src="${path}/resources/scripts/bootstrap-select.js" type="text/javascript"></script>

<script src="${path}/resources/scripts/admin/hierarchicalsettlementtotal/listDetail.js?rnd=${version}" type="text/javascript"></script>
</body>
<!-- END BODY -->
</html>