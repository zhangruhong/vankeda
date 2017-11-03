package com.wugao.vankeda.application.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.wugao.vankeda.domain.activity.Activity;
import com.wugao.vankeda.domain.activity.ActivityRepo;
import com.wugao.vankeda.domain.activity.ActivityService;
import com.wugao.vankeda.infrastructure.filestore.FileInfo;
import com.wugao.vankeda.infrastructure.mybatis.Pagination;
import com.wugao.vankeda.infrastructure.utils.ServletUtil;
import com.wugao.vankeda.support.utils.PageUtil;

@RestController
public class AdminController {
	
	@Resource
	ActivityService activityService;
	
	@Resource
	ActivityRepo activityRepo;
	
	@RequestMapping(value = "v/admin/home", method = RequestMethod.GET)
	public ModelAndView toAdminHomePage() {
		return new ModelAndView("admin/home");
	}
	
	@RequestMapping(value = "v/admin/activity", method = RequestMethod.GET)
	public ModelAndView toActivityPage(String title, String startDate, String endDate, Boolean onBanner, Boolean status, Pagination pagination) {
		pagination.setRows(activityRepo.getList(title, startDate, endDate, status, onBanner, pagination));
		ModelAndView modelAndView = new ModelAndView("admin/activity/activity");
		return PageUtil.setPagedView(modelAndView, pagination);
	}
	
	@RequestMapping(value = "admin/activity", method = RequestMethod.POST)
	public void saveActivity(Activity activity) {
		activityService.save(activity);
	}
	
	@RequestMapping(value = "admin/activity/{id}", method = RequestMethod.GET)
	public Activity getActivityById(@PathVariable("id")String id) {
		return activityRepo.getById(id);
	}
	
	@RequestMapping(value = "admin/activity", method = RequestMethod.PUT)
	public void updateActivity(Activity activity) {
		activityService.update(activity);
	}
	
	@RequestMapping(value = "admin/activity/{id}", method = RequestMethod.DELETE)
	public void removeActivity(@PathVariable("id")String id) {
		activityService.remove(id);
	}
	
	@RequestMapping(value = "admin/activity/image", method = RequestMethod.GET)
	public void getActivityPic(String id, HttpServletResponse resp) throws Exception {
		String filePath = activityRepo.getById(id).getPictPath();
		File file = new File(filePath);
		if(file.exists()) {
			byte[] b = new byte[Long.valueOf(file.length()).intValue()];
			FileInputStream fis = new FileInputStream(file);
			fis.read(b);
			FileInfo fileInfo = new FileInfo(file.getName(), b.length , new Date());
			ServletUtil.respondFileInfo(resp, fileInfo);
			ServletUtil.respondBytes(resp, b); 
		}
	}
	
}
