package com.thinkgem.jeesite.modules.util.weibo.request;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 使用HttpClient请求页面并返回json格式数据.
 * 对方接收的也是json格式数据。
 * 因此使用HttpGet。
 * */
@Service
public class WeiBoRequestService {

    private static String accessToken;

    public WeiBoRequestService(){
        accessToken=Global.getConfig("access_token");
    }

    /**
     * 调用商业接口获取用户的粉丝数，关注数，微博数
     * @param uids
     * @param token
     * @return
     */
    public static String  usersCounts(String uids,String token){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if(StringUtils.isBlank(token)){
            token=accessToken;
        }
        params.add(new BasicNameValuePair("access_token",token));
        params.add(new BasicNameValuePair("uids", uids));
        //要传递的参数.
        String url = "https://api.weibo.com/2/users/counts.json?" + URLEncodedUtils.format(params, HTTP.UTF_8);
        //拼接路径字符串将参数包含进去
        String json = HttpRequestUtils.getString(url);
        return json;
    }

    /**
     * 通过用户的昵称获取单个用户的数据
     * @param screenName
     * @param token
     * @return
     * @throws JSONException
     */
    public static JSONObject getUserByScreenName(String screenName,String token) throws JSONException {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if(StringUtils.isBlank(token)){
            token=accessToken;
        }
        params.add(new BasicNameValuePair("access_token",token));
        params.add(new BasicNameValuePair("screen_name", screenName));
        //要传递的参数.
        String url = "https://api.weibo.com/2/users/show.json?" + URLEncodedUtils.format(params, HTTP.UTF_8);
        //拼接路径字符串将参数包含进去
        JSONObject json = HttpRequestUtils.getJson(url);
        return json;
    }

    /**
     * 通过用户的ID获取单个用户的数据
     * @param id
     * @param token
     * @return
     * @throws JSONException
     */
    public static JSONObject getUserByUserId(String id,String token) throws JSONException {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if(StringUtils.isBlank(token)){
            token=accessToken;
        }
        params.add(new BasicNameValuePair("access_token",token));
        params.add(new BasicNameValuePair("uid", id));
        //要传递的参数.
        String url = "https://api.weibo.com/2/users/show.json?" + URLEncodedUtils.format(params, HTTP.UTF_8);
        //拼接路径字符串将参数包含进去
        JSONObject json = HttpRequestUtils.getJson(url);
        return json;
    }

    /**
     * 调用商业接口，批量获取用户列表
     * @param uids
     * @param token
     * @return
     * @throws JSONException
     */
    public static JSONObject getUserById(String uids,String token) throws JSONException {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if(StringUtils.isBlank(token)){
            token=accessToken;
        }
        params.add(new BasicNameValuePair("access_token", token));
        params.add(new BasicNameValuePair("uids", uids));
        //要传递的参数.
        String url = "https://c.api.weibo.com/2/users/show_batch/other.json?" + URLEncodedUtils.format(params, HTTP.UTF_8);
        //拼接路径字符串将参数包含进去
        JSONObject json = HttpRequestUtils.getJson(url);
        return json;
    }
    /**
     * 返回一条微博的全部评论列表
     * @param id
     * @return
     */
    public static JSONObject commentsShowAll(String id,String token){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if(StringUtils.isBlank(token)){
            token=accessToken;
        }
        params.add(new BasicNameValuePair("access_token", token));
        params.add(new BasicNameValuePair("id", id));
        //要传递的参数.
        String url = "https://c.api.weibo.com/2/comments/show/all.json?"+URLEncodedUtils.format(params, HTTP.UTF_8);
        //拼接路径字符串将参数包含进去
        JSONObject json = HttpRequestUtils.getJson(url);
        return json;
    }

    /**
     * 通过微博ID获取微博信息
     * @param id
     * @param token
     * @return
     */
    public static Map statusesShow(String id, String token) throws weibo4j.org.json.JSONException {
        Map map=new HashMap();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if(StringUtils.isBlank(token)){
            token=accessToken;
        }
        params.add(new BasicNameValuePair("access_token", token));
        params.add(new BasicNameValuePair("ids", id));
        //要传递的参数.
        String url = "https://c.api.weibo.com/2/statuses/show_batch/biz.json?"+URLEncodedUtils.format(params, HTTP.UTF_8);
        //拼接路径字符串将参数包含进去
        JSONObject json = HttpRequestUtils.getJson(url);

        if (StringUtils.isNoneBlank(json.getString("error_code"))) {
            String error=json.getString("error_code");
            map.put("error",error);
        }else{
            JSONArray statusList = json.getJSONArray("statuses");
            int cnt = statusList.length();
            if (cnt != 0) {
                JSONObject status = statusList.getJSONObject(0);
                map.put("status",status);
            }else{
                map.put("error","该条微博不存在");
            }
        }
        return map;
    }

    /**
     * 通过微博URL的mid，查询微博的ID
     * @param mid
     * @param token
     * @return
     * @throws weibo4j.org.json.JSONException
     */
    public static String  queryIdByMid(String mid,String token) throws weibo4j.org.json.JSONException {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if(StringUtils.isBlank(token)){
            token=accessToken;
        }
        params.add(new BasicNameValuePair("access_token", token));
        params.add(new BasicNameValuePair("mid", mid));
        params.add(new BasicNameValuePair("type", "1"));
        params.add(new BasicNameValuePair("isBase62", "1"));
        //要传递的参数
        String url = "https://api.weibo.com/2/statuses/queryid.json?"+URLEncodedUtils.format(params, HTTP.UTF_8);
        //拼接路径字符串将参数包含进去
        JSONObject json = HttpRequestUtils.getJson(url);
        String id=json.getString("id");
        return id;
    }

    /**
     * 获得微博的转赞评
     * @param id
     * @param token
     * @return
     * @throws weibo4j.org.json.JSONException
     */
    public static String  statusesCountBiz(String id,String token) throws weibo4j.org.json.JSONException {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if(StringUtils.isBlank(token)){
            token=accessToken;
        }
        params.add(new BasicNameValuePair("access_token", token));
        params.add(new BasicNameValuePair("ids", id));
        //要传递的参数
        String url = "https://c.api.weibo.com/2/statuses/count/biz.json?"+URLEncodedUtils.format(params, HTTP.UTF_8);
        //拼接路径字符串将参数包含进去
        String rs = HttpRequestUtils.getString(url);
        return rs;
    }
}