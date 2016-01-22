package org.sbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = { WebMvcAutoConfiguration.class })
@Configuration
public class ExpressApplication implements ExpressConfigurer {

    public void addHandlers(ExpressContext ctx) {
        ctx.get("/hello", ((req, resp) -> {
            String msg = "Hello, " + Thread.currentThread().getName();
            resp.getWriter().write(msg);
            System.out.println(msg);
        }));
        ctx.options("/hello", ((req, resp) -> {
            resp.getWriter().write("options");
        }));
        ctx.post("/update", ((req, resp) -> {
            resp.getWriter().write("oh, yeah!");
        }));
    }

    public static void main(String[] args) {
		SpringApplication.run(ExpressApplication.class, args);
	}
}
