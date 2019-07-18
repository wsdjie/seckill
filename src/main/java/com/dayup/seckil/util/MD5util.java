package com.dayup.seckil.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5util {

	public static String salt = "djspringboot";

	public static String md5(String str) {
		return DigestUtils.md5Hex(str);
	}
	//第一次
	public static String inputToBack(String str) {
		return md5(str + salt);
	}
	//第二次
	public static String backtToDb(String str, String dbsalt) {
		return md5(str + dbsalt);
	}
	
	public static String inputToDb(String str, String dbsalt) {
		return backtToDb(inputToBack(str) , dbsalt);
	}
}
