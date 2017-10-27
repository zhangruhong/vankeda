package com.wugao.vankeda.domain.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.wugao.vankeda.domain.vo.search.SearchVo;
import com.wugao.vankeda.infrastructure.mybatis.Pagination;

@Repository
@CacheConfig(cacheNames = "query:center:com.wugao.vankeda.domain.goods.GoodsRepo")
public class GoodsRepo {

	private static final String NS = "com.wugao.vankeda.domain.goods.GoodsRepo.";
	private static final int BATCH_SIZE = 200;

	@Autowired
	
	private SqlSessionTemplate sqlSessionTemplate;

	@CacheEvict(allEntries = true)
	public Goods save(Goods goods) {
		sqlSessionTemplate.insert(NS + "save", goods);
		return goods;
	}

	@CacheEvict(allEntries = true)
	public void update(Goods goods) {
		sqlSessionTemplate.update(NS + "update", goods);
	}

	@CacheEvict(allEntries = true)
	public void remove(String id) {
		sqlSessionTemplate.delete(NS + "remove", id);
	}

	@Cacheable
	public Goods getById(String id) {
		return StringUtils.isEmpty(id) ? null : sqlSessionTemplate.selectOne(NS + "getById", id);
	}

	@Cacheable
	public List<Goods> getList(String search) {
		Map<String, Object> param = new HashMap<>();
		param.put("search", search);
		return sqlSessionTemplate.selectList(NS + "getList", param);
	}
	
	@Cacheable
	public List<Goods> getListByPage(String search, Boolean enabled, Pagination pagination) {
		Map<String, Object> param = new HashMap<>();
		param.put("search", search);
		param.put("enabled", enabled);
		return sqlSessionTemplate.selectList(NS + "getList", param, pagination.toRowBounds());
	}

	@CacheEvict(allEntries = true)
	public void saveBatch(List<Goods> toBeAddList) {
		for(int i = 0; i < toBeAddList.size(); i++) {
			try {
				save(toBeAddList.get(i));
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	public List<Goods> getListBySearch(SearchVo searchVo, Pagination pagination) {
		Map<String, Object> param = new HashMap<>();
		param.put("searchVo", searchVo);
		if(pagination != null) {
			return sqlSessionTemplate.selectList(NS + "getListBySearch", param, pagination.toRowBounds());
		}
		return sqlSessionTemplate.selectList(NS + "getListBySearch", param);
	}

	public void removeAll() {
		sqlSessionTemplate.delete(NS + "removeAll");
		
	}

}
