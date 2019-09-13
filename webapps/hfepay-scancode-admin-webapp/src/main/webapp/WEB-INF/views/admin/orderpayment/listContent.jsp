<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="page" uri="page" %> 
<%
   String path =  request.getContextPath();
request.setAttribute("path", path);
%>
						
<div class="data_collect2">
	<table>
		<tbody>
			<tr>
				<td>
					<div class="ico ico1"></div>
					<div class="data" id="bpos_money_s">
						<p>交易总金额(元)</p>
						<strong><fmt:formatNumber value="${amtStatic.orderTotalAmt}" pattern="#0.##"/></strong>
					</div>
				</td>	
				<td>
					<div class="ico ico1"></div>
					<div class="data" id="bpos_money_s">
						<p>信用卡交易金额(元)</p>
						<strong><fmt:formatNumber value="${amtStatic.cardTotalAmt}" pattern="#0.##"/></strong>
					</div>
				</td>	
				<td>
					<div class="ico ico1"></div>
					<div class="data" id="bpos_money_s">
						<p>其他交易金额(元)</p>
						<strong><fmt:formatNumber value="${amtStatic.otherTotalAmt}" pattern="#0.##"/></strong>
					</div>
				</td>				
				<td>
					<div class="ico ico2"></div>
					<div class="data" id="bpos_money_s">
						<p>支付宝(元)</p>
						<strong><fmt:formatNumber value="${amtStatic.zfbTotalAmt}" pattern="#0.##"/></strong>
					</div>
				</td>
				<td>
					<div class="ico ico2"></div>
					<div class="data" id="bpos_money_s">
						<p>微信公众号(元)</p>
						<strong><fmt:formatNumber value="${amtStatic.wxgzhTotalAmt}" pattern="#0.##"/></strong>
					</div>
				</td>
				<td>
					<div class="ico ico2"></div>
					<div class="data" id="bpos_money_s">
						<p>微信扫码付(元)</p>
						<strong><fmt:formatNumber value="${amtStatic.wxTotalAmt}" pattern="#0.##"/></strong>
					</div>
				</td>	
				<td>
					<div class="ico ico2"></div>
					<div class="data" id="bpos_money_s">
						<p>QQ支付(元)</p>
						<strong><fmt:formatNumber value="${amtStatic.qqTotalAmt}" pattern="#0.##"/></strong>
					</div>
				</td>							
			</tr>
		</tbody>
	</table>
</div>					
		
<div class="portlet-title">
	<div class="caption">
		<i class="fa fa-globe"></i>交易订单列表
	</div>
</div>
						
<table class="table table-striped table-bordered table-hover" id="sample_6">
							<thead>
							<tr>
								<th>交易订单编号</th>
								<!-- <th>渠道编号</th> -->
								<th>渠道名称</th>
								<!-- <th>代理商编号</th> -->
								<th>代理商名称</th>
								<!-- <th>商户编号</th> -->
								<th>商户名称</th>
								<th>门店名称</th>
								<th>收款人编号</th>
								<th>收款人名称</th>
								<!-- <th>支付二维码编号</th> -->
								<th>支付通道</th>
								<th>订单金额(元)</th>
								<!-- <th>商品名称</th> -->
								<th>交易时间</th>
								<!-- <th>结束时间</th> -->
								<th>订单状态</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<td>${item.tradeNo}</td>
									<%-- <td>${item.channelNo}</td> --%>
									<td>${item.channelName}</td>
									<%-- <td>${item.agentNo}</td> --%>
									<td>${item.agentName}</td>
									<%-- <td>${item.merchantNo}</td> --%>
									<td>${item.merchantName}</td>
									<td>${item.storeName}</td>
									<td>${item.cashierNo}</td>
									<td>${item.cashierName}</td>
									<%-- <td>${item.qrCode}</td> --%>
									<td class="parent_id_parse" title="payCode">${item.payCode}</td>
									<td>${item.orderAmt}</td>
									<%-- <td>${item.productName}</td> --%>
									<td class="date_time_parse">${item.beginTime}</td>
									<%-- <td class="date_time_parse">${item.endTime}</td> --%>
									<td>
									<c:if test="${item.orderStatus=='00'}">
										等待付款
									</c:if>
									<c:if test="${item.orderStatus=='01' }">
										交易成功
									</c:if>	
									<c:if test="${item.orderStatus=='02' }">
										交易失败
									</c:if>	
									</td>
							<td>
								<a onclick="javascript:todetail('${item.id}');"><span class="label label-default">查看详情</span></a>
								<%-- <a onclick="javascript:printView('${item.id}');"><span class="label label-primary">打印</span></a> --%>
							</td> 
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
								<tr>
									<td class="page-nodata" colspan="12">查无数据！</td>
								</tr>
					</c:if>
			</tbody>
			</table>
			<script type="text/javascript">
			dataOpr();
						//显示详情
						function todetail(id){
							$.ajax({
								   type: "POST",
								   url: "orderpayment/detail/"+id,
								   success: function(msg){
									   var btn = {success:{   
									       label: "关闭",
									       className: "btn-default",
									       callback: function() {
									       }
									     }};
									   bootbox.dialog({
										   message: msg,
										   title: "详情列表",
										   onEscape: function() {},
										   className: "dialog-class",
										   buttons: btn
										 });
								   }
							});
						}
						//打印
						function printView(id){ 
							$.ajax({
								   type: "GET",
								   url: "orderpayment/print/"+id,
								   success: function(msg){
									   msg = $.parseJSON(msg);
									   if(msg.executeStatus == 0){
										   
										   var oper = 1;
										   html=window.document.body.innerHTML;
										   if(!!msg.merchantInfo.merchantName){
											   $("#merchantNameStr").text(msg.merchantInfo.merchantName);
										   }	
										   if(!!msg.merchantInfo.phone){
											   $("#phoneStr").text($("#phoneStr").text() + msg.merchantInfo.phone);
										   }
										   if(!!msg.merchantInfo.merchantNo){
											   $("#merchantNoStr").text($("#merchantNoStr").text() + msg.merchantInfo.merchantNo);
										   }
										   if(!!msg.payName){
											   $("#payCodeStr").text($("#payCodeStr").text() + msg.payName);
										   }
										   if(!!msg.orderPayment.tradeNo){
											   $("#tradeNoStr").text($("#tradeNoStr").text() + msg.orderPayment.tradeNo);
										   }
										   if(!!msg.orderStatus){
											   $("#orderStatusStr").text($("#orderStatusStr").text() + msg.orderStatus);
										   }
										   if(!!msg.orderTimeStr){
											   $("#orderTimeStr").text($("#orderTimeStr").text() + msg.orderTimeStr);	
										   }										   									   
										   if(!!msg.orderPayment.cashierNo){
											   $("#cashierNoStr").text($("#cashierNoStr").text() + msg.orderPayment.cashierNo);
										   }
										   if(!!msg.orderPayment.orderAmt){
											   $("#orderAmtStr").text($("#orderAmtStr").text() + msg.orderPayment.orderAmt);
										   }										   
										   bdhtml=window.document.body.innerHTML;//获取当前页的html代码 
									       sprnstr="<!--startprint"+oper+"-->";//设置打印开始区域 
									       eprnstr="<!--endprint"+oper+"-->";//设置打印结束区域 
									       prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+18); //从开始代码向后取html 
					                       
									       prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));//从结束代码向前取html 
									       window.document.body.innerHTML=prnhtml; 
									       window.print(); 
									       window.document.body.innerHTML=html; 
									   }
								   }
							});
								
						} 
			</script>
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/>				