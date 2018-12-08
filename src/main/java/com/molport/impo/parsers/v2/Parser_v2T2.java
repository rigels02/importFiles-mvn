package com.molport.impo.parsers.v2;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.molport.impo.parsers.IParser_v2T;
import com.molport.impo.parsers.PropRec;
import com.molport.impo.parsers.Rec;

/**
 * Parser T2 for file_2
 *
 * @author raitis
 */
public class Parser_v2T2 implements IParser_v2T {

	private static final Logger logger = LoggerFactory.getLogger(Parser_v2T2.class);

	private static final String CAT_FIELD = "id";
	private static final String PRICECATEGORY_FIELD = "price_category";

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
				getPriceCategory(lines.get(i));
			} catch (Exception ex) {

				logger.error("Error: Rec:" + rec_num + ":" + ex.getMessage());
				ex.printStackTrace();
				continue;
			}
			records.add(rec);
		}

		return records;
	}

	private void getPriceCategory(PropRec propRec) {
		int idx = propRec.getPropLst().indexOf(PRICECATEGORY_FIELD);
		String priceCategory = propRec.getValLst().get(idx);
		rec.setPriceGroup(priceCategory);

	}

}
