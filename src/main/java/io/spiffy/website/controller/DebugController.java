package io.spiffy.website.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.spiffy.common.Controller;
import io.spiffy.common.api.media.client.MediaClient;
import io.spiffy.common.api.media.dto.MediaType;
import io.spiffy.common.api.stream.client.StreamClient;
import io.spiffy.common.dto.Context;
import io.spiffy.common.util.ObfuscateUtil;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DebugController extends Controller {

    private final MediaClient mediaClient;
    private final StreamClient streamClient;

    @ResponseBody
    @RequestMapping("/load")
    public String load(final Context context) throws InterruptedException {
        // for (int i = 1738; i > 1725; i--) {
        // getXKCDPost("" + i);
        // Thread.sleep(5000);
        // }

        getAwwPage("https://www.reddit.com/r/pics/top/?sort=top&t=all");

        return "success";
    }

    private void getAwwPage(final String page) {
        final long accountId = 1000016L;

        try {
            final Document document = Jsoup.connect(page)
                    .userAgent(
                            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36")
                    .referrer("http://www.google.com").get();
            final Elements links = document.getElementsByClass("outbound");

            for (final Element link : links) {
                if (!link.hasClass("title")) {
                    continue;
                }

                try {
                    final String idempotentId = accountId + "-" + link.attr("href");
                    final Long media = getMedia(accountId, idempotentId, link.attr("href"), new HashSet<String>());
                    if (media != null) {
                        streamClient.postPost(idempotentId, accountId, media, link.html());
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }

            final Element next = document.getElementsByAttributeValue("rel", "nofollow next").first();
            System.out.println(next.attr("href"));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void getXKCDPost(final String id) {
        final long accountId = 1000014L;
        try {
            final Document document = Jsoup.connect("http://xkcd.com/" + id)
                    .userAgent(
                            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36")
                    .referrer("http://www.google.com").get();
            final Element comic = document.getElementById("comic");
            final Element image = comic.getElementsByTag("img").first();

            final XKCDPost post = new XKCDPost(id, image.attr("alt"), "https:" + image.attr("src"), image.attr("title"));

            final String idempotentId = accountId + "-" + post.id;
            final Long media = getMedia(accountId, idempotentId, post.getImage(), new HashSet<String>());

            if (media != null) {
                streamClient.postPost(idempotentId, accountId, media, post.title + ":\r\n" + post.description);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class XKCDPost {
        private String id;
        private String title;
        private String image;
        private String description;
    }

    private Long getMedia(final long accountId, final String idempotentId, final String address, final Set<String> stack)
            throws Exception {
        if (stack.contains(address)) {
            return null;
        }

        stack.add(address);

        if (StringUtils.contains(address, "?")) {
            return getMedia(accountId, idempotentId, address.split("\\?")[0], stack);
        }

        if (StringUtils.startsWithIgnoreCase(address, "//")) {
            return getMedia(accountId, idempotentId, "http:" + address, stack);
        }

        if (StringUtils.endsWithIgnoreCase(address, ".gifv")) {
            try {
                final Document document = Jsoup.connect(address)
                        .userAgent(
                                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36")
                        .referrer("http://www.google.com").get();
                final Element source = document.getElementsByTag("source").first();
                return getMedia(accountId, idempotentId, source.attr("src"), stack);
            } catch (final Exception e) {
                return null;
            }
        }

        System.out.println(address);

        final MediaType type = MediaType.getEnum(address.substring(address.lastIndexOf('.') + 1));
        if (type == null) {
            try {
                final String mediaAddress;
                final Document document = Jsoup.connect(address)
                        .userAgent(
                                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36")
                        .referrer("http://www.google.com").get();
                final Elements video = document.getElementsByAttributeValue("property", "og:video");
                if (!video.isEmpty()) {
                    mediaAddress = video.get(0).attr("content");
                } else {
                    final Elements image = document.getElementsByAttributeValue("property", "og:image");
                    mediaAddress = image.get(0).attr("content");
                }

                return getMedia(accountId, idempotentId, mediaAddress, stack);
            } catch (final Exception e) {
                return null;
            }
        }

        final URL url = new URL(address);
        final byte[] bytes;
        try (final InputStream is = url.openStream()) {
            bytes = IOUtils.toByteArray(is);
        }

        final String name = mediaClient.postMedia(accountId, idempotentId, type, bytes);
        if (name == null) {
            return null;
        }

        return ObfuscateUtil.unobfuscate(name);
    }
}
