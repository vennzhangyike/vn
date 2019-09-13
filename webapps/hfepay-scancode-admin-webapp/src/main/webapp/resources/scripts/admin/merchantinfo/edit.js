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
	
	$("#channelNo").on("change",function(){
		var apiCode = $(this).val();
		var options = $("#agentNo option");
		$("#agentNo").val("");
		for(var i = 0; i < options.size();i++){
			var $this = $(options[i]);
			$this.css("display","block");
			var opval = $this.attr("val");
			if(opval != apiCode && apiCode != '' && opval != '----'){
				$this.css("display","none");
			}
		}
	});
	
	
	function checkMobile(mobile){
		var mobile = mobile;
		var id =$("#id").val();
		var bool = false;
		var baseUrl = $("#baseUrl").text().trim();
		$.ajax({
			   url:baseUrl+"/adminManage/merchantinfo/existMobile",
	 		   data:{"mobile":mobile,"id":id}, 
	 		   type: "POST",
	 		   async:false,
	 		   success: function(data){
	 			  if(data=="true"){
	 				 bool = true;
	 			  }
	 			  
	 		   }
	 		});
		return bool;
	}
	
	$("#mobile").blur(function(){
		var $this = $(this);
        var name = $this.attr("id");
        var errMsg = '手机号码已存在';
        if (checkMobile($("#mobile").val()) && $("#mobile").val().trim()!="") {
        	$("#" + name+ "-error").remove();
        	$this.parent().parent().append('<label id="' + name + '-error" class="error" for="' + name + '">' + errMsg + '，不可用！</label>');
        }
	});
	
	$.validator.addMethod("isMobile", function(value, element) { 
		   var length = value.length; 
		   var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/; 
		   return this.optional(element) || (length == 11 && mobile.test(value)); 
		 }, "请正确填写您的手机号码"); 
	$.validator.addMethod("isPhone", function(value, element) { 
		var mobile = /(^\d{3,4}-?\d{3,4}-?\d{3,4}$)|(^1[34578]{1}[0-9]{9}$)/;
		   return this.optional(element) || (mobile.test(value)); 
		 }, "请正确填写您的电话号码"); 
	$.validator.addMethod("isLegalCard", function(value, element) { 
	   var tel = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/; 
	   return this.optional(element) || (tel.test(value)); 
	   }, "身份证格式错误");

	/*验证表格*/
	$("#form").validate({
        event:"blur",
   	    rules: {
		    channelNo: {
	   	        required: true,
	   	        maxlength: 50
	   	      },
		    agentNo: {
	   	        required: true,
	   	        maxlength: 50
	   	      },
		    merchantName: {
	   	        required: true,
	   	        maxlength: 50
	   	      },
		    shortName: {
	   	        required: true,
	   	        maxlength: 50
	   	      },
		    platformMerchantNo: {
	   	        maxlength: 50
	   	      },
		    busType: {
	   	        required: true,
	   	        maxlength: 50
	   	      },
		    name: {
	   	        required: true,
	   	        maxlength: 30
	   	      },
		    idCard: {
	   	        required: true,
	   	        isLegalCard:true,
	   	        maxlength: 30
	   	      },
		    mobile: {
	   	        required: true,
	   	        isMobile:true,
	   	        maxlength: 20
	   	      },
		    phone: {
	   	        required: true,
	   	        isPhone:true,
	   	        maxlength: 20
	   	      },
		    email: {
		    	email:true,
	   	        maxlength: 50
	   	      },
		    address: {
		    	required: true,
	   	        maxlength: 50
	   	      },
		    merchantLicense: {
	   	        maxlength: 50
	   	      },
		    taxNo: {
	   	        maxlength: 50
	   	      },
		    orgNo: {
	   	        maxlength: 50
	   	      },
	   	   storeName: {
	   	        required: true,
	   	        maxlength: 50
	   	      },
	   	   servicePhone: {
		   	    required: true,
		   	    maxlength: 20
		   	  },
		   	storeAddress: {
			   	required: false,
			   	maxlength: 250
			  },
			  remark: {
			   	required: false,
			   	maxlength: 500
			  }
   	    },
   	    messages: {
		    channelNo: {
	   	        required: "请输入【渠道编号】",
	   	        maxlength: "长度不能超过50"
	   	      },
		    agentNo: {
	   	        required: "请输入【代理商编号】",
	   	        maxlength: "长度不能超过50"
	   	      },
		    merchantName: {
	   	        required: "请输入【商户名称】",
	   	        maxlength: "长度不能超过50"
	   	      },
		    shortName: {
	   	        required: "请输入【商户简称】",
	   	        maxlength: "长度不能超过50"
	   	      },
		    platformMerchantNo: {
	   	        required: "请输入【转接平台号】",
	   	        maxlength: "长度不能超过50"
	   	      },
		    busType: {
	   	        required: "请输入【经营类型】",
	   	        maxlength: "长度不能超过50"
	   	      },
		    name: {
	   	        required: "请输入【法人姓名】",
	   	        maxlength: "长度不能超过30"
	   	      },
		    idCard: {
	   	        required: "请输入【法人身份证】",
	   	        maxlength: "长度不能超过30"
	   	      },
		    mobile: {
	   	        required: "请输入【手机号码】",
	   	        maxlength: "长度不能超过20"
	   	      },
		    phone: {
	   	        required: "请输入【联系电话】",
	   	        maxlength: "长度不能超过20"
	   	      },
		    email: {
		    	email:"请输入正确的邮箱格式",
	   	        maxlength: "长度不能超过100"
	   	      },
		    address: {
	   	        maxlength: "长度不能超过50",
	   	        required: "请输入【地址】"
	   	      },
		    merchantLicense: {
	   	        maxlength: "长度不能超过50"
	   	      },
		    taxNo: {
	   	        maxlength: "长度不能超过50"
	   	      },
		    orgNo: {
	   	        maxlength: "长度不能超过50"
	   	      },
	   	   storeName: {
	   	        required: "请输入【门店名称】",
	   	        maxlength: "长度不能超过50"
	   	      },
	   	   servicePhone: {
		   	    required: "请输入【服务电话】",
		   	    maxlength: "长度不能超过20"
		   	  },
		   	storeAddress: {
			   	required: "请输入【门店地址】",
			   	maxlength: "长度不能超过250"
			  },
			remark: {
			   	maxlength: "长度不能超过500"
			  }
		},
   	    submitHandler:function(form){
   	    	
   	    	$("#save").attr("disabled","disabled");
   			var flag = 0;
   			var errMsg = '';
   			if(checkMobile($("#mobile").val())){
   				flag ++ ;
				errMsg += "该手机号码在商户中已使用!";
   			}
   			var text = $(".form-horizontal input[val='hidden']");
   			for(var i = 0; i < text.size();i++){
   				var $this = $(text[i]);
   				var val = $this.val();
   				if(val == null || val == ''){
   					flag ++ ;
   					errMsg += $this.attr("title") + "不可为空!";
   				}
   			}
   			var busType = $("#busType").val();
   			if(busType == ''){
				flag++;
				errMsg = "经营类型必须选择！<br/>";
			}
   			if(flag == 0){
   				var baseUrl = $("#baseUrl").text().trim();
   				var datajson = $('#form').serialize();
   				$.ajax({
   					url:baseUrl+"/adminManage/merchantinfo/save",
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
	
	function setCategory(obj,level){
		var baseUrl = $("#baseUrl").text().trim();
		var categoryNo = $(obj).find("option:selected").attr("categoryNo");	
		var name = $(obj).find("option:selected").text();	
		var busTypeStr = name+"(" + categoryNo + ")";
		if(categoryNo == "null" || categoryNo == ''){
			var id = $(obj).val();
			$.ajax({
				url:baseUrl+"/adminManage/hfepaycategory/list",
				data:{"parentId":id,"level":level},
				type:"POST",
				success:function(data)
				{
					var json = JSON.parse(data); 
					var objList = json.objList;
					var item = "<option  value='' selected = 'selected'>----请选择----</option>"
					for(var i in objList){ 
						var option = "<option value='" + objList[i].id + "' categoryNo = '" + objList[i].categoryNo + "'>" + objList[i].name + "</option>"
						if(level == 2){
							if(i==0){
								$("#categoryTwo").css("display","block");
								$("#categoryTwo").empty();
								$("#categoryTwo").append(item); 
							}
							$("#categoryTwo").append(option); 
						}else if(level == 3){
							if(i==0){
								$("#categoryThree").css("display","block");
								$("#categoryThree").empty();
								$("#categoryThree").append(item); 
							}
							$("#categoryThree").append(option); 
						}
						
					}
					if(objList.length == 0){
						$('#categoryError').css("display","block");
						if(level == 2){
							$("#categoryTwo").empty();
						}else if(level == 3){
							$("#categoryThree").empty();
						}
					}else{
						$('#categoryError').css("display","none");
					}
				}
			});
		}else{
			$("#busType").val(categoryNo);
			$("#busTypeStr").text(busTypeStr);
			if(level == 2){
				$("#categoryTwo").css("display","none");
				$("#categoryThree").css("display","none");
			}else if(level == 3){
				$("#categoryThree").css("display","none");
			}
		}
	}
	$("#categoryOne").change(function(){
		setCategory(this,2);
	});
	$("#categoryTwo").change(function(){
		setCategory(this,3);
	});
	$("#categoryThree").change(function(){
		var categoryNo = $(this).find("option:selected").attr("categoryNo");	
		var name = $(this).find("option:selected").text();	
		var busTypeStr = name+"(" + categoryNo + ")";
		if(categoryNo == null || categoryNo == ''){
			$('#categoryError').css("display","block");
		}else{
			$("#busType").val(categoryNo);
			$("#busTypeStr").text(busTypeStr);
		}
	});
		
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