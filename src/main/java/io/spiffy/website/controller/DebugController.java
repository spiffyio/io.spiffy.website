package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.net.URL;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.spiffy.common.Controller;
import io.spiffy.common.api.media.client.MediaClient;
import io.spiffy.common.api.media.dto.MediaType;
import io.spiffy.common.api.source.client.SourceClient;
import io.spiffy.common.api.stream.client.StreamClient;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.common.dto.Context;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DebugController extends Controller {

    private final MediaClient mediaClient;
    private final SourceClient sourceClient;
    private final StreamClient streamClient;
    private final UserClient userClient;

    @ResponseBody
    @RequestMapping("/fonts")
    public String fonts(final Context context) {
        return String.join(",", GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
    }

    private void post(final long accountId, final String id) {
        try {
            final Document document = Jsoup.connect("http://xkcd.com/" + id).get();
            final Element comic = document.getElementById("comic");
            final Element image = comic.getElementsByTag("img").first();

            final String src = "https:" + image.attr("src");
            final long mediaId;

            final String idempotentId = "url:" + sourceClient.postUrl(src, "imgs.xkcd.com", id, "xkcd");

            final URL url = new URL(src);
            try (final InputStream is = url.openStream()) {
                final byte[] imageBytes = IOUtils.toByteArray(is);
                mediaId = mediaClient.postMedia(idempotentId, MediaType.PNG, imageBytes);
            }

            final String title = image.attr("alt");
            final String description = image.attr("title");

            streamClient.postPost(idempotentId, accountId, mediaId, title, description);

        } catch (final Exception e) {
        }
    }
}
