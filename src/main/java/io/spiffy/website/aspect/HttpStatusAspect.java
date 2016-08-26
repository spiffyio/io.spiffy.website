package io.spiffy.website.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;

import io.spiffy.common.Aspect;
import io.spiffy.common.dto.ClassMap;
import io.spiffy.common.dto.Context;
import io.spiffy.website.response.AjaxResponse;
import io.spiffy.website.response.BadRequestResponse;

@org.aspectj.lang.annotation.Aspect
public class HttpStatusAspect extends Aspect {

    @Around("@annotation(responseBody)")
    public Object around(final ProceedingJoinPoint pjp, final ResponseBody responseBody) throws Throwable {
        final MethodSignature signature = (MethodSignature) pjp.getSignature();
        final ClassMap args = new ClassMap(signature.getParameterTypes(), pjp.getArgs());
        final Context context = args.get(Context.class);

        if (context == null) {
            return pjp.proceed();
        }

        if (context.getResponse() == null) {
            return pjp.proceed();
        }

        final Method method = signature.getMethod();
        final Class<?> resultClass = method.getReturnType();

        if (!AjaxResponse.class.equals(resultClass)) {
            return pjp.proceed();
        }

        final AjaxResponse response = (AjaxResponse) pjp.proceed();
        if (response == null) {
            return response;
        }

        if (response instanceof BadRequestResponse) {
            context.setResponseStatus(HttpStatus.BAD_REQUEST);
        }

        return response;
    }
}