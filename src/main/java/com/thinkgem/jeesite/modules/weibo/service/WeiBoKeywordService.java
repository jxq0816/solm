/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.weibo.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.util.weibo.post.WeiBoPostService;
import com.thinkgem.jeesite.modules.weibo.dao.WeiBoKeywordDao;
import com.thinkgem.jeesite.modules.weibo.entity.WeiBoKeyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 微博关键字Service
 * @author jiangxingqi
 * @version 2017-03-14
 */
@Service
@Transactional(readOnly = true)
public class WeiBoKeywordService extends CrudService<WeiBoKeywordDao, WeiBoKeyword> {

	@Autowired
	private WeiBoPostService weiBoPostService;

	public WeiBoKeyword get(String id) {
		return super.get(id);
	}
	
	public List<WeiBoKeyword> findList(WeiBoKeyword weiBoKeyword) {
		return super.findList(weiBoKeyword);
	}
	
	public Page<WeiBoKeyword> findPage(Page<WeiBoKeyword> page, WeiBoKeyword weiBoKeyword) {
		return super.findPage(page, weiBoKeyword);
	}
	
	@Transactional(readOnly = false)
	public String insertOrUpdate(WeiBoKeyword weiBoKeyword) {
		String keyword=weiBoKeyword.getKeyword();
		keyword=keyword.toLowerCase();
		weiBoKeyword.setKeyword(keyword);//转小写

		WeiBoKeyword word=new WeiBoKeyword();
		word.setKeyword(keyword);
		List<WeiBoKeyword> list = this.findList(word);//重复性检验

		if(list!=null&&list.size()!=0){
			return "该关键字已经存在";
		}else{
			weiBoPostService.updateSubscribeAddKeyWord(keyword,"");
			super.save(weiBoKeyword);
			return "保存微博关键字成功";
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(WeiBoKeyword weiBoKeyword) {
		super.delete(weiBoKeyword);
	}
	
}