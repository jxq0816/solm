/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.nms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.nms.entity.CrisisData;

/**
 * 危机数据DAO接口
 * @author jiangxingqi
 * @version 2017-01-18
 */
@MyBatisDao
public interface CrisisDataDao extends CrudDao<CrisisData> {
	
}