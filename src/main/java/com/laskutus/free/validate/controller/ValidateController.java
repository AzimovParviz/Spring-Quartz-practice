package com.laskutus.free.validate.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.laskutus.free.validate.model.Validate;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ValidateController {

	public ValidateController() {

	}

	private static class ssnJsonBody {
		@JsonProperty("ssn")
		String ssn;
		@JsonProperty("countryCode")
		String countryCode;

		private ssnJsonBody(String s, String c) {
			this.ssn = s;
			this.countryCode = c;
		}

		@Override
		public String toString() {
			return "ssn: " + ssn + ", country code: " + countryCode;
		}
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "validate_ssn", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String validate_ssn(@RequestBody ssnJsonBody ssnInput) {
		Validate val = new Validate();
		System.out.println(ssnInput.toString());
		String result = val.validate(ssnInput.ssn);
		System.out.println(result);
		//JSONObject returnBody = new JSONObject();
		//returnBody.put("ssn_valid", result);
		return result.toString();
	}

}
