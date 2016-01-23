package org.sbe;

import org.springframework.http.converter.HttpMessageConverter;

import java.util.List;

/**
 * Created by david on 2016-01-22.
 */
public interface ExpressRouteConfigurer {
    void addRoutes(ExpressContext ctx);
}
