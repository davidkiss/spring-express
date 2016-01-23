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
    public ExpressContext expressContext(){
        return new ExpressContext();
    }

    @Bean(name = DispatcherServlet.HANDLER_MAPPING_BEAN_NAME)
    @Autowired(required = false)
    public ExpressHandlerMapping handlerMapping(ExpressContext context, List<ExpressConfigurer> configurers) {
        if (configurers != null) {
            for (ExpressConfigurer configurer : configurers) {
                configurer.addHandlers(context);
            }
        }
        return new ExpressHandlerMapping(context);
    }

}
