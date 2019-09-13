<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title>${channelName }运营系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<jsp:include page="include/commoncss.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${path}/resources/styles/trans.css"/>
<link rel="shortcut icon" type="image/x-icon" href="${currentChannelInfo.icon }">
<style type="text/css">
.modal-dialog {
    width: 40%;
}
</style>
</head>
<body>
<jsp:include page="include/head.jsp"></jsp:include>
<!-- main start -->
<div id="ucenterWrapper" class="ucenterWidth1 clearfix">
<jsp:include page="include/menu.jsp"></jsp:include>
<div id="mainTabs">
	<div class="tabs_item tabshow" id="tab_mainTabsInfo" control="mainTabsInfo">我的账户</div>
</div>
<div id="tab-content">
</div>
<jsp:include page="include/footer.jsp"></jsp:include>
<script> 
var addTabs = function (obj) {  
    id = obj.id.replace(/\//g,'');  
    console.log(obj);  
    $("#tab-content .tab-content").hide();  
    $("#mainTabs .tabs_item").removeClass("tabshow");
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
        	content = '<div class="tab-content" id="' + id + '" style="display:none;"><iframe id="frame_' + id + '" src="' + obj.url + '" width="100%" height="' + mainHeight +  
                    '" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe></div>';  
        }  
        //加入TABS  
        $("#mainTabs").append(title);  
        $("#tab-content").append(content);  
    }/* else{
		var windows = $("#"+id+" iframe");
        if(windows){
        	windows[0].src=windows[0].src;
        }
	}	  */   
       
    //激活TAB  
    $("#tab_" + id).addClass('tabshow').fadeIn();  
    $("#" + id).fadeIn();  
};  
   
var closeTab = function (id) {  
    //如果关闭的是当前激活的TAB，激活他的前一个TAB  
    if ($("#mainTabs .tabshow").attr('id') == "tab_" + id) {  
        $(".tabs_item").removeClass("tabshow");
        $("#tab_" + id).prev(".tabs_item").addClass('tabshow');
        var current = $("#" + id).prev(".tab-content").show();  
        sessionStorage.setItem("currentSelectItem",current.attr('id'));
    }
    else{
    	 sessionStorage.setItem("currentSelectItem",$('.tabshow').attr('control'));
    }
    //关闭TAB  
    $("#tab_" + id).remove();  
    $("#" + id).remove();  
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
function resizeDom(){
	var mainTabsW = $(document.body).width() - 200;//353 - 234;
	$("#mainTabs").css("width",mainTabsW);
}   
$(function () { 
	
	//我的账户信息
    var mainHeight = $(document.body).height() - 48;  
    var content ='<div class="tab-content" id="mainTabsInfo" ><iframe id="frame_mainTabs" src="${path}/adminManage/mainTabs" width="100%" height="' + mainHeight +  
        '" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe></div>'
    $("#tab-content").append(content);  
        
	var refreshTabs = sessionStorage.getItem("all");
	if(refreshTabs){
		refreshTabs = JSON.parse(refreshTabs);
		$.each(refreshTabs,function(i,item){
			addTabs(item);
		});
		var select = sessionStorage.getItem("currentSelectItem");
		 $("#tab-content .tab-content").hide();  
		 $("#mainTabs .tabs_item").removeClass("tabshow");
		//激活TAB  
	    $("#tab_" + select).addClass('tabshow').show();  
	    $("#" + select).show(); 
	}
	
	$(window).on("resize",function() {
		resizeDom();
	});
	resizeDom();
    $("#mainTabs").on("click",".tabs_item",function (e) { 
    	//tab切换
    	$(".tabs_item").removeClass("tabshow");
        $(this).addClass('tabshow');
        var control = $(this).attr('control');
        $("#tab-content .tab-content").hide();  
        $("#" + control).fadeIn();
       /*  var windows = $("#tab-content div :visible")[0];
        if(windows){
        	windows.contentWindow.location.reload(true);
        } */
        sessionStorage.setItem("currentSelectItem",control);
    });  
    $("[addtabs]").click(function () { 
    	//菜单选中
    	$(".navInfoLi li").removeClass('active');  
    	$(this).parent().addClass('active'); 
    	var tab = {id: $(this).attr("id"), title: $(this).attr('title'), url: $(this).attr('url'), close: true};
    	//增加tab
        addTabs(tab);  
        var allTabs = sessionStorage.getItem("all");
    	if(!allTabs){
    		allTabs = [];
    	}
    	else{
    		allTabs = JSON.parse(allTabs);
    	}
    	$.each(allTabs,function(i,item){
    		if(item){
    			var currentId = item.id;
        		if(tab.id==currentId){
        			allTabs.splice(i,1);
        			return;
        		}
    		}
    	});
    	allTabs.push(tab);
    	//将item存入sessionStorage
        sessionStorage.setItem('all',JSON.stringify(allTabs));
        sessionStorage.setItem("currentSelectItem",$(this).attr("id").replace(/\//g,''));
    });  
   
    $("#mainTabs").on("click", "[tabclose]", function (e) {  
        id = $(this).attr("tabclose");  
        closeTab(id);  
        e.stopPropagation();
    }); 
    
    /*  var initObj = $("[addtabs='基本信息']");
    var tab = {id: initObj.attr("id"), title: initObj.attr('title'), url: initObj.attr('url'), close: true};
    //初始个人中心基本信息tab
    addTabs(tab); */
    
  	
}); 
function addtabsByUrl(url,parm){
	var baseurl = "${path}".trim();
	var initObj = $("[url='"+baseurl + url+"']");
	if(!parm){
		parm="";
	}
    var tab = {id: initObj.attr("id"), title: initObj.attr('title'), url: initObj.attr('url') + parm, close: true};
    closeTab(initObj.attr("id").replace(/\//g,''));
    //初始个人中心基本信息tab
    addTabs(tab);
}; 
//显示推广码
function agentPromotion(agentNo,agentName){
	$.ajax({
		   type: "POST",
		   url: "agentbase/promotion/"+agentNo,
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
}; 
//申请分润提现
function addWithDraw(){
	var box = '<form class="form-horizontal"><div class="form-body row">'+
				'<div class="form-group col-md-7">'+
					'<label class="control-label col-md-4">提现金额</label>'+
					'<div class="col-md-8">'+
						'<input type="text" class="form-control" id="balance" placeholder="提现金额">'+
					'</div>'+
				'</div>'+
				'<div class="form-group col-md-7">'+
					'<label class="control-label col-md-4">备注</label>'+
					'<div class="col-md-8">'+
						'<input type="text" class="form-control" id="remark" placeholder="备注">'+
					'</div>'+
				'</div>'+
			   '</div></form>';
    var btn = {
    		success:{   
		       label: "申请提现",
		       className: "btn-success",
		       callback: function() {
		    	   var errMsg = '';
		    	   	var balance = $('#balance').val().trim();
		    	   	var remark = $('#remark').val().trim();
		    	   	var baseurl = "${path}".trim();
		    	   	if(Number(balance)<=0){
		    	   		bootbox.alert("提现金额有误,金额必须大于0！<br>",function(){});
		    	   		return;
		    	   	}
		    	   	if(balance==""||!/^\d+(\.\d{1,2})?$/.test(balance)) {
		    	   		bootbox.alert("提现金额有误,最多保留两位小数！<br>",function(){});
		    	   		return;
		    	   	}
		    	   	else{
		    	   		$.ajax({
							   type: "POST",
							   url:baseurl + "/adminManage/organwithdrawals/addWithDraw",
							   data:{"remark":remark,"balance":balance}
						}).done(function(msg){
							msg = $.parseJSON(msg);
							if(msg.errorCode=='0'){//成功
								bootbox.alert("提现申请成功",function(){
					 				location.reload();
					 			});
							}
							else{
								bootbox.alert(msg.errorMsg,function(){});
							}
				 			
						}).error(function(msg){
							bootbox.alert("申请提现失败",function(){});
						});
		    	   	}
		       }
		     },
		     "取消": {
		        className: "btn-danger",
		        callback: function() {
		        
		        	}
				}
		 };
    bootbox.dialog({
	   message: box,
	   title: "提现申请",
	   onEscape: function() {},
	   className: "green-haze",
	   buttons: btn
	 });
};


function toScan(){
	$.ajax({
		   type: "POST",
		   url: "toscan",
		   success: function(msg){
			   var btn = {
				success:{   
			       label: "确认",
			       className: "btn-default",
			       callback: function() {
			    	   toSubmit();
			       }
			     },
			     Cancel:{   
				       label: "关闭",
				       className: "btn-success",
				       callback: function() {
				       }
				     }
			   };
			   bootbox.dialog({
				   message: msg,
				   title: "扫码收款",
				   onEscape: function() {},
				   className: "green-haze",
				   buttons: btn
				 });
		   }
	});
}
//扫码支付
$("body").delegate("#money","keydown",function(e){
	if(e.keyCode==13){
		var money = $.trim($("#money").val());
		if(Number(money)<=2.5){
	   		bootbox.alert("消费金额必须大于2.50！<br>",function(){});
	   		return;
	   	}
	   	if(money==""||!/^\d+(\.\d{1,2})?$/.test(money)) {
	   		bootbox.alert("消费金额有误,最多保留两位小数！<br>",function(){});
	   		return;
	   	}
		$("#moneyDiv").show();
		$('#code').focus();
	}
});
/* $("body").delegate("#code","keydown",function(e){
	if(e.keyCode==13){
		toSubmit();
	}
}); */

function toSubmit(){
	var money = $.trim($("#money").val());
	var code=$.trim($("#code").val());
	var payway = $("#payways :radio:checked").val();
   	if(!payway){
   		bootbox.alert("请选择支付方式！<br>",function(){});
   		return;
   	}
	if(Number(money)<=0){
   		bootbox.alert("消费金额必须大于0！<br>",function(){});
   		return;
   	}
   	if(money==""||!/^\d+(\.\d{1,2})?$/.test(money)) {
   		bootbox.alert("消费金额有误,最多保留两位小数！<br>",function(){});
   		return;
   	}
   	if(!code||code==""){
   		bootbox.alert("付款授权码不能为空.<br>",function(){});
   		return;
   	}
   	var dataJson = {"orderAmt":money,"authCode":code,"payCode":payway};
	$.ajax({
		   type: "POST",
		   url: "pay",
		   dataType:"json",
		   data:dataJson
	}).done(function(msg){//成功提交
		if(msg.errorCode=='0'){
			bootbox.alert("支付成功.<br>",function(){});
		}
		else{
			bootbox.alert(msg.errorMsg+".<br>",function(){});
		}
		$('.btn-success').click();
	}).error(function(msg){//提交失败
		bootbox.alert("系统错误.<br>",function(){});
	});
}
</script> 
<script src="${path}/resources/scripts/qrcode/jquery.qrcode.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/qrcode/qrcode.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/qrcode/drawQrCode.js" type="text/javascript"></script>
</body>
</html>