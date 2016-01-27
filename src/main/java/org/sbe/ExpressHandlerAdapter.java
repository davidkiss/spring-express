package org.sbe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.context.request.async.AsyncWebRequest;
import org.springframework.web.context.request.async.WebAsyncManager;
import org.springframework.web.context.request.async.WebAsyncUtils;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.NestedServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by david on 2016-01-22.
 */
public class ExpressHandlerAdapter implements HandlerAdapter{
    private static final Logger LOG = LoggerFactory.getLogger(ExpressHandlerAdapter.class);

    private List<HttpMessageConverter<?>> messageConverters;
    private long asyncRequestTimeout = 60_000L;

    public ExpressHandlerAdapter(List<HttpMessageConverter<?>> messageConverters, long asyncRequestTimeout) {
        assert messageConverters != null;
        this.messageConverters = messageConverters;
        this.asyncRequestTimeout = asyncRequestTimeout;
    }

    @Override
    public boolean supports(Object handler) {
        return handler instanceof ExpressRequestMappingInfo;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ModelAndView result = null;
        if (handler != null) {
            ExpressRequestMappingInfo expressRequestMappingInfo = (ExpressRequestMappingInfo) handler;

            WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
            if (asyncManager.hasConcurrentResult()) {
                result = (ModelAndView) asyncManager.getConcurrentResult();
                asyncManager.clearConcurrentResult();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Found concurrent result value [" + result + "]");
                }
                wrapConcurrentResult(result);
                return result;
            }

            if (asyncManager.isConcurrentHandlingStarted()) {
                return null;
            }

            startAsyncProcessing(expressRequestMappingInfo, asyncManager, request, response);
        }
        return result;
    }

    private void wrapConcurrentResult(Object result) throws Exception {
        if (result instanceof Exception) {
            throw (Exception) result;
        } else if (result instanceof Throwable) {
            throw new NestedServletException("Async processing failed", (Throwable) result);
        }
    }

    private void startAsyncProcessing(ExpressRequestMappingInfo expressRequestMappingInfo, WebAsyncManager asyncManager, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AsyncWebRequest asyncWebRequest = WebAsyncUtils.createAsyncWebRequest(request, response);
        asyncWebRequest.setTimeout(this.asyncRequestTimeout);
        asyncManager.setAsyncWebRequest(asyncWebRequest);
        asyncManager.startCallableProcessing(() -> {
            ModelAndView mav = null;
            ExpressHttpServletRequest expressHttpServletRequest = new ExpressHttpServletRequest(request, messageConverters);
            ExpressHttpServletResponse expressHttpServletResponse = new ExpressHttpServletResponse(response);

            expressRequestMappingInfo.getHttpRequestHandler().handleRequest(
                    expressHttpServletRequest, expressHttpServletResponse);

            if (expressHttpServletResponse.getBody() != null) {
                convertResponseBody(expressHttpServletResponse);
            }
            mav = expressHttpServletResponse.getModelAndView();
            return mav;
        }, expressRequestMappingInfo);
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
