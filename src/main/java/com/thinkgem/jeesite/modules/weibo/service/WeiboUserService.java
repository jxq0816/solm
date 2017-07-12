/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.service;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.util.weibo.request.WeiBoRequestService;
import com.thinkgem.jeesite.modules.weibo.dao.WeiboUserDao;
import com.thinkgem.jeesite.modules.weibo.entity.WeiboUser;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 微博用户Service
 *
 * @author jiangxingqi
 * @version 2017-02-27
 */
@Service
@Transactional(readOnly = true)
public class WeiboUserService extends CrudService<WeiboUserDao, WeiboUser> {

    @Autowired
    private WeiboUserDao dao;
    @Autowired
    private WeiBoRequestService weiBoRequestService;

    public WeiboUser get(String id) {
        return super.get(id);
    }

    /**
     * 根据微博用户编号查询本地微博用户，否则调用微博接口查询
     *
     * @param id
     * @return
     * @throws JSONException
     * @throws weibo4j.org.json.JSONException
     * @throws WeiboException
     */
    @Transactional(readOnly = false)
    public WeiboUser getLocalAndOnlineUserById(String id) throws JSONException, weibo4j.org.json.JSONException, WeiboException {
        WeiboUser weiboUser = null;
        if (StringUtils.isNoneBlank(id)) {
            Object object = CacheUtils.get("weiBoUserCache", id);
            if (object != null) {
                weiboUser = (WeiboUser) object;
            } else {
                weiboUser = super.get(id);
                if (weiboUser == null) {//本地没有用户数据
                    weiboUser = this.insert(id);
                }
                CacheUtils.put("weiBoUserCache", id, weiboUser);
            }
        }
        return weiboUser;
    }

    public List<WeiboUser> findList(WeiboUser weiboUser) {
        return super.findList(weiboUser);
    }

    public Page<WeiboUser> findPage(Page<WeiboUser> page, WeiboUser weiboUser) {
        return super.findPage(page, weiboUser);
    }

    @Transactional(readOnly = false)
    public void save(WeiboUser weiboUser) {
        super.save(weiboUser);
    }

    @Transactional(readOnly = false)
    public boolean checkExist(String weiBoUserId) {
        WeiboUser weiboUser = this.get(weiBoUserId);
        if (weiboUser == null) {
            return false;
        } else {
            return true;
        }
    }

    @Transactional(readOnly = false)
    public WeiboUser updateUserCount(WeiboUser weiboUser) {
        String id = weiboUser.getId();
        String rs = weiBoRequestService.usersCounts(id, "");
        com.alibaba.fastjson.JSONArray array = JSON.parseArray(rs);
        Iterator<Object> it = array.iterator();
        while (it.hasNext()) {
            com.alibaba.fastjson.JSONObject user = (com.alibaba.fastjson.JSONObject) it.next();
            int followersCount = user.getIntValue("followers_count");
            int friendsCount = user.getIntValue("friends_count");
            int statusesCount = user.getIntValue("statuses_count");
            weiboUser.setFollowersCount(followersCount);
            weiboUser.setFriendsCount(friendsCount);
            weiboUser.setStatusesCount(statusesCount);
        }
        return weiboUser;
    }

    @Transactional(readOnly = false)
    public WeiboUser insert(String weiBoUserId) throws WeiboException, weibo4j.org.json.JSONException, JSONException {


        JSONObject user = weiBoRequestService.getUserByUserId(weiBoUserId, "");//调用微博接口
        if (StringUtils.isNoneBlank(user.getString("error_code"))) {
            return null;//失败
        }
        WeiboUser weiboUser = this.get(weiBoUserId);//在保存之前，检查库里是否存在
        if (weiboUser != null) {
            return weiboUser;
        } else {
            weiboUser = new WeiboUser();
            weiboUser.setId(weiBoUserId);
            weiboUser.setScreenName(user.getString("screen_name"));
            weiboUser.setName(user.getString("name"));
            weiboUser.setProvince(user.getString("province"));
            weiboUser.setCity(user.getString("city"));
            weiboUser.setLocation(user.getString("location"));
            weiboUser.setDescription(user.getString("description"));
            weiboUser.setUrl(user.getString("url"));
            weiboUser.setProfileImageUrl(user.getString("profile_image_url"));
            weiboUser.setUserDomain(user.getString("domain"));
            weiboUser.setGender(user.getString("gender"));
            weiboUser.setFollowersCount(user.getInt("followers_count"));
            weiboUser.setFriendsCount(user.getInt("friends_count"));
            weiboUser.setFavouritesCount(user.getInt("favourites_count"));
            weiboUser.setStatusesCount(user.getInt("statuses_count"));
            weiboUser.setCreatedAt(new Date(user.getString("created_at")));
            weiboUser.setFollowing(user.getBoolean("following"));
            weiboUser.setVerified(user.getBoolean("verified"));
            weiboUser.setVerifiedType(user.getInt("verified_type"));
            weiboUser.setAllowAllActMsg(user.getBoolean("allow_all_act_msg"));
            weiboUser.setAllowAllComment(user.getBoolean("allow_all_comment"));
            weiboUser.setFollowMe(user.getBoolean("follow_me"));
            weiboUser.setAvatarLarge(user.getString("avatar_large"));
            weiboUser.setOnlineStatus(user.getInt("online_status"));
            weiboUser.setBiFollowersCount(user.getInt("bi_followers_count"));
            weiboUser.setLang(user.getString("lang"));
            weiboUser.setVerifiedReason(user.getString("verified_reason"));
            weiboUser.setWeihao(user.getString("weihao"));
            weiboUser.setStatusId(user.getString("status_id"));
            int urank = user.getInt("urank");
            weiboUser.setUnrank(urank);
            weiboUser.setAbilityTags(user.getString("ability_tags"));
            weiboUser = updateUserCount(weiboUser);
            dao.insert(weiboUser);
            return weiboUser;
        }
    }

    @Transactional(readOnly = false)
    public void delete(WeiboUser weiboUser) {
        super.delete(weiboUser);
    }

}