<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="page" uri="page" %> 
<%
   String path =  request.getContextPath();
request.setAttribute("path", path);
%>
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>新增商家活动</title>
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
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"/>
<link rel="shortcut icon" href="${currentChannelInfo.icon }"/>
<style type="text/css">
.td0{
	text-align: left;
	font-weight: bolder;
}
.portlet span{
	line-height: 14px;
	margin-right: 2px;
	margin-bottom: 2px;
}
#popPage .portlet.box.green-haze{
	background-color: #ccc;
	height: 1px;
	line-height: 1px;
	margin: 10px 0;
}
</style>
</head>
<body class="page-header-fixed page-quick-sidebar-over-content">
<jsp:include page="../include/head.jsp"></jsp:include>
<!-- BEGIN CONTAINER -->
<div class="page-container">
<jsp:include page="../include/menu.jsp"></jsp:include>
	<section id="ucenterSection">
		<div class="portlet box ">
			<div class="portlet-body form">
				<form class="form-horizontal" id="popPage">
					<input type="hidden" id="type"  name = "type" value="${type}">
					<div class="row" id="row">
						<div class="col-md-9">
							<c:forEach var="item" varStatus="idd" items="${list}"> 
								<div class="row" id="row">
									<input type="hidden" id="index" name="index" value="${idd.index}">
									<input type="hidden" class="id" id="id" name="id" value="${item.id}">
									<%-- <label class="control-label col-md-2">活动模式${idd.index+1}:</label> --%>
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label col-md-4 discount" style="width:79px;">活动优惠</label>
											<div class="col-md-8">
												<input type="text" value="${item.activityDiscount}" class="form-control from"  placeholder="活动优惠">
											</div>
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label col-md-4 condition" style="width:79px;">活动条件</label>
											<div class="col-md-8">
												<input type="text" value="${item.activityCondition}" class="form-control to" onkeyup="value=value.replace(/[^\d.]/g,'')"  onpaste="value=value.replace(/[^\d.]/g,'')" placeholder="活动条件">
											</div>
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label col-md-4 chance" style="width:79px;">活动概率</label>
											<div class="col-md-8">
												<input type="text" value="${item.chance}" class="form-control odds" onkeyup="value=value.replace(/[^\d.]/g,'')" onpaste="value=value.replace(/[^\d.]/g,'')" placeholder="活动概率"> 
											</div>
										</div>
									</div>
									<!-- <div class="col-md-3">
										<div class="form-group">
											<div class="col-md-8">
												<a href="#" style="font-size:24px" class="add" title="添加">+</a>&nbsp;&nbsp;
												<a href="#" style="font-size:24px" class="minus" title="删除">-</a>
											</div>
										</div>
									</div> -->
								</div>
							
							
							
							
							
							
								<%-- <div class="form-group">
									<input type="hidden" id="index" name="index" value="${idd.index}">
									<input type="hidden" class="id" id="id" name="id" value="${item.id}">
									<label class="control-label col-md-2">活动模式${idd.index+1}:</label>
									<span style="display: inline">活动模式${idd.index+1}:</span>
									<label class="control-label col-md-1">活动优惠</label>
									<div class="col-md-1">
										<input type="text" value="${item.activityDiscount}" class="form-control from"  placeholder="活动优惠" style="width:160px;display:inline">
									</div>
									<label class="control-label col-md-2">活动条件</label>
									<div class="col-md-1">
										<input type="text" value="${item.activityCondition}" class="form-control to" onkeyup="value=value.replace(/[^\d.]/g,'')"  onpaste="value=value.replace(/[^\d.]/g,'')" placeholder="活动条件" style="width:160px;display:inline">
									</div>
									<label class="control-label col-md-2">活动概率</label>
									<div class="col-md-1">
										<input type="text" value="${item.chance}" class="form-control odds" onkeyup="value=value.replace(/[^\d.]/g,'')" onpaste="value=value.replace(/[^\d.]/g,'')" placeholder="活动概率" style="width:160px;display:inline"> 
									</div>
								</div> --%>
							</c:forEach>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="portlet box green-haze"></div>
							<div class="form-group">
								<label class="control-label col-md-2"></label>
								<div class="col-md-10">
									<button type="button" class="btn green add" id="save">保存</button>
									<button type="button" class="btn default" id="cancle">取消</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</section>
</div>
<jsp:include page="../include/footerjs.jsp"></jsp:include>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<!-- 四级菜单不提供选中和三级菜单紧密绑定，在获取三级ID的时候相应的四级资源必须获取 -->
<div class="fade hide">
<c:forEach var="forth" items="${forthMap}" varStatus="statusforth">
	<div id="forth${forth.key}">
	<c:forEach var="forthMenu" items="${forth.value}" varStatus="statusFF"> 
		<input type="hidden" id="text${forthMenu.id}" value="${forthMenu.id}"/>
	</c:forEach>
	</div>
</c:forEach>
</div>

<script src="${path}/resources/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/bootstrap-select.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/ajaxfileupload.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/common.js" type="text/javascript"></script>
<script type="text/javascript">
	var baseurl = "${path}";
</script>
<script src="${path}/resources/scripts/jquery.validate.js" type="text/javascript"></script>

<script src="${path}/resources/scripts/admin/merchantactivity/editDiscount.js?v=03" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>
