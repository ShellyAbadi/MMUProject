package com.hit.memoryunits;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;

public class MemoryManagementUnitTests {

	@Before
	public void init() {
		HardDisk disk = HardDisk.getInstance();
		disk.clean();
	}

	@Test
	public void testSingleInsert() throws FileNotFoundException, IOException {

		IAlgoCache<Long, Long> algo = new LRUAlgoCacheImpl<>(2);

		MemoryManagementUnit mmu = new MemoryManagementUnit(2, algo);

		HardDisk disk = HardDisk.getInstance();
		byte[] strBytes = "moshe".getBytes();
		Page<byte[]> page = new Page<byte[]>((long) 1, strBytes);
		disk.pageReplacement(page, (long) 0);

		byte[] strBytes2 = "moshe2".getBytes();
		page = new Page<byte[]>((long) 2, strBytes2);
		disk.pageReplacement(page, (long) 0);

		byte[] strBytes3 = "moshe3".getBytes();
		page = new Page<byte[]>((long) 3, strBytes3);
		disk.pageReplacement(page, (long) 0);

		mmu.getPages(new Long[] { (long) 1 });
		Long key1 = algo.getElement((long) 1);
		Assert.assertEquals(new Long(1), key1);

		mmu.getPages(new Long[] { (long) 2 });
		mmu.getPages(new Long[] { (long) 3 });

		key1 = algo.getElement((long) 1);

		Long key2 = algo.getElement((long) 2);
		Long key3 = algo.getElement((long) 3);

		Assert.assertNull(key1);
		Assert.assertEquals(new Long(2), key2);
		Assert.assertEquals(new Long(3), key3);
	}
	
	@After
	public void cleanup() {
		HardDisk disk = HardDisk.getInstance();
		disk.clean();
	}
}
