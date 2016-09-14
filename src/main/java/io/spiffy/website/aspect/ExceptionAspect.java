package io.spiffy.website.aspect;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import io.spiffy.common.Aspect;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.dto.ClassMap;
import io.spiffy.common.dto.Context;
import io.spiffy.common.exception.InvalidParameterException;
import io.spiffy.common.exception.MissingParameterException;
import io.spiffy.common.exception.UnknownPostException;
import io.spiffy.common.exception.UnknownUserException;
import io.spiffy.website.response.BadRequestResponse;

@org.aspectj.lang.annotation.Aspect
public class ExceptionAspect extends Aspect {

    private static final String CODE_KEY = "code";
    private static final String MESSAGE_KEY = "message";
    private static final String STACKTRACE_KEY = "stacktrace";
    private static final Set<Character> OOPSIE_ENDERS;

    static {
        final Set<Character> oopsieEnders = new HashSet<>();
        ".!?>)]};:'\"_-+*".chars().mapToObj(c -> (char) c).forEach(c -> oopsieEnders.add(c));
        OOPSIE_ENDERS = Collections.unmodifiableSet(oopsieEnders);
    }

    @Around("@annotation(requestMapping)")
    public Object around(final ProceedingJoinPoint pjp, final RequestMapping requestMapping) throws Throwable {
        try {
            return pjp.proceed();
        } catch (final Exception e) {
            return handleException(pjp, e);
        }
    }

    private Object handleException(final ProceedingJoinPoint pjp, final Exception e) {
        final MethodSignature signature = (MethodSignature) pjp.getSignature();
        final ClassMap args = new ClassMap(signature.getParameterTypes(), pjp.getArgs());
        final Context context = args.get(Context.class);

        final HttpStatus status;
        if (e instanceof NoHandlerFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof UnknownPostException) {
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof UnknownUserException) {
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof MissingParameterException) {
            final MissingParameterException mpe = (MissingParameterException) e;
            return invalidParameter(context, mpe, mpe.getParameterName(), "required");
        } else if (e instanceof InvalidParameterException) {
            final InvalidParameterException ipe = (InvalidParameterException) e;
            return invalidParameter(context, ipe, ipe.getParameterName(), ipe.getReason());
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return mav(context, e, status);
    }

    private Object invalidParameter(final Context context, final Exception e, final String parameterName, final String reason) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        if (context.isJsonRequest()) {
            context.setResponseStatus(status);
            final String name = parameterName;
            return new BadRequestResponse(name, name + " " + reason);
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
                final ModelMap model = context.getModel();
                model.clear();
                return new ModelAndView("redirect:" + builder.toString(), model);
            }
            context.addAttribute(MESSAGE_KEY, "this is not the page you were looking for.");
        } else {
            logger.error("unhandled error", e);
            context.addAttribute(MESSAGE_KEY, "something borked.");
        }

        if (AppConfig.isShowStacktrace()) {
            context.addAttribute(STACKTRACE_KEY, ExceptionUtils.getStackTrace(e));
        }

        return new ModelAndView("exception", context.getModel());
    }
}
