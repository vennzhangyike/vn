<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	<c:if test="${item.identityFlag=='1' }"><!-- 渠道商 -->
		<tr>
			<td class="col-md-2 td0">系统身份</td><td class="col-md-4 parent_id_parse">渠道商</td>
		</tr>
	</c:if>
	<c:if test="${item.identityFlag=='2' }"><!-- 代理商 -->
		<tr>
			<td class="col-md-2 td0">系统身份</td><td class="col-md-4 parent_id_parse">代理商</td>
		</tr>
		<tr>
			<td class="col-md-2 td0">所属渠道</td><td class="col-md-4 parent_id_parse">${item.channelCode }</td>
		</tr>
	</c:if>
	<c:if test="${item.identityFlag=='3' }"><!-- 普通商户 -->
		<tr>
			<td class="col-md-2 td0">系统身份</td><td class="col-md-4 parent_id_parse">终端商户</td>
		</tr>
		<tr>
			<td class="col-md-2 td0">所属渠道</td><td class="col-md-4 parent_id_parse">${item.channelCode }</td>
		</tr>
		<tr>
			<td class="col-md-2 td0">所属代理商</td><td class="col-md-4 parent_id_parse">${item.agentNo }</td>
		</tr>
	</c:if>
	<tr>
		<td class="col-md-2 td0">邮箱</td><td class="col-md-4 parent_id_parse" title="userName">${item.email}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">联系电话</td><td class="col-md-4 parent_id_parse" title="userName">${item.phone}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">状态</td><td class="col-md-4 parent_id_parse" title="status">
		<c:if test="${item.status == 1}">启用</c:if>
		<c:if test="${item.status == 0}">禁用</c:if>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">创建时间</td><td class="col-md-4 date_time_parse" title="createTime"><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd E HH:mm:ss"/></td>
	</tr>
</table>

