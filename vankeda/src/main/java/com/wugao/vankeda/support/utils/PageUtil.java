package com.wugao.vankeda.support.utils;

import org.springframework.web.servlet.ModelAndView;

import com.wugao.vankeda.infrastructure.mybatis.Pagination;
import com.wugao.vankeda.support.constants.PageConstant;


public class PageUtil {
	
	/**
	 * 根据pagination返回新的modelAndView
	 * @param mav
	 * @param pagination
	 * @return
	 */
	public static ModelAndView setPagedView(ModelAndView mav, Pagination pagination) {
		mav.addObject("currPage", pagination.getPage());
		mav.addObject("beginPage", pagination.getPage() % PageConstant.PAGE_SHOW_COUNT == 0 ? pagination.getPage() - (PageConstant.PAGE_SHOW_COUNT - 1) : pagination.getPage() / PageConstant.PAGE_SHOW_COUNT * PageConstant.PAGE_SHOW_COUNT + 1);
		mav.addObject("endPage", pagination.getPage() % PageConstant.PAGE_SHOW_COUNT == 0 ? pagination.getPage() : (pagination.getPage() + PageConstant.PAGE_SHOW_COUNT) / PageConstant.PAGE_SHOW_COUNT * PageConstant.PAGE_SHOW_COUNT);
		mav.addObject("pages", pagination.getPages());
		mav.addObject("objs", pagination.getRows());
		return mav;
	}

}
