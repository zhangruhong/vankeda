package com.wugao.vankeda.domain.vo.search;

import java.util.ArrayList;
import java.util.List;

public class SearchVo {
	
	public static final String TYPE_NORMAL = "1";//普通
	public static final String TYPE_HIGH = "2";//高佣
	public static final String TYPE_ALL = "-1";//全部
	
	public static final String ORDER_PRICE = "originalPrice";
	
	public static final String ORDER_OFF_RATE = "priceAfterTicket";
	
	public static final String ORDER_SOLD = "soldCountPerMonth";
	
	public static final String ORDER_ASC = "asc";//升序
	
	public static final String ORDER_DESC = "desc";//降序
	
	public static List<String> ORDER_BY = new ArrayList<>();
	public static List<String> ORDER_TYPE = new ArrayList<>();
	static {
		ORDER_BY.add(ORDER_OFF_RATE);
		ORDER_BY.add(ORDER_PRICE);
		ORDER_BY.add(ORDER_SOLD);
		
		ORDER_TYPE.add(ORDER_ASC);
		ORDER_TYPE.add(ORDER_DESC);
	}
	
	private String name;
	
	private String categoryId;
	
	private String categoryPid;
	
	private String lowerPrice;
	
	private String higherPrice;
	
	private String type;
	
	private String orderBy;
	
	private String orderType;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getLowerPrice() {
		return lowerPrice;
	}

	public void setLowerPrice(String lowerPrice) {
		this.lowerPrice = lowerPrice;
	}

	public String getHigherPrice() {
		return higherPrice;
	}

	public void setHigherPrice(String higherPrice) {
		this.higherPrice = higherPrice;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryPid() {
		return categoryPid;
	}

	public void setCategoryPid(String categoryPid) {
		this.categoryPid = categoryPid;
	}

	public String getOrderBy() {
		if(SearchVo.ORDER_BY.contains(this.orderBy)) {
			return this.orderBy;
		}
		return null;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderType() {
		if(SearchVo.ORDER_TYPE.contains(this.orderType)) {
			return this.orderBy;
		}
		return null;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
}
