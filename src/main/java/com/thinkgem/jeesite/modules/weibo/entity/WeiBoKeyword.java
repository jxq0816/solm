/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 微博关键字Entity
 * @author jiangxingqi
 * @version 2017-03-14
 */
public class WeiBoKeyword extends DataEntity<WeiBoKeyword> {
	
	private static final long serialVersionUID = 1L;
	private String keyword;		// keyword
	
	public WeiBoKeyword() {
		super();
	}

	public WeiBoKeyword(String id){
		super(id);
	}

	@Length(min=1, max=20, message="keyword长度必须介于 1 和 20 之间")
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}