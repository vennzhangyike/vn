<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">限额通道</td><td class="col-md-4 parent_id_parse" title="limitPayCode">
			<c:if test="${item.limitPayCode=='1' }">不限</c:if>
			<c:if test="${item.limitPayCode!='1' }">${item.paraName}</c:if>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">限额类型</td><td class="col-md-4 parent_id_parse" title="limitType">
			<c:if test="${item.limitType=='1' }">不限</c:if>
			<c:if test="${item.limitType=='2' }">信用卡</c:if>
			<c:if test="${item.limitType=='3' }">借记卡</c:if>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">全日交易限额(元)</td><td class="col-md-4 parent_id_parse" title="dayTransLimit">${item.dayTransLimit}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">全日提现限额(元)</td><td class="col-md-4 parent_id_parse" title="dayWithdrawalsLimit">${item.dayWithdrawalsLimit}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">单笔交易限额(元)</td><td class="col-md-4 parent_id_parse" title="singleTransLimit">${item.singleTransLimit}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">单笔提现限额(元)</td><td class="col-md-4 parent_id_parse" title="singleWithdrawalsLimit">${item.singleWithdrawalsLimit}</td>
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
