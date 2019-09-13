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
<title>平台限额管理</title>
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
<jsp:include page="../include/head.jsp"></jsp:include>
<!-- BEGIN CONTAINER -->
<div class="page-container">
<jsp:include page="../include/menu.jsp"></jsp:include>
	<!-- BEGIN CONTENT -->
	<section id="ucenterSection">
			
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
												<label class="control-label col-md-2">限额通道</label>
												<div class="col-md-4">
												    <c:if test="${Obj == null}">
													<select  id="limitPayCode" class="form-control" name="limitPayCode">
														<option value="" selected = "selected">----请选择----</option>
														<option value="1">不限</option>
															<c:forEach var="payCodeList" items="${payCodeList}">
																<option value = "${payCodeList.paraVal}">${payCodeList.paraName} </option>
															</c:forEach>
													</select>
													</c:if>
													<c:if test="${Obj != null}">
													<select  id="limitPayCode" class="form-control" name="limitPayCode">
														<option value="" selected = "selected">----请选择----</option>
														<option value="1" <c:if test="${Obj.limitPayCode=='1'}">selected="selected"</c:if>>不限</option>
															<c:forEach var="payCodeList" items="${payCodeList}">
																<option value = "${payCodeList.paraVal}" <c:if test="${Obj.limitPayCode==payCodeList.paraVal}">selected="selected"</c:if>>${payCodeList.paraName}</option>
															</c:forEach>
													</select>
													</c:if> 
													<%-- <input type="text" class="form-control" id="limitPayCode"  name = "limitPayCode" value="${Obj.limitPayCode}" placeholder="限额通道：1 不限 、通道编号"> --%>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">限额类型</label>
												<div class="col-md-4">
													<c:if test="${Obj == null}">
													<select  id="limitType" class="form-control" name="limitType">
														<option value="" selected = "selected">----请选择----</option>
															<option value = "1">不限</option>
															<option value = "2">信用卡</option>
															<option value = "3">借记卡</option>
													</select>
													</c:if>
													<c:if test="${Obj != null}">
													<select  id="limitType" class="form-control" name="limitType">
														<option value="" selected = "selected">----请选择----</option>
															<option value = "1" <c:if test="${Obj.limitType=='1'}">selected="selected"</c:if>>不限</option>
															<option value = "2" <c:if test="${Obj.limitType==2}">selected="selected"</c:if>>信用卡</option>
															<option value = "3" <c:if test="${Obj.limitType==3}">selected="selected"</c:if>>借记卡</option>
													</select>
													</c:if>
													<%-- <input type="text" class="form-control" id="limitType"  name = "limitType" value="${Obj.limitType}" placeholder="限额类型：1 不限 2信用卡 3普通卡"> --%>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">全日交易限额(元)</label>
												<div class="col-md-4">
													<input type="text" class="form-control" id="dayTransLimit"  name = "dayTransLimit" value="${Obj.dayTransLimit}" placeholder="全日交易限额(元)">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">全日提现限额(元)</label>
												<div class="col-md-4">
													<input type="text" class="form-control" id="dayWithdrawalsLimit"  name = "dayWithdrawalsLimit" value="${Obj.dayWithdrawalsLimit}" placeholder="全日提现限额(元)">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">单笔交易限额(元)</label>
												<div class="col-md-4">
													<input type="text" class="form-control" id="singleTransLimit"  name = "singleTransLimit" value="${Obj.singleTransLimit}" placeholder="单笔交易限额(元)">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label class="control-label col-md-2">单笔提现限额(元)</label>
												<div class="col-md-4">
													<input type="text" class="form-control" id="singleWithdrawalsLimit"  name = "singleWithdrawalsLimit" value="${Obj.singleWithdrawalsLimit}" placeholder="单笔提现限额(元)">
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
<jsp:include page="../include/footer.jsp"></jsp:include>
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

<script src="${path}/resources/scripts/admin/platformlimit/edit.js?rnd=${version}" type="text/javascript"></script>
<script src="${path}/resources/scripts/jquery.validate.js" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>