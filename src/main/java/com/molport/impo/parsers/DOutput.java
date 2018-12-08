package com.molport.impo.parsers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *  Output for Debug
 *  
 * @author raitis
 */
public class DOutput {

    public static boolean Debug = true;

    private DOutput() {
    }

    public static void print(String msg) {
        if (Debug) {
            System.out.println("DEBUG: " + msg);
        }
    }


    public static void outFileRec(String fileName, List<Rec> records) throws IOException {
         FileWriter wr = new FileWriter(fileName);
         try {
            for (Rec line : records) {
                wr.write(line + "\n");
            }
        } finally {
        	wr.flush();
            wr.close();
        }
    }
    public static void outFileRecCols(String fileName, List<Rec> records) throws IOException {
        FileWriter wr = new FileWriter(fileName);
        try {
           for (Rec line : records) {
               wr.write(line.print() + "\n");
           }
       } finally {
       	wr.flush();
           wr.close();
       }
   }
    public static void outFileProps(String fileName, List<PropRec> records) throws IOException {
        FileWriter wr = new FileWriter(fileName);
        try {
           for (PropRec line : records) {
               wr.write(line + "\n");
           }
       } finally {
    	   wr.flush();
           wr.close();
       }
   }
}
