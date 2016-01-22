package org.sbe;

import org.springframework.http.HttpMethod;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2016-01-21.
 */
public class ExpressController extends AbstractController {
    private final ExpressContext context;

    public ExpressController(ExpressContext context) {
        this.context = context;

        List<String> supportedMethods = new ArrayList<>();
        for (HttpMethod httpMethod : HttpMethod.values()) {
            supportedMethods.add(httpMethod.name());
        }
        setSupportedMethods(supportedMethods.toArray(new String[supportedMethods.size()]));
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AsyncContext asyncContext = request.startAsync();

        HttpRequestHandler httpRequestHandler = context.resolveHandler(
                HttpMethod.resolve(request.getMethod()), request.getRequestURI());

        if (httpRequestHandler != null) {
            httpRequestHandler.handleRequest(request, response);
        }

        asyncContext.complete();
        return null;
    }
}
