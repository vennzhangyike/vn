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

</style>
</head>
<body class="page-header-fixed page-quick-sidebar-over-content">
<jsp:include page="../include/head.jsp"></jsp:include>
<!-- BEGIN CONTAINER -->
<div class="page-container">
<jsp:include page="../include/menu.jsp"></jsp:include>
	<!-- BEGIN CONTENT -->
	<section id="ucenterSection">
			<div class="page-bar">
			</div>
			<!-- END PAGE HEADER-->
			
			<!-- BEGIN SEARCH CONDITION -->
			<div class="portlet box ">
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form action="save" class="form-horizontal" id="form" method="post">
						<div class="form-body">
							<input type="hidden" id="id"  name = "id" value="${Obj.id}">
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">活动开始日期</label>
												<div class="col-md-4">
													<input type="text" class="form-control date-picker" id="beginDate" name="beginDate" value="<fmt:formatDate value='${Obj.activityBeginTime}' pattern='yyyy-MM-dd hh:mm'/>" >
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">活动结束日期</label>
												<div class="col-md-4">
													<input type="text" class="form-control date-picker" id="endDate" name="endDate" value="<fmt:formatDate value='${Obj.activityEndTime}' pattern='yyyy-MM-dd hh:mm'/>">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">活动ID</label>
												<div class="col-md-4">
													<c:if test="${Obj == null}">
														<input type="text" class="form-control" id="activityId"  name = "activityId" value="${activityId}" placeholder="活动ID" readonly="readonly">
													</c:if>
													<c:if test="${Obj != null}">
														<input type="text" class="form-control" id="activityId"  name = "activityId" value="${Obj.activityId}" placeholder="活动ID" readonly="readonly">
													</c:if>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">活动类型</label>
												<div class="col-md-4">
													<%-- <input type="text" class="form-control" id="activityType"  name = "activityType" value="${Obj.activityType}" placeholder="活动类型 0 折扣 1 满减 3 随机"> --%>
													<c:if test="${Obj == null}">
														<select  id="activityType" class="form-control" name="activityType">
															<option value="" selected = "selected">----请选择----</option>
															<option value = "0">折扣</option>
															<option value = "1">满减</option>
															<option value = "2">随机</option>
														</select>
													</c:if>
													<c:if test="${Obj != null}">
														<select  id="activityType" class="form-control" name="activityType" disabled="disabled">
															<option value="" selected = "selected">----请选择----</option>
															<option value = "0" <c:if test="${Obj.activityType==0}">selected="selected"</c:if> ">折扣</option>
															<option value = "1" <c:if test="${Obj.activityType==1}">selected="selected"</c:if> ">满减</option>
															<option value = "2" <c:if test="${Obj.activityType==2}">selected="selected"</c:if> ">随机</option>
														</select>
													</c:if>
												</div>
											</div>
										</div>
									</div>
									
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">活动描述</label>
												<div class="col-md-4">
													<input type="text" class="form-control" id="activityContent"  name = "activityContent" value="${Obj.activityContent}" placeholder="活动描述">
												</div>
											</div>
										</div>
									</div>
									
							<div class="row">
								<div class="col-md-12">
									<!-- BEGIN EXAMPLE TABLE PORTLET-->
									<div class="portlet box green-haze"></div>
									<div class="form-group">
										<label class="control-label col-md-2"></label>
										<div class="col-md-10">
											<button type="submit" class="btn green" id="save">保存</button>
											<button type="button" class="btn default" id="cancle">取消</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</form>
					<!-- END FORM-->
				</div>
			</div>
			<!-- END SEARCH CONDITION -->
	</section>
</div>
<!-- END CONTENT -->
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
<script type="text/javascript" src="${path}/resources/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="${path}/resources/assets/global/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="${path}/resources/scripts/jquery.validate.js" type="text/javascript"></script>

<script src="${path}/resources/scripts/admin/merchantactivity/edit.js" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>