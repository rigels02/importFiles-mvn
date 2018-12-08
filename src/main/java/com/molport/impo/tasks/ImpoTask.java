package com.molport.impo.tasks;

import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.molport.impo.out.OutputFormater;
import com.molport.impo.parsers.IParser_v2;
import com.molport.impo.parsers.Rec;

public class ImpoTask implements Callable<Void>{

	private static Logger logger = LoggerFactory.getLogger(ImpoTask.class);
	
	private final String filePath;
	private final OutputFormater oFmt;
	private final PrintStream ps;
	private final IParser_v2 parser;
	

	public ImpoTask(String filePath, IParser_v2 parser , OutputFormater oFmt, PrintStream ps) {
		
		this.filePath = filePath;
		this.parser = parser;
		this.oFmt = oFmt;
		this.ps = ps;
		
	}


	@Override
	public Void call() throws Exception {
		logger.info("Task:{} started, Thread: {}, Time(ms): {}",
				filePath,Thread.currentThread().getName(),System.currentTimeMillis());
		List<Rec> result = parser.doParse(filePath);
		
		oFmt.outPrint(ps, result);
		
		logger.info("Task:{} done!, Thread: {}, Time(ms): {}",
				filePath,Thread.currentThread().getName(),System.currentTimeMillis());
		return null;
	}

}
