package com.wugao.vankeda.application.tqg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.taobao.api.response.TbkJuTqgGetResponse.Results;
import com.wugao.vankeda.domain.tqg.TqgService;
import com.wugao.vankeda.domain.vo.search.SearchVo;
import com.wugao.vankeda.support.constants.PageConstant;

@RestController("mobile_tqg")
@RequestMapping
public class TqgController {
	
	@Resource
	TqgService tqgService;
	

	@RequestMapping(value = "v/tqg", method = RequestMethod.GET)
	public ModelAndView ToTqgPage() {
		return new ModelAndView("tqg/tqg");
		
	}
	
	@RequestMapping(value = "tqg/search", method = RequestMethod.GET)
	public Map<String, Object> getTqg(SearchVo searchVo, Integer page){
		Map<String, Object> result = new HashMap<>();
		List<Results> list = tqgService.filterTqgResult(searchVo, tqgService.getTqgResult(page));
		while(list.size() < PageConstant.PAGE_SIZE_M) {
			page++;
			list.addAll(tqgService.filterTqgResult(searchVo, tqgService.getTqgResult(page)));
		}
		result.put("list", list);
		result.put("page", page);
		return result;
	} 
}
