
package com.hfepay.common.excel.jxls;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.hfepay.common.excel.AbstractExcelExporter;
import com.hfepay.common.excel.ExcelExporter;
import com.hfepay.commons.base.lang.Throwables;
  

/**
 * 基于JXLS的excel导出处理器。
 * 
 * @author Sam
 * 
 */
public class JXLSExcelExporter extends AbstractExcelExporter implements ExcelExporter {
    
    private XLSTransformer excelExportHandler = new XLSTransformer();
    
    public JXLSExcelExporter() {

    }
    
    public JXLSExcelExporter(String reportPath, String exportPath) {

        super(reportPath, exportPath);
    }
    
    @SuppressWarnings({ "rawtypes" })
    public JXLSExcelExporter(String reportPath, String exportPath, Map params, Map ds) {

        super(reportPath, exportPath);
        addParams(params);
        addDataSources(ds);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void doExport() throws IOException {

        Map jxlsParams = new HashMap();
        jxlsParams.put("params", reportParams);
        jxlsParams.putAll(dataSources);
        checkAndCreateDir(exportPath);
        try {
			excelExportHandler.transformXLS(reportPath, jxlsParams, exportPath);
		} catch (ParsePropertyException e) {
			throw Throwables.wrapThrow(e);
		} catch (InvalidFormatException e) {
			throw Throwables.wrapThrow(e);
		}
        if (cellSetting != null) {
            new POICellMerger().merge(new File(exportPath), cellSetting.getMergeStartRow(),
                    cellSetting.getRowSize(), cellSetting.getMergeCols());
        }
    }
    
    private String checkAndCreateDir(String exportPath) {

        if (StringUtils.isNotBlank(exportPath)) {
            exportPath = exportPath.replaceAll("\\\\", "/");
            String dir = StringUtils.substringBeforeLast(exportPath, "/");
            File file = new File(dir);
            if (!file.exists())
                file.mkdir();
        }
        return exportPath;
    }
    
    public ExcelExporter hideColumns(int... columns) {
        excelExportHandler.setColumnsToHide(toShorts(columns));
        return this;
    }
    
    @SuppressWarnings("unchecked")
    public XLSTransformer getExcelHandler() {
        return excelExportHandler;
    }
    
    private short[] toShorts(int[] ints) {
    	if (ints != null && ints.length > 0) {
    		short[] ss = new short[ints.length];
    		for (int i = 0,len = ints.length; i < len; i++ ) {
    			ss[i] = (short) ints[i];
    		}
    	}
    	return null;
    }
    
}
