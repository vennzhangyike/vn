<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">收银员编号</td><td class="col-md-4 parent_id_parse" title="cashierNo">${item.cashierNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">商户编号</td><td class="col-md-4 parent_id_parse" title="merchantNo">${item.merchantNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">二维码编号</td><td class="col-md-4 parent_id_parse" title="qrCode">${item.qrCode}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">是否开启收银</td><td class="col-md-4 parent_id_parse" title="isCashier">
			<c:if test="${item.isCashier=='1' }">停止收银</c:if>
			<c:if test="${item.isCashier=='2' }">开始收银</c:if>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">状态</td><td class="col-md-4 parent_id_parse" title="status">
			<c:if test="${item.status=='1' }">启用</c:if>
			<c:if test="${item.status=='2' }">禁用</c:if>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">创建时间</td><td class="col-md-4 date_time_parse" title="createTime">${item.createTime}</td>
	</tr>
</table>
<script type="text/javascript">
dataOpr();
</script>
