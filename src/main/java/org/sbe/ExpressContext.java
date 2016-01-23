package org.sbe;

import org.springframework.http.HttpMethod;
import org.springframework.web.HttpRequestHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by david on 2016-01-22.
 */
public class ExpressContext {
    private Map<String, ExpressRequestMappingInfo> handlerMap = new HashMap<>();

    public ExpressRequestMappingInfo resolveHandler(HttpMethod httpMethod, String path) {
        return handlerMap.get(getHandlerKey(httpMethod, path));
    }

    public void handle(HttpMethod httpMethod, String path, ExpressRequestMappingInfo.ExpressHttpRequestHandler httpRequestHandler) {
        handlerMap.put(getHandlerKey(httpMethod, path), new ExpressRequestMappingInfo(httpMethod, path, httpRequestHandler));
    }

    private static String getHandlerKey(HttpMethod httpMethod, String path) {
        return String.format("%s %s", httpMethod, path);
    }

    public void delete(String path, ExpressRequestMappingInfo.ExpressHttpRequestHandler httpRequestHandler) {
        handle(HttpMethod.DELETE, path, httpRequestHandler);
    }

    public void get(String path, ExpressRequestMappingInfo.ExpressHttpRequestHandler httpRequestHandler) {
        handle(HttpMethod.GET, path, httpRequestHandler);
    }

    public void head(String path, ExpressRequestMappingInfo.ExpressHttpRequestHandler httpRequestHandler) {
        handle(HttpMethod.HEAD, path, httpRequestHandler);
    }

    public void options(String path, ExpressRequestMappingInfo.ExpressHttpRequestHandler httpRequestHandler) {
        handle(HttpMethod.OPTIONS, path, httpRequestHandler);
    }

    public void post(String path, ExpressRequestMappingInfo.ExpressHttpRequestHandler httpRequestHandler) {
        handle(HttpMethod.POST, path, httpRequestHandler);
    }

    public void patch(String path, ExpressRequestMappingInfo.ExpressHttpRequestHandler httpRequestHandler) {
        handle(HttpMethod.PATCH, path, httpRequestHandler);
    }

    public void put(String path, ExpressRequestMappingInfo.ExpressHttpRequestHandler httpRequestHandler) {
        handle(HttpMethod.PUT, path, httpRequestHandler);
    }

    public void trace(String path, ExpressRequestMappingInfo.ExpressHttpRequestHandler httpRequestHandler) {
        handle(HttpMethod.TRACE, path, httpRequestHandler);
    }
}
