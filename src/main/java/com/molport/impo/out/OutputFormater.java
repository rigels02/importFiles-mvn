package com.molport.impo.out;

import com.molport.impo.parsers.Rec;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.List;

/**
 * Format output for parsed records
 * 
 * @author raitis
 */
public class OutputFormater {

	private final String head = "FILE_NAME\tCATALOG_NUMBER\tPACKAGING_UNIT\tQUANTITY_MEASURE\tPRICE\tCURRENCY\tPRICE_GROUP\tCAS_NUMBER\tERROR_TXT";

	
	public void printHEADER(PrintStream ps) {
		ps.println(head);
	}
	
	
	/**
	 * In case of error in field field must be set as Null. packUnitList,
	 * qtyMeasureList, etc. must be the same size. required for
	 * {@link #lineForPackInfo(Rec)}
	 * 
	 * @param ps      output stream
	 * @param records records to output
	 */
	public synchronized void outPrint(PrintStream ps, List<Rec> records) {

		for (Rec rec : records) {

			if (!(rec.getPriceGroup() == null || rec.getPriceGroup().isEmpty())) {
				String oline = lineForPriceGroup(rec);
				ps.println(oline);
				continue;
			}
			String oline = lineForPackInfo(rec);
			//ps.println(oline);
			ps.print(oline);
		}

	}

	
	private String fN(String filePath) {
		return Paths.get(filePath).getFileName().toString();
	}

	private String lineForPriceGroup(Rec rec) {
		StringBuilder sb = new StringBuilder();
		sb.append(fN(rec.getFileName())).append("\t").append((rec.getCatNum() == null) ? "" : rec.getCasNum())
				.append("\t\t\t\t\t").append(rec.getPriceGroup())
				.append((rec.getCasNum() == null) ? "" : rec.getCasNum());
		buildErrorString(sb, rec);
		return sb.toString();
	}

	/**
	 * In case of error in field field must be set as Null. packUnitList,
	 * qtyMeasureList, etc. must be the same size. See {@link Rec}
	 * 
	 * @param rec
	 * @return
	 */
	private String lineForPackInfo(Rec rec) {
		StringBuilder sb = new StringBuilder();
		// sb.append(fN(rec.getFileName())).append("\t").append(rec.getCatNum()).append("\t");
		for (int i = 0; i < rec.getPackUnitList().size(); i++) {
			sb.append(fN(rec.getFileName())).append("\t").append(rec.getCatNum()).append("\t");
			/****
			 * if(i>0) { sb.append("\t\t"); }
			 **/
			sb.append(rec.getPackUnitList().get(i)).append("\t").append(rec.getQtyMeasureList().get(i)).append("\t")
					.append(rec.getPriceList().get(i)).append("\t").append(rec.getCurrList().get(i)).append("\t\t")
					.append(rec.getCasNum()).append("\t");
			
			buildErrorString(sb, rec);
			
			sb.append("\n");
		}
		return sb.toString();
	}

	

	private void buildErrorString(StringBuilder sb, Rec rec) {
		if (!rec.getErrors().isEmpty()) {
			for (String error : rec.getErrors()) {
				sb.append(error).append(";");
			}
		}
	}
	
}
