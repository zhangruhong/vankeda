package com.wugao.vankeda.application.index;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.WirelessShareTpwdCreateRequest;
import com.taobao.api.request.WirelessShareTpwdCreateRequest.GenPwdIsvParamDto;
import com.taobao.api.response.WirelessShareTpwdCreateResponse;
import com.wugao.vankeda.domain.category.CategoryRepo;
import com.wugao.vankeda.domain.goods.GoodsRepo;
import com.wugao.vankeda.domain.vo.search.SearchVo;
import com.wugao.vankeda.infrastructure.mybatis.Pagination;

@RestController("mobile_index")
public class IndexController {
	
	@Value("${taobao.api.url}")
	private String url;
	
	@Value("${taobao.lianmeng.appKey}")
	private String lianmengAppKey;
	
	@Value("${taobao.lianmeng.secretKey}")
	private String lianmengSecretKey;
	
	@Resource
	GoodsRepo goodsRepo;
	
	@Resource
	CategoryRepo categoryRepo;
	
	@RequestMapping(value = "v/index" ,method = RequestMethod.GET)
	public ModelAndView toIndexPage() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("categories", categoryRepo.getTopCategory());
		return mav;
	}
	
	@RequestMapping(value = "index/getGoods", method = RequestMethod.GET)
	public Pagination getGoods(Pagination pagination) {
		SearchVo searchVo = new SearchVo();
		return pagination.setRows(goodsRepo.getListBySearch(searchVo, pagination));
	}
	
	@RequestMapping(value = "index/loadByCategory", method = RequestMethod.GET)
	public Pagination getGoodsByCategory(SearchVo searchVo, Pagination pagination) {
		return pagination.setRows(goodsRepo.getListBySearch(searchVo, pagination));
	}
	
	@RequestMapping(value = "index/getToken", method = RequestMethod.GET)
	public Map<String , String> getToken(String logo, String title, String itemUrl) throws Exception{
		TaobaoClient client = new DefaultTaobaoClient(url, lianmengAppKey, lianmengSecretKey);
		WirelessShareTpwdCreateRequest req = new WirelessShareTpwdCreateRequest();
		GenPwdIsvParamDto obj1 = new GenPwdIsvParamDto();
		obj1.setLogo(logo);
		obj1.setUrl(itemUrl);
		obj1.setText(title);
		req.setTpwdParam(obj1);
		WirelessShareTpwdCreateResponse rsp = client.execute(req);
		Map<String, String> result = new HashMap<>();
		result.put("model", rsp.getModel());
		return result;
	}
	
}
