<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">
	
	<tr>
		<td class="col-md-2 td0">支付订单编号</td><td colspan="3" >${item.payNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">交易订单编号</td><td class="col-md-4 parent_id_parse" >${item.tradeNo}</td>
		<td class="col-md-2 td0">渠道编号</td><td class="col-md-4 parent_id_parse" >${item.channelNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">代理商编号</td><td class="col-md-4 parent_id_parse">${item.agentNo}</td>
		<td class="col-md-2 td0">商户编号</td><td class="col-md-4 parent_id_parse" >${item.merchantNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">支付二维码编号</td><td class="col-md-4 parent_id_parse" >${item.qrCode}</td>
		<td class="col-md-2 td0">支付通道</td><td class="col-md-4 parent_id_parse" title="payCode">${item.payCode}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">交易订单类型</td><td class="col-md-4 parent_id_parse">
		<c:if test="${item.tradeType=='01'}">
			提现
		</c:if>
		<c:if test="${item.tradeType=='02' }">
			收款
		</c:if>	
		</td>
		<td class="col-md-2 td0">订单金额(元)</td><td class="col-md-4 parent_id_parse"><fmt:formatNumber value="${item.orderAmt}" pattern="#0.##"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">订单原交易金额(元)</td><td class="col-md-4 parent_id_parse"><fmt:formatNumber value="${item.tradeAmt}" pattern="#0.##"/></td>
		<td class="col-md-2 td0">打折金额(元)</td><td class="col-md-4 parent_id_parse" ><fmt:formatNumber value="${item.discountAmt}" pattern="#0.##"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">打折策略</td><td  colspan="3">
		${item.discountStrategy}
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">商品名称</td><td class="col-md-4 parent_id_parse" >${item.productName}</td>
		<td class="col-md-2 td0">商品描述</td><td class="col-md-4 parent_id_parse" >${item.productDesc}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">支付的账号id</td><td class="col-md-4 parent_id_parse" >${item.buyerId}</td>
		<td class="col-md-2 td0">支付账号</td><td class="col-md-4 parent_id_parse" >${item.buyerAccount}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">支付额度(元)</td><td class="col-md-4 parent_id_parse" ><fmt:formatNumber value="${item.totalAmount}" pattern="#0.##"/></td>
		<td class="col-md-2 td0">用户支付的款(元)</td><td class="col-md-4 parent_id_parse" ><fmt:formatNumber value="${item.buyerPayAmount}" pattern="#0.##"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">积分优惠(元)</td><td class="col-md-4 parent_id_parse" ><fmt:formatNumber value="${item.pointAmount}" pattern="#0.##"/></td>
		<%-- <td class="col-md-2 td0">商户交易费率(%)</td><td class="col-md-4 parent_id_parse" ><fmt:formatNumber value="${item.merchantRate}" pattern="#0.#####"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">渠道交易费率(%)</td><td class="col-md-4 parent_id_parse" ><fmt:formatNumber value="${item.channelRate}" pattern="#0.#####"/></td>
		<td class="col-md-2 td0">成本交易费率(%)</td><td class="col-md-4 parent_id_parse" ><fmt:formatNumber value="${item.payRate}" pattern="#0.#####"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">代理商交易费率(%)</td><td class="col-md-4 parent_id_parse" ><fmt:formatNumber value="${item.agentRate}" pattern="#0.#####"/></td> --%>
		<td class="col-md-2 td0">业务类型 </td><td class="col-md-4 parent_id_parse" >
		<c:if test="${item.businessType=='0'}">
			基础商品类业务
		</c:if>
		<c:if test="${item.businessType=='1' }">
			普通消费类业务
		</c:if>	
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">支付方式</td><td class="col-md-4 parent_id_parse" >
		<c:if test="${item.payType=='0000'}">
			钱包支付
		</c:if>
		<c:if test="${item.payType=='0001'}">
			支付宝支付
		</c:if>
		<c:if test="${item.payType=='0002' }">
			微信支付
		</c:if>	
		<c:if test="${item.payType=='0003' }">
			银联支付
		</c:if>
		</td>
		<td class="col-md-2 td0">是否是主支付订单</td><td class="col-md-4 parent_id_parse">
		<c:if test="${item.isMainPay=='0'}">
			否
		</c:if>
		<c:if test="${item.isMainPay=='1'}">
			是
		</c:if>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">账务账期</td><td class="col-md-4 parent_id_parse" >${item.accountTime}</td>
		<td class="col-md-2 td0">异常编码</td><td class="col-md-4 parent_id_parse" >${item.errorCode}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">异常信息</td><td class="col-md-4 parent_id_parse" >${item.errorMsg}</td>
		<td class="col-md-2 td0">支付通道流水号</td><td class="col-md-4 parent_id_parse" >${item.payTradeNo}</td>
	</tr>
	<%-- <tr>
		<td class="col-md-2 td0">后台异步通知url</td><td colspan="3" title="${item.notifyUrl}">${item.notifyUrl}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">页面回调通知url</td><td colspan="3"  title="${item.returnUrl}">${item.returnUrl}</td>
	</tr> --%>
	<tr>
		<td class="col-md-2 td0">退款状态</td><td class="col-md-4 parent_id_parse" >
		<c:if test="${item.refundStatus=='Y'}">
			已退款
		</c:if>
		<c:if test="${item.refundStatus=='N' }">
			未退款
		</c:if>	
		</td>
		<td class="col-md-2 td0">支付状态</td><td class="col-md-4 parent_id_parse" >
		<c:if test="${item.payStatus=='00'}">
			等待支付
		</c:if>
		<c:if test="${item.payStatus=='01' }">
			渠道支付中
		</c:if>	
		<c:if test="${item.payStatus=='02' }">
			支付成功
		</c:if>	
		<c:if test="${item.payStatus=='03' }">
			支付失败
		</c:if>	
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">批量批次号</td><td colspan="3" >${item.batchId}</td>
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
