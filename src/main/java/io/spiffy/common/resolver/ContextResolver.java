package io.spiffy.common.resolver;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import io.spiffy.common.Manager;
import io.spiffy.common.api.notification.client.NotificationClient;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.common.dto.Account;
import io.spiffy.common.dto.Context;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class ContextResolver extends Manager implements HandlerMethodArgumentResolver {

    private final NotificationClient notificationClient;
    private final UserClient userClient;

    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Context.class);
    }

    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        final HttpServletResponse response = (HttpServletResponse) webRequest.getNativeResponse();
        final ModelMap model = mavContainer.getModel();

        final Context context = new Context(request, response, model);
        final Account account = userClient.authenticateSession(context);

        if (account == null && StringUtils.isNotEmpty(context.getSessionToken())) {
            context.newSession();
        }

        if (account != null) {
            final long notificationCount = notificationClient.getUnreadCountCall(account.getId());
            context.setNotificationCount(notificationCount);
        }

        context.addAttribute("account", account);
        context.addAttribute("context", context);
        return new Context(context, account);
    }
}
