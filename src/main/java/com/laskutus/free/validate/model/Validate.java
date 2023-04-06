package com.laskutus.free.validate.model;

import java.math.BigDecimal;
import java.time.Year;

public class Validate {

	public Validate() {

	}

	public String validate(String ssn_input) {
		String result = "true";
		int currentYear = Year.now().getValue(); // current year to check for centuries
		char controlCharacter = ssn_input.charAt(ssn_input.length());
		BigDecimal firstPartOfSSN = new BigDecimal(Integer.valueOf(ssn_input.substring(0, 5)));

		// Example code: 131052-308T.
		// 131052 = dd/mm/yy
		// hyphen (-) means she was born in 1900s, plus (+) means she was born in 1800s,
		// A means they were born in 2000s
		// 308 is just to distinguish. Men have an odd number and women an even number.
		// All numbers are between 002 and 899
		// T is a control character. It's established by dividing the nine-digit number
		// of the rest of the ID by 31
		// if the result is a decimal, the decimal part is multiplied by 31 and then
		// rounded to the closest number
		// 131052 divided by 31 = 4227493.8064516129032258064516129
		// 0.8064516129032258064516129 is multiplied by 31 the result is
		// 24.9999999999999999999999999. This is rounded to the closest full number,
		// which in this case is 25. The control character for 25 is T.
		// Map<Integer, Integer> control_characters = new HashMap<Integer, Integer>();

		// Testing if the given SSN is 11 characters long

		if (ssn_input.length() != 11) {
			// Creating an error message. In case there are more things wrong, the error
			// message will be longer
			result += "Wrong character length \n";
		}

		// Validating the century by comparing the year of birth to the currentYear
		if ((Integer.valueOf(ssn_input.substring(4, 5)) > currentYear % 100) && (ssn_input.charAt(6) == 'A')) {
			result += "Wrong century sign. \n";
		}

		// Validating the individual number

		if ((Integer.valueOf(ssn_input.substring(7, 9)) < 2) || (Integer.valueOf(ssn_input.substring(7, 9)) > 899)) {
			result += "Invalid individual code. \n";
		}

		// Validating the control character
		BigDecimal divisionByThirtyOneResult = new BigDecimal(firstPartOfSSN.divide(new BigDecimal(31)).intValue());
		if (divisionByThirtyOneResult.scale() == 0) {
			// divides by 31 without a decimal
			if (controlCharacter != divisionByThirtyOneResult.intValue() - 31) {
				result += "Invalid control character. \n";
			}
		}
		else {
			if (controlCharacter != divisionByThirtyOneResult.scale() * 31) {
				result += "Invalid control character. \n";
			}
		}

		return result;
	}
}
