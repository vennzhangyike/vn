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
								<th>
									 Rendering engine
								</th>
								<th>
									 Browser
								</th>
								<th class="hidden-xs">
									 Platform(s)
								</th>
								<th class="hidden-xs">
									 Engine version
								</th>
								<th class="hidden-xs">
									 CSS grade
								</th>
								<th class="hidden-xs">
									 operation
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pageData}" varStatus="status"> 
						   <tr>
							 <td>
								${item.id }
							</td>
							<td>
								${item.menuName }
							</td>
							<td>
								${item.sort }
							</td>
							<td>
								${item.url }
							</td>
							<td>
								${item.level }
							</td> 
							<td>
							<!-- <a data-toggle="modal" href="#responsive">查看弹出形式 </a> -->
								 <a data-toggle="modal" onclick="javascript:detailPop();">查看弹出形式</a>
								<a href="${path }/adminManage/test/page/viewDetail">查看新页面</a>
								<a  data-toggle="modal" href="#edit">编辑</a>
								<a onclick="javascript:del();">删除</a>
							</td> 
						 </tr>
					</c:forEach>
			</tbody>
			</table>
			<page:page curPage="${pageData.pageIndex }" totalPages="${pageData.pages }"/>				