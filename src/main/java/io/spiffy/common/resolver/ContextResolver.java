package io.spiffy.common.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import io.spiffy.common.Manager;
import io.spiffy.common.dto.Context;

public class ContextResolver extends Manager implements HandlerMethodArgumentResolver {

    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Context.class);
    }

    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        final HttpServletResponse response = (HttpServletResponse) webRequest.getNativeResponse();
        final ModelMap model = mavContainer.getModel();

        return new Context(request, response, model);
    }
}
