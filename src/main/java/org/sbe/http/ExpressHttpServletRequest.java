package org.sbe.http;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by david on 2016-01-22.
 */
public class ExpressHttpServletRequest extends HttpServletRequestWrapper {

    private final List<HttpMessageConverter<?>> messageConverters;
    private final ServletServerHttpRequest servletServerHttpRequest;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @param messageConverters
     * @throws IllegalArgumentException if the request is null
     */
    public ExpressHttpServletRequest(HttpServletRequest request, List<HttpMessageConverter<?>> messageConverters) {
        super(request);
        this.servletServerHttpRequest = new ServletServerHttpRequest(request);
        this.messageConverters = messageConverters;
    }

    public String params(String name) {
        Map<String, String> uriVariables = (Map<String, String>) getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return uriVariables == null ? null : uriVariables.get(name);
    }

    public <T> T getBody(Class<T> clazz) throws IOException {
        MediaType mediaType = MediaType.parseMediaType(getRequest().getContentType());

        T result = null;
        for (HttpMessageConverter<?> messageConverter : messageConverters) {
            if (messageConverter.canRead(clazz, mediaType)) {
                result = (T) messageConverter.read((Class) clazz, servletServerHttpRequest);
                break;
            }
        }
        return result;
    }
}
