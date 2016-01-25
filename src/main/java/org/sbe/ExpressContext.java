package org.sbe;

import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by david on 2016-01-22.
 */
public class ExpressContext {
    private PathMatcher pathMatcher = new AntPathMatcher();
    private List<ExpressRequestMappingInfo> mappingInfoList = new ArrayList<>();

    public ExpressRequestMappingInfo resolveHandler(HttpServletRequest request) {
        ExpressRequestMappingInfo result = null;
        for (ExpressRequestMappingInfo mappingInfo : mappingInfoList) {
            if (request.getMethod().equals(mappingInfo.getHttpMethod().name()) && pathMatcher.match(mappingInfo.getPath(), request.getRequestURI())) {
                Map<String, String> uriVariables = pathMatcher.extractUriTemplateVariables(mappingInfo.getPath(), request.getRequestURI());

                request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, uriVariables);
                result = mappingInfo;
                break;
            }

        }
        return result;
    }

    public void handle(HttpMethod httpMethod, String path, ExpressRequestMappingInfo.ExpressHttpRequestHandler httpRequestHandler) {
        mappingInfoList.add(new ExpressRequestMappingInfo(httpMethod, path, httpRequestHandler));
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
