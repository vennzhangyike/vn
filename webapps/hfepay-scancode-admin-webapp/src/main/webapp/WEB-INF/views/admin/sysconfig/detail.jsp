<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">名称</td><td class="col-md-4 parent_id_parse" title="name">${item.name}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">KEY</td><td class="col-md-4 parent_id_parse" title="keyNo">${item.keyNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">参数名称</td><td class="col-md-4 parent_id_parse" title="paraName">${item.paraName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">参数值</td><td class="col-md-4 parent_id_parse" title="paraVal">${item.paraVal}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">创建时间</td><td class="col-md-4 date_time_parse" title="createTime">${item.createTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">修改时间</td><td class="col-md-4 date_time_parse" title="updateTime">${item.updateTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">操作人账号</td><td class="col-md-4" title="${item.operatorId}">${item.operatorName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">备注</td><td class="col-md-4 parent_id_parse" title="remark">${item.remark}</td>
	</tr>
</table>
<script type="text/javascript">
dataOpr();
</script>
