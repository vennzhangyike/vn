<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">渠道编号</td><td class="col-md-4 parent_id_parse">${item.channelNo}</td>
		<td class="col-md-2 td0">渠道名称</td><td class="col-md-4 parent_id_parse" >${item.channelName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">开户名</td><td class="col-md-4 parent_id_parse">${item.name}</td>
		<td class="col-md-2 td0">身份证号码</td><td class="col-md-4 parent_id_parse" >${item.idCard}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">手机号码</td><td class="col-md-4 parent_id_parse" >${item.mobile}</td>
		<td class="col-md-2 td0">联行号</td><td class="col-md-4 parent_id_parse" >${item.bankCode}</td>
	</tr>
	<tr>
		
		<td class="col-md-2 td0">开户行名称</td><td class="col-md-4 parent_id_parse" >${item.bankName}</td>
		<td class="col-md-2 td0">银行卡号码</td><td class="col-md-4 parent_id_parse" >${item.bankCard}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">账户类型</td>
		<td colspan="3" class="parent_id_parse" title="accountType">${item.accountType}</td>
	</tr>
</table>
<script type="text/javascript">
dataOpr();
</script>
