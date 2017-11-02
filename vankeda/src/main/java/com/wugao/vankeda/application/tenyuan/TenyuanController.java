package com.wugao.vankeda.application.tenyuan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.taobao.api.response.TbkDgItemCouponGetResponse.TbkCoupon;
import com.wugao.vankeda.domain.ticket.TicketService;
import com.wugao.vankeda.domain.vo.search.SearchVo;
import com.wugao.vankeda.support.constants.PageConstant;

@RestController("mobile_tenyuan")
@RequestMapping
public class TenyuanController {
	
	@Resource
	TicketService ticketService;
	
	@RequestMapping(value = "v/tenyuan", method = RequestMethod.GET, produces = "text/html")
	public ModelAndView toSearchPage() {
		ModelAndView mav = new ModelAndView("tenyuan/tenyuan");
		return mav;
	}
	
	@RequestMapping(value = "tenyuan/search", method = RequestMethod.GET)
	public Map<String, Object> search(SearchVo searchVo, Integer page) {
		Map<String, Object> result = new HashMap<>();
		List<TbkCoupon> coupons = ticketService.filterCoupon(searchVo, ticketService.getTicket(searchVo.getName(), page));
		while(coupons.size() < PageConstant.PAGE_SIZE_L) {
			page++;
			coupons.addAll(ticketService.filterCoupon(searchVo, ticketService.getTicket(searchVo.getName(), page)));
		}
		result.put("list", coupons);
		result.put("page", page);
		return result;
	}
	
}
