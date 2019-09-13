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
<title>ChannelSecretKey Edit</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<jsp:include page="../include/commoncss.jsp"></jsp:include>
<style type="text/css">
.hiddenRow{
	display: none;
}
textarea{ resize:none; width:300px; height:400px;}
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
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="${path}/adminManage/index">首页</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="" onclick="javascript:history.go(-1);">秘钥管理</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<c:if test="${Obj == null}"><a href="#">秘钥新增 </a></c:if>
						<c:if test="${Obj != null}"><a href="#">秘钥更新</a></c:if>
					</li>
				</ul>
			</div>
			<!-- END PAGE HEADER-->
			
			<!-- BEGIN SEARCH CONDITION -->
			<div class="portlet box ">
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form action="save" class="form-horizontal" id="form" method="post">
						<div class="form-body">
							<input type="hidden" id="id"  name = "id" value="${Obj.id}">
							<input type="hidden" id="channelNo"  name = "channelNo" value="${Obj.channelNo}">
							<input type="hidden" id="payCode"  name = "payCode" value="${Obj.payCode}">
									<div class="row">
										<div class="col-md-8">
											<div class="form-group">
												<label class="control-label col-md-2">渠道名称</label>
												<div class="col-md-5">
													<c:if test="${Obj == null}">
													<select  id="companyName" class="form-control" name="companyName">
														<option value="" selected = "selected">----请选择----</option>
															<c:forEach var="channel" items="${channelInfo}">
																<option value = "${channel.channelName}" channelNo="${channel.channelNo }">${channel.channelName} </option>
															</c:forEach>
													</select>
													</c:if>
													<c:if test="${Obj != null}">
													<select  id="companyName" class="form-control" name="companyName" disabled="disabled">
														<option value="" selected = "selected">----请选择----</option>
															<c:forEach var="channel" items="${channelInfo}">
																<option value = "${channel.channelName}" <c:if test="${Obj.companyName==channel.channelName}">selected="selected"</c:if> channelNo="${channel.channelNo }">${channel.channelName}</option>
															</c:forEach>
													</select>
													</c:if>
												</div>
											</div>
										</div>
									</div>
<!-- 									<div class="row"> -->
<!-- 										<div class="col-md-8"> -->
<!-- 											<div class="form-group"> -->
<!-- 												<label class="control-label col-md-2">渠道类型</label> -->
<!-- 												<div class="col-md-5"> -->
<%-- 													<input type="text" class="form-control" id="channelType"  name = "channelType" value="${Obj.channelType}" placeholder="渠道类型"> --%>
<!-- 												</div> -->
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 									<div class="row"> -->
<!-- 										<div class="col-md-8"> -->
<!-- 											<div class="form-group"> -->
<!-- 												<label class="control-label col-md-2">优选通道</label> -->
<!-- 												<div class="col-md-5"> -->
<%-- 													<input type="text" class="form-control" id="firstPayobj"  name = "firstPayobj" value="${Obj.firstPayobj}" placeholder="优选通道"> --%>
<!-- 												</div> -->
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 									</div> -->
									
									<%-- <div class="row">
										<div class="col-md-8">
											<div class="form-group">
												<label class="control-label col-md-2">支付通道</label>
												<div class="col-md-5">
													<select  id="payName" class="form-control" name="payName">
														<option value="" selected = "selected">----请选择----</option>
															<c:forEach var="pay" items="${payCodeList}">
																<c:if test="${Obj == null}">
																	<option value = "${pay.name}" payCode="${pay.code }">${pay.name} </option>
																</c:if>
																<c:if test="${Obj != null}">
																	<option value = "${pay.name}" <c:if test="${Obj.payName==pay.name}">selected="selected"</c:if> payCode="${pay.code }">${pay.name}</option>
																</c:if>
															</c:forEach>
													</select>
												</div>
											</div>
										</div>
									</div> --%>
									<div class="row">
										<div class="col-md-8">
											<div class="form-group">
												<label class="control-label col-md-2">授权IP</label>
												<div class="col-md-5">
													<input type="text" class="form-control" id="boundIp"  name = "boundIp" value="${Obj.boundIp}" placeholder="绑定IP">
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-8">
											<div class="form-group">
												<label class="control-label col-md-2">渠道公钥</label>
												<div class="col-md-5">
													<textarea class="form-control" id="joinUserPublicKey" name = "joinUserPublicKey"  placeholder="用户上传公钥">${Obj.joinUserPublicKey}</textarea>
													<%-- <input type="text" class="form-control" id="joinUserPublicKey"  name = "joinUserPublicKey" value="${Obj.joinUserPublicKey}" > --%>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-8">
											<div class="form-group">
												<label class="control-label col-md-2">平台公钥</label>
												<div class="col-md-5">
													<textarea class="form-control" id="joinPublicKey" name = "joinPublicKey"  placeholder="接入加密公钥">${Obj.joinPublicKey}</textarea>
													<%-- <input type="text" class="form-control" id="joinPublicKey"  name = "joinPublicKey" value="${Obj.joinPublicKey}" placeholder="接入加密公钥"> --%>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-8">
											<div class="form-group">
												<label class="control-label col-md-2">平台私钥</label>
												<div class="col-md-5">
													<textarea class="form-control" id="joinPrivateKey" name = "joinPrivateKey" placeholder="签名私钥">${Obj.joinPrivateKey}</textarea>
													<%-- <input type="text" class="form-control" id="joinPrivateKey"  name = "joinPrivateKey" value="${Obj.joinPrivateKey}" placeholder="接入签名公钥"> --%>
												</div>
											</div>
										</div>
									</div>
							<div class="row">
								<div class="col-md-12">
									<!-- BEGIN EXAMPLE TABLE PORTLET-->
									<div class="portlet box green-haze"></div>
									<div class="form-body">
										<div class="col-md-4">
											<div class="form-group">
												<label class="control-label col-md-3"></label>
												<div class="col-md-9">
													<button type="submit" class="btn btn-default" id="save">保存</button>
													<button type="button" class="btn btn-default" id="cancle">取消</button>
												</div>
											</div>
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

<script src="${path}/resources/scripts/admin/channelsecretkey/edit.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/jquery.validate.js" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>