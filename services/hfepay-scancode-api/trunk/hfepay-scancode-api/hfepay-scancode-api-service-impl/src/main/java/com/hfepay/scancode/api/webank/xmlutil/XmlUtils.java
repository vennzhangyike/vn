package com.hfepay.scancode.api.webank.xmlutil;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import net.sf.json.JSONObject;


public class XmlUtils {
	public static Document createNewXmlObj(){
		// 定义工厂 API，使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器  
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        // 定义 API， 使其从 XML 文档获取 DOM 文档实例。使用此类，应用程序员可以从 XML 获取一个 Document  
        DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        // Document 接口表示整个 HTML 或 XML 文档。从概念上讲，它是文档树的根，并提供对文档数据的基本访问  
        Document document = builder.newDocument(); 
		return document;
	}
	
	/**
	 * 根据xml对象输出报文字符串
	 * @param document
	 * @return
	 * @throws Exception
	 */
	public static String xmlStringByObj(Document document) throws Exception{
		String result = null;  
        if (document != null) {  
            StringWriter strWtr = new StringWriter();  
            StreamResult strResult = new StreamResult(strWtr);  
            TransformerFactory tfac = TransformerFactory.newInstance();  
            try {  
                javax.xml.transform.Transformer t = tfac.newTransformer();  
                t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");  
//                t.setOutputProperty(OutputKeys.INDENT, "yes");//报文是否换行  
                t.setOutputProperty(OutputKeys.METHOD, "xml"); // xml, html,  
                t.setOutputProperty(  
                        "{http://xml.apache.org/xslt}indent-amount", "4");  
                t.transform(new DOMSource(document.getDocumentElement()),  
                        strResult);  
            } catch (Exception e) {  
            	throw new Exception("#####创建XML报文错误~！",e); 
            }  
            result = strResult.getWriter().toString();  
            try {  
                strWtr.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
	}
	public static JSONObject parseXMLTOJSON(org.dom4j.Element ele, JSONObject obj) {
		for (Iterator<?> i = ele.elementIterator(); i.hasNext();) {
			org.dom4j.Element node = (org.dom4j.Element) i.next();
			if (node.attributes() != null && node.attributes().size() > 0) {
				for (Iterator<?> j = node.attributeIterator(); j.hasNext();) {
					org.dom4j.Attribute item = (org.dom4j.Attribute) j.next();
					obj.element(item.getName(), item.getValue());
				}
			}
			if (node.getText().length() > 0) {
				obj.element(node.getName(), node.getText());
			}
			if (node.elementIterator().hasNext()) {
				parseXMLTOJSON(node, obj);
			}
		}
		return obj;
	}
}
