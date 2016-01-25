package org.sbe;

import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Map;

/**
 * Created by david on 2016-01-22.
 */
public class ExpressHttpServletRequest extends HttpServletRequestWrapper {

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public ExpressHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    public String params(String name) {
        Map<String, String> uriVariables = (Map<String, String>) getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return uriVariables == null ? null : uriVariables.get(name);
    }
}
