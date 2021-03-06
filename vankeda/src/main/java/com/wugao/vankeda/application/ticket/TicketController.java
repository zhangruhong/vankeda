package com.wugao.vankeda.application.ticket;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hankcs.hanlp.HanLP;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import com.taobao.api.response.TbkDgItemCouponGetResponse.TbkCoupon;
import com.wugao.vankeda.domain.ticket.TicketService;
import com.wugao.vankeda.domain.vo.search.SearchVo;
import com.wugao.vankeda.support.constants.PageConstant;

@RestController("mobile_ticket")
@RequestMapping
public class TicketController {
	
	@Resource
	TicketService ticketService;
	
	@RequestMapping(value = "v/ticket", method = RequestMethod.GET, produces = "text/html")
	public ModelAndView toTaobaoTickPage() {
		return new ModelAndView("ticket/ticket");
	}
	
	@RequestMapping(value = "ticket/getCoupons", method = RequestMethod.GET)
	public Map<String, Object> getCoupons(SearchVo searchVo, Integer page){
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
