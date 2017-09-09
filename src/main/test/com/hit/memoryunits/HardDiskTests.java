package com.hit.memoryunits;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Hashtable;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class HardDiskTests {

	@Test
	public void testWriteToFile() throws FileNotFoundException, IOException {
		HardDisk disk = HardDisk.getInstance();
		byte[] strBytes = "moshe".getBytes();
		Page<byte[]> page = new Page<byte[]>((long) 1, strBytes);
		disk.pageReplacement(page, (long) 0);

		ObjectInputStream in = new ObjectInputStream(new FileInputStream(HardDisk.DEFAULT_FILE_NAME));
		try {
			Map<Long, Page<byte[]>> pagesOnDisk = (Hashtable<Long, Page<byte[]>>) in.readObject();
			page = pagesOnDisk.get((long) 1);
			String val = new String(page.getContent());
			Assert.assertEquals("moshe", val);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			in.close();
		}
	}

	@Test
	public void testHardDiskCleanup() throws FileNotFoundException, IOException {
		HardDisk disk = HardDisk.getInstance();
		byte[] strBytes = "moshe".getBytes();
		Page<byte[]> page = new Page<byte[]>((long) 1, strBytes);
		disk.pageReplacement(page, (long) 0);

		File file = new File(HardDisk.DEFAULT_FILE_NAME);
		Assert.assertTrue(file.exists());

		disk.clean();
		Assert.assertFalse(file.exists());
	}

	@After
	public void cleanup() {
		HardDisk disk = HardDisk.getInstance();
		disk.clean();
	}
}
