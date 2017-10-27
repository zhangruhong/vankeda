package com.wugao.vankeda.domain.goods;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.TbkEvent;
import com.taobao.api.domain.TbkFavorites;
import com.taobao.api.domain.UatmTbkItem;
import com.taobao.api.request.TbkUatmEventGetRequest;
import com.taobao.api.request.TbkUatmEventItemGetRequest;
import com.taobao.api.request.TbkUatmFavoritesGetRequest;
import com.taobao.api.request.TbkUatmFavoritesItemGetRequest;
import com.taobao.api.response.TbkDgItemCouponGetResponse.TbkCoupon;
import com.taobao.api.response.TbkUatmEventGetResponse;
import com.taobao.api.response.TbkUatmEventItemGetResponse;
import com.taobao.api.response.TbkUatmFavoritesGetResponse;
import com.taobao.api.response.TbkUatmFavoritesItemGetResponse;
import com.wugao.vankeda.domain.category.Category;
import com.wugao.vankeda.domain.category.CategoryRepo;

@Validated
@Service
public class GoodsService {
	
	private static final String YES_CHINESE = "是";
	private static final String NO_CHINESE = "否";
	
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
	CategoryRepo categoryRepo;
	
	private DecimalFormat decimalFormat = new DecimalFormat("0.00");

	public Goods saveGoods(@Valid Goods goods) {
		return goodsRepo.save(goods);
	}
	
	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

	public void updateGoods(String id, @Valid Goods goods) {
		// 保存更新
		goodsRepo.update(goods);
	}
	
	public void saveBatchFromExcel() {
		List<Goods> toBeAddList = new ArrayList<>();
		try {
			File directory = new File(this.getClass().getClassLoader().getResource("").getPath() + File.separator + "xlsx" );
			if(directory.isDirectory()) {
				System.out.println(directory.getAbsolutePath());
			}else {
				directory.mkdir();
			}
			File[] excelFiles = directory.listFiles();
			for(File f : excelFiles) {
				HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(f));
				HSSFSheet sheet = workbook.getSheetAt(0);
				sheet.getFirstRowNum();
				for(int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
					HSSFRow row = sheet.getRow(i);
					Goods goods = new Goods();
					goods.setId(row.getCell(0).getStringCellValue());
					goods.setName(row.getCell(1).getStringCellValue());
					goods.setMainImageUrl(row.getCell(2).getStringCellValue());
					goods.setDetailUrl(row.getCell(3).getStringCellValue());
					goods.setShopName(row.getCell(12).getStringCellValue());
					goods.setOriginalPrice(Double.valueOf(row.getCell(6).getStringCellValue()));
					goods.setSoldCountPerMonth(Integer.valueOf(row.getCell(7).getStringCellValue()));
					goods.setIncomingRate(Double.valueOf(row.getCell(8).getStringCellValue())/ 100);
					goods.setIncoming(Double.valueOf(row.getCell(9).getStringCellValue()));
					goods.setSalerWang(row.getCell(10).getStringCellValue());
					goods.setTbkShortUrl(null);
					goods.setTbkLongUrl(row.getCell(5).getStringCellValue());
					goods.setTaoToken(null);
					goods.setTicketTotal(Integer.valueOf(row.getCell(15).getStringCellValue()));
					goods.setTicketLeft(Integer.valueOf(row.getCell(16).getStringCellValue()));
					goods.setTicketValue(row.getCell(17).getStringCellValue());
					goods.setTicketStartTime(StringUtils.isEmpty(row.getCell(18) == null ? null : row.getCell(16).getStringCellValue()) ? null : fmt.parse(row.getCell(16).getStringCellValue()));
					goods.setTicketEndTime(StringUtils.isEmpty(row.getCell(19) == null ? null : row.getCell(17).getStringCellValue()) ? null : fmt.parse(row.getCell(17).getStringCellValue()));
					goods.setTicketUrl(row.getCell(21) == null ? null : row.getCell(18).getStringCellValue());
					goods.setTicketTaoToken(null);
					goods.setTicketShortUrl(null);
					goods.setIsPromotion(null);
					toBeAddList.add(goods);
				}
				goodsRepo.saveBatch(toBeAddList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Scheduled(cron = "0 0 0 * * ?")
	public void saveBatchFromFavorite() throws Exception {
		goodsRepo.removeAll();
		//顶级分类
		Map<String, Category> categoryMap = getCategoryMap(categoryRepo.getTopCategory());
		//最终商品列表
		List<Goods> list = new ArrayList<>();
		Map<String, Goods> map = new HashMap<String, Goods>();
		TaobaoClient client = new DefaultTaobaoClient(url, lianmengAppKey, lianmengSecretKey);
		//选品库请求
		TbkUatmFavoritesGetRequest req = new TbkUatmFavoritesGetRequest();
		//选聘库参数
		req.setPageNo(1L);
		req.setPageSize(200L);
		req.setFields("favorites_title,favorites_id,type");
		req.setType(-1L);
		//选聘库响应
		TbkUatmFavoritesGetResponse favoriteResponse = client.execute(req);
		List<TbkFavorites> favorites = favoriteResponse.getResults();
		//遍历选品库
		for(TbkFavorites f : favorites) {
			Long favoriteId = f.getFavoritesId();
			//选品库命名规则 一级分类-二级分类-选品库编号
			String[] favoriteFileds = f.getFavoritesTitle().split("-");
			//对应本地数据库分类
			Category category = categoryMap.get(favoriteFileds[0]); 
			Map<String, Category> subCategoryMap = new HashMap<>();
			if(category != null) {
				subCategoryMap = getCategoryMap(categoryRepo.getChildren(category.getId()));
			}
			Category subCategory = subCategoryMap.get(favoriteFileds[1]); 
			//遍历该选品库的商品
			for(long i = 1; i <= 2; i++) {
				TbkUatmFavoritesItemGetRequest req2 = new TbkUatmFavoritesItemGetRequest();
				req2.setPlatform(1L);
				req2.setPageSize(100L);
				req2.setAdzoneId(Long.valueOf(adzoneId));
				req2.setFavoritesId(favoriteId);
				req2.setPageNo(i);
				req2.setFields("num_iid,title,pict_url,click_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick,shop_title,zk_final_price_wap,event_start_time,event_end_time,tk_rate,status,type,category, commission_rate, coupon_click_url,coupon_start_time, coupon_end_time, coupon_info, coupon_remain_count, coupon_total_count");
				TbkUatmFavoritesItemGetResponse rsp2 = client.execute(req2);
				List<UatmTbkItem> items = rsp2.getResults();
				//连接异常时自动重连10次
				int tryTimes = 0;
				while(items == null) {
					rsp2 = client.execute(req2);
					items = rsp2.getResults();
					tryTimes++;
					if(tryTimes == 10) {
						break;
					}
				}
				//由响应的UatmTbkItem转换为本地Goods类
				if(items != null && items.size() > 0) {
					for(UatmTbkItem item : items) {
						//之前没保存过，状态有效，剩余量大于0才入库
						if(!map.containsKey(item.getNumIid().toString()) && item.getStatus() == 1  && item.getCouponRemainCount() > 0) {
							Goods goods = new Goods();
							goods.setId(item.getNumIid().toString());
							goods.setDetailUrl(item.getItemUrl());
							goods.setOriginalPrice(Double.valueOf(item.getZkFinalPrice()));
							goods.setShopName(item.getShopTitle());
							goods.setSoldCountPerMonth(item.getVolume().intValue());
							goods.setIncomingRate(Double.valueOf(item.getTkRate()) / 100);
							goods.setName(item.getTitle());
							goods.setIncoming(Double.valueOf(decimalFormat.format(goods.getOriginalPrice() * goods.getIncomingRate())));
							goods.setMainImageUrl(item.getPictUrl());
							goods.setTbkLongUrl(item.getClickUrl());
							goods.setTaoToken(null);
							goods.setTicketTotal(item.getCouponTotalCount() == null ? 0 : item.getCouponTotalCount().intValue());
							goods.setTicketLeft(item.getCouponRemainCount() == null ? 0 : item.getCouponRemainCount().intValue());
							goods.setTicketStartTime(StringUtils.isEmpty(item.getCouponStartTime()) ? null : fmt.parse(item.getCouponStartTime()));
							goods.setTicketEndTime(StringUtils.isEmpty(item.getCouponEndTime())? null : fmt.parse(item.getCouponEndTime()));
							goods.setTicketValue(item.getCouponInfo());
							goods.setTicketUrl(item.getCouponClickUrl());
							goods.setStatus(true);
							goods.setType(f.getType().intValue());
							if(!StringUtils.isEmpty(item.getCouponInfo()) && item.getCouponInfo().indexOf("减") != -1) {
								goods.setPriceAfterTicket(Double.valueOf(decimalFormat.format(Double.valueOf(item.getZkFinalPrice()) - Double.valueOf(item.getCouponInfo().split("减")[1]))));
							}
							if(category != null) {
								goods.setCategoryPid(category.getId());
							}
							if(subCategory != null) {
								goods.setCategoryId(subCategory.getId());
							}
							map.put(goods.getId(), goods);
							
							list.add(goods);
						}
					}
				}
			}
		}
		goodsRepo.saveBatch(list);
	}

	/**
	 * 转换直接获取的券
	 * @param items
	 * @return
	 */
	public List<Goods> parseCouponToGoods(List<TbkCoupon> items){
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		List<Goods> list = new ArrayList<Goods>();
		for(TbkCoupon item : items) {
			Goods goods = new Goods();
			goods.setId(item.getNumIid().toString());
			goods.setName(item.getTitle());
			goods.setMainImageUrl(item.getPictUrl());
			goods.setDetailUrl(item.getItemUrl());
			goods.setOriginalPrice(Double.valueOf(item.getZkFinalPrice()));
			goods.setShopName(item.getShopTitle());
			goods.setSoldCountPerMonth(item.getVolume().intValue());
			goods.setIncomingRate(Double.valueOf(item.getCommissionRate()) / 100);
			goods.setIncoming(Double.valueOf(decimalFormat.format(goods.getOriginalPrice() * goods.getIncomingRate())));
			goods.setSalerWang(item.getNick());
			goods.setTbkLongUrl(item.getCouponClickUrl());
			goods.setTbkShortUrl(item.getCouponClickUrl());
			goods.setTaoToken(null);
			goods.setTicketTotal(item.getCouponTotalCount().intValue());
			goods.setTicketLeft(item.getCouponRemainCount().intValue());
			goods.setTicketValue(item.getCouponInfo());
			goods.setTicketUrl(item.getCouponClickUrl());
			if(!StringUtils.isEmpty(item.getCouponInfo()) && item.getCouponInfo().indexOf("减") != -1) {
				goods.setPriceAfterTicket(Double.valueOf(decimalFormat.format(Double.valueOf(item.getZkFinalPrice()) - Double.valueOf(item.getCouponInfo().split("减")[1]))));
			}
			list.add(goods);
		} 
		return list;
	}

	/**
	 * 暂时不做定向招商
	 * @throws Exception
	 */
	@Deprecated
	public void saveBatchFromEvent() throws Exception {
		List<Goods> list = new ArrayList<>();
		Map<String, Goods> map = new HashMap<String, Goods>();
		TaobaoClient client = new DefaultTaobaoClient(url, lianmengAppKey, lianmengSecretKey);
		for(int i = 1; i< 10000; i++) {
			TbkUatmEventGetRequest eventReq = new TbkUatmEventGetRequest();
			eventReq.setPageNo((long)i);
			eventReq.setPageSize(20L);
			eventReq.setFields("event_id,event_title,start_time,end_time");
			TbkUatmEventGetResponse eventRsp = client.execute(eventReq);
			List<TbkEvent> events = eventRsp.getResults();
			int retryTime = 0;
			while((events == null || events.size() == 0) && retryTime < 10) {
				retryTime++;
				eventRsp = client.execute(eventReq);
				events = eventRsp.getResults();
			} 
			if(events == null) {
				return;
			}
			for(TbkEvent event : events) {
				if(event.getEndTime().before(new Date()) && event.getStartTime().after(new Date())) {
					TbkUatmEventItemGetRequest req = new TbkUatmEventItemGetRequest();
					req.setPlatform(1L);
					req.setPageSize(100L);
					req.setAdzoneId(Long.valueOf(adzoneId));
					req.setEventId(event.getEventId());
					req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick,shop_title,zk_final_price_wap,event_start_time,event_end_time,tk_rate,type,status");
					for(int j = 1; j < 10000; j++) {
						req.setPageNo((long)j);
						TbkUatmEventItemGetResponse rsp = client.execute(req);
						List<UatmTbkItem> items = rsp.getResults();
						for(UatmTbkItem item : items) {
							if(!map.containsKey(item.getNumIid().toString())) {
								Goods goods = new Goods();
								goods.setId(item.getNumIid().toString());
								goods.setDetailUrl(item.getItemUrl());
								goods.setOriginalPrice(Double.valueOf(item.getZkFinalPrice()));
								goods.setShopName(item.getShopTitle());
								goods.setSoldCountPerMonth(item.getVolume().intValue());
								goods.setIncomingRate(Double.valueOf(item.getTkRate()) / 100);
								goods.setName(item.getTitle());
								goods.setIncoming(Double.valueOf(decimalFormat.format(goods.getOriginalPrice() * goods.getIncomingRate())));
								goods.setMainImageUrl(item.getPictUrl());
								goods.setTbkLongUrl(item.getClickUrl());
								goods.setTaoToken(null);
								goods.setTicketTotal(item.getCouponTotalCount() == null ? 0 : item.getCouponTotalCount().intValue());
								goods.setTicketLeft(item.getCouponRemainCount() == null ? 0 : item.getCouponRemainCount().intValue());
								goods.setTicketStartTime(StringUtils.isEmpty(item.getCouponStartTime()) ? null : fmt.parse(item.getCouponStartTime()));
								goods.setTicketEndTime(StringUtils.isEmpty(item.getCouponEndTime())? null : fmt.parse(item.getCouponEndTime()));
								goods.setTicketValue(item.getCouponInfo());
								goods.setTicketUrl(item.getCouponClickUrl());
								goods.setStatus(item.getStatus() == 1L);
								map.put(goods.getId(), goods);
								
								list.add(goods);
							}
						}
						if(items.size() < req.getPageSize()) {
							break;
						}
					}
				}
				if(events.size() < eventReq.getPageSize()) {
					break;
				}
			}
		}
		goodsRepo.saveBatch(list);
	}
	
	public Map<String, Category> getCategoryMap(List<Category> categories){
		Map<String, Category> result = new HashMap<>();
		for(Category c : categories) {
			result.put(c.getName(), c);
		}
		return result;
	}
	
	
}
