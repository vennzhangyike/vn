$(function(){
	$("#form").find("#return").click(function(){
		history.go(-1);
	});
	dataOpr();
});

function toPage(pageNumber){
	var baseUrl = $("#baseUrl").text().trim();
	var datajson = $('#form').serialize();
	var menuUrl=window.location.href.replace(window.location.search,'');//替换掉多余的参数
	location.href= menuUrl + "?pageNo="+pageNumber;
}

