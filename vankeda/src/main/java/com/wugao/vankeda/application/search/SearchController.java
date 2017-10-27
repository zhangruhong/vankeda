package com.wugao.vankeda.application.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.wugao.vankeda.domain.category.Category;
import com.wugao.vankeda.domain.category.CategoryRepo;
import com.wugao.vankeda.domain.goods.GoodsRepo;
import com.wugao.vankeda.domain.goods.GoodsService;
import com.wugao.vankeda.domain.vo.search.SearchVo;
import com.wugao.vankeda.infrastructure.mybatis.Pagination;

@RestController("mobile_search")
@RequestMapping
public class SearchController {
	
	@Value("${taobao.api.url}")
	private String url;
	
	@Value("${taobao.lianmeng.appKey}")
	private String lianmengAppKey;
	
	@Value("${taobao.lianmeng.secretKey}")
	private String lianmengSecretKey;
	
	@Value("${tao.adzone.id}")
	private String adzoneId;
	
	@Autowired
	private GoodsRepo goodsRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	
	
	@RequestMapping(value = "v/search", method = RequestMethod.GET, produces = "text/html")
	public ModelAndView toSearchPage() {
		ModelAndView mav = new ModelAndView("search/search");
		mav.addObject("topCategories", categoryRepo.getTopCategory());
		return mav;
	}
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public Pagination search(SearchVo searchVo, Pagination pagination) {
		return pagination.setRows(goodsRepo.getListBySearch(searchVo, pagination));
	}
	
	@RequestMapping(value = "search/getChildrenCategory", method = RequestMethod.GET)
	public List<Category> getChildrenCategory(String id){
		return categoryRepo.getChildren(id);
	}
	
}
