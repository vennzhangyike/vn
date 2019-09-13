<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>渠道编辑</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<jsp:include page="../../include/commoncss.jsp"></jsp:include>
<link rel="shortcut icon" href="${currentChannelInfo.icon }"/>

<script type="text/javascript" charset="utf-8" src="${path}/resources/scripts/ue/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${path}/resources/scripts/ue/ueditor.all.min.js"> </script>

<style type="text/css">
.hiddenRow{
	display: none;
}
</style>
</head>
<body class="page-header-fixed page-quick-sidebar-over-content">
<%-- <jsp:include page="../../include/head.jsp"></jsp:include> --%>
<!-- BEGIN CONTAINER -->
<div class="page-container">
<%-- <jsp:include page="../../include/menu.jsp"></jsp:include> --%>
	<!-- BEGIN CONTENT -->
	<section id="ucenterSection">
			<%-- <div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="${path }/adminManage/index">首页</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#" onclick="javascript:history.go(-1);">渠道基本信息</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<c:if test="${Obj == null}"><a href="#">渠道新增 </a></c:if>
						<c:if test="${Obj != null}"><a href="#">渠道更新</a></c:if>
					</li>
				</ul>
			</div> --%>
			<!-- END PAGE HEADER-->
			
			<!-- BEGIN SEARCH CONDITION -->
			<div class="portlet box ">
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form action="save" class="form-horizontal" id="form" method="post">
						<div class="form-body">
							<input type="hidden" id="id"  name = "id" value="${Obj.id}">
							<input type="hidden" id="channelNo"  name = "channelNo" value="${Obj.channelNo}">
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">渠道名称*</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="channelName"  name = "channelName" 
												<c:if test="${Obj != null}">readonly="readonly"</c:if> value="${Obj.channelName}" placeholder="渠道名称">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">二级域名编号*</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="channelCode"  name = "channelCode" value="${Obj.channelCode}" placeholder="二级域名编号">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-1">编码抬头*</label>
										<div class="col-md-2">
											<input type="text" class="form-control ajaxCheck2" id="channelPreCode"  name = "channelPreCode" value="${Obj.channelPreCode}" placeholder="编码抬头">
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">公司简称*</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="nickName"  name = "nickName" value="${Obj.nickName}" placeholder="昵称">
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">公司名称*</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="companyName"  name = "companyName" value="${Obj.companyName}" placeholder="公司名称---全称">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">电话*</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="phone"  name = "phone" value="${Obj.phone}" placeholder="电话">
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">备案号</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="recordNumber"  name = "recordNumber" value="${Obj.recordNumber}" placeholder="备案号">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">技术支持邮箱</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="technicalSupportEmail"  name = "technicalSupportEmail" value="${Obj.technicalSupportEmail}" placeholder="技术支持邮箱">
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">地址</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="address"  name = "address" value="${Obj.address}" placeholder="地址">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">商务邮箱</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="businessCooperationEmail"  name = "businessCooperationEmail" value="${Obj.businessCooperationEmail}" placeholder="商务邮箱">
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">客服QQ</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="customServiceQq"  name = "customServiceQq" value="${Obj.customServiceQq}" placeholder="客服QQ">
										</div>
									</div>
								</div>
<!-- 								<div class="col-md-6"> -->
<!-- 									<div class="form-group"> -->
<!-- 										<label class="control-label col-md-2">QQ群</label> -->
<!-- 										<div class="col-md-4"> -->
<%-- 											<input type="text" class="form-control" id="qqGroup"  name = "qqGroup" value="${Obj.qqGroup}" placeholder="QQ群 多个逗号隔开"> --%>
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 								<div class="col-md-6"> -->
<!-- 									<div class="form-group"> -->
<!-- 										<label class="control-label col-md-2">商务QQ</label> -->
<!-- 										<div class="col-md-4"> -->
<%-- 											<input type="text" class="form-control" id="businessCooperationQq"  name = "businessCooperationQq" value="${Obj.businessCooperationQq}" placeholder="商务QQ"> --%>
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 								</div> -->
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">联系电话</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="telephone"  name = "telephone" value="${Obj.telephone}" placeholder="联系电话">
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">备注</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="remark"  name = "remark" value="${Obj.remark}" placeholder="备注">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">公司官网</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="microblogUrl"  name = "microblogUrl" value="${Obj.microblogUrl}" placeholder="公司官网">
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">域名</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="domainName"  name = "domainName" value="${Obj.domainName}" placeholder="域名">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-1">首页上部LOGO</label>
										<div class="col-md-5">
											<input type="file" name="file" id="indexTopImg_2" class="form-control fileInput" 
													onchange="submitForm('indexTopImg_2','indexTopImg','indexTopImg_1','indexTopImg')"/>
										</div>
										<div class="col-md-5" id="" style="margin-top: 7px;">
											<img id="indexTopImg_1" src="${Obj.indexTopImg}" style="max-width: 400px;"/>
											<input type="hidden" val="hidden"  id="indexTopImg"  name = "indexTopImg" value="${Obj.indexTopImg}">
										</div>
									</div>
								</div>
							</div>
<!-- 							<div class="row"> -->
<!-- 								<div class="col-md-9"> -->
<!-- 									<div class="form-group"> -->
<!-- 										<label class="control-label col-md-2">首页底部LOGO</label> -->
<!-- 										<div class="col-md-5"> -->
<!-- 											<input type="file" name="file" id="indexBottomImg_2" class="form-control fileInput"  -->
<!-- 													onchange="submitForm('indexBottomImg_2','indexBottomImg','indexBottomImg_1','indexBottomImg')"/> -->
<!-- 										</div> -->
<!-- 										<div class="col-md-5" id="" style="margin-top: 7px;"> -->
<%-- 											<img id="indexBottomImg_1" src="${Obj.indexBottomImg}" style="max-width: 400px;"/> --%>
<%-- 											<input type="hidden"  val="hidden" id="indexBottomImg"  name = "indexBottomImg" value="${Obj.indexBottomImg}"> --%>
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-1">官方微信二维码</label>
										<div class="col-md-5">
											<input type="file" name="file" id="indexWxImg_2" class="form-control fileInput" 
													onchange="submitForm('indexWxImg_2','indexWxImg','indexWxImg_1','indexWxImg')"/>
										</div>
										<div class="col-md-5" id="" style="margin-top: 7px;">
											<img id="indexWxImg_1" src="${Obj.indexWxImg}" style="max-width: 400px;"/>
											<input type="hidden"  val="hidden" id="indexWxImg"  name = "indexWxImg" value="${Obj.indexWxImg}">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-1">公司地址图</label>
										<div class="col-md-5">
											<input type="file" name="file" id="contactAddressImg_2" class="form-control fileInput" 
													onchange="submitForm('contactAddressImg_2','contactAddressImg','contactAddressImg_1','contactAddressImg')"/>
										</div>
										<div class="col-md-5" id="" style="margin-top: 7px;">
											<img id="contactAddressImg_1" src="${Obj.contactAddressImg}" style="max-width: 400px;"/>
											<input type="hidden"  val="hidden" id="contactAddressImg"  name = "contactAddressImg" value="${Obj.contactAddressImg}">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-1">登陆背景图</label>
										<div class="col-md-5">
											<input type="file" name="file" id="loginBackgroundImg_2" class="form-control fileInput" 
													onchange="submitForm('loginBackgroundImg_2','loginBackgroundImg','loginBackgroundImg_1','loginBackgroundImg')"/>
										</div>
										<div class="col-md-5" id="" style="margin-top: 7px;">
											<img id="loginBackgroundImg_1" src="${Obj.loginBackgroundImg}" style="max-width: 400px;"/>
											<input type="hidden"  val="hidden" id="loginBackgroundImg"  name = "loginBackgroundImg" value="${Obj.loginBackgroundImg}">
										</div>
									</div>
								</div>
							</div>
<!-- 							<div class="row"> -->
<!-- 								<div class="col-md-9"> -->
<!-- 									<div class="form-group"> -->
<!-- 										<label class="control-label col-md-2">个人中心LOGO</label> -->
<!-- 										<div class="col-md-5"> -->
<!-- 											<input type="file" name="file" id="centerHeadImg_2" class="form-control fileInput"  -->
<!-- 													onchange="submitForm('centerHeadImg_2','centerHeadImg','centerHeadImg_1','centerHeadImg')"/> -->
<!-- 										</div> -->
<!-- 										<div class="col-md-5" id="" style="margin-top: 7px;"> -->
<%-- 											<img id="centerHeadImg_1" src="${Obj.centerHeadImg}" style="max-width: 400px;"/> --%>
<%-- 											<input type="hidden"  val="hidden" id="centerHeadImg"  name = "centerHeadImg" value="${Obj.centerHeadImg}"> --%>
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->

							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-1">渠道图标</label>
										<div class="col-md-5">
											<input type="file" name="file" id="icon_2" class="form-control fileInput" 
													onchange="submitForm('icon_2','icon','icon_1','icon')"/>
										</div>
										<div class="col-md-5" id="" style="margin-top: 7px;">
											<img id="icon_1" src="${Obj.icon}" style="max-width: 400px;"/>
											<input type="hidden"  val="hidden" id="icon"  name = "icon" value="${Obj.icon}">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-1">关于我们</label>
										<div class="col-md-10">
											<script id="editor" type="text/plain" style="width:100%;height:300px;"></script>
											<input type="hidden" class="form-control" id="aboutUs"  name = "aboutUs" value='${Obj.aboutUs}'>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-1">帮助中心</label>
										<div class="col-md-10">
											<script id="helpInfoEditor" type="text/plain" style="width:100%;height:300px;"></script>
											<input type="hidden" class="form-control" id="helpInfo"  name = "helpInfo" value='${Obj.helpInfo}'>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-1">电子协议</label>
										<div class="col-md-10">
											<script id="agreementEditor" type="text/plain" style="width:100%;height:300px;"></script>
											<input type="hidden" class="form-control" id="agreement"  name = "agreement" value='${agreementInfo.agreementcontent}'>
										</div>
									</div>
								</div>
							</div>
							
							<div class="page-bar" style="margin-top: 20px;">
								<h3>公众号信息</h3>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">公众号ID*</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="appid"  name = "appid" value="${Obj.appid}" placeholder="公众号ID">
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">公众号key*</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="secret"  name = "secret" value="${Obj.secret}" placeholder="公众号key">
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">注册消息模板ID*</label>
										<div class="col-md-4">
											<input type="text" class="form-control" 
												id="registerTemplateId" name="registerTemplateId" value="${Obj.registerTemplateId}"
												placeholder="注册消息模板ID">
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">支付消息模板ID*</label>
										<div class="col-md-4">
											<input type="text" class="form-control"
												id="payTemplateId" name="payTemplateId" value="${Obj.payTemplateId}"
												placeholder="支付消息模板ID">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">提现模版ID*</label>
										<div class="col-md-4">
											<input type="text" class="form-control" 
												id="withDrawsTemplateId" name="withDrawsTemplateId" value="${Obj.withDrawsTemplateId}"
												placeholder="提现模版ID">
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">商户信息待完善模版ID</label>
										<div class="col-md-4">
											<input type="text" class="form-control" 
												id="unperfectTemplateId" name="unperfectTemplateId" value="${Obj.unperfectTemplateId}"
												placeholder="商户信息待完善模版ID">
										</div>
									</div>
								</div>
							</div>
							
							<div class="page-bar" style="margin-top: 20px;">
								<h3>短信参数信息</h3>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">短信用户名</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="smsuser"  name = "smsuser" value="${channelExpandConditionSms.smsuser}" placeholder="短信用户名">
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">短信密码</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="smspassword"  name = "smspassword" value="${channelExpandConditionSms.smspassword}" placeholder="短信密码">
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">短信发送地址</label>
										<div class="col-md-4">
											<input type="text" class="form-control" 
												id="sendUrl" name="sendUrl" value="${channelExpandConditionSms.sendUrl}"
												placeholder="短信发送地址">
										</div>
									</div>
								</div>
							</div>
								
							
							
							<c:if test="${Obj == null}">
							<!--
							<div class="page-bar" style="margin-top: 20px;">
								<h3>用户账户信息</h3>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">账号</label>
										<div class="col-md-4">
											<input type="text" class="form-control ajaxCheck1" id="userName"  name = "userName" value="${Obj.userName}" placeholder="账号">
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">密码</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="password"  name = "password">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">姓名</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="shortName" name="shortName" value="${Obj.shortName}"  placeholder="姓名">
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">邮箱</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="email"  name = "email" value="${Obj.email}"   placeholder="邮箱">
										</div>
									</div>
								</div>
							</div>
							<c:if test="${roles.size() > 0}">
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label col-md-2">账户角色</label>
											<div class="col-md-8">
												<select id = "roleId" class="form-control" name="roleId">
													<c:forEach var="obj" items="${roles}">
														<c:if test="${obj.description == '预付费渠道管理员' || obj.description == '后付费渠道管理员'}">
															<option value = "${obj.id}">${obj.description}--${obj.roleName}</option>
														</c:if>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</div>
							</c:if>
							-->
							
							
							<div class="page-bar" style="margin-top: 20px;">
								<h3>银行账户信息</h3>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">银行卡号码*</label>
										<div class="col-md-4">
											<input type="text" class="form-control"
												id="bankCard" name="bankCard" value="${Obj.bankCard}"
												placeholder="银行卡号码">
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">联行号*</label>
										<div class="col-md-4">
											<input type="text" class="form-control" readonly="readonly"
												id="bankCode" name="bankCode" value="${Obj.bankCode}"
												placeholder="联行号 ">
										</div>
									</div>
								</div>
							</div>
							<div class="row">								
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">开户行名称*</label>
										<div class="col-md-4">
											<input type="text" class="form-control" readonly="readonly"
												id="bankName" name="bankName" value="${Obj.bankName}"
												placeholder="开户行名称">
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">账户类型*
										</label>
										<div class="col-md-4">
											<select  id="accountType" class="form-control" name="accountType">													
													<option value = "1">公司 </option>
											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">身份证号码*</label>
										<div class="col-md-4">
											<input type="text" class="form-control"
												id="idCard" name="idCard" value="${Obj.idCard}"
												placeholder="身份证号码">
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">姓名*</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="name"
												name="name" value="${Obj.name}" placeholder="姓名">
										</div>
									</div>
								</div>
								
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2">手机号码</label>
										<div class="col-md-4">
											<input type="text" class="form-control"
												id="mobile" name="mobile" value="${Obj.mobile}"
												placeholder="手机号码">
										</div>
									</div>
								</div>
							</div>
							</c:if>
							<div class="row">
								<div class="portlet box green-haze"></div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="col-md-2"></label>
										<div class="col-md-9">
										<button type="submit" class="btn green" id="save">保存</button>
										<button type="button" class="btn default" id="cancle">取消</button>
										</div>
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
<jsp:include page="../../include/footerjs.jsp"></jsp:include>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<!-- 四级菜单不提供选中和三级菜单紧密绑定，在获取三级ID的时候相应的四级资源必须获取 -->
<div class="fade hide">
<c:forEach var="forth" items="${forthMap}" varStatus="statusforth">
	<div id="forth${forth.key}">
	<c:forEach var="forthMenu" items="${forth.value}" varStatus="statusFF"> 
		<input type="hidden" id="text${forthMenu.id}" value="${forthMenu.id}"/>
	</c:forEach>
	</div>
</c:forEach>
</div>

<script src="${path}/resources/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/demo.js" type="text/javascript"></script>

<script type="text/javascript">
	var baseurl = "${path}";
</script>
<script src="${path}/resources/scripts/admin/channel/channelexpand/edit.js?rnd=${version}" type="text/javascript"></script>
<script src="${path}/resources/scripts/jquery.validate.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/ajaxfileupload.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/common.js" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>