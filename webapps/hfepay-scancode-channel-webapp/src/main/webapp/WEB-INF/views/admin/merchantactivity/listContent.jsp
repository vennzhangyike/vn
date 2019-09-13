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
								<th>渠道编号</th>
								<th>代理商编号</th>
								<th>商户编号</th>
								<th>活动ID</th>
								<th>活动开始时间</th>
								<th>活动结束时间</th>
								<th>活动类型</th>
								<th>活动描述</th>
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
									<td class="parent_id_parse" title="channelNo">${item.channelNo}</td>
									<td class="parent_id_parse" title="agentNo">${item.agentNo}</td>
									<td class="parent_id_parse" title="merchantNo">${item.merchantNo}</td>
									<td class="parent_id_parse" title="activityId">${item.activityId}</td>
									<td class="date_time_parse">${item.activityBeginTime}</td>
									<td class="date_time_parse">${item.activityEndTime}</td>
									<td class="parent_id_parse" title="activityType">
										<c:if test="${item.activityType=='0' }">折扣</c:if>
										<c:if test="${item.activityType=='1' }">满减</c:if>
										<c:if test="${item.activityType=='2' }">随机</c:if>
									</td>
									<td class="parent_id_parse" title="activityContent">${item.activityContent}</td>
									<td class="date_time_parse">${item.createTime}</td>
									<td class="parent_id_parse" title="status">
										<c:if test="${item.status=='1' }">启用</c:if>
										<c:if test="${item.status=='2' }">禁用</c:if>
									</td>

							<td>
								<a onclick="javascript:todetail('${item.id}');"><span class="label label-default">查看详情</span></a>
								<a href="${path}/adminManage/merchantactivity/${item.id}"><span class="label label-info">修改</span></a>
								<a href="${path}/adminManage/merchantactivity/editDiscount/${item.activityId}"><span class="label label-info">修改规则</span></a>
								<a onclick="javascript:del('${item.id}','${item.activityId}');"><span class="label label-danger">删除</span></a>
						   			<c:if test="${item.status == 2}"><a onclick="javascript:updateStatus(this);" status="1" value="${item.id}" merchantNo="${item.merchantNo}"><span class="label label-success">启用</span></a></c:if>
									<c:if test="${item.status == 1}"><a onclick="javascript:updateStatus(this);" status="2" value="${item.id}" merchantNo="${item.merchantNo}"><span class="label label-danger">禁用</span></a></c:if>	
							</td> 
						 </tr>
					</c:forEach>
			</tbody>
			</table>
			<script type="text/javascript">
						dataOpr();
						
						//显示详情
						function todetail(id){
							$.ajax({
								   type: "POST",
								   url: "merchantactivity/detail/"+id,
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
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"  totalCounts="${pager.totalCount }"/>				