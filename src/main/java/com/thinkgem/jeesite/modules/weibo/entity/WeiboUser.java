/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 微博用户Entity
 * @author jiangxingqi
 * @version 2017-03-07
 */
public class WeiboUser extends DataEntity<WeiboUser> {
	
	private static final long serialVersionUID = 1L;
	private String screenName;		// screen_name
	private String name;		// name
	private String province;		// province
	private String city;		// city
	private String location;		// location
	private String description;		// description
	private String url;		// url
	private String profileImageUrl;		// profile_image_url
	private String userDomain;		// user_domain
	private String gender;		// gender
	private Integer followersCount;		// followers_count
	private Integer friendsCount;		// friends_count
	private Integer favouritesCount;		// favourites_count
	private Date createdAt;		// created_at
	private boolean following;		// following
	private boolean verified;		// verified
	private Integer verifiedType;		// verified_type
	private boolean allowAllActMsg;		// allow_all_act_msg
	private boolean allowAllComment;		// allow_all_comment
	private boolean followMe;		// follow_me
	private String avatarLarge;		// avatar_large
	private Integer onlineStatus;		// online_status
	private Integer biFollowersCount;		// bi_followers_count
	private String verifiedReason;		// verified_reason
	private String weihao;		// weihao
	private String statusId;		// status_id
	private Integer statusesCount;		// statuses_count
	private Integer unrank;		// unrank
	private String lang;

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	private String abilityTags;		// ability_tags


	public Integer getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(Integer followersCount) {
		this.followersCount = followersCount;
	}

	public Integer getFriendsCount() {
		return friendsCount;
	}

	public void setFriendsCount(Integer friendsCount) {
		this.friendsCount = friendsCount;
	}

	public Integer getFavouritesCount() {
		return favouritesCount;
	}

	public void setFavouritesCount(Integer favouritesCount) {
		this.favouritesCount = favouritesCount;
	}

	public boolean isFollowing() {
		return following;
	}

	public void setFollowing(boolean following) {
		this.following = following;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public Integer getVerifiedType() {
		return verifiedType;
	}

	public void setVerifiedType(Integer verifiedType) {
		this.verifiedType = verifiedType;
	}

	public boolean isAllowAllActMsg() {
		return allowAllActMsg;
	}

	public void setAllowAllActMsg(boolean allowAllActMsg) {
		this.allowAllActMsg = allowAllActMsg;
	}

	public boolean isAllowAllComment() {
		return allowAllComment;
	}

	public void setAllowAllComment(boolean allowAllComment) {
		this.allowAllComment = allowAllComment;
	}

	public boolean isFollowMe() {
		return followMe;
	}

	public void setFollowMe(boolean followMe) {
		this.followMe = followMe;
	}

	public Integer getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public Integer getBiFollowersCount() {
		return biFollowersCount;
	}

	public void setBiFollowersCount(Integer biFollowersCount) {
		this.biFollowersCount = biFollowersCount;
	}


	public WeiboUser() {
		super();
	}

	public WeiboUser(String id){
		super(id);
	}

	@Length(min=0, max=20, message="screen_name长度必须介于 0 和 20 之间")
	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	
	@Length(min=0, max=20, message="name长度必须介于 0 和 20 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=11, message="province长度必须介于 0 和 11 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=11, message="city长度必须介于 0 和 11 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=20, message="location长度必须介于 0 和 20 之间")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	@Length(min=0, max=30, message="description长度必须介于 0 和 30 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	
	@Length(min=0, max=30, message="user_domain长度必须介于 0 和 30 之间")
	public String getUserDomain() {
		return userDomain;
	}

	public void setUserDomain(String userDomain) {
		this.userDomain = userDomain;
	}
	
	@Length(min=0, max=1, message="gender长度必须介于 0 和 1 之间")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	

	
	public String getAvatarLarge() {
		return avatarLarge;
	}

	public void setAvatarLarge(String avatarLarge) {
		this.avatarLarge = avatarLarge;
	}

	@Length(min=0, max=30, message="verified_reason长度必须介于 0 和 30 之间")
	public String getVerifiedReason() {
		return verifiedReason;
	}

	public void setVerifiedReason(String verifiedReason) {
		this.verifiedReason = verifiedReason;
	}
	
	@Length(min=0, max=30, message="weihao长度必须介于 0 和 30 之间")
	public String getWeihao() {
		return weihao;
	}

	public void setWeihao(String weihao) {
		this.weihao = weihao;
	}
	
	@Length(min=0, max=16, message="status_id长度必须介于 0 和 16 之间")
	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public Integer getStatusesCount() {
		return statusesCount;
	}

	public void setStatusesCount(Integer statusesCount) {
		this.statusesCount = statusesCount;
	}

    public Integer getUnrank() {
        return unrank;
    }

    public void setUnrank(Integer unrank) {
        this.unrank = unrank;
    }

    @Length(min=0, max=255, message="ability_tags长度必须介于 0 和 255 之间")
	public String getAbilityTags() {
		return abilityTags;
	}

	public void setAbilityTags(String abilityTags) {
		this.abilityTags = abilityTags;
	}
	
}