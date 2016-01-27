package org.sbe.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class of the sample application.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class SampleApplication {

    public static void main(String[] args) {
        // By default, application will run on port 8080.
        // To change it, use system property -Dserver.port=<port> when starting the application
		SpringApplication.run(SampleApplication.class, args);
	}
}
