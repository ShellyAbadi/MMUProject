package com.hit.memoryunits;

import java.util.Hashtable;
import java.util.Map;

public class RAM {

	private int capacity;
	private Map<Long, Page<byte[]>> pages;

	public RAM(int initialCapacity) {
		this.capacity = initialCapacity;
		this.pages = new Hashtable<>();
	}

	public void addPage(Page<byte[]> addPage) {
		this.pages.put(addPage.getPageId(), addPage);
	}

	public void addPages(Page<byte[]>[] addPages) {
		for (Page<byte[]> page : addPages) {
			addPage(page);
		}
	}

	public int getInitialCapacity() {
		return this.capacity;
	}

	public Page<byte[]> getPage(Long pageId) {
		Page<byte[]> page = this.pages.get(pageId);
		return page;
	}

	public Map<Long, Page<byte[]>> getPages() {
		Map<Long, Page<byte[]>> clone = new Hashtable<>(this.pages);
		return clone;
	}

	public Page<byte[]>[] getPages(Long[] pageIds) {
		int numberOfPages = pageIds.length;
		Page<byte[]> pages[] = new Page[pageIds.length];

		for (int i = 0; i < numberOfPages; i++) {
			Long id = pageIds[i];
			Page<byte[]> page = this.pages.get(id);
			pages[i] = page;
		}

		return pages;
	}

	public void removePage(Page<byte[]> removePage) {
		this.pages.remove(removePage.getPageId());
	}

	public void removePages(Page<byte[]>[] removePages) {
		for (Page<byte[]> page : removePages) {
			removePage(page);
		}
	}

	public void setInitialCapacity(int initialCapacity) {
		this.capacity = initialCapacity;
	}

	public void setPages(Map<Long, Page<byte[]>> pages) {
		this.pages = pages;
	}

	public boolean isFull() {
		return this.capacity <= this.pages.size();
	}
}
