package com.wugao.vankeda.application.ticket;

import java.util.Arrays;
import java.util.List;

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

@RestController("mobile_ticket")
@RequestMapping
public class TicketController {
	
	@Value("${taobao.api.url}")
	private String url;
	
	@Value("${taobao.lianmeng.appKey}")
	private String lianmengAppKey;
	
	@Value("${taobao.lianmeng.secretKey}")
	private String lianmengSecretKey;
	
	@Value("${tao.adzone.id}")
	private String adzoneId;
	
	@RequestMapping(value = "v/ticket", method = RequestMethod.GET, produces = "text/html")
	public ModelAndView toTaobaoTickPage(String keyword, Integer page) {
		return new ModelAndView("ticket/ticket");
	}
	
	@RequestMapping(value = "ticket/getCoupons", method = RequestMethod.GET)
	public List<TbkCoupon> getCoupons(String keyword, Integer page){
		TaobaoClient client = new DefaultTaobaoClient(url, lianmengAppKey, lianmengSecretKey);
		TbkDgItemCouponGetRequest req = new TbkDgItemCouponGetRequest();
		req.setAdzoneId(Long.valueOf(adzoneId));
		req.setPlatform(1L);
		req.setQ(StringUtils.isEmpty(keyword) ? "" : org.apache.commons.lang.StringUtils.join(HanLP.extractKeyword(keyword, 3).toArray(), " "));
		req.setPageSize(24L);
		req.setPageNo(StringUtils.isEmpty(page) ? 1L: Long.valueOf(page));
		TbkDgItemCouponGetResponse rsp;	
		try {
			rsp = client.execute(req);
			List<TbkCoupon> items = rsp.getResults();
			return items;
		} catch (ApiException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
