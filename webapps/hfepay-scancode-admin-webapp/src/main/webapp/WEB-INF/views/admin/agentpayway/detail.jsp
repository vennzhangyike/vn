<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">代理商编号</td><td class="col-md-4 parent_id_parse" title="agentNo">${item.agentNo}</td>
	</tr>
	<%-- <tr>
		<td class="col-md-2 td0">支付通道代码</td><td class="col-md-4 parent_id_parse" title="payCode">${item.payCode}</td>
	</tr> --%>
	<tr>
		<td class="col-md-2 td0">支付通道名称</td><td class="col-md-4 parent_id_parse" title="payName">${item.payName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">T1交易费率</td><td class="col-md-4 parent_id_parse" title="t1Rate"><fmt:formatNumber value="${item.t1Rate}" pattern="#0.#####"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">T0垫资成本</td><td class="col-md-4 parent_id_parse" title="t0Rate"><fmt:formatNumber value="${item.t0Rate}" pattern="#0.#####"/></td>
	</tr>	
	<tr>
		<td class="col-md-2 td0">提现手续费(元)</td><td class="col-md-4 parent_id_parse" title="rate"><fmt:formatNumber value="${item.rate}" pattern="#0.#####"/></td>
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
