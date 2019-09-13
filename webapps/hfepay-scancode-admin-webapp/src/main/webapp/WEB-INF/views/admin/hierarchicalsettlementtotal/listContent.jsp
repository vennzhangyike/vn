<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
					<div class="ico ico2"></div>
					<div class="data" id="bpos_money_s">
						<p>总分润(元)</p>
						<strong><fmt:formatNumber value="${amtStatic.zfrTotalAmt}" pattern="#0.##"/></strong>
					</div>
				</td>
				<td>
					<div class="ico ico2"></div>
					<div class="data" id="bpos_money_s">
						<p>T1清算金额(元)</p>
						<strong><fmt:formatNumber value="${amtStatic.t1qsjeTotalAmt}" pattern="#0.##"/></strong>
					</div>
				</td>
				<td>
					<div class="ico ico2"></div>
					<div class="data" id="bpos_money_s">
						<p>T1交易分润(元)</p>
						<strong><fmt:formatNumber value="${amtStatic.t1jyfrTotalAmt}" pattern="#0.##"/></strong>
					</div>
				</td>
				<td>
					<div class="ico ico2"></div>
					<div class="data" id="bpos_money_s">
						<p>T0清算金额(元)</p>
						<strong><fmt:formatNumber value="${amtStatic.t0qsjeTotalAmt}" pattern="#0.##"/></strong>
					</div>
				</td>
				<td>
					<div class="ico ico2"></div>
					<div class="data" id="bpos_money_s">
						<p>T0交易分润(元)</p>
						<strong><fmt:formatNumber value="${amtStatic.t0jyfrTotalAmt}" pattern="#0.##"/></strong>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<table class="table table-striped table-bordered table-hover" id="sample_6">
							<thead>
							<tr>
								<th>结算批次号</th>
								<th>渠道编号</th>
								<th>渠道名称</th>
								<th>代理商编号</th>
								<th>代理商名称</th>
								<th>代理商级别</th>
								<th>上级名称</th> 
								<th>T1清算金额(元)</th>
								<th>T1交易分润(元)</th>
								<th>T0清算金额(元)</th>
								<th>T0交易分润(元)</th>
								<th>总分润(元)</th>
								<th>结算日期</th> 
								<th>操作</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<td>${item.batchNo}</td>
									<td>${item.channelNo}</td>
									<td>${item.channelName}</td>
									<td>${item.agentNo}</td>
									<td>${item.agentName}</td>
									<td>${item.agentLevel}</td>
									<td>${item.parentName}</td> 
									<td>
									<c:if test="${empty item.tranTotalAmount}">0</c:if>
									<c:if test="${!empty item.tranTotalAmount}">${item.tranTotalAmount}</c:if>
									</td>
									<td>
									<c:if test="${empty item.tranProfit}">0</c:if>
									<c:if test="${!empty item.tranProfit}">${item.tranProfit}</c:if>
									</td>
									<td>
									<c:if test="${empty item.outTotalAmount}">0</c:if>
									<c:if test="${!empty item.outTotalAmount}">${item.outTotalAmount}</c:if>
									</td>
									<td>
									<c:if test="${empty item.outProfit}">0</c:if>
									<c:if test="${!empty item.outProfit}">${item.outProfit}</c:if>
									</td>
									<td>${item.totalProfit}</td>
									 <td>${item.settleDate}</td>
									<td>
										<a onclick="javascript:listdetail('${item.channelNo}','${item.agentNo}','${item.settleDate}');"><span class="label label-default">分润明细</span></a>
									</td>
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
						<tr>
							<td class="page-nodata" colspan="14">查无数据！</td>
						</tr>
					</c:if>
			</tbody>
			</table>
			<script type="text/javascript">
				
				//分润明细
				function listdetail(channelNo,agentNo,settleDate){
					var organNo="";
					if(agentNo != ""){
						organNo = agentNo;
					}else{
						organNo = channelNo;
					}
					$.ajax({
						   type: "POST",
						   url: "hierarchicalsettlementtotal/list/detail",
						   data:{"organNo":organNo,"settleDate":settleDate}, 
						   success: function(msg){
							   var btn = {success:{   
							       label: "关闭",
							       className: "btn-success",
							       callback: function() {
							       }
							     }};
							   bootbox.dialog({
								   message: msg,
								   title: "详情列表",
								   onEscape: function() {},
								   className: "green-haze",
								   buttons: btn
								 });
						   }
					});
				}
			</script>
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/>	<b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条