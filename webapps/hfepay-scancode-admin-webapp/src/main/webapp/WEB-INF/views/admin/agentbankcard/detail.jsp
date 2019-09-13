<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">代理商编号</td><td class="col-md-4 parent_id_parse" title="agentNo">${item.agentNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">银行开户行代码</td><td class="col-md-4 parent_id_parse" title="bankCode">${item.bankCode}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">开户行银行名称（支行）</td><td class="col-md-4 parent_id_parse" title="bankName">${item.bankName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">银行卡号码</td><td class="col-md-4 parent_id_parse" title="bankCard">${item.bankCard}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">身份证号码</td><td class="col-md-4 parent_id_parse" title="idCard">${item.idCard}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">开户名</td><td class="col-md-4 parent_id_parse" title="name">${item.name}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">手机号码</td><td class="col-md-4 parent_id_parse" title="mobile">${item.mobile}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">是否对公对私</td>
		<c:if test="${item.accountType=='0'}">
			<td class="col-md-4 parent_id_parse" title="accountType">对私</td>
		</c:if>
		<c:if test="${item.accountType=='1'}">
			<td class="col-md-4 parent_id_parse" title="accountType">对公</td>
		</c:if>
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
