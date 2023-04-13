package com.laskutus.free;

import com.laskutus.free.exchange.controller.ExchangeController;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FreeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreeApplication.class, args);
		try {
			ExchangeController ec = new ExchangeController();
			ec.run();
		} catch (Exception e) {
			System.out.println("scheduler error: " + e);
		}
	}
}
