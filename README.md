### Overview
Spring Express = [Spring](http://spring.io/) + [Express.js](http://expressjs.com/).

A simple and easy-to-learn [Spring-based](http://spring.io/) web framework inspired by [Express.js](http://expressjs.com/).

### Sample Application class to start the web application
```java
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
```

### Sample Express route configuration
```java
@Configuration
public class SampleRouteConfig implements ExpressRouteConfigurer {
    private static final Logger LOG = LoggerFactory.getLogger(SampleRouteConfig.class);

    private AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

    public void configureRoutes(ExpressContext ctx) {
        // Will be executed for any HTTP method type (GET, POST, etc.):
        ctx.all("/secret", (req, resp) -> {
            LOG.info("Accessing the secret section ...");
            resp.send("Method: %s", req.getMethod());
        });
        // Uses src/main/resources/templates/hello-to.html mustache template.
        // - needs the spring-boot-starter-mustache dependency in pom.xml:
        ctx.get("/hello-to/{name}", (req, resp) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("name", req.params("name"));
            resp.render("hello-to", model);
        });
        // Matches /ab1cd:
        ctx.get("/ab?cd", (req, resp) -> {
            resp.send("ab?cd");
        });
        // Matches /b22cd:
        ctx.get("/b*cd", (req, resp) -> {
            resp.send("b*cd");
        });
        // Matches /c/a/b/cd:
        ctx.get("/c/**/cd", (req, resp) -> {
            resp.send("c/**/cd");
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
```
