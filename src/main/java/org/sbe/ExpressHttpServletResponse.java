package org.sbe;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.Map;

/**
 * Created by david on 2016-01-22.
 */
public class ExpressHttpServletResponse extends HttpServletResponseWrapper {

    private ModelAndView modelAndView;
    private Object body;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param response The response to wrap
     * @throws IllegalArgumentException if the response is null
     */
    public ExpressHttpServletResponse(HttpServletResponse response) {
        super(response);
    }

    public ModelAndView getModelAndView() {
        return modelAndView;
    }

    public Object getBody() {
        return body;
    }

    public void render(String view) {
        this.modelAndView = new ModelAndView(view);
    }

    public void render(String view, Map<String, ?> model) {
        this.modelAndView = new ModelAndView(view, model);
    }

    public void send(String str, Object... args) throws IOException {
        if (str != null) {
            getWriter().write(String.format(str, args));
        }
    }

    public void sendJson(Object body) throws IOException {
        setContentType(MediaType.APPLICATION_JSON_VALUE);
        this.body = body;
    }

}
