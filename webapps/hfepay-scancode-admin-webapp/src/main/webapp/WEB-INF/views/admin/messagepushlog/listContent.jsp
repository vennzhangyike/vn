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
								<th>渠道名称</th>
								<th>代理商名称</th>
								<th>代理商级别</th>
								<th>商户名称</th>
								<th>消息类型</th>
								<th>推送规则</th>
								<th>推送频率</th>
								<th>推送方式</th>
								<th>推送时间</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<td>${item.channelName}</td>
									<td>${item.agentName}<c:if test="${empty item.agentName}">不限</c:if></td>
									<td class="parent_id_parse"  title="temp1">${item.temp1}</td>
									<td>${item.merchantName}<c:if test="${empty item.merchantName}">不限</c:if></td>
									<td class="parent_id_parse"  title="messageType">${item.messageType}</td>
									<td>${item.pushRule}</td>
									<td class="parent_id_parse"  title="pushTime">${item.pushTime}</td>
									<td class="parent_id_parse"  title="pushWay">${item.pushWay}</td>
									<td class="date_time_parse">${item.pushDate}</td>

							<td>
								<a onclick="javascript:todetail('${item.id}');"><span class="label label-default">查看详情</span></a>
							</td> 
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
						<tr>
							<td class="page-nodata" colspan="10">查无数据！</td>
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
						   url: "messagepushlog/detail/"+id,
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
								   className: "dialog-class",
								   buttons: btn
								 });
						   }
					});
				}
			</script>
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}" />	<b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条			