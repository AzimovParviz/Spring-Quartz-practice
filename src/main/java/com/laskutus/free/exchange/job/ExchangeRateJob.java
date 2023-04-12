package com.laskutus.free.exchange.job;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateJob implements Job {
	private static Logger _log = LoggerFactory.getLogger(ExchangeRateJob.class);

	public ExchangeRateJob() {

	}

	public String fetchExchangeRates() {
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
			_log.info("Response code: " + con.getResponseCode());
			con.disconnect();
			JSONObject jo = new JSONObject(content.toString());
			return jo.toString();
		} catch (Exception e) {
			_log.info(e.toString());
		}
		return "something went wrong";
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		ExchangeRateJob exchangeRateJob = new ExchangeRateJob();
		String rates = exchangeRateJob.fetchExchangeRates();
		_log.info("Received exchange rates!!!" + rates + new Date());
		context.setResult(rates);
	}

}
