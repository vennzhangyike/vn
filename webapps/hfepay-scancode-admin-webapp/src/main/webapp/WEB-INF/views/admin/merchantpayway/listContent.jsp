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
								<th>商户编号</th>
								<th>商户名称</th>
								<!-- <th>费率代码</th> -->
								<th>费率名称</th>
								<th>T1交易费率</th>
								<th>T0垫资成本</th>
								<th>提现手续费(元)</th>
								<th>入网状态</th>
								<th>费率状态</th>
								<th>创建时间</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<td>${item.merchantNo}</td>
									<td>${item.merchantName}</td>
									<%-- <td>${item.payCode}</td> --%>
									<td>${item.payName}</td>
									<td class="parent_id_parse"><fmt:formatNumber value="${item.t1Rate}" pattern="#0.#####"/></td>
									<td class="parent_id_parse"><fmt:formatNumber value="${item.t0Rate}" pattern="#0.#####"/></td>
									<td class="parent_id_parse"><fmt:formatNumber value="${item.rate}" pattern="#0.##"/></td>
									<td class="parent_id_parse" title="acceptStatus">${item.acceptStatus}</td>
									<td class="parent_id_parse" title="status">${item.status}</td>
									<td class="date_time_parse">${item.createTime}</td>
							<td>
								<a onclick="javascript:todetail('${item.id}');"><span class="label label-default">查看详情</span></a>
								<a href="${path}/adminManage/merchantpayway/edit/${item.id}/${item.merchantNo}"><span class="label label-info">修改</span></a>
								<%-- <a onclick="javascript:del('${item.id}');"><span class="label label-danger">删除</span></a> --%>
								<a href="${path}/adminManage/merchantpayway/link/${item.id}"><span class="label label-primary">变更记录</span></a>
								
						   		<c:if test="${item.status == 2}"><a onclick="javascript:updateStatus(this);" status="1" value="${item.id}"><span class="label label-success">启用</span></a></c:if>
								<c:if test="${item.status == 1}"><a onclick="javascript:updateStatus(this);" status="2" value="${item.id}"><span class="label label-danger">禁用</span></a></c:if>	
								<%-- <c:if test="${item.acceptStatus == 1 || item.acceptStatus == 2}"><a onclick="javascript:audit('${item.id}');"><span class="label label-info">费率变更</span></a></c:if>
								<c:if test="${item.acceptStatus == 0}"><a onclick="javascript:join('${item.id}');"><span class="label label-info">费率入网</span></a></c:if> --%>
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
								   url: "merchantpayway/detail/"+id,
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
						
						//费率变更
						function audit(id){
							$.ajax({
					 		   type: "POST",
					 		   url: "merchantpayway/audit/"+id,
					 		   success: function(msg){
					 			  msg = $.parseJSON(msg);
					 			  bootbox.alert(msg.values,function(){
					 				 if(msg.executeStatus == 0){
					 					window.location.reload();
					 				 }
					 			  });
					 		   }
					 		});
						}
						
						//费率入网
						function join(id){
							$.ajax({
					 		   type: "POST",
					 		   url: "merchantpayway/join/"+id,
					 		   success: function(msg){
					 			  msg = $.parseJSON(msg);
					 			  bootbox.alert(msg.values,function(){
					 				 if(msg.executeStatus == 0){
					 					window.location.reload();
					 				 }
					 			  });
					 		   }
					 		});
						}
			</script>
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/>
			<b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条</b>		