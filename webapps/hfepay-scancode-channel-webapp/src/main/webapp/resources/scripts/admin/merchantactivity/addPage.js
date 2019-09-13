/**
 * 
 */
$(function(){
	
	
	//type 0:折扣 1：满减 2：随机
	var type =  $("#type").val();
	
	$(".form-horizontal").delegate(".minus","click",function(){
		if($(".minus").length==1){
			bootbox.alert("最后一个节点不能删除");
		}else{
			$(this).parents('.row').remove();
		}
	});
	
	$(".form-horizontal").delegate(".to","blur",function(){
		$(".to").each(function(i,item){
			var val=$.trim($(item).val());
			if(val !='' && !/^[1-9]{1}\d*$/.test(val)){
				flag=true;
				bootbox.alert("活动条件只能输入数字且第一位不能为0");
				$(item).val("");
				return false;
			}
		})
	})
	
	if(type == 0){
		$(".form-horizontal").delegate(".from","blur",function(){
			$(".from").each(function(i,item){
				var val=$.trim($(item).val());
				if(val !='' && !/^0.\d{2}/.test(val)){
					flag=true;
					bootbox.alert("活动优惠必须为小于1的两位小数");
					$(item).val("");
					return false;
				}
			})
		})
//		$(".odds").each(function(i,item){
//			$(".chance").hide();
//			$(".odds").hide();
//		})
		$(".chance").parent('div').parent().hide();
	}
	if(type == 1){
		$(".form-horizontal").delegate(".from","blur",function(){
			$(".from").each(function(i,item){
				var val=$.trim($(item).val());
				if(val !='' && !/^1\d*$/.test(val)){
					flag=true;
					bootbox.alert("活动优惠只能输入数字且第一位不能为0");
					$(item).val("");
					return false;
				}
			})
		})
//		$(".odds").each(function(i,item){
//			$(".chance").hide();
//			$(".odds").hide();
//		})
		$(".chance").parent('div').parent().hide();
	}
	if(type == 2){
		$(".form-horizontal").delegate(".from","blur",function(){
			$(".from").each(function(i,item){
				var val=$.trim($(item).val());
				if(val !='' && !/^\d*[,]\d*$/.test(val)){
					flag=true;
					bootbox.alert("活动优惠输入有误，例如：1,10 ; 10,20(逗号为英文逗号)");
					$(item).val("");
					return false;
				}
			})
		})
//		$(".odds").each(function(i,item){
//			$(".chance").show();
//			$(".odds").show();
//		})
		$(".chance").parent('div').parent().show();
		
		$(".form-horizontal").delegate(".odds","blur",function(){
			$(".odds").each(function(i,item){
				var val=$.trim($(item).val());
				if(val !='' && !/^(?:0\.\d+|[01](?:\.0)?)$/.test(val)){
					flag=true;
					bootbox.alert("活动概率必须为小于等于1的两位小数");
					return false;
				}
			})
		})
	}
	
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
			var row = "<div class='row'>"+$("#row").html()+'</div>';
			$(this).parents(".row").after(row);
		}
	});
});