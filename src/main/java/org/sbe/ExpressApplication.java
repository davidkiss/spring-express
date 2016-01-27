package org.sbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.Map;
import java.util.concurrent.Future;

@SpringBootApplication
@EnableAutoConfiguration
public class ExpressApplication {

    public static void main(String[] args) {
		SpringApplication.run(ExpressApplication.class, args);
	}
}
