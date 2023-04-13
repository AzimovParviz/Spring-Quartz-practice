package com.laskutus.free.validate.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;

import org.json.JSONObject;

public class Validate {

	public Validate() {

	}

	public String validate(String ssn_input) {
		String result = "";
		String errorMessage = "";
		int currentYear = Year.now().getValue(); // current year to check for centuries
		char controlCharacter = ssn_input.charAt(ssn_input.length() - 1);
		System.out.println((ssn_input.substring(0, 6)));
		BigDecimal firstPartOfSSN = new BigDecimal(Integer.valueOf(ssn_input.substring(0, 6)));
		BigDecimal totalNumerical = new BigDecimal(
				firstPartOfSSN.intValue() * 1000 + Integer.valueOf(ssn_input.substring(7, 10)));
		System.out.println("individual code: " + ssn_input.substring(7, 10));
		System.out.println("first part of ssn: " + firstPartOfSSN.toPlainString());
		System.out.println("total numerical: " + totalNumerical);

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
		//

		if (ssn_input.length() != 11) {
			// Creating an error message. In case there are more things wrong, the error
			// message will be longer
			errorMessage += "Wrong character length \n";
		}
		// Checking against ASCII values of letters and numbers from the control
		// character table, making sure to convert the numerical value to the ones we
		// would get after calculating the control character
		// Control character cannot be these letters: G, I, O, Q, Z. Afterwards, we are
		// just assigning control character's numeric value to a new variable
		int numericValueOfControlCharacter = controlCharacter;
		if (controlCharacter == 71 || controlCharacter == 81 || controlCharacter == 90 || controlCharacter == 79
				|| controlCharacter == 73) {
			errorMessage += "Invalid control character. Control character cannot be " + controlCharacter + '\n';
		} else if (controlCharacter >= 65 && controlCharacter <= 70) {
			numericValueOfControlCharacter -= 55;
		} else if (controlCharacter == 72) {
			numericValueOfControlCharacter -= 56;
		} else if (controlCharacter > 72 && controlCharacter <= 78) {
			numericValueOfControlCharacter -= 57;
		} else if (controlCharacter > 78 && controlCharacter <= 80) {
			numericValueOfControlCharacter -= 58;
		} else if (controlCharacter > 80 && controlCharacter <= 89) {
			numericValueOfControlCharacter -= 59;
		} else if (controlCharacter >= 48 && controlCharacter <= 57) {
			numericValueOfControlCharacter -= 48;
		}
		// Validating the century by comparing the year of birth to the currentYear
		if ((Integer.valueOf(ssn_input.substring(4, 5)) > currentYear % 100) && (ssn_input.charAt(6) == 'A')) {
			errorMessage += "Wrong century sign. \n";
		}

		// Validating the individual number

		if ((Integer.valueOf(ssn_input.substring(7, 9)) < 2) || (Integer.valueOf(ssn_input.substring(7, 9)) > 899)) {
			errorMessage += "Invalid individual code. \n";
		}

		// Validating the control character
		BigDecimal divisionByThirtyOneResult = new BigDecimal(totalNumerical
				.divide(new BigDecimal(31), 10, RoundingMode.FLOOR).setScale(10, RoundingMode.FLOOR).toPlainString());
		System.out.println("errorMessage of division: " + divisionByThirtyOneResult);
		if (divisionByThirtyOneResult.scale() == 0) {
			// divides by 31 without a decimal
			if (controlCharacter != divisionByThirtyOneResult.intValue() - 31) {
				errorMessage += "Invalid control character \n";
			}
		} else {
			// calculating using the decimal remainder
			BigDecimal decimalRemainder = divisionByThirtyOneResult.remainder(BigDecimal.ONE);
			System.out.println(decimalRemainder.multiply(new BigDecimal(31).setScale(2, RoundingMode.HALF_UP))
					.setScale(0, RoundingMode.HALF_UP).toPlainString());
			int controlCharacterCalculationResult = decimalRemainder
					.multiply(new BigDecimal(31).setScale(2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP)
					.intValue();
			if (numericValueOfControlCharacter != controlCharacterCalculationResult) {
				System.out.println("value of the control char: " + (int) numericValueOfControlCharacter);
				System.out.println("calculated control char: " + controlCharacterCalculationResult);
				errorMessage += "Invalid control character \n";
			}
		}

		// Generating the JSON response

		JSONObject jo = new JSONObject();
		if (errorMessage.isEmpty())
			jo.put("ssn_valid", true);
		else if (result.isEmpty()) {
			jo.put("error_message", errorMessage);
			jo.put("ssn_valid", false);
		}
		return jo.toString();
	}
}
