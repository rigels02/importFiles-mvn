package com.molport.impo.parsers;

import java.util.List;

public interface IParser_v2 {

	/**
	 * Main parser method
	 * 
	 * @param fileName input file to parse
	 * @return
	 */
	List<Rec> doParse(String fileName);

}