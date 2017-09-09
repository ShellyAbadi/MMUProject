package com.hit.memoryunits;

import java.io.Serializable;

public class Page<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long pageId;
	private T content;

	public Page(Long id, T content) {
		this.setPageId(id);
		this.setContent(content);
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
