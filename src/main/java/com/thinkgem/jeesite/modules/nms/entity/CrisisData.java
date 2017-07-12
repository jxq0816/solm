/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.nms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 危机数据Entity
 * @author jiangxingqi
 * @version 2017-01-18
 */
public class CrisisData extends DataEntity<CrisisData> {
	
	private static final long serialVersionUID = 1L;
	private Integer autoId;
	private Office office;//公司
	private String weiBoId;//微博编号
	private String customerId;		// 外键评价人编号
	private String customerName;		// 评价人姓名
	private String content;		// 原文
	private String pageUrl;		// page_url
	private Date commentTime;		// 发文时间
	private String sourceHostName;		// source_host_name
	private String sourceHost;		// 来源
	private Integer crisisLevel;		// 危机级别
	private Integer repostsCount;                            //转发数
	private Integer commentsCount;                           //评论数
	private Integer attitudesCount;						//赞数

	@ExcelField(title="编号", align=2, sort=1)
	public Integer getAutoId() {
		return autoId;
	}

	public void setAutoId(Integer autoId) {
		this.autoId = autoId;
	}

	@ExcelField(title="微博编号", align=15, sort=2)
	public String getWeiBoId() {
		return weiBoId;
	}

	public void setWeiBoId(String weiBoId) {
		this.weiBoId = weiBoId;
	}

	@ExcelField(title="原文", align=50, sort=3)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ExcelField(title="危机级别", align=2, sort=4)
	public Integer getCrisisLevel() {
		return crisisLevel;
	}

	public void setCrisisLevel(Integer crisisLevel) {
		this.crisisLevel = crisisLevel;
	}

	@ExcelField(title="转", align=2, sort=5)
	public Integer getRepostsCount() {
		return repostsCount;
	}

	public void setRepostsCount(Integer repostsCount) {
		this.repostsCount = repostsCount;
	}

	@ExcelField(title="赞", align=2, sort=6)
	public Integer getAttitudesCount() {
		return attitudesCount;
	}

	public void setAttitudesCount(Integer attitudesCount) {
		this.attitudesCount = attitudesCount;
	}

	@ExcelField(title="评", align=2, sort=7)
	public Integer getCommentsCount() {
		return commentsCount;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="时间", align=20, sort=8)
	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public void setCommentsCount(Integer commentsCount) {
		this.commentsCount = commentsCount;
	}

	public CrisisData() {
		super();
	}

	public CrisisData(String id){
		super(id);
	}

	@Length(min=0, max=32, message="外键评价人编号长度必须介于 0 和 32 之间")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	@Length(min=0, max=20, message="评价人姓名长度必须介于 0 和 20 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	
	@Length(min=0, max=255, message="page_url长度必须介于 0 和 255 之间")
	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	

	
	@Length(min=0, max=10, message="source_host_name长度必须介于 0 和 10 之间")
	public String getSourceHostName() {
		return sourceHostName;
	}

	public void setSourceHostName(String sourceHostName) {
		this.sourceHostName = sourceHostName;
	}
	
	@Length(min=0, max=30, message="来源长度必须介于 0 和 30 之间")
	public String getSourceHost() {
		return sourceHost;
	}

	public void setSourceHost(String sourceHost) {
		this.sourceHost = sourceHost;
	}
	


	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}






}