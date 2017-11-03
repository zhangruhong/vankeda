package com.wugao.vankeda.domain.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wugao.vankeda.infrastructure.mybatis.Pagination;

@Repository
public class ActivityRepo {
	
	private static final String NS = "com.wugao.vankeda.domain.activity.ActivityRepo.";
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	public Activity getById(String id) {
		return sqlSessionTemplate.selectOne(NS + "getActivityById", id);
	}
	
	public Activity save(Activity activity) {
		activity.setId(UUID.randomUUID().toString());
		sqlSessionTemplate.insert(NS + "saveActivity", activity);
		return activity;
	}
	
	public Activity update(Activity activity) {
		sqlSessionTemplate.update(NS + "updateActivity", activity);
		return activity;
	}
	
	public void remove(String id) {
		sqlSessionTemplate.delete(NS + "removeActivity", id);
	}
	
	/**
	 * 插入活动商品对应表
	 * @param list
	 */
	public void saveActGoodsMap(List<ActivityGoodsMap> list) {
		sqlSessionTemplate.insert(NS + "saveActGoodsMap", list);
	}
	
	/**
	 * 从活动中删除部分商品
	 * @param activityId
	 * @param ids
	 */
	public void removeGoodsFromAct(String activityId, List<String> ids) {
		Map<String, Object> param = new HashMap<>();
		param.put("activityId", activityId);
		param.put("ids", ids);
		sqlSessionTemplate.delete(NS + "removeGoodsFromAct", param);
	}
	
	public List<Activity> getList(String title, String startDate, String endDate, Boolean status, Boolean onBanner, Pagination pagination){
		Map<String, Object> param = new HashMap<>();
		param.put("title", title);
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		param.put("status", status);
		param.put("onBanner", onBanner);
		if(pagination != null) {
			return sqlSessionTemplate.selectList(NS + "getActivityList", param, pagination.toRowBounds());
		}
		return sqlSessionTemplate.selectList(NS + "getActivityList", param);
	}

}
