package com.hfepay.pay.utils.IpUtil;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by long on 2017/1/17.
 */
public final class GetCityByIpUtil implements ILocator, Observer {

    private final SimpleFileWatchService watchService;
//    private final String filePath;
    private final Locator locator;
    private static GetCityByIpUtil l;

    public GetCityByIpUtil(String filePath, int intervalSeconds) throws IOException {
//        this.filePath = filePath;
        locator = Locator.loadFromLocal(filePath);
        watchService = new SimpleFileWatchService(filePath, intervalSeconds);
        watchService.addObserver(this);
        watchService.excute();
    }
    
    public static void init(){
    	 try {
    		if (null == l) {
    			System.out.println("==================================================");
//    			l = new GetCityByIpUtil("E:\\华付通\\小二买单\\IP源数据\\17monipdb.dat", 10);
    			l = new GetCityByIpUtil("/projects/hfscancode/app/scancode/conf/17monipdb.dat", 10);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static LocationInfo getCityInfoByIP(String ip){
    	init();
        return l.find(ip);
    }

    // only for test
    public static void main(String[] args) {
        System.out.println(getCityInfoByIP("180.149.131.31"));
    }

    @Override
    public LocationInfo find(String ip) {
        return locator.find(ip);
    }

    @Override
    public LocationInfo find(byte[] ipBin) {
        return locator.find(ipBin);
    }

    @Override
    public LocationInfo find(int address) {
        return locator.find(address);
    }

    @Override
    public void update(Observable o, Object arg) {
//        try {
//            locator = Locator.loadFromLocal(filePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void shutdown() {
        watchService.deleteObserver(this);
        watchService.shutdown();
    }
}

