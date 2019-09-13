<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="page" %> 
<%
   String path =  request.getContextPath();
request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered table-hover" id="sample_6">
							<thead>
							<tr>
								
								<th>商户编号</th>
								<th>商户名称</th>
								<th>开户行名称</th>
								<th>银行卡号</th>
								<th>联行号</th>
								<th>开户名</th>
								<th>手机号码</th>
								<th>是否开通提现</th>
								<th>账户类型</th>
								<th>状态</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<td class="parent_id_parse" title="merchantNo">${item.merchantNo}</td>
									<td class="parent_id_parse" title="merchantName">${item.merchantName}</td>
									<td class="parent_id_parse" title="bankName">${item.bankName}</td>
									<td class="parent_id_parse" title="bankCard">${item.bankCard}</td>
									<td class="parent_id_parse" title="bankCode">${item.bankCode}</td>
									<td class="parent_id_parse" title="name">${item.name}</td>
									<td class="parent_id_parse" title="mobile">${item.mobile}</td>
									<td class="parent_id_parse" title="isRealAccount">
										<c:if test="${item.isRealAccount=='Y' }">开通</c:if>
										<c:if test="${item.isRealAccount=='N' }">不开通</c:if>
									</td>
									<td class="parent_id_parse" title="accountType">${item.accountType}</td>
									<td class="parent_id_parse" title="status">
										${item.status}
									</td>

							<td>
								<a onclick="javascript:todetail('${item.id}');"><span class="label label-default">查看详情</span></a>
								<c:if test="${ item.status != 5}"><a href="${path}/adminManage/merchantbankcard/${item.id}"><span class="label label-info">修改</span></a></c:if>
								<a href="${path}/adminManage/merchantbankcardchange/content?merchantNo=${item.merchantNo}"><span class="label label-info">变更记录</span></a>
						   		<c:if test="${item.status == 1 || item.status == 3}"><a onclick="javascript:updateStatus(this);" status="2" value="${item.id}"><span class="label label-danger">禁用</span></a></c:if>
								<c:if test="${ item.status == 2 || item.status == 4}"><a onclick="javascript:updateStatus(this);" status="3" value="${item.id}"><span class="label label-success">启用</span></a></c:if>	
							</td> 
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
						<tr>
							<td class="page-nodata" colspan="11">查无数据！</td>
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
								   url: "merchantbankcard/detail/"+id,
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
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}" totalCounts="${pager.totalCount }"/>