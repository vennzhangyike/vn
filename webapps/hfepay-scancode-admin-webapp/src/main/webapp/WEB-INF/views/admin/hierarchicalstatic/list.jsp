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
<title>结算对账单</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<jsp:include page="../include/commoncss.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/select2/select2.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/datatables/extensions/Scroller/css/dataTables.scroller.min.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/datatables/extensions/ColReorder/css/dataTables.colReorder.min.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css"/>

<!-- BEGIN PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL STYLES -->
<style type="text/css">
.td0{
	text-align: right;
	font-weight: bolder;
}
.portlet span{
	line-height: 14px;
	margin-right: 2px;
	margin-bottom: 2px;
}
</style>
<link rel="shortcut icon" href="${currentChannelInfo.icon }"/>
</head>
<body class="page-header-fixed page-quick-sidebar-over-content">
<!-- BEGIN CONTAINER -->
<div class="page-container">
<section id="ucenterSection">
			<!-- BEGIN SEARCH CONDITION -->
			<div class="portlet box ">
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal" id="form">
						<div class="form-body">
<!-- 							<h3 class="form-section">查询</h3> -->
							<!--/row-->
							<div class="row">
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">机构编号</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="organNo" name="organNo">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
									<select id = "payCode" class="form-control" name="payCode" style="display:none;">
										<option value = "">  --  请选择  --  </option>
										<c:forEach var="item" items="${mappingDicionList}">
											<option value = "${item.paraVal}">${item.paraName}</option>
										</c:forEach>
									</select>						
							</div>
							<div class="portlet box green-haze"></div>
							<div class="row">
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4"></label>
										<div class="col-md-8">
											<button type="button" class="btn green" id="query">查询</button>
											<button type="button" class="btn default" id="cancle">重置</button>
										</div>
									</div>
								</div>
								<!--/span-->
							</div>
							
						</div>
					</form>
					<!-- END FORM-->
				</div>
			</div>
			<!-- END SEARCH CONDITION -->
			
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box green-haze">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-globe"></i>分润统计列表
								<span class="label label-success export" style="cursor: pointer;float: right;">导出excel</span>
							</div>
						</div>
						<div class="portlet-body" id="tablec">
							
						</div>
						<!-- <div class="portlet-body" id="tablec1">
							
						</div> -->
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
			<!-- END PAGE CONTENT-->
	<!-- END CONTENT -->
	</section>
</div>
<!-- END CONTAINER -->
<jsp:include page="../include/footerjs.jsp"></jsp:include>
<!-- BEGIN PAGE LEVEL PLUGINS -->

<script src="${path}/resources/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/ajaxfileupload.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${path}/resources/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="${path}/resources/assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js" charset="UTF-8"></script>
<div style="display: none;" id="baseUrl">${path}</div>
<script src="${path}/resources/scripts/admin/hierarchicalstatic/list.js?rnd=${version}" type="text/javascript"></script>
</body>
<!-- END BODY -->
<script>
var addTabs = function (obj,organNo) {  
    id = obj.id.replace(/\//g,'');  
    console.log(obj);  
    $("#tab-content .tab-content", window.parent.document).hide();  
    $("#mainTabs .tabs_item", window.parent.document).removeClass("tabshow");
    //如果TAB不存在，创建一个新的TAB  
    if (!$("#" + id)[0]) {  
        //固定TAB中IFRAME高度  
        mainHeight = $(document.body).height() - 48;  
        //创建新TAB的title  
        title = '<div class="tabs_item" id="tab_' + id + '" style="display:none;" control="' + id + '">' + obj.title;  
        //是否允许关闭  
        if (obj.close) {  
            title += ' <i class="close hairline" tabclose="' + id + '"></i>';  
        }  
        title += '</div>';  
        //是否指定TAB内容  
        if (obj.content) {  
            content = '<div class="tab-content" id="' + id + '" style="display:none;">' + obj.content + '</div>';  
        } else {//没有内容，使用IFRAME打开链接  
        	var url = obj.url + "?organNo=" + organNo;
        	content = '<div class="tab-content" id="' + id + '" style="display:none;"><iframe id="frame_' + id + '" src="' + url + '" width="100%" height="' + mainHeight +  
                    '" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe></div>';  
        }  
      	//如窗口存在则先关闭
        closeTab(id);
        //加入TABS  
        $("#mainTabs", window.parent.document).append(title);  
        $("#tab-content", window.parent.document).append(content); 
    }/* else{
		var windows = $("#"+id+" iframe");
        if(windows){
        	windows[0].src=windows[0].src;
        }
	}	  */   
    
    //激活TAB  
    $("#tab_" + id, window.parent.document).addClass('tabshow').fadeIn();  
    $("#" + id, window.parent.document).fadeIn();  
    
    var allTabs = sessionStorage.getItem("all");
    if(!allTabs){
		allTabs = [];
	}
	else{
		allTabs = JSON.parse(allTabs);
	}
	allTabs.push(obj);
	//将item存入sessionStorage
    sessionStorage.setItem('all',JSON.stringify(allTabs));
    sessionStorage.setItem("currentSelectItem",$(this).attr("id").replace(/\//g,''));
}; 

var closeTab = function (id) {  
    //关闭TAB  
    $("#tab_" + id, window.parent.document).remove();  
    $("#" + id, window.parent.document).remove();  
    var allTabsClose = sessionStorage.getItem("all");
    if(allTabsClose){
    	allTabsClose = JSON.parse(allTabsClose);
    	$.each(allTabsClose,function(i,item){
    		if(item){
    			var currentId = item.id.replace(/\//g,'');
        		if(id==currentId){
        			allTabsClose.splice(i,1);
        			return;
        		}
    		}
    	});
    	sessionStorage.setItem('all',JSON.stringify(allTabsClose));
    }
}; 

function addtabsByUrl(url,organNo){
	var baseurl = "${path}".trim();
	var initObj = $("[url='"+baseurl + url+"']", window.parent.document);
    var tab = {id: initObj.attr("id"), title: initObj.attr('title'), url: initObj.attr('url'), close: true};
    //初始个人中心基本信息tab
    addTabs(tab,organNo);
}; 
</script>
</html>