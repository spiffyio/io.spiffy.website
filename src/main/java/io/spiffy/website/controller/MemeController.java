package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.spiffy.common.Controller;
import io.spiffy.common.api.media.client.MediaClient;
import io.spiffy.common.api.media.dto.MediaType;
import io.spiffy.common.dto.Context;
import io.spiffy.common.util.ImageUtil;
import io.spiffy.common.util.ObfuscateUtil;
import io.spiffy.website.response.AjaxResponse;
import io.spiffy.website.response.UploadResponse;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MemeController extends Controller {

    private final MediaClient mediaClient;

    @ResponseBody
    @RequestMapping(value = "/meme", method = RequestMethod.POST)
    public AjaxResponse upload(final Context context, @RequestParam final MultipartFile file,
            final @RequestParam String idempotentId) throws IOException {
        final byte[] macro = ImageUtil.macro(file.getBytes(), "", "make all the memes");
        final long mediaId = mediaClient.postMedia(idempotentId, MediaType.getEnum(file.getContentType()), macro);

        return new UploadResponse(ObfuscateUtil.obfuscate(mediaId));
    }
}
