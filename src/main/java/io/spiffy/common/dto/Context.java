package io.spiffy.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;

import io.spiffy.common.config.AppConfig;
import io.spiffy.common.util.CsrfUtil;
import io.spiffy.common.util.ListUtil;

@Data
@AllArgsConstructor
public class Context {
    public static final String SESSION_ID_COOKIE = "session-id";
    public static final String SESSION_TOKEN_COOKIE = "session-token";

    public static final String USER_AGENT = "User-Agent";
    public static final String SPIFFY_FORWARDED_SESSION = "SPIFFY-Forwarded-Session";
    public static final String SPIFFY_API_CERTIFICATE = "SPIFFY-API-Certificate";
    public static final String X_CSRF_TOKEN = "X-CSRF-Token";
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String X_FORWARDED_HOST = "X-Forwarded-Host";
    public static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";
    public static final String X_SSL_SECURE = "X-SSL-Secure";

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final FilterChain chain;
    private final ModelMap model;

    public Context(final HttpServletRequest request, final HttpServletResponse response) {
        this(request, response, null, null);
    }

    public Context(final HttpServletRequest request, final HttpServletResponse response, final ModelMap model) {
        this(request, response, null, model);
    }

    public Context(final ServletRequest request, final ServletResponse response, final FilterChain chain) {
        this((HttpServletRequest) request, (HttpServletResponse) response, chain, null);
    }

    public boolean isSecure() {
        if (request == null) {
            return false;
        }

        if (request.isSecure()) {
            return true;
        }

        if ("https".equalsIgnoreCase(getHeader(X_FORWARDED_PROTO))) {
            return true;
        }

        return "true".equalsIgnoreCase(getHeader(X_SSL_SECURE));
    }

    public boolean isCsrfTokenValid(final String name) {
        return CsrfUtil.validateToken(getSessionId(), name, getCsrfToken());
    }

    public String generateCsrfToken(final String name) {
        return CsrfUtil.generateToken(getSessionId(), name);
    }

    public String getCsrfToken() {
        return getHeader(X_CSRF_TOKEN);
    }

    public String getHost() {
        if (request == null) {
            return null;
        }

        return request.getServerName();
    }

    public String getIPAddress() {
        if (request == null) {
            return null;
        }

        final String xff = request.getHeader(X_FORWARDED_FOR);
        return xff != null ? xff : request.getRemoteAddr();
    }

    public String getRequestUri() {
        if (request == null) {
            return null;
        }

        return request.getRequestURI();
    }

    public HttpSession getSession() {
        return request.getSession(true);
    }

    public String getUserAgent() {
        return getHeader(USER_AGENT);
    }

    public String getHeader(final String name) {
        if (name == null) {
            return null;
        }

        if (request == null) {
            return null;
        }

        return request.getHeader(name);
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

    public String getSessionId() {
        final String sessionId = getHeader(SPIFFY_FORWARDED_SESSION);
        if (StringUtils.isNotEmpty(sessionId)) {
            return sessionId;
        }

        return getSession().getId();
    }

    public void sendRedirect(final String uri) throws IOException {
        if (response == null) {
            return;
        }

        response.sendRedirect(uri);
    }

    public void setSessionToken(final String token) {
        System.out.println(token);

        final Cookie cookie = new Cookie(SESSION_TOKEN_COOKIE, token);
        cookie.setPath("/");
        cookie.setSecure(AppConfig.isSecure());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(8640000);
        response.addCookie(cookie);
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

    public void addAttribute(final String name, final Object attribute) {
        if (model == null) {
            return;
        }

        model.addAttribute(name, attribute);
    }
}