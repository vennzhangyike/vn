<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>

<c:forEach var="item" items="${pager.result}">
	<div class="weui-cell weui-cell_switch">
		<div class="weui-cell__hd">${item.qrName }
			<span class="codeSkz"><c:if test="${item.isCashier=='2' }">（收款中）</c:if></span>
		</div>
		<div class="weui-cell__ft">
			<input class="weui-switch" type="checkbox" value="${item.id }" <c:if test="${item.isCashier=='2' }">checked="checked"</c:if> attr="${item.qrCode }">
		</div>
	</div>
</c:forEach>

<c:if test="${flag=='0' }">
	<div class="weui-cell weui-cell_switch moreDiv" nextPage="${pager.nextPage }">
			<div class="weui-cell__hd">更多....</div>
	</div>
</c:if>

<!-- 无数据显示 -->
<c:if test="${pager.totalCount==0 }">
	<div class="weui-cell weui-cell_switch">
			<div class="weui-cell__hd">二维码均在收银状态或尚未分配二维码，暂无数据</div>
	</div>
</c:if>