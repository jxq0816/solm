/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoStatus;

/**
 * 微博DAO接口
 * @author jiangxingqi
 * @version 2017-03-13
 */
@MyBatisDao
public interface WeiBoStatusDao extends CrudDao<WeiBoStatus> {
	
}