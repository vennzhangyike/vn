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
								<!-- <th>渠道编号</th> -->
								<th>渠道名称</th>
								<th>开户行名称</th>
								<th>银行卡号码</th>
								<th>联行号</th>
								<th>身份证号码</th>
								<th>开户名</th>
								<th>手机号码</th>
								<th>账户类型</th>
								<!-- <th>订单实时到账</th> -->
								<th>创建时间</th>
								<th>状态</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
						   			<%-- <td>${item.channelNo}</td> --%>
									<td>${item.channelName}</td>
									<td>${item.bankName}</td>
									<td>${item.bankCard}</td>
									<td>${item.bankCode}</td>
									<td>${item.idCard}</td>
									<td>${item.name}</td>
									<td>${item.mobile}</td>
									<td class="parent_id_parse" title="accountType">${item.accountType}</td>
									<%-- <td class="parent_id_parse">
									<c:if test="${item.isRealAccount=='Y'}">
										是
									</c:if>
									<c:if test="${item.isRealAccount == null || item.isRealAccount=='N' }">
										否
									</c:if>	
									</td>	 --%>							
									<td class="date_time_parse">${item.createTime}</td>									
									<td class="parent_id_parse">
									<c:if test="${item.status=='1'}">
										有效
									</c:if>
									<c:if test="${item.status=='2'}">
										无效
									</c:if>
									</td>
							<td>
								<a onclick="javascript:todetail('${item.id}');"><span class="label label-default">查看详情</span></a>
								<a href="${path}/adminManage/channelbankcard/${item.id}"><span class="label label-info">修改</span></a>
								<a href="${path}/adminManage/channelbankcard/link/${item.id}/${item.channelNo}"><span class="label label-primary">变更记录</span></a>
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
								   url: "channelbankcard/detail/"+id,
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
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/>	<b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条			