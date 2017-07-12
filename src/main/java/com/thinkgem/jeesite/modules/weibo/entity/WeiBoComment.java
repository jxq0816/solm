/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 * 微博评论Entity
 * @author jiangxingqi
 * @version 2017-03-06
 */
public class WeiBoComment extends DataEntity<WeiBoComment> {
	
	private static final long serialVersionUID = 1L;
	private String weiBoId;		// 微博原文的编号
	private String weiBoUserId;		// 发文人ID
	private String commentUserId;		// 评论人ID
	private String commentText;		// 评论内容
	private Date createdAt;		// 评论时间
	private List<WeiBoCommentReply> replyList;//回复列表

	public List<WeiBoCommentReply> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<WeiBoCommentReply> replyList) {
		this.replyList = replyList;
	}

	public WeiBoComment() {
		super();
	}

	public WeiBoComment(String id){
		super(id);
	}

	@Length(min=0, max=32, message="微博原文的编号长度必须介于 0 和 32 之间")
	public String getWeiBoId() {
		return weiBoId;
	}

	public void setWeiBoId(String weiBoId) {
		this.weiBoId = weiBoId;
	}
	
	@Length(min=0, max=32, message="发文人ID长度必须介于 0 和 32 之间")
	public String getWeiBoUserId() {
		return weiBoUserId;
	}

	public void setWeiBoUserId(String weiBoUserId) {
		this.weiBoUserId = weiBoUserId;
	}
	
	@Length(min=0, max=32, message="评论人ID长度必须介于 0 和 32 之间")
	public String getCommentUserId() {
		return commentUserId;
	}

	public void setCommentUserId(String commentUserId) {
		this.commentUserId = commentUserId;
	}
	
	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}