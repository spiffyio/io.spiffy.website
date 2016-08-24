package io.spiffy.website.controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import io.spiffy.common.Controller;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.dto.Context;

@ControllerAdvice
public class ExceptonController extends Controller {

    private static final String CODE_KEY = "code";
    private static final String MESSAGE_KEY = "message";
    private static final String STACKTRACE_KEY = "stacktrace";
    private static final Set<Character> OOPSIE_ENDERS;

    static {
        final Set<Character> oopsieEnders = new HashSet<>();
        ".!?>)]};:'\"_-+*".chars().mapToObj(c -> (char) c).forEach(c -> oopsieEnders.add(c));
        OOPSIE_ENDERS = Collections.unmodifiableSet(oopsieEnders);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView noHandlerFoundException(final Context context, final NoHandlerFoundException e) {
        return mav(context, e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exception(final Context context, final Exception e) {
        return mav(context, e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ModelAndView mav(final Context context, final Exception e, final HttpStatus status) {
        context.setResponseStatus(status);
        context.addAttribute(CODE_KEY, status + ": " + status.getReasonPhrase());

        if (HttpStatus.NOT_FOUND.equals(status)) {
            final String uri = context.getRequestUri();
            final char ender = uri.charAt(uri.length() - 1);
            if (OOPSIE_ENDERS.contains(ender)) {
                final StringBuilder builder = new StringBuilder();
                StringBuilder window = new StringBuilder();
                for (final char c : uri.toCharArray()) {
                    window.append(c);
                    if (!OOPSIE_ENDERS.contains(c)) {
                        builder.append(window);
                        window = new StringBuilder();
                    }
                }
                return redirect(builder.toString(), context);
            }
            context.addAttribute(MESSAGE_KEY, "this is not the page you were looking for.");
        } else {
            context.addAttribute(MESSAGE_KEY, "something borked.");
        }

        if (AppConfig.isShowStacktrace()) {
            context.addAttribute(STACKTRACE_KEY, ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        }

        return mav("exception", context);
    }
}
