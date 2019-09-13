package com.hfepay.scancode.channel.commons;

public class FileUploadConfig {

	private String path;//图片上传路径

	private String filePath;//项目路径--图片访问路径
	
	private String qrcodePath;//二维码扫码地址
	
	private String viralPath;//二维码访问路径
	
	private String qrPath;//二维码存放目录

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getQrcodePath() {
		return qrcodePath;
	}

	public void setQrcodePath(String qrcodePath) {
		this.qrcodePath = qrcodePath;
	}

	public String getViralPath() {
		return viralPath;
	}

	public void setViralPath(String viralPath) {
		this.viralPath = viralPath;
	}

	public String getQrPath() {
		return qrPath;
	}

	public void setQrPath(String qrPath) {
		this.qrPath = qrPath;
	}

}
