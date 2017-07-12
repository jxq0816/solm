/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.nms.entity.FinalQuantiy;
import com.thinkgem.jeesite.modules.oa.dao.OaNotifyDao;
import com.thinkgem.jeesite.modules.oa.dao.OaNotifyRecordDao;
import com.thinkgem.jeesite.modules.oa.entity.OaNotify;
import com.thinkgem.jeesite.modules.oa.entity.OaNotifyRecord;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 通知通告Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OaNotifyService extends CrudService<OaNotifyDao, OaNotify> {

	@Autowired
	private OaNotifyRecordDao oaNotifyRecordDao;

	private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

	public OaNotify get(String id) {
		OaNotify entity = dao.get(id);
		return entity;
	}
	
	/**
	 * 获取通知发送记录
	 * @param oaNotify
	 * @return
	 */
	public OaNotify getRecordList(OaNotify oaNotify) {
		oaNotify.setOaNotifyRecordList(oaNotifyRecordDao.findList(new OaNotifyRecord(oaNotify)));
		return oaNotify;
	}
	
	public Page<OaNotify> find(Page<OaNotify> page, OaNotify oaNotify) {
		oaNotify.setPage(page);
		page.setList(dao.findList(oaNotify));
		return page;
	}

	/**
	 * 检查是否需要通知
	 * @param oaNotify
	 */
	public Boolean checkMailNotify(OaNotify oaNotify,User receiverDB){
		OaNotifyRecord record=new OaNotifyRecord(oaNotify);
		record.setReadFlag("0");//未读
		record.setUser(receiverDB);
		List<OaNotifyRecord> recordList=oaNotifyRecordDao.findList(record);
		System.out.println(receiverDB.getName()+" "+recordList.size());
		logger.info(receiverDB.getName()+"当前有"+recordList.size()+"个未读消息");
		if(recordList!=null&&recordList.size()!=0){//如果有未读消息，则不必发送通知
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 获取通知数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(OaNotify oaNotify) {
		return dao.findCount(oaNotify);
	}
	
	@Transactional(readOnly = false)
	public void save(OaNotify oaNotify) {
		super.save(oaNotify);
		
		// 更新发送接受人记录
		oaNotifyRecordDao.deleteByOaNotifyId(oaNotify.getId());
		if (oaNotify.getOaNotifyRecordList().size() > 0){
			oaNotifyRecordDao.insertAll(oaNotify.getOaNotifyRecordList());
		}
	}
	
	/**
	 * 更新阅读状态
	 */
	@Transactional(readOnly = false)
	public void updateReadFlag(OaNotify oaNotify) {
		OaNotifyRecord oaNotifyRecord = new OaNotifyRecord(oaNotify);
		oaNotifyRecord.setUser(oaNotifyRecord.getCurrentUser());
		oaNotifyRecord.setReadDate(new Date());
		oaNotifyRecord.setReadFlag("1");
		oaNotifyRecordDao.update(oaNotifyRecord);
	}

	/**
	 * 插入危机数据提醒通知
	 */
	@Transactional(readOnly = false)
	public void insertCrisisNotify(String disposeId,String userId,String type,String title) {
		OaNotify notify=new OaNotify();
		notify.setContent(disposeId);
		notify.setOaNotifyRecordIds(userId);
		notify.setType(type);
		notify.setStatus(FinalQuantiy.publish);
		notify.setTitle(title);
		notify.setCreateBy(new User(FinalQuantiy.adminId));
		notify.setCreateDate(new Date());
		notify.setUpdateBy(new User(FinalQuantiy.adminId));
		notify.setUpdateDate(new Date());
		this.save(notify);
	}
}