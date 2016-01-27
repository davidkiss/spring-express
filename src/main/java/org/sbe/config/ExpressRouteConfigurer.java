package org.sbe.config;

import org.sbe.routing.ExpressContext;

/**
 * Created by david on 2016-01-22.
 */
public interface ExpressRouteConfigurer {
    void addRoutes(ExpressContext ctx);
}
