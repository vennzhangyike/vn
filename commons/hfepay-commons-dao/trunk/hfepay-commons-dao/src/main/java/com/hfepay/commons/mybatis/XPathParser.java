package com.hfepay.commons.mybatis;

import java.io.InputStream;
import java.util.Properties;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;

import com.hfepay.commons.base.lang.reflect.Reflector;

/**
 * 扩展ibatis xml解析器
 * 
 * @author chujiang
 * 
 */
public class XPathParser extends org.apache.ibatis.parsing.XPathParser {

	public XPathParser(Document document) {
		super(document);
	}

	public XPathParser(InputStream inputStream, boolean validation, Properties variables, EntityResolver entityResolver) {
		super(inputStream, validation, variables, entityResolver);
	}

	/**
	 * 获取mapper.xml的Document
	 * 
	 * @return
	 */
	public Document getDocument() {
		return (Document) Reflector.of(XPathParser.class).getValue(this, "document");
	}

}
