/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.weibo.entity.WeiboUser;

/**
 * 微博用户DAO接口
 * @author jiangxingqi
 * @version 2017-02-27
 */
@MyBatisDao
public interface WeiboUserDao extends CrudDao<WeiboUser> {
	
}