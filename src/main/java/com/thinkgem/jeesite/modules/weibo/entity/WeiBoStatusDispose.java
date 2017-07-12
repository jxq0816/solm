/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 微博数据处理Entity
 * @author jiangxingqi
 * @version 2017-04-06
 */
public class WeiBoStatusDispose extends DataEntity<WeiBoStatusDispose> {
	
	private static final long serialVersionUID = 1L;
	private String autoId;		// auto_id
	private WeiBoStatus weiBoStatus;		// 微博外键
	private User user;		// 外键客服编号
	private String actionStatus;		// 动作状态
	private String issueStatus;		// 问题状态
	private String customerStatus;		// 客户状态
	private String reply;		// reply
	private Date responseTime;		// response_time
	private float responseHourDiff;		// response_hour_diff
	private Date replyTime;		// reply_time
	private float replyHourDiff;		// reply_hour_diff
	private Date solveTime;		// solve_time
	private float solveHourDiff;		// solve_hour_diff
	private String sourceHostName;		// source_host_name
	private String crisisLevel;		// 危机级别
	private String repostsCount;		// 转
	private String attitudesCount;		// 赞
	private String commentsCount;		// 评
	
	public WeiBoStatusDispose() {
		super();
	}

	public WeiBoStatusDispose(String id){
		super(id);
	}

	@ExcelField(title="编号", align=2, sort=1)
	@Length(min=1, max=11, message="auto_id长度必须介于 1 和 11 之间")
	public String getAutoId() {
		return autoId;
	}

	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}

	@ExcelField(title="危机级别", align=2, sort=2, dictType="crisis_level")
	@Length(min=0, max=11, message="危机级别长度必须介于 0 和 11 之间")
	public String getCrisisLevel() {
		return crisisLevel;
	}

	public void setCrisisLevel(String crisisLevel) {
		this.crisisLevel = crisisLevel;
	}

	@NotNull(message="外键客服编号不能为空")
	@ExcelField(title="客服", align=2, sort=3, value="user.name")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ExcelField(title="原文", value="weiBoStatus.text", align=2, sort=4)
	public WeiBoStatus getWeiBoStatus() {
		return weiBoStatus;
	}

	@ExcelField(title="发文时间", value="weiBoStatus.createdAt", align=2, sort=5)
	public WeiBoStatus getWeiBoStatusCreateAt() {
		return weiBoStatus;
	}

	@ExcelField(title="问题状态", align=2, sort=6,dictType="issue_status")
	public String getIssueStatus() {
		return issueStatus;
	}

	public void setIssueStatus(String issueStatus) {
		this.issueStatus = issueStatus;
	}

	@Length(min=0, max=1, message="客户状态长度必须介于 0 和 1 之间")
	@ExcelField(title="客户状态", align=2, sort=7,dictType="customer_status")
	public String getCustomerStatus() {
		return customerStatus;
	}

	public void setWeiBoStatus(WeiBoStatus weiBoStatus) {
		this.weiBoStatus = weiBoStatus;
	}

	@ExcelField(title="回复内容", align=2, sort=8)
	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	
	@Length(min=0, max=1, message="动作状态长度必须介于 0 和 1 之间")
	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}

	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}


	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}

	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	

	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSolveTime() {
		return solveTime;
	}

	public void setSolveTime(Date solveTime) {
		this.solveTime = solveTime;
	}

	public float getResponseHourDiff() {
		return responseHourDiff;
	}

	public void setResponseHourDiff(float responseHourDiff) {
		this.responseHourDiff = responseHourDiff;
	}

	public float getReplyHourDiff() {
		return replyHourDiff;
	}

	public void setReplyHourDiff(float replyHourDiff) {
		this.replyHourDiff = replyHourDiff;
	}

	public float getSolveHourDiff() {
		return solveHourDiff;
	}

	public void setSolveHourDiff(float solveHourDiff) {
		this.solveHourDiff = solveHourDiff;
	}

	@Length(min=0, max=10, message="source_host_name长度必须介于 0 和 10 之间")
	public String getSourceHostName() {
		return sourceHostName;
	}

	public void setSourceHostName(String sourceHostName) {
		this.sourceHostName = sourceHostName;
	}
	

	
	@Length(min=0, max=11, message="转长度必须介于 0 和 11 之间")
	public String getRepostsCount() {
		return repostsCount;
	}

	public void setRepostsCount(String repostsCount) {
		this.repostsCount = repostsCount;
	}
	
	@Length(min=0, max=11, message="赞长度必须介于 0 和 11 之间")
	public String getAttitudesCount() {
		return attitudesCount;
	}

	public void setAttitudesCount(String attitudesCount) {
		this.attitudesCount = attitudesCount;
	}
	
	@Length(min=0, max=11, message="评长度必须介于 0 和 11 之间")
	public String getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(String commentsCount) {
		this.commentsCount = commentsCount;
	}
	
}