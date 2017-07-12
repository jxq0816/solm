/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.nms.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.nms.entity.CrisisData;
import com.thinkgem.jeesite.modules.nms.entity.CrisisDispose;
import com.thinkgem.jeesite.modules.nms.entity.FinalQuantiy;
import com.thinkgem.jeesite.modules.nms.service.CrisisDataService;
import com.thinkgem.jeesite.modules.nms.service.CrisisDisposeService;
import com.thinkgem.jeesite.modules.util.Base64Util;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoComment;
import com.thinkgem.jeesite.modules.weibo.entity.WeiboUser;
import com.thinkgem.jeesite.modules.weibo.service.WeiBoCommentService;
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
import java.util.Map;

/**
 * 危机数据处理Controller
 *
 * @author jiangxingqi
 * @version 2017-01-18
 */
@Controller
@RequestMapping(value = "${adminPath}/nms/crisisDispose")
public class CrisisDisposeController extends BaseController {
    @Autowired
    private CrisisDisposeService crisisDisposeService;
    @Autowired
    private CrisisDataService crisisDataService;
    @Autowired
    private WeiboUserService weiboUserService;
    @Autowired
    private WeiBoCommentService weiBoCommentService;


    @ModelAttribute
    public CrisisDispose get(@RequestParam(required = false) String id) {
        CrisisDispose entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = crisisDisposeService.get(id);
        }
        if (entity == null) {
            entity = new CrisisDispose();
        }
        return entity;
    }

    //数据统计
    @RequiresPermissions("nms:crisisDispose:view")
    @RequestMapping(value = "countNum")
    public String countNum(CrisisDispose crisisDispose, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Map<String, Object>> list = crisisDisposeService.countNum(crisisDispose);
        List<Map<String, Object>> officeList = crisisDisposeService.countNumGroupByOffice(crisisDispose);
        model.addAttribute("list", list);
        model.addAttribute("officeList", officeList);
        model.addAttribute("crisisDispose", crisisDispose);
        return "modules/nms/crisisDisposeCnt";
    }

    //月统计
    @RequiresPermissions("nms:crisisDispose:view")
    @RequestMapping(value = "monthCnt")
    public String monthCnt(CrisisDispose crisisDispose, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Map<String, Object>> list = crisisDisposeService.monthCnt(crisisDispose);
        List<Map<String, Object>> officeList = crisisDisposeService.monthCntGroupByOffice(crisisDispose);
        model.addAttribute("list", list);
        model.addAttribute("officeList", officeList);
        model.addAttribute("crisisDispose", crisisDispose);
        return "modules/nms/crisisDisposeMonthCnt";
    }

    @RequiresPermissions("nms:crisisDispose:view")
    @RequestMapping(value = {"list", ""})
    public String list(CrisisDispose crisisDispose, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CrisisDispose> page = crisisDisposeService.findPage(new Page<CrisisDispose>(request, response), crisisDispose);
        model.addAttribute("page", page);
        model.addAttribute("query", crisisDispose);//数据回传
        return "modules/nms/crisisDisposeList";
    }

    @RequiresPermissions("nms:crisisDispose:view")
    @RequestMapping(value = "form")
    public String form(CrisisDispose crisisDispose, Model model) {
        model.addAttribute("crisisDispose", crisisDispose);
        return "modules/nms/crisisDisposeForm";
    }

    @RequiresPermissions("nms:crisisDispose:view")
    @RequestMapping(value = "updateLevelForm")
    public String updateLevelForm(CrisisDispose crisisDispose, Model model) {
        model.addAttribute("crisisDispose", crisisDispose);
        return "modules/nms/crisisDisposeAdjust";
    }

    @RequiresPermissions("nms:crisisDispose:edit")
    @RequestMapping(value = "updateLevel")
    public String updateLevel(CrisisDispose crisisDispose, Model model) {
        model.addAttribute("crisisDispose", crisisDispose);
        CrisisData data = crisisDispose.getCrisisData();
        int level = data.getCrisisLevel();
        data = crisisDataService.get(data);
        data.setCrisisLevel(level);
        crisisDataService.save(data);
        if (level == 0) {
            crisisDispose.setIssueStatus(FinalQuantiy.solved);
        }
        crisisDispose.setActionStatus(FinalQuantiy.solved);
        crisisDisposeService.save(crisisDispose);
        return "redirect:" + Global.getAdminPath() + "/nms/crisisDispose/list";
    }

    @RequiresPermissions("nms:crisisDispose:view")
    @RequestMapping(value = "replyForm")
    public String replyForm(CrisisDispose crisisDispose, Model model) throws IOException, JSONException, WeiboException, weibo4j.org.json.JSONException {

        CrisisData data = crisisDispose.getCrisisData();
        data=crisisDataService.get(data);

        String customerId = data.getCustomerId();//微博用户的编号
        WeiboUser weiboUser=weiboUserService.getLocalAndOnlineUserById(customerId);
        if(weiboUser==null){
            model.addAttribute("errorMsg", "该用户不存在");
        }
        model.addAttribute("customer", weiboUser);

        String content = data.getContent();
        content = Base64Util.decode(content);
        data.setContent(content);
        crisisDispose.setCrisisData(data);//转码

        crisisDisposeService.saveResponseTime(crisisDispose);//保存响应时间
        WeiBoComment weiBoComment=new WeiBoComment();
        String weiboId=data.getWeiBoId();
        weiBoComment.setWeiBoId(weiboId);
        List<WeiBoComment> commentList = weiBoCommentService.findList(weiBoComment);
        commentList=weiBoCommentService.findCommentAndReplyList(commentList);
        model.addAttribute("data", data);
        model.addAttribute("crisisDispose", crisisDispose);
        model.addAttribute("commentList", commentList);
        return "modules/nms/crisisDisposeReplyForm";
    }

    //回复保存
    @RequiresPermissions("nms:crisisDispose:edit")
    @RequestMapping(value = "replySave")
    public String replySave(CrisisDispose crisisDispose, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, crisisDispose)) {
            return form(crisisDispose, model);
        }
        String rs=crisisDisposeService.replySave(crisisDispose);//更新状态
        addMessage(redirectAttributes, rs);
        return "redirect:" + Global.getAdminPath() + "/nms/crisisDispose/?repage";
    }

    //上报
    @RequiresPermissions("nms:crisisDispose:edit")
    @RequestMapping(value = "report")
    public String report(CrisisDispose crisisDispose, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, crisisDispose)) {
            return form(crisisDispose, model);
        }
        crisisDisposeService.report(crisisDispose);//上报
        addMessage(redirectAttributes, "保存危机数据处理成功");
        return "redirect:" + Global.getAdminPath() + "/nms/crisisDispose/?repage";
    }

    //更新问题状态
    @RequiresPermissions("nms:crisisDispose:edit")
    @RequestMapping(value = "update")
    public String update(CrisisDispose crisisDispose, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, crisisDispose)) {
            return form(crisisDispose, model);
        }
        crisisDisposeService.update(crisisDispose);//更新状态
        addMessage(redirectAttributes, "保存危机数据处理成功");
        return "redirect:" + Global.getAdminPath() + "/nms/crisisDispose/?repage";
    }

    @RequiresPermissions("nms:crisisDispose:edit")
    @RequestMapping(value = "delete")
    public String delete(CrisisDispose crisisDispose, RedirectAttributes redirectAttributes) {
        crisisDisposeService.delete(crisisDispose);
        addMessage(redirectAttributes, "删除危机数据处理成功");
        return "redirect:" + Global.getAdminPath() + "/nms/crisisDispose/?repage";
    }

    /**
     * 危机数据导出
     * @param crisisDispose
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("nms:crisisDispose:view")
    @RequestMapping(value = "export", method= RequestMethod.POST)
    public String exportFile(CrisisDispose crisisDispose, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "crisisDispose"+ DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CrisisDispose> page = crisisDisposeService.findPage(new Page<CrisisDispose>(request, response), crisisDispose);
            ExportExcel exportExcel=new ExportExcel("危机数据处理", CrisisDispose.class);
            exportExcel=exportExcel.setDataList(page.getList());
            exportExcel.write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "导出危机处理数据失败！失败信息："+e.getMessage());
        }
        return "modules/nms/crisisDisposeList";
    }

}