/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.web;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.util.weibo.post.WeiBoPostService;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoMail;
import com.thinkgem.jeesite.modules.weibo.entity.WeiboUser;
import com.thinkgem.jeesite.modules.weibo.service.WeiBoMailService;
import com.thinkgem.jeesite.modules.weibo.service.WeiboUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微博私信Controller
 * @author jiangxingqi
 * @version 2017-03-23
 */
@Controller
@RequestMapping(value = "${adminPath}/weibo/weiBoMail")
public class WeiBoMailController extends BaseController {
	@Autowired
	private WeiBoPostService weiBoPostService;
	@Autowired
	private WeiBoMailService weiBoMailService;
	@Autowired
	private WeiboUserService weiboUserService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public WeiBoMail get(@RequestParam(required=false) String id) {
		WeiBoMail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = weiBoMailService.get(id);
		}
		if (entity == null){
			entity = new WeiBoMail();
		}
		return entity;
	}
	
	@RequiresPermissions("weibo:weiBoMail:view")
	@RequestMapping(value = {"chartList", ""})
	public String chartList(WeiBoMail weiBoMail, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user= UserUtils.getUser();
		String weiboUserId=user.getWeiBoUserId();
		weiBoMail.setReceiverId(weiboUserId);
		Page<WeiBoMail> page = weiBoMailService.findFansChartPage(new Page<WeiBoMail>(request, response), weiBoMail);
		model.addAttribute("page", page);
		return "modules/weibo/weiBoMailChartList";
	}

	@RequiresPermissions("weibo:weiBoMail:view")
	@RequestMapping(value = "list")
	public String list(WeiBoMail weiBoMail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WeiBoMail> page = weiBoMailService.findPage(new Page<WeiBoMail>(request, response), weiBoMail);
		model.addAttribute("page", page);
		model.addAttribute("query", weiBoMail);
		return "modules/weibo/weiBoMailList";
	}

	@RequiresPermissions("weibo:weiBoMail:view")
	@RequestMapping(value = "form")
	public String form(WeiBoMail weiBoMail, Model model) {
		model.addAttribute("weiBoMail", weiBoMail);
		return "modules/weibo/weiBoMailForm";
	}

	/**
	 *
	 * @param last =1时表示获取当前最新的对话
	 * @param weiBoMail
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws JSONException
	 * @throws org.json.JSONException
	 * @throws WeiboException
	 */
	@RequiresPermissions("weibo:weiBoMail:view")
	@RequestMapping(value = "chart")
	public String chart(@RequestParam(required=false,defaultValue="0") String last ,WeiBoMail weiBoMail,HttpServletRequest request, HttpServletResponse response,Model model) throws JSONException, org.json.JSONException, WeiboException {
		String fansId=weiBoMail.getSenderId();
		WeiboUser fans=weiboUserService.getLocalAndOnlineUserById(fansId);
		if (fans == null) {
			model.addAttribute("errorMsg", "该用户不存在");
		}
		//收件人为当前登录用户
		if(StringUtils.isBlank(weiBoMail.getReceiverId())){
			User user= UserUtils.getUser();
			String weiBoUserId=user.getWeiBoUserId();
			weiBoMail.setReceiverId(weiBoUserId);
		}

		//收信箱
		Page pageQuery=new Page<WeiBoMail>(request, response);
		Page<WeiBoMail> page = weiBoMailService.findChartPage(pageQuery,weiBoMail);
		if("1".equals(last)){
			int lastPage=page.getTotalPage();
			pageQuery.setPageNo(lastPage);
		}
		page=weiBoMailService.findChartPage(page,weiBoMail);
		model.addAttribute("fans", fans);
		String ip=Global.getConfig("ip");
		String port=Global.getConfig("port");
		model.addAttribute("page", page);
		model.addAttribute("query", weiBoMail);
		model.addAttribute("ip", ip);
		model.addAttribute("port", port);
		return "modules/weibo/weiBoMailChartForm";
	}

	@RequiresPermissions("weibo:weiBoMail:edit")
	@RequestMapping(value = "save")
	public String save(WeiBoMail weiBoMail, Model model, RedirectAttributes redirectAttributes){
		if (!beanValidator(model, weiBoMail)){
			return form(weiBoMail, model);
		}
		weiBoMailService.save(weiBoMail);//入库
		addMessage(redirectAttributes, "保存微博私信成功");
		return "redirect:"+Global.getAdminPath()+"/weibo/weiBoMail/?repage";
	}
	@ResponseBody
	@RequestMapping(value = "chartSave")
	public Map chartSave(WeiBoMail weiBoMail, Model model, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
		weiBoMail.setCreatedAt(new Date());//回复时间
		Map map=new HashMap<String,Object>();
		map.put("text",weiBoMail.getText());
		String jsonObject= JSON.toJSONString(map);
		User user=UserUtils.getUser();
		String token=systemService.getAccessToken(user.getId());
		weiBoPostService.messagesReply(weiBoMail.getReceiverId(),jsonObject,token);//调用新浪微博回复接口
		weiBoMailService.save(weiBoMail);//入库
		Map rs=new HashMap<String,Object>();
		rs.put("info","回复成功");
		rs.put("senderId",weiBoMail.getReceiverId());
		rs.put("senderName",weiBoMail.getReceiverName());
		rs.put("receiverId",weiBoMail.getSenderId());
		rs.put("receiverName",weiBoMail.getSenderName());
		return rs;
	}
	
	@RequiresPermissions("weibo:weiBoMail:edit")
	@RequestMapping(value = "delete")
	public String delete(WeiBoMail weiBoMail, RedirectAttributes redirectAttributes) {
		weiBoMailService.delete(weiBoMail);
		addMessage(redirectAttributes, "删除微博私信成功");
		return "redirect:"+Global.getAdminPath()+"/weibo/weiBoMail/?repage";
	}

}