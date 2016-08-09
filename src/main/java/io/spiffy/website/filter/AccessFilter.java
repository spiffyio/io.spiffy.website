package io.spiffy.website.filter;

import java.io.IOException;

import javax.servlet.ServletException;

import io.spiffy.common.Filter;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.dto.Context;

public class AccessFilter extends Filter {

    public Result doFilter(final Context context) throws IOException, ServletException {
        if (!AppConfig.isForwardToProd()) {
            return Result.Continue;
        }

        if ("24.16.208.98".equalsIgnoreCase(context.getIPAddress())) {
            return Result.Continue;
        }

        final String uri = context.getRequestUri();

        context.sendRedirect("https://spiffy.io" + uri);
        return Result.Halt;
    }
}
