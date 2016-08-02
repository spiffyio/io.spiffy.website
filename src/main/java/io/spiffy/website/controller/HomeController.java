package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.spiffy.common.Controller;
import io.spiffy.common.api.email.client.EmailClient;
import io.spiffy.common.api.security.client.SecurityClient;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.common.dto.Context;
import io.spiffy.email.manager.EmailManager;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class HomeController extends Controller {

    private final EmailClient emailClient;
    private final SecurityClient securityClient;
    private final UserClient userClient;

    private final EmailManager emailManager;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(final Context context) {
        System.out.println(emailClient.postEmailAddress("me@spiffy.io"));
        System.out.println(securityClient.encryptString("john@spiffy.io"));
        System.out.println(securityClient.decryptString(1000001L));
        System.out.println(userClient.postAccount("john", "me@spiffy.io"));

        // emailManager.send("john <john@spiffy.io>");

        context.addAttribute("csrf", context.generateCsrfToken("home"));
        return home(context.getRequest().getLocale(), context.getModel());
    }

    protected String home(final Locale locale, final ModelMap model) {
        logger.info(String.format("Welcome home! The client locale is %s.", locale));

        final Date date = new Date();
        final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        final String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate);

        return "home";
    }
}
