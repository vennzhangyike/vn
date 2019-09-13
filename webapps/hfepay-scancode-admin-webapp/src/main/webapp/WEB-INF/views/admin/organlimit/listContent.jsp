<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="page" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%
   String path =  request.getContextPath();
request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered table-hover" id="sample_6">
							<thead>
							<tr>
								<th>机构名称</th>
								<th>限额类型</th>
								<th>限额通道</th>
								<th>限制方式</th>
								<th>最低限额</th>
								<th>最高限额</th>
								<th>状态</th>
								<th>创建时间</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<td>${item.organName}</td>
									<td class="parent_id_parse"  title="limitType">${item.limitType}</td>
									<td class="parent_id_parse"  title="limitPayCode">
									<c:if test="${item.limitPayCode == 9}">不限</c:if><c:if test="${item.limitPayCode != 9}">${item.limitPayCode}</c:if></td>
									<td class="parent_id_parse"  title="limitMode">${item.limitMode}</td>
									<td><fmt:formatNumber value="${item.minLimit}" pattern="#0.##"/></td>
									<td><fmt:formatNumber value="${item.maxLimit}" pattern="#0.##"/></td>
									<td class="parent_id_parse"  title="status">${item.status}</td>
									<td class="date_time_parse">${item.createTime}</td>
							<td>
								<a href="${path}/adminManage/organlimit/${item.id}"><span class="label label-info">修改</span></a>
								<c:if test="${item.status == 1}"><a onclick="javascript:updateStatus(this);" status="2" value="${item.id}"><span class="label label-danger">禁用</span></a></c:if>
								<c:if test="${item.status == 2}"><a onclick="javascript:updateStatus(this);" status="1" value="${item.id}"><span class="label label-success">启用</span></a></c:if>
							</td> 
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
						<tr>
							<td class="page-nodata" colspan="9">查无数据！</td>
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
						   url: "organlimit/detail/"+id,
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
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/>	<b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条			