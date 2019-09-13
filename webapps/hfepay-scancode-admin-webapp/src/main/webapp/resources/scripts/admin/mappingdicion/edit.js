$(function(){
	Metronic.init(); // 初始化框架核心组件
	Layout.init(); // 初始化网格
	QuickSidebar.init(); // 初始化快捷操作栏
	Demo.init(); // 初始化项目
	
	$("#cancle").click(function(){
		history.go(-1);
	});
	
	/*验证表格*/
	$("#form").validate({
        event:"blur",
   	    rules: {
   		    name: {
	   	        required: true,
	   	        maxlength: 30
	   	      },
	   	    keyNo: {
	   	        required: true,
	   	        maxlength: 30
	   	      },
	   	    paraName: {
	   	        required: true,
	   	        maxlength: 30
	   	      },
	   	    paraVal: {
	   	        required: true,
	   	        maxlength: 30
	   	      }
   	    },
   	    messages: {
   	    	name: {
	   	        required: "请输入【名称】",
	   	        maxlength: "长度不能超过30"
	   	      },
	   	    keyNo: {
	   	        required: "请输入【KEY】",
	   	        maxlength: "长度不能超过30"
	   	      },
	   	    paraName: {
	   	        required: "请输入【参数名称】",
	   	        maxlength: "长度不能超过30"
	   	      },
	   	    paraVal: {
	   	        required: "请输入【参数值】",
	   	        maxlength: "长度不能超过30"
	   	      },
		    phone: {
	   	        required: "请输入【电话】",
	   	        maxlength: "长度不能超过30"
	   	      }
		},
   	    submitHandler:function(form){$("#save").attr("disabled","disabled");
   			var flag = 0;
   			var errMsg = '';
   			var text = $(".form-horizontal input[val='hidden']");
   			for(var i = 0; i < text.size();i++){
   				var $this = $(text[i]);
   				var val = $this.val();
   				if(val == null || val == ''){
   					flag ++ ;
   					errMsg += $this.attr("name") + "不可为空!";
   				}
   			}
   			
   			if(flag == 0){
   				var baseUrl = $("#baseUrl").text().trim();
   				var datajson = $('#form').serialize();
   				$.ajax({
   					url:baseUrl+"/adminManage/mappingdicion/save",
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
		
});
