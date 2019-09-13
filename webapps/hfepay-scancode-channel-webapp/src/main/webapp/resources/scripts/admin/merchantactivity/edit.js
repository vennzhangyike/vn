$(function(){
	Metronic.init(); // 初始化框架核心组件
	Layout.init(); // 初始化网格
	QuickSidebar.init(); // 初始化快捷操作栏
	Demo.init(); // 初始化项目
	
	var array = [];
	//activityType 弹出框
	$("#activityType").change(function(){
		var val=$(this).val();
		if(val!=''){
				$.ajax({
					   type: "POST",
					   url: "detail/add/"+val,
					   success: function(msg){
						   var btn = {
							success:{   
						       label: "确认",
						       className: "btn-default",
						       callback: function() {
						    	   toSubmit();
						       }
						     },
						     Cancel:{   
							       label: "关闭",
							       className: "btn-success",
							       callback: function() {
							       }
							     }
						   };
						   bootbox.dialog({
							   className: "my-modal",
							   message: msg,
							   title: "活动规则",
							   onEscape: function() {},
							   className: "green-haze",
							   buttons: btn
							 });
					   }
				});
		}
	});
	
	//参数回传
	function toSubmit(){
		array = [];
		var froms=$('#popPage .row');
		var type = $("#activityType").val();
		$.each(froms,function(i,item){
			var from = $(item).find('.from').val();//活动优惠
			var to = $(item).find('.to').val();//活动条件
			var odds = $(item).find('.odds').val();//活动概率
			if(type == 2){
				if(from != '' && to != '' && odds != ''){
					array.push({"from":from,"to":to,"odds":odds});
				}
			}else{
				if(from != '' && to != ''){
					array.push({"from":from,"to":to,"odds":odds});
				}
			}
			
		}); 
	}
	
	$('.date-picker').datetimepicker({
		format: 'yyyy-mm-dd hh:ii',
        weekStart: 0,
        autoclose: true,
        todayHighlight: true,
        defaultDate : new Date(),
        keyboardNavigation: true,
        todayBtn: 'linked',
        clearBtn: 'linked',
        language: 'zh-CN'
	});
	
	$("#cancle").click(function(){
		history.go(-1);
	});
	
	/*验证表格*/
	$("#form").validate({
        event:"blur",
   	    rules: {
		    activityId: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    activityType: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    activityContent: {
	   	        required: true,
	   	        maxlength: 25
	   	      }
		   
   	    },
   	    messages: {
		   
		    activityId: {
	   	        required: "请输入【活动ID】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    activityType: {
	   	        required: "请输入【活动类型】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    activityContent: {
	   	        required: "请输入【活动描述】",
	   	        maxlength: "长度不能超过25"
	   	      }
		},
   	    submitHandler:function(form){
   			var flag = 0;
   			var errMsg = '';
   			if(array.length < 1){
				bootbox.alert("活动规则不能为空，请选择活动类型来填写活动规则");
				return;
			}
   			if(flag == 0){
   				var baseUrl = $("#baseUrl").text().trim();
   				var datajson = $('#form').serialize();
   				for(var i = 0; i< array.length;i++){
   					datajson += "&array="+JSON.stringify(array[i]);
   					 
   				}
   				$.ajax({
   					url:baseUrl+"/adminManage/merchantactivity/save",
   					data:datajson, 
   					dataType: "json",  
   					type:"POST",
   					success:function(msg)
   					{
   						//msg = $.parseJSON(msg);
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
