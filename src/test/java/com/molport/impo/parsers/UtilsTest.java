package com.molport.impo.parsers;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void test_casOk() {
		String cas = "7732-18-5";
		boolean ok = Utils.casOk(cas);
		assertTrue(ok);
		String cas1 = "7732-17-5";
		ok = Utils.casOk(cas1);
		assertTrue(!ok);
		String cas2 = "7732-173-5";
		ok = Utils.casOk(cas2);
		assertTrue(!ok);
	}

}
