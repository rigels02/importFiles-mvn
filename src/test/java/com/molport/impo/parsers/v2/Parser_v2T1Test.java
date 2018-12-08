package com.molport.impo.parsers.v2;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import com.molport.impo.out.OutputFormater;
import com.molport.impo.parsers.DOutput;
import com.molport.impo.parsers.IParser_v2;
import com.molport.impo.parsers.Parser_v2;
import com.molport.impo.parsers.PropRec;
import com.molport.impo.parsers.Rec;

import chemaxon.formats.MolFormatException;
import chemaxon.formats.MolImporter;
import chemaxon.struc.Molecule;

public class Parser_v2T1Test {

	private int recCount = 1;

	private List<PropRec> getPropeties(String fileName) throws MolFormatException, IOException {

		List<PropRec> recs = new ArrayList<>();

		try (MolImporter mimpo1 = new MolImporter(fileName)) {

			Stream<Molecule> mols = mimpo1.getMolStream();

			mols.forEach(mo -> {
				String fName = Paths.get(mimpo1.getFileName()).getFileName().toString();
				// int recNum = mimpo1.getRecordCount();

				int propNum = mo.getPropertyCount();

				PropRec props = new PropRec();
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

	@Test
	public void test_Parser_v2T1() throws MolFormatException, IOException {
		String file = "src/test/data/input_02.sdf";
		List<PropRec> props = getPropeties(file);
		DOutput.outFileProps("src/test/data/output.txt", props);
		Parser_v2T1 parser = new Parser_v2T1();
		List<Rec> records = parser.parse("Tst.txt", props);
		System.out.println(records);

	}

	@Test
	public void test_Parser_v2T2() throws MolFormatException, IOException {
		String file = "src/test/data/input_2.sdf";
		List<PropRec> props = getPropeties(file);
		DOutput.outFileProps("src/test/data/output.txt", props);
		Parser_v2T2 parser = new Parser_v2T2();
		List<Rec> records = parser.parse("Tst.txt", props);
		System.out.println(records);

	}

	@Test
	public void test_Parser_v2T3() throws MolFormatException, IOException {
		String file = "src/test/data/input_3.sdf";
		List<PropRec> props = getPropeties(file);
		DOutput.outFileProps("src/test/data/output.txt", props);
		Parser_v2T3 parser = new Parser_v2T3();
		List<Rec> records = parser.parse("Tst.txt", props);
		System.out.println(records);

	}

	@Test
	public void test_Parser_v2T4() throws MolFormatException, IOException {
		String file = "src/test/data/input_4.sdf";
		List<PropRec> props = getPropeties(file);
		DOutput.outFileProps("src/test/data/output.txt", props);
		Parser_v2T4 parser = new Parser_v2T4();
		List<Rec> records = parser.parse("Tst.txt", props);
		System.out.println(records);

	}

	@Test
	public void test_Parser_v2T5() throws MolFormatException, IOException {
		String file1 = "src/test/data/input_02.sdf";
		String file2 = "src/test/data/input_2.sdf";
		String file3 = "src/test/data/input_3.sdf";
		String file4 = "src/test/data/input_4.sdf";
		String file5 = "src/test/data/input_5.sdf";
		// List<PropRec> props = getPropeties(file);
		// DOutput.outFileProps("src/test/data/output.txt", props);
		IParser_v2 parser = new Parser_v2();

		List<Rec> recs = parser.doParse(file1);
		DOutput.outFileRec("output.txt", recs);
		recs = parser.doParse(file2);
		DOutput.outFileRec("output.txt", recs);
		recs = parser.doParse(file3);
		DOutput.outFileRec("output.txt", recs);
		recs = parser.doParse(file4);
		DOutput.outFileRec("output.txt", recs);
		recs = parser.doParse(file5);
		DOutput.outFileRec("output.txt", recs);

	}

	@Test
	public void test_Parser_v2_with_output() throws MolFormatException, IOException {
		String file1 = "src/test/data/input_02.sdf";
		String file2 = "src/test/data/input_2.sdf";
		String file3 = "src/test/data/input_3.sdf";
		String file4 = "src/test/data/input_4.sdf";
		String file5 = "src/test/data/input_5.sdf";
		// List<PropRec> props = getPropeties(file);
		// DOutput.outFileProps("src/test/data/output.txt", props);
		IParser_v2 parser = new Parser_v2();

		List<Rec> recs = parser.doParse(file1);
		DOutput.outFileRecCols("output.txt", recs);
		OutputFormater oFmt = new OutputFormater();
		PrintStream ps = new PrintStream(new File("output_1.txt"));
		oFmt.outPrint(ps, recs);
		ps.close();
		recs = parser.doParse(file2);
		DOutput.outFileRecCols("output.txt", recs);
		ps = new PrintStream(new File("output_2.txt"));
		oFmt.outPrint(ps, recs);
		ps.close();
		recs = parser.doParse(file3);
		DOutput.outFileRecCols("output.txt", recs);
		ps = new PrintStream(new File("output_3.txt"));
		oFmt.outPrint(ps, recs);
		ps.close();
		recs = parser.doParse(file4);
		DOutput.outFileRecCols("output.txt", recs);
		ps = new PrintStream(new File("output_4.txt"));
		oFmt.outPrint(ps, recs);
		ps.close();
		recs = parser.doParse(file5);
		DOutput.outFileRecCols("output.txt", recs);
		ps = new PrintStream(new File("output_5.txt"));
		oFmt.outPrint(ps, recs);
		ps.close();
	}

	@Test
	public void test_Parser_v2_with_file_input02() throws MolFormatException, IOException {

		String file = "src/test/data/input_02.sdf";
		List<PropRec> props = getPropeties(file);
		DOutput.outFileProps("src/test/data/output.txt", props);
		IParser_v2 parser = new Parser_v2();

		List<Rec> recs = parser.doParse(file);
		DOutput.outFileRecCols("src/test/data/recs.txt", recs);
		OutputFormater oFmt = new OutputFormater();
		PrintStream ps = new PrintStream(new File("src/test/data/output_1.txt"));
		oFmt.outPrint(ps, recs);
		ps.close();

	}

	@Test
	public void test_Parser_v2_with_file_input_2() throws MolFormatException, IOException {

		String file = "src/test/data/input_2.sdf";
		List<PropRec> props = getPropeties(file);
		DOutput.outFileProps("src/test/data/output.txt", props);
		IParser_v2 parser = new Parser_v2();

		List<Rec> recs = parser.doParse(file);
		DOutput.outFileRecCols("src/test/data/recs.txt", recs);
		OutputFormater oFmt = new OutputFormater();
		PrintStream ps = new PrintStream(new File("src/test/data/output_1.txt"));
		oFmt.outPrint(ps, recs);
		ps.close();

	}

	@Test
	public void test_Parser_v2_with_file_input_3() throws MolFormatException, IOException {

		String file = "src/test/data/input_3.sdf";
		List<PropRec> props = getPropeties(file);
		DOutput.outFileProps("src/test/data/output.txt", props);
		IParser_v2 parser = new Parser_v2();

		List<Rec> recs = parser.doParse(file);
		DOutput.outFileRecCols("src/test/data/recs.txt", recs);
		OutputFormater oFmt = new OutputFormater();
		PrintStream ps = new PrintStream(new File("src/test/data/output_1.txt"));
		oFmt.outPrint(ps, recs);
		ps.close();

	}

	@Test
	public void test_Parser_v2_with_file_input_4() throws MolFormatException, IOException {

		String file = "src/test/data/input_4.sdf";
		List<PropRec> props = getPropeties(file);
		DOutput.outFileProps("src/test/data/output.txt", props);
		IParser_v2 parser = new Parser_v2();

		List<Rec> recs = parser.doParse(file);
		DOutput.outFileRecCols("src/test/data/recs.txt", recs);
		OutputFormater oFmt = new OutputFormater();
		PrintStream ps = new PrintStream(new File("src/test/data/output_1.txt"));
		oFmt.outPrint(ps, recs);
		ps.close();

	}

	@Test
	public void test_Parser_v2_with_file_input_5() throws MolFormatException, IOException {

		String file = "src/test/data1/file_05.sdf";
		List<PropRec> props = getPropeties(file);
		DOutput.outFileProps("src/test/data/props.txt", props);
		IParser_v2 parser = new Parser_v2();

		List<Rec> recs = parser.doParse(file);
		DOutput.outFileRecCols("src/test/data/recs.txt", recs);
		OutputFormater oFmt = new OutputFormater();
		PrintStream ps = new PrintStream(new File("src/test/data/output_1.txt"));
		oFmt.outPrint(ps, recs);
		ps.close();

	}

	@Test
	public void test_Parser_v2_with_file_file_01() throws MolFormatException, IOException {

		String file = "src/test/data/file_01.sdf";
		List<PropRec> props = getPropeties(file);
		DOutput.outFileProps("src/test/data/output.txt", props);
		IParser_v2 parser = new Parser_v2();

		List<Rec> recs = parser.doParse(file);
		DOutput.outFileRecCols("src/test/data/recs.txt", recs);
		OutputFormater oFmt = new OutputFormater();
		PrintStream ps = new PrintStream(new File("src/test/data/output_1.txt"));
		oFmt.outPrint(ps, recs);
		ps.close();

	}

	@Test
	public void test_Parser_v2_with_file_file_02() throws MolFormatException, IOException {

		String file = "src/test/data/file_02.sdf";
		List<PropRec> props = getPropeties(file);
		DOutput.outFileProps("src/test/data/output.txt", props);
		IParser_v2 parser = new Parser_v2();

		List<Rec> recs = parser.doParse(file);
		DOutput.outFileRecCols("src/test/data/recs.txt", recs);
		OutputFormater oFmt = new OutputFormater();
		PrintStream ps = new PrintStream(new File("src/test/data/output_1.txt"));
		oFmt.outPrint(ps, recs);
		ps.close();

	}

	@Test
	public void test_Parser_v2_with_file_file_03() throws MolFormatException, IOException {

		String file = "src/test/data/file_03.sdf";
		List<PropRec> props = getPropeties(file);
		DOutput.outFileProps("src/test/data/output.txt", props);
		IParser_v2 parser = new Parser_v2();

		List<Rec> recs = parser.doParse(file);
		DOutput.outFileRecCols("src/test/data/recs.txt", recs);
		OutputFormater oFmt = new OutputFormater();
		PrintStream ps = new PrintStream(new File("src/test/data/output_1.txt"));
		oFmt.outPrint(ps, recs);
		ps.close();

	}

	@Test
	public void test_Parser_v2_with_file_file_04() throws MolFormatException, IOException {

		String file = "src/test/data/file_04.sdf";
		List<PropRec> props = getPropeties(file);
		DOutput.outFileProps("src/test/data/output.txt", props);
		IParser_v2 parser = new Parser_v2();

		List<Rec> recs = parser.doParse(file);
		DOutput.outFileRecCols("src/test/data/recs.txt", recs);
		OutputFormater oFmt = new OutputFormater();
		PrintStream ps = new PrintStream(new File("src/test/data/output_1.txt"));
		oFmt.outPrint(ps, recs);
		ps.close();

	}

	@Test
	public void test_Parser_v2_with_file_file_05() throws MolFormatException, IOException {

		String file = "src/test/data/file_05.sdf";
		List<PropRec> props = getPropeties(file);
		DOutput.outFileProps("src/test/data/output.txt", props);
		IParser_v2 parser = new Parser_v2();

		List<Rec> recs = parser.doParse(file);
		DOutput.outFileRecCols("src/test/data/recs.txt", recs);
		OutputFormater oFmt = new OutputFormater();
		PrintStream ps = new PrintStream(new File("src/test/data/output_1.txt"));
		oFmt.outPrint(ps, recs);
		ps.close();

	}
}
