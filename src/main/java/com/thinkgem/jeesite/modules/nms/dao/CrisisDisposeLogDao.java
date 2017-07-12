/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.nms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.nms.entity.CrisisDisposeLog;

import java.util.List;

/**
 * 危机数据处理记录DAO接口
 * @author jiangxingqi
 * @version 2017-01-19
 */
@MyBatisDao
public interface CrisisDisposeLogDao extends CrudDao<CrisisDisposeLog> {
    List<CrisisDisposeLog> findCrisisDisposeLogList(CrisisDisposeLog log);
}