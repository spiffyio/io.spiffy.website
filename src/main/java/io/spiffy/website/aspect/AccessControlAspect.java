package io.spiffy.website.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Aspect;
import io.spiffy.common.Controller;
import io.spiffy.common.dto.ClassMap;
import io.spiffy.common.dto.Context;
import io.spiffy.website.annotation.AccessControl;
import io.spiffy.website.response.AjaxResponse;
import io.spiffy.website.response.RedirectResponse;

@org.aspectj.lang.annotation.Aspect
public class AccessControlAspect extends Aspect {

    @Around("@within(accessControl) or @annotation(accessControl)")
    public Object around(final ProceedingJoinPoint pjp, final AccessControl accessControl) throws Throwable {
        final MethodSignature signature = (MethodSignature) pjp.getSignature();
        final ClassMap args = new ClassMap(signature.getParameterTypes(), pjp.getArgs());
        final Context context = args.get(Context.class);

        if (context.isAuthenticated()) {
            return pjp.proceed();
        }

        final Method method = signature.getMethod();
        final Class<?> resultClass = method.getReturnType();

        final String uri = "/login?returnUri=" + context.getRequestUri();

        context.setResponseStatus(HttpStatus.UNAUTHORIZED);

        if (ModelAndView.class.equals(resultClass)) {
            return Controller.redirect(uri, context);
        }

        if (AjaxResponse.class.equals(resultClass)) {
            return new RedirectResponse(uri);
        }

        return pjp.proceed();
    }
}