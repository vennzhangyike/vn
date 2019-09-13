package com.hfepay.scancode.ueditor.upload;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hfepay.scancode.ueditor.UploadContants;
import com.hfepay.scancode.ueditor.define.State;

public class Uploader {
	private HttpServletRequest request = null;
	private Map<String, Object> conf = null;
	

	public Uploader(){}
	
	public Uploader(HttpServletRequest request, Map<String, Object> conf) {
		this.request = request;
		this.conf = conf;
	}

	public final State doExec() {
		String filedName = (String) this.conf.get("fieldName");
		State state = null;
		this.conf.put("localRootPath", UploadContants.getConfValueByKey(UploadContants.LOCAL_ROOT_PATH));
		if ("true".equals(this.conf.get("isBase64"))) {
			state = Base64Uploader.save(this.request.getParameter(filedName),
					this.conf);
		} else {
			state = BinaryUploader.save(this.request, this.conf);
		}

		return state;
	}
}
