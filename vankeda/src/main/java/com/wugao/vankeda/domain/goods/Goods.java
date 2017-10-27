package com.wugao.vankeda.domain.goods;

import java.io.Serializable;
import java.util.Date;

public class Goods implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String mainImageUrl;
	private String detailUrl;
	private String shopName;
	private Double originalPrice;
	private Integer soldCountPerMonth;
	private Double incomingRate;
	private Double incoming;
	private String salerWang;
	private String tbkShortUrl;
	private String tbkLongUrl;
	private String taoToken;
	private Integer ticketTotal;
	private Integer ticketLeft;
	private String ticketValue;
	private Date ticketStartTime;
	private Date ticketEndTime;
	private String ticketUrl;
	private String ticketTaoToken;
	private String ticketShortUrl;
	private Boolean isPromotion;
	private String categoryPid;
	private String categoryId;
	private Boolean status; 
	private Integer type;
	private Double priceAfterTicket;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMainImageUrl() {
		return mainImageUrl;
	}
	public void setMainImageUrl(String mainImageUrl) {
		this.mainImageUrl = mainImageUrl;
	}
	public String getDetailUrl() {
		return detailUrl;
	}
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Double getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}
	public Integer getSoldCountPerMonth() {
		return soldCountPerMonth;
	}
	public void setSoldCountPerMonth(Integer soldCountPerMonth) {
		this.soldCountPerMonth = soldCountPerMonth;
	}
	public Double getIncomingRate() {
		return incomingRate;
	}
	public void setIncomingRate(Double incomingRate) {
		this.incomingRate = incomingRate;
	}
	public Double getIncoming() {
		return incoming;
	}
	public void setIncoming(Double incoming) {
		this.incoming = incoming;
	}
	public String getSalerWang() {
		return salerWang;
	}
	public void setSalerWang(String salerWang) {
		this.salerWang = salerWang;
	}
	public String getTbkShortUrl() {
		return tbkShortUrl;
	}
	public void setTbkShortUrl(String tbkShortUrl) {
		this.tbkShortUrl = tbkShortUrl;
	}
	public String getTbkLongUrl() {
		return tbkLongUrl;
	}
	public void setTbkLongUrl(String tbkLongUrl) {
		this.tbkLongUrl = tbkLongUrl;
	}
	public String getTaoToken() {
		return taoToken;
	}
	public void setTaoToken(String taoToken) {
		this.taoToken = taoToken;
	}
	public Integer getTicketTotal() {
		return ticketTotal;
	}
	public void setTicketTotal(Integer ticketTotal) {
		this.ticketTotal = ticketTotal;
	}
	public Integer getTicketLeft() {
		return ticketLeft;
	}
	public void setTicketLeft(Integer ticketLeft) {
		this.ticketLeft = ticketLeft;
	}
	public String getTicketValue() {
		return ticketValue;
	}
	public void setTicketValue(String ticketValue) {
		this.ticketValue = ticketValue;
	}
	public Date getTicketStartTime() {
		return ticketStartTime;
	}
	public void setTicketStartTime(Date ticketStartTime) {
		this.ticketStartTime = ticketStartTime;
	}
	public Date getTicketEndTime() {
		return ticketEndTime;
	}
	public void setTicketEndTime(Date ticketEndTime) {
		this.ticketEndTime = ticketEndTime;
	}
	public String getTicketUrl() {
		return ticketUrl;
	}
	public void setTicketUrl(String ticketUrl) {
		this.ticketUrl = ticketUrl;
	}
	public String getTicketTaoToken() {
		return ticketTaoToken;
	}
	public void setTicketTaoToken(String ticketTaoToken) {
		this.ticketTaoToken = ticketTaoToken;
	}
	public String getTicketShortUrl() {
		return ticketShortUrl;
	}
	public void setTicketShortUrl(String ticketShortUrl) {
		this.ticketShortUrl = ticketShortUrl;
	}
	public Boolean getIsPromotion() {
		return isPromotion;
	}
	public void setIsPromotion(Boolean isPromotion) {
		this.isPromotion = isPromotion;
	}
	public String getCategoryPid() {
		return categoryPid;
	}
	public void setCategoryPid(String categoryPid) {
		this.categoryPid = categoryPid;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public Double getPriceAfterTicket() {
		return priceAfterTicket;
	}
	public void setPriceAfterTicket(Double priceAfterTicket) {
		this.priceAfterTicket = priceAfterTicket;
	}
	@Override
	public String toString() {
		return "Goods [id=" + id + ", name=" + name + ", mainImageUrl=" + mainImageUrl + ", detailUrl=" + detailUrl
				+ ", shopName=" + shopName + ", originalPrice=" + originalPrice + ", soldCountPerMonth="
				+ soldCountPerMonth + ", incomingRate=" + incomingRate + ", incoming=" + incoming + ", salerWang="
				+ salerWang + ", tbkShortUrl=" + tbkShortUrl + ", tbkLongUrl=" + tbkLongUrl + ", taoToken=" + taoToken
				+ ", ticketTotal=" + ticketTotal + ", ticketLeft=" + ticketLeft + ", ticketValue=" + ticketValue
				+ ", ticketStartTime=" + ticketStartTime + ", ticketEndTime=" + ticketEndTime + ", ticketUrl="
				+ ticketUrl + ", ticketTaoToken=" + ticketTaoToken + ", ticketShortUrl=" + ticketShortUrl
				+ ", isPromotion=" + isPromotion + ", categoryPid=" + categoryPid + ", categoryId=" + categoryId + "]";
	}
	
	
}
