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
import io.spiffy.common.dto.Context;
import io.spiffy.common.util.ObfuscateUtil;
import io.spiffy.website.annotation.AccessControl;
import io.spiffy.website.annotation.Csrf;
import io.spiffy.website.response.AjaxResponse;
import io.spiffy.website.response.SuccessResponse;
import io.spiffy.website.response.UploadResponse;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class UploadController extends Controller {

    private final MediaClient mediaClient;
    private final StreamClient streamClient;

    @AccessControl
    @RequestMapping("/upload")
    public ModelAndView upload(final Context context) {
        return mav("upload", context);
    }

    @ResponseBody
    @AccessControl
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public AjaxResponse upload(final Context context, @RequestParam final MultipartFile file,
            final @RequestParam String idempotentId) throws IOException {
        final long mediaId = mediaClient.postMedia(idempotentId, MediaType.getEnum(file.getContentType()), file.getBytes());
        final String url = mediaClient.getMedia(mediaId);
        return new UploadResponse(ObfuscateUtil.obfuscate(mediaId), url);
    }

    @ResponseBody
    @Csrf("submit")
    @AccessControl(returnUri = "/upload")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public AjaxResponse submit(final Context context, final @RequestParam String[] media, final @RequestParam String title,
            final @RequestParam(required = false) String description,
            final @RequestParam("idempotentId") String idempotentPrefix) throws IOException {
        final String idempotentId = idempotentPrefix + "-" + context.getAccountId();
        streamClient.postPost(idempotentId, context.getAccountId(), ObfuscateUtil.unobfuscate(media[0]), title, description);
        return new SuccessResponse(true);
    }
}
