package com.laskutus.free.exchange.controller;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import com.laskutus.free.FreeApplication;
import com.laskutus.free.exchange.job.ExchangeRateJob;
import com.laskutus.free.utils.FileOperations;

import org.json.JSONObject;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeController {

	@Autowired
	private ApplicationContext context;

	// TODO: separate modules
	@Bean
	public JobDetail jobDetail() {
		return newJob(ExchangeRateJob.class).withIdentity("job1", "group1").build();
	}

	@Bean
	public Trigger trigger(JobDetail job) {
		Date runTime = evenMinuteDate(new Date());

		return newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();
	}

	@Bean
	public Scheduler scheduler(Trigger trigger, JobDetail job) throws SchedulerException {
		SchedulerFactory sf = new StdSchedulerFactory();

		Scheduler scheduler = sf.getScheduler();
		scheduler.scheduleJob(job, trigger);
		scheduler.start();
		return scheduler;
	}

	private static Logger log = LoggerFactory.getLogger(ExchangeController.class);

	// JUST WRITE TO FILE IN THE JOB AND THEN READ FROM FILE HERE LOL

	public void run() throws Exception {

		log.info("------- Initializing ----------------------");
		log.info("------- Initialization Complete -----------");

		// computer a time that is on the next round minute

		log.info("------- Scheduling Job  -------------------");

		// define the job and tie it to our HelloJob class
		JobDetail job = jobDetail();
		// Trigger the job to run on the next round minute
		Trigger trigger = trigger(job);
		// init scheduler bean
		Scheduler sched = scheduler(trigger, job);
		// Tell quartz to schedule the job using our trigger
		sched.scheduleJob(job, trigger);
		//
		// log.info(job.getKey() + " will run at: " + runTime);

		// Start up the scheduler (nothing can actually run until the
		// scheduler has been started)

		log.info("------- Started Scheduler -----------------");

		// wait long enough so that the scheduler as an opportunity to
		// run the job!
		log.info("------- Waiting 65 seconds... -------------");
		try {
			// wait 65 seconds to show job
			Thread.sleep(5L * 1000L);

			// executing...
		} catch (Exception e) {
			//
		}
		// shut down the scheduler
		log.info("------- Shutting Down ---------------------");
		sched.deleteJob(new JobKey("job1", "group1"));
		sched.shutdown(true);
		log.info("------- Shutdown Complete -----------------");
	}

	@CrossOrigin("*")
	@GetMapping(value = "exchange_amount", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String exchangeAmount(@RequestParam String to, String from, int amount) {

		FileOperations fo = new FileOperations();
		String rates = fo.ReadFile("rates.json");
		//System.out.println("params: " + to + from + amount);
		JSONObject returnBody = new JSONObject();
		returnBody.put("from", from);
		returnBody.put("to", to);
		if (!rates.isEmpty()) {
			JSONObject jsonRates = new JSONObject(rates);
			jsonRates = jsonRates.getJSONObject("conversion_rates");
			float USD = jsonRates.getFloat("USD");
			float SEK = jsonRates.getFloat("SEK");
			float EUR = jsonRates.getFloat("EUR");
			log.info(to + from + amount);
			if (to.equals("USD")) {
				System.out.println("==========FROM USD TO SEK");
				if (from.equals("SEK")) {
					// 215/50/17
					System.out.println("==========FROM USD TO SEK");
					returnBody.put("exchange_rate", SEK);
					// 1 usd = 10.34 sek for example.
					returnBody.put("to_amount", SEK * amount);
					return returnBody.toString();
				}
			}
			// return String.valueOf(USD) + String.valueOf(SEK) + String.valueOf(EUR);
			return returnBody.toString();
		}
		return returnBody.toString();

	}

}
