<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>用户基本信息</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<jsp:include page="../include/commoncss.jsp"></jsp:include>
</head>
<body class="page-header-fixed page-quick-sidebar-over-content">
<c:if test="${channelPage != null}">
	<jsp:include page="../channel/channelexpand/edit.jsp"></jsp:include>
</c:if>
<c:if test="${agentPage != null}">
	<jsp:include page="../agentbase/edit.jsp"></jsp:include>
	<div class="page-container col-md-4" style="margin-left: 270px;">
		
	</div>
</c:if>
<c:if test="${merchantPage != null}">
	<jsp:include page="../merchantinfo/edit.jsp"></jsp:include>
</c:if>
<jsp:include page="../include/footerjs.jsp"></jsp:include>
<script src="${path}/resources/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/common.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/base.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/jquery.validate.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/ajaxfileupload.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/common.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/qrcode/jquery.qrcode.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/qrcode/qrcode.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/qrcode/drawQrCode.js" type="text/javascript"></script>
<script type="text/javascript">
	var baseurl = "${path}";
	dataOpr();
	//显示推广码
	function agentPromotion(agentNo,agentName){
		$.ajax({
			   type: "POST",
			   url: baseurl.trim() + "/adminManage/agentbase/promotion/"+agentNo,
			   success: function(msg){
				   var btn = {success:{
				       label: "关闭",
				       className: "btn-success",
				       callback: function() {
				       }
				     }};
				   bootbox.dialog({
					   message: msg,
					   title: "推广码信息",
					   onEscape: function() {},
					   className: "green-haze",
					   buttons: btn
					});
			   }
		});
	}
</script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>
