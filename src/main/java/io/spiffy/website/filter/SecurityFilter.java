package io.spiffy.website.filter;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;

import io.spiffy.common.Filter;
import io.spiffy.common.dto.Context;

public class SecurityFilter extends Filter {

    public Result doFilter(final Context context) throws IOException, ServletException {
        if (context == null) {
            return Result.Continue;
        }

        final String uri = context.getRequestUri();
        if (uri == null) {
            return Result.Continue;
        }

        System.out.println(uri);

        final boolean embeddable = StringUtils.endsWithIgnoreCase(uri, "/embed")
                || StringUtils.containsIgnoreCase(uri, "/embed/");
        context.setResponseEmbeddable(embeddable);

        return Result.Continue;
    }
}
