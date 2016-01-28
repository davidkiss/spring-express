package org.springexpress.config;

import org.springexpress.routing.ExpressContext;

/**
 * Created by david on 2016-01-22.
 */
public interface ExpressRouteConfigurer {
    void configureRoutes(ExpressContext ctx);
}
