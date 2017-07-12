/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.weibo.dao.WeiBoCommentDao;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoComment;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoCommentReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 微博评论Service
 * @author jiangxingqi
 * @version 2017-03-02
 */
@Service
@Transactional(readOnly = true)
public class WeiBoCommentService extends CrudService<WeiBoCommentDao, WeiBoComment> {
	@Autowired
	private WeiBoCommentReplyService weiBoCommentReplyService;

	public WeiBoComment get(String id) {
		return super.get(id);
	}
	
	public List<WeiBoComment> findList(WeiBoComment weiBoComment) {
		return super.findList(weiBoComment);
	}

	public List<WeiBoComment> findCommentAndReplyList( List<WeiBoComment> commentList) {
		for(WeiBoComment i:commentList){
			WeiBoCommentReply reply=new WeiBoCommentReply();
			reply.setWeiBoComment(i);
			List<WeiBoCommentReply> list = weiBoCommentReplyService.findList(reply);
			i.setReplyList(list);
		}
		return commentList;
	}
	
	public Page<WeiBoComment> findPage(Page<WeiBoComment> page, WeiBoComment weiBoComment) {
		return super.findPage(page, weiBoComment);
	}
	
	@Transactional(readOnly = false)
	public void save(WeiBoComment weiBoComment) {
		super.save(weiBoComment);
	}

	@Transactional(readOnly = false)
	public void insert(WeiBoComment weiBoComment) {
		dao.insert(weiBoComment);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeiBoComment weiBoComment) {
		super.delete(weiBoComment);
	}
	
}