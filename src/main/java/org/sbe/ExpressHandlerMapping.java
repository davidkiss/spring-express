package org.sbe;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by david on 2016-01-21.
 */
@Component(DispatcherServlet.HANDLER_MAPPING_BEAN_NAME)
public class ExpressHandlerMapping extends AbstractHandlerMapping {

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        return "snootController";
    }
}
