<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">用户类型</td><td class="col-md-4 parent_id_parse" title="userType">
			<c:if test="${item.userType=='1' }">商户</c:if>
			<c:if test="${item.userType=='2' }">代理商</c:if>
			<c:if test="${item.userType=='3' }">渠道</c:if>
			<c:if test="${item.userType=='9' }">不限</c:if>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">标题</td><td class="col-md-4 parent_id_parse" title="title">${item.title}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">内容</td><td class="col-md-4 parent_id_parse" title="content">${item.content}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">类型</td><td class="col-md-4 parent_id_parse" title="messageType">
			<c:if test="${item.messageType=='1' }">普通消息</c:if>
			<c:if test="${item.messageType=='2' }">系统消息</c:if>
			<c:if test="${item.messageType=='3' }">广告消息</c:if>
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
	<tr>
		<td class="col-md-2 td0">修改时间</td><td class="col-md-4 date_time_parse" title="updateTime">${item.updateTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">备注</td><td class="col-md-4 parent_id_parse" title="remark">${item.remark}</td>
	</tr>
</table>
<script type="text/javascript">
dataOpr();
</script>
