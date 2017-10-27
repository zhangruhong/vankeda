package com.wugao.vankeda.domain.activity;

import java.io.Serializable;

public class ActivityGoodsMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String activityId;
	private String goodsId;
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	
}
