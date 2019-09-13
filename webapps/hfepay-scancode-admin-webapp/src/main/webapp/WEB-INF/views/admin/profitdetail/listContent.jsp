<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ taglib prefix="page" uri="page" %> 
<%
   String path =  request.getContextPath();
request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered table-hover" id="sample_6">
							<thead>
							<tr>
								<th>机构编号</th>
								<th>机构名称</th>
								<th>商户编号</th>
								<th>商户名称</th>
								<th>支付通道</th>
								<th>交易类型</th>
								<th>费率类型</th>
								<th>分润依据</th>
								<th>分润类型</th>
								<th>利率差</th>
								<th>分润金额</th>
								<th>结算日期</th>
							</tr>
							</thead>
							<tbody id="tableContent">
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
						   			<td class="parent_id_parse">${item.identityNo}</td>
						   			<td class="parent_id_parse">${item.organName}</td>
						   			<td class="parent_id_parse">${item.merchantNo}</td>
						   			<td class="parent_id_parse">${item.merchantName}</td>
						   			<td class="parent_id_parse">${item.payCode}</td>
									<td class="parent_id_parse">
									<c:if test="${item.tradeType=='01'}">
									提现
									</c:if>
									<c:if test="${item.tradeType=='02'}">
									交易
									</c:if>
									</td>
									<td class="parent_id_parse">
									<c:if test="${item.tradeType=='01'}">
									T0
									</c:if>
									<c:if test="${item.tradeType=='02'}">
									T1
									</c:if>
									</td>
									
									<c:if test="${item.profitType=='01'}">
										<td class="parent_id_parse">${item.profitBase}</td>
										<td class="parent_id_parse">提现金额分润</td>
									</c:if>
									<c:if test="${item.profitType=='02'}">
										<td class="parent_id_parse">${item.profitBase}</td>
										<td class="parent_id_parse">交易金额分润</td>
									</c:if>
									<c:if test="${item.profitType=='03'}">
										<td class="parent_id_parse"><fmt:formatNumber value="${item.profitBase}" pattern="#0"/></td>
										<td class="parent_id_parse">提现次数分润</td>
									</c:if>
									<c:if test="${item.profitType=='04'}">
										<td class="parent_id_parse"><fmt:formatNumber value="${item.profitBase}" pattern="#0"/></td>
										<td class="parent_id_parse">T1结算分润</td>
									</c:if>
									<td class="parent_id_parse">${item.rateDiff}</td>
									<td class="parent_id_parse">${item.profit}</td>
									<td class="date_time_parse"><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd E"/></td>								
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
								<tr>
									<td class="page-nodata" colspan="12">查无数据！</td>
								</tr>
					</c:if>
			</tbody>
			</table>
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/>	<b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条			