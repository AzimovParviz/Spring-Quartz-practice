package com.laskutus.free.exchange.job;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.laskutus.free.utils.FileOperations;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class ExchangeRateJob implements Job {
	private static Logger _log = LoggerFactory.getLogger(ExchangeRateJob.class);

	public ExchangeRateJob() {

	}

	public void fetchExchangeRatesUSD() {
		try {
			URL url = new URL("https://v6.exchangerate-api.com/v6/15e1b1b29c2c9a429b03db50/latest/USD");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer content = new StringBuffer();
			String inputLine = "";
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			FileOperations fo = new FileOperations();
			fo.CreateFile("usd.json");
			fo.WriteToFile("usd.json", content.toString());
			_log.info("Response code: " + con.getResponseCode());
			con.disconnect();

		} catch (Exception e) {
			_log.info(e.toString());
		}
	}

	public void fetchExchangeRatesSEK() {
		try {
			URL url = new URL("https://v6.exchangerate-api.com/v6/15e1b1b29c2c9a429b03db50/latest/SEK");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer content = new StringBuffer();
			String inputLine = "";
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			FileOperations fo = new FileOperations();
			fo.CreateFile("sek.json");
			fo.WriteToFile("sek.json", content.toString());
			_log.info("Response code: " + con.getResponseCode());
			con.disconnect();

		} catch (Exception e) {
			_log.info(e.toString());
		}
	}

	public void fetchExchangeRatesEUR() {
		try {
			URL url = new URL("https://v6.exchangerate-api.com/v6/15e1b1b29c2c9a429b03db50/latest/EUR");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer content = new StringBuffer();
			String inputLine = "";
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			FileOperations fo = new FileOperations();
			fo.CreateFile("eur.json");
			fo.WriteToFile("eur.json", content.toString());
			_log.info("Response code: " + con.getResponseCode());
			con.disconnect();

		} catch (Exception e) {
			_log.info(e.toString());
		}
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		ExchangeRateJob exchangeRateJob = new ExchangeRateJob();
		exchangeRateJob.fetchExchangeRatesUSD();
		exchangeRateJob.fetchExchangeRatesSEK();
		exchangeRateJob.fetchExchangeRatesEUR();

	}

}
