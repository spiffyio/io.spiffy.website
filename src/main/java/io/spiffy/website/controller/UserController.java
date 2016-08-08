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
import io.spiffy.website.response.LoginResponse;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class UserController extends Controller {

    private final UserClient userClient;

    @RequestMapping({ "/login", "/signin" })
    public ModelAndView login(final Context context,
            final @RequestParam(required = false, defaultValue = "/") String returnUri) {
        return mav("authenticate", context);
    }

    @ResponseBody
    @Csrf("login")
    @RequestMapping(value = { "/login", "signin" }, method = RequestMethod.POST)
    public LoginResponse login(final Context context, final @RequestParam String email, final @RequestParam String password) {
        final AuthenticateAccountOutput output = userClient.authenticateAccount(email, password, context);
        context.initializeSession(output.getSessionToken());
        return new LoginResponse(output.getSessionToken());
    }

    @RequestMapping({ "/register", "/signup" })
    public ModelAndView register(final Context context,
            final @RequestParam(required = false, defaultValue = "/") String returnUri) {
        return mav("authenticate", context);
    }

    @ResponseBody
    @Csrf("register")
    @RequestMapping(value = { "/register", "signup" }, method = RequestMethod.POST)
    public LoginResponse register(final Context context, final @RequestParam String username, final @RequestParam String email,
            final @RequestParam String password) {
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
