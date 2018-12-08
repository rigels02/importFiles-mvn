package com.molport.impo.parsers.v2;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.molport.impo.parsers.IParser_v2T;
import com.molport.impo.parsers.PropRec;
import com.molport.impo.parsers.Rec;
import com.molport.impo.parsers.Utils;

/**
 * Parser T4 for file_4
 *
 * @author raitis
 */
public class Parser_v2T4 implements IParser_v2T {

	private static final Logger logger = LoggerFactory.getLogger(Parser_v2T4.class);

	private static final String CAT_FIELD = "Catalogue_Number";
	private static final String CAS_FIELD = "Cas";
	private static final String PRICE_FIELD = "Price";
	private static final String PACKING_FIELD = "Packing";
	private static final String PACKMEASURE_FIELD = "Packing_Measure";

	List<Rec> records = new LinkedList<>();
	private Rec rec;

	@Override
	public List<Rec> parse(String fileName, List<PropRec> lines) {
		int rec_num = 0;

		for (int i = 0; i < lines.size(); i++) {
			rec_num = lines.get(i).getRecId();
			rec = new Rec();
			rec.setFileName(fileName);
			try {
				getCatalog_Val(lines.get(i));
				getCasNum_Val(lines.get(i));
				getPrice(lines.get(i));
				getPacking(lines.get(i));
				getPackMeasure(lines.get(i));
			} catch (Exception ex) {
				logger.error("Error: Rec:" + rec_num + ":" + ex.getMessage());
				ex.printStackTrace();
				continue;
			}

			records.add(rec);
		}

		return records;
	}

	private void getPackMeasure(PropRec propRec) {
		int idx = propRec.getPropLst().indexOf(PACKMEASURE_FIELD);
		if (idx == -1) {
			logger.error(IMSG.NO_PACKING_MEASURE_FIELD);
			rec.getErrors().add(IMSG.NO_PACKING_MEASURE_FIELD);
			return;
		}
		String packMeasure = propRec.getValLst().get(idx);
		rec.getQtyMeasureList().add(packMeasure);

	}

	private void getPacking(PropRec propRec) {
		int idx = propRec.getPropLst().indexOf(PACKING_FIELD);
		if (idx == -1) {
			logger.error(IMSG.NO_PACKING_PROVIDED);
			rec.getErrors().add(IMSG.NO_PACKING_PROVIDED);
			return;
		}
		String packVal = propRec.getValLst().get(idx);
		try {
			rec.getPackUnitList().add(Float.valueOf(packVal));
		} catch (NumberFormatException e) {
			logger.error(IMSG.BAD_PACKAGING_UNIT_NUMBER);
			rec.getErrors().add(IMSG.BAD_PACKAGING_UNIT_NUMBER);
		}

	}

	private void getPrice(PropRec propRec) {
		int idx = propRec.getPropLst().indexOf(PRICE_FIELD);
		if (idx == -1) {
			logger.error(IMSG.NO_PRICE_PROVIDED);
			rec.getErrors().add(IMSG.NO_PRICE_PROVIDED);
			return;
		}
		String price = propRec.getValLst().get(idx);
		try {
			rec.getPriceList().add(Float.valueOf(price));
		} catch (NumberFormatException e) {
			logger.error(IMSG.BAD_PRICE_NUMBER);
			rec.getErrors().add(IMSG.BAD_PRICE_NUMBER);
		}
		rec.getCurrList().add("USD");
	}

	private void getCasNum_Val(PropRec propRec) {
		int idx = propRec.getPropLst().indexOf(CAS_FIELD);
		if (idx == -1) {
			logger.error(IMSG.NO_CAS_NUMBER);
			rec.getErrors().add(IMSG.NO_CAS_NUMBER);
			return;
		}
		String cas = propRec.getValLst().get(idx).trim();
		if (Utils.casOk(cas)) {

			rec.setCasNum(cas);
		} else {
			logger.error(IMSG.INVALID_CAS_NUMBER, cas);
			rec.getErrors().add("Invalid cas number \"" + cas + "\"");

		}

	}

	private void getCatalog_Val(PropRec propRec) {
		int idx = propRec.getPropLst().indexOf(CAT_FIELD);
		rec.setCatNum(propRec.getValLst().get(idx));

	}

}
