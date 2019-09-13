var selectUtil = {
	initAgent:function(){
		$("#cancle").click(function(){
			$("#agentNo").selectpicker('refresh');
		});
		
		$('#agentNo').selectpicker({
	    	noneSelectedText:"  --  请选择  --  ",
			noneResultsText:"查无数据！ {0}",
			style: 'btn-select'
	    }).init(function () {
			$("#agentNo").next().find("input").on('input', function (e) {
				$("#agentNo").empty();
				$("#agentNo").append("<option value=''>  --  请选择  --  </option>"); 
				var agentName = $("#agentNo").next().find("input").val().trim();
				var baseUrl = $("#baseUrl").text().trim();
				
				var datajson = {"agentName":agentName};
				if(!!$("#channelNo").val()){
					datajson = {"agentName":agentName,"channelNo":$("#channelNo").val().trim()};
				}
				
				if(agentName != ''){
					$.ajax({
						url:baseUrl + "/adminManage/agentbase/list",
						data:datajson,
						async: false,
						type:"POST",
						success:function(data)
						{
							var data = JSON.parse(data);
							var objList = data.objList;
							for(var i in objList){
								var option = "<option value='" + objList[i].agentNo + "'>" + objList[i].agentName + "</option>"
								$("#agentNo").append(option); 
							}
						}
					});
					
				}
		        $("#agentNo").selectpicker('refresh');
			});					
		});
		if(!!$(".agentNo").val()){
			$('#agentNo').selectpicker('val', $(".agentNo").val());
		}	
    },
    isNullAgentNo:function(){
    	var agentNo = $("#agentNo").val();
		if(agentNo == ""){
			$("#agentNo").parent().parent().append('<label class="control-label error">请选择【代理商名称】</label>')
			return true;
		}
		return false;
    },
};