<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<c:forEach var="item" items="${pager.result}">
		<a href="javascript:void(0);" id="cashier01" class="weui-media-box weui-media-box_appmsg qrcodes">
			<span class="idid" style="display: none;">${item.id }</span>
			<span class="qrCode" style="display: none;">${item.qrCode }</span>
			<div class="weui-media-box__hd">
				<img class="weui-media-box__thumb" src="${path}/resources/images/wechat/editCode.png" alt="收银员">
			</div>
			<div class="weui-media-box__bd">
				<h4 class="weui-media-box__title qrName">${item.qrName }</h4>
				<p class="weui-media-box__desc">
				<c:choose>
					 <c:when test="${!empty item.qid}">   
					    	已绑定收银员
					  </c:when> 
					  <c:otherwise>   
					    	未绑定收银员
					  </c:otherwise> 
				</c:choose> 
				</p>
				<p class="skz"><span class="yellow">
				<c:choose>
					 <c:when test="${item.isCashier=='2'}">   
					    	收款中
					  </c:when> 
					  <c:otherwise>   
					    	未收银
					  </c:otherwise> 
				</c:choose> 
				</span></p>
			</div>
		</a>
</c:forEach>

<c:if test="${flag=='0' }">
	<a href="javascript:void(0);" id="moreDiv" class="weui-media-box weui-media-box_appmsg"  nextPage="${pager.nextPage }">更多....</a>
</c:if>

<!-- 无数据显示 -->
<c:if test="${pager.totalCount==0 }">
	<div class="weui-cell weui-cell_switch">
			<div class="weui-cell__hd">暂无二维码数据</div>
	</div>
</c:if>		