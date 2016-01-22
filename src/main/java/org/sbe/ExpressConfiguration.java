package org.sbe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.List;

/**
 * Created by david on 2016-01-22.
 */
@Configuration
public class ExpressConfiguration {

    @Bean
    public DispatcherServlet dispatcherServlet() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setDetectAllHandlerMappings(false);
        dispatcherServlet.setDispatchOptionsRequest(true);
        dispatcherServlet.setDispatchTraceRequest(true);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return dispatcherServlet;
    }

    @Bean
    @Autowired(required = false)
    public ExpressController snootController(List<ExpressConfigurer> configurers) {
        ExpressContext context = new ExpressContext();
        if (configurers != null) {
            for (ExpressConfigurer configurer : configurers) {
                configurer.addHandlers(context);
            }
        }
        return new ExpressController(context);
    }

}
