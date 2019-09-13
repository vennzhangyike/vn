$(function(){
	Metronic.init(); // 初始化框架核心组件
	Layout.init(); // 初始化网格
	QuickSidebar.init(); // 初始化快捷操作栏
	Demo.init(); // 初始化项目
	
	$('.date-picker').datepicker({
	    rtl: Metronic.isRTL(),
	    orientation: "left",
	    autoclose: true
	});
	
	$("#companyName").change(function(t){
		var channelNo = $("#companyName").find("option:selected").attr("channelNo");
		$("#form").find("#channelNo").val(channelNo);
	});
	
	$("#payName").change(function(t){
		var payCode = $("#payName").find("option:selected").attr("payCode");
		$("#form").find("#payCode").val(payCode);
	});
	
	function checkIsSame(){
		var channelNo = $("#companyName").find("option:selected").attr("channelNo");
		var id = $("#id").val();
		var bool = false;
		$.ajax({
			   url:"check",
	 		   data:{"channelNo":channelNo,"id":id}, 
	 		   type: "POST",
	 		   async:false,
	 		   success: function(msg){
	 			  msg = $.parseJSON(msg);
	 			  bool = msg.check;
	 		   }
	 		});
		return bool;
	}
	
	$("#cancle").click(function(){
		history.go(-1);
	});

	/*验证表格*/
	$("#form").validate({
        event:"submit",
   	    rules: {
		    companyName: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    boundIp: {
	   	        maxlength: 100
	   	      },
	   	   joinUserPublicKey: {
	   		   required: true
	   	      },
		    joinPublicKey: {
	   	        required: true
	   	      },
		    joinPrivateKey: {
	   	        required: true
	   	      }
   	    },
   	    messages: {
		    companyName: {
	   	        required: "请选择【渠道名称】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    boundIp: {
	   	        maxlength: "长度不能超过100"
	   	      },
	   	   joinUserPublicKey:{
	   		   required: "请输入【用户上传公钥】"
	   	   	  },
		    joinPublicKey: {
	   	        required: "请输入【平台公钥】"
	   	      },
		    joinPrivateKey: {
	   	        required: "请输入【平台私钥】"
	   	      }
		    
		},
   	    submitHandler:function(form){
   			var flag = 0;
   			var errMsg = '';
   			$("#companyName").removeAttr("disabled"); 
   			
   			if(checkIsSame()!=true){
   				flag++;
   				errMsg+="该渠道下已设置秘钥信息！";
   			}
   			
   			if(flag == 0){
   				var baseUrl = $("#baseUrl").text().trim();
   				var datajson = $('#form').serialize();
   				$.ajax({
   					url:baseUrl+"/adminManage/channelsecretkey/save",
   					data:datajson,
   					type:"POST",
   					success:function(msg)
   					{
   						msg = $.parseJSON(msg);
   			 			  bootbox.alert(msg.values,function(){
   			 				 if(msg.executeStatus == '0'){
   			 					location.href=baseUrl+msg.url;
   			 				 }
   			 			  });
   					}
   				});
   			}else{
   				bootbox.alert(errMsg,function(){});
   			}
			return false;
	    },
		errorPlacement: function(error, element) { 
		     error.appendTo(element.parent().parent()); 
		}
	}); 
	
	var text = $("select.form-control");
	for(var i = 0; i < text.size();i++){
		var $this = $(text[i]);
		var id = $this.attr("id");
		var val = $("."+id).val();
		var ops = $this.find("option");
		for(var j = 0; j < ops.size();j++){
			var opval = $(ops[j]).val();
			if(opval == val){
				$(ops[j]).attr("selected","selected");
			}else{
				$(ops[j]).removeClass("selected");
			}
		}
	}
		
});


//1		required:true	                  必须输入的字段。
//2		remote:"check.php"	          使用 ajax 方法调用 check.php 验证输入值。
//3		email:true	                          必须输入正确格式的电子邮件。
//4		url:true	                          必须输入正确格式的网址。
//5		date:true	                          必须输入正确格式的日期。日期校验 ie6 出错，慎用。
//6		dateISO:true	                  必须输入正确格式的日期（ISO），例如：2009-06-23，1998/01/22。只验证格式，不验证有效性。
//7		number:true	                          必须输入合法的数字（负数，小数）。
//8		digits:true				  必须输入整数。
//9		creditcard				  必须输入合法的信用卡号。
//10	equalTo:"#field"	          输入值必须和 #field 相同。
//11	accept:					  输入拥有合法后缀名的字符串（上传文件的后缀）。
//12	maxlength:5				  输入长度最多是 5 的字符串（汉字算一个字符）。
//13	minlength:10			  输入长度最小是 10 的字符串（汉字算一个字符）。
//14	rangelength:[5,10]		  输入长度必须介于 5 和 10 之间的字符串（汉字算一个字符）。
//15	range:[5,10]		  	  输入值必须介于 5 和 10 之间。
//16	max:5		   			  输入值不能大于 5。
//17	min:10					  输入值不能小于 10