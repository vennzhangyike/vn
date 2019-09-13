package com.hfepay.common.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;


/**
 * 
 * ClassName: ExportExcelController <br/>
 * Function: 根据模板导出EXCEL WEB控制器基类. <br/>
 * Reason:  <br/>
 * date: 2014年9月13日 上午11:37:23 <br/>
 *
 * @version
 */
public class ExportExcelController {
	
	/**
	 * exportExcel:导出EXCEL
	 * @param map 导出数据
	 * @param templateFilePath excel模板路径
	 * @param res 
	 */
    public static void exportExcel(Map<String, Object> map,String templateFilePath,HttpServletResponse res){
		
		Date date=new Date();
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName=sf.format(date);
		
		res.setContentType("application/msexcel;charset=UTF-8");// 定义输出类型
		res.reset();// 清除缓冲中的数据
		res.setHeader("Content-disposition","attachment;filename="+fileName+".xls");// 设定输出文件头
		
		//获取excel模板
		InputStream inputStream = ExportExcelController.class.getClassLoader().getResourceAsStream(templateFilePath);
		XLSTransformer transformer=new XLSTransformer();
		
		//HSSFWorkbook workBook=transformer.transformXLS(inputStream,map);
		
		
		OutputStream out=null;
		try {
			Workbook workBook=transformer.transformXLS(inputStream, map);
			out=res.getOutputStream();
			workBook.write(out);
			System.out.println("导出EXCEL成功!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
