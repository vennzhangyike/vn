package com.hfepay.commons.base.lang;

import org.apache.commons.lang3.RandomStringUtils;
/**
 * 添加基于common-lang3的random 工具类
 * @author Sam
 *
 */
public class Randoms extends RandomStringUtils {
	public static void main(String[] args) {
		Sys.println(Randoms.randomNumeric(6));
	}
}
