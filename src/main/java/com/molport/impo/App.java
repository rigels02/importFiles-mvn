package com.molport.impo;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;

import com.molport.impo.out.OutputFormater;
import com.molport.impo.parsers.IParser_v2;
import com.molport.impo.parsers.Parser_v2;
import com.molport.impo.parsers.Rec;
//import chemaxon.formats.MolImporter
import com.molport.impo.tasks.TasksRunner;

/**
 *
 * @author raitis
 */
public class App {

	// private static Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {

		PropertyConfigurator.configure("log4j.properties");

		ArgsT argsCls = new ArgsT(args);
		String[] inputFiles = argsCls.getInputFiles();
		PrintStream output = null;
		if (inputFiles == null) {
			return;
		}
		try {
			output = argsCls.getOutputStream();
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			return;
		}

		IParser_v2 parser = new Parser_v2();
		OutputFormater oFmt = new OutputFormater();
		PrintStream ps = new PrintStream(output);

		oFmt.printHEADER(ps);

		if (argsCls.isThreading()) {
			System.out.println("Starts parallel processing ...");
			TasksRunner tasksRunner = new TasksRunner();
			tasksRunner.runTasks(inputFiles, parser, oFmt, ps);

		} else {
			System.out.println("Starts sequential processing...");
			for (String filePath : inputFiles) {

				List<Rec> result = parser.doParse(filePath);

				oFmt.outPrint(ps, result);
			}
		}

		ps.close();
	}

}
