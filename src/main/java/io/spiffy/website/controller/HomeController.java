package io.spiffy.website.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.spiffy.common.Controller;
import io.spiffy.common.api.invite.client.InviteClient;
import io.spiffy.common.api.invite.input.InviteInput;
import io.spiffy.common.dto.Context;

public class HomeController extends Controller {

    private final InviteClient client;

    @Inject
    public HomeController(final InviteClient client) {
        this.client = client;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(final Context context) {
        System.out.println(context.getSessionId());
        return home(context.getRequest().getLocale(), context.getModel());
    }

    @RequestMapping(value = "/extend", method = RequestMethod.GET)
    public String extend(final Context context) {
        context.setSessionExpiry(-1);
        System.out.println(context.getSessionId());
        return home(context.getRequest().getLocale(), context.getModel());
    }

    @RequestMapping(value = "/invite", method = RequestMethod.GET)
    public String invite(final Context context, final @RequestParam String email) {
        System.out.println(client.call(new InviteInput(email)));
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
