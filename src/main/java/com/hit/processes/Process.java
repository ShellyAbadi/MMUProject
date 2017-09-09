package com.hit.processes;

import java.util.concurrent.Callable;

import com.hit.memoryunits.MemoryManagementUnit;

public class Process implements Callable<Boolean> {

	public Process(int id, MemoryManagementUnit mmu, ProcessCycles processCycles) {

	}

	public int getId() {
		return 0;
	}

	public void setId(int id) {

	}

	@Override
	public Boolean call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
