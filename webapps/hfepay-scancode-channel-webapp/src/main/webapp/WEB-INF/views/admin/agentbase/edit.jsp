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
<title>新增代理商</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<jsp:include page="../include/commoncss.jsp"></jsp:include>
<link rel="shortcut icon" href="${currentChannelInfo.icon }"/>
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
						<a href="" onclick="javascript:history.go(-1);">代理商管理</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<c:if test="${Obj == null}"><a href="#">代理商新增 </a></c:if>
						<c:if test="${Obj != null}"><a href="#">代理商更新</a></c:if>
					</li>
				</ul>
			</div> --%>
			<!-- END PAGE HEADER-->
			
			<!-- BEGIN SEARCH CONDITION -->
			<div class="portlet box ">
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form action="" class="form-horizontal" id="form" method="post">
						<div class="form-body">
							<input type="hidden" id="id"  name = "id" value="${Obj.id}">
							<input type="hidden" id="redirectUrl"  name = "redirectUrl" value="${redirectUrl}">
									<div class="row" style="display:none;">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">渠道名称</label>
												<div class="col-md-4">
												<c:if test="${Obj == null}">
													<select  id="channelNo" class="form-control" name="channelNo">
															<c:forEach var="channelList" items="${channelList}">
																<option value = "${channelList.channelNo}" channelName = "${channelList.channelName}">${channelList.channelName}</option>
															</c:forEach>
													</select>
													</c:if>
													<c:if test="${Obj != null}">
													<select  id="channelNo" class="form-control" name="channelNo"  disabled="disabled">
															<c:forEach var="channelList" items="${channelList}">
																<option value = "${channelList.channelNo}" channelName = "${channelList.channelName}" <c:if test="${Obj.channelNo==channelList.channelNo}">selected="selected"</c:if> ">${channelList.channelName}</option>
															</c:forEach>
													</select>
													</c:if>
													<%-- <input type="text" class="form-control" id="channelNo"  name = "channelNo" value="${Obj.channelNo}" placeholder="渠道名称"> --%>
												</div>
											</div>
										</div>
									</div>
									<div class="row" style="display:none;">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">代理商级别</label>
												<div class="col-md-4">
													<c:if test="${Obj == null}">
													<select  id="agentLevel" class="form-control" name="agentLevel" >
															<c:forEach var="agentLevelList" items="${agentLevelList}" >
																<option value = "${agentLevelList.paraVal}"">${agentLevelList.paraVal} </option>
															</c:forEach>
													</select>
													</c:if>
													<c:if test="${Obj != null}">
													<select  id="agentLevel" class="form-control" name="agentLevel" disabled="disabled"> 
															<c:forEach var="agentLevelList" items="${agentLevelList}">
																<option value = "${agentLevelList.paraVal}" <c:if test="${Obj.agentLevel==agentLevelList.paraVal}">selected="selected"</c:if>">${agentLevelList.paraVal}</option>
															</c:forEach>
													</select>
													</c:if>
													<%-- <input type="text" class="form-control" id="agentLevel"  name = "agentLevel" value="${Obj.agentLevel}" placeholder="代理商层级"> --%>
												</div>
											</div>
										</div>
									</div>
									<div class="row" style="display:none;">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">上级机构</label>
												<div class="col-md-4">
													<input type="hidden" id="parentNoVal" value='${Obj.parentNo }'>
														<select  id="parentNo" class="form-control" name="parentNo" disabled="disabled">
															
														</select>
													<%-- <input type="text" class="form-control" id="parentNo"  name = "parentNo" value="${Obj.parentNo}" readonly="readonly" placeholder="上级编号"> --%>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">代理商编号</label>
												<div class="col-md-4">
													<c:if test="${Obj == null}">
														<input type="text" class="form-control"
															id="agentNo" name="agentNo" value="${agentNo}"
															readonly="true" placeholder="代理商编号">
													</c:if>
													<c:if test="${Obj != null}">
														<input type="text" class="form-control"
															id="agentNo" name="agentNo"
															value="${Obj.agentNo}" placeholder="代理商编号"
															readonly="readonly">
													</c:if>
													<%-- <input type="text" class="form-control" id="agentNo"  name = "agentNo" value="${Obj.agentNo}" placeholder="代理商编号"> --%>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">代理商名称</label>
												<div class="col-md-4">
													<input type="text" class="form-control" id="agentName"  name = "agentName" value="${Obj.agentName}" placeholder="代理商名称">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">编码抬头</label>
												<div class="col-md-4">
													<input type="text" class="form-control" id="agentPreCode"  name = "agentPreCode" value="${Obj.agentPreCode}" placeholder="编码抬头">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">代理商类型</label>
												<div class="col-md-4">
												<c:if test="${Obj == null}">
													<select  id="agentType" class="form-control" name="agentType">
														<option value="" selected = "selected">----请选择----</option>
														<option value = "0">个人</option>
														<option value = "1">机构</option>
													</select>
													</c:if>
													<c:if test="${Obj != null}">
													<select  id="agentType" class="form-control" name="agentType">
														<option value="" selected = "selected">----请选择----</option>
														<option value = "0" <c:if test="${Obj.agentType==0}">selected="selected"</c:if> ">个人</option>
														<option value = "1" <c:if test="${Obj.agentType==1}">selected="selected"</c:if> ">机构</option>
													</select>
													</c:if>
													<%-- <input type="text" class="form-control" id="agentType"  name = "agentType" value="${Obj.agentType}" placeholder="代理商类型：0 个人、1 机构"> --%>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">联系人姓名</label>
												<div class="col-md-4">
													<input type="text" class="form-control" id="name"  name = "name" value="${Obj.name}" placeholder="联系人姓名">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">联系人手机号码</label>
												<div class="col-md-4">
													<input type="text" class="form-control" id="mobile"  name = "mobile" value="${Obj.mobile}" placeholder="联系人手机号码">
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
											<c:if test="${agentPage == null}">
											<button type="button" class="btn default" id="cancle">取消</button>
											</c:if>
											<c:if test="${agentPage != null}">
											<button type="button" class="btn info" onclick="javascript:agentPromotion('${Obj.agentNo}','${Obj.agentName}');">推广码</button></c:if>
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

<script src="${path}/resources/scripts/admin/agentbase/edit.js?rnd=${version}" type="text/javascript"></script>
<script src="${path}/resources/scripts/jquery.validate.js" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>