package com.hfepay.scancode.channel.commons;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author tanbiao
 */

public class PageTag extends TagSupport {

	private static final long serialVersionUID = 5501985693393798382L;

	private static final Log log = LogFactory.getLog(PageTag.class);

	private String pageFunction="toPage";//ajax分页的JavaScript函数名称
	
	private String curPage="1";//当前页码
	
	private String showPages="10";//显示的页码
	
	private String totalPages;//总页数
	
	private String PREVIOUS_PAGE = "上一页";

	private String NEXT_PAGE = "下一页 ";
	
	private String ANY_PAGE = "GO ";
	
	private final String S_SCRIPT="<script type=\"text/javascript\">";
	
	private final String E_SCRIPT="</script>";
	
	private String totalCounts = "0";
	
	public String getHref(int number) {
		return "Javascript:"+pageFunction+"(" + number + ");";
	}

	public String goHref(int number) {
		return " <a href=\"" + getHref(number) + "\" class=\"pagebox\">" + number + "</a>";
	}
	
	public String clickToPage(){
		String scFunction = "function clickToPage(){                                      "+
				"	var inputPage = document.getElementById(\"pageboxInpute\").value;"+
				"	if(inputPage==''){                                       "+
				"		return;                                              "+
				"	}                                                        "+
				pageFunction+"(inputPage);                                       "+
				"}                                                            "+
				"function inputEventBinder(obj){                              "+
				"	obj.value=obj.value.replace(/\\D/g,\"\").replace(/^0*/,\"\");"+
				"	if(obj.value>"+totalPages+"){                                "+
				"		obj.value="+totalPages+";                                "+
				"	}                                                        "+
				"}                                                            ";
		return S_SCRIPT+scFunction+E_SCRIPT;
	}
	
	public int doEndTag() throws JspException {
		int showPages = Integer.parseInt(this.showPages);
		int curpage = Integer.parseInt(this.curPage);
		int totalPages = Integer.parseInt(this.totalPages);
		
		StringBuffer strBuf = new StringBuffer(2048);
		// 总页数
		int pagecount = totalPages;
		// 初始化值
		if (curpage == 0) {
			curpage = 1;
		} else {
			if (curpage <= 0) {
				curpage = 1;
			}
			if (curpage > pagecount) {
				curpage = pagecount;
			}
		}
		

		if (curpage > 1) {
			strBuf.append("<a href=\"" + getHref(curpage - 1) + "\" class=\"pagebox\" >" + PREVIOUS_PAGE + "</a>");
		}

		// 分页
		if (pagecount <= showPages + 2) {
			for (int i = 1; i <= pagecount; i++) {
				if (i == curpage) {
					strBuf.append("<font class=\"cpagebox\">" + i + "</font>");
				} else {
					strBuf.append(goHref(i));
				}
			}
		} else {
			if (curpage < showPages) { 
				for (int i = 1; i <= showPages; i++) {
					if (i == curpage) {
						strBuf.append("<font class=\"cpagebox\">" + i + "</font>");
					} else {
						strBuf.append(goHref(i));
					}
				}
				strBuf.append("<font class=\"vpagebox\">...</font>");
				strBuf.append(goHref(pagecount));
			} else if (curpage > pagecount - showPages + 1) { // 右边
				strBuf.append(goHref(1));
				strBuf.append("<font class=\"vpagebox\">...</font>");
				for (int i = pagecount - showPages + 1; i <= pagecount; i++) {
					if (i == curpage) {
						strBuf.append("<font class=\"cpagebox\">" + i
								+ "</font>");
					} else {
						strBuf.append(goHref(i));
					}
				}
			} else { // 中间
				strBuf.append(goHref(1));
				//strBuf.append(goHref(2));
				strBuf.append("<font class=\"vpagebox\">...</font>");
				int offset = (showPages - 2) / 2;
				for (int i = curpage - offset; i <= curpage + offset; i++) {
					if (i == curpage) {
						strBuf.append("<font class=\"cpagebox\">" + i + "</font>");
					} else {
						strBuf.append(goHref(i));
					}
				}
				strBuf.append("<font class=\"vpagebox\">...</font>");
				strBuf.append(goHref(pagecount));
			}
		}

		// 显示下-页
		if (curpage != pagecount) {
			// 加上链接 curpage+1
			strBuf.append("<a href=\"" + getHref(curpage + 1) + "\" class=\"pagebox\" >" + NEXT_PAGE + "</a>");
		}
		
		// 显示跳页
		if (pagecount > 0) {
			//只能输入整数
			strBuf.append("<input type=\"text\" value="+curPage+"  id=\"pageboxInpute\" onkeyup='inputEventBinder(this)' onafterpaste='inputEventBinder(this)'/>");
			strBuf.append("<button class=\"pageboxBtn\" val=\"" + pagecount + "\" onclick='clickToPage();'>" + ANY_PAGE + "</button>");
			strBuf.append("<b class=\"totalCountInPage\">共&nbsp;"+totalPages+"&nbsp;页</b>");
			strBuf.append("<b class=\"totalCountInPage\">共&nbsp;"+totalCounts+"&nbsp;条</b>");
		}
				
//		strBuf.append("</div>");
		strBuf.append(clickToPage());
		//strBuf.append("<input name='pageNo' type='hidden' size='3' length='3' />");
		try {
			pageContext.getOut().println(strBuf.toString());
		} catch (IOException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
		}
		
		return EVAL_PAGE;
	}

	public String getCurPage() {
		return curPage;
	}

	public void setCurPage(String curPage) {
		this.curPage = curPage;
	}

	public String getShowPages() {
		return showPages;
	}

	public void setShowPages(String showPages) {
		this.showPages = showPages;
	}

	public String getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}

	public String getPageFunction() {
		return pageFunction;
	}

	public void setPageFunction(String pageFunction) {
		this.pageFunction = pageFunction;
	}
	
	public String getTotalCounts() {
		return totalCounts;
	}

	public void setTotalCounts(String totalCounts) {
		this.totalCounts = totalCounts;
	}
	
}

