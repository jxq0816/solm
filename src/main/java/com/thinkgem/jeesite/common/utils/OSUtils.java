/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ThinkGem
 * @version 2014-4-15
 */
public class OSUtils {

	public static boolean isWindows() {
		String os = System.getProperty("os.name");
		if(os.toLowerCase().startsWith("windows")){
			return true;
		}else{
			return false;
		}
	}
}
