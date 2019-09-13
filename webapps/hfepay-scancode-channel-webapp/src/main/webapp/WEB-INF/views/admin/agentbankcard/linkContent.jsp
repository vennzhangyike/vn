<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="page" %> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
   String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<div class="page-container">
			<div class="row">
				<div class="col-md-12">
					<div class="portlet box green-haze">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-globe"></i>代理商账户信息变更记录
							</div>
						</div>
						<div class="portlet-body" id="tablec">
							<table class="table table-striped table-bordered table-hover" id="sample_6">
							<thead>
							<tr>
								<td>代理商编号</td>
								<td>代理商名称</td>
								<td>所属机构</td>
								<td>支付通道名称</td>
								
								<td>银行卡号码</td>
								<td>身份证号码</td>
								<td>开户名</td>
								<td>手机号码</td>
								<td>账户类型</td>
								<td>修改时间</td>
								</tr>
							</thead>
							<tbody id="tableContent">
							
						   <c:forEach var="item" items="${pager.result}"> 
						   <tr>
								<td>${item.agentNo}</td>
								<td>${item.agentName}</td>
								<td>${item.bankCode}</td>
								<td>${item.bankName}</td>
								<td>${item.bankCard}</td>
								<td>${item.idCard}</td>
								<td>${item.name}</td>
								<td>${item.mobile}</td>
								<td class="parent_id_parse">
								<c:if test="${item.accountType=='1'}">
									公司
								</c:if>
								<c:if test="${item.accountType=='0' }">
									个人
								</c:if>	
								</td>
								<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd E HH:mm:ss"/></td>		
						  </tr>
						</c:forEach>
						</tbody>
						</table>
						</div>
						<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/><b class="totalCountInPage">共&nbsp;${pager.totalCount}&nbsp;条
						<div class="portlet box ">
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal" id="form">
						<div class="form-body">
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2"></label>
										<div class="col-md-10">
											<button type="button" class="btn btn-default" id="return" style="float:right;">返回</button>
										</div>
									</div>
								</div>
								<!--/span-->
							</div>
						</div>
					</form>
					<!-- END FORM-->
				</div>
			</div>
					</div>
				</div>
			</div>
</div>

<div style="display: none;" id="baseUrl">${path}</div>
