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
<title>结算账户设置编辑</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<jsp:include page="../../include/commoncss.jsp"></jsp:include>
<style type="text/css">
.hiddenRow{
	display: none;
}
</style>
</head>
<body class="page-header-fixed page-quick-sidebar-over-content">
<%-- <jsp:include page="../../include/head.jsp"></jsp:include> --%>
<!-- BEGIN CONTAINER -->
<div class="page-container">
<%-- <jsp:include page="../../include/menu.jsp"></jsp:include> --%>
	<!-- BEGIN CONTENT -->
	<section id="ucenterSection">
			<%-- <div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="${path }/adminManage/index">首页</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#" onClick="javascript:history.go(-1);">结算账户设置</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<c:if test="${Obj == null}"><a href="#">账户新增 </a></c:if>
						<c:if test="${Obj != null}"><a href="#">账户更新</a></c:if>
					</li>
				</ul>
			</div> --%>
			<!-- END PAGE HEADER-->
			
			<!-- BEGIN SEARCH CONDITION -->
			<div class="portlet box ">
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form class="form-horizontal" id="form">
							<div class="form-body">
								<input type="hidden" id="id" name="id" value="${Obj.id}">
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label col-md-2">渠道编号</label>
											<div class="col-md-4">
												<input type="text" class="form-control ajaxCheck" id="channelNo" name="channelNo" value="${Obj.channelNo}"placeholder="渠道编号" readonly="readonly">
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label col-md-2">渠道名称</label>
											<div class="col-md-4">
												<input type="text" class="form-control channelNameCheck" id="channelName" name="channelName" value="${Obj.channelName}" placeholder="渠道名称" readonly="readonly">
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label col-md-2">银行卡号码</label>
											<div class="col-md-4">
												<input type="text" class="form-control ajaxCheck"
													id="bankCard" name="bankCard" value="${Obj.bankCard}"
													placeholder="银行卡号码">
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label col-md-2">联行号</label>
											<div class="col-md-4">
												<input type="text" class="form-control ajaxCheck" <c:if test="${not empty Obj.bankCode}"> readonly="readonly" </c:if>
													id="bankCode" name="bankCode" value="${Obj.bankCode}"
													placeholder="联行号 	">
											</div>
										</div>
									</div>									
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label col-md-2">银行开户行名称</label>
											<div class="col-md-4">
												<input type="text" class="form-control ajaxCheck" readonly="readonly"
													id="bankName" name="bankName" value="${Obj.bankName}"
													placeholder="开户行银行名称">
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label col-md-2">账户类型
											</label>
											<div class="col-md-4">
												<select  id="accountType" class="form-control" name="accountType" <c:if test="${Obj.accountType==0}">disabled="disabled"</c:if> >
														<option value="" selected = "selected">----请选择----</option>
														<option value = "1" <c:if test="${Obj.accountType==1}">selected="selected"</c:if> >公司 </option>
														<option value = "0" <c:if test="${Obj.accountType==0}">selected="selected"</c:if> >个人</option>
												</select>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label col-md-2">开户名</label>
											<div class="col-md-4">
												<input type="text" class="form-control ajaxCheck" id="name"
													name="name" value="${Obj.name}" placeholder="开户名">
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label col-md-2">手机号码</label>
											<div class="col-md-4">
												<input type="text" class="form-control ajaxCheck"
													id="mobile" name="mobile" value="${Obj.mobile}"
													placeholder="手机号码">
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label col-md-2">身份证号码</label>
											<div class="col-md-4">
												<input type="text" class="form-control ajaxCheck"
													id="idCard" name="idCard" value="${Obj.idCard}"
													placeholder="身份证号码">
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
												<button type="submit" class="btn btn-default" id="save">保存</button>
												<button type="button" class="btn btn-default" id="cancle">取消</button>
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
<jsp:include page="../../include/footerjs.jsp"></jsp:include>
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


<script type="text/javascript">
	var baseurl = "${path}";
</script>
<script src="${path}/resources/scripts/admin/channel/channelbankcard/edit.js?rnd=${version}" type="text/javascript"></script>
<script src="${path}/resources/scripts/jquery.validate.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/ajaxfileupload.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/common.js" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>