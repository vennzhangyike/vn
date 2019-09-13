<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>

<c:forEach var="item" items="${pager.result}">
	<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg stores">
			<div class="weui-media-box__hd">
				<img class="weui-media-box__thumb" src="${path}/resources/images/wechat/progress2.png" alt="收银员">
			</div>
			<div class="weui-media-box__bd">
				<h4 class="weui-media-box__title storeName">${item.storeName }</h4>
				<p class="weui-media-box__desc servicePhone">${item.servicePhone }</p>
				<p class="weui-media-box__desc status" style="display:none">${item.storeStatus }</p>
				<p class="weui-media-box__desc storeNo" style="display:none">${item.storeNo }</p>
				<p class="weui-media-box__desc storeAddress" style="display:none">${item.storeAddress }</p>
				<p class="weui-media-box__desc idid" style="display:none">${item.id }</p>
			</div>
		</a>
</c:forEach>

<c:if test="${flag=='0' }">
	<a href="javascript:void(0);" id="moreDiv" class="weui-media-box weui-media-box_appmsg" nextPage="${pager.nextPage }">更多....</a>
	</div>
</c:if>

<!-- 无数据显示 -->
<c:if test="${pager.totalCount==0 }">
	<div class="weui-cell weui-cell_switch">
			<div class="weui-cell__hd">暂无门店数据</div>
	</div>
</c:if>