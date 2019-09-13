<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<jsp:include page="include/commoncss.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${path}/resources/styles/trans.css"/>
<style type="text/css">
.hiddenRow{
	display: none;
}
</style>
</head>
<body class="page-header-fixed page-quick-sidebar-over-content">
<div class="page-container">
	<section id="ucenterSection">		
		<c:if test="${user.identityFlag=='2'||user.identityFlag=='1'}">
			<div class="form-body clearfix">
				<div class="col-md-1">
					<div class="form-group text-center" >
						<br>
						<img width="50" height="50" src="${path}/resources/images/default_family.jpg" style="border-radius:30px">
						<div><br><label>Hi,${user.shortName}</label></div>
					</div>
				</div>
				<div class="col-md-3" style="margin-top:15px">
					<div class="form-group clearfix">
						<label class="control-label col-md-4">我的钱包：</label>
						<div class="col-md-4">
							${wallet.balance+wallet.freezesAmt }
						</div>
					</div>
					<div class="form-group clearfix">
						<label class="control-label col-md-4">总二维码数：</label>
						<div class="col-md-4">
							${obj.qrTotal}
						</div>
					</div>
				</div>
				<div class="col-md-3" style="margin-top:15px">
					<div class="form-group clearfix">
						<label class="control-label col-md-4">可提现金额：</label>
						<div class="col-md-4">
							${wallet.balance }
						</div>
					</div>
					<div class="form-group clearfix">
						<label class="control-label col-md-4">已使用二维码：</label>
						<div class="col-md-4">
							${obj.useTotal}
						</div>
					</div>
				</div>	
				<div class="col-md-3" style="margin-top:15px">
					<div class="form-group clearfix">
						<label class="control-label col-md-4">冻结金额：</label>
						<div class="col-md-4">
							${wallet.freezesAmt }
						</div>
					</div>
					<div class="form-group clearfix">
						<label class="control-label col-md-4">剩余二维码：</label>
						<div class="col-md-4">
							${obj.lessTotal}
						</div>
					</div>
				</div>		
			</div>
			<div class="form-body clearfix" style="border-top:none">
				<div class="col-md-8">
					<div class="form-group col-md-3">
						<button type="button" class="btn green" onClick="window.parent.frames.addWithDraw()">申请分润提现</button>
					</div>
					<c:if test="${user.identityFlag=='2'}">
					<div class="form-group  col-md-3">
						<button type="button" class="btn green" onclick="window.parent.frames.agentPromotion('${obj.agentNo}','${obj.agentName}');">我的推广码</button>
					</div>
					</c:if>
					<div class="form-group  col-md-3">
						<button type="button" class="btn green" onClick="window.parent.frames.addtabsByUrl('/adminManage/hierarchicalsettlementtotal','?organNo=${organNo}')" >分润记录</button>
					</div>
					<div class="form-group  col-md-3">
						<button type="button" class="btn green" onClick="window.parent.frames.addtabsByUrl('/adminManage/organwithdrawals','?organNo=${organNo}')" >提现记录</button>
					</div>
				</div>
			</div><br>
		</c:if>
		
		<c:if test="${user.identityFlag=='3'}">
			<div class="form-body clearfix">
				<div class="col-md-1">
					<div class="form-group text-center" >
						<br>
						<img width="50" height="50" src="${path}/resources/images/default_family.jpg" style="border-radius:30px">
						<div><br><label>Hi,${user.shortName}</label></div>
					</div>
				</div>
				<div class="col-md-3" style="margin-top:15px">			
					<div class="form-group clearfix">
						<label class="control-label col-md-4">交易总金额(元)：</label>
						<div class="col-md-4">
							${amtStatic.orderTotalAmt}
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-4">支付宝(元)：</label>
						<div class="col-md-4">
							${amtStatic.zfbTotalAmt}
						</div>
					</div>
				</div>
				<div class="col-md-3" style="margin-top:15px">
					<div class="form-group clearfix">
						<label class="control-label col-md-4">提现总金额(元)：</label>
						<div class="col-md-4">
							${amtStatic.txTotalAmt}
						</div>
					</div>
					<div class="form-group clearfix">
						<label class="control-label col-md-4">微信公众号(元)：</label>
						<div class="col-md-4">
							${amtStatic.wxgzhTotalAmt}
						</div>
					</div>
				</div>	
				<div class="col-md-3" style="margin-top:15px">
					<div class="form-group clearfix">
						<label class="control-label col-md-4"></label>
						<div class="col-md-4">
						</div>
					</div>
					<div class="form-group clearfix">
						<label class="control-label col-md-4">微信扫码付：</label>
						<div class="col-md-4">
							${amtStatic.wxTotalAmt}
						</div>
					</div>
				</div>		
			</div>
			<div class="form-body clearfix">
				<div class="col-md-10">
					<div class="form-group col-md-2">
						<button type="button" class="btn green" onclick="javascript:window.location.href='${path}/adminManage/merchantstore/edit/0/${user.merchantNo}'">创建门店</button>
					</div>
					<div class="form-group  col-md-2">
						<button type="button" class="btn green" onclick="javascript:window.location.href='${path}/adminManage/merchantadmin/0?merchantNo=${user.merchantNo}'">新增账户</button>
					</div>
					<div class="form-group  col-md-2">
						<button type="button" class="btn green" onClick="window.parent.frames.addtabsByUrl('/adminManage/orderpayment')" >交易记录</button>
					</div>
					<div class="form-group  col-md-2">
						<button type="button" class="btn green" onClick="window.parent.frames.addtabsByUrl('/adminManage/withdrawals')" >提现记录</button>
					</div>
					<!-- <div class="form-group  col-md-3">
						<button type="button" class="btn green" onclick="window.parent.frames.toScan();">扫码收款</button>
					</div> -->
				</div>
			</div><br>
		</c:if>
		<c:if test="${user.identityFlag=='2'||user.identityFlag=='1'}">
		<div class="form-body clearfix">
			<label class="control-label" style="margin-left: 20px;">今日交易</label>
			<div class="col-md-12">	
				<div class="data_collect2" >
					<table>
						<tbody>
							<tr style="height: 50px;">
								<td >
									<div class="ico ico1"></div>
									<div class="data" id="bpos_money_s">
										<p>交易总金额(元)</p>
										<strong><fmt:formatNumber value="${amtStatic.orderTotalAmt}" pattern="#0.##"/></strong>
									</div>
								</td>
								<td>
									<div class="ico ico2"></div>
									<div class="data" id="bpos_money_s">
										<p>提现总金额(元)</p>
										<strong><fmt:formatNumber value="${amtStatic.txTotalAmt}" pattern="#0.##"/></strong>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="ico ico2"></div>
									<div class="data" id="bpos_money_s">
										<p>支付宝(元)</p>
										<strong><fmt:formatNumber value="${amtStatic.zfbTotalAmt}" pattern="#0.##"/></strong>
									</div>
								</td>
								<td>
									<div class="ico ico2"></div>
									<div class="data" id="bpos_money_s">
										<p>微信公众号(元)</p>
										<strong><fmt:formatNumber value="${amtStatic.wxgzhTotalAmt}" pattern="#0.##"/></strong>
									</div>
								</td>
								<td>
									<div class="ico ico2"></div>
									<div class="data" id="bpos_money_s">
										<p>微信扫码付(元)</p>
										<strong><fmt:formatNumber value="${amtStatic.wxTotalAmt}" pattern="#0.##"/></strong>
									</div>
								</td>							
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div></c:if>
		<br>
		<c:if test="${user.identityFlag=='2'|| user.identityFlag=='1'}">
		<div class="form-body clearfix">
			<div class="col-md-6">
				<div align="left" style="margin-top: 2%;width: 80%;margin-left: 1%;margin-bottom: 2%;">
				<h3>您有&nbsp;
				<a href="javascript:window.parent.frames.addtabsByUrl('/adminManage/merchantinfo')" >
					<span style="color:#4190de">${merchantList}</span>
				</a>&nbsp;个商户未审核</h3>
				</div>
			</div>
			<c:if test="${user.identityFlag=='1'}">
			<div class="col-md-6">
				<div align="left" style="margin-top: 2%;width: 80%;margin-left: 1%;margin-bottom: 2%;">
				<h3>您有&nbsp;
				<a href="javascript:window.parent.frames.addtabsByUrl('/adminManage/organwithdrawals/auditlist')" >
					<span style="color:#4190de">${organWithdrawalNumber}</span>
				</a>&nbsp;个代理商分润提现未审核</h3>
				</div>
			</div>
			</c:if>
		</div>
		</c:if>
		
		
				<c:if test="${user.identityFlag=='4'}">
			<div class="row form-horizontal" style="background:#FFF">
				<div class="col-md-1">
					<div class="form-group text-center" >
						<br>
						<img width="50" height="50" src="${path}/resources/images/default_family.jpg" style="border-radius:30px">
						<div><br><label>Hi,${user.shortName}</label></div>
					</div>
				</div>
				<%-- <div class="col-md-3">					
					<div class="form-group" style="margin-top: 40px;">
						<label class="control-label col-md-6">交易总金额(元)：</label>
						<div class="col-md-4" style="margin-top: 7px;">
							${amtStatic.orderTotalAmt}
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-6">支付宝(元)：</label>
						<div class="col-md-4" style="margin-top: 7px;">
							${amtStatic.zfbTotalAmt}
						</div>
					</div>
				</div> --%>
				<%-- <div class="col-md-3">
					<div class="form-group" style="margin-top: 40px;">
						<label class="control-label col-md-6">提现总金额(元)：</label>
						<div class="col-md-4" style="margin-top: 7px;">
							${amtStatic.txTotalAmt}
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-6">微信公众号(元)：</label>
						<div class="col-md-4" style="margin-top: 7px;">
							${amtStatic.wxgzhTotalAmt}
						</div>
					</div>
				</div>	 --%>
				<%-- <div class="col-md-3">
					<div class="form-group" style="margin-top: 80px;">
						<label class="control-label col-md-6">微信扫码付：</label>
						<div class="col-md-4" style="margin-top: 7px;">
							${amtStatic.wxTotalAmt}
						</div>
					</div>
				</div>		 --%>
			</div>
			<div class="form-body" style="background:#FFF">
				<div class="col-md-10">
					<%-- <div class="form-group col-md-2">
						<button type="button" class="btn green" onclick="javascript:window.location.href='${path}/adminManage/merchantstore/edit/0/${user.merchantNo}'">创建门店</button>
					</div>
					<div class="form-group  col-md-2">
						<button type="button" class="btn green" onclick="javascript:window.location.href='${path}/adminManage/merchantadmin/0?merchantNo=${user.merchantNo}'">新增账户</button>
					</div> --%>
					<div class="form-group  col-md-2">
						<button type="button" class="btn green" onClick="window.parent.frames.addtabsByUrl('/adminManage/orderpayment')" >交易记录</button>
					</div>
					<!-- <div class="form-group  col-md-2">
						<button type="button" class="btn green" onClick="window.parent.frames.addtabsByUrl('/adminManage/withdrawals')" >提现记录</button>
					</div> -->
					<div class="form-group  col-md-3">
						<button type="button" class="btn green" onclick="window.parent.frames.addtabsByUrl('/adminManage/merchantcashing')">扫码收款</button>
					</div>
				</div>
			</div><br>
		</c:if>
		
		
	</section>

</body>
</html>