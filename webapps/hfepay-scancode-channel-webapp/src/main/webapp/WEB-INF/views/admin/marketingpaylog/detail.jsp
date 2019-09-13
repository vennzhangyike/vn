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
		<td class="col-md-2 td0">代理商编号</td><td class="col-md-4 parent_id_parse" title="organNo">${item.organNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">商户编号</td><td class="col-md-4 parent_id_parse" title="merchantNo">${item.merchantNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">营销交易订单号</td><td class="col-md-4 parent_id_parse" title="markTradeNo">${item.markTradeNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">交易订单号</td><td class="col-md-4 parent_id_parse" title="tradeNo">${item.tradeNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">交易金额</td><td class="col-md-4 parent_id_parse" title="tradeAmt">${item.tradeAmt}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">优惠金额</td><td class="col-md-4 parent_id_parse" title="discountAmt">${item.discountAmt}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">实付金额</td><td class="col-md-4 parent_id_parse" title="paidAmt">${item.paidAmt}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">收银员编号</td><td class="col-md-4 parent_id_parse" title="cashierNo">${item.cashierNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">创建日期</td><td class="col-md-4 parent_id_parse" title="createTime">${item.createTime}</td>
	</tr>
	
	<tr>
		<td class="col-md-2 td0">状态</td>
		<c:if test="${item.status=='00'}">
			<td class="col-md-4 parent_id_parse" title="status">等待付款</td>
		</c:if>
		<c:if test="${item.status=='01'}">
			<td class="col-md-4 parent_id_parse" title="status">交易成功</td>
		</c:if>
		<c:if test="${item.status=='03'}">
			<td class="col-md-4 parent_id_parse" title="status">交易失败</td>
		</c:if>
	</tr>
	
	
</table>
<script type="text/javascript">
dataOpr();
</script>
