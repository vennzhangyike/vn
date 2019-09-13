<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="page" uri="page" %> 
<%
   String path =  request.getContextPath();
request.setAttribute("path", path);
%>
<form class="form-horizontal">
	<div class="form-body row">
				<div class="form-group col-md-7">
					<label class="control-label col-md-4">支付方式</label>
					<div class="col-md-8" id="payways" style="margin-top:6px">
						<c:forEach var="payway" items="${payWays }" varStatus="payWaysStatus">
							<label><input name="payway" type="radio" value="${payway.key }" style="vertical-align:middle;margin:0"/> ${payway.value }</label>&nbsp;&nbsp;&nbsp;&nbsp;
						</c:forEach>
					</div>
				</div>
				<div class="form-group col-md-7">
					<label class="control-label col-md-4">消费金额(元)</label>
					<div class="col-md-8">
						<input type="text" class="form-control" id="money" placeholder="消费金额(元)">
					</div>
				</div>
				<div class="form-group col-md-7" id="moneyDiv">
					<label class="control-label col-md-4">授权码</label>
					<div class="col-md-8">
						<input type="text" class="form-control" id="code" placeholder="授权码">
						<!-- <p style="color:#f00;margin-top:2px">扫码录入自动跳转，手动录入请按确认键提交</p> -->
					</div>
				</div>
	</div>
</form>