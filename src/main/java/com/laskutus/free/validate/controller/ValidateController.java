package com.laskutus.free.validate.controller;

import com.laskutus.free.validate.model.Validate;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ValidateController {

	public ValidateController() {

	}

	private static class ssnJsonBody {
		String ssn;
		String countryCode;

		private ssnJsonBody() {
		}
	}

	@CrossOrigin(origins="*")
	@PostMapping(value="validate_ssn")
	public String validate_ssn(@RequestBody ssnJsonBody ssnInput) {
		Validate val = new Validate();
		System.out.println(ssnInput.toString());
		String result = val.validate(ssnInput.ssn);
		System.out.println(result);
		return result;
	}

}
