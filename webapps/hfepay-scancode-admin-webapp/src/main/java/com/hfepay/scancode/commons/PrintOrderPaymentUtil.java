/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.commons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.SwingUtilities;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Springs;
import com.hfepay.scancode.commons.contants.OrderStatus;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.order.OrderPaymentService;


public class PrintOrderPaymentUtil implements Printable {
	
	private OrderPayment orderPayment = null;
	private MerchantInfo merchantInfo = null;
	
	public PrintOrderPaymentUtil(){		
	}
	
	public PrintOrderPaymentUtil(OrderPayment orderPayment,MerchantInfo merchantInfo){
		this.orderPayment = orderPayment;
		this.merchantInfo = merchantInfo;
		
	}
	
	public static void printOrderPayment(String id) throws Exception {
		OrderPaymentService orderPaymentService = Springs.getBean("orderPaymentService");
		MerchantInfoService merchantInfoService = Springs.getBean("merchantInfoService");
		OrderPayment entity = orderPaymentService.findById(id);
		String merchantNo = entity.getMerchantNo();
		MerchantInfo merchantInfo = merchantInfoService.findByMerchantNo(merchantNo);
		// 通俗理解就是书、文档
		Book book = new Book();
		// 设置成竖打
		PageFormat pf = new PageFormat();
		pf.setOrientation(PageFormat.PORTRAIT);
		// 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
		Paper p = new Paper();
		p.setSize(240, 300);// 纸张大小
		p.setImageableArea(15, 15, 240, 300);// A4(595 X
												// 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72
		pf.setPaper(p);
		// 把 PageFormat 和 Printable 添加到书中，组成一个页面
		book.append(new PrintOrderPaymentUtil(entity,merchantInfo), pf);

		// 获取打印服务对象
		PrinterJob job = PrinterJob.getPrinterJob();
		// 设置打印类
		job.setPageable(book);
		job.print();
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		// 转换成Graphics2D
		Graphics2D g2 = (Graphics2D) graphics;
		// 设置打印颜色为黑色
		g2.setColor(Color.black);

		// 打印起点坐标
		double x = pageFormat.getImageableX();
		double y = pageFormat.getImageableY();
		int pageWidth = 167;//设置页面宽度
		if(orderPayment != null && merchantInfo != null){			
			String merchantName = merchantInfo.getMerchantName();
			String phone = merchantInfo.getPhone();//用客服电话
			Font font = new Font("新宋体", Font.PLAIN, 12);
			int space = 5;//设置页面字体间距
			g2.setFont(font);// 设置标题字体  
			float titleHeigth = font.getSize2D();// 字体默认标题高度
			if(0 < merchantName.length() && merchantName.length() <=10){
				FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);
		        int merchantNameWidth = SwingUtilities.computeStringWidth(fm, merchantName);//标题宽度
		        g2.drawString(merchantName, (pageWidth-merchantNameWidth) / 2, (float) y + titleHeigth + space);  
			}else if(10 < merchantName.length() && merchantName.length() <=20){				
				String merchantNameUp = merchantName.substring(0, 10);
				FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);
		        int merchantNameUpWidth = SwingUtilities.computeStringWidth(fm, merchantNameUp);//标题宽度
		        g2.drawString(merchantNameUp, (pageWidth-merchantNameUpWidth) / 2, (float) y + titleHeigth + space); 
				String merchantNameDown = merchantName.substring(10, merchantName.length());
				int merchantNameDownWidth = SwingUtilities.computeStringWidth(fm, merchantNameDown);//标题宽度
				titleHeigth = (font.getSize2D() + space) * 2;// (字体默认标题高度 + 间距)  的2倍
		        g2.drawString(merchantNameDown, (pageWidth-merchantNameDownWidth) / 2, (float) y + titleHeigth); 
			}
			font = new Font("新宋体", Font.PLAIN, 9);
			g2.setFont(font);// 设置内容字体  
			int row = 0;
			float contentHeigth = font.getSize2D();// 字体默认内容高度
			if(Strings.isNotEmpty(phone)){
				String phoneStr = "电话：" + phone;
				FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(font);
		        int phoneStrWidth = SwingUtilities.computeStringWidth(fm, phoneStr);//标题宽度
		        row++;
		        g2.drawString(phoneStr, (pageWidth-phoneStrWidth) / 2, (float) y + titleHeigth + (contentHeigth + space)*row);
		        
			}
			String xingUpStr = "**************************************";
	        row++;
	        g2.drawString(xingUpStr, (float) x, (float) y + titleHeigth + (contentHeigth + space)*row);			
			String merchantNoStr = "商户号:" + merchantInfo.getMerchantNo();
			row++;
	        g2.drawString(merchantNoStr, (float) x, (float) y + titleHeigth + (contentHeigth + space)*row);
	        String payName = "";
	        if(PayCode.PAYCODE_WXGZHZF.getCode().equals(orderPayment.getPayCode())){
	        	payName = PayCode.PAYCODE_WXGZHZF.getDesc();
	        }else if(PayCode.PAYCODE_WXSMZF.getCode().equals(orderPayment.getPayCode())){
	        	payName = PayCode.PAYCODE_WXSMZF.getDesc();
	        }else if(PayCode.PAYCODE_ZFBSMZF.getCode().equals(orderPayment.getPayCode())){
	        	payName = PayCode.PAYCODE_ZFBSMZF.getDesc();
	        }
	        String payCodeStr = "付款方式:" + payName;
			row++;
	        g2.drawString(payCodeStr, (float) x, (float) y + titleHeigth + (contentHeigth + space)*row);
	        String tradeNoStr = "订单号:" + orderPayment.getTradeNo();
			row++;
	        g2.drawString(tradeNoStr, (float) x, (float) y + titleHeigth + (contentHeigth + space)*row);
	        String orderStatus = "";
	        if(OrderStatus.ORDER_FAIL.getCode().equals(orderPayment.getOrderStatus())){
	        	orderStatus = OrderStatus.ORDER_FAIL.getDesc();
	        }else if(OrderStatus.ORDER_SUCCESS.getCode().equals(orderPayment.getOrderStatus())){
	        	orderStatus = OrderStatus.ORDER_SUCCESS.getDesc();
	        }else if(OrderStatus.ORDER_TREATE.getCode().equals(orderPayment.getOrderStatus())){
	        	orderStatus = OrderStatus.ORDER_TREATE.getDesc();
	        }
	        String orderStatusStr = "交易状态:" + orderStatus;
			row++;
	        g2.drawString(orderStatusStr, (float) x, (float) y + titleHeigth + (contentHeigth + space)*row);
	        String beginTimeStr = "交易日期:" + Dates.format("yyyy-MM-dd HH:mm:ss", orderPayment.getBeginTime());
			row++;
	        g2.drawString(beginTimeStr, (float) x, (float) y + titleHeigth + (contentHeigth + space)*row);
	        String cashierNoStr = "操作员号:" + orderPayment.getCashierNo();
	        row++;
	        g2.drawString(cashierNoStr, (float) x, (float) y + titleHeigth + (contentHeigth + space)*row);
	        row++;
	        g2.drawLine((int) x,(int) (y + titleHeigth + (contentHeigth + space)*row),(int) (pageWidth - x),(int) (y + titleHeigth + (contentHeigth + space)*row)); //绘制实线	       
	        font = new Font("新宋体", Font.PLAIN, 10);
			g2.setFont(font);// 设置金额字体  
			String orderAmtStr = "实收 RMB:" + orderPayment.getOrderAmt();
			row++;
	        g2.drawString(orderAmtStr, (float) x, (float) y + titleHeigth + (contentHeigth + space)*row);
	        font = new Font("新宋体", Font.PLAIN, 9);
			g2.setFont(font);// 设置内容字体  
	        String xingDownStr = "**************************************";
	        row++;
	        g2.drawString(xingDownStr, (float) x, (float) y + titleHeigth + (contentHeigth + space)*row);	        
	        String tipUpStr = "请妥善保管好购物凭证";
			FontMetrics fmUp = Toolkit.getDefaultToolkit().getFontMetrics(font);
	        int tipUpStrWidth = SwingUtilities.computeStringWidth(fmUp, tipUpStr);
	        row++;
	        g2.drawString(tipUpStr, (pageWidth-tipUpStrWidth) / 2, (float) y + titleHeigth + (contentHeigth + space)*row); 
	        font = new Font("新宋体", Font.PLAIN, 10);
			g2.setFont(font);// 设置底部字体  
	        String tipDownStr = "多谢惠顾";
			FontMetrics fmDown = Toolkit.getDefaultToolkit().getFontMetrics(font);
	        int tipDownStrWidth = SwingUtilities.computeStringWidth(fmDown, tipDownStr);
	        row++;
	        g2.drawString(tipDownStr, (pageWidth-tipDownStrWidth) / 2, (float) y + titleHeigth + (contentHeigth + space)*row); 
	        row++;
	        row++;
	        float[] dash1 = { 4.0f };
			// 设置打印线的属性。
			// 1.线宽 2、3、不知道，4、空白的宽度，5、虚线的宽度，6、偏移量
			g2.setStroke(new BasicStroke(0.1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, dash1, 0.0f));
	        g2.drawLine((int) x,(int) (y + titleHeigth + (contentHeigth + space)*row),pageWidth-(int) x,(int) (y + titleHeigth + (contentHeigth + space)*row)); //绘制实线
	        g2.dispose();
		}
		return PAGE_EXISTS;
	}

}
