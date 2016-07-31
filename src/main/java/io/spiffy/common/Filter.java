package io.spiffy.common;

import java.io.IOException;

import javax.servlet.*;

import io.spiffy.common.dto.Context;

public abstract class Filter extends Manager implements javax.servlet.Filter {

    public enum Result {
        Continue, Halt
    }

    public void init(final FilterConfig config) throws ServletException {

    }

    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        final Context context = new Context(request, response, chain);

        final Result result = doFilter(context);
        if (result == Result.Halt) {
            return;
        }

        chain.doFilter(request, response);
    }

    public void destroy() {

    }

    public abstract Result doFilter(final Context context) throws IOException, ServletException;
}
