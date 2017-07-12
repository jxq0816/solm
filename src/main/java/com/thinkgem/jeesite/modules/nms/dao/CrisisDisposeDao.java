/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.nms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.nms.entity.CrisisDispose;

import java.util.List;
import java.util.Map;

/**
 * 危机数据处理DAO接口
 * @author jiangxingqi
 * @version 2017-01-18
 */
@MyBatisDao
public interface CrisisDisposeDao extends CrudDao<CrisisDispose> {
    List<Map<String,Object>> countNum(CrisisDispose crisisDispose);
    List<Map<String,Object>> monthCnt(CrisisDispose crisisDispose);
    List<Map<String, Object>> countNumGroupByOffice(CrisisDispose crisisDispose);
    List<Map<String, Object>> monthCntGroupByOffice(CrisisDispose crisisDispose);
}