package com.hfepay.commons.criteria.impl;

import java.util.Map;

import com.hfepay.commons.base.collection.Arrays;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;
/**
 * 基于Annotation配置的列<->属性映射器
 * 
 * @author Sam
 *
 */
public class AnnotationColumnMapper implements ColumnMapper {
	 
	public Column getColumn(Class<?> entityClass,String property) {
		Map<String,SelectColumnMapping> mappings = getMappings(entityClass);
		if (mappings != null && mappings.get(property) != null) {
			SelectColumnMapping scm = mappings.get(property);
			return new Column(scm.column(),Strings.defaultIfEmpty(scm.tableAlias(),scm.table()),scm.type());
		}
		return null;
	}

	private Map<String,SelectColumnMapping> getMappings(Class<?> entityClass) {
		Map<String,SelectColumnMapping> cachedMapping = cachedPool.get(entityClass);
		if (cachedMapping == null) {
			cachedMapping = Maps.newMap();
			SelectColumnMappings mappings = entityClass.getAnnotation(SelectColumnMappings.class);
			SelectColumnMapping[] mps = mappings.value();
			if (Arrays.isNotEmpty(mps)) {
				for (SelectColumnMapping m : mps) {
					cachedMapping.put(m.property(), m);
				}
			}
			cachedPool.put(entityClass, cachedMapping);
		}
		return cachedMapping;
	}
	

	private static Map<Class<?>,Map<String,SelectColumnMapping>> cachedPool = Maps.newMap();

}
