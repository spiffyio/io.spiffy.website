package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.common.dto.Context;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HomeController extends Controller {

    private final UserClient userClient;

    @RequestMapping("/")
    public ModelAndView home(final Context context) {
        return mav("home", context);
    }
}
