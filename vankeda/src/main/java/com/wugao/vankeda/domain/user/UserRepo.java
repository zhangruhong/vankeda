package com.wugao.vankeda.domain.user;

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

import com.wugao.vankeda.infrastructure.mybatis.Pagination;


@Repository
@CacheConfig(cacheNames = "query:vankeda:com.wugao.vankeda.domain.user.UserRepo")
public class UserRepo {

	private static final String NS = "com.wugao.vankeda.domain.user.UserRepo.";

	@Autowired
	
	private SqlSessionTemplate sqlSessionTemplate;

	@CacheEvict(allEntries = true)
	public User save(User user) {
		user.setId(UUID.randomUUID().toString());
		sqlSessionTemplate.insert(NS + "save", user);
		return user;
	}

	@CacheEvict(allEntries = true)
	public void update(User user) {
		sqlSessionTemplate.update(NS + "update", user);
	}

	@CacheEvict(allEntries = true)
	public void remove(String id) {
		sqlSessionTemplate.delete(NS + "remove", id);
	}

	@Cacheable
	public User getById(String id) {
		return StringUtils.isEmpty(id) ? null : (User) sqlSessionTemplate.selectOne(NS + "getById", id);
	}

	@Cacheable
	public User getByUsername(String username) {
		return StringUtils.isEmpty(username) ? null : (User) sqlSessionTemplate.selectOne(NS + "getByUsername", username);
	}

	@Cacheable
	public List<User> getList(String search) {
		Map<String, Object> param = new HashMap<>();
		param.put("search", search);
		return sqlSessionTemplate.selectList(NS + "getList", param);
	}
	
	@Cacheable
	public List<User> getListByPage(String search, Boolean enabled, Pagination pagination) {
		Map<String, Object> param = new HashMap<>();
		param.put("search", search);
		param.put("enabled", enabled);
		return sqlSessionTemplate.selectList(NS + "getList", param, pagination.toRowBounds());
	}

}
