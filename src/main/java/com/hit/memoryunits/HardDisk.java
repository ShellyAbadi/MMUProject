package com.hit.memoryunits;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;
import java.util.Map;

public class HardDisk {

	public static final int _SIZE = 500;
	public static final String DEFAULT_FILE_NAME = "pages.pg";

	private static HardDisk instance = null;
	private Map<Long, Page<byte[]>> pagesOnDisk;

	public static HardDisk getInstance() {
		if (instance != null)
			return instance;

		synchronized (HardDisk.class) {
			if (instance == null)
				instance = new HardDisk();
		}

		return instance;
	}

	private HardDisk() {
		this.pagesOnDisk = new Hashtable<>();
		File yourFile = new File(DEFAULT_FILE_NAME);
		try {
			yourFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadFromDisk() throws FileNotFoundException, IOException {
		File yourFile = new File(DEFAULT_FILE_NAME);
		if (yourFile.length() == 0)
			return;

		ObjectInputStream in = new ObjectInputStream(new FileInputStream(DEFAULT_FILE_NAME));
		try {
			this.pagesOnDisk = (Hashtable<Long, Page<byte[]>>) in.readObject();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			in.close();
		}
	}

	private void writeToDisk() throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DEFAULT_FILE_NAME));
		try {
			out.writeObject(this.pagesOnDisk);
			out.flush();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			out.close();
		}
	}

	public Page<byte[]> pageFault(Long pageId) throws FileNotFoundException, IOException {
		loadFromDisk();
		Page<byte[]> currPage = pagesOnDisk.get(pageId);
		return currPage;
	}

	public Page<byte[]> pageReplacement(Page<byte[]> moveToHdPage, Long moveToRamId)
			throws FileNotFoundException, IOException {
		loadFromDisk();
		this.pagesOnDisk.put(moveToHdPage.getPageId(), moveToHdPage);
		writeToDisk();
		Page<byte[]> currPage = pagesOnDisk.get(moveToRamId);
		return currPage;
	}

	public void clean() {
		File file = new File(DEFAULT_FILE_NAME);
		if (file.exists())
			file.delete();
	}
}
