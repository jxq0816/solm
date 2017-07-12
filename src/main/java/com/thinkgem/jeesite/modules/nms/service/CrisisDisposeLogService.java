/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.nms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.nms.dao.CrisisDisposeLogDao;
import com.thinkgem.jeesite.modules.nms.entity.CrisisDisposeLog;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 危机数据处理记录Service
 * @author jiangxingqi
 * @version 2017-01-19
 */
@Service
@Transactional(readOnly = true)
public class CrisisDisposeLogService extends CrudService<CrisisDisposeLogDao, CrisisDisposeLog> {

	@Autowired
	private CrisisDisposeLogDao crisisDisposeLogDao;

	public CrisisDisposeLog get(String id) {
		return super.get(id);
	}
	
	public List<CrisisDisposeLog> findList(CrisisDisposeLog crisisDisposeLog) {
		return super.findList(crisisDisposeLog);
	}
	
	public Page<CrisisDisposeLog> findPage(Page<CrisisDisposeLog> page, CrisisDisposeLog crisisDisposeLog) {
		User user= UserUtils.getUser();
		if(user.isAdmin()==false){
			Office office=user.getOffice();
			crisisDisposeLog.setOffice(office);
		}
		return super.findPage(page, crisisDisposeLog);
	}

	public List<CrisisDisposeLog> findCrisisDisposeLogList(CrisisDisposeLog log) {
		return crisisDisposeLogDao.findCrisisDisposeLogList(log);
	}

	@Transactional(readOnly = false)
	public void save(CrisisDisposeLog crisisDisposeLog) {
		super.save(crisisDisposeLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(CrisisDisposeLog crisisDisposeLog) {
		super.delete(crisisDisposeLog);
	}

	@Transactional(readOnly = false)
	public User chooseUser(Office office) {
		CrisisDisposeLog log = new CrisisDisposeLog();
		log.setOffice(office);
		List<CrisisDisposeLog> list = this.findCrisisDisposeLogList(log);
		Double min = null;
		User user;//负责人
		if (list != null && list.size() != 0) {//该部门有人
			user = list.get(0).getUser();
			if (list.get(0).getDisposedCnt() != null && list.get(0).getDisposingCnt() != null) {
				min = list.get(0).getDisposedCnt() * 0.2 + list.get(0).getDisposingCnt() * 0.8;
			}
		} else {
			return null;//分配失败
		}
		for (CrisisDisposeLog i : list) {
			Integer disposedCnt = i.getDisposedCnt();
			Integer disposingCnt = i.getDisposingCnt();
			if (disposedCnt == null && disposingCnt == null) {//从未执行过任务
				user = i.getUser();//有新人
				break;
			} else {
				Double cnt = disposedCnt * 0.2 + disposingCnt * 0.8;
				if (min == null || cnt < min) {
					min = cnt;
					user = i.getUser();//无新人，求业绩最小的人
				}
			}
		}
		return user;
	}

	/**
	 * 分配任务，更新用户当前的任务数
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void updateLogForAssign(User user) {
		//分配任务
		if (user != null && StringUtils.isNoneBlank(user.getId())) {//更新负责人的业绩
			CrisisDisposeLog log = new CrisisDisposeLog();//查询参数
			log.setUser(user);
			List<CrisisDisposeLog> logDBList = this.findList(log);//查询结果
			if(logDBList.size()==0){
				CrisisDisposeLog logDB = new CrisisDisposeLog();
				logDB.setUser(user);
				logDB.setDisposedCnt(0);
				logDB.setDisposingCnt(1);
				this.save(logDB);//新建
			}else{
				for (CrisisDisposeLog logDB : logDBList) {
					if (logDB != null) {
						logDB.setDisposingCnt(logDB.getDisposingCnt() + 1);//正在处理的任务数目加一
						this.save(logDB);//更新
					}
				}
			}
		}
	}
	/**
	 * 客户状态已解决，更新用户当前的任务数
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void updateLogForEnd(User user) {
		CrisisDisposeLog log = new CrisisDisposeLog();
		if (user != null && StringUtils.isNoneBlank(user.getId())) {
			log.setUser(user);
			List<CrisisDisposeLog> list = this.findList(log);//正常情况下list的大小为1
			for (CrisisDisposeLog i : list) {
				Integer disposedCnt = i.getDisposedCnt();
				disposedCnt++;
				i.setDisposedCnt(disposedCnt);//已处理数量加一

				Integer disposingCnt = i.getDisposingCnt();
				disposingCnt--;
				i.setDisposingCnt(disposingCnt);//正在处理数量减一
				this.save(i);
			}
		}
	}
	
}