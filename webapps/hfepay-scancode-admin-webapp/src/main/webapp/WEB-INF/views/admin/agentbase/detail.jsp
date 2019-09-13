<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">渠道名称</td><td class="col-md-4 parent_id_parse" title="channelNo">${item.channelNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">代理商级别</td><td class="col-md-4 parent_id_parse" title="agentLevel">${item.agentLevel}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">上级机构</td><td class="col-md-4 parent_id_parse" title="parentNo">${item.parentNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">代理商编号</td><td class="col-md-4 parent_id_parse" title="agentNo">${item.agentNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">代理商名称</td><td class="col-md-4 parent_id_parse" title="agentName">${item.agentName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">编码抬头</td><td class="col-md-4 parent_id_parse" title="agentPreCode">${item.agentPreCode}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">代理商类型</td>
		<c:if test="${item.agentType=='0'}">
			<td class="col-md-4 parent_id_parse" title="status">个人</td>
		</c:if>
		<c:if test="${item.agentType=='1'}">
			<td class="col-md-4 parent_id_parse" title="status">机构</td>
		</c:if>
	</tr>
	<tr>
		<td class="col-md-2 td0">联系人姓名</td><td class="col-md-4 parent_id_parse" title="name">${item.name}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">联系人手机号码</td><td class="col-md-4 parent_id_parse" title="mobile">${item.mobile}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">总二维码数量</td><td class="col-md-4 parent_id_parse" title="qrTotal">${item.qrTotal}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">已使用二维码数量</td><td class="col-md-4 parent_id_parse" title="useTotal">${item.useTotal}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">剩余二维码数量</td><td class="col-md-4 parent_id_parse" title="lessTotal">${item.lessTotal}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">状态</td>
		<c:if test="${item.status=='2'}">
			<td class="col-md-4 parent_id_parse" title="status">禁用</td>
		</c:if>
		<c:if test="${item.status=='1'}">
			<td class="col-md-4 parent_id_parse" title="status">启用</td>
		</c:if>
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
