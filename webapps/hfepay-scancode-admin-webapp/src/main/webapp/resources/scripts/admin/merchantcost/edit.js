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
		$(".form-horizontal input").val('');
		$(".form-horizontal select").val('');
	});
	
	$("#save").click(function(){
		var flag = 0;
		var text = $(".form-horizontal input");
		for(var i = 0; i < text.size()-1;i++){
			var $this = $(text[i]);
			if($this.is(":visible") == true){
				var val = $this.val();
				if(val == null || val == ''){
					flag ++ ;
				}
			}
		}
		text = $(".form-horizontal select");
		for(var i = 0; i < text.size();i++){
			var $this = $(text[i]);
			var val = $this.val();
			if(val == null || val == ''){
				flag ++ ;
			}
		}
		if(flag == 0){
			$("#form").submit();
		}else{
			bootbox.alert("请检查内容再次填写！",function(){
				  //window.location.reload();
			});
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
