/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.nms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.nms.dao.CrisisDataDao;
import com.thinkgem.jeesite.modules.nms.entity.CrisisData;
import com.thinkgem.jeesite.modules.nms.entity.CrisisDispose;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.util.Base64Util;
import com.thinkgem.jeesite.modules.util.weibo.request.WeiBoRequestService;
import com.thinkgem.jeesite.modules.weibo.service.WeiboUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 危机数据Service
 *
 * @author jiangxingqi
 * @version 2017-01-18
 */
@Service
@Transactional(readOnly = true)
public class CrisisDataService extends CrudService<CrisisDataDao, CrisisData> {

    @Autowired
    private CrisisDisposeService disposeService;
    @Autowired
    private CrisisDisposeLogService crisisDisposeLogService;
    @Autowired
    private WeiBoRequestService weiBoRequestService;
    @Autowired
    private WeiboUserService weiboUserService;

    public CrisisData get(String id) {
        return super.get(id);
    }

    public List<CrisisData> findList(CrisisData crisisData) {
        return super.findList(crisisData);
    }

    public Page<CrisisData> findPage(Page<CrisisData> page, CrisisData crisisData) {
        User user = UserUtils.getUser();
        if (user.isAdmin() == false) {
            Office office = user.getOffice();
            crisisData.setOffice(office);
        }
        crisisData.setPage(page);
        List<CrisisData> list = this.findList(crisisData);
        for (CrisisData i : list) {
            String content = i.getContent();
            Base64Util base64Util = new Base64Util();
            try {
                content = base64Util.decode(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
            i.setContent(content);
        }
        page.setList(list);
        return page;
    }

    /**
     * php 入库
     *
     * @param crisisData
     */
    @Transactional(readOnly = false)
    public String insert(CrisisData crisisData) throws JSONException, UnsupportedEncodingException, org.json.JSONException, WeiboException {

        String weiBoId = crisisData.getWeiBoId();
        if (StringUtils.isBlank(weiBoId)) {
            String url = crisisData.getPageUrl();
            int start = url.lastIndexOf("/");
            int end = url.lastIndexOf("?");
            String mid = url.substring(start + 1, end);
            weiBoId = WeiBoRequestService.queryIdByMid(mid, "");
        }
        Boolean bool = this.weiBoIdExist(weiBoId);
        if (bool == true) {
            return "微博不存在或者已经入库";
        }
        //危机数据保存
        crisisData.preInsert();
        crisisData.setWeiBoId(weiBoId);
        if (crisisData.getCrisisLevel() == null) {
            crisisData.setCrisisLevel(1);
        }
        Map map = weiBoRequestService.statusesShow(weiBoId, "");
        String error= (String) map.get("error");
        if (StringUtils.isNoneBlank(error)) {
            return error;
        }

        JSONObject status = (JSONObject)map.get("status");

        String deleted = status.getString("deleted");//删除标记
        String content = status.getString("text");//原文

        if ("1".equals(deleted)) {
            return content;
        }
        content = Base64Util.encode(content);
        crisisData.setContent(content);

        JSONObject user = status.getJSONObject("user");//消费者
        String id = user.getString("id");
        if(weiboUserService.checkExist(id)==false){
            weiboUserService.insert(id);//将微博用户信息保存本地
        }
        crisisData.setCustomerId(id);
        String screenName = user.getString("screen_name");
        crisisData.setCustomerName(screenName);

        String createdAt = status.getString("created_at");//发文时间
        Date createdAtTime = new Date(createdAt);
        crisisData.setCommentTime(createdAtTime);

        int repostsCount = status.getInt("reposts_count");
        int commentsCount = status.getInt("comments_count");
        int attitudesCount = status.getInt("attitudes_count");
        crisisData.setRepostsCount(repostsCount);
        crisisData.setCommentsCount(commentsCount);
        crisisData.setAttitudesCount(attitudesCount);

        dao.insert(crisisData);//入库
        CrisisDispose dispose = this.initDispose(crisisData);//任务分配
        if (dispose != null) {
            disposeService.assignedWorkTask(dispose);//任务保存
            return "入库成功，任务分配成功";
        } else {
            return "入库成功，因为部门没有客服，任务分配失败";
        }
    }

    @Transactional(readOnly = false)
    public CrisisDispose initDispose(CrisisData crisisData) {
        //任务初始化
        CrisisDispose dispose = new CrisisDispose();
        dispose.setCrisisData(crisisData);
        dispose.setActionStatus("0");
        dispose.setCustomerStatus("0");
        dispose.setIssueStatus("0");
        //任务分配
        Office office = crisisData.getOffice();
        User user=crisisDisposeLogService.chooseUser(office);
        if(user!=null){
            dispose.setUser(user);//分给业绩最小的人
            return dispose;
        }else {
            return null;//分配失败
        }
    }

    /**
     * 检验微博ID的有效性，如果重复或为空，则返回True
     *
     * @param weiBoId
     * @return
     */
    public boolean weiBoIdExist(String weiBoId) {
        CrisisData crisisData = new CrisisData();
        crisisData.setWeiBoId(weiBoId);
        List<CrisisData> list = this.findList(crisisData);
        if (list != null && list.size() != 0) {
            return true;//存在
        } else {
            return false;//不存在
        }
    }

    @Transactional(readOnly = false)
    public void delete(CrisisData crisisData) {
        super.delete(crisisData);
    }

}