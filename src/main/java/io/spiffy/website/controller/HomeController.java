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
import io.spiffy.common.api.security.client.PostStringClient;
import io.spiffy.common.api.security.client.ValidateStringClient;
import io.spiffy.common.api.security.input.PostStringInput;
import io.spiffy.common.api.security.input.ValidateStringInput;
import io.spiffy.common.dto.Context;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class HomeController extends Controller {

    private final PostStringClient postClient;
    private final ValidateStringClient validateClient;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(final Context context) {
        System.out.println(context.getSessionId());
        System.out.println(postClient.call(new PostStringInput("password")));
        System.out.println(validateClient.call(new ValidateStringInput(1000000L, "password")));
        System.out.println(validateClient.call(new ValidateStringInput(50L, "password")));
        System.out.println(validateClient.call(new ValidateStringInput(1000003L, "password")));
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
