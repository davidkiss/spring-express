package org.sbe;

import org.springframework.http.HttpMethod;
import org.springframework.web.HttpRequestHandler;

/**
 * Created by david on 2016-01-22.
 */
public class ExpressRequestMappingInfo {
    private final HttpMethod httpMethod;
    private final String path;
    private final HttpRequestHandler httpRequestHandler;

    public ExpressRequestMappingInfo(HttpMethod httpMethod, String path, HttpRequestHandler httpRequestHandler) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.httpRequestHandler = httpRequestHandler;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public HttpRequestHandler getHttpRequestHandler() {
        return httpRequestHandler;
    }
}
