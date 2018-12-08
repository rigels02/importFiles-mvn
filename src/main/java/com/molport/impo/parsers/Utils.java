package com.molport.impo.parsers;

/**
 * check method according to:
 * {@link https://en.wikipedia.org/wiki/CAS_Registry_Number}
 * 
 * @author raitis
 */
public class Utils {

	/**
	 * Check method according to:
	 * {@link https://en.wikipedia.org/wiki/CAS_Registry_Number}
	 * 
	 * @param cas CAS Registry Number to be checked
	 * @return true is ok
	 */
	public static boolean casOk(String cas) {

		// check consistency
		String[] parts = cas.split("-");
		if (parts.length != 3)
			return false;
		if (parts[0].length() < 2 || parts[0].length() > 7)
			return false;
		if (parts[1].length() != 2)
			return false;
		if (parts[2].length() != 1)
			return false;
		if (!checkSumOk(parts))
			return false;
		return true;
	}

	private static boolean checkSumOk(String[] parts) {
		String cass = parts[0] + parts[1];
		int control = Character.getNumericValue(parts[2].charAt(0));
		if (control == -1)
			return false;
		int sum = 0;
		for (int k = 1, i = cass.length() - 1; i >= 0; i--, k++) {
			int d = Character.getNumericValue(cass.charAt(i));
			if (d == -1)
				return false;
			sum = sum + d * k;
		}
		if (sum % 10 != control)
			return false;
		return true;
	}

}
