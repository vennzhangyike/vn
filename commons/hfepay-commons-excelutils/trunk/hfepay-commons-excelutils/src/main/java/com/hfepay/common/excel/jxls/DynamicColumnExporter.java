package com.hfepay.common.excel.jxls;
 
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.hfepay.common.excel.ExcelExporter;
import com.hfepay.common.excel.util.Exporters;
import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.io.Streams;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.base.lang.Sys;
import com.hfepay.commons.base.lang.Throwables;

/**
 * 简单动态列EXCEL导出器，只针对简单的EXCEL数据导出，即导出的Excel只需要标题，列表这些，如果比较复杂的话应该
 * 使用自定义模板的方式来实现
 * @author sam
 *
 */
public class DynamicColumnExporter {
	/**
	 * 默认的动态列导出模板
	 */
	private static final String DEFAULT_TEMPLATE = "dynamicTemplate.xls";	
	
	/**
	 * 动态列导出模板所在位置，一般情况下不须要修改此
	 */
	private String  template = getFileOfClassPath(DEFAULT_TEMPLATE);
	
	/**
	 * 导出Excel的标题
	 */
	private String title;
	
	/**
	 * 列头定义，即表格的头部文本信息
	 */
	private List<String> columns = Lists.newList();
	
	/**
	 * 导出的记录中的记录相应的属性集，导出器会根据这个顺序和名称取记录的属性作输出
	 */
	private List<String> properties = Lists.newList();
	
	/**
	 * 导出的记录集
	 */
	@SuppressWarnings("rawtypes")
	private List records = new ArrayList();
	
	/**
	 * 设置与Dict的映射关系
	 */
	private Map<String,String> dictMapping = Maps.newMap();
	
	/**
	 * 依赖的导出器
	 */
	private ExcelExporter exporter = new JXLSExcelExporter(); 	
	
	DynamicColumnExporter() { }
	

	
	/**
	 * 设置导出标题
	 * @param title
	 * @return
	 */
	public DynamicColumnExporter title(String title) {
		this.title = title;
		return this;
	}
	
	public DynamicColumnExporter cellMerge(Integer uniteStartRow,Integer rowSize,List<Integer> uniteCols) {
		this.exporter.cellMerge(uniteStartRow, rowSize, uniteCols);
		return this;
	}

	
	/**
	 * 设置列标题
	 * @param columns
	 * @return
	 */
	public DynamicColumnExporter columns(String...columns) {
		this.columns.addAll(Lists.of(columns));
		return this;
	}

	public DynamicColumnExporter applyDict(String property,String dictName) {
		dictMapping.put(property,dictName);
		return this;
	}

	
	/**
	 * 设置列标题
	 * @param columns
	 * @return
	 */
	public DynamicColumnExporter columns(List<String> columns) {
		this.columns.addAll(columns);
		return this;
	}
	
	/**
	 * 设置记录的属性
	 * @param props
	 * @return
	 */
	public DynamicColumnExporter properties(String...props) {
		this.properties.addAll(Lists.of(props));
		return this;
	}
	
	/**
	 * 设置记录的属性
	 * @param props
	 * @return
	 */
	public DynamicColumnExporter properties(List<String> props) {
		this.properties.addAll(props);
		return this;
	}
	
	/**
	 * 设置要导出的数据集
	 * @param records
	 * @return
	 */ 
	@SuppressWarnings("rawtypes")
	public DynamicColumnExporter record(List records) {
		this.records = records;
		return this;
	}
	
	/**
	 * 导出处理
	 * @param f 导出的目标文件
	 * @return
	 * @throws IOException 
	 */
	public void export(File f) throws IOException {
		if (!dictMapping.isEmpty()) {
			handleRecords();
		}
		if (f != null && !f.exists())
			f.createNewFile();
		exporter.setExportPath(f.getAbsolutePath());
		exporter.setReportPath(template);
		exporter.addParam("title", this.title);
		exporter.addDataSource("columns", this.columns);		
		exporter.addDataSource("properties", this.properties);
		exporter.addDataSource("records", this.records);

		exporter.export();
	}
	
	private void handleRecords() {
//		DictFactory dictFactory = SpringUtils.getBean(DictFactory.class);
//		DictService dictService = dictFactory.createDictService();
//		if (CollectionUtils.isNotEmpty(records)) {
//			for (int i = 0; i < records.size(); i++ ) {
//				Object o = records.get(i);
//				if (o != null) {
//					for (Entry<String,String> entry : dictMapping.entrySet()) {
//						if (StringUtils.contains(entry.getValue(), ".")) {
//							String dictConfigName = entry.getValue().split("[.]")[0];
//							String name = entry.getValue().split("[.]")[1];
//							Object v = BeanUtils.getAnyProperty(o, entry.getKey());
//							if (v != null) {
//								Object vv = 
//									dictService.getDictItemNameByValue(dictConfigName, name, null, v.toString());
//								BeanUtils.setProperty(o, entry.getKey(), vv);
//							}
//						}
//					}
//				}
//			}
//		}
	}



	/**
	 * 导出处理
	 * @param exportPath 导出的目标路径，如果只写文件名的话则会在应用程序指定的临时目录写文件
	 * @return
	 * @throws IOException 
	 */
	public File export(String exportPath) throws IOException {		
		File exportFile = Exporters.findFile(exportPath);
		export(exportFile);
		return exportFile;
	}	
		
	
	/**
	 * 创建一个导出处理器
	 * @return
	 */
	public static DynamicColumnExporter create(){
		return new DynamicColumnExporter();
	}
	
	@SuppressWarnings("deprecation")
	String getFileOfClassPath(String path) {
		URL url = Thread.currentThread().getContextClassLoader().getResource(path);
		return URLDecoder.decode(url.getFile());
	}

	/**
	 * 获取导出报表标题
	 * @return
	 */
	public String getTitle() {
		return title;
	}	
	
	/**
	 * 获取模板名称 
	 * @return
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * 获取记录集
	 * @return
	 */ 
	@SuppressWarnings("rawtypes")
	public List getRecords() {
		return records;
	}
	
	/**
	 * 返回内部的Excel导出处理器
	 * @return
	 */
	public ExcelExporter getInnerExporter() {
		return exporter;
	}
	
//	public static void main(String[] a) throws IOException {
//		List<Employee> emps = Lists.newList();
//		for (int i = 0; i<100; i++ ) {
//			emps.add(new Employee("Elsa"+i, 28+i, 1500+i, 0.15+i));
//		}
//		
//		DynamicColumnExporter de = DynamicColumnExporter.create();
//		de.title("员工列表")
//		  .properties("name", "age", "payment", "bonus")
//		  .columns("名称","年龄","工资","交税")
//		  .record(emps)
//		  .export("dynamicemp");
//	
//	}
	
	/**
	 * 根据指定的jxls模板进行导出
	 * @param exportTempPath 导出的路径，不指标定交使用系统的临时目录
	 * @param exportName 导出的文件名称，不指定的话会根据UUID生成一个临时文件名称
	 * @param columns 要传给模板的表头列表
	 * @param props 传给模板的参数列表数据
	 * @param records 传给模板的数据列表数据
	 * @param title 传给模板的标头信息数据
	 * @return
	 */ 
	@SuppressWarnings("rawtypes")
	public static File export(String exportTempPath,
								   String exportName,
								   String title,
								   List<String> columns,  
								   List<String> props,
								   List records) {
		DynamicColumnExporter dynamic = Exporters.createDynamicExporter();
 		dynamic.columns(columns);
 		dynamic.properties(props);
 		dynamic.title(title);
 		dynamic.record(records);
		File outPath = Sys.getJavaIoTmpDir();
		if (Strings.isNotBlank(exportTempPath) && new File(exportTempPath).exists())
			outPath = new File(exportTempPath);
		exportName = Strings.defaultString(exportName, UUID.randomUUID().toString());	
		File exportFile = new File(outPath,Exporters.addSuffixName(exportName));
		
		try {
			dynamic.export(exportFile);
		} catch (IOException e) {
			throw Throwables.wrapThrow(e);
		}
		return exportFile;		
	}
	
	/**
	 * 根据模板进行导出并交导出的excel文件下载到响应流中
	 * @param downloadName 下载的文件名称，无须进行编码，此处默认会进行编码处理
	 * @param columns 要传给模板的表头列表
	 * @param props 传给模板的参数列表数据
	 * @param records 传给模板的数据列表数据
	 * @param response 响应流
	 * @param closeable 下载完毕后是否关闭响应流
	 */
	@SuppressWarnings("rawtypes")
	public static void exportWithDownload(String downloadName,
							   String title,
							   List<String> columns,  
							   List<String> props,
							   List records,
							   HttpServletResponse response,boolean closeable) {
		File downloadFile = export(null, null, title, columns, props, records);
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

