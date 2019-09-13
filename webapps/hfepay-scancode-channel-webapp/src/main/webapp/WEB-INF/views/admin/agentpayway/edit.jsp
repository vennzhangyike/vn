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
<title>支付方式设置</title>
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
						<a href="" onclick="javascript:history.go(-1);">支付方式管理</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<c:if test="${Obj == null}"><a href="#">支付方式新增 </a></c:if>
						<c:if test="${Obj != null}"><a href="#">支付方式更新</a></c:if>
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
							<input type="hidden" id="t0"  name = "t0" value="">
							<input type="hidden" id="t1"  name = "t1" value="">
							<input type="hidden" id="rat"  name = "rat" value="">
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">代理商编号</label>
												<div class="col-md-4">
													<c:if test="${Obj == null}">
													<select  id="agentNo" class="form-control" name="agentNo">
														<option value="" selected = "selected">----请选择----</option>
															<c:forEach var="agentList" items="${agentList}">
																<option value = "${agentList.agentNo}">${agentList.agentName}</option>
															</c:forEach>
													</select>
													</c:if>
													<c:if test="${Obj != null}">
													<select  id="agentNo" class="form-control" name="agentNo" disabled="disabled">
														<option value="" selected = "selected">----请选择----</option>
															<c:forEach var="agentList" items="${agentList}">
																<option value = "${agentList.agentNo}" <c:if test="${Obj.agentNo==agentList.agentNo}">selected="selected"</c:if> ">${agentList.agentName}</option>
															</c:forEach>
													</select>
													</c:if>
													<input type="hidden" id="agentNoVal"  name = "agentNoVal" value="${Obj.agentNo}">
												</div>
											</div>
										</div>
									</div>
									<%-- <div class="row">
										<div class="col-md-8">
											<div class="form-group">
												<label class="control-label col-md-2">支付通道代码</label>
												<div class="col-md-5">
													<input type="text" class="form-control" id="payCode"  name = "payCode" value="${Obj.payCode}" placeholder="支付通道代码">
												</div>
											</div>
										</div>
									</div> --%>
									<input type="hidden" id="payName"  name = "payName" value="${Obj.payName}">
									
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">支付通道名称</label>
												<div class="col-md-4">
													<input type="hidden" id="payCodeVal" value='${Obj.payCode }'>
													<select  id="payCode" class="form-control" name="payCode">
														<option value="" selected = "selected">----请选择----</option>
													</select>
													<%-- <input type="text" class="form-control" id="payCode"  name = "payCode" value="${Obj.payCode}" placeholder="支付通道名称"> --%>
												</div>
											</div>
										</div>
									</div>									
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">T1交易费率</label>
												<div class="col-md-4">
													<input type="text" class="form-control" id="t1Rate"  name = "t1Rate" value="<fmt:formatNumber value="${Obj.t1Rate}" pattern="#0.#####"/>" placeholder="T1交易费率">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">T0垫资成本</label>
												<div class="col-md-4">
													<input type="text" class="form-control" id="t0Rate"  name = "t0Rate" value="<fmt:formatNumber value="${Obj.t0Rate}" pattern="#0.#####"/>" placeholder="T0垫资成本">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">提现手续费(元)</label>
												<div class="col-md-4">
													<input type="text" class="form-control" id="rate"  name = "rate" value="<fmt:formatNumber value="${Obj.rate}" pattern="#0.##"/>" placeholder="提现手续费(元)">
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

<script src="${path}/resources/scripts/admin/agentpayway/edit.js?rnd=${version}" type="text/javascript"></script>
<script src="${path}/resources/scripts/jquery.validate.js" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>