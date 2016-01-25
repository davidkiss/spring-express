package org.sbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.Map;
import java.util.concurrent.Future;

@SpringBootApplication
public class ExpressApplication {

    @Configuration
    public static class RouteConfiguration implements ExpressRouteConfigurer {
        private AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        public void addRoutes(ExpressContext ctx) {
            ctx.get("/hello/{name}", (req, resp) -> {
                resp.send("Hello, %s", req.params("name"));
            });
            ctx.get("/bye/{fname}", (req, resp) -> {
                resp.send("Bye, %s", req.params("fname"));
            });
            ctx.get("/getjson", (req, resp) -> {
                Future<ResponseEntity<Map>> json = asyncRestTemplate.getForEntity(
                        "https://api.github.com/emojis", Map.class);
                resp.sendJson(json.get().getBody());
            });
            ctx.options("/hello", ((req, resp) -> {
                resp.send("options");
            }));
            ctx.post("/hello", ((req, resp) -> {
                Map body = req.getBody(Map.class);
                resp.send("oh, yeah! " + body);
            }));
        }
    }

    public static void main(String[] args) {
		SpringApplication.run(ExpressApplication.class, args);
	}
}
