package org.sbe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

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
}
