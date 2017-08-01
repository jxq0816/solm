package com.thinkgem.jeesite.test;

public class Test {

	public static void main(String[] args) {
		String keywordString = "[2017, 微博, 评论]"	;
		keywordString=keywordString.substring(1,keywordString.length()-1);
		String[] array=keywordString.split(",");
		System.out.print(array[0]);
	}
}
