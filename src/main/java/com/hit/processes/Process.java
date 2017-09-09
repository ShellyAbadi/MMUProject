package com.hit.processes;

import java.util.List;
import java.util.concurrent.Callable;

import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.memoryunits.Page;

public class Process implements Callable<Boolean> {
	MemoryManagementUnit mmu;
	int processId;
	ProcessCycles processCycles;
	
	public Process(int id, MemoryManagementUnit mmu, ProcessCycles processCycles) {
		this.processId = id;
		this.mmu = mmu;
		this.processCycles = processCycles;
	}

	public int getId() {
		return processId;
	}

	public void setId(int id) {
		this.processId = id;
	}

	@Override
	public Boolean call() throws Exception {
		try
		{
			for (ProcessCycle cycle : processCycles.getProcessCycles())
			{
				Long[] pagesIds = (Long[]) cycle.getPages().toArray();
				Page[] pages = mmu.getPages(pagesIds);
				List<byte[]> data = cycle.getData();
				int index = 0;
					for (Page page : pages)
					{
						page.setContent(data.get(index));
						index++;
					}
				Thread.sleep(cycle.getSleepMs());
			}
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}

}
