package com.hfepay.commons.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;


public class PropertyPlaceholderConfigurer extends org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {
	
	private static Map<String, Object> ctxPropertiesMap;  
	
	@Override
	protected void loadProperties(Properties props) throws IOException {
		super.loadProperties(props);
	}
	
    @Override  
    protected void processProperties(ConfigurableListableBeanFactory beanFactory,  
            Properties props)throws BeansException {  
  
        super.processProperties(beanFactory, props);  
        //load properties to ctxPropertiesMap  
        ctxPropertiesMap = new HashMap<String, Object>();  
        for (Object key : props.keySet()) {  
            String keyStr = key.toString();  
            String value = props.getProperty(keyStr);  
            ctxPropertiesMap.put(keyStr, value);  
        }  
    }  
  
    //static method for accessing context properties  
    public static Object getContextProperty(String name) {  
        return ctxPropertiesMap.get(name);  
    }  
	
	@Override
	public void setLocations(Resource[] locations) {
		String tomcatLocation = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
		if(!StringUtils.isEmpty(tomcatLocation) && tomcatLocation.indexOf("work")>0){
			tomcatLocation = tomcatLocation.substring(0, tomcatLocation.indexOf("work"));
		}else if(!StringUtils.isEmpty(tomcatLocation) && tomcatLocation.indexOf("webapps")>0){
			tomcatLocation = tomcatLocation.substring(0, tomcatLocation.indexOf("webapps"));
		}
		logger.info(tomcatLocation);
		Resource[] newLocations = new Resource[locations.length];
		for (int i = 0; i < locations.length; i ++) {
			Resource location =  locations[i];
			try {
				if("file:./application.properties".equals(location.getURL().toString())){
					location = new UrlResource(tomcatLocation + "./application.properties");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			newLocations[i] = location;
		}
		super.setLocations(newLocations);
	}

}
