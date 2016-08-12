package io.spiffy.website.controller;

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
            context.addAttribute(MESSAGE_KEY, "this is not the page you were looking for.");
        } else {
            context.addAttribute(MESSAGE_KEY, "something borked.");
        }

        if (AppConfig.isShowStacktrace()) {
            context.addAttribute(STACKTRACE_KEY, ExceptionUtils.getStackTrace(e));
        }

        return mav("exception", context);
    }
}
