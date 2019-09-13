
$(function(){
	
	Metronic.init(); // 初始化框架核心组件
	Layout.init(); // 初始化网格
	QuickSidebar.init(); // 初始化快捷操作栏
	Demo.init(); // 初始化项目
	var array = [];
	
	$("#cancle").click(function(){
		history.go(-1);
	});
	
	//type 0:折扣 1：满减 2：随机
	var type =  $("#type").val();
	
	if(type == 0){
		$(".form-horizontal").delegate(".from","blur",function(){
			$(".from").each(function(i,item){
				var val=$.trim($(item).val());
				if(val !='' && !/^0.\d{2}/.test(val)){
					flag=true;
					bootbox.alert("活动优惠必须为小于1的两位小数");
					return false;
				}
			})
		})
		$(".odds").each(function(i,item){
			$(".odds").attr("readonly",true);
		})
	}
	if(type == 1){
		$(".form-horizontal").delegate(".from","blur",function(){
			$(".from").each(function(i,item){
				var val=$.trim($(item).val());
				if(val !='' && !/^1\d*$/.test(val)){
					flag=true;
					bootbox.alert("活动优惠只能输入数字且第一位不能为0");
					return false;
				}
			})
		})
		$(".odds").each(function(i,item){
			$(".odds").attr("readonly",true);
		})
	}
	if(type == 2){
		$(".form-horizontal").delegate(".from","blur",function(){
			$(".from").each(function(i,item){
				var val=$.trim($(item).val());
				if(val !='' && !/^\d*[,]\d*$/.test(val)){
					flag=true;
					bootbox.alert("活动优惠输入有误，例如：1,10 ; 10,20(逗号为英文逗号)");
					return false;
				}
			})
		})
		$(".odds").each(function(i,item){
			$(".odds").attr("readonly",false);
		})
		
		$(".form-horizontal").delegate(".odds","blur",function(){
			$(".odds").each(function(i,item){
				var val=$.trim($(item).val());
				if(val !='' && !/^0.\d{2}/.test(val)){
					flag=true;
					bootbox.alert("概率必须为小于1的两位小数");
					return false;
				}
			})
		})
	}
	
	$(".form-horizontal").delegate(".to","blur",function(){
		$(".to").each(function(i,item){
			var val=$.trim($(item).val());
			if(val !='' && !/^[1-9]{1}\d*$/.test(val)){
				flag=true;
				bootbox.alert("活动条件只能输入数字且第一位不能为0");
				return false;
			}
		})
	})
	
	
	$(".form-horizontal").delegate(".add","click",function(){
		var fromArray=$(".from");
		var toArray=$(".to");
		var flag = false;
		$(".from").each(function(i,item){
			var val=$.trim($(item).val());
			if(!val||val==''){
				flag=true;
				bootbox.alert("活动优惠不能为空");
				return false;
			}
		})
		if(flag){
			return;
		}
		$(".to").each(function(i,item){
			var val=$.trim($(item).val());
			if(!val||val==""){
				flag=true;
				bootbox.alert("活动条件不能为空");
				return false;
			}
		})
		if(flag){
			return;
		}
		
		if(type == 2){
			$(".odds").each(function(i,item){
				var val=$.trim($(item).val());
				if(!val||val==""){
					flag=true;
					bootbox.alert("活动概率不能为空");
					return false;
				}
			})
		}
		
		if(!flag){
			array = [];
			var froms=$('#popPage .form-group');
			$.each(froms,function(i,item){
				var id = $(item).find('.id').val();//从
				var from = $(item).find('.from').val();//从
				var to = $(item).find('.to').val();//到
				var odds = $(item).find('.odds').val();//概率
				if(from &&from!=''){
					array.push({"from":from,"to":to,"odds":odds,"id":id});
				}
			}); 
			var datajson = "";
			for(var i = 0; i< array.length;i++){
				datajson += "&array="+JSON.stringify(array[i]);
			}
			$.ajax({
				url:"discountSave",
				data:datajson, 
				dataType: "json",  
				type:"POST",
				success:function(msg){
					bootbox.alert(msg.values,function(){
						if(msg.executeStatus == '0'){
							location.href=$("#baseUrl").text()+"/adminManage/"+msg.url;
						}
					});
				}
			});
		}
	});
});