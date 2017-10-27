package com.wugao.vankeda.domain.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@CacheConfig(cacheNames = "query:center:com.wugao.vankeda.domain.category.CategoryRepo")
public class CategoryRepo {

	private static final String NS = "com.wugao.vankeda.domain.category.CategoryRepo.";
	private static final int BATCH_SIZE = 200;

	@Autowired
	
	private SqlSessionTemplate sqlSessionTemplate;

	@CacheEvict(allEntries = true)
	public Category save(Category category) {
		category.setId(UUID.randomUUID().toString());
		sqlSessionTemplate.insert(NS + "save", category);
		return category;
	}

	@CacheEvict(allEntries = true)
	public void update(Category category) {
		sqlSessionTemplate.update(NS + "update", category);
	}

	@CacheEvict(allEntries = true)
	public void remove(String id) {
		sqlSessionTemplate.delete(NS + "remove", id);
	}

	@Cacheable
	public Category getById(String id) {
		return StringUtils.isEmpty(id) ? null : sqlSessionTemplate.selectOne(NS + "getById", id);
	}
	
	public List<Category> getTopCategory(){
		return sqlSessionTemplate.selectList(NS + "getTopCategory");
	}
	
	public List<Category> getChildren(String id){
		Map<String, String> param = new HashMap<>();
		param.put("id", id);
		return sqlSessionTemplate.selectList(NS + "getChildren", id);
	}
}
