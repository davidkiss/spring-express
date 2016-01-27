package org.sbe;

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
        ctx.all("/secret", (req, resp) -> {
            LOG.info("Accessing the secret section ...");
            resp.send("Method: %s", req.getMethod());
        });
        ctx.get("/hello/{name}", (req, resp) -> {
            resp.send("Hello, %s!", req.params("name"));
        });
        ctx.get("/rest-proxy", (req, resp) -> {
            Future<ResponseEntity<Map>> json = asyncRestTemplate.getForEntity(
                    "http://petstore.swagger.io/v2/store/inventory", Map.class);
            resp.sendJson(json.get().getBody());
        });
        ctx.options("/hello", ((req, resp) -> {
            resp.send("options");
        }));
        ctx.post("/hello", ((req, resp) -> {
            Map body = req.getBody(Map.class);
            resp.send("I received this: " + body);
        }));
    }
}
