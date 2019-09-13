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
								<th>二维码编号</th>
								<!-- <th>渠道编号</th>
								<th>渠道名称</th> -->
								<th>代理商编号</th>
								<th>代理商名称</th>
								<!-- <th>商户编号</th> -->
								<th>商户名称</th>
								<th>使用状态</th>
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
						   			<td class="parent_id_parse">${item.qrCode}</td>
						   			<%-- <td class="parent_id_parse">${item.channelNo}</td>
									<td class="parent_id_parse">${item.channelName}</td> --%>
						   			<td class="parent_id_parse">${item.agentNo}</td>
									<td class="parent_id_parse">${item.agentName}</td>
						   			<%-- <td class="parent_id_parse">${item.merchantNo}</td> --%>
									<td class="parent_id_parse">${item.merchantName}</td>
									<td class="parent_id_parse">
									<c:if test="${item.useStatus=='1'}">
										已使用
									</c:if>
									<c:if test="${item.useStatus=='2'}">
										未使用
									</c:if>
									</td>	
									<td class="date_time_parse">${item.createTime}</td>								
									<td class="parent_id_parse">
									<c:if test="${item.qrStatus=='1'}">
										启用
									</c:if>
									<c:if test="${item.qrStatus=='2'}">
										禁用
									</c:if>
									</td>
									
							<td>
								<a onclick="javascript:todetail('${item.id}');"><span class="label label-default">查看详情</span></a>
								<c:if test="${item.qrStatus == 2}">
									<a onclick="javascript:updateStatus(this);" status="1" value="${item.id}">
										<span class="label label-success">启用</span>
									</a>
								</c:if>
								<c:if test="${item.qrStatus == 1 || item.qrStatus==null}">
									<a onclick="javascript:updateStatus(this);" status="2" value="${item.id}">
										<span class="label label-danger">禁用</span>
									</a>
								</c:if>
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
								   url: "platformqrcode/detail/"+id,
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
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"  totalCounts="${pager.totalCount }"/>				