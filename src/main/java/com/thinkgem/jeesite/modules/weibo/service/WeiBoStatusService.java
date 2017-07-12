/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.nms.service.CrisisDisposeLogService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.util.weibo.request.WeiBoRequestService;
import com.thinkgem.jeesite.modules.weibo.dao.WeiBoStatusDao;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoStatus;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoStatusDispose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weibo4j.org.json.JSONException;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 微博Service
 * @author jiangxingqi
 * @version 2017-03-13
 */
@Service
@Transactional(readOnly = true)
public class WeiBoStatusService extends CrudService<WeiBoStatusDao, WeiBoStatus> {
    @Autowired
    private CrisisDisposeLogService crisisDisposeLogService;

    @Autowired
    private WeiBoRequestService weiBoRequestService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private SystemService systemService;

    @Autowired
    private WeiBoStatusDisposeService weiBoStatusDisposeService;

	public WeiBoStatus get(String id) {
		return super.get(id);
	}
	
	public List<WeiBoStatus> findList(WeiBoStatus weiBoStatus) {
		return super.findList(weiBoStatus);
	}
	
	public Page<WeiBoStatus> findPage(Page<WeiBoStatus> page, WeiBoStatus weiBoStatus) {
		return super.findPage(page, weiBoStatus);
	}
	
	@Transactional(readOnly = false)
	public void save(WeiBoStatus weiBoStatus) {
		super.save(weiBoStatus);
	}

    @Transactional(readOnly = false)
    public WeiBoStatus queryWeiBoStatusById(WeiBoStatus weiBoStatus) throws JSONException {
        String id=weiBoStatus.getId();
        Map map=weiBoRequestService.statusesShow(id,"");
        weibo4j.org.json.JSONObject json=(weibo4j.org.json.JSONObject)map.get("status");
        String text= json.getString("text");
        String createdAt = json.getString("created_at");//发文时间
        weibo4j.org.json.JSONObject user = json.getJSONObject("user");//用户
        String weiBoUserId=user.getString("id");
        String screenName=user.getString("screen_name");
        weiBoStatus.setWeiboUserId(weiBoUserId);
        weiBoStatus.setScreenName(screenName);
        Date createdAtTime = new Date(createdAt);
        weiBoStatus.setCreatedAt(createdAtTime);
        weiBoStatus.setText(text);
        return weiBoStatus;
    }

	@Transactional(readOnly = false)
	public String insert(WeiBoStatus weiBoStatus) throws JSONException {
        if(this.checkWeiBoStatusExist(weiBoStatus)){
            return "微博已入库，不可重复入库";
        }
        weiBoStatus.setCreateDate(new Date());
        weiBoStatus.setUpdateDate(new Date());

		dao.insert(weiBoStatus);//微博入库
        WeiBoStatusDispose dispose=initDispose(weiBoStatus);//分配任务
        if(dispose!=null){
            weiBoStatusDisposeService.save(dispose);//保存任务
            crisisDisposeLogService.updateLogForAssign(dispose.getUser());
            return "入库成功";
        }else{
            return "分配失败";
        }
	}
    @Transactional(readOnly = false)
    public boolean checkWeiBoStatusExist(WeiBoStatus weiBoStatus){
        if(this.get(weiBoStatus)==null){
            return false;
        }else{
            return true;
        }
    }


    @Transactional(readOnly = false)
    public WeiBoStatusDispose initDispose(WeiBoStatus weiBoStatus) throws JSONException {
        //任务初始化
        WeiBoStatusDispose dispose = new WeiBoStatusDispose();
        String keyword=weiBoStatus.getKeyword();
        if(keyword.startsWith("@")){
            keyword=keyword.substring(1,keyword.length());//因为关键字第一个是@
        }
        //任务分配
        /* Office office =new Office();

        office.setScreenName(keyword);
        List<Office> officeList = officeService.findListByCondition(office);

        if(officeList==null || officeList.size()==0){
            return null;
        }
        office=officeList.get(0);
        if(office==null){
            return null;
        }
        User user = crisisDisposeLogService.chooseUser(office);//任务分给谁
        */
        User query=new User();
        query.setWeiBoScreenName(keyword);
        List<User> userList= systemService.findAllUser(query);
        if(userList!=null&& userList.size()!=0){
            dispose.setUser(userList.get(0));
        }else{
            System.out.print("没人管"+keyword);
            return null;//没人管
        }

        weiBoStatus.setCreateDate(new Date());
        weiBoStatus.setUpdateDate(new Date());
        dispose.setWeiBoStatus(weiBoStatus);
        dispose.setActionStatus("0");
        dispose.setCustomerStatus("0");
        dispose.setIssueStatus("0");
        String rs=weiBoRequestService.statusesCountBiz(weiBoStatus.getId(),"");//获得转赞评
        JSONArray array= JSON.parseArray(rs);
        Iterator<Object> it = array.iterator();
        while (it.hasNext()) {
            JSONObject status = (JSONObject) it.next();
            String comments=status.getString("comments");
            String attitudes=status.getString("attitudes");
            String reposts=status.getString("reposts");
            dispose.setCommentsCount(comments);
            dispose.setAttitudesCount(attitudes);
            dispose.setRepostsCount(reposts);
        }
        dispose.setCrisisLevel("0");

        return dispose;

    }


	@Transactional(readOnly = false)
	public void delete(WeiBoStatus weiBoStatus) {
		super.delete(weiBoStatus);
	}
	
}