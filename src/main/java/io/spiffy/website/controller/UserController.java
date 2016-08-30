package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.common.dto.Account;
import io.spiffy.common.dto.Context;
import io.spiffy.common.exception.UnknownUserException;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UserController extends Controller {

    private final UserClient userClient;

    @RequestMapping("/{user}")
    public ModelAndView home(final Context context, final @PathVariable String user) {
        final Account account = userClient.getAccount(user);
        if (account == null) {
            throw new UnknownUserException(user);
        }

        if (account.getId().equals(context.getAccountId())) {
            context.addAttribute("myaccount", true);
        } else {
            return redirect("/" + user + "/stream", context);
        }

        return mav("user", context);
    }
}