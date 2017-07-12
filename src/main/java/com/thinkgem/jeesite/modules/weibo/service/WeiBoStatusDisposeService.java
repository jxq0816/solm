/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.nms.entity.FinalQuantiy;
import com.thinkgem.jeesite.modules.nms.service.CrisisDisposeLogService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.util.DateUtil;
import com.thinkgem.jeesite.modules.util.weibo.post.WeiBoPostService;
import com.thinkgem.jeesite.modules.weibo.dao.WeiBoStatusDisposeDao;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoStatus;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoStatusDispose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 微博数据处理Service
 * @author jiangxingqi
 * @version 2017-04-06
 */
@Service
@Transactional(readOnly = true)
public class WeiBoStatusDisposeService extends CrudService<WeiBoStatusDisposeDao, WeiBoStatusDispose> {
	@Autowired
	private SystemService systemService;
	@Autowired
	private CrisisDisposeLogService crisisDisposeLogService;
	@Autowired
	private WeiBoPostService weiBoPostService;

	public WeiBoStatusDispose get(String id) {
		return super.get(id);
	}
	
	public List<WeiBoStatusDispose> findList(WeiBoStatusDispose weiBoStatusDispose) {
		return super.findList(weiBoStatusDispose);
	}
	
	public Page<WeiBoStatusDispose> findPage(Page<WeiBoStatusDispose> page, WeiBoStatusDispose weiBoStatusDispose) {
		return super.findPage(page, weiBoStatusDispose);
	}
	
	@Transactional(readOnly = false)
	public void save(WeiBoStatusDispose weiBoStatusDispose) {
		super.save(weiBoStatusDispose);
	}

	@Transactional(readOnly = false)
	public void update(WeiBoStatusDispose weiBoStatusDispose) {
		//问题状态 0：未处理 1：已处理 2：已上报 3：已转出 4：关闭
		//客户状态 0：未解决 1：已解决
		//动作状态 0：未处理 1：已处理
		String issueStatus = weiBoStatusDispose.getIssueStatus();
		String customerStatus = weiBoStatusDispose.getCustomerStatus();
		WeiBoStatusDispose weiBoStatusDisposeDB=dao.get(weiBoStatusDispose);
		String customerStatusDB=weiBoStatusDisposeDB.getCustomerStatus();
		//客户状态 为问题解决的唯一标记
		if (FinalQuantiy.solved.equals(customerStatus)&&FinalQuantiy.unsolved.equals(customerStatusDB)) {
			Date now=new Date();
			weiBoStatusDispose.setSolveTime(now);
			Date commentTime=weiBoStatusDispose.getWeiBoStatus().getCreatedAt();
			if(commentTime!=null){
				float hour=DateUtil.getHourDiff(commentTime,now);
				weiBoStatusDispose.setSolveHourDiff(hour);
			}
			//更新负责人的业绩
			User user = weiBoStatusDispose.getUser();
			crisisDisposeLogService.updateLogForEnd(user);
		}
		//问题状态已解决或已关闭------>动作状态级联为已解决
		if(FinalQuantiy.solved.equals(issueStatus)||FinalQuantiy.closed.equals(issueStatus)){
			weiBoStatusDispose.setActionStatus(FinalQuantiy.solved);
		}
		//问题状态已解决，动作状态级联为已解决
		if(FinalQuantiy.solved.equals(customerStatus)){
			weiBoStatusDispose.setIssueStatus(FinalQuantiy.closed);
		}
		super.save(weiBoStatusDispose);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeiBoStatusDispose weiBoStatusDispose) {
		super.delete(weiBoStatusDispose);
	}

	@Transactional(readOnly = false)
	public void saveResponseTime(WeiBoStatusDispose dispose) {
		dispose.setResponseTime(new Date());
		WeiBoStatus data = dispose.getWeiBoStatus();
		Date commentTime = data.getCreatedAt();
		float hour = DateUtil.getHourDiff(commentTime, new Date());
		dispose.setResponseHourDiff(hour);
		super.save(dispose);
	}
	@Transactional(readOnly = false)
	public String replySave(WeiBoStatusDispose dispose) {
		WeiBoStatus status=dispose.getWeiBoStatus();
		User user=dispose.getUser();
		user=systemService.getUser(user.getId());
		Office office=user.getOffice();
		String rs=weiBoPostService.comment(status.getId(),dispose.getReply(),office.getAccessToken());//调用微博回复接口
		JSONObject object = JSON.parseObject(rs);
		String errorCode=object.getString("error_code");
		if(StringUtils.isBlank(errorCode)){//无错
			dispose.setIssueStatus(FinalQuantiy.solved);//已处理
			dispose.setActionStatus(FinalQuantiy.solved);//已响应
			Date commentTime=dispose.getWeiBoStatus().getCreatedAt();
			Date responseTime=new Date();
			dispose.setReplyTime(responseTime);
			float hour=DateUtil.getHourDiff(commentTime,responseTime);
			dispose.setReplyHourDiff(hour);
			super.save(dispose);//已回复
			String weiboUserId=dispose.getWeiBoStatus().getWeiboUserId();//微博用户的ID
			weiBoPostService.updateSubscribe(weiboUserId);//将微博用户的ID加入订阅
			return "success";
		}else{
			String error="错误编号："+errorCode;
			String message=object.getString("error");
			error+=" 错误信息："+message+" 系统已自动删除该条微博危机数据";
			if("20101".equals(errorCode)){
				this.delete(dispose);
			}
			return error;
		}

	}
}