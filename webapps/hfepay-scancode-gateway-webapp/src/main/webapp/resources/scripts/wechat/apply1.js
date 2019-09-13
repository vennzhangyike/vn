$(function(){
	reset();
	//注册按钮
	$("#next").click(function(){
		var realName=$("#realName").val();
		//var merchantName=$("#merchantName").val();
		var idCard=$("#idCard").val();

		var frontImg = $("#imgIdentityUp").val();
		var backImg = $("#imgIdentityCover").val();
		var handImg =$("#imgIdentity").val();
		var jsapiTicket = $("#jsapiTicket").val();
		
		if(!realName || realName==""){
			$.toast("请填写真实姓名",'cancel');
			return;
		}
		if(!jsapiTicket || jsapiTicket==""){
			$.toast("参数异常，请刷新重试",'cancel');
			return;
		}
		
//		if(!merchantName || merchantName==""){
//			$.toast("商户名称不能为空");
//			return;		
//		}
		if(!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(idCard)){
			$.toast("身份证不能格式有误",'cancel');
			return;
		}
		
		if(!frontImg || frontImg==""){
			$.toast("请上传身份证正面照",'cancel');
			return;		
		}
		if(!backImg || backImg==""){
			$.toast("请上传身份证反面照",'cancel');
			return;
		}
		if(!handImg || handImg==""){
			$.toast("请上传手持身份证照片",'cancel');
			return;
		}
		
		var dataJson = {"name":realName,"idcardImg1":frontImg,"idcardImg2":backImg,"idcardImg3":handImg,
				/*"merchantName":merchantName,*/"jsapiTicket":jsapiTicket,
				"idCard":idCard
		};
		$.showLoading("数据处理中...");//显示进度条
		$('#next').attr('disabled','disabled');
		//注册
		$.ajax({
				url:basePath+"/scancode/apply/step1",
				data:dataJson,
				type:"POST",
				success:function(json)
				{
					$.hideLoading();//隐藏进度条
					$('#next').removeAttr('disabled');
					json = JSON.parse(json);
					var executeStatus = json.errorCode; 
					if(executeStatus == "0"){
						location.href=basePath+"/scancode/apply2";
					}
					else{
						$.toast(json.errorMsg);
						return;
					}
				},
				error:function(){
					$('#next').removeAttr('disabled');
					$.hideLoading();//隐藏进度条
					$.toast("内部错误",'cancel');
				}
			});
	});
	
	
	$("#realName").on("blur",function(){
		var value = $(this).val();
		if(value==""){
			$.toast("请输入正确的姓名",'cancel');
		}else{
			reset();
		}
	});
	$("#idCard").on("blur",function(){
		var value = $(this).val();
		if(!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value)){
			$.toast("请输入正确的身份证号码",'cancel');
		}
		else{
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
    var imgIdentity =$("#imgIdentityCli");
    var imgIdentityUpCli = $("#imgIdentityUpCli");
    var imgIdentityCoverCli = $("#imgIdentityCoverCli");
   // var reload = $('#reload');
  //  var check = $('#check');
    imgIdentity.click(function (){
       /* var img=$("#imgIdentity").val();
        if(img!=""){
        	$('#uploadId').val("imgIdentity");
	        $('#uploadImgId').val("imgIdentityCli");
        	xian();
        }else{*/
        	 get('imgIdentity','imgIdentityCli');
       // }
    });
    imgIdentityUpCli.click(function (){
       /* var img=$("#imgIdentityUp").val();
        if(img!=""){
        	$('#uploadId').val("imgIdentityUp");
	        $('#uploadImgId').val("imgIdentityUpCli");
        	xian();
        }else{*/
        	get('imgIdentityUp','imgIdentityUpCli');
        //}
    });
    imgIdentityCoverCli.click(function (){
        /*var img=$("#imgIdentityCover").val();
        if(img!=""){
        	$('#uploadId').val("imgIdentityCover");
	        $('#uploadImgId').val("imgIdentityCoverCli");
        	xian();
        }else{*/
        	 get('imgIdentityCover','imgIdentityCoverCli');
      //  }
    });
    /*reload.click(function(){
    	 mei();
		 var uploadId = $('#uploadId').val();
		 var uploadImgId = $('#uploadImgId').val();
		 if(uploadId=="imgServerId") {
			 getalbum('imgServerId','previewImage');
		 }else{
			 get(uploadId, uploadImgId);
		 }
    });
    check.click(function(){
    	mei();
		var uploadId = $('#uploadId').val();
		var uploadImgId = $('#uploadImgId').val();
		var curImageSrc = $("#"+uploadImgId).attr('src');
        var imgArray = [];
        imgArray.push(curImageSrc);
        wx.previewImage({
          current: curImageSrc,
          urls: imgArray
        });
    });*/
    
	console.log("check success");
});
wx.error(function(res){
	/* alert(appId);
	alert(signature); */
	//alert(res);
	//hiApp.alert('<div style="text-align:center;">微信系统内部错误，请稍后重试！</div>');
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
    //sourceType: ['album'],
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
