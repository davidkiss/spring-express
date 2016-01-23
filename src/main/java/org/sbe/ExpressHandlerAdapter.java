package org.sbe;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by david on 2016-01-22.
 */
//@Component
public class ExpressHandlerAdapter implements HandlerAdapter{
    private List<HttpMessageConverter<?>> messageConverters;

    public void setMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        assert messageConverters != null;
        this.messageConverters = messageConverters;
    }

    @Override
    public boolean supports(Object handler) {
        return handler instanceof ExpressRequestMappingInfo;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ModelAndView mav = null;
        if (handler != null) {
            ExpressHttpServletRequest expressHttpServletRequest = new ExpressHttpServletRequest(request);
            ExpressHttpServletResponse expressHttpServletResponse = new ExpressHttpServletResponse(response);

            ((ExpressRequestMappingInfo) handler).getHttpRequestHandler().handleRequest(
                    expressHttpServletRequest, expressHttpServletResponse);

            if (expressHttpServletResponse.getBody() != null) {
                convertResponseBody(expressHttpServletResponse);
            } else {
                mav = expressHttpServletResponse.getModelAndView();
            }
        }
        return mav;
    }

    private void convertResponseBody(ExpressHttpServletResponse response) throws java.io.IOException {
        MediaType mediaType = MediaType.valueOf(response.getContentType());
        HttpMessageConverter messageConverter = getHttpMessageConverter(response, mediaType);

        if (messageConverter != null) {
            ServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
            messageConverter.write(response.getBody(), mediaType, outputMessage);
        }
    }

    private HttpMessageConverter<?> getHttpMessageConverter(ExpressHttpServletResponse expressHttpServletResponse, MediaType mediaType) {
        HttpMessageConverter<?> result = null;
        for (HttpMessageConverter mc : messageConverters) {
            if (mc.canWrite(expressHttpServletResponse.getBody().getClass(), mediaType)) {
                result = mc;
                break;
            }
        }
        return result;
    }

    @Override
    public long getLastModified(HttpServletRequest request, Object handler) {
        return -1;
    }
}
