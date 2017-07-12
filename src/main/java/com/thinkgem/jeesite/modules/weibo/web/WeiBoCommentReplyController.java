/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoCommentReply;
import com.thinkgem.jeesite.modules.weibo.service.WeiBoCommentReplyService;

/**
 * 微博评论Controller
 * @author jiangxingqi
 * @version 2017-03-06
 */
@Controller
@RequestMapping(value = "${adminPath}/weibo/weiBoCommentReply")
public class WeiBoCommentReplyController extends BaseController {

	@Autowired
	private WeiBoCommentReplyService weiBoCommentReplyService;
	
	@ModelAttribute
	public WeiBoCommentReply get(@RequestParam(required=false) String id) {
		WeiBoCommentReply entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = weiBoCommentReplyService.get(id);
		}
		if (entity == null){
			entity = new WeiBoCommentReply();
		}
		return entity;
	}
	
	@RequiresPermissions("weibo:weiBoCommentReply:view")
	@RequestMapping(value = {"list", ""})
	public String list(WeiBoCommentReply weiBoCommentReply, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WeiBoCommentReply> page = weiBoCommentReplyService.findPage(new Page<WeiBoCommentReply>(request, response), weiBoCommentReply); 
		model.addAttribute("page", page);
		return "modules/weibo/weiBoCommentReplyList";
	}

	@RequiresPermissions("weibo:weiBoCommentReply:view")
	@RequestMapping(value = "form")
	public String form(WeiBoCommentReply weiBoCommentReply, Model model) {
		model.addAttribute("weiBoCommentReply", weiBoCommentReply);
		return "modules/weibo/weiBoCommentReplyForm";
	}

	@RequiresPermissions("weibo:weiBoCommentReply:edit")
	@RequestMapping(value = "save")
	public String save(WeiBoCommentReply weiBoCommentReply, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, weiBoCommentReply)){
			return form(weiBoCommentReply, model);
		}
		weiBoCommentReplyService.save(weiBoCommentReply);
		addMessage(redirectAttributes, "保存微博评论成功");
		return "redirect:"+Global.getAdminPath()+"/weibo/weiBoCommentReply/?repage";
	}
	
	@RequiresPermissions("weibo:weiBoCommentReply:edit")
	@RequestMapping(value = "delete")
	public String delete(WeiBoCommentReply weiBoCommentReply, RedirectAttributes redirectAttributes) {
		weiBoCommentReplyService.delete(weiBoCommentReply);
		addMessage(redirectAttributes, "删除微博评论成功");
		return "redirect:"+Global.getAdminPath()+"/weibo/weiBoCommentReply/?repage";
	}

}