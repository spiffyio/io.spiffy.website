package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.common.api.user.dto.Session;
import io.spiffy.common.api.user.output.AuthenticateAccountOutput;
import io.spiffy.common.dto.Context;
import io.spiffy.common.util.ObfuscateUtil;
import io.spiffy.website.annotation.AccessControl;
import io.spiffy.website.annotation.Csrf;
import io.spiffy.website.google.GoogleClient;
import io.spiffy.website.response.*;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UserController extends Controller {

    private static final String FORM_KEY = "form";
    private static final String RETURN_URI_KEY = "returnUri";
    private static final String SESSIONS_KEY = "sessions";

    private static final String FORM_LOGIN = "login";
    private static final String FORM_REGISTER = "register";

    private final GoogleClient googleClient;
    private final UserClient userClient;

    @AccessControl
    @RequestMapping("/account")
    public ModelAndView account(final Context context) {
        final List<Session> sessions = userClient.getSessions(context.getAccountId());
        context.addAttribute(SESSIONS_KEY, sessions);
        return mav("account", context);
    }

    @RequestMapping("/verify")
    public ModelAndView verify(final Context context, final @RequestParam("email") String token) {
        userClient.verifyEmail(token);
        return mav("account", context);
    }

    @ResponseBody
    @Csrf("verify")
    @AccessControl(returnUri = "/account")
    @RequestMapping(value = "/account/verify", method = RequestMethod.POST)
    public AjaxResponse verifyEmail(final Context context) {
        final boolean success = userClient.sendVerifyEmail(context, context.getEmail());
        return new VerifyResponse(success);
    }

    @RequestMapping({ "/login", "/signin" })
    public ModelAndView login(final Context context,
            final @RequestParam(required = false, defaultValue = "/") String returnUri) {
        context.addAttribute(FORM_KEY, FORM_LOGIN);
        context.addAttribute(RETURN_URI_KEY, returnUri);
        return mav("authenticate", context);
    }

    @ResponseBody
    @Csrf("login")
    @RequestMapping(value = { "/login", "signin" }, method = RequestMethod.POST)
    public AjaxResponse login(final Context context, final @RequestParam String email, final @RequestParam String password,
            final @RequestParam(required = false) String fingerprint,
            final @RequestParam("g-recaptcha-response") String recaptcha) {
        if (!googleClient.recaptcha(context, recaptcha)) {
            return new InvalidRecaptchaResponse();
        }

        final AuthenticateAccountOutput output = userClient.authenticateAccount(email, password, context, fingerprint);
        context.initializeSession(output.getSessionToken());
        return new LoginResponse(output.getSessionToken());
    }

    @RequestMapping({ "/register", "/signup" })
    public ModelAndView register(final Context context,
            final @RequestParam(required = false, defaultValue = "/") String returnUri) {
        context.addAttribute(FORM_KEY, FORM_REGISTER);
        context.addAttribute(RETURN_URI_KEY, returnUri);
        return mav("authenticate", context);
    }

    @ResponseBody
    @Csrf("register")
    @RequestMapping(value = { "/register", "signup" }, method = RequestMethod.POST)
    public AjaxResponse register(final Context context, final @RequestParam String username, final @RequestParam String email,
            final @RequestParam String password, final @RequestParam(required = false) String fingerprint,
            final @RequestParam("g-recaptcha-response") String recaptcha) {
        if (!googleClient.recaptcha(context, recaptcha)) {
            return new InvalidRecaptchaResponse();
        }

        userClient.registerAccount(username, email, password);
        final AuthenticateAccountOutput output = userClient.authenticateAccount(email, password, context, fingerprint);
        context.initializeSession(output.getSessionToken());
        return new LoginResponse(output.getSessionToken());
    }

    @RequestMapping({ "/logout", "/signout" })
    public ModelAndView logout(final Context context,
            final @RequestParam(required = false, defaultValue = "/") String returnUri) {
        context.invalidateSession();
        userClient.invalidateSession(context);
        return redirect(returnUri, context);
    }

    @ResponseBody
    @Csrf("logout")
    @RequestMapping(value = { "/logout", "/signout" }, method = RequestMethod.POST)
    public AjaxResponse logoutSession(final Context context, final @RequestParam String session) {
        final boolean success = userClient.invalidateSession(ObfuscateUtil.unobfuscate(session), context);
        return new LogoutResponse(success);
    }
}
