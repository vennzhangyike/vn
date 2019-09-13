
package com.hfepay.common.excel;

import java.util.List;

/**
 * 合并单元设置信息。
 * 
 * @author Sam
 * 
 */
public class CellMergeSetting {
    
    private int mergeStartRow;
    private int rowSize;
    private List<Integer> mergeCols;
    
    public CellMergeSetting(int mergeStartRow, int rowSize, List<Integer> mergeCols) {

        this.mergeStartRow = mergeStartRow;
        this.rowSize = rowSize;
        this.mergeCols = mergeCols;
    }
    
    public int getMergeStartRow() {

        return mergeStartRow;
    }
    
    public void setMergeStartRow(int mergeStartRow) {

        this.mergeStartRow = mergeStartRow;
    }
    
    public int getRowSize() {

        return rowSize;
    }
    
    public void setRowSize(int rowSize) {

        this.rowSize = rowSize;
    }
    
    public List<Integer> getMergeCols() {

        return mergeCols;
    }
    
    public void setMergeCols(List<Integer> mergeCols) {

        this.mergeCols = mergeCols;
    }
    
}
