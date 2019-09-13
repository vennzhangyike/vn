<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">收银员编号</td><td class="col-md-4 parent_id_parse" title="cashierNo">${item.cashierNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">门店编号</td><td class="col-md-4 parent_id_parse" title="storeNo">${item.storeNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">商户编号</td><td class="col-md-4 parent_id_parse" title="merchantNo">${item.merchantNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">姓名</td><td class="col-md-4 parent_id_parse" title="userName">${item.userName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">身份证号</td><td class="col-md-4 parent_id_parse" title="idCard">${item.idCard}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">手机号码</td><td class="col-md-4 parent_id_parse" title="mobile">${item.mobile}</td>
	</tr>
	<%-- <tr>
		<td class="col-md-2 td0">收银员微信OPENID</td><td class="col-md-4 parent_id_parse" title="openId">${item.openId}</td>
	</tr> --%>
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
