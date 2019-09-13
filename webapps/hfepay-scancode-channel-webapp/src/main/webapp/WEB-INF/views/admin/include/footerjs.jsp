<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><!-- BEGIN FOOTER -->
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
<script type="text/javascript">
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
			var name = parent.selectMenuName(url);
			parent.addTabs({id: url, title: name, url: '${path}' + url, close: true});
			history.go(-1);
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
</script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- END CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="/resources/scripts/html5shiv.min.js"></script>
<script src="/resources/scripts/respond.min.js"></script>
<![endif]-->
