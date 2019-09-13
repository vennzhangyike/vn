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
<title>站内信</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<jsp:include page="../include/commoncss.jsp"></jsp:include>
<link rel="shortcut icon" href="${currentChannelInfo.icon }"/>
<script type="text/javascript" charset="utf-8" src="${path}/resources/scripts/ue/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${path}/resources/scripts/ue/ueditor.all.min.js"> </script>
<style type="text/css">
.hiddenRow{
	display: none;
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
						<a href="" onclick="javascript:history.go(-1);">-Message-管理</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<c:if test="${Obj == null}"><a href="#">-Message-新增 </a></c:if>
						<c:if test="${Obj != null}"><a href="#">-Message-更新</a></c:if>
					</li>
				</ul>
			</div> --%>
			<!-- END PAGE HEADER-->
			
			<!-- BEGIN SEARCH CONDITION -->
			<div class="portlet box ">
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form action="save" class="form-horizontal" id="form" method="post">
						<div class="form-body">
							<input type="hidden" id="id"  name = "id" value="${Obj.id}">
									<div class="row">
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">用户类型</label>
												<div class="col-md-4">
													<c:if test="${Obj == null}">
														<select  id="userType" class="form-control" name="userType">
															<option value="" selected = "selected">----请选择----</option>
																<option value = "1">商户</option>
																<option value = "2">代理商</option>
														</select>
													</c:if>
													<c:if test="${Obj != null}">
														<select  id="userType" class="form-control" name="userType">
															<option value="" selected = "selected">----请选择----</option>
																<option value = "1" <c:if test="${Obj.userType==1}">selected="selected"</c:if>>商户</option>
																<option value = "2" <c:if test="${Obj.userType==2}">selected="selected"</c:if>>代理商</option>
														</select>
													</c:if>
													<%-- <input type="text" class="form-control" id="userType"  name = "userType" value="${Obj.userType}" placeholder="用户类型"> --%>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">标题</label>
												<div class="col-md-4">
													<input type="text" class="form-control" id="title"  name = "title" value="${Obj.title}" placeholder="标题">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">内容</label>
												<div class="col-md-4">
													<script id="editorDetail" type="text/plain" style="width:1024px;height:300px;"></script>
													<input type="hidden" class="form-control" id="content"  name = "content" value='${Obj.content}'>
													<%-- <input type="text" class="form-control" id="content"  name = "content" value="${Obj.content}" placeholder="内容"> --%>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">类型</label>
												<div class="col-md-4">
													<c:if test="${Obj == null}">
														<select  id="messageType" class="form-control" name="messageType">
															<option value="" selected = "selected">----请选择----</option>
																<option value = "1">普通消息</option>
																<option value = "2">系统消息</option>
																<option value = "3">广告消息</option>
														</select>
													</c:if>
													<c:if test="${Obj != null}">
														<select  id="messageType" class="form-control" name="messageType">
															<option value="" selected = "selected">----请选择----</option>
																<option value = "1" <c:if test="${Obj.messageType==1}">selected="selected"</c:if>>普通消息</option>
																<option value = "2" <c:if test="${Obj.messageType==2}">selected="selected"</c:if>>系统消息</option>
																<option value = "3" <c:if test="${Obj.messageType==3}">selected="selected"</c:if>>广告消息</option>
														</select>
													</c:if>
													<%-- <input type="text" class="form-control" id="messageType"  name = "messageType" value="${Obj.messageType}" placeholder="类型"> --%>
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

<script src="${path}/resources/scripts/admin/message/edit.js?rnd=${version}" type="text/javascript"></script>
<script src="${path}/resources/scripts/jquery.validate.js" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>