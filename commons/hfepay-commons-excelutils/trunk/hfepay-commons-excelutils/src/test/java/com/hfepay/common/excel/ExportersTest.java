package com.hfepay.common.excel;

import java.util.List;

import org.junit.Test;

import com.hfepay.common.excel.util.Exporters;
import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.collection.Maps;

public class ExportersTest {

	@Test
	public void testExcelExport() { 
		int cnt = 1000;
		List<Demo> demos = Lists.newList();
		for (int i = 0; i < cnt; i++ ) {
			demos.add(Demo.create("name"+i, "code"+i, i+10000));
		}
		
		Exporters.export("demo", null, null, Maps.map("ss",demos), Maps.map("title","Demo Data List"));
	}
//	
	public static class Demo {
		private String name;
		private String code;
		private Integer value;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public Integer getValue() {
			return value;
		}
		public void setValue(Integer value) {
			this.value = value;
		}
		
		public static Demo create(String name,String code,Integer value) {
			Demo d = new Demo();
			d.setName(name);
			d.setCode(code);
			d.setValue(value);
			return d;
		}
		
	}
}
