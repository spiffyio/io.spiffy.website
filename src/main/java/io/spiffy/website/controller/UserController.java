package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.common.api.user.output.AuthenticateAccountOutput;
import io.spiffy.common.dto.Context;
import io.spiffy.website.annotation.Csrf;
import io.spiffy.website.google.GoogleClient;
import io.spiffy.website.response.AjaxResponse;
import io.spiffy.website.response.InvalidRecaptchaResponse;
import io.spiffy.website.response.LoginResponse;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UserController extends Controller {

    private static final String FORM_KEY = "form";
    private static final String RETURN_URI_KEY = "returnUri";

    private static final String FORM_LOGIN = "login";
    private static final String FORM_REGISTER = "register";

    private final GoogleClient googleClient;
    private final UserClient userClient;

    @RequestMapping("/account")
    public ModelAndView account(final Context context) {
        return mav("account", context);
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
            final @RequestParam("g-recaptcha-response") String recaptcha) {
        if (!googleClient.recaptcha(context, recaptcha)) {
            return new InvalidRecaptchaResponse();
        }

        final AuthenticateAccountOutput output = userClient.authenticateAccount(email, password, context);
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
            final @RequestParam String password, final @RequestParam("g-recaptcha-response") String recaptcha) {
        if (!googleClient.recaptcha(context, recaptcha)) {
            return new InvalidRecaptchaResponse();
        }

        userClient.registerAccount(username, email, password);
        final AuthenticateAccountOutput output = userClient.authenticateAccount(email, password, context);
        context.initializeSession(output.getSessionToken());
        return new LoginResponse(output.getSessionToken());
    }

    @RequestMapping({ "/logout", "/signout" })
    public ModelAndView logout(final Context context,
            final @RequestParam(required = false, defaultValue = "/") String returnUri) {
        context.invalidateSession();
        return redirect(returnUri, context);
    }
}
