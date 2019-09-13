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
								<th>申请机构编号</th>
								<th>机构名称</th>
								<th>申请提现金额(元)</th>
								<th>实际到账金额(元)</th>
								<!-- <th>银行开户行代码</th>
								<th>开户行银行名称（支行）</th>
								<th>银行卡号码</th>
								<th>身份证号码</th>
								<th>姓名</th>
								<th>手机号码</th>
								<th>账户类型</th> -->
								<th>提现状态</th>
								<th>操作人账号</th>
								<th>申请时间</th>
								<!-- <th>备注</th> -->
								<th>操作</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<td>${item.organNo}</td>
									<td>${item.organName}</td>
									<td>${item.balance}</td>
									<td>${item.actualAmount}</td>
									<%-- <td>${item.bankCode}</td>
									<td>${item.bankName}</td>
									<td>${item.bankCard}</td>
									<td>${item.idCard}</td>
									<td>${item.name}</td>
									<td>${item.mobile}</td>
									<td>${item.accountType}</td> --%>
									<td>
										<c:if test="${item.status == 1}">申请中</c:if>
										<c:if test="${item.status == 2}">审核通过</c:if>	
										<c:if test="${item.status == 3}">审核拒绝</c:if>	
									</td>
									<td>${item.userName}</td>
									<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd E HH:mm:ss"/></td>
									<%-- <td>${item.remark}</td> --%>
									<td>
									<c:if test="${item.status == 1}">
										<a class="audit" attr="${item.id }" organNo="${item.organNo}"><span class="label label-success">审核</span></a>
									</c:if>
									<c:if test="${item.status != 1}">
										--
									</c:if>
									</td>
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
						<tr>
							<td class="page-nodata" colspan="15">查无数据！</td>
						</tr>
					</c:if>
			</tbody>
			</table>
			
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}" totalCounts="${pager.totalCount }"/>				