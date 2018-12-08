package com.molport.impo;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Process command line arguments
 * 
 * @author raitis
 */
public class ArgsT implements IArgs{

	private final List<String> args = new ArrayList<>();
	private boolean threading = false;
	private IArgs classArgs;
	
	public ArgsT(String[] args) {
		
		for (String string : args) {
			if(string.toUpperCase().charAt(0) == '-'
					&& string.toUpperCase().charAt(1) == 'T') {
				threading = true;
			}else {
				this.args.add(string);
			}
		}
		String[] args1 = new String[this.args.size()];
		for(int i=0; i< this.args.size(); i++) {
			args1[i] = this.args.get(i);
		}
		this.classArgs = new Args(args1);
	
	}

	private void printUsage() {
		System.out.println(""
				+ "Usage:\n\n" + "[-T] inportFile <sdf files directory> [output file]\n\n"
						+ "where: -T -use threads to process\n\n");
	}

	@Override
	public String[] getInputFiles() {
		if(this.args.size() == 0) {
			printUsage();
			return null;
		}
		return classArgs.getInputFiles();
	}

	@Override
	public PrintStream getOutputStream() throws FileNotFoundException {
		if(this.args.size() == 0) {
			printUsage();
			return null;
		}
		return classArgs.getOutputStream();
	}

	public boolean isThreading() {
		return threading;
	}
	
	
	
	
}
