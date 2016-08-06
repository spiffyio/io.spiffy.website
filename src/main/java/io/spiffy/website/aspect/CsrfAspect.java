package io.spiffy.website.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;

import io.spiffy.common.Aspect;
import io.spiffy.common.dto.ClassMap;
import io.spiffy.common.dto.Context;
import io.spiffy.website.annotation.Csrf;

@org.aspectj.lang.annotation.Aspect
public class CsrfAspect extends Aspect {

    @Around("@annotation(csrf)")
    public Object around(final ProceedingJoinPoint pjp, final Csrf csrf) throws Throwable {
        final MethodSignature signature = (MethodSignature) pjp.getSignature();
        final ClassMap args = new ClassMap(signature.getParameterTypes(), pjp.getArgs());
        final Context context = args.get(Context.class);

        System.out.println(context.getCsrfToken());
        System.out.println(context.generateCsrfToken(csrf.value()));

        if (context.isCsrfTokenValid(csrf.value())) {
            return pjp.proceed();
        }

        throw new Exception();
    }
}
