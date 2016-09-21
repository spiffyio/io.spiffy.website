package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;
import io.spiffy.common.api.media.client.MediaClient;
import io.spiffy.common.api.media.dto.MediaType;
import io.spiffy.common.api.stream.client.StreamClient;
import io.spiffy.common.api.stream.output.PostPostOutput;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.common.dto.Context;
import io.spiffy.common.util.ObfuscateUtil;
import io.spiffy.website.annotation.AccessControl;
import io.spiffy.website.annotation.Csrf;
import io.spiffy.website.response.AjaxResponse;
import io.spiffy.website.response.BadRequestResponse;
import io.spiffy.website.response.SuccessResponse;
import io.spiffy.website.response.UploadResponse;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UploadController extends Controller {

    private static final String FORM_BANNER = "form=banner";
    private static final String FORM_ICON = "form=icon";
    private static final String FORM_FILE = "form=file";

    private final MediaClient mediaClient;
    private final StreamClient streamClient;
    private final UserClient userClient;

    @AccessControl
    @RequestMapping("/upload")
    public ModelAndView upload(final Context context) {
        return mav("upload", context);
    }

    @ResponseBody
    @AccessControl
    @RequestMapping(value = "/upload", method = RequestMethod.POST, params = { FORM_BANNER })
    public AjaxResponse uploadBanner(final Context context, @RequestParam(name = "banner") final MultipartFile file,
            final @RequestParam String idempotentId) throws IOException {
        final MediaType type = MediaType.getEnum(file.getContentType());
        final String name = mediaClient.postMedia(context.getAccountId(), idempotentId, type, file.getBytes());
        userClient.postAccount(context.getUsername(), context.getEmail(), null, ObfuscateUtil.unobfuscate(name));
        return new SuccessResponse(true);
    }

    @ResponseBody
    @AccessControl
    @RequestMapping(value = "/upload", method = RequestMethod.POST, params = { FORM_ICON })
    public AjaxResponse uploadIcon(final Context context, @RequestParam(name = "icon[0]") final MultipartFile file,
            @RequestParam(name = "icon[1]") final MultipartFile thumbnail, final @RequestParam String idempotentId)
            throws IOException {
        final MediaType type = MediaType.getEnum(file.getContentType());
        final String name = mediaClient.postMedia(context.getAccountId(), idempotentId, type, file.getBytes(),
                thumbnail.getBytes());
        userClient.postAccount(context.getUsername(), context.getEmail(), ObfuscateUtil.unobfuscate(name), null);
        return new SuccessResponse(true);
    }

    @ResponseBody
    @AccessControl
    @RequestMapping(value = "/upload", method = RequestMethod.POST, params = { FORM_FILE })
    public AjaxResponse upload(final Context context, @RequestParam final MultipartFile file,
            final @RequestParam String idempotentId) throws IOException {
        final MediaType type = MediaType.getEnum(file.getContentType());
        final String name = mediaClient.postMedia(context.getAccountId(), idempotentId, type, file.getBytes());
        return new UploadResponse(name);
    }

    @ResponseBody
    @Csrf("submit")
    @AccessControl(returnUri = "/upload")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public AjaxResponse submit(final Context context, final @RequestParam String[] media,
            final @RequestParam(required = false) String description,
            final @RequestParam("idempotentId") String idempotentPrefix) throws IOException {
        final String idempotentId = idempotentPrefix + "-" + context.getAccountId();

        final PostPostOutput output = streamClient.postPost(idempotentId, context.getAccountId(),
                ObfuscateUtil.unobfuscate(media[0]), description);
        if (output.getError() != null) {
            return new BadRequestResponse("error", "something went wrong");
        }

        return new UploadResponse(output.getName());
    }
}
