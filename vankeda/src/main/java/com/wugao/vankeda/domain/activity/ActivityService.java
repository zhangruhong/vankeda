package com.wugao.vankeda.domain.activity;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ActivityService {
	
	@Resource
	ActivityRepo activityRepo;
	
	private static final String ACTIVITY_URL_PREFIX = "/activity/";

	public void save(Activity activity) {
		activity.setClickUrl(ACTIVITY_URL_PREFIX + getRandomCode(20));
		activity.setCreateDate(new Date());
		activity.setUpdateDate(new Date());
		activity.setStatus(activity.getStatus() == null ? false : activity.getStatus());
		activity.setOnBanner(activity.getOnBanner() == null ? false : activity.getOnBanner());
		activityRepo.save(activity);
		
	}

	public void update(Activity activity) {
		Activity savedActivity = activityRepo.getById(activity.getId());
		savedActivity.setTitle(activity.getTitle());
		savedActivity.setOnBanner(activity.getOnBanner());
		savedActivity.setPictPath(activity.getPictPath());
		savedActivity.setStartDate(activity.getStartDate());
		savedActivity.setEndDate(activity.getEndDate());
		savedActivity.setStatus(activity.getStatus());
		savedActivity.setUpdateDate(new Date());
		activityRepo.update(savedActivity);
	}
	
	private String getRandomCode(int length) {
		String result = "";
		for(int i = 0; i < length; i++) {
			int type = (int)(Math.random() * 3);
			switch(type) {
			case 0:
				result += String.valueOf((char)(Math.random() * 26 + 97));
				break;
			case 1:
				result += String.valueOf((char)(Math.random() * 26 + 65));
				break;
			case 2:
				result += String.valueOf((char)(Math.random() * 10 + 48));
				break;
			}
		}
		return result;
	}

	public void remove(String id) {
		Activity savedActivity = activityRepo.getById(id);
		if(!StringUtils.isEmpty(savedActivity.getPictPath())) {
			File file = new File(savedActivity.getPictPath());
			if(file.exists()) {
				file.renameTo(new File(file.getPath() + ".bak"));
				file.delete();
			}
		}
		activityRepo.removeGoodsFromAct(savedActivity.getId(), null);
		activityRepo.remove(id);
		
	}
}
