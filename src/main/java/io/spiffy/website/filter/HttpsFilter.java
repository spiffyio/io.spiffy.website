package io.spiffy.website.filter;

import java.io.IOException;

import javax.servlet.ServletException;

import io.spiffy.common.Filter;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.dto.Context;

public class HttpsFilter extends Filter {

    public Result doFilter(final Context context) throws IOException, ServletException {
        if (!AppConfig.isSecure()) {
            return Result.Continue;
        }

        if (context.isSecure()) {
            return Result.Continue;
        }

        final String host = context.getHost();
        final String uri = context.getRequestUri();

        context.sendRedirect("https://" + host + uri);
        return Result.Halt;
    }
}