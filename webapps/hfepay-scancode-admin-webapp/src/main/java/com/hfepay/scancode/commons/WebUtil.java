package com.hfepay.scancode.commons;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

public class WebUtil {
    /**
     * 请求是否是ajax
     * @param request
     * @return
     */
	public static boolean isAjax(HttpServletRequest request){
        return  (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())   ) ;
    }
	
    /**
     * 请求是否是ajax
     * @param request
     * @return
     * @throws IOException 
     */
	public static void printJson(ServletResponse response,JSONObject json) throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json"); 
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }
	
	/**
     * 下载文件名编码
     * @param request
     * @return
     * @throws IOException 
     */
	public static String rechangeFileName(HttpServletRequest request,String filename) throws IOException{
		try {
		      //判断是否是IE11
		      Boolean flag= request.getHeader("User-Agent").indexOf("like Gecko")>0;
		      //IE11 User-Agent字符串:Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko
		      //IE6~IE10版本的User-Agent字符串:Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.0; Trident/6.0)
		       
		       if (request.getHeader("User-Agent").toLowerCase().indexOf("msie") > 0 || flag){
		    	   filename = URLEncoder.encode(filename, "UTF-8");//IE浏览器
		    	   if (filename.length() > 150) {  
		               String guessCharset = request.getCharacterEncoding(); /*根据request的locale 得出可能的编码，中文操作系统通常是gb2312*/  
		               filename = new String(filename.getBytes(guessCharset), "ISO8859-1");   
		           }
		       }else {
		        //先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,
		        //这个文件名称用于浏览器的下载框中自动显示的文件名
		        filename = new String(filename.replaceAll(" ", "").getBytes("UTF-8"), "ISO8859-1");
		        //firefox浏览器User-Agent字符串: 
		        //Mozilla/5.0 (Windows NT 6.1; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0
		       }
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		
		return filename;
    }
}
