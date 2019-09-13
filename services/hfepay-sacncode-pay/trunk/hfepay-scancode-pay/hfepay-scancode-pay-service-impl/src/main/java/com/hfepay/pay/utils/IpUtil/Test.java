package com.hfepay.pay.utils.IpUtil;

import java.io.IOException;

public class Test {

	public static void main(String[] args) {
		Locator l;
		try {
			l = Locator.loadFromNet("http://7j1xnu.com1.z0.glb.clouddn.com/17monipdb.dat");
			LocationInfo info = l.find("8.8.8.8");
			info = l.find("183.131.7.18");
			System.out.println(info);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
