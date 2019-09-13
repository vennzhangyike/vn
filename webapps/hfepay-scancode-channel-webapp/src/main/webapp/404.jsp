<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>
<!DOCTYPE html>
<!-- 
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.3.5
Version: 4.1.0
Author: KeenThemes
Website: http://www.keenthemes.com/
Contact: support@keenthemes.com
Follow: www.twitter.com/keenthemes
Like: www.facebook.com/keenthemes
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
License: You must have a valid license purchased only from themeforest(the above link) in order to legally use the theme for your project.
-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>Metronic | Extra - 404 Pag</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description" />
<meta content="" name="author" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link
	href="${path }/resources/assets/global/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${path }/resources/assets/global/plugins/simple-line-icons/simple-line-icons.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${path }/resources/assets/global/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />

<link href="${path }/resources/assets/admin/pages/css/error.css"
	rel="stylesheet" type="text/css" />

<link href="${path }/resources/assets/global/css/components.css"
	id="style_components" rel="stylesheet" type="text/css" />
<link href="${path }/resources/assets/admin/layout/css/layout.css"
	rel="stylesheet" type="text/css" />
<link href="${path }/resources/assets/admin/layout/css/custom.css"
	rel="stylesheet" type="text/css" />

<link rel="shortcut icon" href="favicon.ico" />
</head>

<body style="background-color: #dddddd;">
	<div style="margin-top: 8%;width: 80%;margin-left: 10%;padding: 5%;background-color: #ffffff;">
		<div class="page-content">
			<div class="page-bar">
				<ul class="page-breadcrumb">
					<li><i class="fa fa-home"></i> <a href="#">Home</a> <i
						class="fa fa-angle-right"></i></li>
					<li><a href="#">Extra</a> <i class="fa fa-angle-right"></i></li>
					<li><a href="#">404 Page</a></li>
				</ul>
				<div class="page-toolbar">
					<div class="btn-group pull-right">
						<button type="button"
							class="btn btn-fit-height grey-salt dropdown-toggle"
							data-toggle="dropdown" data-hover="dropdown" data-delay="1000"
							data-close-others="true"><a href="../index/login"> 登录 </a></button>
					</div>
				</div>
			</div>
			<!-- END PAGE HEADER-->
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12 page-404">
					<div class="number">404</div>
					<div class="details">
						<h3>Sorry! 你访问的地址不存在.</h3>
						<p>
							We can not find the page you're looking for.<br /> <a
								href="../index/login"> Return login </a> or try the search bar below.
						</p>
						<form action="#">
							<div class="input-group input-medium">
								<input type="text" class="form-control" placeholder="keyword...">
								<span class="input-group-btn">
									<button type="submit" class="btn blue">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
							<!-- /input-group -->
						</form>
					</div>
				</div>
			</div>
			<!-- END PAGE CONTENT-->
		</div>
	</div>
	<!--[if lt IE 9]>
<script src="${path }/resources/assets/global/plugins/respond.min.js"></script>
<script src="${path }/resources/assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
	<script src="${path }/resources/assets/global/plugins/jquery.min.js"
		type="text/javascript"></script>

</body>
</html>
