package com.example.classicmodelsweb.utils;

import org.springframework.stereotype.Component;

@Component
public class Pagination {
	private int page;
	private int resPerPage;
	private int totalRes;
	private int defDispPages;

	public Pagination() {
		// TODO Auto-generated constructor stub
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getResPerPage() {
		return resPerPage;
	}

	public void setResPerPage(int resPerPage) {
		this.resPerPage = resPerPage;
	}

	public int getTotalRes() {
		return totalRes;
	}

	public void setTotalRes(int totalRes) {
		this.totalRes = totalRes;
	}

	public int getDefDispPages() {
		return defDispPages;
	}

	public void setDefDispPages(int defDispPages) {
		this.defDispPages = defDispPages;
	}

	public int[] get() {
		int nPages = this.totalRes / this.resPerPage;
		if (this.totalRes % this.resPerPage > 0)
			++nPages;
		int displayPages = Math.min(defDispPages, nPages);

		int[] pagination = new int[displayPages];
		int lmid = displayPages / 2 + 1, rmid = nPages - lmid;

		if (this.page > 0 && this.page <= lmid)
			for (int i = 0, start = 1; i < displayPages; ++i)
				pagination[i] = start++;
		else if (this.page > rmid && this.page <= nPages)
			for (int i = displayPages - 1, start = nPages; i >= 0; --i)
				pagination[i] = start--;
		else
			for (int i = 0, start = page - lmid; i < displayPages; ++i)
				pagination[i] = ++start;

		return pagination;
	}
}
