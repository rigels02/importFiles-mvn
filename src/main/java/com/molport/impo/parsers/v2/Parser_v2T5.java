package com.molport.impo.parsers.v2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.molport.impo.parsers.IParser_v2T;
import com.molport.impo.parsers.PropRec;
import com.molport.impo.parsers.Rec;
import com.molport.impo.parsers.Utils;

/**
 * Parser T5 for file_5
 *
 * @author raitis
 */
public class Parser_v2T5 implements IParser_v2T {

	
	private static final Logger logger = LoggerFactory.getLogger(Parser_v2T5.class);

	private static final String CAT_FIELD = "Code";
	private static final String CAS_FIELD = "CAS_no";
	private static final String PRICE_FIELD = "Price"; // Price2_Euro
	private static final String SIZE_FIELD = "Quantity"; // Quantity1 = 2.5 g

	private List<Rec> records = new LinkedList<>();
	private Map<Integer, String> sizeFields = new HashMap<>();
	private Map<Integer, String> priceFields = new HashMap<>();
	private Map<Integer, String> currFields = new HashMap<>();

	private Rec rec;

	@Override
	public List<Rec> parse(String fileName, List<PropRec> lines) {
		int rec_num = 0;

		for (int i = 0; i < lines.size(); i++) {
			rec_num = lines.get(i).getRecId();
			rec = new Rec();
			rec.setFileName(fileName);
			clearMaps();
			try {
				getCatalog_Val(lines.get(i));
				getCasNum_Val(lines.get(i));
				mapPrices(lines.get(i));
				mapSizes(lines.get(i));
				getSizesAndPrices(lines.get(i));
			} catch (Exception ex) {
				logger.error("Error: Rec:" + rec_num + ":" + ex.getMessage());
				ex.printStackTrace();
				// continue;
			}

			records.add(rec);
		}

		return records;
	}

	private void clearMaps() {
		sizeFields.clear();
		priceFields.clear();
		currFields.clear();
		
	}

	private void mapSizes(PropRec propRec) {
		for (int i = 0; i < propRec.getPropLst().size(); i++) {
			String key = propRec.getPropLst().get(i);
			if (key.contains(SIZE_FIELD)) {

				int idx = exctractIdx(key);

				sizeFields.put(idx, propRec.getValLst().get(i));

			}
		}

	}

	private int exctractIdx(String sizeToken) {
		String sidx = sizeToken.replaceAll("[^0-9]", "").trim();
		return Integer.valueOf(sidx);
	}

	private void mapPrices(PropRec propRec) {
		for (int i = 0; i < propRec.getPropLst().size(); i++) {
			String key = propRec.getPropLst().get(i);
			if (key.contains(PRICE_FIELD)) {

				int idx = exctractIdx(key);
				priceFields.put(idx, propRec.getValLst().get(i));
				String parts[] = key.split("_");
				currFields.put(idx, parts[1]);

			}
		}

	}

	private void getSizesAndPrices(PropRec propRec) {
		for (Integer key_id : sizeFields.keySet()) {
			String unit_g = sizeFields.get(key_id);
			String[] parts = unit_g.split(" ");
			String digit_part = parts[0];
			// String digit_part = unit_g.replaceAll("[^0-9]", "").trim();
			// String unit = unit_g.substring(unit_g.indexOf(digit_part) +
			// digit_part.length(), unit_g.length());
			String unit = parts[1];
			rec.getPackUnitList().add(Float.valueOf(digit_part));
			rec.getQtyMeasureList().add(unit);
			// get price value
			try {
				rec.getPriceList().add(Float.valueOf(priceFields.get(key_id)));
			} catch (Exception ex) {
				rec.getPriceList().add(null);
				logger.error(IMSG.PRICE_NOT_PROVIDED_OR_WRONG, priceFields.get(key_id));
				rec.getErrors().add(IMSG.PRICE_NOT_PROVIDED_OR_WRONG2 + priceFields.get(key_id));
				// return;
			}
			rec.getCurrList().add(currFields.get(key_id));
		}

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
