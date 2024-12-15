package com.twitter.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwitterCloneApplication {

	public static void main(String[] args) throws Exception {
		Logger logger = LoggerFactory.getLogger(TwitterCloneApplication.class);
		SpringApplication.run(TwitterCloneApplication.class, args);
		logger.info("<-------------------------------Application Started------------------------------->");

	}

}
