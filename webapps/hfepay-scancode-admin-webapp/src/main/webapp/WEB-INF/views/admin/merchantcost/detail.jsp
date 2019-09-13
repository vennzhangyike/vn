<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">进出账流水号</td><td class="col-md-4 parent_id_parse">${item.tradeNo}</td>
		<td class="col-md-2 td0">渠道编号</td><td class="col-md-4 parent_id_parse">${item.channelNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">代理商编号</td><td class="col-md-4 parent_id_parse">
		${item.agentNo}
		</td>
		<td class="col-md-2 td0">商户编号</td><td class="col-md-4 parent_id_parse" >${item.merchantNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">支付二维码编号</td><td class="col-md-4 parent_id_parse">${item.qrCode}</td>
		<td class="col-md-2 td0">支付通道</td><td class="col-md-4 parent_id_parse" title='payCode'>${item.payCode}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">进出账类型</td><td class="col-md-4 parent_id_parse" title='type'>${item.type}</td>
		<td class="col-md-2 td0">费率类型</td><td class="col-md-4 parent_id_parse" title='payCode'>
		<c:if test="${item.rateType=='1'}">
			T0
		</c:if>
		<c:if test="${item.rateType=='2' }">
			T1
		</c:if>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">进出账金额(元)</td><td class="col-md-4 parent_id_parse"><fmt:formatNumber value="${item.amount}" pattern="#0.##"/></td>
		<td class="col-md-2 td0">进出账费率</td><td class="col-md-4 parent_id_parse" title='payCode'><fmt:formatNumber value="${item.merchantRate}" pattern="#0.#####"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">进出账固定成本金额(元)</td><td class="col-md-4 parent_id_parse"><fmt:formatNumber value="${item.fixedAmount}" pattern="#0.##"/></td>
		<td class="col-md-2 td0">商户总成本(元)</td><td class="col-md-4 parent_id_parse" title='payCode'><fmt:formatNumber value="${item.merchantCost}" pattern="#0.##"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">状态</td><td class="col-md-4 parent_id_parse">
		<c:if test="${item.status == 1}">启用</c:if>
		<c:if test="${item.status == 2}">禁用</c:if>
		</td>
		<td class="col-md-2 td0">进出帐时间</td><td class="col-md-4 date_time_parse" title='payCode'>${item.createTime}</td>
	</tr>
</table>
<script type="text/javascript">
dataOpr();
</script>
