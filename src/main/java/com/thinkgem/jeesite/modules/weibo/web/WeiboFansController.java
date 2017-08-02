/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.nms.entity.FinalQuantiy;
import com.thinkgem.jeesite.modules.oa.entity.OaNotify;
import com.thinkgem.jeesite.modules.oa.service.OaNotifyService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.util.WebSocketUtil;
import com.thinkgem.jeesite.modules.util.weibo.request.WeiBoRequestService;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoMail;
import com.thinkgem.jeesite.modules.weibo.service.WeiBoMailService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 微博用户Controller
 * @author jiangxingqi
 * @version 2017-03-07
 */
@Controller
@RequestMapping(value = "${adminPath}/weibo/weiboFans")
public class WeiboFansController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WeiBoRequestService weiBoRequestService;

	@Autowired
	private WeiBoMailService weiBoMailService;

	@Autowired
	private SystemService systemService;

	@Autowired
	private OaNotifyService oaNotifyService;

	private static String app_secret = "34e0514d15165a11e2c103707f31c97e"; //appkey对应的secret，验证签名时使用。

	@RequestMapping(value = "callBack")
	public void callBack(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException, weibo4j.org.json.JSONException {
		String nonce = request.getParameter("nonce");
		String echoStr = request.getParameter("echostr");
		String timestamp = request.getParameter("timestamp");
		String signature = request.getParameter("signature");
		BufferedReader in = request.getReader();
		String returnContent=doService(nonce,echoStr,timestamp,signature,in);
		output(response, returnContent); //输出响应的内容。
	}
	//测试回调
	public String  doService(String nonce,String echoStr,String timestamp,String signature,BufferedReader in) throws ServletException, IOException, JSONException, weibo4j.org.json.JSONException {
		String returnContent = ""; //返回的响应结果。默认为空字符串。
		//验证签名
		boolean isValid = ValidateSHA(signature, nonce, timestamp);
		if (isValid) { //签名正确时，如果存在echoStr就返回echoStr，否则接收数据
			if (StringUtils.isNotBlank(echoStr)) {
				//存在echoStr，是首次配置时验证url可达性。
				returnContent = echoStr; //返回内容为echoStr的内容
			} else {
				//正常推送消息时不会存在echoStr参数。
				//接收post过来的消息数据
				StringBuilder sb = new StringBuilder();

				String line;
				while ((line = in.readLine()) != null) {
					sb.append(line);
				}
				//TODO 根据业务对消息进行处理。处理完成可以返回空串，也可以返回回复消息。
				String receivedMessage=sb.toString();
				JSONObject mail= JSON.parseObject(receivedMessage);
				String text = mail.getString("text");
				if("取消关注事件消息".equals(text)||"订阅事件消息".equals(text)){
					returnContent = "关注事件不处理";
				}
				String senderId = mail.getString("sender_id"); //发送方id
				weibo4j.org.json.JSONObject sender=weiBoRequestService.getUserByUserId(senderId,"");
				String senderName=sender.getString("screen_name");//发送人昵称
				String receiverId = mail.getString("receiver_id");//接收方id
				weibo4j.org.json.JSONObject receiver=weiBoRequestService.getUserByUserId(receiverId,"");
				String receiverName=receiver.getString("screen_name");//接收方昵称

				String createdAtString = mail.getString("created_at");
				Date createdAt=new Date(createdAtString);
				WeiBoMail weiBoMail=new WeiBoMail();
				weiBoMail.setSenderId(senderId);
				weiBoMail.setSenderName(senderName);
				weiBoMail.setReceiverId(receiverId);
				weiBoMail.setReceiverName(receiverName);
				weiBoMail.setText(text);
				weiBoMail.setCreatedAt(createdAt);
				weiBoMailService.save(weiBoMail);
				WebSocketUtil webSocketUtil=new WebSocketUtil();
				logger.error("私信  senderId="+senderId+",senderName="+senderName+",receiverId="+receiverId+",receiverName"+receiverName);
				boolean bool=webSocketUtil.sendMessage(senderId);//调用webSocket，主动更新对话列表
				if(bool==false){
					User user=new User();
					//System.out.println("receiverId："+receiverId);
					user.setWeiBoUserId(receiverId);
					List<User> receiverList=systemService.findUserByWeiBoUserId(user);//客服收信人查询，如果存在，则通知
					//System.out.println(receiverList.size());
					if(receiverList!=null&&receiverList.size()!=0){
						User receiverDB=receiverList.get(0);//接受人
						OaNotify notify =new OaNotify();
						notify.setOaNotifyRecordIds(receiverDB.getId());//接收人
						notify.setTitle("来自"+senderName+"的私信");
						String param="receiverId="+receiverId+"&receiverName="+receiverName+"&senderId="+senderId+"&weiBoSenderName="+senderName;
						notify.setContent(param);
						notify.setType(FinalQuantiy.mail);//私信
						notify.setStatus(FinalQuantiy.publish);//已发布
						User admin=systemService.getUser(FinalQuantiy.adminId);
						notify.setCreateBy(admin);
						notify.setUpdateBy(admin);
						if(oaNotifyService.checkMailNotify(notify,receiverDB)){
							oaNotifyService.save(notify);
						}
						//System.out.println(notify);
					}
				}
			}
		} else {
			//TODO 异常信息
			returnContent = "sign error!";
		}
		return returnContent;
	}
	/**
	 * 验证sha1签名，验证通过返回true，否则返回false
	 *
	 * @param signature
	 * @param nonce
	 * @param timestamp
	 * @return
	 */
	private boolean ValidateSHA(String signature, String nonce,
								String timestamp) {
		if (signature == null || nonce == null || timestamp == null) {
			return false;
		}
		String sign = sha1(getSignContent(nonce, timestamp, app_secret));
		if (!signature.equals(sign)) {
			return false;
		}
		return true;
	}
	/**
	 * 生产sha1签名
	 *
	 * @param strSrc
	 * @return
	 */
	private static String sha1(String strSrc) {
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			//TODO
			e.printStackTrace();
		}
		return strDes;
	}
	private static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;

		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));

			if (tmp.length() == 1) {
				des += "0";
			}

			des += tmp;
		}

		return des;
	}
	/**
	 * 对非空参数按字典顺序升序构造签名串
	 *
	 * @param params
	 * @return
	 */

	private static String getSignContent(String... params) {
		List<String> list = new ArrayList(params.length);
		for (String temp : params) {
			if (StringUtils.isNotBlank(temp)) {
				list.add(temp);
			}
		}
		Collections.sort(list);
		StringBuilder strBuilder = new StringBuilder();
		for (String element : list) {
			strBuilder.append(element);
		}
		return strBuilder.toString();
	}
	/**
	 * 输出返回内容
	 *
	 * @param response
	 * @param msg
	 * @throws IOException
	 */
	private void output(HttpServletResponse response, String msg)
			throws IOException {
		if (msg != null) {
			response.getOutputStream().write(msg.getBytes());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
	/**
	 * 生成回复的消息。（发送被动响应消息）
	 *
	 * @param data       消息的内容。
	 * @param type       消息的类型
	 * @param senderId   回复消息的发送方uid。蓝v用户自己
	 * @param receiverId 回复消息的接收方  蓝v用户的粉丝uid
	 * @return
	 */
	private static String generateReplyMsg(String data, String type, String senderId, String receiverId) {
		JSONObject jo = new JSONObject();
		jo.put("result", true);
		jo.put("sender_id", senderId);
		jo.put("receiver_id", receiverId);
		jo.put("type", type);
		try {
			jo.put("data", URLEncoder.encode(data, "utf-8")); //data字段的内容需要进行utf8的urlencode
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jo.toString();
	}

	/**
	 * 生成文本类型的消息data字段
	 *
	 * @return
	 */
	private static String textMsg() {
		JSONObject jo = new JSONObject();
		jo.put("text", "中文消息");
		return jo.toString();
	}

	/**
	 * 生成文本类型的消息data字段
	 *
	 * @return
	 */
	private static String articleMsg() {
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		for (int i = 0; i < 1; i++) {
			JSONObject temp = new JSONObject();
			temp.put("display_name", "两个故事");
			temp.put("summary", "今天讲两个故事，分享给你。谁是公司？谁又是中国人？​");
			temp.put("image", "http://storage.mcp.weibo.cn/0JlIv.jpg");
			temp.put("url", "http://e.weibo.com/mediaprofile/article/detail?uid=1722052204&aid=983319");
			ja.add(temp);
		}
		jo.put("articles", ja);
		return jo.toString();
	}

	/**
	 * 生成文本类型的消息data字段
	 *
	 * @return
	 */
	private static String positionMsg() {
		JSONObject jo = new JSONObject();
		jo.put("longitude", "344.3344");
		jo.put("latitude", "232.343434");
		return jo.toString();
	}
}