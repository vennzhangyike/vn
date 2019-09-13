
    var idimgSize,idimg2Size; // 个人认证图片的大小
    var idimgSizeHuman,idimg2SizeHuman; // 易读的个人认证图片的大小
    var yyzzImgSize; // 营业执照图片的大小
    var yyzzImgSizeHuman; // 易读的营业执照图片的大小
    var allowExt =['JPG','PNG'];
    var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
    var sizeLabel = ["B", "KB", "MB", "GB"];

    function getFiletype(filename) {
        var extStart  = filename.lastIndexOf(".")+1;
         return filename.substring(extStart,filename.length).toUpperCase();
    }
    // 判断上传的文件的大小
    function fileChange(target) {
        var fileSize = 0;
        if (isIE && !target.files) {
            var filePath = target.value;
            var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
            var file = fileSystem.GetFile (filePath);
            fileSize = file.Size;
        } else {
            fileSize = target.files[0].size;
        }
        return fileSize;
    }
    // 将文件大小转换成易读形式
    function calFileSize(size) {
        for (var index = 0; index < sizeLabel.length; index++) {
            if (size < 1024) {
                return round(size, 2) + sizeLabel[index];
            }
            size = size / 1024;
        }
        return round(size, 2) + sizeLabel[index];
    }
    function round(number, count) {
        return Math.round(number * Math.pow(10, count)) / Math.pow(10, count);
    }
    
    function change(fileId,resultId){
    	var obj = $("#"+fileId);
    	$(obj).parent().find('.errorTips').hide();
    	if ($(obj).val()) {
            idimgSize = fileChange(document.getElementById(fileId));
            idimgSizeHuman = calFileSize(idimgSize);
            if (idimgSize > 2*1024*1024) {
            	$(obj).parent().find('.errorTips').show().find('em').html('请将图片控制在2MB内（当前图片大小为'+idimgSizeHuman+')');
            }

            var img = $(obj).val();
            var fileExt = getFiletype(img);
            if ($.inArray(fileExt,allowExt) == -1) {
            	$(obj).parent().find('.errorTips').show().find('em').html("文件格式不正确");
            }else{//上传,将图片路径返回
            	uploadImg(fileId,resultId);
            }
        }
    }
    
    //上传图片
    function uploadImg(fileId,resultId){
    	$.ajaxFileUpload({
			url : basePath+'/user/file/upload',
			data:{"typePath":"business/"+$("#merchantNo").val()},//营业执照上传路径//根据用户编号区分路径
			secureuri : false,
			fileElementId : fileId,
			dataType : 'json',
			success : function(json) {
				if(json.executeStatus == "0"){
					$("#"+resultId).val(json.values);
					reset();
					//$("#show_"+resultId).attr("src",json.values).show();
				}else{
					$.toast(json.values);//.alert(json.values);
				}
			},
			error : function() {
				$.toast('图片上传失败,请稍后重试',"cancel");
			}
		});
    }
    
    function reset(){
		$("#errorDiv").hide();
		$("#errorMsg").text("");
	}
	
    