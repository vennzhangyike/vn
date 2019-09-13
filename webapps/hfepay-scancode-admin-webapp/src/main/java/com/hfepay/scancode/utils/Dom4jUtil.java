package com.hfepay.scancode.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Dom4jUtil {
	
static Logger logger = LoggerFactory.getLogger(Dom4jUtil.class);
	
    /**
     * 获取指定xml文档的Document对象,xml文件必须在classpath中可以找到
     *
     * @param xmlFilePath xml文件路径
     * @return Document对象
     */ 
    public static Document parse2Document(String xmlFilePath){
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            doc = reader.read(Dom4jUtil.class.getClassLoader().getResourceAsStream(xmlFilePath));
        } catch (DocumentException e) {
            e.printStackTrace();
            logger.info("Dom4jUtil读取xml失败，路径为" + xmlFilePath);
        }
        return doc;
    }
    
    @SuppressWarnings({ "rawtypes" })
	public static Map ParseXmlDataWithName(String xmlFilePath,String eleName){
        //将xml解析为Document对象
        Document doc = Dom4jUtil.parse2Document(xmlFilePath);
        //获取文档的根元素
        Element root  = doc.getRootElement();
        //定义保存属性、值的map
        Map<String,String> map = new HashMap<String,String>();
        Element node = root.element(eleName);
        for(Iterator i_action=node.elementIterator("node");i_action.hasNext();){
            Element e_action = (Element)i_action.next();
            map.put(e_action.attributeValue("name"), e_action.getText());
        }
        
        return map;
        
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
        Map map = Dom4jUtil.ParseXmlDataWithName("D:/record.xml","value");
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
           Map.Entry<String, String> entry = it.next();
           System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
    }

}