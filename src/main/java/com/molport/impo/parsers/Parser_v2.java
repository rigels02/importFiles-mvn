package com.molport.impo.parsers;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chemaxon.formats.MolFormatException;
import chemaxon.formats.MolImporter;
import chemaxon.struc.Molecule;

/**
 * Using molport Chemaxon jchem. CATALOG_FIELDS Map is used to get associated
 * file type to call necessary Parser_v2Tx.
 * 
 * @author raitis
 *
 */
public class Parser_v2 implements IParser_v2 {

	private static final Logger logger = LoggerFactory.getLogger(Parser_v2.class);

	

	/* (non-Javadoc)
	 * @see com.molport.impo.parsers.IParser_v2#doParse(java.lang.String)
	 */
	@Override
	public List<Rec> doParse(String fileName) {

		List<PropRec> recs = null;
		try {
			recs = getPropeties(fileName);
		} catch (IOException e) {
			logger.error("Error process file: {}\n{}", fileName, e.getMessage());
			return null;
		}
		
		String token = recs.get(0).getPropLst().get(0);

		IParser_v2T parser = FileTypeParserFactoryV2.get(token);
		
		if (parser == null) {
			logger.error("Unknown File type. File: {}", fileName);
			return null;
		}
		List<Rec> records = parser.parse(fileName, recs);
		return records;

	}

	private List<PropRec> getPropeties(String fileName) throws MolFormatException, IOException {

		List<PropRec> recs = new ArrayList<>();

		try (MolImporter mimpo1 = new MolImporter(fileName)) {

			Stream<Molecule> mols = mimpo1.getMolStream();
			mols.forEach(mo -> {
				String fName = Paths.get(mimpo1.getFileName()).getFileName().toString();
				// int recNum = mimpo1.getRecordCount();

				int propNum = mo.getPropertyCount();

				PropRec props = new PropRec();

				int recCount = 1;
				props.setRecId(recCount);
				recCount++;

				for (int i = 0; i < propNum; i++) {

					String key = mo.getPropertyKey(i);
					props.setFileName(fName);
					props.getPropLst().add(key);
					props.getValLst().add((String) mo.getPropertyObject(key));

				}
				recs.add(props);
			});

		}

		return recs;
	}
}
