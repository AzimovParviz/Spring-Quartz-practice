package com.laskutus.free.exchange.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeController {
	public ExchangeController() {
	}

	@CrossOrigin("*")
	@GetMapping(value = "exchange_amount", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String exchangeAmount(RequestParam params) {
		
		return "hui";
	}

}
