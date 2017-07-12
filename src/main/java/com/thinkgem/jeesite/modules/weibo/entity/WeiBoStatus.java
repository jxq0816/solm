/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 微博Entity
 * @author jiangxingqi
 * @version 2017-03-13
 */
public class WeiBoStatus extends DataEntity<WeiBoStatus> {
	
	private static final long serialVersionUID = 1L;
	private String text;		// 原文
	private String keyword;		// 关键词
	private String weiboUserId;		// 发文人ID
	private String screenName;		// 发文人昵称
	private Date createdAt;		// 发文时间
	
	public WeiBoStatus() {
		super();
	}

	public WeiBoStatus(String id){
		super(id);
	}

	@ExcelField(title="微博ID", type=1, align=2, sort=1)
	public String getId() {
		return id;
	}

	@ExcelField(title="正文", type=1, align=10, sort=4)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Length(min=0, max=32, message="关键词长度必须介于 0 和 32 之间")
	@ExcelField(title="关键字", type=1, align=2, sort=2)
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	@Length(min=0, max=32, message="发文人ID长度必须介于 0 和 32 之间")
	public String getWeiboUserId() {
		return weiboUserId;
	}

	public void setWeiboUserId(String weiboUserId) {
		this.weiboUserId = weiboUserId;
	}
	
	@Length(min=0, max=32, message="发文人昵称长度必须介于 0 和 32 之间")
	@ExcelField(title="昵称", type=1, align=2, sort=3)
	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="时间", type=1, align=20, sort=5)
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}