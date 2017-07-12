/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.nms.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 危机数据处理Entity
 * @author jiangxingqi
 * @version 2017-01-18
 */
public class CrisisDispose extends DataEntity<CrisisDispose> {
	
	private static final long serialVersionUID = 1L;
	private CrisisData crisisData;		// fk_crisis_data_id
	private User user;		// 客服
	private Office office;		// 外键部门编号
	private String actionStatus;		// 动作状态
	private String issueStatus;		// 问题状态
	private String customerStatus;		// 客户状态
	private String reply;		// 回复
	private Date responseTime;//反应时间
	private float responseHourDiff;//反应时长
	private Date replyTime;//回复时间
	private float replyHourDiff;//回复时长
	private Date solveTime;//解决时间
	private float solveHourDiff;//解决时长
	private String timeType;//时间类型

	public CrisisDispose() {
		super();
	}

	public CrisisDispose(String id){
		super(id);
	}

	@ExcelField(title="编号", align=2, sort=1,value="crisisData.autoId")
	public CrisisData getCrisisData() {
		return crisisData;
	}

	@ExcelField(title="原文", align=2, sort=2,value="crisisData.content")
	public CrisisData getCrisisDataContent() {
		return crisisData;
	}

	@ExcelField(title="来源", align=2, sort=3,value="crisisData.sourceHostName")
	public CrisisData getCrisisDataSourceHostName() {
		return crisisData;
	}

	@ExcelField(title="危机级别", align=2, sort=4,value="crisisData.crisisLevel",dictType="crisis_level")
	public CrisisData getCrisisDataCrisisLevel() {
		return crisisData;
	}

	@ExcelField(title="发文时间", align=2, sort=5,value="crisisData.commentTime")
	public CrisisData getCrisisDataCreateAt() {
		return crisisData;
	}

	public void setCrisisData(CrisisData crisisData) {
		this.crisisData = crisisData;
	}

	@ExcelField(title="客服", align=2, sort=3, value="user.name")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=1, message="动作状态长度必须介于 0 和 1 之间")
	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}
	
	@Length(min=0, max=1, message="问题状态长度必须介于 0 和 1 之间")
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

	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}

	@ExcelField(title="回复内容", align=2, sort=8)
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}


	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public Date getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}

	public float getResponseHourDiff() {
		return responseHourDiff;
	}

	public void setResponseHourDiff(float responseHourDiff) {
		this.responseHourDiff = responseHourDiff;
	}

	public Date getSolveTime() {
		return solveTime;
	}

	public void setSolveTime(Date solveTime) {
		this.solveTime = solveTime;
	}

	public float getSolveHourDiff() {
		return solveHourDiff;
	}

	public void setSolveHourDiff(float solveHourDiff) {
		this.solveHourDiff = solveHourDiff;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public float getReplyHourDiff() {
		return replyHourDiff;
	}

	public void setReplyHourDiff(float replyHourDiff) {
		this.replyHourDiff = replyHourDiff;
	}
	
}