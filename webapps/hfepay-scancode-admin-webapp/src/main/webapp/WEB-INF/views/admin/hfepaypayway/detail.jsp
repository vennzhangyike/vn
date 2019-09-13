<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">平台支付通道代码</td><td class="col-md-4 parent_id_parse">${item.payCode}</td>
		<td class="col-md-2 td0">平台支付通道名称</td><td class="col-md-4 parent_id_parse">${item.payName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">通道类型</td><td class="col-md-4 parent_id_parse">
		<c:if test="${item.payType=='1'}">
			商户入驻
		</c:if>
		<c:if test="${item.payType=='2' }">
			支付通道
		</c:if>	
		</td>
		<td class="col-md-2 td0">支付通道描述</td><td class="col-md-4 parent_id_parse" >${item.payDesc}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">T1交易费率</td><td class="col-md-4 parent_id_parse"><fmt:formatNumber value="${item.t1Rate}" pattern="#0.#####"/></td>
		<td class="col-md-2 td0">T0垫资成本</td><td class="col-md-4 parent_id_parse"><fmt:formatNumber value="${item.t0Rate}" pattern="#0.#####"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">提现手续费(元)</td><td colspan="3"><fmt:formatNumber value="${item.rate}" pattern="#0.##"/></td>
	</tr>
</table>
<script type="text/javascript">
dataOpr();
</script>
