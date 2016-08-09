package io.spiffy.website.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;
import io.spiffy.common.dto.Context;

public class LegalController extends Controller {

    private static final String LETTER_KEY = "letter";

    private static final String LETTER_PRIVACY = "privacy";
    private static final String LETTER_TERMS = "terms";

    @RequestMapping("/privacy")
    public ModelAndView privacy(final Context context) {
        context.addAttribute(LETTER_KEY, LETTER_PRIVACY);
        return mav("legal", context);
    }

    @RequestMapping("/terms")
    public ModelAndView terms(final Context context) {
        context.addAttribute(LETTER_KEY, LETTER_TERMS);
        return mav("legal", context);
    }
}
