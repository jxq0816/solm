/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 微博评论Entity
 * @author jiangxingqi
 * @version 2017-03-06
 */
public class WeiBoCommentReply extends DataEntity<WeiBoCommentReply> {
	
	private static final long serialVersionUID = 1L;
	private WeiBoComment weiBoComment;		// 微博评论的编号
	private String replyUserId;		// 回复人
	private String replyText;		// 回复内容
	private Date createdAt;		// 评论时间
	
	public WeiBoCommentReply() {
		super();
	}

	public WeiBoCommentReply(String id){
		super(id);
	}

	public WeiBoComment getWeiBoComment() {
		return weiBoComment;
	}

	public void setWeiBoComment(WeiBoComment weiBoComment) {
		this.weiBoComment = weiBoComment;
	}

	@Length(min=0, max=32, message="回复人长度必须介于 0 和 32 之间")
	public String getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}
	
	public String getReplyText() {
		return replyText;
	}

	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}