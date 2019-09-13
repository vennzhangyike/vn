<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="page" %> 
<%
   String path =  request.getContextPath();
request.setAttribute("path", path);
%>
<div class="caption"><button type="button" class="btn btn-default" id="delete">删除</button></div>
<div><br></div>
<table class="table table-striped table-bordered table-hover" id="sample_6">
							<thead>
							<tr>
								<th><input id="allItem" name = "allItem" type="checkbox" /></th>
								<th>标题</th>
								<th>状态</th>
								<th>创建日期</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
					<c:forEach var="item" items="${pager.result}"> 
						   <tr>
						   		<td><input class="subItem" name="item" type="checkbox" value="${item.id}" /></td>
								<td class="parent_id_parse"><a href="${path}/adminManage/usermessage/messagedetail/${item.messageId}">${item.title}</a></td>
								<td class="parent_id_parse">
										<c:if test="${item.readStatus=='1' }">未读</c:if>
										<c:if test="${item.readStatus=='2' }">已读</c:if>
									</td>
								<td class="date_time_parse">${item.createTime}</td>
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
						<tr>
							<td class="page-nodata" colspan="4">查无数据！</td>
						</tr>
					</c:if>
			</tbody>
			</table>
			<script type="text/javascript">
						dataOpr();
			</script>
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/><b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条				