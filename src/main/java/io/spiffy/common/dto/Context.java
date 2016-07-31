package io.spiffy.common.dto;

import lombok.Data;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import io.spiffy.common.util.ListUtil;

@Data
public class Context {
    private static final String SESSION_ID_COOKIE = "sessionId";

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ModelMap model;

    public HttpSession getSession() {
        return request.getSession(true);
    }

    public String getSessionId() {
        return getSession().getId();
    }

    public void setSessionExpiry(final int expiry) {
        if (response == null) {
            return;
        }

        final Cookie cookie = getCookie(SESSION_ID_COOKIE);
        if (cookie == null) {
            return;
        }

        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
    }

    public Cookie getCookie(final String name) {
        if (name == null) {
            return null;
        }

        if (request == null) {
            return null;
        }

        final List<Cookie> cookies = ListUtil.asList(request.getCookies());
        for (final Cookie cookie : cookies) {
            if (name.equalsIgnoreCase(cookie.getName())) {
                return cookie;
            }
        }

        return null;
    }
}