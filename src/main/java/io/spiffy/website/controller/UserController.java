package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;
import io.spiffy.common.api.media.client.MediaClient;
import io.spiffy.common.api.media.dto.ContentType;
import io.spiffy.common.api.media.output.GetAccountMediaOutput;
import io.spiffy.common.api.notification.client.NotificationClient;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.common.dto.Account;
import io.spiffy.common.dto.Context;
import io.spiffy.common.exception.UnknownUserException;
import io.spiffy.website.annotation.AccessControl;
import io.spiffy.website.annotation.Csrf;
import io.spiffy.website.response.AjaxResponse;
import io.spiffy.website.response.BadRequestResponse;
import io.spiffy.website.response.NotificationsResponse;
import io.spiffy.website.response.SuccessResponse;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class UserController extends Controller {

    private final MediaClient mediaClient;
    private final NotificationClient notificationClient;
    private final UserClient userClient;

    @RequestMapping("/{user}")
    public ModelAndView home(final Context context, final @PathVariable String user) {
        final Account account = userClient.getAccount(user);
        if (account == null) {
            throw new UnknownUserException(user);
        }

        return redirect("/" + user + "/stream", context);
    }

    @AccessControl
    @RequestMapping("/notifications")
    public ModelAndView notifications(final Context context) {
        context.addAttribute("notifications", notificationClient.getNoficiations(context.getAccountId()));
        return mav("notifications", context);
    }

    @ResponseBody
    @AccessControl
    @Csrf("notifications")
    @RequestMapping(value = "/notifications", method = RequestMethod.POST)
    public AjaxResponse getNotificationCount(final Context context) {
        final long notificationCount = notificationClient.getUnreadCount(context.getAccountId());
        return new NotificationsResponse(notificationCount);
    }

    @ResponseBody
    @AccessControl
    @Csrf("delete")
    @RequestMapping(value = "/{user}/media/delete", method = RequestMethod.POST)
    public AjaxResponse delete(final Context context, final @PathVariable String user, final @RequestParam String media) {
        final Account account = userClient.getAccount(user);
        if (account == null || !account.getId().equals(context.getAccountId())) {
            return new BadRequestResponse("error", "unauthorized", null);
        }

        mediaClient.deleteMedia(account.getId(), Arrays.asList(media.split(",")));

        return new SuccessResponse(true);
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

        final GetAccountMediaOutput output = mediaClient.getAccountMedia(context.getAccountId(), type, null, 1000, true);
        context.addAttribute("media", output.getContents());

        return mav("media", context);
    }
}