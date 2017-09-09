package com.hit.memoryunits;

import java.io.FileNotFoundException;
import java.io.IOException;
import com.hit.algorithm.IAlgoCache;

public class MemoryManagementUnit {

	private static final HardDisk hardDisk;

	static {
		hardDisk = HardDisk.getInstance();
	}

	private IAlgoCache<Long, Long> algoCache;
	private RAM ram;

	public MemoryManagementUnit(int ramCapacity, IAlgoCache<Long, Long> algo) {
		this.ram = new RAM(ramCapacity);
		this.algoCache = algo;
	}

	public Page<byte[]>[] getPages(Long[] pageIds) throws IOException {

		for (Long pageId : pageIds) {
			loadPageToRam(pageId);
		}
		return this.ram.getPages(pageIds);
	}

	private void loadPageToRam(Long pageId) throws FileNotFoundException, IOException {

		Long internalId = algoCache.getElement(pageId);

		// Page already in ram.
		if (internalId != null)
			return;

		// Need to load page to ram.
		if (ram.isFull()) {
			Long pageToRemove = this.algoCache.putElement(pageId, null);
			Page<byte[]> pageMoveToHd = this.ram.getPage(pageToRemove);
			Page<byte[]> newPage = MemoryManagementUnit.hardDisk.pageReplacement(pageMoveToHd, pageId);
			this.ram.addPage(newPage);
			this.algoCache.putElement(pageId, newPage.getPageId());
		} else {
			// ram have space, just load page to ram.
			// get page from ram.
			Page<byte[]> page = MemoryManagementUnit.hardDisk.pageFault(pageId);
			this.algoCache.putElement(pageId, page.getPageId());
			this.ram.addPage(page);
		}
	}
}
