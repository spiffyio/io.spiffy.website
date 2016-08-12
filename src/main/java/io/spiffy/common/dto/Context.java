package io.spiffy.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;

import io.spiffy.common.config.AppConfig;
import io.spiffy.common.util.CsrfUtil;
import io.spiffy.common.util.ListUtil;
import io.spiffy.common.util.ObfuscateUtil;

@Data
@AllArgsConstructor
public class Context {
    private enum CookieAge {
        DELETE(0), SESSION(-1), STANDARD(10 * 365 * 24 * 60 * 60);

        private final int age;

        private CookieAge(final int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }
    }

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
    private final Account account;

    public Context(final HttpServletRequest request, final HttpServletResponse response) {
        this(request, response, null, null, null);
    }

    public Context(final HttpServletRequest request, final HttpServletResponse response, final ModelMap model) {
        this(request, response, null, model, null);
    }

    public Context(final ServletRequest request, final ServletResponse response, final FilterChain chain) {
        this((HttpServletRequest) request, (HttpServletResponse) response, chain, null, null);
    }

    public Context(final Context context, final Account account) {
        this(context.getRequest(), context.getResponse(), context.getChain(), context.getModel(), account);
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

    public String getCookieValue(final String name) {
        final Cookie cookie = getCookie(name);
        if (cookie == null) {
            return null;
        }

        return cookie.getValue();
    }

    public String getSessionId() {
        final String viaHeader = getHeader(SPIFFY_FORWARDED_SESSION);
        if (StringUtils.isNotEmpty(viaHeader)) {
            return viaHeader;
        }

        final String viaCookie = getCookieValue(SESSION_ID_COOKIE);
        if (StringUtils.isNotEmpty(viaCookie)) {
            return viaCookie;
        }

        final String sessionId = UUID.randomUUID().toString() + "-" + ObfuscateUtil.obfuscate(new Date().getTime());
        setCookie(SESSION_ID_COOKIE, sessionId, CookieAge.SESSION);
        deleteCookie(SESSION_TOKEN_COOKIE);

        return sessionId;
    }

    public String getSessionToken() {
        return getCookieValue(SESSION_TOKEN_COOKIE);
    }

    public void sendRedirect(final String uri) throws IOException {
        if (response == null) {
            return;
        }

        response.sendRedirect(uri);
    }

    public void setResponseStatus(final HttpStatus status) {
        if (response == null) {
            return;
        }

        response.setStatus(status.value());
    }

    public void initializeSession(final String token) {
        setCookie(SESSION_ID_COOKIE, CookieAge.STANDARD);
        setCookie(SESSION_TOKEN_COOKIE, token, CookieAge.STANDARD);
    }

    public void invalidateSession() {
        deleteCookie(SESSION_ID_COOKIE);
        deleteCookie(SESSION_TOKEN_COOKIE);
    }

    public void deleteCookie(final String name) {
        setCookie(name, CookieAge.DELETE);
    }

    public void setCookie(final String name, final CookieAge age) {
        setCookie(name, null, age);
    }

    public void setCookie(final String name, final String value, final CookieAge age) {
        if (response == null) {
            return;
        }

        Cookie cookie = getCookie(name);
        if (cookie == null) {
            cookie = new Cookie(name, value);
        }

        cookie.setMaxAge(age.getAge());

        cookie.setPath("/");
        cookie.setSecure(AppConfig.isSecure());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public void addAttribute(final String name, final Object attribute) {
        if (model == null) {
            return;
        }

        model.addAttribute(name, attribute);
    }
}