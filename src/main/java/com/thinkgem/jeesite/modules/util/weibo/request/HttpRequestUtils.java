package com.thinkgem.jeesite.modules.util.weibo.request;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import weibo4j.org.json.JSONObject;
import weibo4j.org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * 使用HttpClient请求页面并返回json格式数据.
 * 对方接收的也是json格式数据。
 * 因此使用HttpGet。
 * */
public class HttpRequestUtils {

    public static void main(String[] args){

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("access_token", "2.00SdyOsBnp71EDd4adc526abc8EYEB"));
        params.add(new BasicNameValuePair("screen_name", "Jiangson的记忆"));
        //要传递的参数.
        String url = "https://api.weibo.com/2/users/show.json?" + URLEncodedUtils.format(params, HTTP.UTF_8);
        //拼接路径字符串将参数包含进去
        JSONObject json = getJson(url);
        System.out.println(json);

    }

    public static JSONObject getJson(String url) {

        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        JSONObject json = null;
        try {
            HttpResponse res = client.execute(get);
            //if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = res.getEntity();
            json = new JSONObject(new JSONTokener(new InputStreamReader(entity.getContent(), HTTP.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally{
            //关闭连接 ,释放资源
            client.getConnectionManager().shutdown();
        }
        return json;
    }
    public static String getString(String url) {

        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse res = client.execute(get);
            HttpEntity entity = res.getEntity();
            InputStreamReader inputStreamReader= new InputStreamReader(entity.getContent(), HTTP.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder result = new StringBuilder();
            String line = null;
            try {
                while((line = bufferedReader.readLine()) != null){
                    result.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try{
                    inputStreamReader.close();
                    bufferedReader.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally{
            //关闭连接 ,释放资源
            client.getConnectionManager().shutdown();
        }
    }
}