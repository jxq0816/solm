/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.nms.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.nms.entity.CrisisData;
import com.thinkgem.jeesite.modules.nms.entity.FinalQuantiy;
import com.thinkgem.jeesite.modules.nms.service.CrisisDataService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.util.Base64Util;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import weibo4j.model.WeiboException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.thinkgem.jeesite.modules.util.RequestUtil.getRequestPostStr;

/**
 * 危机数据Controller
 *
 * @author jiangxingqi
 * @version 2017-01-17
 */
@Controller
@RequestMapping(value = "${adminPath}/nms/crisisData")
public class CrisisDataController extends BaseController {

    @Autowired
    private CrisisDataService crisisDataService;

    @Autowired
    private OfficeService officeService;

    @ModelAttribute
    public CrisisData get(@RequestParam(required = false) String id) {
        CrisisData entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = crisisDataService.get(id);
        }
        if (entity == null) {
            entity = new CrisisData();
        }
        return entity;
    }

    @RequiresPermissions("nms:crisisData:view")
    @RequestMapping(value = {"list", ""})
    public String list(CrisisData crisisData, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        Page<CrisisData> page = crisisDataService.findPage(new Page<CrisisData>(request, response), crisisData);
        model.addAttribute("query", crisisData);
        model.addAttribute("page", page);
        return "modules/nms/crisisDataList";
    }

    @RequiresPermissions("nms:crisisData:view")
    @RequestMapping(value = "form")
    public String form(CrisisData crisisData, Model model) throws IOException {
        String content = Base64Util.decode(crisisData.getContent());
        crisisData.setContent(content);
        model.addAttribute("crisisData", crisisData);
        return "modules/nms/crisisDataForm";
    }

    @RequiresPermissions("nms:crisisData:edit")
    @RequestMapping(value = "save")
    public String save(CrisisData crisisData, Model model, RedirectAttributes redirectAttributes) throws IOException, weibo4j.org.json.JSONException, JSONException, WeiboException {
        if (!beanValidator(model, crisisData)) {
            return form(crisisData, model);
        }
        String content = Base64Util.encode(crisisData.getContent());
        crisisData.setContent(content);
        String rs = null;
        if (StringUtils.isNoneBlank(crisisData.getId())) {
            crisisDataService.save(crisisData);//update
        } else {
            String url = crisisData.getPageUrl();
            if (StringUtils.isBlank(url)||crisisData.getOffice()==null|| StringUtils.isBlank(crisisData.getOffice().getId())) {
                rs = "url不能为空，部门必须选择";
            } else {
                rs = crisisDataService.insert(crisisData);//insert
            }
        }
        addMessage(redirectAttributes, rs);
        return "redirect:" + Global.getAdminPath() + "/nms/crisisData/?repage";
    }

    @RequiresPermissions("nms:crisisData:edit")
    @RequestMapping(value = "delete")
    public String delete(CrisisData crisisData, RedirectAttributes redirectAttributes) {
        crisisDataService.delete(crisisData);
        addMessage(redirectAttributes, "删除危机数据成功");
        return "redirect:" + Global.getAdminPath() + "/nms/crisisData/?repage";
    }

    @RequestMapping(value = "saveJson")
    @ResponseBody
    public Map saveJson(HttpServletRequest request) throws IOException, WeiboException, JSONException, weibo4j.org.json.JSONException {
        Map map = new HashMap<String, Object>();
        String submitMethod = request.getMethod();
        String data = null;
        if (submitMethod.equals("GET")) {
            String s = request.getQueryString();
            if (StringUtils.isNoneBlank(s)) {
                data = new String(s.getBytes("iso-8859-1"), "utf-8").replaceAll("%22", "\"");
            }
        } else {
            data = getRequestPostStr(request);
        }
        //System.out.println(data);
        //data="{\"id\":\"4073210285170175\",\"text\":\"122\",\"screen_name\":\"孙杨\",\"created_at\":1484705640,\"source_host\":\"weibo.com\",\"userid\":\"1878546883\",\"source_hostname\":\"新浪微博\"}";
        if (StringUtils.isBlank(data)) {
            map.put("data", data);
            map.put("result", "fail");
            return map;
        }
        JSONObject object = JSON.parseObject(data);
        String weiBoId = object.getString("id");//微博编号

        if (crisisDataService.weiBoIdExist(weiBoId) == true) {
            map.put("data", data);
            map.put("result", "fail,微博ID重复或为空");
            return map;
        }


        String sourceHost = object.getString("source_host");//来源URL
        String sourceHostName = object.getString("source_hostname");//来源名称
        String pageUrl = object.getString("page_url");//原文URL
        Integer crisisLevel = object.getInteger("level");//危机级别
        String officeName = object.getString("office_name");//部门名称
        //System.out.println("部门："+officeName);

        CrisisData crisisData = new CrisisData();
        Office office = new Office();
        office.setName(officeName);
        office.setType(FinalQuantiy.department);
        List<Office> list = officeService.findListByCondition(office);
        //System.out.println("部门列表："+list.size());
        if (list != null && list.size() != 0) {
            office = list.get(0);
            crisisData.setWeiBoId(weiBoId);
            crisisData.setOffice(office);//公司默认西门子家电
            crisisData.setCrisisLevel(crisisLevel);
            crisisData.setSourceHost(sourceHost);
            crisisData.setSourceHostName(sourceHostName);
            crisisData.setPageUrl(pageUrl);
            crisisData.setRemarks(data);
            String rs = crisisDataService.insert(crisisData);//入库
            map.put("data", data);
            map.put("result", rs);
        }else{
            map.put("data", data);
            map.put("result", "无对应部门，入库失败");
        }
        return map;
    }

    /**
     * 导出危机数据
     * @param crisisData
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("nms:crisisData:view")
    @RequestMapping(value = "export", method= RequestMethod.POST)
    public String exportFile(CrisisData crisisData,  HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CrisisData> page = crisisDataService.findPage(new Page<CrisisData>(request, response), crisisData);
            ExportExcel exportExcel=new ExportExcel("危机数据", CrisisData.class);
            exportExcel=exportExcel.setDataList(page.getList());
            exportExcel.write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "导出危机数据失败！失败信息："+e.getMessage());
        }
        return "modules/nms/crisisDataList";
    }
}