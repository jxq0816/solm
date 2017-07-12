/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.nms.entity.FinalQuantiy;
import com.thinkgem.jeesite.modules.oa.service.OaNotifyService;
import com.thinkgem.jeesite.modules.weibo.dao.WeiBoCommentReplyDao;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoComment;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoCommentReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微博评论Service
 * @author jiangxingqi
 * @version 2017-03-06
 */
@Service
@Transactional(readOnly = true)
public class WeiBoCommentReplyService extends CrudService<WeiBoCommentReplyDao, WeiBoCommentReply> {

	@Autowired
	private OaNotifyService oaNotifyService;

	public WeiBoCommentReply get(String id) {
		return super.get(id);
	}
	
	public List<WeiBoCommentReply> findList(WeiBoCommentReply weiBoCommentReply) {
		return super.findList(weiBoCommentReply);
	}
	
	public Page<WeiBoCommentReply> findPage(Page<WeiBoCommentReply> page, WeiBoCommentReply weiBoCommentReply) {
		return super.findPage(page, weiBoCommentReply);
	}
	
	@Transactional(readOnly = false)
	public void save(WeiBoCommentReply weiBoCommentReply) {
		super.save(weiBoCommentReply);
	}

	@Transactional(readOnly = false)
	public void insert(WeiBoCommentReply weiBoCommentReply) {
		//收到回复之后，客服发送通知
		WeiBoComment comment=weiBoCommentReply.getWeiBoComment();
		String commentId=comment.getId();//根据weiboId去找对应的危机数据
		HashMap map=new HashMap();
		map.put("commentId",commentId);
		Map rs=dao.findUserByCommentId(map);//获得客服的ID
		String userId=(String) rs.get("userId");
		//String weiBoId=(String) rs.get("weiBoId");
		String disposeId=(String) rs.get("disposeId");
		oaNotifyService.insertCrisisNotify(disposeId,userId, FinalQuantiy.crisisDataStore,"微博回复通知");
		dao.insert(weiBoCommentReply);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeiBoCommentReply weiBoCommentReply) {
		super.delete(weiBoCommentReply);
	}
	
}