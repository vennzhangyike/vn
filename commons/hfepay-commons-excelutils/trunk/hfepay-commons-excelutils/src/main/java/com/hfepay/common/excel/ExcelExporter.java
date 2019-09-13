
package com.hfepay.common.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Excel 文件导出处理器,根据用户指定报表模板和数据源结合进行导出，报表模板支持嵌入标签 或者脚本， 数据源支持的格式有：List-List,List-Map,List-VO。
 * 
 * @author Sam
 * 
 */
public interface ExcelExporter {
    
    /**
     * 添加导出参数,如在报表中这样声明${paramName},可以传进来一个值来替换此, 如果声明了参数而没有传没有参数值,则值为空
     * 
     * @param paramName 参数名
     * @param paramValue 参数值
     */
    public ExcelExporter addParam(String paramName, Object paramValue);
    
    /**
     * 添加导出的数据源
     * 
     * @param dsName
     * @param dataSource
     */
    @SuppressWarnings("rawtypes")
	public ExcelExporter addDataSource(String dsName, List dataSource);
    
    /**
     * 添加导出的数据源集
     * 
     * @param dataSources
     */  
	@SuppressWarnings("rawtypes")
	public ExcelExporter addDataSources(Map dataSources);
    
    /**
     * 添加一个参数集
     * 
     * @param params
     */
    @SuppressWarnings("rawtypes")
    public ExcelExporter addParams(Map params);
    
    /**
     * 执行导出
     * 
     * @throws IOException
     */
    public void export() throws IOException;
    
    
    /**
     * 导出到一个输出流中
     * 
     * @param outStream 输出流
     * @param isCloseStream 输出完毕后是否要关闭此输出流
     * @throws IOException
     */
    public void export(OutputStream outStream, boolean isCloseStream) throws IOException;
    
    /**
     * 取得内部处理导出的excel处理器，可以是自定义或者其他第三方工具如JXLS
     * 
     * @return
     */
    public <T> T getExcelHandler();
    
    /**
     * 设置要隐藏的列,即在学渲染时不显示指定的列
     * 
     * @param columns
     */
    public ExcelExporter hideColumns(int... columns);
    
    /**
     * 获取导出的路径
     * 
     * @return
     */
    public String getExportPath();
    
    /**
     * 设置导出的路径
     * 
     * @param exportPath
     */
    public ExcelExporter setExportPath(String exportPath);
    
    /**
     * 获取报表模板路径
     * 
     * @return
     */
    public String getExportTemplatePath();
    
    /**
     * 设置报表模板路径
     * 
     * @param reportPath
     */
    public ExcelExporter setReportPath(String reportPath);
    
    /**
     * 设置合并单元格
     * 
     * @param uniteStartRow
     * @param rowSize
     * @param uniteCols
     * @return
     */
    public ExcelExporter cellMerge(Integer uniteStartRow, Integer rowSize, List<Integer> uniteCols);
    
}
