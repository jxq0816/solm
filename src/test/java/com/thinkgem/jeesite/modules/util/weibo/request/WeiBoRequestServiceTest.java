package com.thinkgem.jeesite.modules.util.weibo.request;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by boxiaotong on 2017/3/13.
 */
public class WeiBoRequestServiceTest {

    private WeiBoRequestService weiBoRequestService=new WeiBoRequestService();

    @Test
    public void getUserByScreenName() throws Exception {
        String screenName="西门子家电客户服务";
        JSONObject user=weiBoRequestService.getUserByScreenName(screenName,"");
        System.out.print(user.toString());
    }

    @Test
    public void usersCounts() throws Exception {
        String id="1717624644";
        String user=weiBoRequestService.usersCounts(id,"");
        System.out.print(user);
    }

    @Test
    public void getUserById() throws Exception {
        String id="3901147339";
        JSONObject json= weiBoRequestService.getUserById(id,"");
        JSONArray statusList=json.getJSONArray("users");
        int cnt=statusList.length();
        for(int i=0;i<cnt;i++) {
            JSONObject user = statusList.getJSONObject(i);
            String senderName = user.getString("screen_name");//发送人昵称
            System.out.println(senderName);
        }
    }
    @Test
    public void getUserByUserId() throws Exception {
        String id="3636826163";
        JSONObject json= weiBoRequestService.getUserByUserId(id,"");
        System.out.println(json);
    }

    @Test
    public void commentsShowAll() throws Exception {
        String id="1878546883";
        JSONObject json= weiBoRequestService.commentsShowAll(id,"");
        System.out.println(json.toString());
    }

    @Test
    public void statusesShow() throws Exception {
        String id="4080837181583476";
        Map json=weiBoRequestService.statusesShow(id,"");
        System.out.println(json.toString());
    }
    @Test
    public void queryIdByMid() throws Exception {
        String mid="EALf4xDHV";
        String rs=weiBoRequestService.queryIdByMid(mid,"");
        System.out.println(rs);
    }
    @Test
    public void statusesCountBiz() throws Exception {
        String id="";
        String rs=weiBoRequestService.statusesCountBiz(id,"");
        com.alibaba.fastjson.JSONArray array= JSON.parseArray(rs);
        Iterator<Object> it = array.iterator();
        while (it.hasNext()) {
            com.alibaba.fastjson.JSONObject ob = (com.alibaba.fastjson.JSONObject) it.next();
            System.out.println(ob);
        }
    }
}