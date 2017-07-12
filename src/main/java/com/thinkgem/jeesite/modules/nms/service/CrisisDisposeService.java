/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.nms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.nms.dao.CrisisDisposeDao;
import com.thinkgem.jeesite.modules.nms.entity.CrisisData;
import com.thinkgem.jeesite.modules.nms.entity.CrisisDispose;
import com.thinkgem.jeesite.modules.nms.entity.CrisisDisposeLog;
import com.thinkgem.jeesite.modules.nms.entity.FinalQuantiy;
import com.thinkgem.jeesite.modules.oa.service.OaNotifyService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.util.Base64Util;
import com.thinkgem.jeesite.modules.util.DateUtil;
import com.thinkgem.jeesite.modules.util.weibo.post.WeiBoPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 危机数据处理Service
 *
 * @author jiangxingqi
 * @version 2017-01-18
 */
@Service
@Transactional(readOnly = true)
public class CrisisDisposeService extends CrudService<CrisisDisposeDao, CrisisDispose> {

    @Autowired
    private WeiBoPostService weiBoPostService;
    @Autowired
    private CrisisDisposeLogService crisisDisposeLogService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private OaNotifyService oaNotifyService;

    public CrisisDispose get(String id) {
        return super.get(id);
    }

    public List<CrisisDispose> findList(CrisisDispose crisisDispose) {
        return super.findList(crisisDispose);
    }

    public Page<CrisisDispose> findPage(Page<CrisisDispose> page, CrisisDispose crisisDispose) {
        User user= UserUtils.getUser();
        if(user.isAdmin()==false){
            Office office=user.getOffice();
            crisisDispose.setOffice(office);
            if(FinalQuantiy.employee.equals(user.getUserType())){
                crisisDispose.setUser(user);
            }
        }
        crisisDispose.setPage(page);
        List<CrisisDispose> list = super.findList(crisisDispose);
        for(CrisisDispose i:list){
            CrisisData data=i.getCrisisData();
            String content=data.getContent();
            Base64Util base64Util=new Base64Util();
            try {
                content=base64Util.decode(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
            data.setContent(content);
            i.setCrisisData(data);
        }
        page.setList(list);
        return page;
    }
    @Transactional(readOnly = true)
    public List<Map<String,Object>> countNum(CrisisDispose crisisDispose) {
        return dao.countNum(crisisDispose);
    }
    @Transactional(readOnly = true)
    public List<Map<String,Object>> monthCnt(CrisisDispose crisisDispose) {
        return dao.monthCnt(crisisDispose);
    }
    @Transactional(readOnly = true)
    public  List<Map<String, Object>> countNumGroupByOffice(CrisisDispose crisisDispose) {
        return dao.countNumGroupByOffice(crisisDispose);
    }
    @Transactional(readOnly = true)
    public  List<Map<String, Object>> monthCntGroupByOffice(CrisisDispose crisisDispose) {
        return dao.monthCntGroupByOffice(crisisDispose);
    }

    @Transactional(readOnly = false)
    public String replySave(CrisisDispose crisisDispose) {
        CrisisData data=crisisDispose.getCrisisData();
        User user=crisisDispose.getUser();
        String token=systemService.getAccessToken(user.getId());
        String rs=weiBoPostService.comment(data.getWeiBoId(),crisisDispose.getReply(),token);//调用微博回复接口
        JSONObject object = JSON.parseObject(rs);
        String errorCode=object.getString("error_code");
        if(StringUtils.isBlank(errorCode)){//无错
            crisisDispose.setIssueStatus(FinalQuantiy.solved);//已处理
            crisisDispose.setActionStatus(FinalQuantiy.solved);//已响应
            Date commentTime=crisisDispose.getCrisisData().getCommentTime();
            Date responseTime=new Date();
            crisisDispose.setReplyTime(responseTime);
            float hour=DateUtil.getHourDiff(commentTime,responseTime);
            crisisDispose.setReplyHourDiff(hour);
            super.save(crisisDispose);//已回复
            String weiboUserId=data.getCustomerId();//微博用户的ID
            weiBoPostService.updateSubscribe(weiboUserId);//将微博用户的ID加入订阅
            return "success";
        }else{
            String error="错误编号："+errorCode;
            String message=object.getString("error");
            error+=" 错误信息："+message+" 系统已自动删除该条危机数据";
            if("20101".equals(errorCode)){
                this.delete(crisisDispose);
            }
            return error;
        }

    }

    /**
     * 分配任务给指定客服
     * @param crisisDispose
     */
    @Transactional(readOnly = false)
    public void assignedWorkTask(CrisisDispose crisisDispose) {
        //分配任务
        User user = crisisDispose.getUser();
        if (user != null && StringUtils.isNoneBlank(user.getId())) {//更新负责人的业绩
            CrisisDisposeLog log = new CrisisDisposeLog();//查询参数
            log.setUser(user);
            List<CrisisDisposeLog> logDBList = crisisDisposeLogService.findList(log);//查询结果
            if(logDBList.size()==0){
                CrisisDisposeLog logDB = new CrisisDisposeLog();
                logDB.setUser(user);
                logDB.setDisposedCnt(0);
                logDB.setDisposingCnt(1);
                crisisDisposeLogService.save(logDB);//新建
            }else{
                for (CrisisDisposeLog logDB : logDBList) {
                    if (logDB != null) {
                        logDB.setDisposingCnt(logDB.getDisposingCnt() + 1);//正在处理的任务数目加一
                        crisisDisposeLogService.save(logDB);//更新
                    }
                }
            }
            super.save(crisisDispose);//任务分配保存
            oaNotifyService.insertCrisisNotify(crisisDispose.getId(),user.getId(),FinalQuantiy.crisisDataStore,"危机数据入库通知");
        }
    }
    //问题上报
    @Transactional(readOnly = false)
    public void report(CrisisDispose crisisDispose) {
        User user=crisisDispose.getUser();//客服
        user=systemService.getUser(user.getId());
        User lead=new User();
        lead.setOffice(user.getOffice());
        lead.setUserType(FinalQuantiy.leader);
        List<User>	list=systemService.findUserByOfficeIdAndUserType(lead);
        if(list!=null&&list.size()!=0){
            crisisDispose.setUser(list.get(0));//转给主管
            crisisDispose.setIssueStatus(FinalQuantiy.transfer);
        }
        super.save(crisisDispose);
    }

    @Transactional(readOnly = false)
    public void update(CrisisDispose crisisDispose) {
        //问题状态 0：未处理 1：已处理 2：已上报 3：已转出 4：关闭
        //客户状态 0：未解决 1：已解决
        //动作状态 0：未处理 1：已处理
        String issueStatus = crisisDispose.getIssueStatus();
        String customerStatus = crisisDispose.getCustomerStatus();
        CrisisDispose crisisDisposeDB=dao.get(crisisDispose);
        String customerStatusDB=crisisDisposeDB.getCustomerStatus();
        //客户状态 为问题解决的唯一标记
        if (FinalQuantiy.solved.equals(customerStatus)&&FinalQuantiy.unsolved.equals(customerStatusDB)) {
            Date now=new Date();
            crisisDispose.setSolveTime(now);
            Date commentTime=crisisDispose.getCrisisData().getCommentTime();
            float hour=DateUtil.getHourDiff(commentTime,now);
            crisisDispose.setSolveHourDiff(hour);
            //更新负责人的业绩
            User user = crisisDispose.getUser();
            crisisDisposeLogService.updateLogForEnd(user);
        }
        //问题状态已解决或已关闭------>动作状态级联为已解决
        if(FinalQuantiy.solved.equals(issueStatus)||FinalQuantiy.closed.equals(issueStatus)){
            crisisDispose.setActionStatus(FinalQuantiy.solved);
        }
        //问题状态已解决，动作状态级联为已解决
        if(FinalQuantiy.solved.equals(customerStatus)){
            crisisDispose.setIssueStatus(FinalQuantiy.closed);
        }
        super.save(crisisDispose);
    }
    @Transactional(readOnly = false)
    public void saveResponseTime(CrisisDispose crisisDispose) {
        crisisDispose.setResponseTime(new Date());
        CrisisData data = crisisDispose.getCrisisData();
        Date commentTime = data.getCommentTime();
        float hour = DateUtil.getHourDiff(commentTime, new Date());
        crisisDispose.setResponseHourDiff(hour);
        super.save(crisisDispose);
    }


    @Transactional(readOnly = false)
    public void delete(CrisisDispose crisisDispose) {
        super.delete(crisisDispose);
    }

}