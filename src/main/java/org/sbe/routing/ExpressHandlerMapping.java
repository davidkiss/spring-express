package org.sbe.routing;

import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by david on 2016-01-21.
 */
public class ExpressHandlerMapping extends AbstractHandlerMapping {

    private final ExpressContext context;

    public ExpressHandlerMapping(ExpressContext context) {
        this.context = context;
    }

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        ExpressRequestMappingInfo requestMappingInfo = context.resolveHandler(request);
        return requestMappingInfo;
    }
}
