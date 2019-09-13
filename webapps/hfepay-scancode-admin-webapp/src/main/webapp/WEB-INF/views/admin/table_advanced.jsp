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
<title>Metronic | Data Tables - Advanced Datatables</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<jsp:include page="include/commoncss.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${path }/resources/assets/global/plugins/select2/select2.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/assets/global/plugins/datatables/extensions/Scroller/css/dataTables.scroller.min.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/assets/global/plugins/datatables/extensions/ColReorder/css/dataTables.colReorder.min.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" type="text/css" href="${path }/resources/assets/global/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css"/>
<!-- END PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL STYLES -->
<link rel="shortcut icon" href="favicon.ico"/>
</head>
<body class="page-header-fixed page-quick-sidebar-over-content">
<jsp:include page="include/head.jsp"></jsp:include>
<!-- BEGIN CONTAINER -->
<div class="page-container">
<jsp:include page="include/menu.jsp"></jsp:include>
	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<div class="page-content">
			<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<div class="modal fade" id="portlet-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
							<h4 class="modal-title">Modal title</h4>
						</div>
						<div class="modal-body">
							 Widget settings form goes here
						</div>
						<div class="modal-footer">
							<button type="button" class="btn blue">Save changes</button>
							<button type="button" class="btn default" data-dismiss="modal">Close</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
			<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<!-- BEGIN PAGE HEADER-->
			<h3 class="page-title">
			Advanced Datatables <small>advanced datatables</small>
			</h3>
			<div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="dashbord">Home</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">Data Tables</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">Advanced Datatables</a>
					</li>
				</ul>
			</div>
			<!-- END PAGE HEADER-->
			
			<!-- BEGIN SEARCH CONDITION -->
			<div class="portlet box ">
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal">
						<div class="form-body">
							
							<h3 class="form-section">Search</h3>
							<!--/row-->
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-3">userName</label>
										<div class="col-md-9">
											<input type="text" class="form-control">
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-3">sex:</label>
										<div class="col-md-9">
											<select class="form-control">
												<option value="">Male</option>
												<option value="">Female</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-3">sex:</label>
										<div class="col-md-9">
											<select class="form-control">
												<option value="">Male</option>
												<option value="">Female</option>
											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-3">City</label>
										<div class="col-md-9">
											<div class="input-group input-large date-picker input-daterange" data-date="10/11/2012" data-date-format="mm/dd/yyyy">
												<input type="text" class="form-control" name="from">
												<span class="input-group-addon">
												to </span>
												<input type="text" class="form-control" name="to">
											</div>
										</div>
									</div>
								</div>
								<!--/span-->
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-3">State</label>
										<div class="col-md-9">
											<input type="text" class="form-control">
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-3"></label>
										<div class="col-md-9">
											<button type="button" class="btn green">Submit</button>
											<button type="button" class="btn default">Cancel</button>
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
								<i class="fa fa-globe"></i>Columns Reorder
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
</div>
<!-- END CONTAINER -->
<jsp:include page="include/footer.jsp"></jsp:include>
<!-- BEGIN PAGE LEVEL PLUGINS -->

<!-- 查看详情弹出框 -->
<!-- responsive -->
<div id="responsive" style="top:20%;" class="modal fade" tabindex="-1" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">用户内容详情</h4>
			</div>
			
			
			<div class="dxr_table ma10">
				<table class="table table-striped table-bordered table-hover">
					<tbody>
						<tr>
							<td class="t_t"  width="120px">用管理管理管理</td>
							<td>1231313131</td>
							<td class="t_t"  width="120px">张理</td>
							<td>454545454554</td>
						</tr>
						<tr>
							<td class="t_t">用户管理</td>
							<td>1231313131</td>
							<td class="t_t">张管理</td>
							<td>454545454554</td>
						</tr>
						<tr>
							<td class="t_t">用户管理</td>
							<td>1231313131</td>
							<td class="t_t">张三理</td>
							<td>454545454554</td>
						</tr>
						<tr>
							<td class="t_t">用户管理</td>
							<td>1231313131</td>
							<td class="t_t">管理</td>
							<td>454545454554</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn default">Close</button>
				<button type="button" class="btn green">Save changes</button>
			</div>
		</div>
	</div>
</div>



<!-- 编辑 -->
<div id="edit" style="top:20%;" class="modal fade" tabindex="-1" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog">
		<div class="modal-content form-horizontal">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">修改用户信息</h4>
			</div>
			<div class="dxr_table ma10">
				<table class="table table-striped table-bordered table-hover">
					<tbody>
						<tr>
							<td class="t_t"  width="120px">心情</td>
							<td><input type="text" class="form-control" value="今天天气很好呀"></td>
							<td class="t_t"  width="120px">标准</td>
							<td>
								<div class="radio-list">
										<label class="radio-inline">
										<div class="radio"><input type="radio" name="optionsRadios2" value="option1"></div>
										Free </label>
										<label class="radio-inline">
										<div class="radio"><input type="radio" name="optionsRadios2" value="option2" checked=""></div>
										Professional </label>
									</div>
							</td>
						</tr>
						<tr>
							<td class="t_t">性别</td>
							<td>
									<select class="form-control">
										<option value="">Male</option>
										<option value="">Female</option>
									</select>
							</td>
							<td class="t_t">爱好</td>
							<td>
								<div class="checkbox-list">
										<label class="checkbox-inline">
										ball:<input type="checkbox" id="alert_close" value="1" checked="">
										</label>
									<label class="checkbox-inline">
										ball:<input type="checkbox" id="alert_close" value="1" checked="">
										</label>
										<label class="checkbox-inline">
										ball:<input type="checkbox" id="alert_close" value="1" checked="">
										</label>
									</div>	
							</td>
						</tr>
						<tr>
							<td class="t_t">用户管理</td>
							<td>1231313131</td>
							<td class="t_t">张三理</td>
							<td>454545454554</td>
						</tr>
						<tr>
							<td class="t_t">用户管理</td>
							<td>1231313131</td>
							<td class="t_t">管理</td>
							<td>454545454554</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn default">Close</button>
				<button type="button" class="btn green">Save changes</button>
			</div>
		</div>
	</div>
</div>

<!-- END PAGE LEVEL PLUGINS -->
<script src="${path }/resources/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${path }/resources/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${path }/resources/assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="${path }/resources/assets/admin/layout/scripts/demo.js" type="text/javascript"></script>

<script type="text/javascript" src="${path }/resources/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script>
jQuery(document).ready(function() {       
   Metronic.init(); // init metronic core components
Layout.init(); // init current layout
QuickSidebar.init(); // init quick sidebar
Demo.init(); // init demo features
   //TableAdvanced.init();
//UIAlertDialogApi.init();
$('.date-picker').datepicker({
    rtl: Metronic.isRTL(),
    orientation: "left",
    autoclose: true
});
toPage(1);
});


//弹出框,ajax加载完毕之后才显示文本框
function detailPop(){
	$.sendReq({
		   type: "POST",
		   url: "page/viewDetail",
		   success: function(msg){
		     $("#responsive").modal('toggle');
		   }
		});
}

/**
 * 确认框
 */
function del(obj){
	bootbox.setLocale("zh_CN");  //设置按钮为中文
      bootbox.confirm("确定执行该操作吗?", function(result) {
    	  if(result){
    		  bootbox.alert("删除成功!");  //弹出框
    	  }
      }); 
}

function toPage(pageNumber){
	$.sendReq({
		   type: "POST",
		   url: "showPages?page="+pageNumber+"&rows=2",
		   success: function(msg){
		     $("#tablec").html(msg);
		   }
		});
}
</script>
</body>
<!-- END BODY -->
</html>