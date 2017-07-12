/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.service.OaNotifyService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.weibo.dao.WeiBoMailDao;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 微博私信Service
 * @author jiangxingqi
 * @version 2017-03-23
 */
@Service
@Transactional(readOnly = true)
public class WeiBoMailService extends CrudService<WeiBoMailDao, WeiBoMail> {

	@Autowired
	public SystemService systemService;
	@Autowired
	public OaNotifyService oaNotifyService;

	public WeiBoMail get(String id) {
		return super.get(id);
	}
	
	public List<WeiBoMail> findList(WeiBoMail weiBoMail) {
		return super.findList(weiBoMail);
	}

	public Page<WeiBoMail> findChartPage(Page<WeiBoMail> page,WeiBoMail weiBoMail) {
		weiBoMail.setPage(page);
		List<WeiBoMail> list = dao.findChartList(weiBoMail);
		page.setList(list);
		return page;
	}

	public Page<WeiBoMail> findFansChartPage(Page<WeiBoMail> page, WeiBoMail weiBoMail) {
		weiBoMail.setPage(page);
		List<WeiBoMail> list=dao.findFansChartList(weiBoMail);
		page.setList(list);
		return page;
	}

	public Page<WeiBoMail> findPage(Page<WeiBoMail> page, WeiBoMail weiBoMail) {
		return super.findPage(page, weiBoMail);
	}
	
	@Transactional(readOnly = false)
	public void save(WeiBoMail weiBoMail) {
		super.save(weiBoMail);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeiBoMail weiBoMail) {
		super.delete(weiBoMail);
	}
	
}