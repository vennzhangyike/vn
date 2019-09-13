<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">商户编号</td><td>${item.merchantNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">商户名称</td><td>${item.merchantName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">费率代码</td><td>${item.payCode}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">费率名称</td><td>${item.payName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">T1交易费率</td><td ><fmt:formatNumber value="${item.t1Rate}" pattern="#0.#####"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">T0垫资成本</td><td ><fmt:formatNumber value="${item.t0Rate}" pattern="#0.#####"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">提现手续费(元)</td><td><fmt:formatNumber value="${item.rate}" pattern="#0.##"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">费率状态</td><td class="col-md-4 parent_id_parse" title="status">${item.status}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">入网状态</td><td class="col-md-4 parent_id_parse" title="acceptStatus">${item.acceptStatus}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">创建时间</td><td class="date_time_parse">${item.createTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">修改时间</td><td class="date_time_parse">${item.updateTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">操作人名称</td><td title="${item.operator}">${item.operatorName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">备注</td><td>${item.remark}</td>
	</tr>
	</tr>
</table>
<script type="text/javascript">
dataOpr();
</script>
