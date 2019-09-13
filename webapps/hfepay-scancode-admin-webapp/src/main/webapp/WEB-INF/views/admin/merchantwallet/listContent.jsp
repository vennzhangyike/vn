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
								<th>可用余额(元)</th>
								<th>冻结余额(元)</th>
								<th>是否有效</th>
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
									<td class="parent_id_parse" title="balance">${item.balance}</td>
									<td class="parent_id_parse" title="freezesAmt">${item.freezesAmt}</td>
									<td class="parent_id_parse" title="status">${item.status}</td>
							<td>
								<a onclick="javascript:todetail('${item.id}');"><span class="label label-default">查看详情</span></a>
						<!--  		<a href="${path}/adminManage/merchantwallet/${item.id}"><span class="label label-info">修改</span></a>
								<a onclick="javascript:del('${item.id}');"><span class="label label-danger">删除</span></a>
						   			<c:if test="${item.status == 0}"><a onclick="javascript:updateStatus(this);" status="1" value="${item.id}"><span class="label label-success">启用</span></a></c:if>
									<c:if test="${item.status == 1}"><a onclick="javascript:updateStatus(this);" status="0" value="${item.id}"><span class="label label-danger">禁用</span></a></c:if>	
						-->
							</td> 
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
						<tr>
							<td class="page-nodata" colspan="6">查无数据！</td>
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
								   url: "merchantwallet/detail/"+id,
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
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/><b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条				