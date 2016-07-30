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
import io.spiffy.common.dto.Context;
import io.spiffy.invite.service.InviteService;

public class HomeController extends Controller {

    private final InviteService service;

    @Inject
    public HomeController(final InviteService service) {
        this.service = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(final Context context) {
        return home(context.getRequest().getLocale(), context.getModel());
    }

    @RequestMapping(value = "/invite", method = RequestMethod.GET)
    public String invite(final Context context, final @RequestParam String email) {
        service.post(email);
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