package io.spiffy.website.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;

@RequestMapping("/error")
public class ErrorController extends Controller {

    @RequestMapping("/**")
    public ModelAndView embed() {
        return new ModelAndView("embed");
    }
}