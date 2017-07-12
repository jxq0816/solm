/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoKeyword;

/**
 * 微博关键字DAO接口
 * @author jiangxingqi
 * @version 2017-03-14
 */
@MyBatisDao
public interface WeiBoKeywordDao extends CrudDao<WeiBoKeyword> {
	
}