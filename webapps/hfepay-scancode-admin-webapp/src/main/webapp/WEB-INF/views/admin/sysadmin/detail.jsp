<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">账号</td><td class="col-md-4 parent_id_parse" title="userName">${item.userName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">姓名</td><td class="col-md-4 parent_id_parse" title="password">${item.shortName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">账户角色</td><td class="col-md-4 parent_id_parse" title="userName">${item.roleName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">邮箱</td><td class="col-md-4 parent_id_parse" title="userName">${item.email}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">联系电话</td><td class="col-md-4 parent_id_parse" title="userName">${item.phone}</td>
	</tr>
<%-- 	<tr>
		<td class="col-md-2 td0">渠道编号</td><td class="col-md-4 parent_id_parse" title="salt">${item.channelCode}</td>
	</tr> --%>
<!-- 	<tr> -->
<%-- 		<td class="col-md-2 td0">密码</td><td class="col-md-4 parent_id_parse" title="password">${item.password}</td> --%>
<!-- 	</tr> -->
<!-- 	<tr> -->
<%-- 		<td class="col-md-2 td0">加密盐</td><td class="col-md-4 parent_id_parse" title="salt">${item.salt}</td> --%>
<!-- 	</tr> -->
<%-- 	<tr>
		<td class="col-md-2 td0">渠道名称</td><td class="col-md-4" title="${item.channelCode}">${item.channelName}</td>
	</tr> --%>
	<tr>
		<td class="col-md-2 td0">状态</td><td class="col-md-4 parent_id_parse" title="status">${item.status}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">创建时间</td><td class="col-md-4 date_time_parse" title="createTime">${item.createTime}</td>
	</tr>
</table>
<script type="text/javascript">
dataOpr();
</script>
