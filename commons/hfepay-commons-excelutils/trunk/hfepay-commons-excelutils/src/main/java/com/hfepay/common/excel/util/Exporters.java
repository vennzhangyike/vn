package com.hfepay.common.excel.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.hfepay.common.excel.ExcelExporter;
import com.hfepay.common.excel.jxls.DynamicColumnExporter;
import com.hfepay.common.excel.jxls.JXLSExcelExporter;
import com.hfepay.commons.base.io.Streams;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.base.lang.Sys;
import com.hfepay.commons.base.lang.Throwables;

/**
 * Excel导出工具
 * @author sam
 *
 */
public abstract class Exporters { 
	
	public static final String TEMPLATE_BASE = "template/";
	
	/**
	 * 创建一个基于模板的EXCEL导出器
	 * @param template 模板路径
	 * @param exportPath 导出路径，可以不设置文件名后缀和文件目录，会自动加上，文件目录则使用系统临时目录
	 * @return
	 */
	public static ExcelExporter createJXLSExporter(String template,String exportPath) {
		template = findTemplatePath(template); 
		ExcelExporter exporter = new JXLSExcelExporter(template,exportPath);		
		return exporter;
	}
	
	/**
	 * 创建一个基于模板的EXCEL导出器，不指定导出路径，会以系统临时目录+模板名做导出路径
	 * 只要适用于有些模块要直接导出到客户端的情况
	 * @param template 模板路径
	 * @return
	 */
	public static ExcelExporter createJXLSExporter(String template) {
		String exportPath = getDefaultTempFilePath(null);
		template = findTemplatePath(template);		
		ExcelExporter exporter = new JXLSExcelExporter(template,exportPath);		
		return exporter;
	}
	
	private static String getDefaultTempFilePath(String fileName) {
		File file = Sys.getJavaIoTmpDir();
		fileName  = addSuffixName(Strings.defaultString(fileName, UUID.randomUUID().toString()));
		return new File(file,fileName).getAbsolutePath();
	}
	
	/**
	 * 查找某个导出模板的路径 
	 * @param template
	 * @return
	 */
	public static String findTemplatePath(String template) {
		//只要传进来的template包含有'/','\'，就认为是一个已经完整的路径不再处理
		if (Strings.containsAny(template, '/','\\') ) {
			return template;
		}
		return findResource(TEMPLATE_BASE+addSuffixName(template));
		
	}
	
	/**
	 * 从ClassPath中查找某个资源
	 * @param resource 资源路径
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String findResource(String resource) {
		URL url = Thread.currentThread().getContextClassLoader().getResource(resource);
		return URLDecoder.decode(url.getFile());
	}
	
	/**
	 * 根据一个路径生成一个File对象,
	 * @param path
	 * @return
	 */
	public static File findFile(String path) {
		String dir = findResource("tempdir/");
		File tempdir = new File(dir);
		if (!tempdir.exists()) {
			try {
				tempdir.createNewFile();
			} catch (IOException e) { 
				e.printStackTrace();
			}
		}
		
		String fileName = addSuffixName(path);
		if (Strings.isNotBlank(dir)) {
			dir = dir.replaceAll("\\\\", "/");
		}
		if (Strings.isNotBlank(path)) {
			path = path.replaceAll("\\\\", "/");
		}
		if (Strings.containsAny(path, "/")) {
			dir = Strings.substringBeforeLast(path, "/");
			fileName = Strings.substringAfterLast(path, "/");
		}
		fileName = addSuffixName(fileName);		
		File exportFile = new File(dir,fileName);
		return exportFile;
	}
	
	/**
	 * 添加后缀名
	 * @param fileName
	 * @return
	 */
	public static String addSuffixName(String fileName) {
		if (!Strings.defaultString(fileName).matches(".*\\.xls")) {
			fileName += ".xls";
		}
		return fileName;
	}
	
	/**
	 * 创建一个动态列导出器
	 * @return
	 */
	public static DynamicColumnExporter createDynamicExporter() {
		return DynamicColumnExporter.create();
	}

	
	/**
	 * 根据指定的jxls模板进行导出
	 * @param templatePath 模板路径,默认会classpath:/template/目录下查找对应的模板
	 * @param exportTempPath 导出的路径，不指标定交使用系统的临时目录
	 * @param exportName 导出的文件名称，不指定的话会根据UUID生成一个临时文件名称
	 * @param dataSources 传给模板的数据源
	 * @param params 传给模板的非数据源参数
	 * @return
	 */ 
	@SuppressWarnings("rawtypes")
	public static File export(String templatePath,
								   String exportTempPath,
								   String exportName,
								   Map dataSources,  
								   Map params) {
		File outPath = Sys.getJavaIoTmpDir();
		if (Strings.isNotBlank(exportTempPath) && new File(exportTempPath).exists())
			outPath = new File(exportTempPath);
		exportName = Strings.defaultString(exportName, UUID.randomUUID().toString());	
		File exportFile = new File(outPath,addSuffixName(exportName));
		ExcelExporter exporter = createJXLSExporter(templatePath,exportFile.getAbsolutePath());
		try {
			exporter.addDataSources(dataSources)
					.addParams(params)
					.export();
			return exportFile;
		} catch (IOException e) {
			throw Throwables.wrapThrow(e);
		}
		
	}
	
	/**
	 * 根据模板进行导出并交导出的excel文件下载到响应流中
	 * @param templatePath 模板路径,默认会classpath:/template/目录下查找对应的模板
	 * @param downloadName 下载的文件名称，无须进行编码，此处默认会进行编码处理
	 * @param dataSources 要传给模板的数据列表
	 * @param params 传给模板的参数非数据列表数据
	 * @param response 响应流
	 * @param closeable 下载完毕后是否关闭响应流
	 */
	@SuppressWarnings("rawtypes")
	public static void exportWithDownload(String templatePath,
							  String downloadName,
							  Map dataSources,
							  Map params,
							  HttpServletResponse response,boolean closeable) {
		File downloadFile = export(templatePath,null,null,dataSources,params);
		try {
			String encodedfileName = new String(downloadName.getBytes(), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
			OutputStream out = response.getOutputStream();
			Streams.write(out,Streams.fileIn(downloadFile));
			if (closeable)
				Streams.safeClose(out);
		} catch (IOException e) {
			throw Throwables.wrapThrow(e);
		}
		
	}
	
	
	
	
	
}





