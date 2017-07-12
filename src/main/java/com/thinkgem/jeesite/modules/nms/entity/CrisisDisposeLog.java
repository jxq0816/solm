/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.nms.entity;

import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 危机数据处理记录Entity
 * @author jiangxingqi
 * @version 2017-01-19
 */
public class CrisisDisposeLog extends DataEntity<CrisisDisposeLog> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 客服
	private Office office;		// 外键部门编号
	private Integer disposedCnt;		// 已处理数量
	private Integer disposingCnt;		// 正在处理数量
	
	public CrisisDisposeLog() {
		super();
	}

	public CrisisDisposeLog(String id){
		super(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Integer getDisposedCnt() {
		return disposedCnt;
	}

	public void setDisposedCnt(Integer disposedCnt) {
		this.disposedCnt = disposedCnt;
	}

	public Integer getDisposingCnt() {
		return disposingCnt;
	}

	public void setDisposingCnt(Integer disposingCnt) {
		this.disposingCnt = disposingCnt;
	}


	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}