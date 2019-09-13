(function($){
	bootbox.setLocale("zh_CN");
    /**
     * ajax封装
     * url 发送请求的地址
     * data 发送到服务器的数据，数组存储，如：{"date": new Date().getTime(), "state": 1}
     * async 默认值: true。默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。
     *       注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
     * type 请求方式("POST" 或 "GET")， 默认为 "GET"
     * dataType 预期服务器返回的数据类型，常用的如：xml、html、json、text
     * successfn 成功回调函数
     * errorfn 失败回调函数
     */
    $.sendReq=function(funcParam) {
    	var url=funcParam.url, 
    	data=funcParam.data, 
    	type=funcParam.type, 
    	successfn=funcParam.success, 
    	async=funcParam.async, 
    	dataType=funcParam.dataType, 
    	errorfn=funcParam.error;
    	
        async = (async==null || async=="" || typeof(async)=="undefined")? "true" : async;
        type = (type==null || type=="" || typeof(type)=="undefined")? "post" : type;
        dataTypeJson = (dataType==null || dataType=="" || typeof(dataType)=="undefined")? {} : {dataType: dataType};
        data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
        successfn = (successfn==null || successfn=="" || typeof(successfn)=="undefined")||typeof(successfn)!="function"? $.noop : successfn;
        errorfn = (errorfn==null || errorfn=="" || typeof(errorfn)=="undefined")||typeof(errorfn)!="function"? $.noop : errorfn;
        
        
        var params = $.extend({
        	type: type,
            async: async,
            data: data,
            url: url,
            success: function(d){
			           	 if(d.isDenied){//没有权限
			   				  bootbox.alert(d.message); 
			   			   	  return;
			   			   }
			           	 if(d.isNotLogin){//没有登录
			 				   bootbox.alert(d.message,function(){
			 					   window.location.href=getRootPath()+'/index/login';
			 				   });
			 				   return;
			 			   	}
			               successfn(d);
		           },
           error: function(e){
		           	errorfn(e);
		           }
           }, dataTypeJson);
        
        return $.ajax(params);
    };
    
    /**
     * ajax封装
     * url 发送请求的地址
     * data 发送到服务器的数据，数组存储，如：{"date": new Date().getTime(), "state": 1}
     * successfn 成功回调函数
     */
    $.axs=function(url, data, successfn) {
        data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
        $.ajax({
            type: "post",
            data: data,
            url: url,
            dataType: "json",
            success: function(d){
                successfn(d);
            }
        });
    };
    
    /**
     * ajax封装
     * url 发送请求的地址
     * data 发送到服务器的数据，数组存储，如：{"date": new Date().getTime(), "state": 1}
     * dataType 预期服务器返回的数据类型，常用的如：xml、html、json、text
     * successfn 成功回调函数
     * errorfn 失败回调函数
     */
    $.axse=function(url, data, successfn, errorfn) {
        data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
        $.ajax({
            type: "post",
            data: data,
            url: url,
            dataType: "json",
            success: function(d){
                successfn(d);
            },
            error: function(e){
                errorfn(e);
            }
        });
    };
    
    $.getRootPath = getRootPath;
    function getRootPath(){  
        //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp  
        var curWwwPath=window.document.location.href;  
        //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp  
        var pathName=window.document.location.pathname;  
        var pos=curWwwPath.indexOf(pathName);  
        //获取主机地址，如： http://localhost:8083  
        var localhostPaht=curWwwPath.substring(0,pos);  
        //获取带"/"的项目名，如：/uimcardprj  
        var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);  
        return (localhostPaht+projectName);  
    }
})(jQuery);