/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.nms.entity;

/**
 * 危机数据Entity
 * @author jiangxingqi
 * @version 2017-01-18
 */
public class FinalQuantiy{

	//问题状态：未处理 客户状态 ：未解决 动作状态 0：未处理
	final static public String unsolved="0";
	// 问题状态1：已解决 客户状态 ：已解决 动作状态 1：已处理
	final static public String solved="1";
	final static public String report="2";
	final static public String transfer="3";
	final static public String closed="4";

	//员工
	final static public String leader="2";
	final static public String employee="3";


	//部门
	final static public String company="1";
	final static public String department="2";
	final static public String adminId="1";

	//通知
	final static public String publish="1";

	//通知类型
	final static public String crisisDataStore="0";
	final static public String commentReply="1";
	final static public String at="2";
	final static public String mail="3";


}