package com.molport.impo.tasks;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Test;

import com.molport.impo.out.OutputFormater;
import com.molport.impo.parsers.IParser_v2;
import com.molport.impo.parsers.Parser_v2;
import com.molport.impo.parsers.Rec;

public class TasksRunnerTest {
	
	private  int taskNum=0;

	class Task implements Callable<String>{

		private int id;
		
		public Task(int id) {
			this.id = id;
		}

		@Override
		public String call() throws Exception {
			System.out.println("Started task NUM: "+id+", "+
					Thread.currentThread().getName()+
					"Time(ms):\t"+System.currentTimeMillis());
			Thread.sleep(1000);
			long time = System.currentTimeMillis();
			return "Task: "+id+" done! "+Thread.currentThread().getName()+" Time(ms):\t"+time;
		}
		
		
	}
	
	@Test
	public void test_ThreadsAndPool() {
		
		List<Callable<String>> callableTasks = new ArrayList<>();
		List<Future<String>> futures = new ArrayList<>();
		int remainingTaskNumber= 10;
		
		ExecutorService service = Executors.newFixedThreadPool(5 );
		
		
		while(remainingTaskNumber >0) {
			taskNum++;
			callableTasks.add(new Task(taskNum));
			remainingTaskNumber--;
			
		}
		try {
			futures = service.invokeAll(callableTasks);
			System.out.println(">>>>>>>>>All tasks invoked........");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Future<String> future : futures) {
			
			try {
				
				System.out.println("-> "+future.get());
				
			}catch (Exception  e) {
				System.err.println(e.getMessage());
			}
		}

		
	}
	
	@Test
	public void test_TaskRunner_with_ImpoTasks() {
		String[] files = new String[] {
			"src/test/data/input_02.sdf",
			"src/test/data/input_2.sdf",
			"src/test/data/input_3.sdf",
			"src/test/data/input_4.sdf",
			"src/test/data/input_5.sdf"
		}; 
		
		
		Parser_v2 parser = new Parser_v2();
		OutputFormater oFmt = new OutputFormater();
		PrintStream ps = null;
		try {
			ps = new PrintStream("src/test/data/output_1.txt");
			oFmt.printHEADER(ps);
			
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
			
		}
		TasksRunner tasksRunner = new TasksRunner();
		tasksRunner.runTasks(files, parser, oFmt, ps);
	}

	@Test
	public void test_TaskRunner_with_ImpoTasks_1() {
		String[] files = new String[] {
			"src/test/data1/file_01.sdf",
			"src/test/data1/file_02.sdf",
			"src/test/data1/file_03.sdf",
			"src/test/data1/file_04.sdf",
			"src/test/data1/file_05.sdf"
		}; 
		
		
		Parser_v2 parser = new Parser_v2();
		OutputFormater oFmt = new OutputFormater();
		PrintStream ps = null;
		try {
			ps = new PrintStream("src/test/data/output_1.txt");
			oFmt.printHEADER(ps);
			
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
			
		}
		TasksRunner tasksRunner = new TasksRunner();
		tasksRunner.runTasks(files, parser, oFmt, ps);
	}
	
	/*************************************************************************************
	private void parsing(String[] files, IParser_v2 parser, OutputFormater oFmt, PrintStream ps) {
		
		for (String filePath : files) {
			List<Rec> result = parser.doParse(filePath);
			oFmt.outPrint(ps, result);
		}
	}
	
	private void tasksRun(String[] files, IParser_v2 parser, OutputFormater oFmt, PrintStream ps) {
		TasksRunner tasksRunner = new TasksRunner();
		tasksRunner.runTasks(files, parser, oFmt, ps);
	}
	
	@Test
	public void test_compare_results_Seq_Parallel() {
		String[] files = new String[] {
				"src/test/data1/file_01.sdf",
				"src/test/data1/file_02.sdf",
				"src/test/data1/file_03.sdf",
				"src/test/data1/file_04.sdf",
				"src/test/data1/file_05.sdf"
			}; 
			List<String> fnLst = new ArrayList<>();
			for (String line : files) {
				String[] tokens = line.split("/");
				fnLst.add(tokens[tokens.length-1]);
			}
			
			Parser_v2 parser = new Parser_v2();
			OutputFormater oFmt = new OutputFormater();
			PrintStream ps = null;
			try {
				ps = new PrintStream("src/test/data/cmp/output_1.txt");
				oFmt.printHEADER(ps);
				
			} catch (FileNotFoundException e) {
				fail(e.getMessage());
				
			}
			parsing(files, parser, oFmt, ps);
			ps.close();
			
			
			try {
				ps = new PrintStream("src/test/data/cmp/output_2.txt");
				oFmt.printHEADER(ps);
				
			} catch (FileNotFoundException e) {
				fail(e.getMessage());
				
			}
			
			TasksRunner tasksRunner = new TasksRunner();
			tasksRunner.runTasks(files, parser, oFmt, ps);
			ps.close();
			
			//process and split output_1
			String result = "src/test/data/cmp/output_1.txt".replaceAll("[^0-9]", "").trim();
		  List<String> outFlst_1 = split_output("src/test/data/cmp/output_1.txt",fnLst);
		 //process and split output_2
		  List<String> outFlst_2 = split_output("src/test/data/cmp/output_2.txt",fnLst);
		  
		  assertTrue(outFlst_1.size() == outFlst_2.size());
		  
		  for (int i= 0; i<outFlst_1.size(); i++) {
			try {
				fileCMP(outFlst_1.get(i), outFlst_2.get(i));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void fileCMP(String file1, String file2) throws FileNotFoundException {
		BufferedReader fr1 = new BufferedReader(new FileReader(file1));
		BufferedReader fr2 = new BufferedReader(new FileReader(file2));
		List<String> lst1 = fr1.lines().collect(Collectors.toList());
		List<String> lst2 = fr2.lines().collect(Collectors.toList());
		assertTrue(lst1.size() == lst2.size());
		for(int i=0; i< lst1.size(); i++) {
			assertTrue(lst1.get(i).equals(lst2.get(i)));
		}
	}

	private List<String> split_output(String outputFile, List<String> fnLst) {
		List<String> outFilesList = new ArrayList<>();
		
		String Idx = outputFile.replaceAll("[^0-9]", "").trim();
		try {
			 //FileReader fr = new FileReader("src/test/data/cmp/output_1.txt");
			FileReader fr = new FileReader(outputFile);
			 
			 BufferedReader buf_r = new BufferedReader(fr);
			List<String> input = buf_r.lines().collect(Collectors.toList());
			fr.close();
			 
			 for (String fn : fnLst) {
				 String outF = "src/test/data/cmp/"+fn+"_"+Idx;
				 outFilesList.add(outF);
				FileWriter fw = new FileWriter(outF);
				 
				 for (String line : input) {
				
					 if(line.contains(fn)) {
						 fw.write(line+"\n");
						 
					 }
				 }
				 fw.close();
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outFilesList;
	}
	*************************************************************/
	
	
}
