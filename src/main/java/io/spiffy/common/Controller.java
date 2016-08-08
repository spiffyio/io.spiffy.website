package io.spiffy.common;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.dto.Context;

@RequestMapping
public abstract class Controller extends Manager {

    public static ModelAndView mav(final String view, final Context context) {
        return new ModelAndView(view, context.getModel());
    }

    public static ModelAndView redirect(final String uri, final Context context) {
        final ModelMap model = context.getModel();
        if (model != null) {
            model.clear();
        }

        return new ModelAndView("redirect:" + uri, model);
    }
}
