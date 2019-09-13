<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title>${currentChannelInfo.nickName }扫码收款运营系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<jsp:include page="include/commoncss.jsp"></jsp:include>
<link rel="shortcut icon" type="image/x-icon" href="${currentChannelInfo.icon }">
</head>
<body>
<jsp:include page="include/head.jsp"></jsp:include>
<!-- main start -->
<div id="ucenterWrapper" class="ucenterWidth1 clearfix">
<jsp:include page="include/menu.jsp"></jsp:include>
<div id="mainTabs">
	<div class="tabs_item tabshow" id="tab_ucenterSection" control="ucenterSection">主页</div>
</div>
<div id="tab-content">
	<section class="tab-content" id="ucenterSection">
		<div class="col-md-12">
			<div align="left" style="margin-top: 4%;width: 80%;margin-left: 5%;">
			<c:if test="${merchantList>0}">
				<h3>您有&nbsp;
					<a href="#" id="/adminManage/merchantinfo" url="/adminManage/merchantinfo" 
						addtabs="商户基本信息" title="商户基本信息">
						<span style="color:#F00">${merchantList}</span>
					</a>&nbsp;个商户未审核</h3>
			</c:if>
			</div>
			<div style="margin-top: 8%;width: 90%;margin-left: 5%;padding: 5%;background-color: #fff;border: 1px solid #4190de;border-radius:2px!important;">
				<div class="details">
					<h3>欢迎<span style="color:#4190de;margin:0 10px">${currentScanAdminUser.userName }</span>进入运营平台首页.</h3>
					<br/>
					<p>
						请根据角色进行对应的操作.
					</p>
				</div>
			</div>
		</div>	
	</section>
</div>
<jsp:include page="include/footer.jsp"></jsp:include>
<script>  
var addTabs = function (obj) {  
    id = obj.id.replace(/\//g,'');  
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
        $("#frame_" + id).load(function(){
        	var href = this.contentWindow.location.href;
            if(href.indexOf("login")!=-1){
                    top.location.href='${path}/index/login';
            }
        });
        
    }/* else{
		var windows = $("#"+id+" iframe");
        if(windows){
        	windows[0].src=windows[0].src;
        }
	}	   */
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
});  
</script> 
</body>
</html>