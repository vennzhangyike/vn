<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="page" uri="page"%>
<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>

<head>
<link href="${path}/resources/styles/style.css" rel="stylesheet" type="text/css"/>
<link href="${path}/resources/styles/page.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.hiddenRow{
	display: none;
}
</style>
</head>
<div id="paykey">
	<form class="form-horizontal" role="form" id="create_app_form"
		method="post" action="#">
		
		<!-- BEGIN SEARCH CONDITION -->
		<div class="portlet box ">
			<div class="portlet-body form">
				<!-- BEGIN FORM-->
				<form action="save" class="form-horizontal" id="form" method="post">
					<div class="form-body">
						<div class="code_t">门店名称：<span id="show_name">${storeName }</span></div>
						<!-- 文本打印部分 -->
						<div id="my_area">
							<div class="code">
								<div class="wx">
									<img src="${path}/resources/images/codeimage/wx_light.png"><p class="grey">微信</p>
								</div>
								<div class="al">
									<img src="${path}/resources/images/codeimage/alipay_light.png"><p class="grey">支付宝</p>
								</div>
								<div class="bd">
									<img src="${path}/resources/images/codeimage/baidu_grey.png"><p class="grey">百度钱包</p>
								</div>
								<div class="jd">
									<img src="${path}/resources/images/codeimage/jd_grey.png"><p class="grey">京东钱包</p>
								</div>
								<div id="qrcode" style="position:absolute;top:220px;left:80px">
									<div id="codeico">
										<img src="${icon}" height="60px" width="60px">
									</div>
								</div>
								<img src="${path}/resources/images/codeimage/codeBg.jpg" class="codeBg" width="400px" height="600px">
								<div class="codenumer">NO:${qrCode }</div>
								<div class="codelogo"><img src="${channelLogo }" ></div>							
							</div>
						</div>
						<a href="#" onclick="window.print();return false;" class="print">打印</a>
					</div>
				</form>
				<!-- END FORM-->
			</div>
		</div>
		<!-- END SEARCH CONDITION -->
	</form>
</div>
<!-- END CONTENT -->
<script src="${path}/resources/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/qrcode/jquery.qrcode.js?rnd=${version}" type="text/javascript"></script>
<script src="${path}/resources/scripts/qrcode/qrcode.js?rnd=${version}" type="text/javascript"></script>
<script src="${path}/resources/scripts/qrcode/drawQrCode.js?rnd=${version}" type="text/javascript"></script>
<script>
init('${image }');
</script>