package io.spiffy.website.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;
import io.spiffy.common.dto.Context;

public class LegalController extends Controller {

    @RequestMapping("/privacy")
    public ModelAndView privacy(final Context context) {
        return mav("privacy", context);
    }

    @RequestMapping("/terms")
    public ModelAndView terms(final Context context) {
        return mav("terms", context);
    }
}
