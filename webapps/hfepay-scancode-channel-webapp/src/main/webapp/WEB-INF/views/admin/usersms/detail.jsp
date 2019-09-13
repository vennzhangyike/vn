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
		<td class="col-md-2 td0">手机号</td><td class="col-md-4 parent_id_parse" title="phone">${item.phone}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">短信内容</td><td class="col-md-4 parent_id_parse" title="content">${item.content}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">发送结果</td><td class="col-md-4 parent_id_parse" title="sendResult">
			<c:if test="${item.sendResult=='1' }">发送成功</c:if>
			<c:if test="${item.sendResult=='0' }">发送失败</c:if>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">发送时间</td><td class="col-md-4 date_time_parse" title="createTime">${item.createTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">备注</td><td class="col-md-4 parent_id_parse" title="remark">${item.remark}</td>
	</tr>
</table>
<script type="text/javascript">
dataOpr();
</script>
