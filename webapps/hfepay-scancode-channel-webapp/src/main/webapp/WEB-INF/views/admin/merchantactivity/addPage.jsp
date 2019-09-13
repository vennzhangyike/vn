<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="page" uri="page" %> 
<%
   String path =  request.getContextPath();
request.setAttribute("path", path);
%>
<style type="text/css">
.my-modal{
	width:500px;
}
</style>
<form class="form-horizontal" id="popPage">
	<input type="hidden" id="type"  name = "type" value="${type}">
	<div class="row" id="row">
		
		<div class="col-md-3">
			<div class="form-group">
				<label class="control-label col-md-4 discount" style="width:79px;">活动优惠</label>
				<div class="col-md-8">
					<input type="text" value="" class="form-control from"  placeholder="活动优惠">
				</div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="form-group">
				<label class="control-label col-md-4 condition" style="width:79px;">活动条件</label>
				<div class="col-md-8">
					<input type="text" value="" class="form-control to" onkeyup="value=value.replace(/[^\d.]/g,'')"  onpaste="value=value.replace(/[^\d.]/g,'')" placeholder="活动条件">
				</div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="form-group">
				<label class="control-label col-md-4 chance" style="width:79px;">活动概率</label>
				<div class="col-md-8">
					<input type="text" value="" class="form-control odds" onkeyup="value=value.replace(/[^\d.]/g,'')" onpaste="value=value.replace(/[^\d.]/g,'')" placeholder="活动概率"> 
				</div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="form-group">
				<div class="col-md-8">
					<a href="#" style="font-size:24px" class="add" title="添加">+</a>&nbsp;&nbsp;
					<a href="#" style="font-size:24px" class="minus" title="删除">-</a>
				</div>
			</div>
		</div>
	</div>
</form>
<script src="${path}/resources/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/admin/merchantactivity/addPage.js" type="text/javascript"></script>
