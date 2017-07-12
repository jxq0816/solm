/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 微博私信Entity
 * @author jiangxingqi
 * @version 2017-03-23
 */
public class WeiBoMail extends DataEntity<WeiBoMail> {
	
	private static final long serialVersionUID = 1L;
	private String text;		// text
	private String receiverId;		// 接受人ID
	private String receiverName;		// 接受人昵称
	private String senderId;		// 发送人
	private String senderName;		// 发送人昵称
	private Date createdAt;		// 发送时间
	
	public WeiBoMail() {
		super();
	}

	public WeiBoMail(String id){
		super(id);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Length(min=0, max=32, message="接受人ID长度必须介于 0 和 32 之间")
	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	
	@Length(min=0, max=32, message="接受人昵称长度必须介于 0 和 32 之间")
	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
	@Length(min=0, max=32, message="发送人长度必须介于 0 和 32 之间")
	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	
	@Length(min=0, max=32, message="发送人昵称长度必须介于 0 和 32 之间")
	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}