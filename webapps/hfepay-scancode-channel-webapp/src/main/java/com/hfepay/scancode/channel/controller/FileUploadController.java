package com.hfepay.scancode.channel.controller;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.scancode.channel.commons.FileUploadConfig;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/file")
public class FileUploadController extends BaseController{

	Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@Autowired
	private FileUploadConfig fileUploadConfig;
	
	private static String viralPath = "data";
	
	@RequestMapping("/upload")
	@ResponseBody
	public JSON upload(@RequestParam(value="file",required=false) MultipartFile file,String typePath,HttpServletRequest request){
		
		String path = fileUploadConfig.getPath();
		//判断文件夹
		File dir = new File(path+File.separator+typePath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		int num = 0;
		//获取文件名
		String fileName = file.getOriginalFilename();
		String type = fileName.substring(fileName.lastIndexOf("."),fileName.length());
		if(fileName.indexOf("rar") > 0 || fileName.indexOf("zip") > 0){
			num = 100;
		}else{
			num = 2;
		}
		if(file.getSize() > num * 1024 * 1024){
			//文件过大
			logger.info("******************  【上传文件】  fail  *************");
			Map<Object, Object> map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"文件过大");
			return JSONSerializer.toJSON(map); 
		}
		
		fileName = Strings.getUUID().replace("-", "") + type;
		File iconFile = null;
		iconFile = new File(dir.getPath(),fileName);
		//保存
		try {
			file.transferTo(iconFile);
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("******************  【上传文件】 fail  *************");
			Map<Object, Object> map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"上传失败");
			return JSONSerializer.toJSON(map); 
		}
		logger.info("******************  【上传文件】  success  *************");
		String rootpath = path.substring(path.lastIndexOf("/"));
		String imgUrl = fileUploadConfig.getFilePath() + "/" + viralPath + rootpath + "/" + typePath + "/" + fileName;
		Map<Object, Object> map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,imgUrl);
		return JSONSerializer.toJSON(map); 
	}
	
}
