/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.web;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.util.weibo.post.WeiBoPostService;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoKeyword;
import com.thinkgem.jeesite.modules.weibo.service.WeiBoKeywordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微博关键字Controller
 * @author jiangxingqi
 * @version 2017-03-14
 */
@Controller
@RequestMapping(value = "${adminPath}/weibo/weiBoKeyword")
public class WeiBoKeywordController extends BaseController {

	@Autowired
	private WeiBoKeywordService weiBoKeywordService;
	@Autowired
	private WeiBoPostService weiBoPostService;
	
	@ModelAttribute
	public WeiBoKeyword get(@RequestParam(required=false) String id) {
		WeiBoKeyword entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = weiBoKeywordService.get(id);
		}
		if (entity == null){
			entity = new WeiBoKeyword();
		}
		return entity;
	}
	
	@RequiresPermissions("weibo:weiBoKeyword:view")
	@RequestMapping(value = {"list", ""})
	public String list(WeiBoKeyword weiBoKeyword, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WeiBoKeyword> page = weiBoKeywordService.findPage(new Page<WeiBoKeyword>(request, response), weiBoKeyword); 
		model.addAttribute("page", page);
		return "modules/weibo/weiBoKeywordList";
	}

	@RequiresPermissions("weibo:weiBoKeyword:view")
	@RequestMapping(value = "form")
	public String form(WeiBoKeyword weiBoKeyword, Model model) {
		model.addAttribute("weiBoKeyword", weiBoKeyword);
		return "modules/weibo/weiBoKeywordForm";
	}

	@RequiresPermissions("weibo:weiBoKeyword:edit")
	@RequestMapping(value = "save")
	public String save(WeiBoKeyword weiBoKeyword, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, weiBoKeyword)){
			return form(weiBoKeyword, model);
		}
		String rs=weiBoKeywordService.insertOrUpdate(weiBoKeyword);
		addMessage(redirectAttributes, rs);
		return "redirect:"+Global.getAdminPath()+"/weibo/weiBoKeyword/?repage";
	}
	
	@RequiresPermissions("weibo:weiBoKeyword:edit")
	@RequestMapping(value = "delete")
	public String delete(WeiBoKeyword weiBoKeyword, RedirectAttributes redirectAttributes) {
		String rs;
		String json=weiBoPostService.updateSubscribeDelKeyWord(weiBoKeyword.getKeyword(),"");
		JSONObject obj=JSONObject.parseObject(json);
		String errorCode=obj.getString("error_code");
		if(StringUtils.isNoneBlank(errorCode)){
			rs=obj.getString("error");
		}else{
			weiBoKeywordService.delete(weiBoKeyword);
			rs="删除微博关键字成功";
		}
		addMessage(redirectAttributes, rs);
		return "redirect:"+Global.getAdminPath()+"/weibo/weiBoKeyword/?repage";
	}

}