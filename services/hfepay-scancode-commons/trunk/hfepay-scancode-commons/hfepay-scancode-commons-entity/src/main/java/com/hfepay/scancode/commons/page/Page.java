package com.hfepay.scancode.commons.page;

import java.io.Serializable;

public class Page implements Serializable {
	private static final long serialVersionUID = 1L;
	private int currentPage=1;
	private int pageSize=10;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentPage;
		result = prime * result + pageSize;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Page other = (Page) obj;
		if (currentPage != other.currentPage)
			return false;
		if (pageSize != other.pageSize)
			return false;
		return true;
	}



}
