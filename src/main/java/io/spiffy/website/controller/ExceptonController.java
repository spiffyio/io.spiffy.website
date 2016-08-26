package io.spiffy.website.controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import io.spiffy.common.Controller;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.dto.Context;
import io.spiffy.common.exception.InvalidParameterException;
import io.spiffy.common.exception.MissingParameterException;
import io.spiffy.common.util.JsonUtil;
import io.spiffy.website.response.BadRequestResponse;

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

    @ExceptionHandler(MissingParameterException.class)
    public ModelAndView missingParameterException(final Context context, final MissingParameterException e) {
        return invalidParameter(context, e, e.getParameterName(), "required");
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ModelAndView invalidParameterException(final Context context, final InvalidParameterException e) {
        return invalidParameter(context, e, e.getParameterName(), e.getReason());
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exception(final Context context, final Exception e) {
        return mav(context, e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ModelAndView invalidParameter(final Context context, final Exception e, final String parameterName,
            final String reason) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        if (context.isJsonRequest()) {
            context.setResponseStatus(status);

            final String name = parameterName;
            final BadRequestResponse response = new BadRequestResponse(name, name + " " + reason);
            final String json = JsonUtil.serialize(response);
            final ModelMap model = JsonUtil.deserialize(ModelMap.class, json);

            return new ModelAndView(new MappingJackson2JsonView(), model);
        }

        return mav(context, e, status);
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
