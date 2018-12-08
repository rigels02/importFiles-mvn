package com.molport.impo.tasks;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.molport.impo.out.OutputFormater;
import com.molport.impo.parsers.IParser_v2;


public class TasksRunner {
	private static Logger logger = LoggerFactory.getLogger(TasksRunner.class);
	private static final int nThreads = 10;
	
	
	public void runTasks(String[] filePath, IParser_v2 parser, 
						OutputFormater oFmt, PrintStream ps) {
		
		List<ImpoTask> callableTasks = new ArrayList<>();
		List<Future<Void>> futures = new ArrayList<>();
		int remainingTaskNumber= filePath.length;
		
		ExecutorService service = Executors.newFixedThreadPool(nThreads );
		int fileIdx = 0;
		
		while(remainingTaskNumber >0) {
			callableTasks.add(new ImpoTask(filePath[fileIdx], parser, oFmt, ps));
			remainingTaskNumber--;
			fileIdx++;
			
		}
		try {
			futures = service.invokeAll(callableTasks);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		for (Future<Void> future : futures) {
			
			try {
				
				future.get();
				
			}catch (Exception  e) {
				logger.error(e.getMessage());
			}
		}
		service.shutdown();
		
	}
}
