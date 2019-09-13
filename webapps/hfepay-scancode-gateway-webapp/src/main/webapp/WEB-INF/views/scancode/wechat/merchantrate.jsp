<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<html lang="zh-cmn-Hans">
<head>
<title>费率查询</title>
<meta charset="UTF-8">
<meta http-equive="Cache-Control" content="no-cache"/>
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart>

 <c:forEach var="item" items="${paywayList}">
 	<div class="weui-cells__title">${item.payCode}</div>
	<div class="weui-cells weui-cell_access" style="margin-top:0.5em">
		<div class="weui-cell">
			<div class="weui-cell__bd weui-cell_primary"><p>T0费率</p></div>
			<div class=""><fmt:formatNumber value="${item.t0Rate*100}" pattern="#0.##"/>%</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd weui-cell_primary"><p>T1费率</p></div>
			<div class=""><fmt:formatNumber value="${item.t1Rate*100}" pattern="#0.##"/>%</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd weui-cell_primary"><p>提现手续费</p></div>
			<div class=""><fmt:formatNumber value="${item.rate}" pattern="#0.##"/>元</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd weui-cell_primary"><p>T1结算手续费</p></div>
			<div class=""><fmt:formatNumber value="${item.rateAmount}" pattern="#0.##"/>元</div>
		</div>
	</div>
 </c:forEach>
<c:if test="${paywayList.size() == 0}">
	<div class="weui-cells__title">暂无数据..</div>
</c:if>	
<div style="height:100px"></div>
<div class="weui-footer weui-footer_fixed-bottom weui-footer-a">
	<div class="weui-btn-area clearfix">
		<a href="my" class="weui-btn weui-btn_primary close-popup">返回个人中心</a>
	</div>
</div>

		
<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
</body></html>