package org.sbe;

import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by david on 2016-01-22.
 */
public class ExpressRequestMappingInfo {
    public interface ExpressHttpRequestHandler {
        void handleRequest(ExpressHttpServletRequest request, ExpressHttpServletResponse response) throws Exception;
    }

    private final HttpMethod httpMethod;
    private final String path;
    private final ExpressHttpRequestHandler httpRequestHandler;

    public ExpressRequestMappingInfo(HttpMethod httpMethod, String path, ExpressHttpRequestHandler httpRequestHandler) {
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

    public ExpressHttpRequestHandler getHttpRequestHandler() {
        return httpRequestHandler;
    }
}
