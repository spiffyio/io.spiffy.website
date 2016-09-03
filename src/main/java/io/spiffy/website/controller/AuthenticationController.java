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
import io.spiffy.common.api.user.output.RecoverAccountOutput;
import io.spiffy.common.api.user.output.RegisterAccountOutput;
import io.spiffy.common.dto.Context;
import io.spiffy.common.util.ObfuscateUtil;
import io.spiffy.website.annotation.AccessControl;
import io.spiffy.website.annotation.Csrf;
import io.spiffy.website.google.GoogleClient;
import io.spiffy.website.response.*;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AuthenticationController extends Controller {

    private static final String EMAIL_KEY = "email";
    private static final String FORM_KEY = "form";
    private static final String RETURN_URI_KEY = "returnUri";
    private static final String SESSIONS_KEY = "sessions";
    private static final String TOKEN_KEY = "token";

    private static final String FORM_FORGOT = "forgot";
    private static final String FORM_LOGIN = "login";
    private static final String FORM_REGISTER = "register";
    private static final String FORM_RECOVER = "recover";

    private final GoogleClient googleClient;
    private final UserClient userClient;

    @AccessControl
    @RequestMapping("/account")
    public ModelAndView account(final Context context) {
        return redirect("/" + context.getUsername(), context);
    }

    @AccessControl
    @RequestMapping("/sessions")
    public ModelAndView sessions(final Context context) {
        final List<Session> sessions = userClient.getSessions(context.getAccountId());
        context.addAttribute(SESSIONS_KEY, sessions);
        return mav("account", context);
    }

    @RequestMapping("/recover")
    public ModelAndView recovery(final Context context, final @RequestParam("email") String email,
            final @RequestParam("token") String token) {
        context.addAttribute(FORM_KEY, FORM_RECOVER);
        context.addAttribute(EMAIL_KEY, email);
        context.addAttribute(TOKEN_KEY, token);

        return mav("authenticate", context);
    }

    @ResponseBody
    @Csrf("recover")
    @RequestMapping(value = "/recover", method = RequestMethod.POST)
    public AjaxResponse recover(final Context context, final @RequestParam String email,
            final @RequestParam("password") String password, final @RequestParam("token") String token,
            final @RequestParam(required = false) String fingerprint,
            final @RequestParam("g-recaptcha-response") String recaptcha) {
        if (!googleClient.recaptcha(context, recaptcha)) {
            return new BadRequestResponse("recaptcha", "invalid recaptcha");
        }

        final RecoverAccountOutput output = userClient.recoverAccount(email, token, password, context, fingerprint);
        if (RecoverAccountOutput.Error.INVALID_TOKEN.equals(output.getError())) {
            return new BadRequestResponse("error", "invalid token",
                    "<span class=\"clickable\" data-go=\"/forgot\">recovery token invalid. click here to resend.</span>");
        } else if (RecoverAccountOutput.Error.INVALID_PASSWORD.equals(output.getError())) {
            return new BadRequestResponse("password", "invalid password", "must be 'password' or 'testpass'");
        } else if (RecoverAccountOutput.Error.INVALID_EMAIL.equals(output.getError())) {
            return new BadRequestResponse("email", "invalid email", "email must be @spiffy.io"); // FIXME
        } else if (AuthenticateAccountOutput.Error.UNKNOWN_EMAIL.equals(output.getError())) {
            return new BadRequestResponse("email", "unknown email",
                    "<span class=\"clickable\" data-go=\"/register\">new user? click here to register.</span>");
        }

        context.initializeSession(output.getSessionToken());
        return new LoginResponse(output.getSessionToken());
    }

    @RequestMapping("/verify")
    public ModelAndView verify(final Context context, final @RequestParam("email") String email,
            final @RequestParam("token") String token) {
        userClient.verifyEmail(token);
        return mav("account", context);
    }

    @ResponseBody
    @Csrf("verify")
    @AccessControl(returnUri = "/account")
    @RequestMapping(value = "/account/verify", method = RequestMethod.POST)
    public AjaxResponse verifyEmail(final Context context, final @RequestParam String idempotentId) {
        final boolean success = userClient.sendVerifyEmail(context, context.getEmail(), idempotentId);
        return new VerifyResponse(success);
    }

    @RequestMapping("/forgot")
    public ModelAndView forgot(final Context context,
            final @RequestParam(required = false, defaultValue = "/") String returnUri) {
        context.addAttribute(FORM_KEY, FORM_FORGOT);
        context.addAttribute(RETURN_URI_KEY, returnUri);

        return mav("authenticate", context);
    }

    @ResponseBody
    @Csrf("forgot")
    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public AjaxResponse forgot(final Context context, final @RequestParam String email,
            final @RequestParam("g-recaptcha-response") String recaptcha) {
        if (!googleClient.recaptcha(context, recaptcha)) {
            return new BadRequestResponse("recaptcha", "invalid recaptcha");
        }

        userClient.sendRecoveryEmail(email);

        return new SuccessResponse(true);
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
            return new BadRequestResponse("recaptcha", "invalid recaptcha");
        }

        final AuthenticateAccountOutput output = userClient.authenticateAccount(email, password, context, fingerprint);
        if (AuthenticateAccountOutput.Error.INVALID_PASSWORD.equals(output.getError())) {
            return new BadRequestResponse("password", "invalid password",
                    "<span class=\"clickable\" data-go=\"/forgot\">forgot password? click here to resend.</span>");
        } else if (AuthenticateAccountOutput.Error.INVALID_EMAIL.equals(output.getError())) {
            return new BadRequestResponse("email", "invalid email", "email must be @spiffy.io"); // FIXME
        } else if (AuthenticateAccountOutput.Error.UNKNOWN_EMAIL.equals(output.getError())) {
            return new BadRequestResponse("email", "unknown email",
                    "<span class=\"clickable\" data-go=\"/register\">new user? click here to register.</span>");
        }

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
            return new BadRequestResponse("recaptcha", "invalid recaptcha");
        }

        final RegisterAccountOutput output = userClient.registerAccount(username, email, password, context, fingerprint);
        if (RegisterAccountOutput.Error.INVALID_PASSWORD.equals(output.getError())) {
            return new BadRequestResponse("password", "invalid password", "must be 'password' or 'testpass'");
        } else if (RegisterAccountOutput.Error.INVALID_USERNAME.equals(output.getError())) {
            return new BadRequestResponse("username", "invalid username", "min length: 3, max length: 25");
        } else if (RegisterAccountOutput.Error.INVALID_EMAIL.equals(output.getError())) {
            return new BadRequestResponse("email", "invalid email", "email must be @spiffy.io"); // FIXME
        } else if (RegisterAccountOutput.Error.EXISTING_USERNAME.equals(output.getError())) {
            return new BadRequestResponse("username", "existing username",
                    "<span class=\"clickable\" data-go=\"/login\">existing user? click here to login.</span>");
        } else if (RegisterAccountOutput.Error.EXISTING_EMAIL.equals(output.getError())) {
            return new BadRequestResponse("email", "existing email",
                    "<span class=\"clickable\" data-go=\"/login\">existing user? click here to login.</span>");
        }

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
