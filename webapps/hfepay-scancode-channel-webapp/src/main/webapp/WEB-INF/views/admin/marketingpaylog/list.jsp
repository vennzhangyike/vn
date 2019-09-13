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
<link rel="stylesheet" type="text/css" href="${path}/resources/styles/trans.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/styles/bootstrap-select.css"/>

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
<link rel="shortcut icon" href="${currentChannelInfo.icon }"/>
</head>
<body class="page-header-fixed page-quick-sidebar-over-content">
<!-- BEGIN CONTAINER -->
<div class="noprint">
<div class="page-container">
<section id="ucenterSection">
			<!-- BEGIN SEARCH CONDITION -->
			<div class="portlet box ">
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal" id="form" >
						<div class="form-body">
							<!-- <h3 class="form-section">查询</h3> -->
							<!--/row-->
							<div class="row">
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">商户编号</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="merchant_no" name="merchant_no" value="${merchant_no}">
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">优惠类型</label>
										<div class="col-md-8">
											<input type="text" class="form-control " id="discount_type"  name="discount_type">
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">收银员编号</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="cashier_no" name="cashier_no">
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">创建日期:</label>
										<div class="col-md-8">
											<input type="text" class="form-control date-picker" name="create_time" id="create_time">
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
											<!-- <button type="button" class="btn default" id="export">导出</button> -->
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
						<!-- <span class="label label-success export" style="cursor: pointer;float: right;">导出excel</span> -->
						<div class="portlet-body" id="tablec">
							
						</div>
						<!-- <div class="portlet-body" id="tablec1">
							
						</div> -->
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
			<!-- END PAGE CONTENT-->
	<!-- END CONTENT -->
	</section>
</div>
<!-- END CONTAINER -->
<jsp:include page="../include/footerjs.jsp"></jsp:include>
<!-- BEGIN PAGE LEVEL PLUGINS -->
</div>

<div id="printDiv" style="display:none;">
<!--startprint1-->
<b><div style="text-align:center;font-size:16pt;font-family:新宋体;whith:240pt;" id="merchantNameStr" ></div></b>
<b><div style="text-align:center;font-size:10pt;font-family:新宋体;" id="phoneStr" >电话：</div></b>
<div style="text-align:center;font-size:10pt;font-family:新宋体;">******************************</div>
<div style="font-size:9pt;font-family:新宋体;" id="merchantNoStr" >商户号：</div>
<div style="font-size:9pt;font-family:新宋体;" id="payCodeStr" >付款方式：</div>
<div style="font-size:9pt;font-family:新宋体;" id="tradeNoStr" >订单号：</div>
<div style="font-size:9pt;font-family:新宋体;" id="orderStatusStr" >交易状态：</div>
<div style="font-size:9pt;font-family:新宋体;" id="orderTimeStr" >交易日期：</div>
<div style="font-size:9pt;font-family:新宋体;" id="cashierNoStr" >操作员号：</div>
<div style="text-align:center;font-size:10pt;font-family:新宋体;">______________________________</div>
<b><div style="font-size:12pt;font-family:新宋体;" id="orderAmtStr" >实收 RMB：</div></b>
<div style="text-align:center;font-size:10pt;font-family:新宋体;">******************************</div>
<b><div style="text-align:center;font-size:10pt;font-family:新宋体;">请妥善保管好购物凭证</div></b>
<b><div style="text-align:center;font-size:12pt;font-family:新宋体;">多谢惠顾</div></b>
<div style="text-align:center;font-size:10pt;font-family:新宋体;">&nbsp;</div>
<div style="text-align:center;font-size:10pt;font-family:新宋体;">- - - - - - - - - - - - - - -</div>
<!--endprint1--> 
</div>


<script src="${path}/resources/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/ajaxfileupload.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${path}/resources/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="${path}/resources/assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js" charset="UTF-8"></script>
<script type="text/javascript">
	var baseurl = "${path}";
</script>
<script src="${path}/resources/scripts/admin/marketingpaylog/list.js?rnd=${version}" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>