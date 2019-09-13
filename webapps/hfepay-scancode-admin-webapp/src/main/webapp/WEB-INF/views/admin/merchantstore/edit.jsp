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
<title>商户门店更新</title>
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
						<a href="" onclick="javascript:history.go(-1);">商户门店管理</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<c:if test="${Obj == null}"><a href="#">商户门店新增 </a></c:if>
						<c:if test="${Obj != null}"><a href="#">商户门店更新</a></c:if>
					</li>
				</ul>
			</div> --%>
			<!-- END PAGE HEADER-->
			
			<!-- BEGIN SEARCH CONDITION -->
			<div class="portlet box ">
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form action="JavaScript:void(0);" class="form-horizontal" id="form" method="post">
						<div class="form-body">
							<input type="hidden" id="id"  name = "id" value="${Obj.id}">
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">商户名称</label>
										<div class="col-md-4 col-xs-9 col-sm-6" style="margin-top: 7px;">
											${mer.merchantName}
											<input type="hidden" name = "merchantNo" value="${mer.merchantNo}">
										</div>
									</div>
								</div>
								<c:if test="${ not empty Obj.storeNo}">
									<div class="col-lg-5 col-sm-6">
										<div class="form-group">
											<label class="control-label col-md-3 col-sm-4">门店编号</label>
											<div class="col-md-4 col-xs-9 col-sm-6" style="margin-top: 7px;">
													${Obj.storeNo}
											</div>
										</div>
									</div>
								</c:if>
							</div>
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">门店名称*</label>
										<div class="col-md-4 col-xs-9 col-sm-6">
											<input type="text" class="form-control" id="storeName"  name = "storeName" value="${Obj.storeName}" placeholder="门店名称">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">门店级别*</label>
										<div class="col-md-4 col-xs-9 col-sm-6">
											<input type="hidden" class="form-control storeType" value="${Obj.storeType}">
											<select id = "storeType" class="form-control" name="storeType">
												<option value = "">  --  请选择  --  </option>
												<option value = "0">总店</option>
												<option value = "1">分店</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">服务电话*</label>
										<div class="col-md-4 col-xs-9 col-sm-6">
											<input type="text" class="form-control" id="servicePhone"  name = "servicePhone" value="${Obj.servicePhone}" placeholder="服务电话">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">营业开始时间*</label>
										<div class="col-md-4 col-xs-9 col-sm-6">
											<input type="text" class="form-control" id="serviceBegin"  name = "serviceBegin" value="${Obj.serviceBegin}" placeholder="营业开始时间">
										</div>
									</div>
								</div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">营业结束时间*</label>
										<div class="col-md-4 col-xs-9 col-sm-6">
											<input type="text" class="form-control" id="serviceEnd"  name = "serviceEnd" value="${Obj.serviceEnd}" placeholder="营业结束时间">
										</div>
									</div>
								</div>
							</div>
							<%-- <div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">门店地址</label>
										<div class="col-md-4 col-xs-9 col-sm-6">
											<input type="text" class="form-control" id="storeAddress"  name = "storeAddress" value="${Obj.storeAddress}" placeholder="门店地址">
										</div>
									</div>
								</div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">商户营业执照号</label>
										<div class="col-md-4 col-xs-9 col-sm-6">
											<input type="text" class="form-control" id="merchantLicense"  name = "merchantLicense" value="${Obj.merchantLicense}" placeholder="商户营业执照号">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">门店介绍</label>
										<div class="col-md-4 col-xs-9 col-sm-6">
											<input type="text" class="form-control" id="storeDesc"  name = "storeDesc" value="${Obj.storeDesc}" placeholder="门店介绍">
										</div>
									</div>
								</div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">执照名称</label>
										<div class="col-md-4 col-xs-9 col-sm-6">
											<input type="text" class="form-control" id="licenseName"  name = "licenseName" value="${Obj.licenseName}" placeholder="执照名称">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">法人姓名</label>
										<div class="col-md-4 col-xs-9 col-sm-6">
											<input type="text" class="form-control" id="name"  name = "name" value="${Obj.name}" placeholder="法人姓名">
										</div>
									</div>
								</div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">身份证号</label>
										<div class="col-md-4 col-xs-9 col-sm-6">
											<input type="text" class="form-control" id="idCard"  name = "idCard" value="${Obj.idCard}" placeholder="身份证号">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">身份证正面</label>
										<div class="col-md-4 col-sm-4">
											<input type="file" name="file" id="idcardImg1_1" class="form-control fileInput" 
													onchange="submitForm('idcardImg1_1','idcardImg1','idcardImg1_2','idcardImg1')"/>
										</div>
										<div class="col-md-4 col-sm-4" id="" style="margin-top: 7px;">
											<img id="idcardImg1_2" src="${Obj.idcardImg1}" style="max-width: 100%;"/>
											<input type="hidden" val="hidden"  id="idcardImg1"  name = "idcardImg1" value="${Obj.idcardImg1}">
										</div>
									</div>
								</div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">身份证反面</label>
										<div class="col-md-4 col-sm-4">
											<input type="file" name="file" id="idcardImg2_1" class="form-control fileInput" 
													onchange="submitForm('idcardImg2_1','idcardImg2','idcardImg2_2','idcardImg2')"/>
										</div>
										<div class="col-md-4 col-sm-4" id="" style="margin-top: 7px;">
											<img id="idcardImg2_2" src="${Obj.idcardImg2}" style="max-width: 100%;"/>
											<input type="hidden" val="hidden"  id="idcardImg2"  name = "idcardImg2" value="${Obj.idcardImg2}">
										</div>
									</div>
								</div>
							</div> --%>
							<%-- <div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">手持身份证</label>
										<div class="col-md-4 col-sm-4">
											<input type="file" name="file" id="idcardImg3_1" class="form-control fileInput" 
												onchange="submitForm('idcardImg3_1','idcardImg3','idcardImg3_2','idcardImg3')"/>
										</div>
										<div class="col-md-4 col-sm-4" id="" style="margin-top: 7px;">
											<img id="idcardImg3_2" src="${Obj.idcardImg3}" style="max-width: 100%;"/>
											<input type="hidden" val="hidden"  id="idcardImg3"  name = "idcardImg3" value="${Obj.idcardImg3}">
										</div>
									</div>
								</div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">营业执照图片</label>
										<div class="col-md-4 col-sm-4">
											<input type="file" name="file" id="merchantLicenseImg_1" class="form-control fileInput" 
													onchange="submitForm('merchantLicenseImg_1','merchantLicenseImg','merchantLicenseImg_2','merchantLicenseImg')"/>
										</div>
										<div class="col-md-4 col-sm-4" id="" style="margin-top: 7px;">
											<img id="merchantLicenseImg_2" src="${Obj.merchantLicenseImg}" style="max-width: 100%;"/>
											<input type="hidden" val="hidden"  id="merchantLicenseImg"  name = "merchantLicenseImg" value="${Obj.merchantLicenseImg}">
										</div>
									</div>
								</div>
								
							</div>
							
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">法人门店图片</label>
										<div class="col-md-4 col-sm-4">
											<input type="file" name="file" id="groupPhotoImg_1" class="form-control fileInput" 
												onchange="submitForm('groupPhotoImg_1','groupPhotoImg','groupPhotoImg_2','groupPhotoImg')"/>
										</div>
										<div class="col-md-4 col-sm-4" id="" style="margin-top: 7px;">
											<img id="groupPhotoImg_2" src="${Obj.groupPhotoImg}" style="max-width: 100%;"/>
											<input type="hidden" val="hidden"  id="groupPhotoImg"  name = "groupPhotoImg" value="${Obj.groupPhotoImg}">
										</div>
									</div>
								</div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">相册照片</label>
										<div class="col-md-4 col-xs-9 col-sm-6">
											<input type="hidden" class="form-control isPhoto" value="${Obj.isPhoto}">
											<select id = "isPhoto" class="form-control" name="isPhoto">
												<option value = "">  --  请选择  --  </option>
												<option value = "0">无</option>
												<option value = "1">有</option>
											</select>
										</div>
									</div>
								</div>
							</div> --%>
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">门店地址图片</label>
										<div class="col-md-4 col-sm-4">
											<input type="file" name="file" id="storeAddressImg_1" class="form-control fileInput" 
												onchange="submitForm('storeAddressImg_1','storeAddressImg','storeAddressImg_2','storeAddressImg')"/>
										</div>
										<div class="col-md-4 col-sm-4" id="" style="margin-top: 7px;">
											<img id="storeAddressImg_2" src="${Obj.storeAddressImg}" style="max-width: 100%;"/>
											<input type="hidden" val="hidden"  id="storeAddressImg"  name = "storeAddressImg" value="${Obj.storeAddressImg}">
										</div>
									</div>
								</div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">门店图片</label>
										<div class="col-md-4 col-sm-4">
											<input type="file" name="file" id="storePhotosImg_1" class="form-control fileInput" 
												onchange="submitForm('storePhotosImg_1','storePhotosImg','storePhotosImg_2','storePhotosImg')"/>
										</div>
										<div class="col-md-4 col-sm-4" id="" style="margin-top: 7px;">
											<img id="storePhotosImg_2" src="${Obj.storePhotosImg}" style="max-width: 100%;"/>
											<input type="hidden" val="hidden"  id="storePhotosImg"  name = "storePhotosImg" value="${Obj.storePhotosImg}">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">店内图片</label>
										<div class="col-md-4 col-sm-4">
											<input type="file" name="file" id="storeImg_1" class="form-control fileInput" 
												onchange="submitForm('storeImg_1','storeImg','storeImg_2','storeImg')"/>
										</div>
										<div class="col-md-4 col-sm-4" id="" style="margin-top: 7px;">
											<img id="storeImg_2" src="${Obj.storeImg}" style="max-width: 100%;"/>
											<input type="hidden" val="hidden"  id="storeImg"  name = "storeImg" value="${Obj.storeImg}">
										</div>
									</div>
								</div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">门店地址</label>
										<div class="col-md-4 col-xs-9 col-sm-6">
											<input type="text" class="form-control" id="storeAddress"  name = "storeAddress" value="${Obj.storeAddress}" placeholder="门店地址">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">
										<label class="control-label col-md-3 col-sm-4">备注</label>
										<div class="col-md-4 col-sm-4">
											<input type="text" class="form-control" id="remark"  name = "remark" value="${Obj.remark}" placeholder="备注">
										</div>
									</div>
								</div>
							
							</div>
							<div class="row">
								<div class="col-md-12">
								<div class="portlet box green-haze"></div>
								<div class="col-lg-5 col-sm-6">
									<div class="form-group">										
										<label class="control-label col-md-3 col-sm-4"></label>
										<div class="col-md-4 col-sm-4">
											<button type="submit" class="btn green" id="save">保存</button>
											<button type="button" class="btn default" id="cancle">取消</button>
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

<script src="${path}/resources/scripts/admin/merchantstore/edit.js?rnd=${version}" type="text/javascript"></script>
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