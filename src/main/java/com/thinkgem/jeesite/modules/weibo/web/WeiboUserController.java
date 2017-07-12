/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.weibo.entity.WeiboUser;
import com.thinkgem.jeesite.modules.weibo.service.WeiboUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import weibo4j.model.WeiboException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微博用户Controller
 * @author jiangxingqi
 * @version 2017-03-07
 */
@Controller
@RequestMapping(value = "${adminPath}/weibo/weiboUser")
public class WeiboUserController extends BaseController {

	@Autowired
	private WeiboUserService weiboUserService;
	
	@ModelAttribute
	public WeiboUser get(@RequestParam(required=false) String id) {
		WeiboUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = weiboUserService.get(id);
		}
		if (entity == null){
			entity = new WeiboUser();
		}
		return entity;
	}
	
	@RequiresPermissions("weibo:weiboUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(WeiboUser weiboUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WeiboUser> page = weiboUserService.findPage(new Page<WeiboUser>(request, response), weiboUser); 
		model.addAttribute("page", page);
		return "modules/weibo/weiboUserList";
	}

	@RequiresPermissions("weibo:weiboUser:view")
	@RequestMapping(value = "form")
	public String form(WeiboUser weiboUser, Model model) {
		model.addAttribute("weiboUser", weiboUser);
		return "modules/weibo/weiboUserForm";
	}

	@RequiresPermissions("weibo:weiboUser:edit")
	@RequestMapping(value = "save")
	public String save(WeiboUser weiboUser, Model model, RedirectAttributes redirectAttributes) throws JSONException, weibo4j.org.json.JSONException, WeiboException {
		if (!beanValidator(model, weiboUser)){
			return form(weiboUser, model);
		}
		//weiboUserService.save(weiboUser);
		String weiBoId=weiboUser.getId();
		String msg=null;
		if(weiboUserService.checkExist(weiBoId)){
			msg="该用户已经存在";
		}else{
			weiboUserService.insert(weiBoId);
			msg="保存微博用户成功";
		}
		addMessage(redirectAttributes, msg);
		return "redirect:"+Global.getAdminPath()+"/weibo/weiboUser/?repage";
	}
	
	@RequiresPermissions("weibo:weiboUser:edit")
	@RequestMapping(value = "delete")
	public String delete(WeiboUser weiboUser, RedirectAttributes redirectAttributes) {
		weiboUserService.delete(weiboUser);
		addMessage(redirectAttributes, "删除微博用户成功");
		return "redirect:"+Global.getAdminPath()+"/weibo/weiboUser/?repage";
	}

}