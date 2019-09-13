<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">渠道编号</td><td class="col-md-4 parent_id_parse" title="channelNo">${item.channelNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">代理商编号</td><td class="col-md-4 parent_id_parse" title="agentNo">${item.agentNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">商户编号</td><td class="col-md-4 parent_id_parse" title="merchantNo">${item.merchantNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">活动ID</td><td class="col-md-4 parent_id_parse" title="activityId">${item.activityId}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">活动开始时间</td><td class="col-md-4 date_time_parse" title="activityBeginTime">${item.activityBeginTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">活动结束时间</td><td class="col-md-4 date_time_parse" title="activityEndTime">${item.activityEndTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">活动类型</td><td class="col-md-4 parent_id_parse" title="activityType">
			<c:if test="${item.activityType=='0' }">折扣</c:if>
			<c:if test="${item.activityType=='1' }">满减</c:if>
			<c:if test="${item.activityType=='2' }">随机</c:if>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">活动描述</td><td class="col-md-4 parent_id_parse" title="activityContent">${item.activityContent}</td>
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
