<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">提现订单编号</td><td class="col-md-4 parent_id_parse" >${item.tradeNo}</td>
		<td class="col-md-2 td0">渠道编号</td><td class="col-md-4 parent_id_parse" >${item.channelNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">代理商编号</td><td class="col-md-4 parent_id_parse" >${item.agentNo}</td>
		<td class="col-md-2 td0">商户编号</td><td class="col-md-4 parent_id_parse" >${item.merchantNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">支付通道</td><td class="col-md-4 parent_id_parse" title="payCode">${item.payCode}</td>
		<td class="col-md-2 td0">交易费率信息</td><td class="col-md-4 parent_id_parse" >${item.rateDetail}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">提现金额(元)</td><td class="col-md-4 parent_id_parse"><fmt:formatNumber value="${item.orderAmt}" pattern="#0.##"/></td>
		<td class="col-md-2 td0">提现单笔手续费(元)</td><td class="col-md-4 parent_id_parse" ><fmt:formatNumber value="${item.drawFee}" pattern="#0.##"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">交易手续费(元)</td><td class="col-md-4 parent_id_parse" ><fmt:formatNumber value="${item.tradeFee}" pattern="#0.##"/></td>
		<td class="col-md-2 td0">商户总成本(元)</td><td class="col-md-4 parent_id_parse" ><fmt:formatNumber value="${item.merchantCost}" pattern="#0.##"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">支付通道流水号</td><td colspan="3" class="col-md-4 parent_id_parse" >${item.payTradeNo}</td>
	</tr>	
	<tr>
		<td class="col-md-2 td0">交易开始时间</td><td class="col-md-4 date_time_parse" >${item.beginTime}</td>
		<td class="col-md-2 td0">交易结束时间</td><td class="col-md-4 date_time_parse" >${item.endTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">结算日期</td><td class="col-md-4" >${item.settleTime}</td>
		<td class="col-md-2 td0">商户清算时间</td><td class="col-md-4" >${item.settleMerchant}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">退款状态</td><td class="col-md-4 parent_id_parse" >
		<c:if test="${item.refundStatus=='Y'}">
			已退款
		</c:if>
		<c:if test="${item.refundStatus=='N' }">
			未退款
		</c:if>	
		</td>
		<td class="col-md-2 td0">订单状态</td><td class="col-md-4 parent_id_parse" title='resultCode'>${item.resultCode}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">批量批次号</td><td class="col-md-4 parent_id_parse" >${item.batchId}</td>
		<td class="col-md-2 td0">交易结果信息</td><td class="col-md-4 parent_id_parse">${item.resultInfo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">操作编码</td><td class="col-md-4 parent_id_parse">${item.optCode}</td>
		<td class="col-md-2 td0">记录状态</td><td class="col-md-4 parent_id_parse" >
		<c:if test="${item.recordStatus=='Y'}">
			有效
		</c:if>
		<c:if test="${item.recordStatus=='N' }">
			无效
		</c:if>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">回调时间</td><td class="col-md-4 date_time_parse">${item.updateTime}</td>
		<td class="col-md-2 td0">操作人账号</td><td class="col-md-4 parent_id_parse">${item.operator}</td>
	</tr>
</table>
<script type="text/javascript">
dataOpr();
</script>
