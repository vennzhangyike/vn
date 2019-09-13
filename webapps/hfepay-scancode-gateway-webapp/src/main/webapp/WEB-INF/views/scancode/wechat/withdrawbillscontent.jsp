<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<c:forEach var="item" items="${pager.result}">
	<a class="weui-cell" href="javascript:;" data-target="#details">
		<div class="weui-media-box__hd">
			<img class="weui-media-box__thumb" src="${path}/resources/images/wechat/${item.payCode }.png" height="40px">
		</div>
		<div class="weui-cell__bd">
			<p class="payCode">
			<c:choose>
				<c:when test="${item.payCode=='ZFBSMZF'}">   
			    	支付宝钱包
			  	</c:when> 
			  	<c:when test="${item.payCode=='WXSMZF'}">   
			    	微信扫码钱包
			  	</c:when> 
			  	<c:when test="${item.payCode=='WXGZHZF'}">   
			    	微信公众号钱包
			  	</c:when>
			  	<c:when test="${item.payCode=='QQZF'}">   
			    	QQ钱包
			  	</c:when>
			  	<c:otherwise>
			  		其他方式
			  	</c:otherwise>
			</c:choose>
			</p>
			<p class="billData beginTime"><fmt:formatDate value="${item.beginTime}" pattern="yyyy-MM-dd HH:mm"/></p>
		</div>
		<div class="weui-cell__ft">
			<span class="yellow size22 orderAmt">
				<c:choose>
					<c:when test="${empty item.orderAmt}">   
					    	0.00
					 </c:when> 
					 <c:otherwise>
				  		${item.orderAmt }
				  	</c:otherwise>
				</c:choose>	  	
			</span>
			<span class="merchantCost" style="display: none;">
				<c:choose>
					<c:when test="${empty item.merchantCost}">   
					    	0.00
					 </c:when> 
					 <c:otherwise>
				  		${item.merchantCost }
				  	</c:otherwise>
				</c:choose>	  	
			</span>
			<span class="tradeNo" style="display: none;">${item.tradeNo }</span>
			<p class="billState resultCode">
				<c:choose>
					<c:when test="${item.resultCode=='0'}">   
				    	订单已创建
				  	</c:when> 
				  	<c:when test="${item.resultCode=='1'}">   
				    	提现处理中
				  	</c:when> 
				  	<c:when test="${item.resultCode=='2'}">   
				    	提现成功
				  	</c:when>
				  	<c:when test="${item.resultCode=='3'}">   
				    	提现失败
				  	</c:when>  
				  	<c:otherwise>
				  		状态未知
				  	</c:otherwise>
				</c:choose>
			</p>
		</div>
	</a>
</c:forEach>
<c:if test="${pager.result.size() == 0}">
		<span style='text-align:center;color:#666;padding:20px;display:block' id='noneData'>暂无数据</span>
</c:if>
<input type="hidden" id="totalPagescon" value="${pager.totalPages }">