<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><!-- BEGIN FOOTER -->
<div class="page-footer">
	<div class="page-footer-inner">
		 2016 &copy; ${currentChannelInfo.companyName } 版权所有 ${currentChannelInfo.recordNumber }
	</div>
	<div class="scroll-to-top">
		<i class="icon-arrow-up"></i>
	</div>
</div>
<!-- END FOOTER -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="../resources/assets/global/plugins/respond.min.js"></script>
<script src="../resources/assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
<script src="${path}/resources/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<%-- <script src="${path}/resources/assets/global/plugins/jquery-migrate.min.js" type="text/javascript"></script> --%>
<!-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="${path}/resources/assets/global/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="${path}/resources/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<%-- <script src="${path}/resources/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script> --%>
<script src="${path}/resources/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="${path}/resources/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${path}/resources/assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="${path}/resources/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<%-- <script src="${path}/resources/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script> --%>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="${path }/resources/assets/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- END CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="/resources/scripts/html5shiv.min.js"></script>
<script src="/resources/scripts/respond.min.js"></script>
<![endif]-->
<script type="text/javascript">
$(function(){
	handfunction();
	//setInterval(handfunction,30000);
	/* setTimeout(handfunction(), 3000);  */
// 	$(".navInfoLi span").on("click",function(){
// 		$(".ucenterSub").stop(true).slideUp();
// 		$(this).parent().find(".ucenterSub").stop(true).slideToggle();
// 	});
	
	var Accordion = function(el, multiple) {
		this.el = el || {};
		this.multiple = multiple || false;

		// Variables privadas
		var links = this.el.find('.link');
		// Evento
		links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
	}

	Accordion.prototype.dropdown = function(e) {
		var $el = e.data.el;
			$this = $(this),
			$next = $this.next();

		if (!e.data.multiple) {
			$el.find('.submenu').not($next).slideUp().parent().removeClass('open');
			$el.find("span").removeClass('active');
		};
		
		$next.slideToggle();
		$this.parent().toggleClass('open');
		$this.toggleClass('active');
		
	}	

	var accordion = new Accordion($('#accordion'), false);
	
	$('.submenu').click(function(){
		$('#accordion .cur').removeClass('cur');
	});
	
	$('#accordion .personalCenter').click(function(){
		$(this).addClass('cur');
		$(".submenu").slideUp();
		$(".link").removeClass('active');
		$(".open").removeClass('open');
		$(".submenu li").removeClass('active');
		
		//tab切换
    	$(".tabs_item").removeClass("tabshow");
        $("#mainTabs .tabs_item:eq(0)").addClass('tabshow');
        $("#tab-content .tab-content").hide();  
        $("#tab-content #ucenterSection").fadeIn();
        $("#mainTabs .tabs_item:eq(0)").trigger("click");
	});
	
	$(".headerUl .headerLi1").hover(function(){
		$(".headerUl .centerLogout").slideToggle();
	});
	
	$("#dealWithPwd").on("click",function(){
		var box = '<form class="form-horizontal"><div class="form-body row">'+
		'<div class="form-group col-md-7">'+
			'<label class="control-label col-md-4">新密码</label>'+
			'<div class="col-md-8">'+
				'<input type="text" class="form-control" id="password">'+
			'</div>'+
		'</div>'+
		'<div class="form-group col-md-7">'+
			'<label class="control-label col-md-4">重复密码</label>'+
			'<div class="col-md-8">'+
				'<input type="text" class="form-control" id="passwordStr">'+
			'</div>'+
		'</div>'+
	   '</div></form>';
		var btn = {
			success:{   
		       label: "确认修改",
		       className: "btn-success",
		       callback: function() {
		    	    var password = $('#password').val();
		    	   	var passwordStr = $('#passwordStr').val();
		    		var flag = 0;
		    	   	if(passwordStr == '' || password == ''  || password == null) flag++;
		    	   	var errMsg = '密码不可为空!';
		    	   	if(password != '' && password != null ){
		   				var tel =  /\w$/;
		   			    if(!tel.test(password)){
		   				   flag++;
		   				   errMsg="请输入字母、数字、下划线!";
		   			    }else{
		   	   			    if(password.trim() != passwordStr.trim()){
		   	   			    	flag++;
		   	    				errMsg="两次输入的密码需要一样!";
		   	   			    }
		   			    }
		   			}
		    	   	if(flag > 0){
		    	   		bootbox.alert(errMsg,function(){});
		    	   	}else{
		    	    	$.ajax({
							   type: "POST",
							   url: "${path}/adminManage/sys/admin/save",
							   data:{"id":'${currentUser.id }',"userName":'${currentUser.userName}',"password":password},
							   success: function(msg){
								   	msg = $.parseJSON(msg);
						 			bootbox.alert(msg.values,function(){
					 			  	});
							   }
						});
		    	   	}
		       }
		     },
		     "取消": {
		        className: "btn-danger",
		        callback: function() {
		        }
		     }
		 }
		bootbox.dialog({
			message: box,
			title: "修改密码",
			onEscape: function() {},
			className: "green-haze",
			buttons: btn
		});
	});
	
	//页面加载完毕选中左侧菜单
	selectMenu(window.location.href);
	function selectMenu(href){
		if($(".navInfoLi li a").length==0){//菜单还没有加载完毕继续刷新
			window.location.reload();
		}
		var path = window.location.host;
		var menuUrl=window.location.href.replace(window.location.search,'');//替换掉多余的参数
		var index = menuUrl.indexOf(path)+path.length;
		var url = menuUrl.substr(index);
		url = dealUrl(url);
		if(url.length-1==url.lastIndexOf('/')){//是否/结尾
			 url =  url.substring(0,url.length-1);
		}
// 		console.log(url);
		var currentMenu = $(".navInfoLi li a[href='/"+url+"']");
// 		var currentMenu = $(".navInfoLi li a[href='"+path+"/"+url+"']");
		var parentLi = currentMenu.parent('li');//li变为选中状态
		//li的父菜单展开
		var ul = parentLi.parent('ul');
		ul.show();
		ul.parent().find("span").css("color","#ffffff");//一级菜单展开
		parentLi.addClass('active');//二级菜单选中
		
	}
	
	function dealUrl(url){
		var url1 = url;
		url =  url.match(/(\w+\/){4}\w+\/?/);
// 		console.log(url+"==4");
		if(url != null){
			url = url[0];
		}else{
			url=url1;
		}
		if(compareUrl(url) == null){
			url =  url.match(/(\w+\/){3}\w+\/?/);
// 			console.log(url+"==3");
			if(url != null){
				url = url[0];
			}else{
				url=url1;
			}
		}else{
			return url;
		}
		if(compareUrl(url) == null){
			url =  url.match(/(\w+\/){2}\w+\/?/);
// 			console.log(url+"==2");
			if(url != null){
				url = url[0];
			}else{
				url=url1;
			}
		}else{
			return url;
		}
		if(compareUrl(url) == null){
			url =  url.match(/(\w+\/){1}\w+\/?/);
// 			console.log(url+"==1");
			if(url != null){
				url = url[0];
			}else{
				url=url1;
			}
		}else{
			return url;
		}
		if(compareUrl(url) == null){
			return "#"; 
		}else{
			return url;
		}
	}
	
	function compareUrl(url){
		var atabs = $(".navInfoLi li a");
		if(url.length-1==url.lastIndexOf('/')){//是否/结尾
			 url =  url.substring(0,url.length-1);
		}
		var num = 0;
		for(var i = 0; i < atabs.size();i++){
			var href = $(atabs[i]).attr("href");			
			if(href.indexOf('/') == 0){
				href =  href.substring(1,href.length);
			}
			if(href == url){
// 				console.log(url+"=="+href);
				num++;
			}
		}
		if(num == 1){
			return url;
		}
		return null;
	}
});

function sweetAlert(msg){
	bootbox.alert(msg.values,function(){});
}

function sweetAlertWithFunc(msg,func){
	bootbox.alert(msg.values,func());
	setTimeout(function(){
		$("div.bootbox,div.modal-backdrop").fadeOut(function(){
			$(this).remove();
		});
	}, 1500);
}

function sweetRedirect(msg,url){
	sweetAlert(msg);
	setTimeout(function(){
		if(msg.executeStatus == '0'){
			location.href = '${path}' + url;
		}else{
			$("#save").removeAttr("disabled");
		} 
	}, 1500);
}

function sweetReload(msg){
	sweetAlert(msg);
	setTimeout(function(){
		if(msg.executeStatus == '0'){
			window.location.reload();
		}else{
			$("#save").removeAttr("disabled");
		}
	}, 1500);
}
function selectMenuName(href){
	var path = "${path}";
	var currentMenu = $(".navInfoLi li a[url='"+path + href+"']");
	return currentMenu.text();
}

function handfunction(){
	var path = "${path}";
	$.ajax({
		url:path+"/index/message",
		type : "POST",
		async : false,
		success: function(msg){
 			msg = $.parseJSON(msg);
 			var messageSize = msg.messageSize;
 			var select = $("#message");
 			var option = "("+messageSize+")";
 			select.html(option);
 		}
	});
}
</script>