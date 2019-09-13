<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="page" uri="page" %> 
<%
   String path =  request.getContextPath();
request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered table-hover" id="sample_6">
							<thead>
							<tr>
								<th width = "10%">机构编号</th>
								<th>机构名称</th>
								<th>利润总额(元)</th>
								<th>提现总额(元)</th>
								<th>钱包余额(元)</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<td>${item.organNo}</td>
									<td>${item.organName}</td>
									<td><fmt:formatNumber value="${item.totalProfit}" pattern="#0.##"/></td>
									<td><fmt:formatNumber value="${item.totalActualAmount}" pattern="#0.##"/></td>
									<td><fmt:formatNumber value="${item.balance}" pattern="#0.##"/></td>
									<td>
										<a onclick="javascript:addtabsByUrl('/adminManage/hierarchicalsettlementtotal','${item.organNo}') ;"><span class="label label-primary">分润明细</span></a>
										<a onclick="javascript:addtabsByUrl('/adminManage/organwithdrawals','${item.organNo}');"><span class="label label-primary" >提现明细</span></a>
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
			
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}" totalCounts="${pager.totalCount}"/>