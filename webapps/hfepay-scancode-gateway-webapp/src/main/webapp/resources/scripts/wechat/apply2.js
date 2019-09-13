$(function(){
	reset();
	/*if($(".weui_switch").is(":checked")){
		$(".haszz").show();
		$("#license").text("有营业执照");
	}
	else{
		$(".haszz").hide();
	}*/
	var indexTab = 0;
	if($.trim($("#storeName"))!='' && $.trim($("#merchantLicense"))==''){//如果门店名称为空，说明是新增;门店不为空，同时执照为空说明是无执照的用户修改信息
		$('#tabChange a:eq(1)').click();//通过触发点击事件构造无执照的商户资料完善效果
	}
	$("#tabChange a").click(function(){
		$("#tabChange a").removeClass('weui-bar__item--on');
		$(this).addClass('weui-bar__item--on');//上边变化
		//图片位置变化
		var index = $('#tabChange a').index($(this));//获取当前是第几个tab被点击
		indexTab = index;
		if(index==0){//有营业执照显示四张图片同时填写营业执照号
			$("#licenceLi").show();
			$("#licencePic").show();
			$("#idhandimgLi").css({"width":"50%"});
		}else{//三张图片，最后一张宽度100%
			$("#licenceLi").hide();
			$("#licencePic").hide();
			$("#idhandimgLi").css({"width":"100%"});
		}
		
	});
//	$(".weui_switch").click(function(){//checkbox选择开关
//		var temp = $(this).is(":checked");
//		if(temp){
//			$(".haszz").show();
//			$("#license").text("有营业执照");
//		}else{
//			$(".haszz").hide();
//			$("#license").text("无营业执照");
//		}
//	});
	
	//注册按钮
	$("#next").click(function(){
		var merchantName = $("#merchantName").val();
		var storeName = $("#storeName").val();
		var address=$("#address").val();
		var servicePhone=$("#servicePhone").val();
		var merchantLicense=$("#merchantLicense").val();
		
		var idlicenceimg = $("#idlicenceimg").val();
		var idhandimg = $("#idhandimg").val();
		var idgroupimg =$("#idgroupimg").val();
		var idinimg =$("#idinimg").val();
		var jsapiTicket = $("#jsapiTicket").val();
		
		if(!merchantName ||merchantName==""){
			$.toast("请填写商户名称",'cancel');
			return;
		}
		if(!storeName ||storeName==""){
			$.toast("请填写门店名称",'cancel');
			return;
		}
		if(!/\d+$/.test(servicePhone)){
			$.toast("请填写正确的服务电话",'cancel');
			return;		
		}
		if(!jsapiTicket || jsapiTicket==""){
			$.toast("参数异常，请刷新重试",'cancel');
			return;
		}
		if(indexTab==0){
			if(!merchantLicense || merchantLicense==""){
				$.toast("营业执照不能为空",'cancel');
				return;
			}
		}
		$.showLoading("数据处理中...");//显示进度条
		$('#next').attr('disabled','disabled');
		var dataJson = {"storeName":storeName,/*"busType":busType,*/"storeAddress":address,"servicePhone":servicePhone,"merchantLicense":merchantLicense
				,"groupPhotoImg":idgroupimg,"storeImg":idinimg,"storePhotosImg":idhandimg,"merchantLicenseImg":idlicenceimg,"merchantName":merchantName,
				"jsapiTicket":jsapiTicket};
		//注册
		$.ajax({
				url:basePath+"/scancode/apply/step2",
				data:dataJson,
				type:"POST",
				success:function(json)
				{
					$.hideLoading();//隐藏进度条
					$('#next').removeAttr('disabled');
					json = JSON.parse(json);
					var executeStatus = json.errorCode; 
					if(executeStatus == "0"){
						location.href=basePath+"/scancode/apply3";
					}
					else{
						$.toast(json.errorMsg,'cancel');
						return;
					}
				},
				error:function(){
					$.hideLoading();//隐藏进度条
					$.toast("内部错误",'cancel');
					$('#next').removeAttr('disabled');
				}
			});
	});
	$("#storeName").on("blur",function(){
		var value = $(this).val();
		if(value==""){
			$.toast("请输入门店名称",'cancel');
		}else{
			reset();
		}
	});
	$("#merchantName").on("blur",function(){
		var value = $(this).val();
		if(value==""){
			$.toast("请输入商户名称",'cancel');
		}else{
			reset();
		}
	});
	
	$("#servicePhone").on("blur",function(){
		var value = $(this).val();
		if(value==""){
			$.toast("请输入服务电话",'cancel');
		}else{
			reset();
		}
	});
	
	function reset(){
		$("#errorDiv").hide();
		$("#errorMsg").text("");
	}
	
	function showDiv(msg){
		$("#errorDiv").show();
		$("#errorMsg").text(msg);
	}
	
});



/***微信图片上传***/
var appId=$("#appId").val();//'wxcd45db31731fce73';
var timestamp=$("#timestamp").val(); //'1477034918';
var nonceStr=$("#nonceStr").val(); //'ab6a98ec-abc1-4036-92dc-362eaa0add7e';
var signature=$("#signature").val(); //'6fbb2e3f2ce6b6974a3e9bf5b955471186966de4';
wx.config({
    debug: false, 
    appId: appId, 
    timestamp: timestamp, 
    nonceStr: nonceStr, 
    signature: signature,
    jsApiList: ['chooseImage','uploadImage','downloadImage'] 
});
wx.ready(function(){
    var idlicenceimgCli =$("#idlicenceimgCli");
    var idhandimgCli = $("#idhandimgCli");
    var idgroupimgCli = $("#idgroupimgCli");
    var idinimgCli = $("#idinimgCli");
    idlicenceimgCli.click(function (){
    	getalbum('idlicenceimg','idlicenceimgCli');
    });
    idhandimgCli.click(function (){
    	getalbum('idhandimg','idhandimgCli');
    });
    idgroupimgCli.click(function (){
    	getalbum('idgroupimg','idgroupimgCli');
    });
    idinimgCli.click(function (){
    	getalbum('idinimg','idinimgCli');
    });
	console.log("check success");
});
wx.error(function(res){
	console.log("check error");
});
wx.checkJsApi({
    jsApiList: ['chooseImage'], 
    success: function(res) {
    	console.log("JS"+res);
    }
});
function get(id,imgId){
	wx.chooseImage({
    count: 1,
    sizeType: ['compressed'],
    sourceType: ['camera'],
    success: function (res) {
        var localId = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
        $("#"+imgId).attr("src",localId);
        $("#"+id).val(localId);
        upload(id);	
    	}
	});
}
function getalbum(id,imgId){
	wx.chooseImage({
    count: 1,
    sizeType: ['compressed'],
    sourceType: ['album','camera'],
    success: function (res) {
        var localId = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
        $("#"+imgId).attr("src",localId);
        $("#"+id).val(localId);
        upload(id);	
    	}
	});
}
function upload(id){
	var localId=$("#"+id).val();
	wx.uploadImage({
    localId: localId, // 需要上传的图片的本地ID，由chooseImage接口获得
    isShowProgressTips: 1, // 默认为1，显示进度提示
    success: function (res) {
        	var serverId = res.serverId; //返回图片的服务器端ID
        	//alert("serverId==="+serverId);
        	$("#"+id).val(serverId);
    	}
	});	
}
