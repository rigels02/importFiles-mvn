package com.molport.impo;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author raitis
 */
public class ArgsIT {
    

    /**
     * Test of getInputFiles method, of class Args.
     */
    @Test
    public void testGetInputFiles()  {
      
       IArgs arg = new Args(new String[]{});
        String[] list = arg.getInputFiles();
        assertTrue(list == null);
        
        try {
           PrintStream ps = arg.getOutputStream();
           ps.println("ok");
            assertTrue(ps != null);
           //new Args(Arrays.asList(new String[]{}));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArgsIT.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Input dir given
        arg = new Args(new String[]{"src/test/data"});
        list = arg.getInputFiles();
        assertTrue(list != null);
        PrintStream ps;
        try {
            ps = arg.getOutputStream();
             ps.println("ok");
            assertTrue(ps != null);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArgsIT.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Input dir given and output file
        arg = new Args(new String[]{"src/test/data","src/test/data/output.txt"});
        list = arg.getInputFiles();
        assertTrue(list != null);
        
        try {
            ps = arg.getOutputStream();
             ps.println("ok"); 
             ps.close();
            assertTrue(ps != null);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArgsIT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void test_ArgsT() throws FileNotFoundException {
    	
    	String args[]= new String[] {"input.txt", "output.txt"};
    	ArgsT clArgs = new ArgsT(args);
    	String[] input = clArgs.getInputFiles();
    	PrintStream output = clArgs.getOutputStream();
    	boolean ok = clArgs.isThreading();
    	assertTrue(input.length == 1);
    	assertTrue(output != null);
    	assertTrue(!ok);
    	args = new String[] {};
    	clArgs = new ArgsT(args);
    	input = clArgs.getInputFiles();
    	output = clArgs.getOutputStream();
    	ok = clArgs.isThreading();
    	assertTrue(input == null);
    	assertTrue(output == null);
    	assertTrue(!ok);
    	args = new String[] {"input.txt"};
    	clArgs = new ArgsT(args);
    	input = clArgs.getInputFiles();
    	output = clArgs.getOutputStream();
    	ok = clArgs.isThreading();
    	assertTrue(input.length == 1);
    	assertTrue(output != null);
    	assertTrue(!ok);
    	args = new String[] {"-t","input.txt", "output.txt"};
    	clArgs = new ArgsT(args);
    	input = clArgs.getInputFiles();
    	output = clArgs.getOutputStream();
    	ok = clArgs.isThreading();
    	assertTrue(input.length == 1);
    	assertTrue(output != null);
    	assertTrue(ok);
    	args = new String[] {"-t","src/test/data1", "output.txt"};
    	clArgs = new ArgsT(args);
    	input = clArgs.getInputFiles();
    	output = clArgs.getOutputStream();
    	ok = clArgs.isThreading();
    	assertTrue(input.length >= 1);
    	assertTrue(output != null);
    	assertTrue(ok);
    	
    }
    
}
