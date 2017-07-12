/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.nms.entity.FinalQuantiy;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoComment;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoStatus;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoStatusDispose;
import com.thinkgem.jeesite.modules.weibo.entity.WeiboUser;
import com.thinkgem.jeesite.modules.weibo.service.WeiBoCommentService;
import com.thinkgem.jeesite.modules.weibo.service.WeiBoStatusDisposeService;
import com.thinkgem.jeesite.modules.weibo.service.WeiBoStatusService;
import com.thinkgem.jeesite.modules.weibo.service.WeiboUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import weibo4j.model.WeiboException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 微博数据处理Controller
 *
 * @author jiangxingqi
 * @version 2017-04-06
 */
@Controller
@RequestMapping(value = "${adminPath}/weibo/weiBoStatusDispose")
public class WeiBoStatusDisposeController extends BaseController {

    @Autowired
    private WeiBoStatusService weiBoStatusService;

    @Autowired
    private WeiBoStatusDisposeService weiBoStatusDisposeService;

    @Autowired
    private WeiboUserService weiboUserService;

    @Autowired
    private WeiBoCommentService weiBoCommentService;

    @ModelAttribute
    public WeiBoStatusDispose get(@RequestParam(required = false) String id) {
        WeiBoStatusDispose entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = weiBoStatusDisposeService.get(id);
        }
        if (entity == null) {
            entity = new WeiBoStatusDispose();
        }
        return entity;
    }

    @RequiresPermissions("weibo:weiBoStatusDispose:view")
    @RequestMapping(value = {"list", ""})
    public String list(WeiBoStatusDispose weiBoStatusDispose, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<WeiBoStatusDispose> page = weiBoStatusDisposeService.findPage(new Page<WeiBoStatusDispose>(request, response), weiBoStatusDispose);
        model.addAttribute("page", page);
        model.addAttribute("query", weiBoStatusDispose);
        return "modules/weibo/weiBoStatusDisposeList";
    }

    @RequiresPermissions("weibo:weiBoStatusDispose:view")
    @RequestMapping(value = "form")
    public String form(WeiBoStatusDispose weiBoStatusDispose, Model model) {
        model.addAttribute("weiBoStatusDispose", weiBoStatusDispose);
        return "modules/weibo/weiBoStatusDisposeForm";
    }

    @RequiresPermissions("weibo:weiBoStatusDispose:edit")
    @RequestMapping(value = "save")
    public String save(WeiBoStatusDispose weiBoStatusDispose, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, weiBoStatusDispose)) {
            return form(weiBoStatusDispose, model);
        }
        weiBoStatusDisposeService.save(weiBoStatusDispose);
        addMessage(redirectAttributes, "保存微博数据处理成功");
        return "redirect:" + Global.getAdminPath() + "/weibo/weiBoStatusDispose/?repage";
    }

    //更新问题状态
    @RequiresPermissions("weibo:weiBoStatusDispose:edit")
    @RequestMapping(value = "update")
    public String update(WeiBoStatusDispose weiBoStatusDispose, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, weiBoStatusDispose)) {
            return form(weiBoStatusDispose, model);
        }
        weiBoStatusDisposeService.update(weiBoStatusDispose);//更新状态
        addMessage(redirectAttributes, "保存微博数据处理成功");
        return "redirect:" + Global.getAdminPath() + "/weibo/weiBoStatusDispose/?repage";
    }

    @RequiresPermissions("weibo:weiBoStatusDispose:edit")
    @RequestMapping(value = "delete")
    public String delete(WeiBoStatusDispose weiBoStatusDispose, RedirectAttributes redirectAttributes) {
        weiBoStatusDisposeService.delete(weiBoStatusDispose);
        addMessage(redirectAttributes, "删除微博数据处理成功");
        return "redirect:" + Global.getAdminPath() + "/weibo/weiBoStatusDispose/?repage";
    }

    @RequiresPermissions("weibo:weiBoStatusDispose:view")
    @RequestMapping(value = "replyForm")
    public String replyForm(WeiBoStatusDispose weiBoStatusDispose, Model model) throws JSONException, weibo4j.org.json.JSONException, WeiboException, IOException {

        WeiBoStatus status = weiBoStatusDispose.getWeiBoStatus();
        status = weiBoStatusService.get(status);
        String customerId = status.getWeiboUserId();//微博用户的编号
        WeiboUser weiboUser = weiboUserService.getLocalAndOnlineUserById(customerId);
        if (weiboUser == null) {
            model.addAttribute("errorMsg", "该用户不存在 微博id="+customerId);
        }
        model.addAttribute("customer", weiboUser);
        weiBoStatusDispose.setWeiBoStatus(status);

        weiBoStatusDisposeService.saveResponseTime(weiBoStatusDispose);//保存响应时间
        WeiBoComment weiBoComment = new WeiBoComment();
        String weiboId = status.getId();
        weiBoComment.setWeiBoId(weiboId);
        List<WeiBoComment> commentList = weiBoCommentService.findList(weiBoComment);
        commentList=weiBoCommentService.findCommentAndReplyList(commentList);
        model.addAttribute("data", status);
        model.addAttribute("weiBoStatusDispose", weiBoStatusDispose);
        model.addAttribute("commentList", commentList);
        return "modules/weibo/weiBoDisposeReplyForm";
    }


    //回复保存
    @RequiresPermissions("weibo:weiBoStatusDispose:edit")
    @RequestMapping(value = "replySave")
    public String replySave(WeiBoStatusDispose weiBoStatusDispose, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, weiBoStatusDispose)) {
            return form(weiBoStatusDispose, model);
        }
        String rs = weiBoStatusDisposeService.replySave(weiBoStatusDispose);//更新状态
        addMessage(redirectAttributes, rs);
        return "redirect:" + Global.getAdminPath() + "/weibo/weiBoStatusDispose/?repage";
    }

    @RequiresPermissions("weibo:weiBoStatusDispose:view")
    @RequestMapping(value = "updateLevelForm")
    public String updateLevelForm(WeiBoStatusDispose weiBoStatusDispose, Model model) {
        model.addAttribute("weiBoStatusDispose", weiBoStatusDispose);
        return "modules/weibo/weiBoDisposeAdjust";
    }

    @RequiresPermissions("weibo:weiBoStatusDispose:edit")
    @RequestMapping(value = "updateLevel")
    public String updateLevel(WeiBoStatusDispose weiBoStatusDispose, Model model) {
        model.addAttribute("weiBoStatusDispose", weiBoStatusDispose);
        String level=weiBoStatusDispose.getCrisisLevel();
        if ("0".equals(level)) {
            weiBoStatusDispose.setIssueStatus(FinalQuantiy.solved);
        }
        weiBoStatusDispose.setActionStatus(FinalQuantiy.solved);
        weiBoStatusDisposeService.save(weiBoStatusDispose);
        return "redirect:" + Global.getAdminPath() + "/weibo/weiBoStatusDispose/list";
    }

    /**
     * 导出微博数据
     * @param weiBoStatusDispose
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("weibo:weiBoStatusDispose:view")
    @RequestMapping(value = "export", method= RequestMethod.POST)
    public String exportFile(WeiBoStatusDispose weiBoStatusDispose, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "weiBoStatusDispose"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WeiBoStatusDispose> page = weiBoStatusDisposeService.findPage(new Page<WeiBoStatusDispose>(request, response), weiBoStatusDispose);
            ExportExcel exportExcel=new ExportExcel("微博数据", WeiBoStatusDispose.class);
            exportExcel=exportExcel.setDataList(page.getList());
            exportExcel.write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "导出微博处理数据失败！失败信息："+e.getMessage());
        }
        return "modules/weibo/weiBoStatusDisposeList";
    }
}