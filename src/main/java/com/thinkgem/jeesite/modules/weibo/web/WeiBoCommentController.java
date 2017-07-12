/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.nms.entity.CrisisData;
import com.thinkgem.jeesite.modules.nms.service.CrisisDataService;
import com.thinkgem.jeesite.modules.util.Base64Util;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoComment;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoCommentReply;
import com.thinkgem.jeesite.modules.weibo.service.WeiBoCommentReplyService;
import com.thinkgem.jeesite.modules.weibo.service.WeiBoCommentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 微博评论Controller
 * @author jiangxingqi
 * @version 2017-03-06
 */
@Controller
@RequestMapping(value = "${adminPath}/weibo/weiBoComment")
public class WeiBoCommentController extends BaseController {

	@Autowired
	private WeiBoCommentService weiBoCommentService;
	@Autowired
	private CrisisDataService crisisDataService;
	@Autowired
	private WeiBoCommentReplyService weiBoCommentReplyService;
	
	@ModelAttribute
	public WeiBoComment get(@RequestParam(required=false) String id) {
		WeiBoComment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = weiBoCommentService.get(id);
		}
		if (entity == null){
			entity = new WeiBoComment();
		}
		return entity;
	}
	
	@RequiresPermissions("weibo:weiBoComment:view")
	@RequestMapping(value = {"list", ""})
	public String list(WeiBoComment weiBoComment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WeiBoComment> page = weiBoCommentService.findPage(new Page<WeiBoComment>(request, response), weiBoComment); 
		model.addAttribute("page", page);
		return "modules/weibo/weiBoCommentList";
	}

	@RequiresPermissions("weibo:weiBoComment:view")
	@RequestMapping(value = "form")
	public String form(WeiBoComment weiBoComment, Model model) {
		model.addAttribute("weiBoComment", weiBoComment);
		return "modules/weibo/weiBoCommentForm";
	}

	@RequiresPermissions("weibo:weiBoComment:edit")
	@RequestMapping(value = "save")
	public String save(WeiBoComment weiBoComment, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, weiBoComment)){
			return form(weiBoComment, model);
		}
		weiBoCommentService.save(weiBoComment);
		addMessage(redirectAttributes, "保存微博评论成功");
		return "redirect:"+Global.getAdminPath()+"/weibo/weiBoComment/?repage";
	}
	
	@RequiresPermissions("weibo:weiBoComment:edit")
	@RequestMapping(value = "delete")
	public String delete(WeiBoComment weiBoComment, RedirectAttributes redirectAttributes) {
		weiBoCommentService.delete(weiBoComment);
		addMessage(redirectAttributes, "删除微博评论成功");
		return "redirect:"+Global.getAdminPath()+"/weibo/weiBoComment/?repage";
	}

	@RequestMapping(value = "receiveMessage")
	@ResponseBody
	public String receiveMessage(String message) throws IOException {
		if(StringUtils.isNoneBlank(message)){
			message= Base64Util.decode(message);
			JSONObject rs= JSON.parseObject(message);
			JSONObject text = rs.getJSONObject("text");
			JSONObject comment = text.getJSONObject("comment");//评论对象

			JSONObject status = comment.getJSONObject("status");//原文
			JSONObject weiBoUser = status.getJSONObject("user");//消费者
			String weiBoUserId=weiBoUser.getString("id");//消费者ID
			String weiBoId = status.getString("id");//微博ID
			CrisisData data=new CrisisData();
			data.setWeiBoId(weiBoId);
			Boolean bool = crisisDataService.weiBoIdExist(weiBoId);//微博是否存在

			if(bool==true){//微博已存在
				String id=comment.getString("id");//评论或回复Id
				JSONObject replyComment = comment.getJSONObject("reply_comment");//评论回复
				String commentText=comment.getString("text");//评论或回复的内容
				JSONObject commentUser=comment.getJSONObject("user");//评论或回复人
				String commentUserId=commentUser.getString("id");//评论或回复人ID
				Date createAt=new Date(comment.getString("created_at"));
				if(replyComment==null){//改条评论是对原文的评论
					WeiBoComment commentDB=new WeiBoComment();
					commentDB.setId(id);
					commentDB.setWeiBoId(weiBoId);
					commentDB.setWeiBoUserId(weiBoUserId);//评论人微博编号
					commentDB.setCommentUserId(commentUserId);//评论人ID
					commentDB.setCommentText(commentText);//评的啥
					commentDB.setCreatedAt(createAt);
					commentDB.setRemarks(comment.toString());
					weiBoCommentService.insert(commentDB);
					return "success";
				}else{//是对评论的回复
					String commentId=replyComment.getString("id");//回复的是那条评论
					WeiBoComment commentDB = weiBoCommentService.get(commentId);//判断评论是否存在
					if(commentDB!=null){
						WeiBoCommentReply reply=new WeiBoCommentReply();
						reply.setId(id);
						reply.setWeiBoComment(commentDB);
						reply.setReplyUserId(commentUserId);
						reply.setCreatedAt(createAt);
						reply.setReplyText(commentText);
						weiBoCommentReplyService.insert(reply);
					}
					return "success";
				}
			}else{
				return "weibo is not exist";
			}
		}else {
			return "message is null";
		}
	}

}