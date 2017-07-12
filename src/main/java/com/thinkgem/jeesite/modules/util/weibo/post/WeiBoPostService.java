package com.thinkgem.jeesite.modules.util.weibo.post;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.util.Base64Util;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

//对接口进行测试
@Service
public class WeiBoPostService {
    private String charset = "utf-8";
    private HttpClientUtil httpClientUtil = null;
    private String accessToken;
    private String commentSubId;
    private String keywordSubId;
    private String ip;
    private String port;
    private String adminPath;

    public WeiBoPostService(){
        httpClientUtil = new HttpClientUtil();
        accessToken= Global.getConfig("access_token");
        commentSubId=Global.getConfig("comment_sub_id");
        keywordSubId=Global.getConfig("keyword_sub_id");
        ip=Global.getConfig("ip");
        port=Global.getConfig("port");
        adminPath=Global.getConfig("adminPath");
    }

    public String comment(String id,String comment,String token){
        String httpOrgCreateTest = "https://c.api.weibo.com/2/comments/create/biz.json";
        Map<String,String> createMap = new HashMap<String,String>();
        if(StringUtils.isBlank(token)){
            token=accessToken;
        }
        createMap.put("access_token",token);
        createMap.put("id",id);
        createMap.put("comment",comment);
        String httpOrgCreateTestRtn = httpClientUtil.doPost(httpOrgCreateTest,createMap,charset);
        return httpOrgCreateTestRtn;
    }
    public void updateSubscribe(String uids){
        Map<String,String> createMap = new HashMap<String,String>();
        createMap.put("access_token",accessToken);
        createMap.put("subid",commentSubId);
        createMap.put("add_uids",uids);
        String httpOrgCreateTestRtn = httpClientUtil.doPost("https://c.api.weibo.com/subscribe/update_subscribe.json",createMap,charset);
        System.out.println("result:"+httpOrgCreateTestRtn);
    }
    public void updateSubscribeAddKeyWord(String keywords,String token){
        Map<String,String> createMap = new HashMap<String,String>();
        if(StringUtils.isBlank(token)){
            token=accessToken;
        }
        createMap.put("access_token",token);
        createMap.put("subid",keywordSubId);
        createMap.put("add_keywords",keywords);
        String httpOrgCreateTestRtn = httpClientUtil.doPost("https://c.api.weibo.com/subscribe/update_subscribe.json",createMap,charset);
        System.out.println("result:"+httpOrgCreateTestRtn);
    }

    public String updateSubscribeDelKeyWord(String keywords,String token){
        Map<String,String> createMap = new HashMap<String,String>();
        if(StringUtils.isBlank(token)){
            token=accessToken;
        }
        createMap.put("access_token",token);
        createMap.put("subid",keywordSubId);
        createMap.put("del_keywords",keywords);
        String rs = httpClientUtil.doPost("https://c.api.weibo.com/subscribe/update_subscribe.json",createMap,charset);
        return rs;
    }
    public String receiveMessage(String message) throws UnsupportedEncodingException {
        Map<String,String> createMap = new HashMap<String,String>();
        createMap.put("message",Base64Util.encode(message));
        String url="http://"+ip+":"+port+"/solm"+adminPath+"/weibo/weiBoComment/receiveMessage";
        String httpOrgCreateTestRtn = httpClientUtil.doPost(url,createMap,charset);
        return httpOrgCreateTestRtn;
        //System.out.println("result:"+httpOrgCreateTestRtn);
    }
    public String receiveKeyWordMessage(String message) throws UnsupportedEncodingException {

        Map<String,String> createMap = new HashMap<String,String>();
        createMap.put("message",Base64Util.encode(message));
        String url="http://"+ip+":"+port+"/solm"+adminPath+"/weibo/weiBoStatus/receiveKeyWordMessage";
        String httpOrgCreateTestRtn = httpClientUtil.doPost(url,createMap,charset);
        //System.out.println("result:"+httpOrgCreateTestRtn);
        return httpOrgCreateTestRtn;
    }
    public String messagesReply(String receiverId ,String data,String token) throws UnsupportedEncodingException {
        Map<String,String> createMap = new HashMap<String,String>();
        if(StringUtils.isBlank(token)){
            token=accessToken;
        }
        createMap.put("access_token",token);
        createMap.put("type","text");
        createMap.put("data",data);
        createMap.put("receiver_id",receiverId);
        String rs = httpClientUtil.doPost("https://m.api.weibo.com/2/messages/reply.json",createMap,charset);
        //拼接路径字符串将参数包含进去
        return rs;
    }

}
