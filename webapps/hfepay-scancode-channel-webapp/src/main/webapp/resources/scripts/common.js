/**
 * 上传图片
 * file:type=file的input的id
 * imgPath: 上传成功后返回的图片路径，保存到imgPath的input中
 * preImg:预览框的id
 * 2015.03.30
 */
function submitForm(file,imgPath,preImg,data) {
	var filev = document.getElementById(file);
	var patn;
	if(data == 'apifile'){
		 patn = /\.zip$|\.rar$/;
	}else{
		 patn = /\.jpg$|\.png$|\.jpeg$|\.gif$|\.JPG$|\.PNG$|\.JPEG$|\.GIF$/;
	}
	$('#'+preImg).parent().find("label").remove();
	$('#'+preImg).parent().append('<label class="loading">上传中...</label>');
	if (filev.value != null && patn.test(filev.value)) {
		$.ajaxFileUpload({
					url : baseurl+'/adminManage/file/upload',
					data:{"typePath":data},
					type:'POST',
					secureuri : false,
					fileElementId : file,
					dataType : 'json',
					success : function(msg) {
						if(msg.executeStatus == 0){
							 var img = $('#'+imgPath);
							 var result = msg.values;//.split(";"); //包含id与路径
							 img.val(result);
							 $('#'+preImg).parent().find("label").remove();
							 if(data == 'apifile'){
								 $('#'+preImg).attr("href",result);
								 $('#'+preImg).parent().append('<label class="success">上传成功！</label>');
							 }else{
								 $('#'+preImg).attr("src",result);
							 }
						 }else{
							 bootbox.alert(msg.values,function(){});
						 }
					},
					error : function(data, status, e) {
						bootbox.alert('上传失败,请稍后重试');
					}
				});
	} else {
		$('#'+preImg).parent().find("label").remove();
		if(data == 'apifile'){
			bootbox.alert('选择压缩文件上传,格式要求【zip|rar】');
		}else{
			bootbox.alert('选择图片上传,格式要求【jpg|png|jpeg|gif|JPG|PNG|JPEG|GIF】');
		}
	}
	
}