<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">商户编号</td><td class="col-md-4 parent_id_parse" title="merchantNo">${item.merchantNo}</td>
		<td class="col-md-2 td0">商户名称</td><td class="col-md-4 parent_id_parse" title="merchantName">${item.merchantName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">可用余额(元)</td><td class="col-md-4 parent_id_parse" title="balance">${item.balance}</td>
		<td class="col-md-2 td0">冻结余额(元)</td><td class="col-md-4 parent_id_parse" title="freezesAmt">${item.freezesAmt}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">是否有效</td><td class="col-md-4 parent_id_parse" title="status">${item.status}</td>
		<td class="col-md-2 td0">入网时间</td><td class="col-md-4 date_time_parse" title="createTime">${item.createTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">修改时间</td><td class="col-md-4 date_time_parse" title="updateTime">${item.updateTime}</td>
		<td class="col-md-2 td0">操作人账号</td><td class="col-md-4 parent_id_parse" title="operator">${item.operatorName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">备注</td><td class="col-md-4 parent_id_parse" title="remark">${item.remark}</td>
		<td class="col-md-2 td0"></td><td class="col-md-4 parent_id_parse" title="operator"></td>
	</tr>
</table>
<script type="text/javascript">
dataOpr();
</script>
