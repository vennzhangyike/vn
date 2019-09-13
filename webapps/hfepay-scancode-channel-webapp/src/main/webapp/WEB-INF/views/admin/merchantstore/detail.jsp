<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">商户编号</td><td>${item.merchantNo}</td>
		<td class="col-md-2 td0">商户名称</td><td>${item.merchantName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">门店编号</td><td>${item.storeNo}</td>
		<td class="col-md-2 td0">门店名称</td><td>${item.storeName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">门店级别</td><td class="col-md-4 parent_id_parse" title="storeType">${item.storeType}</td>
		<td class="col-md-2 td0">服务电话</td><td class="col-md-4 parent_id_parse" title="servicePhone">${item.servicePhone}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">营业开始时间</td><td class="col-md-4 parent_id_parse" title="serviceBegin">${item.serviceBegin}</td>
		<td class="col-md-2 td0">营业结束时间</td><td class="col-md-4 parent_id_parse" title="serviceEnd">${item.serviceEnd}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">门店地址</td><td class="col-md-4 parent_id_parse" colspan="3">${item.storeAddress}</td>
		
	</tr>
	<%-- <tr>
		<td class="col-md-2 td0">门店介绍</td><td class="col-md-4 parent_id_parse" colspan="3" title="storeDesc">${item.storeDesc}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">执照名称</td><td class="col-md-4 parent_id_parse" title="licenseName">${item.licenseName}</td>
		<td class="col-md-2 td0">相册照片</td><td class="col-md-4 parent_id_parse" title="isPhoto">${item.isPhoto}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">法人姓名</td><td class="col-md-4 parent_id_parse" title="name">${item.name}</td>
		<td class="col-md-2 td0">身份证号</td><td class="col-md-4 parent_id_parse" title="idCard">${item.idCard}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">身份证正面</td><td class="col-md-4 parent_id_parse" title="idcardImg1">
		<c:if test="${not empty item.idcardImg1}"><img style="max-width: 200px; max-height: 200px;" src="${item.idcardImg1}"></c:if>
		</td>
		<td class="col-md-2 td0">身份证反面</td><td class="col-md-4 parent_id_parse" title="idcardImg2">
		<c:if test="${not empty item.idcardImg2}"><img style="max-width: 200px; max-height: 200px;" src="${item.idcardImg2}"></c:if>
		</td>
	</tr> --%>
	<%-- <tr>
		<td class="col-md-2 td0">手持身份证</td><td class="col-md-4 parent_id_parse" title="idcardImg3">
		<c:if test="${not empty item.idcardImg3}"><img style="max-width: 200px; max-height: 200px;" src="${item.idcardImg3}"></c:if>
		</td>
		<td class="col-md-2 td0">营业执照图片</td><td class="col-md-4 parent_id_parse" title="merchantLicenseImg">
		<c:if test="${not empty item.merchantLicenseImg}"><img style="max-width: 200px; max-height: 200px;" src="${item.merchantLicenseImg}"></c:if>
		</td>
	</tr> --%>
	<tr>		
		<td class="col-md-2 td0">门店地址图片</td><td class="col-md-4 parent_id_parse" title="storeAddressImg">
		<c:if test="${not empty item.storeAddressImg}">
		<div class="small_pic">
			<a href="#pic_storeAddressImg">
			<img style="max-width: 150px; max-height: 150px;" src="${item.storeAddressImg}">
				<div id="pic_storeAddressImg" style="display:none;"><img style="max-width: 800px; max-height: 600px;" src="${item.storeAddressImg}"></div>
			</a>
		</div>
		</c:if>
		</td>
		<td class="col-md-2 td0">店内图片</td><td class="col-md-4 parent_id_parse" title="storeImg">
		<c:if test="${not empty item.storeImg}">
		<div class="small_pic">
			<a href="#pic_storeImg">
			<img style="max-width: 150px; max-height: 150px;" src="${item.storeImg}">
				<div id="pic_storeImg" style="display:none;"><img style="max-width: 800px; max-height: 600px;" src="${item.storeImg}"></div>
			</a>
		</div>
		</c:if>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">门店图片</td><td class="col-md-4 parent_id_parse" colspan="3" title="storePhotosImg">
		<c:if test="${not empty item.storePhotosImg}">
		<div class="small_pic">
			<a href="#pic_storePhotosImg">
			<img style="max-width: 150px; max-height: 150px;" src="${item.storePhotosImg}">
				<div id="pic_storePhotosImg" style="display:none;"><img style="max-width: 800px; max-height: 600px;" src="${item.storePhotosImg}"></div>
			</a>
		</div>
		</c:if>
		</td>
		<%-- <td class="col-md-2 td0">法人门店图片</td><td class="col-md-4 parent_id_parse" title="groupPhotoImg">
		<c:if test="${not empty item.groupPhotoImg}"><img style="max-width: 200px; max-height: 200px;" src="${item.groupPhotoImg}"></c:if>
		</td> --%>
	</tr>
	<tr>
		<td class="col-md-2 td0">操作人名称</td><td class="col-md-4" title="${item.operator}">${item.operatorName}</td>
		<td class="col-md-2 td0">创建时间</td><td class="col-md-4 date_time_parse" title="createTime">${item.createTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">审核人</td><td class="col-md-4 parent_id_parse" title="auditOperator">${item.auditOperator}</td>
		<td class="col-md-2 td0">审核日期</td><td class="col-md-4 date_time_parse" title="auditDate">${item.auditDate}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">审核理由</td><td class="col-md-4 parent_id_parse" title="auditReson">${item.auditReson}</td>
		<td class="col-md-2 td0">门店状态</td><td class="col-md-4 parent_id_parse" title="storeStatus">${item.storeStatus}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">备注</td><td class="col-md-4 parent_id_parse"  colspan="3" title="remark">${item.remark}</td>
	</tr>
</table>
<script type="text/javascript">
dataOpr();
$('div.small_pic a').fancyZoom({scaleImg: true, closeOnClick: true});
</script>
