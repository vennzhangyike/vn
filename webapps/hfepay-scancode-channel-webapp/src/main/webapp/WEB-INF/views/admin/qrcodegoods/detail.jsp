<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">二维码编号</td><td>${item.qrCode}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">商户编号</td><td>${item.merchantNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">商户名称</td><td>${item.merchantName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">商品名称</td><td>${item.goodsName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">商品描述</td><td>${item.goodsDesc}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">是否默认</td><td class="col-md-4 parent_id_parse" title="isDefault">${item.isDefault}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">创建时间</td><td class="col-md-4 date_time_parse">${item.createTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">修改时间</td><td class="col-md-4 date_time_parse">${item.updateTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">操作人名称</td><td class="col-md-4" title="${item.operator}">${item.operatorName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">备注</td><td class="col-md-4 parent_id_parse" title="remark">${item.remark}</td>
	</tr>
</table>
<script type="text/javascript">
dataOpr();
</script>
