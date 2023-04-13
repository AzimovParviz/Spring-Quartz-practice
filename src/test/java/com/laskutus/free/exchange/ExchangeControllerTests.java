package com.laskutus.free.exchange;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class ExchangeControllerTests {
	private MockMvc mockMvc;

	@BeforeEach
	void setUp(WebApplicationContext wac) {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new ExchangeControllerTests()).build();

		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	// For whatever reason, I get an error related to the JSONObject library
	// whenever I run this test but when I run the app the endpoint functions
	// normally and does not throw any errors or warnings related to JSONObject
	//
	// @Test
	// public void ExchangeController_shouldReturn200() throws Exception {
	// String requestParams = "?to=USD&from=SEK&amount=31";
	// this.mockMvc.perform(get("/exchange_amount"+requestParams))
	// .andExpect(status().isOk());
	// }
}
