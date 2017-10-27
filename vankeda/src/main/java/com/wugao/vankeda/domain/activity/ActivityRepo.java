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
		return sqlSessionTemplate.selectOne(NS + "getById", id);
	}
	
	public Activity save(Activity activity) {
		activity.setId(UUID.randomUUID().toString());
		sqlSessionTemplate.insert(NS + "save", activity);
		return activity;
	}
	
	public Activity update(Activity activity) {
		sqlSessionTemplate.update(NS + "update", activity);
		return activity;
	}
	
	public void remove(String id) {
		sqlSessionTemplate.delete(NS + "remove", id);
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
		sqlSessionTemplate.delete(NS + "saveActGoodsMap", param);
	}
	
	public List<Activity> getList(String startDate, String endDate, Boolean status, Pagination pagination){
		Map<String, Object> param = new HashMap<>();
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		param.put("status", status);
		if(pagination != null) {
			return sqlSessionTemplate.selectList(NS + "getList", param, pagination.toRowBounds());
		}
		return sqlSessionTemplate.selectList(NS + "getList", param);
	}

}
