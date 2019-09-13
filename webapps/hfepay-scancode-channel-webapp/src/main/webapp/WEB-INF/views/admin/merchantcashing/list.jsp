<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" uri="page" %> 
<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>密码修改</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<jsp:include page="../include/commoncss.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${path }/resources/scripts/loading/jquery-showloading.css">
<link rel="shortcut icon" href="${currentChannelInfo.icon }"/>
<style type="text/css">
.hiddenRow{
	display: none;
}
.form-control{
    height: 120px;
    font-size: 50px;
    width:60%;
}
.w-pay-selector {
    position: relative;
    color: #3d3d3d;
    font-size: 12px;
}
.w-pay-money, .w-pay-type {
    position: relative;
    margin: 10px 10px 10px 0;
    width: 186px;
    height: 100px;
    border: 2px solid #BCCCEE;
    background: #fff;
    text-align: center;
    font-weight: 700;
    font-size: 14px;
    line-height: 56px;
    cursor: pointer;
    display: inline-block;
    vertical-align: middle;
    padding-top: 25px;
}
.w-pay-selected, .w-pay-type:hover {
    height: 100px;
    border-color: #3983e5;
    border-width: 2px;
    line-height: 56px;
}
.w-pay-selected{
	background: url(${path }/resources/images/pay_selected.png) right bottom no-repeat;
	background-color: #fff;
}
.form-horizontal .control-label {
    padding-top: 40px;
    margin-bottom: 0;
    text-align: right;
    font-size: 30px;
}
.emptytips{
	margin: 30px 10px 10px 0;
    height: 100px;
    font-weight: 700;
    font-size: 30px;
    color: red;
}
#discount{font-size:40px;padding-top:10px;display:block}
#discount span{color:#4190de;font-size:50px;}
</style>
</head>
<body class="page-header-fixed page-quick-sidebar-over-content">
<%-- <jsp:include page="../include/head.jsp"></jsp:include> --%>
<!-- BEGIN CONTAINER -->
<div class="page-container" style="padding-top: 60px;">
<%-- <jsp:include page="../include/menu.jsp"></jsp:include> --%>
	<!-- BEGIN CONTENT -->
	<section id="ucenterSection">
			<div class="portlet box ">
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form class="form-horizontal">
						<div class="row">		
							<div class="col-md-12">
								<div class="form-group">
									<label class="control-label col-md-2">消费金额(元)</label>
									<div class="col-md-9">
										<input type="text" class="form-control" id="money" placeholder="消费金额(元)">
										<span id="discount"></span>
									</div>
								</div>
							</div>
						</div>	
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label class="control-label col-md-2">支付方式</label>
									<div class="col-md-9" id="payways" style="margin-top:6px">
									 <div class="w-pay-selector" id="pro-view-17">
										<c:forEach var="payway" items="${payWays }" varStatus="payWaysStatus">
											<div tabindex="0" class="w-pay-type" paytype="${payway.key }"><img src="${path }/resources/images/${payway.key }.jpg" alt="${payway.value }"></div>
										</c:forEach>
										<c:if test="${empty payWays or fn:length(payWays)==0}">
											<div class="emptytips"><span>暂无支付方式，请联系渠道商设置支付方式</span></div>
										</c:if>
									</div>
									</div>
								</div>
							</div>
						</div>	
						<div class="row">	
							<div class="col-md-12" id="moneyDiv">
								<div class="form-group">
									<label class="control-label col-md-2">授权码</label>
									<div class="col-md-9">
										<input type="text" class="form-control" id="code" placeholder="授权码">
										<!-- <p style="color:#f00;margin-top:2px">扫码录入自动跳转，手动录入请按确认键提交</p> -->
									</div>
								</div>
							</div>
						</div>
						<div class="row" style="margin-top:10px">
							<div class="col-md-12">
								<div class="form-group">
									<label class="control-label col-md-2"></label>
									<div class="col-md-9">
										<button type="button" class="btn green" id="save" style="font-size:30px;width:280px;height:70px;">提交</button>
									</div>
								</div>
							</div>
						</div>
					</form>
					<!-- END FORM-->
				</div>
			</div>
			<!-- END SEARCH CONDITION -->
	</section>
</div>
<!-- END CONTENT -->
<jsp:include page="../include/footerjs.jsp"></jsp:include>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="${path}/resources/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="${path }/resources/scripts/loading/jquery-showloading.js" type="text/javascript"></script>

<script src="${path}/resources/scripts/admin/merchantcashing/list.js?rnd=${version}" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>