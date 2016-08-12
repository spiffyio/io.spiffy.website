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

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UploadController extends Controller {

    private final MediaClient mediaClient;
    private final StreamClient streamClient;

    @RequestMapping("/upload")
    public ModelAndView upload(final Context context) {
        return mav("upload", context);
    }

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(final Context context, @RequestParam("file") final MultipartFile file,
            final @RequestParam("idempotentId") String idempotentPrefix) throws IOException {
        final String idempotentId = idempotentPrefix + "-" + context.getAccountId();
        final long mediaId = mediaClient.postMedia(idempotentId, MediaType.getEnum(file.getContentType()), file.getBytes());
        streamClient.postPost(idempotentId, context.getAccountId(), mediaId, "title", "description");

        return "{\"success\":true}";
    }
}
