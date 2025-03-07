package io.spiffy.website.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;

import io.spiffy.common.Aspect;
import io.spiffy.common.dto.ClassMap;
import io.spiffy.common.dto.Context;
import io.spiffy.website.annotation.Csrf;
import io.spiffy.website.response.AjaxResponse;
import io.spiffy.website.response.BadRequestResponse;

@org.aspectj.lang.annotation.Aspect
public class CsrfAspect extends Aspect {

    @Around("@annotation(csrf)")
    public Object around(final ProceedingJoinPoint pjp, final Csrf csrf) throws Throwable {
        final MethodSignature signature = (MethodSignature) pjp.getSignature();
        final ClassMap args = new ClassMap(signature.getParameterTypes(), pjp.getArgs());
        final Context context = args.get(Context.class);

        if (context.isCsrfTokenValid(csrf.value())) {
            return pjp.proceed();
        }

        final Method method = signature.getMethod();
        final Class<?> resultClass = method.getReturnType();

        context.setResponseStatus(HttpStatus.UNAUTHORIZED);

        if (AjaxResponse.class.equals(resultClass)) {
            return new BadRequestResponse("token", "invalid");
        }

        throw new Exception();
    }
}
