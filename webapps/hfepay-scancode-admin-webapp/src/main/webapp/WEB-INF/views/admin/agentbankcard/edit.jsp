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
<title>结算账户管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<jsp:include page="../include/commoncss.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${path}/resources/styles/bootstrap-select.css"/>
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
						<a href="" onclick="javascript:history.go(-1);">结算账户管理</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<c:if test="${Obj == null}"><a href="#">结算账户新增 </a></c:if>
						<c:if test="${Obj != null}"><a href="#">结算账户更新</a></c:if>
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
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">代理商名称</label>
												<div class="col-md-4">
													<select id="agentNo" name="agentNo" class="form-control selectpicker show-tick" data-live-search="true">
														<option value=''>  --  请选择  --  </option>
														<c:forEach var="item" items="${agentList}">
															<option value = "${item.agentNo}">${item.agentName}</option>
														</c:forEach>	
											        </select>
													<input type="hidden" class="form-control agentNo" value="${Obj.agentNo}">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">银行卡号码</label>
												<div class="col-md-4">
													<input type="text" class="form-control" id="bankCard"  name = "bankCard" value="${Obj.bankCard}" placeholder="银行卡号码">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">银行开户行代码</label>
												<div class="col-md-4">
													<input type="text" class="form-control" id="bankCode" readonly="readonly"  name = "bankCode" value="${Obj.bankCode}" placeholder="银行开户行代码">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">开户行银行名称</label>
												<div class="col-md-4">
													<input type="text" class="form-control" id="bankName" readonly="readonly" name = "bankName" value="${Obj.bankName}" placeholder="开户行银行名称">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">身份证号码</label>
												<div class="col-md-4">
													<input type="text" class="form-control" id="idCard"  name = "idCard" value="${Obj.idCard}" placeholder="身份证号码">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">开户名</label>
												<div class="col-md-4">
													<input type="text" class="form-control" id="name"  name = "name" value="${Obj.name}" placeholder="开户名">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">手机号码</label>
												<div class="col-md-4">
													<input type="text" class="form-control" id="mobile"  name = "mobile" value="${Obj.mobile}" placeholder="手机号码">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">是否对公对私</label>
												<div class="col-md-4">
													<select  id="accountType" class="form-control" name="accountType">
														<option value="" selected = "selected">----请选择----</option>
														<option value = "0" <c:if test="${Obj.accountType=='0'}">selected="selected"</c:if>>对私 </option>
														<option value = "1" <c:if test="${Obj.accountType=='1'}">selected="selected"</c:if>>对公</option>
													</select>
												</div>
											</div>
										</div>
									</div>
									<%-- <div class="row">
										<div class="col-md-8">
											<div class="form-group">
												<label class="control-label col-md-2">是否实时到账</label>
												<div class="col-md-5">
													<select  id="isRealAccount" class="form-control" name="isRealAccount">
														<option value="" selected = "selected">----请选择----</option>
														<option value = "Y" <c:if test="${Obj.isRealAccount=='Y'}">selected="selected"</c:if>>是 </option>
														<option value = "N" <c:if test="${Obj.isRealAccount=='N'}">selected="selected"</c:if>>否 </option>
													</select>
												</div>
											</div>
										</div>
									</div> --%>
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
<script src="${path}/resources/scripts/admin/commons/selectUtil.js?rnd=${version}" type="text/javascript"></script>

<script src="${path}/resources/scripts/admin/agentbankcard/edit.js?rnd=${version}" type="text/javascript"></script>
<script src="${path}/resources/scripts/jquery.validate.js" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>