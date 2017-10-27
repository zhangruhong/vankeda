package com.wugao.vankeda.infrastructure.mybatis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.github.pagehelper.Page;

public class Pagination implements Serializable{

	private static final long serialVersionUID = 1L;

	private int page = 1;

	private int pages = 1;

	private int pageSize = 10;

	private int total = 0;

	private List<?> rows = new ArrayList<Object>();

	public Pagination() {

	}

	public int calculateOffset() {
		int offset = 0;
		try {
			if (page < 1)
				page = 1;
			if (page > (int) Math.ceil((double) total / pageSize))
				page = (int) Math.ceil((double) total / pageSize);
			offset = (page - 1) * pageSize;
			if (offset < 0)
				offset = 0;
		} catch (Exception e) {
		}
		return offset;
	}

	public <T> List<T> doPaging(List<T> totalList) {
		total = totalList.size();
		int fromIndex = calculateOffset();
		int toIndex = fromIndex + pageSize;
		if (toIndex > total) {
			toIndex = total;
		}
		return totalList.subList(fromIndex, toIndex);
	}

	public RowBounds toRowBounds() {
		return new RowBounds(page, pageSize);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;

	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public Pagination setRows(List<?> rows) {
		if (rows instanceof Page<?>) {
			Page<?> page = (Page<?>) rows;
			this.total = (int) page.getTotal();
			this.pages = (int) Math.ceil((double) total / pageSize);
			if (this.pages < 1) {
				this.pages = 1;
			}
			this.rows = page.getResult();
		} else {
			this.rows = rows;
		}
		return this;
	}

}