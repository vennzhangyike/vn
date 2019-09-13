<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">商户编号</td><td class="col-md-4 parent_id_parse" title="merchantNo">${item.merchantNo}</td>
		<td class="col-md-2 td0">商户名称</td><td class="col-md-4 parent_id_parse" title="merchantName">${item.merchantName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">身份证号码</td><td class="col-md-4 parent_id_parse" title="idCard">${item.idCard}</td>
		<td class="col-md-2 td0">开户名</td><td class="col-md-4 parent_id_parse" title="name">${item.name}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">银行开户行代码</td><td class="col-md-4 parent_id_parse" title="bankCode">${item.bankCode}</td>
		<td class="col-md-2 td0">银行名称</td><td class="col-md-4 parent_id_parse" title="bankName">${item.bankName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">银行卡号</td><td class="col-md-4 parent_id_parse" title="bankCard">${item.bankCard}</td>
		<td class="col-md-2 td0">手机号码</td><td class="col-md-4 parent_id_parse" title="mobile">${item.mobile}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">是否开通提现</td><td class="col-md-4 parent_id_parse" title="isRealAccount">
			<c:if test="${item.isRealAccount=='Y' }">开通</c:if>
			<c:if test="${item.isRealAccount=='N' }">不开通</c:if>
		</td>
		<td class="col-md-2 td0">状态</td><td class="col-md-4 parent_id_parse" title="status">${item.status}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">入网时间</td><td class="col-md-4 date_time_parse" title="createTime">${item.createTime}</td>
		<td class="col-md-2 td0">修改时间</td><td class="col-md-4 date_time_parse" title="updateTime">${item.updateTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">操作人账号</td><td class="col-md-4 parent_id_parse" title="operator">${item.operatorName}</td>
		<td class="col-md-2 td0">备注</td><td class="col-md-4 parent_id_parse" title="remark">${item.remark}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0" >账户类型</td><td colspan="3" class="col-md-4 parent_id_parse" title="accountType">${item.accountType}</td>
	</tr>
</table>
<script type="text/javascript">
dataOpr();
</script>
