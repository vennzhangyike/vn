function generateQRCode(rendermethod, picwidth, picheight, url) {
        $("#qrcode").qrcode({ 
            render: rendermethod, // 渲染方式有table方式（IE兼容）和canvas方式
            width: picwidth, //宽度 
            height:picheight, //高度 
            text: url, //内容 
            typeNumber:-1,//计算模式
            correctLevel:2,//二维码纠错级别
            background:"#ffffff",//背景颜色
            foreground:"#000000"  //二维码颜色
        });
    }
    function init(url) {//根据url画出二维码
        generateQRCode("canvas",200, 200, url);
        
        //获取网页中的canvas对象 
        var mycanvas1=document.getElementsByTagName('canvas')[0]; 
        //将转换后的img标签插入到html中 
        var img=convertCanvasToImage(mycanvas1); 
        $('canvas').hide()
        $('#qrcode').append(img);//imagQrDiv表示你要插入的容器id
    }
    
  //从 canvas 提取图片 image 
    function convertCanvasToImage(canvas) { 
    //新Image对象，可以理解为DOM 
    var image = new Image(); 
    // canvas.toDataURL 返回的是一串Base64编码的URL，当然,浏览器自己肯定支持 
    // 指定格式 PNG 
    image.src = canvas.toDataURL("image/png"); 
    return image; 
    } 
   
    
    
    //中文编码格式转换
    function utf16to8(str) {
        var out, i, len, c;
        out = "";
        len = str.length;
        for (i = 0; i < len; i++) {
            c = str.charCodeAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                out += str.charAt(i);
            } else if (c > 0x07FF) {
                out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
                out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            } else {
                out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            }
        }
        return out;
    }