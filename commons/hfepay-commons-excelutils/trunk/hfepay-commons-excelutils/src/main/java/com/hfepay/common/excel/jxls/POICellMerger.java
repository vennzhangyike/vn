
package com.hfepay.common.excel.jxls;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.hfepay.common.excel.CellMerger;
import com.hfepay.commons.base.io.Streams;

/**
 * 基于POI的EXCEL 单元格合并器。
 * @author Sam
 *
 */
@SuppressWarnings("deprecation")
public class POICellMerger implements CellMerger {
    
    public void merge(File excelFile, Integer uniteStartRow, Integer rowSize,
        List<Integer> uniteCols) throws IOException {
        POIFSFileSystem poiFile = new POIFSFileSystem(Streams.fileIn(excelFile));
        HSSFWorkbook workbook = new HSSFWorkbook(poiFile);
        HSSFSheet sheet = workbook.getSheetAt(0);
        int endRow = uniteStartRow + rowSize + 1;
        if (CollectionUtils.isNotEmpty(uniteCols)) {
            for (Integer col : uniteCols) {
                for (int r = uniteStartRow, mergeEnd = uniteStartRow, mergeStart = uniteStartRow; r < endRow; r++) {
                    HSSFRow row = sheet.getRow(r);
                    HSSFCell cell = row.getCell(col.shortValue());
                    String currCellValue = ObjectUtils.toString(cell);
                    boolean isLastRow = (r == (endRow - 1));
                    if (r > uniteStartRow) {
                        HSSFCell pcell = sheet.getRow(r - 1).getCell(col.shortValue());
                        String provComment = ObjectUtils.toString(pcell);
                        if (!StringUtils.equals(currCellValue, provComment) || isLastRow) {
                            mergeEnd = isLastRow ? r : r - 1;
                            sheet.addMergedRegion(new Region(mergeStart,  col.shortValue(),mergeEnd,col.shortValue()));
                            mergeStart = r;
                        }
                    }
                }
            }
            
            OutputStream fileOut = null;
            try {
                fileOut = Streams.fileOut(excelFile);
                workbook.write(fileOut);
            } finally {
            	Streams.safeClose(fileOut);
            }
        }
    }
     
    public void merge(String excelPath, Integer uniteStartRow, Integer rowSize,
        List<Integer> uniteCols) throws IOException {

        merge(new File(excelPath), uniteStartRow, rowSize, uniteCols);
    }
    
    public static void main(String[] strs) throws IOException {

        // new POICellUniter().unite("c:\\test.xls", 4, 63, 0);
        // POIFSFileSystem poiFile = new POIFSFileSystem(new FileInputStream("c:\\test.xls"));
        // HSSFWorkbook workbook = new HSSFWorkbook(poiFile);
        // HSSFSheet sheet = workbook.getSheetAt(0);
        // HSSFComment comment = sheet.getCellComment(4,0);
        
        // Lang.println(sheet.getRow(4).getCell(0).toString());
    }
    
}
