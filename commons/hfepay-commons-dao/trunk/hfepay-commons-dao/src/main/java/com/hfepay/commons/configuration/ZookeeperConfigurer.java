package com.hfepay.commons.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

import com.hfepay.commons.base.collection.Arrays;


/**
 * ClassName: ZookeeperConfigurer <br/>
 * Reason: 应用参数配置器. <br/>
 * date: 2013-11-25 上午09:10:06 <br/>
 *
 * @author XieJunfeng
 * @version 
 * @since JDK 1.6
 */
public class ZookeeperConfigurer extends PropertyPlaceholderConfigurer {

	private Map<String, Object> ctxPropsMap = new HashMap<String, Object>();

    private ZookeeperResource zkLocation;
    private Resource[] localLocations = new Resource[0];

    @Override
    public void setLocation(Resource location) {
        zkLocation = (ZookeeperResource) location;
        super.setLocations((Resource[]) Arrays.add(localLocations, zkLocation));
    }

    @Override
    public void setLocations(Resource[] locations) {
        this.localLocations = locations;
        super.setLocations((Resource[]) Arrays.add(locations, zkLocation));
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        super.processProperties(beanFactoryToProcess, props);

		for (Object key : props.keySet()) {
			ctxPropsMap.put(key.toString(), props.get(key));
		}
    }

	public Object getProperty(String key) {
		return ctxPropsMap.get(key);
	}

	public ZookeeperResource getZkResoucre() {
		return zkLocation;
	}
}
