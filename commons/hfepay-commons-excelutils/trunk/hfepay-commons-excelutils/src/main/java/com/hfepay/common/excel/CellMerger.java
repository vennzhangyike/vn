
package com.hfepay.common.excel;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * excel单元格合并处理器。
 * 
 * @author Sam
 * 
 */
public interface CellMerger {
    
    /**
     * 垂直合并单元格
     * 
     * @param excelFile 要合并单元格的excel文件
     * @param uniteStartRow 合并单元格的起始行
     * @param rowSize 合并单元格要处理的行数
     * @param uniteCols 要进行合并元格的列集,可以指定多列
     * @throws IOException
     */
    public void merge(File excelFile, Integer uniteStartRow, Integer rowSize,
        List<Integer> uniteCols) throws IOException;
    
    /**
     * 垂直合并单元格
     * 
     * @param excelPath 要合并单元格的excel文件路径
     * @param uniteStartRow 合并单元格的起始行
     * @param rowSize 合并单元格要处理的行数
     * @param uniteCols 要进行合并元格的列集,可以指定多列
     * @throws IOException
     */
    public void merge(String excelPath, Integer uniteStartRow, Integer rowSize,
        List<Integer> uniteCols) throws IOException;
}
