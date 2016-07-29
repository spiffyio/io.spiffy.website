package io.spiffy.website.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.spiffy.common.Controller;

public class HomeController extends Controller {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home1(final Locale locale, final Model model) {
        return home(locale, model);
    }

    @RequestMapping(value = "/2", method = RequestMethod.GET)
    public String home2(final Locale locale, final Model model) {
        return home1(locale, model);
    }

    protected String home(final Locale locale, final Model model) {
        logger.info(String.format("Welcome home! The client locale is %s.", locale));

        final Date date = new Date();
        final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        final String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate);

        return "home";
    }
}
