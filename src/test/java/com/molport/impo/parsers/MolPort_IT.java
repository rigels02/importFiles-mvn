package com.molport.impo.parsers;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import chemaxon.formats.MolFormatException;
import chemaxon.formats.MolImporter;
import chemaxon.struc.Molecule;

public class MolPort_IT {

	private List<PropRec> molecule = new ArrayList<>();
	
	@Test
	public void test() throws MolFormatException, IOException {
		
		String file = "src/test/data/input_02.sdf";
		
		MolImporter mimpo1 = new MolImporter(file);
	
        Stream<Molecule> mols = mimpo1.getMolStream();
        mols.forEach(mo -> {
        	String fName = Paths.get(mimpo1.getFileName()).getFileName().toString();
        	//int recNum = mimpo1.getRecordCount();
        	
        	int propNum = mo.getPropertyCount();
        	
        	PropRec props = new PropRec();
        	for(int i=0; i< propNum; i++) {
        		
        		String key = mo.getPropertyKey(i);
        		props.setFileName(fName);
        		props.getPropLst().add(key);
        		props.getValLst().add((String) mo.getPropertyObject(key));
        		
        		
        	}
        	molecule.add(props);
        });
    
        
        mimpo1.close();
        System.out.println(molecule);
        
        //molecule.indexOf("")
	}

}
