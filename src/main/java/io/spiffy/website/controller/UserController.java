package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;
import io.spiffy.common.api.media.client.MediaClient;
import io.spiffy.common.api.media.dto.ContentType;
import io.spiffy.common.api.media.output.GetAccountMediaOutput;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.common.dto.Account;
import io.spiffy.common.dto.Context;
import io.spiffy.common.exception.UnknownUserException;
import io.spiffy.website.annotation.AccessControl;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class UserController extends Controller {

    private final MediaClient mediaClient;
    private final UserClient userClient;

    @RequestMapping("/{user}")
    public ModelAndView home(final Context context, final @PathVariable String user) {
        final Account account = userClient.getAccount(user);
        if (account == null) {
            throw new UnknownUserException(user);
        }

        if (!account.getId().equals(context.getAccountId())) {
            return redirect("/" + user + "/stream", context);
        }

        context.addAttribute("myaccount", true);

        return mav("user", context);
    }

    @AccessControl
    @RequestMapping("/{user}/images")
    public ModelAndView images(final Context context, final @PathVariable String user) {
        return media(context, user, ContentType.IMAGE);
    }

    @AccessControl
    @RequestMapping("/{user}/videos")
    public ModelAndView videos(final Context context, final @PathVariable String user) {
        return media(context, user, ContentType.VIDEO);
    }

    private ModelAndView media(final Context context, final String user, final ContentType type) {
        final Account account = userClient.getAccount(user);
        if (account == null) {
            throw new UnknownUserException(user);
        }

        if (!account.getId().equals(context.getAccountId())) {
            return redirect("/" + user + "/stream", context);
        }

        final GetAccountMediaOutput output = mediaClient.getAccountMedia(context.getAccountId(), type, null, 24, true);
        System.out.println(output);

        return mav("user", context);
    }
}