<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">广告编号</td><td class="col-md-4 parent_id_parse">${item.adviertNo}</td>
		<td class="col-md-2 td0">机构名称</td><td class="col-md-4 parent_id_parse"><c:if test="${organ != null}">${organ.organName}</c:if></td>	
	</tr>
	<tr>
		<td class="col-md-2 td0">生效日期</td><td class="col-md-4 date_time_parse">${item.startDate}</td>		
		<td class="col-md-2 td0">结束日期</td><td class="col-md-4 date_time_parse" >${item.endDate}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">推广范围</td><td class="col-md-4" ><c:if test="${province != null && city == null}">${province.pname}</c:if><c:if test="${city != null}">${city.cname}</c:if></td>
		<td class="col-md-2 td0">广告位</td><td class="col-md-4 parent_id_parse" title="adviertPosition">${item.adviertPosition}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">广告图片</td><td class="col-md-4" colspan="3"><img src="${item.imgUrl}" style="max-width: 100px;"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">广告链接地址</td><td class="col-md-4" ><a href="${item.adviertLink}" target="_Blank">${item.adviertLink}</a></td>
		<td class="col-md-2 td0">优先级别</td><td class="col-md-4 parent_id_parse" title="priority">${item.priority}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">状态</td><td class="col-md-4 parent_id_parse" title="status"> ${item.status} </td>
		<td class="col-md-2 td0">记录状态</td><td class="col-md-4 parent_id_parse" title="recordStatus"> ${item.recordStatus} </td>
	</tr>
	<tr>
		<td class="col-md-2 td0">创建时间</td><td class="col-md-4 date_time_parse">${item.createTime}</td>
		<td class="col-md-2 td0">修改时间</td><td class="col-md-4 date_time_parse">${item.updateTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">操作人账号</td><td class="col-md-4" >${item.operator}</td>
		<td class="col-md-2 td0">备注</td><td class="col-md-4 parent_id_parse" >${item.remark}</td>
	</tr>
</table>
<script type="text/javascript">
dataOpr();
</script>
