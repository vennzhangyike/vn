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
	
	$("#cancle").click(function(){
		history.go(-1);
	});
	
	$.validator.addMethod("isLegalCard", function(value, element) { 
		   var tel = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/; 
		   return this.optional(element) || (tel.test(value)); 
		   }, "身份证格式错误");

	/*验证表格*/
	$("#form").validate({
        event:"blur",
   	    rules: {
		    merchantNo: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    storeNo: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    storeName: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    storeType: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    servicePhone: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    serviceBegin: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    serviceEnd: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    storeAddress: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    storeAddressImg: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    storeDesc: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    licenseName: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    name: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    idCard: {
	   	        required: true,
	   	        isLegalCard:true,
	   	        maxlength: 25
	   	      },
		    idcardImg1: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    idcardImg2: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    idcardImg3: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    merchantLicense: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    merchantLicenseImg: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    storePhotosImg: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    groupPhotoImg: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    storeImg: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    isPhoto: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    auditOperator: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    auditReson: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    storeStatus: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    temp1: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    temp2: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    temp3: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    temp4: {
	   	        required: true,
	   	        maxlength: 25
	   	      }
   	    },
   	    messages: {
		    merchantNo: {
	   	        required: "请输入【商户编号】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    storeNo: {
	   	        required: "请输入【门店编号】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    storeName: {
	   	        required: "请输入【门店名称】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    storeType: {
	   	        required: "请输入【门店级别】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    servicePhone: {
	   	        required: "请输入【服务电话】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    serviceBegin: {
	   	        required: "请输入【营业开始时间】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    serviceEnd: {
	   	        required: "请输入【营业结束时间】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    storeAddress: {
	   	        required: "请输入【门店地址】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    storeAddressImg: {
	   	        required: "请输入【门店地址图片】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    storeDesc: {
	   	        required: "请输入【门店介绍】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    licenseName: {
	   	        required: "请输入【执照名称】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    name: {
	   	        required: "请输入【法人姓名】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    idCard: {
	   	        required: "请输入【身份证号】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    merchantLicense: {
	   	        required: "请输入【营业执照号】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    isPhoto: {
	   	        required: "请输入【相册照片】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    auditOperator: {
	   	        required: "请输入【审核人】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    auditReson: {
	   	        required: "请输入【审核理由】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    storeStatus: {
	   	        required: "请输入【门店状态 】",
	   	        maxlength: "长度不能超过25"
	   	      }
		},
   	    submitHandler:function(form){
   	    	$("#save").attr("disabled","disabled");
   			var flag = 0;
   			var errMsg = '';
//   			var text = $(".form-horizontal select");
//   			for(var i = 0; i < text.size();i++){
//   				var $this = $(text[i]);
//   				var val = $this.val();
//   				if(val == null || val == ''){
//   					flag ++ ;
//   				}
//   			}
   			if(flag == 0){
   				var baseUrl = $("#baseUrl").text().trim();
   				var datajson = $('#form').serialize();
   				$.ajax({
   					url:baseUrl+"/adminManage/merchantstore/save",
   					data:datajson,
   					type:"POST",
   					success:function(msg)
   					{
   						msg = $.parseJSON(msg);
   						sweetRedirect(msg,msg.url);
   					},
   					error: function(XMLHttpRequest, textStatus, errorThrown) {
   						$("#save").removeAttr("disabled");
   				    }
   				});
   			}else{
   				$("#save").removeAttr("disabled");
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