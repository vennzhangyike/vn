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
								<th>支付通道</th>
								<th>交易类型</th>
								<th>交易金额(元)</th>
								<th>费率类型</th>
								<th>分润金额(元)</th>
								<!-- <th>结算状态</th> -->
								<th>结算日期</th>
							</tr>
							</thead>
							<tbody id="tableContent">
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
						   			<td class="parent_id_parse">${item.organNo}</td>
						   			<td class="parent_id_parse">${item.orgName}</td>
						   			<td class="parent_id_parse">${item.payCode}</td>
									<td class="parent_id_parse">
									<c:if test="${item.type=='1'}">
									交易
									</c:if>
									<c:if test="${item.type=='2'}">
									提现
									</c:if>
									</td>
						   			<td class="parent_id_parse">${item.amount}</td>
									<td class="parent_id_parse">
									<c:if test="${item.rateType=='1'}">
									T0
									</c:if>
									<c:if test="${item.rateType=='2'}">
									T1
									</c:if>
									</td>
									<td class="parent_id_parse">${item.profitAmount}</td>
									<%-- <td class="parent_id_parse">
									<c:if test="${item.settleStatus=='0'}">
										 未结算 
									</c:if>
									<c:if test="${item.settleStatus=='1'}">
										已结算
									</c:if>
									</td>	 --%>
									<td class="date_time_parse">${item.transDate}</td>								
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
								<tr>
									<td class="page-nodata" colspan="9">查无数据！</td>
								</tr>
					</c:if>
			</tbody>
			</table>
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/>	<b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条			