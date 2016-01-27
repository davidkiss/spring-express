package org.sbe.sample;

import org.sbe.routing.ExpressContext;
import org.sbe.config.ExpressRouteConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by david on 2016-01-26.
 */
@Configuration
public class SampleRouteConfig implements ExpressRouteConfigurer {
    private static final Logger LOG = LoggerFactory.getLogger(SampleRouteConfig.class);

    private AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

    public void addRoutes(ExpressContext ctx) {
        // Will be executed for any HTTP method type (GET, POST, etc.):
        ctx.all("/secret", (req, resp) -> {
            LOG.info("Accessing the secret section ...");
            resp.send("Method: %s", req.getMethod());
        });
        // Using path params:
        ctx.get("/hello/{name}", (req, resp) -> {
            resp.send("Hello, %s!", req.params("name"));
        });
        // Make an async REST call:
        ctx.get("/async-rest", (req, resp) -> {
            Future<ResponseEntity<Map>> json = asyncRestTemplate.getForEntity(
                    "http://petstore.swagger.io/v2/store/inventory", Map.class);
            resp.sendJson(json.get().getBody());
        });
        ctx.options("/hello", ((req, resp) -> {
            resp.send("options");
        }));
        // Converts request body to Map object (for supported content types):
        ctx.post("/hello", ((req, resp) -> {
            Map body = req.getBody(Map.class);
            resp.send("I received this: " + body);
        }));
    }
}
