/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.util.Base64Util;
import com.thinkgem.jeesite.modules.util.weibo.request.WeiBoRequestService;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoKeyword;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoStatus;
import com.thinkgem.jeesite.modules.weibo.service.WeiBoKeywordService;
import com.thinkgem.jeesite.modules.weibo.service.WeiBoStatusService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import weibo4j.org.json.JSONException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 微博Controller
 * @author jiangxingqi
 * @version 2017-03-13
 */
@Controller
@RequestMapping(value = "${adminPath}/weibo/weiBoStatus")
public class WeiBoStatusController extends BaseController {

	@Autowired
	private WeiBoRequestService weiBoRequestService;
	@Autowired
	private WeiBoStatusService weiBoStatusService;
	@Autowired
	private WeiBoKeywordService weiBoKeywordService;
	
	@ModelAttribute
	public WeiBoStatus get(@RequestParam(required=false) String id) {
		WeiBoStatus entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = weiBoStatusService.get(id);
		}
		if (entity == null){
			entity = new WeiBoStatus();
		}
		return entity;
	}
	
	@RequiresPermissions("weibo:weiBoStatus:view")
	@RequestMapping(value = {"list", ""})
	public String list(WeiBoStatus weiBoStatus, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WeiBoStatus> page = weiBoStatusService.findPage(new Page<WeiBoStatus>(request, response), weiBoStatus); 
		model.addAttribute("page", page);
		return "modules/weibo/weiBoStatusList";
	}

	/**
	 * 导出危机数据
	 * @param weiBoStatus
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("weibo:weiBoStatus:view")
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportFile(WeiBoStatus weiBoStatus, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<WeiBoStatus> page = weiBoStatusService.findPage(new Page<WeiBoStatus>(request, response), weiBoStatus);
			ExportExcel exportExcel=new ExportExcel("微博数据", WeiBoStatus.class);
			exportExcel=exportExcel.setDataList(page.getList());
			exportExcel.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导出数据失败！失败信息："+e.getMessage());
		}
		return "modules/weibo/weiBoStatusList";
	}

	@RequiresPermissions("weibo:weiBoStatus:view")
	@RequestMapping(value = "form")
	public String form(WeiBoStatus weiBoStatus, Model model) {
		model.addAttribute("weiBoStatus", weiBoStatus);
		return "modules/weibo/weiBoStatusForm";
	}

	@RequiresPermissions("weibo:weiBoStatus:edit")
	@RequestMapping(value = "save")
	public String save(WeiBoStatus weiBoStatus, Model model, RedirectAttributes redirectAttributes) throws JSONException {
		if (!beanValidator(model, weiBoStatus)){
			return form(weiBoStatus, model);
		}
		String id=weiBoStatus.getId();//微博编号
		weiBoStatus=weiBoStatusService.queryWeiBoStatusById(weiBoStatus);
		String msg=weiBoStatusService.insert(weiBoStatus);
		addMessage(redirectAttributes, msg);
		return "redirect:"+Global.getAdminPath()+"/weibo/weiBoStatus/?repage";
	}
	
	@RequiresPermissions("weibo:weiBoStatus:edit")
	@RequestMapping(value = "delete")
	public String delete(WeiBoStatus weiBoStatus, RedirectAttributes redirectAttributes) {
		weiBoStatusService.delete(weiBoStatus);
		addMessage(redirectAttributes, "删除微博成功");
		return "redirect:"+Global.getAdminPath()+"/weibo/weiBoStatus/?repage";
	}

	@RequestMapping(value = "receiveKeyWordMessage")
	@ResponseBody
	public String receiveKeyWordMessage(String message) throws IOException, JSONException {
		String rs="";
		if (StringUtils.isNoneBlank(message)) {
			message = Base64Util.decode(message);
			JSONObject ms = JSON.parseObject(message);
			JSONObject matchInfo = ms.getJSONObject("match_info");
			String keywordString = matchInfo.getString("keyword");
			keywordString=keywordString.substring(1,keywordString.length()-1);
			String[] array=keywordString.split(",");
			for (int i=0;i<array.length;i++) {
				String keyword = array[i].trim();
				WeiBoKeyword weiBoKeyword = new WeiBoKeyword();
				weiBoKeyword.setKeyword(keyword);
				List<WeiBoKeyword> list = weiBoKeywordService.findList(weiBoKeyword);
				if (list != null && list.size() != 0) {
					JSONObject text = ms.getJSONObject("text");//订阅
					JSONObject status = text.getJSONObject("status");
					String statusText = status.getString("text");//微博正文
					String weiBoId = status.getString("id");//微博ID
					JSONObject weiBoUser = status.getJSONObject("user");//消费者
					String weiBoUserId = weiBoUser.getLong("id").toString();//消费者ID
					String screenName = weiBoUser.getString("screen_name");//消费者昵称
					//System.out.println("weiBoUser="+weiBoUser.toString());
					//System.out.println("weiBoUserId="+weiBoUserId);
					Date createAt = new Date(status.getString("created_at"));
					WeiBoStatus weiBo = new WeiBoStatus();
					weiBo.setText(statusText);
					weiBo.setKeyword(keyword);
					weiBo.setWeiboUserId(weiBoUserId);
					weiBo.setScreenName(screenName);
					weiBo.setId(weiBoId);
					weiBo.setCreatedAt(createAt);
					weiBo.setRemarks(status.toString());
					String msg = weiBoStatusService.insert(weiBo);
					rs+= keyword + "订阅微博" + msg;
				}else{
					rs+="本地没有配置该关键字"+keyword;
				}
			}
		} else {
			rs="message is null";
		}
		return rs;
	}
}