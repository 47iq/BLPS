package org.iq47.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class ApiKeyFilter implements Filter {

    @Value("${X_EDX_API_KEY}")
    private String API_KEY;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        final String authorizationHeader = req.getHeader("X_EDX_API_KEY");
        if(authorizationHeader == null) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else if(!authorizationHeader.equals(this.API_KEY)) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        chain.doFilter(request, response);
    }
}