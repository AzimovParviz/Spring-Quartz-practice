package com.laskutus.free.validate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class ValidateControllerTests {
	private MockMvc mockMvc;

	@BeforeEach
	void setUp(WebApplicationContext wac) {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new ValidateControllerTests()).build();

		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void ValidateController_shouldValidateSSN_returnsTrue() throws Exception {
		String requestBody = "{	\"ssn\":\"131052-308T\",\"countryCode\":\"FI\"}";
		this.mockMvc.perform(post("/validate_ssn").content(requestBody).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string("{\"ssn_valid\":true}"));
	}

	@Test
	public void ValidateController_shouldValidateSSN_returnsFalse() throws Exception {
		String requestBody = "{	\"ssn\":\"111022-303T\",\"countryCode\":\"FI\"}";
		this.mockMvc.perform(post("/validate_ssn").content(requestBody).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content()
						.string("{\"error_message\":\"Invalid control character \\n\",\"ssn_valid\":false}"));
	}
}
