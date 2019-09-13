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
	
	selectUtil.initAgent();
	
	$("#bankCard").on("keyup afterpaste",function(){
		var value = $(this).val();
		$(this).val(value.replace(/\D/g,""));
		//输入超过六位开始条用查询
		if(value.length<2){
			$("#bankName").val("");
			$("#bankCode").val("");
		}
		if(value.length>=2&&value.length<11){
			$.ajax({
  				type: "POST",
  				url: "getBankInfo",
  				data: {"bankCard":value},
  				success:function(msg){
  					$("#-error").remove();
  					if(msg && msg!="" && msg!=null){
  						var card = JSON.parse(msg);
  	  					$("#bankName").val(card.bankName);
  	  					if(card.clearBankChannelNo == null){
  	  						$("#bankCode").removeAttr("readonly");
  	  					}else{
  	  						$("#bankCode").val(card.clearBankChannelNo);
  	  						$("#bankCode").attr("readonly","readonly");
  	  					}
  	  					$("#accountType").val("0");
	  					$("#accountType").attr("disabled","disabled");

  	  					
  					}
  					else{
  						$("#bankName").val("");
  	  					$("#bankCode").val("");
  	  					$("#accountType").val("");
  	  					$("#bankCode").removeAttr("readonly");
  	  					$("#bankName").removeAttr("readonly");
  	  					$("#accountType").removeAttr("disabled");

  					}
  				}
  			});
		}
	});
	
	
	function checkIsSame(){
		var agentNo = $("#agentNo").val();
		var id = $("#id").val();
		var bool = false;
		$.ajax({
			   url:"check",
	 		   data:{"agentNo":agentNo,"id":id}, 
	 		   type: "POST",
	 		   async:false,
	 		   success: function(msg){
	 			  msg = $.parseJSON(msg);
	 			  bool = msg.check;
	 		   }
	 		});
		return bool;
	}
	
	var vcity={ 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
            21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
            33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",
            42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
            51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",
            63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"
           };


	// 检查号码是否符合规范，包括长度，类型
	isCardNo = function(card) {
		// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
		var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
		if (reg.test(card) === false) {
			return false;
		}

		return true;
	};

	// 取身份证前两位,校验省份
	checkProvince = function(card) {
		var province = card.substr(0, 2);
		if (vcity[province] == undefined) {
			return false;
		}
		return true;
	};

	// 检查生日是否正确
	checkBirthday = function(card) {
		var len = card.length;
		// 身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
		if (len == '15') {
			var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/;
			var arr_data = card.match(re_fifteen);
			var year = arr_data[2];
			var month = arr_data[3];
			var day = arr_data[4];
			var birthday = new Date('19' + year + '/' + month + '/' + day);
			return verifyBirthday('19' + year, month, day, birthday);
		}
		// 身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
		if (len == '18') {
			var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
			var arr_data = card.match(re_eighteen);
			var year = arr_data[2];
			var month = arr_data[3];
			var day = arr_data[4];
			var birthday = new Date(year + '/' + month + '/' + day);
			return verifyBirthday(year, month, day, birthday);
		}
		return false;
	};

	// 校验日期
	verifyBirthday = function(year, month, day, birthday) {
		var now = new Date();
		var now_year = now.getFullYear();
		// 年月日是否合理
		if (birthday.getFullYear() == year
				&& (birthday.getMonth() + 1) == month
				&& birthday.getDate() == day) {
			// 判断年份的范围（3岁到100岁之间)
			var time = now_year - year;
			if (time >= 3 && time <= 100) {
				return true;
			}
			return false;
		}
		return false;
	};

	// 校验位的检测
	checkParity = function(card) {
		// 15位转18位
		card = changeFivteenToEighteen(card);
		var len = card.length;
		if (len == '18') {
			var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
					8, 4, 2);
			var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4',
					'3', '2');
			var cardTemp = 0, i, valnum;
			for (i = 0; i < 17; i++) {
				cardTemp += card.substr(i, 1) * arrInt[i];
			}
			valnum = arrCh[cardTemp % 11];
			if (valnum == card.substr(17, 1)) {
				return true;
			}
			return false;
		}
		return false;
	};

	// 15位转18位身份证号
	changeFivteenToEighteen = function(card) {
		if (card.length == '15') {
			var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
					8, 4, 2);
			var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4',
					'3', '2');
			var cardTemp = 0, i;
			card = card.substr(0, 6) + '19' + card.substr(6, card.length - 6);
			for (i = 0; i < 17; i++) {
				cardTemp += card.substr(i, 1) * arrInt[i];
			}
			card += arrCh[cardTemp % 11];
			return card;
		}
		return card;
	};
	
	
	$.validator.addMethod("isMobile", function(value, element) { 
		   var length = value.length; 
		   var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/; 
		   return this.optional(element) || (length == 11 && mobile.test(value)); 
		 }, "请正确填写您的手机号码"); 

	$.validator.addMethod("isLegalCard", function(value, element) { 
	   var tel = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/; 
	   return this.optional(element) || (tel.test(value)); 
	   }, "身份证格式错误");
	
	$.validator.addMethod("isIllegalString", function(value, element) { 
		   var tel = /[@#\$%\^&\*]+/g; 
		   return this.optional(element) || (!tel.test(value)); 
		   }, "不能使用非法字符");

	$.validator.addMethod("isBlank", function(value, element) { 
		 return $.trim(value) != '';
		   }, "不能为空");
	
	$.validator.addMethod("isPhone", function(value, element) { 
		   var length = value.length; 
		   var mobile =/(^\d{3,4}-?\d{3,4}-?\d{3,4}$)|(^1[34578]{1}[0-9]{9}$)/;
		   return this.optional(element) || (mobile.test(value)); 
		 }, "请正确填写您的手机号码"); 

	/*验证表格*/
	$("#form").validate({
        event:"blur",
   	    rules: {
		    agentNo: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    agentName: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    bankCode: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    bankName: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    bankCard: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    idCard: {
	   	        required: true,
	   	        maxlength: 18,
	   	        isIllegalString:true,
	   	        isBlank:true
	   	        //isLegalCard:true
	   	      },
		    name: {
	   	        required: true,
	   	        maxlength: 25,
	   	        isIllegalString:true,
	   	        isBlank:true
	   	      },
		    mobile: {
	   	        required: true,
	   	        maxlength: 13,
	   	        isPhone:true,
	   	        isIllegalString:true,
	   	        isBlank:true
	   	      },
		    accountType: {
	   	        required: true,
	   	      }
   	    },
   	    messages: {
		    agentNo: {
	   	        required: "请输入【代理商编号】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    agentName: {
	   	        required: "请输入【代理商名称】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    bankCode: {
	   	        required: "请输入【银行开户行代码】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    bankName: {
	   	        required: "请输入【开户行银行名称（支行）】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    bankCard: {
	   	        required: "请输入【银行卡号码】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    idCard: {
	   	        required: "请输入【身份证号码】",
	   	        maxlength: "长度不能超过18"
	   	      },
		    name: {
	   	        required: "请输入【姓名】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    mobile: {
	   	        required: "请输入【手机号码】",
	   	        maxlength: "长度不能超过13"
	   	      },
	   	   accountType: {
	   	        required: "请选择【是否对公对私】",
	   	      }
		},
   	    submitHandler:function(form){
   			var flag = 0;
   			var errMsg = '';
   			var card = $("#idCard").val();
   			
   			if(selectUtil.isNullAgentNo()){
   				return ;
   			}
   			// 校验长度，类型
   			if (isCardNo(card) == false ||checkProvince(card) == false||checkBirthday(card) == false||checkParity(card) == false) {
   				flag++;
   				errMsg+="您输入的身份证号码不正确，请重新输入";
   			}
   			if(checkIsSame()!=true){
   				flag++;
   				errMsg+="该代理商已存在账户，不能重复添加";
   			}
   			if(flag == 0){
   				$("#save").attr("disabled","disabled");
   				var baseUrl = $("#baseUrl").text().trim();
   				var datajson = $('#form').serialize();
   				$.ajax({
   					url:baseUrl+"/adminManage/agentbankcard/save",
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