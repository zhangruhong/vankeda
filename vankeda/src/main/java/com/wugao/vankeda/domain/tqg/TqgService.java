package com.wugao.vankeda.domain.tqg;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkJuTqgGetRequest;
import com.taobao.api.response.TbkJuTqgGetResponse;
import com.taobao.api.response.TbkJuTqgGetResponse.Results;
import com.wugao.vankeda.application.tqg.TqgController;
import com.wugao.vankeda.domain.vo.search.SearchVo;
import com.wugao.vankeda.support.constants.PageConstant;

@Service
public class TqgService {
	
	private static final Log logger = LogFactory.getLog(TqgController.class);
	
	@Value("${taobao.api.url}")
	private String url;
	
	@Value("${taobao.lianmeng.appKey}")
	private String lianmengAppKey;
	
	@Value("${taobao.lianmeng.secretKey}")
	private String lianmengSecretKey;
	
	@Value("${tao.adzone.id}")
	private String adzoneId;

	public List<Results> getTqgResult(Integer page){
		TaobaoClient client = new DefaultTaobaoClient(url, lianmengAppKey, lianmengSecretKey);
		TbkJuTqgGetRequest req = new TbkJuTqgGetRequest();
		req.setAdzoneId(Long.parseLong(adzoneId));
		req.setFields("click_url,pic_url,reserve_price,zk_final_price,total_amount,sold_num,title,category_name,start_time,end_time");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -5);
		req.setStartTime(calendar.getTime());
		calendar.add(Calendar.DATE, 15);
		req.setEndTime(calendar.getTime());
		req.setPageNo((long)(StringUtils.isEmpty(page) ? 1 : page));
		req.setPageSize(PageConstant.PAGE_SIZE_M);
		TbkJuTqgGetResponse rsp;
		try {
			rsp = client.execute(req);
			return rsp.getResults();
		} catch (ApiException e) {
			logger.warn("getTqgResult error:" + e.getMessage());
			return null;
		}
	}
	
	public List<Results> filterTqgResult(SearchVo searchVo, List<Results> objs){
		List<Results> result = new ArrayList<>();
		if(objs != null && objs.size() > 0) {
			for(Results obj : objs) {
				boolean isOk = true;
				if(StringUtils.isEmpty(obj.getClickUrl())) {
					isOk = false;
				}
				if(!StringUtils.isEmpty(searchVo.getHigherPrice())) {
					double higherPrice = Double.parseDouble(searchVo.getHigherPrice());
					if(Double.parseDouble(obj.getZkFinalPrice()) > higherPrice + SearchVo.PRICE_INTERVAL) {
						isOk = false;
					}
				}
				if(!StringUtils.isEmpty(searchVo.getLowerPrice())) {
					double lowerPrice = Double.parseDouble(searchVo.getLowerPrice());
					if(Double.parseDouble(obj.getZkFinalPrice()) < lowerPrice - SearchVo.PRICE_INTERVAL) {
						isOk = false;
					}
				}
				
				if(isOk) {
					result.add(obj);
				}
			}
		}
		return result;
	}
}
