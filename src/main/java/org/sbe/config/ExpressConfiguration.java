package org.sbe.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sbe.routing.ExpressContext;
import org.sbe.routing.ExpressHandlerAdapter;
import org.sbe.routing.ExpressHandlerMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.DispatcherServlet;

import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2016-01-22.
 */
@Configuration
@ConditionalOnBean(ExpressRouteConfigurer.class)
public class ExpressConfiguration {

    private static boolean romePresent =
            ClassUtils.isPresent("com.rometools.rome.feed.WireFeed", ExpressConfiguration.class.getClassLoader());

    private static final boolean jaxb2Present =
            ClassUtils.isPresent("javax.xml.bind.Binder", ExpressConfiguration.class.getClassLoader());

    private static final boolean jackson2Present =
            ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", ExpressConfiguration.class.getClassLoader()) &&
                    ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", ExpressConfiguration.class.getClassLoader());

    private static final boolean jackson2XmlPresent =
            ClassUtils.isPresent("com.fasterxml.jackson.dataformat.xml.XmlMapper", ExpressConfiguration.class.getClassLoader());

    private static final boolean gsonPresent =
            ClassUtils.isPresent("com.google.gson.Gson", ExpressConfiguration.class.getClassLoader());

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public DispatcherServlet dispatcherServlet() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setDetectAllHandlerMappings(true);
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
    public ExpressHandlerMapping handlerMapping(ExpressContext context, List<ExpressRouteConfigurer> configurers) {
        if (configurers != null) {
            for (ExpressRouteConfigurer configurer : configurers) {
                configurer.addRoutes(context);
            }
        }
        ExpressHandlerMapping handlerMapping = new ExpressHandlerMapping(context);
        handlerMapping.setOrder(0);
        return handlerMapping;
    }

    protected void addHttpMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setWriteAcceptCharset(false);

        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(stringConverter);
        messageConverters.add(new ResourceHttpMessageConverter());
        messageConverters.add(new SourceHttpMessageConverter<Source>());
        messageConverters.add(new AllEncompassingFormHttpMessageConverter());

        if (romePresent) {
            messageConverters.add(new AtomFeedHttpMessageConverter());
            messageConverters.add(new RssChannelHttpMessageConverter());
        }

        if (jackson2XmlPresent) {
            ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.xml().applicationContext(this.applicationContext).build();
            messageConverters.add(new MappingJackson2XmlHttpMessageConverter(objectMapper));
        }
        else if (jaxb2Present) {
            messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
        }

        if (jackson2Present) {
            ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().applicationContext(this.applicationContext).build();
            messageConverters.add(new MappingJackson2HttpMessageConverter(objectMapper));
        }
        else if (gsonPresent) {
            messageConverters.add(new GsonHttpMessageConverter());
        }
    }

    @Bean(name = DispatcherServlet.HANDLER_ADAPTER_BEAN_NAME)
    public ExpressHandlerAdapter handlerAdapter() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        addHttpMessageConverters(messageConverters);

        ExpressHandlerAdapter expressHandlerAdapter = new ExpressHandlerAdapter(messageConverters, 10_000L);
        return expressHandlerAdapter;
    }

}
