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
<title>商户更新</title>
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
						<a href="" onclick="javascript:history.go(-1);">商户管理</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<c:if test="${Obj == null}"><a href="#">商户新增 </a></c:if>
						<c:if test="${Obj != null}"><a href="#">商户更新</a></c:if>
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
							<input type="hidden" id="status"  name = "status" value="${Obj.status}">
							<input type="hidden" id="redirectUrl"  name = "redirectUrl" value="${redirectUrl}">
							<c:if test="${Obj != null}">
								<div class="row">
									<div class="col-lg-5 col-sm-6">
										<div class="form-group">
											<label class="control-label col-md-3 col-sm-4">渠道名称</label>
											<div class="col-md-4 col-sm-4" style="margin-top: 7px;">
												${Obj.channelName}
											</div>
										</div>
									</div>
									<div class="col-lg-5 col-sm-6">
										<div class="form-group">
											<label class="control-label col-md-3 col-sm-4">代理商名称</label>
											<div class="col-md-4 col-sm-4" style="margin-top: 7px;">
												${Obj.agentName}
											</div>
										</div>
									</div>
								</div>
							</c:if>
							<div class="row">
								<c:if test="${Obj != null}">
									<div class="col-lg-5 col-sm-6">
										<div class="form-group">
											<label class="control-label col-md-3 col-sm-4">商户编号</label>
											<div class="col-md-4 col-sm-4" style="margin-top: 7px;">
												${Obj.merchantNo}
											</div>
										</div>
									</div>
								</c:if>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">商户名称</label>
										<div class="col-md-4 col-sm-4">
											<input type="text" class="form-control" id="merchantName"  name = "merchantName" value="${Obj.merchantName}" placeholder="商户名称">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">商户简称</label>
										<div class="col-md-4 col-sm-4">
											<input type="text" class="form-control" id="shortName"  name = "shortName" value="${Obj.shortName}" placeholder="商户简称">
										</div>
									</div>
								</div>
								<c:if test="${Obj != null}">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">转接平台商户编号</label>
										<div class="col-md-4 col-sm-4" style="margin-top: 7px;">
											${Obj.platformMerchantNo}
										</div>
									</div>
								</div>
								</c:if>
							</div>
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">经营类型</label>
										<div class="col-md-8 col-sm-8" style="margin-top: 7px;">
											<div id= "busTypeStr">${busTypeStr}</div>
											<input type="hidden" class="form-control" id="busType"  name = "busType" value="${Obj.busType}" placeholder="经营类型">
											<table>
												<tr>
													<td>
														<select  id="categoryOne" class="form-control" name="categoryOne" >
															<option value="" selected = "selected">----请选择----</option>
																<c:forEach var="category" items="${categoryOne}">
																	<option value = "${category.id}" categoryNo = "${category.categoryNo}">${category.name} </option>
																</c:forEach>
														</select>
													</td>
													<td>
														<select  id="categoryTwo" class="form-control" name="categoryTwo"  style="display:none; margin-left: 10px;" ></select>
													</td>
													
												</tr>
												<tr>
													<td>
														<select  id="categoryThree" class="form-control" name="categoryThree" style="display:none;"></select>
													</td>
													<td>
														
													</td>
												</tr>
											</table>
											<label style="color:red;display:none;" id="categoryError">经营类目数据异常！</label>
										</div>
									</div>
								</div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">法人姓名</label>
										<div class="col-md-4 col-sm-4">
											<input type="text" class="form-control" id="name"  name = "name" value="${Obj.name}" disabled="disabled" placeholder="法人姓名">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">法人身份证号码</label>
										<div class="col-md-4 col-sm-4">
											<input type="text" class="form-control" id="idCard"  name = "idCard" value="${Obj.idCard}" disabled="disabled" placeholder="法人身份证号码">
										</div>
									</div>
								</div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">身份证正面</label>
										<div class="col-md-4 col-xs-4 col-sm-4">
											<input type="file" name="file" id="idcardImg1_1" class="form-control fileInput" <c:if test="${merchantPage != null}">disabled="disabled"</c:if>
													onchange="submitForm('idcardImg1_1','idcardImg1','idcardImg1_2','idcardImg1')"/>
										</div>
										<div class="col-md-4 col-xs-4 col-sm-4" id="" style="margin-top: 7px;">
											<img id="idcardImg1_2" src="${Obj.idcardImg1}" style="max-width: 100%;"/>
											<input type="hidden" val="hidden" title="身份证正面" id="idcardImg1"   name = "idcardImg1" value="${Obj.idcardImg1}">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">身份证反面</label>
										<div class="col-md-4 col-xs-4 col-sm-4">
											<input type="file" name="file" id="idcardImg2_1" class="form-control fileInput" <c:if test="${merchantPage != null}">disabled="disabled"</c:if>
													onchange="submitForm('idcardImg2_1','idcardImg2','idcardImg2_2','idcardImg2')"/>
										</div>
										<div class="col-md-4 col-xs-4 col-sm-4" id="" style="margin-top: 7px;">
											<img id="idcardImg2_2" src="${Obj.idcardImg2}" style="max-width: 100%;"/>
											<input type="hidden" val="hidden" title="身份证反面" id="idcardImg2"  name = "idcardImg2" value="${Obj.idcardImg2}">
										</div>
									</div>
								</div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">手持身份证</label>
										<div class="col-md-4 col-xs-4 col-sm-4">
											<input type="file" name="file" id="idcardImg3_1" class="form-control fileInput" <c:if test="${merchantPage != null}">disabled="disabled"</c:if>
													onchange="submitForm('idcardImg3_1','idcardImg3','idcardImg3_2','idcardImg3')"/>
										</div>
										<div class="col-md-4 col-xs-4 col-sm-4" id="" style="margin-top: 7px;">
											<img id="idcardImg3_2" src="${Obj.idcardImg3}" style="max-width: 100%;"/>
											<input type="hidden" val="hidden" title="手持身份证" id="idcardImg3"  name = "idcardImg3" value="${Obj.idcardImg3}">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">手机号码</label>
										<div class="col-md-4 col-sm-4">
											<input type="text" class="form-control" id="mobile"  name = "mobile" value="${Obj.mobile}" placeholder="手机号码">
										</div>
									</div>
								</div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">联系电话</label>
										<div class="col-md-4 col-sm-4">
											<input type="text" class="form-control" id="phone"  name = "phone" value="${Obj.phone}" placeholder="联系电话">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">邮箱</label>
										<div class="col-md-4 col-sm-4">
											<input type="text" class="form-control" id="email"  name = "email" value="${Obj.email}" placeholder="邮箱">
										</div>
									</div>
								</div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">地址</label>
										<div class="col-md-4 col-sm-4">
											<input type="text" class="form-control" id="address"  name = "address" value="${Obj.address}" placeholder="地址">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">商户营业执照号</label>
										<div class="col-md-4 col-sm-4">
											<input type="text" class="form-control" id="merchantLicense"  name = "merchantLicense" value="${Obj.merchantLicense}" placeholder="商户营业执照号">
										</div>
									</div>
								</div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">营业执照图片</label>
										<div class="col-md-4 col-xs-4 col-sm-4">
											<input type="file" name="file" id="merchantLicenseImg_1" class="form-control fileInput" 
													onchange="submitForm('merchantLicenseImg_1','merchantLicenseImg','merchantLicenseImg_2','merchantLicenseImg')"/>
										</div>
										<div class="col-md-4 col-xs-4 col-sm-4" id="" style="margin-top: 7px;">
											<img id="merchantLicenseImg_2" src="${Obj.merchantLicenseImg}" style="max-width: 100%;"/>
											<input type="hidden" title="营业执照图片"  id="merchantLicenseImg"  name = "merchantLicenseImg" value="${Obj.merchantLicenseImg}">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">税务登记证号码</label>
										<div class="col-md-4 col-sm-4">
											<input type="text" class="form-control" id="taxNo"  name = "taxNo" value="${Obj.taxNo}" placeholder="税务登记证号码">
										</div>
									</div>
								</div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">税务登记证图片</label>
										<div class="col-md-4 col-xs-4 col-sm-4">
											<input type="file" name="file" id="taxImg_1" class="form-control fileInput" 
													onchange="submitForm('taxImg_1','taxImg','taxImg_2','taxImg')"/>
										</div>
										<div class="col-md-4 col-xs-4 col-sm-4" id="" style="margin-top: 7px;">
											<img id="taxImg_2" src="${Obj.taxImg}" style="max-width: 100%;"/>
											<input type="hidden" id="taxImg"  name = "taxImg" value="${Obj.taxImg}" title="税务登记证图片">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">组织机构代码</label>
										<div class="col-md-4 col-sm-4">
											<input type="text" class="form-control" id="orgNo"  name = "orgNo" value="${Obj.orgNo}" placeholder="组织机构代码">
										</div>
									</div>
								</div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">备注</label>
										<div class="col-md-4 col-sm-4">
											<input type="text" class="form-control" id="remark"  name = "remark" value="${Obj.remark}" placeholder="备注">
										</div>
									</div>
								</div>
							</div>
							<c:if test="${Obj == null}">
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">门店名称</label>
										<div class="col-md-4 col-sm-4">
											<input type="text" class="form-control" id="storeName"  name = "storeName" value="${Obj.storeName}" placeholder="门店名称">
										</div>
									</div>
								</div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">服务电话</label>
										<div class="col-md-4 col-sm-4">
											<input type="text" class="form-control" id="servicePhone"  name = "servicePhone" value="${Obj.servicePhone}" placeholder="服务电话">
										</div>
									</div>
								</div>
								<div class="col-lg-9 col-sm-12">
									<div class="form-group">
										<label class="control-label col-md-2 col-sm-4">门店地址</label>
										<div class="col-md-8 col-sm-6">
											<input type="text" class="form-control" id="storeAddress"  name = "storeAddress" value="${Obj.storeAddress}" placeholder="门店地址">
										</div>
									</div>
								</div>
							</div>
							</c:if>
							<div class="row">
							<div class="col-md-12">
								<div class="portlet box green-haze"></div>
								<div class="col-lg-5 col-sm-12">
									
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-2"></label>
										<div class="col-lg-8 col-sm-8">
											<button type="submit" class="btn green" id="save">保存</button>
											<c:if test="${merchantPage == null}">
											<button type="button" class="btn default" id="cancle">取消</button></c:if>
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

<script src="${path}/resources/scripts/admin/merchantinfo/edit.js?rnd=${version}" type="text/javascript"></script>
<script src="${path}/resources/scripts/jquery.validate.js" type="text/javascript"></script>
<script type="text/javascript">
	var baseurl = "${path}";
</script>
<script src="${path}/resources/scripts/ajaxfileupload.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/common.js" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>