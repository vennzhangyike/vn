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
<title>500</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<style type="text/css">
	html,body,div,span,applet,object,iframe,h1,h2,h3,h4,h5,h6,p,blockquote,pre,a,abbr,acronym,address,big,cite,code,del,dfn,em,img,ins,kbd,q,s,samp,small,strike,strong,sub,sup,tt,var,b,u,i,center,dl,dt,dd,ol,ul,li,fieldset,form,label,legend,table,caption,tbody,tfoot,thead,tr,th,td,article,aside,canvas,details,embed,figure,figcaption,footer,header,hgroup,menu,nav,output,ruby,section,summary,time,mark,audio,video{margin:0;padding:0;border:0;font:inherit;font-size:100%;vertical-align:baseline}html{line-height:1}ol,ul{list-style:none}table{border-collapse:collapse;border-spacing:0}caption,th,td{font-weight:normal;vertical-align:middle}q,blockquote{quotes:none}q:before,q:after,blockquote:before,blockquote:after{content:"";content:none}a img{border:none}article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section,summary{display:block}html{width:100%;height:100%}body{color:#000;background:#fff;font-size:14px;-webkit-font-smoothing:antialiased;font-smoothing:antialiased;font-family:Helvetica, "STHeiti STXihei", "Microsoft JhengHei", "Microsoft YaHei", Tohoma, Arial;width:100%;height:100%;overflow-x:hidden;overflow-y:hidden}.container{padding-top:80px;min-width:320px}.dragon-ctrl{margin:0 auto;width:130px;height:104px;background: url("../resources/images/wechat/dragon.gif") center center no-repeat;background-size:130px 104px}.error-msg{margin-top:30px;text-align:center;color:#333;font-size:16px;line-height:24px}.error-code{text-align:center;font-size:14px;line-height:14px;margin-top:10px;color:#999}
</style>
</head>
<body>

<div class="container">
	<div style="margin: 0 auto;width: 130px;height: 104px;background: url(${path}/resources/images/wechat/dragon.gif) center center no-repeat;background-size: 130px 104px;">
	</div>
	<div class="error-msg">
		哎呀，页面被霸王龙破坏了~<br>
		请联系管理员~
	</div>
	<div class="error-code">
		编号：500
	</div>
</div>
	
</body></html>