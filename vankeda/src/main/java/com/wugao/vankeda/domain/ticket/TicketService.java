package com.wugao.vankeda.domain.ticket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hankcs.hanlp.HanLP;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import com.taobao.api.response.TbkDgItemCouponGetResponse.TbkCoupon;
import com.wugao.vankeda.domain.vo.search.SearchVo;
import com.wugao.vankeda.support.constants.PageConstant;

@Service
public class TicketService {
	
	@Value("${taobao.api.url}")
	private String url;
	
	@Value("${taobao.lianmeng.appKey}")
	private String lianmengAppKey;
	
	@Value("${taobao.lianmeng.secretKey}")
	private String lianmengSecretKey;
	
	@Value("${tao.adzone.id}")
	private String adzoneId;

	public List<TbkCoupon> getTicket(String keyword, Integer page){
		TaobaoClient client = new DefaultTaobaoClient(url, lianmengAppKey, lianmengSecretKey);
		TbkDgItemCouponGetRequest req = new TbkDgItemCouponGetRequest();
		req.setAdzoneId(Long.valueOf(adzoneId));
		req.setPlatform(1L);
		req.setQ(StringUtils.isEmpty(keyword) ? "" : org.apache.commons.lang.StringUtils.join(HanLP.extractKeyword(keyword, 3).toArray(), " "));
		req.setPageSize(PageConstant.PAGE_SIZE_L);
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
	
	public List<TbkCoupon> filterCoupon(SearchVo searchVo, List<TbkCoupon> coupons){
		List<TbkCoupon> result = new ArrayList<>();
		if(coupons != null && coupons.size() > 0){
			for(TbkCoupon coupon : coupons) {
				boolean isOk = true;
				if(!StringUtils.isEmpty(searchVo.getHigherPrice())) {
					double higherPrice = Double.parseDouble(searchVo.getHigherPrice());
					if(Double.parseDouble(coupon.getZkFinalPrice()) > higherPrice + SearchVo.PRICE_INTERVAL) {
						isOk = false;
					}
				}
				if(!StringUtils.isEmpty(searchVo.getLowerPrice())) {
					double lowerPrice = Double.parseDouble(searchVo.getLowerPrice());
					if(Double.parseDouble(coupon.getZkFinalPrice()) < lowerPrice - SearchVo.PRICE_INTERVAL) {
						isOk = false;
					}
				}
				
				if(isOk) {
					result.add(coupon);
				}
			}
		}
		return result;
	}
}
