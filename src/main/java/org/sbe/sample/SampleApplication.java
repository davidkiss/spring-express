package org.sbe.sample;

import org.sbe.config.ExpressConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Main class of the sample application.
 */
@SpringBootApplication
@EnableAutoConfiguration
@Import(ExpressConfiguration.class)
public class SampleApplication {

    public static void main(String[] args) {
        // By default, application will run on port 8080.
        // To change it, use system property -Dserver.port=<port> when starting the application
		SpringApplication.run(SampleApplication.class, args);
	}
}
