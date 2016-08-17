package io.spiffy.website.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;
import io.spiffy.common.dto.Context;

public class RedirectController extends Controller {

    @RequestMapping("/halo/**")
    public ModelAndView home(final Context context) {
        return redirect("/", context);
    }
}