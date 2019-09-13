package com.hfepay.scancode.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.scancode.commons.FileUploadConfig;
import com.hfepay.scancode.service.commons.ScanCodeConstants;

import net.sf.json.JSONSerializer;


@Controller()
@RequestMapping("/user/file")
public class FileUploadController extends BaseController{

	Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@Autowired
	private FileUploadConfig fileUploadConfig;
	
	@RequestMapping("/upload")
	public void upload(@RequestParam(value="file",required=false) MultipartFile file,String typePath,HttpServletRequest request,HttpServletResponse response) throws IOException{
		logger.debug("----------------------------------");
		//判断文件夹
		File dir = new File(fileUploadConfig.getPath()+ScanCodeConstants.SPT+typePath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		logger.debug("----------------------------------");
		PrintWriter pw = response.getWriter();
		if(file.getSize() > 20*1024*1024){
			//图片过大
			logger.info("*****上传图片fail,图片过大*****");
			Map<Object, Object> map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"图片过大");
			pw.write(JSONSerializer.toJSON(map).toString());
			pw.close();
			return;
		}
		//获取文件名
		String fileName = file.getOriginalFilename();
		fileName = Strings.getUUID()+fileName.substring(fileName.lastIndexOf("."),fileName.length());
		File iconFile = null;
		iconFile = new File(dir.getPath(),fileName);
		//保存
		try {
			file.transferTo(iconFile);
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("*****上传图片fail,保存图片失败,检查图片服务器是否挂了*****"+e);
			Map<Object, Object> map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"上传失败");
			pw.write(JSONSerializer.toJSON(map).toString());
			pw.close();
			return;
		}
		String imgUrl = /*fileUploadConfig.getFilePath()+*/ScanCodeConstants.FILE_UPLOAD_ROOT_PATH+ScanCodeConstants.SPT+typePath+"/"+fileName;
		Map<Object, Object> map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,imgUrl);
		pw.write(JSONSerializer.toJSON(map).toString());
		pw.close();
		return;
	}
	
}
