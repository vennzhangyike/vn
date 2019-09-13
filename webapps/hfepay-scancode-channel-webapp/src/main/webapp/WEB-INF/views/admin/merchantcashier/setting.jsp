<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="page" uri="page" %> 
<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<div class="portlet box ">
	<div class="portlet-body form">
		<!-- BEGIN FORM-->
		<form action="#" class="form-horizontal" id="form" method="post">
			<div class="form-body">
				<div class="row" style="margin-top: 4%;width: 80%;margin-left: 10%;padding: 3%;background-color: #ffffff;
										border: 1px solid #24dde5;border-radius:10px!important;box-shadow:inset 0 1px 10px #bce9f9,0 1px 0 rgba(0,0,0,.2);
										word-break: break-all; word-wrap:break-word;position: relative;">
					所有二维码<div class="portlet box green-haze">&nbsp;</div>
					<div class="col-md-12 allApis">
						<c:forEach var="obj" items="${objList}">
							<span class="label label-primary unApi" value="${obj.qrCode}" val="${obj.qrCode}"  style="cursor: pointer;float: left;" title="${obj.qrName}">${obj.qrName}</span>
						</c:forEach>
					</div>
				</div>
				<div class="row" style="margin-top: 4%;width: 80%;margin-left: 10%;padding: 3%;background-color: #ffffff;
										border: 1px solid #24dde5;border-radius:10px!important;box-shadow:inset 0 1px 10px #bce459,0 1px 0 rgba(0,0,0,.2);
										word-break: break-all; word-wrap:break-word;position: relative;">
					已选二维码<div class="portlet box green-haze"></div>
					<div class="col-md-12 ownApis">
						<c:forEach var="item" items="${qrList}">
							<span class="label label-success ownApi" value="${item.qrCode}" val="${item.qrCode}" style="cursor: pointer;float: left;">${item.qrName}</span>
						</c:forEach>
					</div>
				</div>
				<input type="hidden" id="storeNo1" value="${storeNo}">
				<input type="hidden" id="cashierNo1" value="${cashierNo}">
				<input type="hidden" id="merchantNo1" value="${merchantNo}">
				<div class="row" style="margin-top: 10px;">
					<div class="col-md-12">
						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="form-body">
							<div class="col-md-4">
								<div class="form-group">
									<label class="control-label col-md-3"></label>
									<div class="col-md-9">
										<button type="button" class="btn green" id="save1">保存</button>
										<button type="button" class="btn default" id="cancle1">取消</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<!-- END FORM-->
	</div>
</div>
<script src="${path}/resources/scripts/admin/merchantcashier/setting.js?rnd=${version}" type="text/javascript"></script>