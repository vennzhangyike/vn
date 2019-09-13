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
				<c:when test="${item.payCode=='WXGZHZF'}">   
			    	微信公众号支付
			  	</c:when> 
			  	<c:when test="${item.payCode=='ZFBSMZF'}">   
			    	支付宝扫码支付
			  	</c:when>
			  	<c:when test="${item.payCode=='QQZF'}">   
			    	QQ支付
			  	</c:when> 
			  	<c:otherwise>
			  		其他方式
			  	</c:otherwise>
			</c:choose>
			</p>
			<p class="billData cashierName">${item.cashierName}</p>
			<p class="billData storeName">${item.storeName}</p>
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
			<span class="discountAmt" style="display: none;">
				<c:choose>
					<c:when test="${empty item.discountAmt}">   
					    	0.00
					 </c:when> 
					 <c:otherwise>
				  		${item.discountAmt }
				  	</c:otherwise>
				</c:choose>	  	
			</span>
			<%-- <span class="merchantCost" style="display: none;">
				<c:choose>
					<c:when test="${empty item.merchantCost}">   
					    	0.00
					 </c:when> 
					 <c:otherwise>
				  		${item.merchantCost }
				  	</c:otherwise>
				</c:choose>	  	
			</span> --%>
			<span class="tradeNo" style="display: none;">${item.tradeNo }</span>
			<p class="billState payStatus">
				<c:choose>
					<c:when test="${item.payStatus=='00'}">   
				    	等待处理
				  	</c:when> 
				  	<c:when test="${item.payStatus=='01'}">   
				    	处理中
				  	</c:when> 
				  	<c:when test="${item.payStatus=='02'}">  
				  		<c:if test="${item.tradeType=='02'}">
				  			交易成功
				  		</c:if> 
				    	<c:if test="${item.tradeType=='01'}">
				  			提现成功
				  		</c:if> 
				  	</c:when>
				  	<c:when test="${item.payStatus=='03'}">   
				    	<c:if test="${item.tradeType=='02'}">
				  			交易失败
				  		</c:if> 
				    	<c:if test="${item.tradeType=='01'}">
				  			提现失败
				  		</c:if> 
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