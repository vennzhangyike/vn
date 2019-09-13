<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<div class="form-body row">
	<div class="form-group col-md-12" style="text-align: center;">
		<h1>${agentBase.agentName }</h1>
	</div>
	<div class="form-group col-md-12">
		<div id="qrcode" style="margin:0 auto">
			<div id="codeico">
				<img src="${icon}" height="60px" width="60px">
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
init('${agentPromotion.qrImgAddress }');
</script>
