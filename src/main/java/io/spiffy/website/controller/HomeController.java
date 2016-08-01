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
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.email.client.PostEmailAddressClient;
import io.spiffy.common.api.email.input.PostEmailAddressInput;
import io.spiffy.common.api.security.client.EncryptStringClient;
import io.spiffy.common.api.security.client.GetEncryptedStringClient;
import io.spiffy.common.api.security.client.HashStringClient;
import io.spiffy.common.api.security.client.MatchesHashedStringClient;
import io.spiffy.common.api.security.input.PostStringInput;
import io.spiffy.common.dto.Context;
import io.spiffy.user.service.UserAccountService;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class HomeController extends Controller {

    private final HashStringClient postClient;
    private final MatchesHashedStringClient validateClient;
    private final PostEmailAddressClient postEmailAddressClient;
    private final EncryptStringClient encryptClient;
    private final GetEncryptedStringClient getEncryptedClient;
    private final UserAccountService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(final Context context) {
        System.out.println(postEmailAddressClient.call(new PostEmailAddressInput("me@spiffy.io")));
        System.out.println(encryptClient.call(new PostStringInput("john@spiffy.io")));
        System.out.println(getEncryptedClient.call(new GetInput(1000001L)));
        System.out.println(userService.post("john", "me@spiffy.io"));
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
