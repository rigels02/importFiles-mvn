package com.molport.impo.parsers;

import java.util.HashMap;
import java.util.Map;

import com.molport.impo.parsers.v2.Parser_v2T1;
import com.molport.impo.parsers.v2.Parser_v2T2;
import com.molport.impo.parsers.v2.Parser_v2T3;
import com.molport.impo.parsers.v2.Parser_v2T4;
import com.molport.impo.parsers.v2.Parser_v2T5;

/**
 * Parser_v2Tx factory
 * 
 * @author raitis
 */
public class FileTypeParserFactoryV2 {
	
	
	private static final Map<String, FileTypes> CATALOG_FIELDS;

	static {
		CATALOG_FIELDS = new HashMap<>();
		CATALOG_FIELDS.put("Catalog Number", FileTypes.Type_1);
		CATALOG_FIELDS.put("id", FileTypes.Type_2);
		CATALOG_FIELDS.put("Catalog#", FileTypes.Type_3);
		CATALOG_FIELDS.put("Catalogue_Number", FileTypes.Type_4);
		CATALOG_FIELDS.put("ID", FileTypes.Type_5);

	}
	
	
	private FileTypeParserFactoryV2() {
	}

	public static IParser_v2T get(String token) {
		if (!CATALOG_FIELDS.containsKey(token)) {

			return null;
		}

		FileTypes key = CATALOG_FIELDS.get(token);
		
		switch (key) {
		case Type_1:
			return new Parser_v2T1();

		case Type_2:
			return new Parser_v2T2();

		case Type_3:
			return new Parser_v2T3();

		case Type_4:
			return new Parser_v2T4();

		case Type_5:
			return new Parser_v2T5();
		default:
			break;

		// default:
		// throw new IllegalArgumentException("Unknown File Type!");

		}
		return null;

	}

}
