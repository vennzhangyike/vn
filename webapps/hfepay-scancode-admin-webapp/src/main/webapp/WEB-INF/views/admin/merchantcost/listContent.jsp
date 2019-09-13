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
					<div class="ico ico2"></div>
					<div class="data" id="bpos_money_s">
						<p>提现总金额(元)</p>
						<strong><fmt:formatNumber value="${amtStatic.txTotalAmt}" pattern="#0.##"/></strong>
					</div>
				</td>
				<td class="last">
					<div class="ico ico5"></div>
					<div class="data" id="bpos_money_s">
						<p>总成本(元)</p>
						<strong><fmt:formatNumber value="${amtStatic.totalCost}" pattern="#0.##"/></strong>
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
			</tr>
		</tbody>
	</table>
</div>

<div class="portlet-title">
	<div class="caption">
		<i class="fa fa-globe"></i>商户成本列表
	</div>
</div>
			
<table class="table table-striped table-bordered table-hover" id="sample_6">
							<thead>
							<tr>
								<th>进出账流水号</th>
								<th>渠道名称</th>
								<th>代理商名称</th>
								<th>商户名称</th>
								<th>支付通道</th>
								<th>进出账类型</th>
								<!-- <th>费率类型</th> -->
								<th>进出账金额(元)</th>
								<!-- <th>进出账费率</th>
								<th>固定成本金额(元)</th> -->
								<th>交易成本(元)</th>
								<!-- <th>状态</th> -->
								<th>进出帐时间</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<td>${item.tradeNo}</td>
									<td>${item.channelName}</td>
									<td>${item.agentName}</td>
									<td>${item.merchantName}</td>
									<td class="parent_id_parse" title='payCode'>${item.payCode}</td>
									<td class="parent_id_parse" title='type'>${item.type}</td>
									<%-- <td>
									<c:if test="${item.rateType=='1'}">
										T0
									</c:if>
									<c:if test="${item.rateType=='2' }">
										T1
									</c:if>	
									</td> --%>
									<td><fmt:formatNumber value="${item.amount}" pattern="#0.##"/></td>
									<%-- <td><fmt:formatNumber value="${item.merchantRate}" pattern="#0.#####"/></td>
									<td><fmt:formatNumber value="${item.fixedAmount}" pattern="#0.##"/></td> --%>
									<td><fmt:formatNumber value="${item.merchantCost}" pattern="#0.##"/></td>
									<%-- <td>
									<c:if test="${item.status == 1}">启用</c:if>
									<c:if test="${item.status == 2}">禁用</c:if>
									</td> --%>
									<td class="date_time_parse">${item.createTime}</td>

							<td>
								<a onclick="javascript:todetail('${item.id}');"><span class="label label-default">查看详情</span></a>
							</td> 
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
								<tr>
									<td class="page-nodata" colspan="13">查无数据！</td>
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
					   url: "merchantcost/detail/"+id,
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
			</script>
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/><b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条</b>	
			<%-- <page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"  totalCounts="${pager.totalCount }"/>	 --%>		
				