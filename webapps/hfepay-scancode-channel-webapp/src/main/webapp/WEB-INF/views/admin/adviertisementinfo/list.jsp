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
<style type="text/css">
.td0{
	text-align: right;
	font-weight: bolder;
}
</style>
<head>
<meta charset="utf-8"/>
<title>AdviertisementInfo Manager</title>
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
<link rel="stylesheet" type="text/css" href="${path}/resources/styles/bootstrap-select.css"/>

<!-- BEGIN PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL STYLES -->
<link rel="shortcut icon" href="../favicon.ico"/>
</head>
<body class="page-header-fixed page-quick-sidebar-over-content">
<!-- BEGIN CONTAINER -->
<div class="page-container">
	<section id="ucenterSection">
	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<div class="page-content">
			<!-- BEGIN SEARCH CONDITION -->
			<div class="portlet box ">
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal" id="form">
						<div class="form-body">
							
							<!--/row-->
							<div class="row">
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">广告编号</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="adviertNo" name="adviertNo">
										</div>
									</div>
								</div>
								<!-- <div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">机构名称</label>
										<div class="col-md-8">
											<select id="organNo" name="organNo" class="form-control selectpicker show-tick" data-live-search="true">
												<option value=''>  --  请选择  --  </option>
									        </select>
										</div>
									</div>
								</div> -->
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">广告位</label>
										<div class="col-md-8">
											<select id="adviertPosition" name="adviertPosition" class="form-control">
												<option value = "">  --  请选择  --  </option>
												<option value='0'>首页广告</option>
												<option value='1'>支付成功页广告</option>
									        </select>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">优先级别</label>
										<div class="col-md-8">
											<select id="priority" name="priority" class="form-control">
												<option value = "">  --  请选择  --  </option>
												<option value='0'>最高级</option>
												<option value='1'>一级</option>
												<option value='2'>二级</option>
												<option value='3'>三级</option>
									        </select>
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">状态</label>
										<div class="col-md-8">
											<select id="status" name="status" class="form-control">
												<option value = "">  --  请选择  --  </option>
												<option value='1'>申请中</option>
												<option value='2'>审核通过</option>
												<option value='3'>审核拒绝</option>
									        </select>
										</div>
									</div>
								</div>
								<div class="col-md-3" style="display: none;">
									<div class="form-group">
										<label class="control-label col-md-4">记录状态</label>
										<div class="col-md-8">
											<select id="recordStatus" name="recordStatus" class="form-control">
												<option value = "">  --  请选择  --  </option>
												<option value='Y'>正常</option>
												<option value='N'>删除</option>
									        </select>
										</div>
									</div>
								</div>
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
											
											<button type="button" class="btn btn-default" id="newUser" onClick="javascript:window.location.href='${path}/adminManage/adviertisementinfo/0'">新增广告</button>
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
								<i class="fa fa-globe"></i>广告列表
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
		</div>
	</div>
	<!-- END CONTENT -->
	</section>
</div>
<!-- END CONTAINER -->
<jsp:include page="../include/footerjs.jsp"></jsp:include>
<!-- BEGIN PAGE LEVEL PLUGINS -->

<script src="${path}/resources/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/ajaxfileupload.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/base.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/common.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/bootstrap-select.js" type="text/javascript"></script>

<script src="${path}/resources/scripts/admin/adviertisementinfo/list.js" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>