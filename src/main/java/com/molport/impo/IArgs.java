package com.molport.impo;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public interface IArgs {

	String[] getInputFiles();

	PrintStream getOutputStream() throws FileNotFoundException;

}