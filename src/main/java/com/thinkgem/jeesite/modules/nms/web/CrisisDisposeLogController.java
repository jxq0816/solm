/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.nms.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.nms.entity.CrisisDisposeLog;
import com.thinkgem.jeesite.modules.nms.service.CrisisDisposeLogService;
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
 * 危机数据处理记录Controller
 * @author jiangxingqi
 * @version 2017-01-19
 */
@Controller
@RequestMapping(value = "${adminPath}/nms/crisisDisposeLog")
public class CrisisDisposeLogController extends BaseController {

	@Autowired
	private CrisisDisposeLogService crisisDisposeLogService;
	
	@ModelAttribute
	public CrisisDisposeLog get(@RequestParam(required=false) String id) {
		CrisisDisposeLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = crisisDisposeLogService.get(id);
		}
		if (entity == null){
			entity = new CrisisDisposeLog();
		}
		return entity;
	}
	
	@RequiresPermissions("nms:crisisDisposeLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(CrisisDisposeLog crisisDisposeLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CrisisDisposeLog> page = crisisDisposeLogService.findPage(new Page<CrisisDisposeLog>(request, response), crisisDisposeLog); 
		model.addAttribute("page", page);
		return "modules/nms/crisisDisposeLogList";
	}

	@RequiresPermissions("nms:crisisDisposeLog:view")
	@RequestMapping(value = "form")
	public String form(CrisisDisposeLog crisisDisposeLog, Model model) {
		model.addAttribute("crisisDisposeLog", crisisDisposeLog);
		return "modules/nms/crisisDisposeLogForm";
	}

	@RequiresPermissions("nms:crisisDisposeLog:edit")
	@RequestMapping(value = "save")
	public String save(CrisisDisposeLog crisisDisposeLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, crisisDisposeLog)){
			return form(crisisDisposeLog, model);
		}
		crisisDisposeLogService.save(crisisDisposeLog);
		addMessage(redirectAttributes, "保存危机数据处理记录成功");
		return "redirect:"+Global.getAdminPath()+"/nms/crisisDisposeLog/?repage";
	}
	
	@RequiresPermissions("nms:crisisDisposeLog:edit")
	@RequestMapping(value = "delete")
	public String delete(CrisisDisposeLog crisisDisposeLog, RedirectAttributes redirectAttributes) {
		crisisDisposeLogService.delete(crisisDisposeLog);
		addMessage(redirectAttributes, "删除危机数据处理记录成功");
		return "redirect:"+Global.getAdminPath()+"/nms/crisisDisposeLog/?repage";
	}

}