package io.spiffy.website.manager;

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
import org.springframework.scheduling.annotation.Scheduled;

import com.amazonaws.services.sqs.AmazonSQSClient;

import io.spiffy.common.api.media.client.MediaClient;
import io.spiffy.common.api.media.dto.MediaType;
import io.spiffy.common.api.stream.client.StreamClient;
import io.spiffy.common.event.IngestEvent;
import io.spiffy.common.event.IngestPostEvent;
import io.spiffy.common.event.IngestSourceEvent;
import io.spiffy.common.manager.SQSManager;
import io.spiffy.common.util.JsonUtil;
import io.spiffy.common.util.ObfuscateUtil;

public class WebsiteSQSManager extends SQSManager<IngestEvent> {

    private static final String QUEUE_NAME = "spiffyio-website";

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";

    private final MediaClient mediaClient;
    private final StreamClient streamClient;

    @Inject
    public WebsiteSQSManager(final AmazonSQSClient client, final MediaClient mediaClient, final StreamClient streamClient) {
        super(client, IngestEvent.class, QUEUE_NAME);
        this.mediaClient = mediaClient;
        this.streamClient = streamClient;
    }

    @Override
    @Scheduled(fixedRate = 300000)
    public void poll() {
        super.poll();
    }

    public void process(final IngestEvent content, final String json) {
        if (IngestPostEvent.SUB_TYPE.equalsIgnoreCase(content.getSubType())) {
            final IngestPostEvent event = JsonUtil.deserialize(IngestPostEvent.class, json);
            processPost(event.getAccountId(), event.getAddress(), event.getDescription());
        } else if (IngestSourceEvent.SUB_TYPE.equalsIgnoreCase(content.getSubType())) {
            final IngestSourceEvent event = JsonUtil.deserialize(IngestSourceEvent.class, json);
            processSource(event.getAccountId(), event.getAddress());
        }
    }

    private Document getDocument(final String address) {
        try {
            return Jsoup.connect(address).userAgent(USER_AGENT).referrer("http://www.google.com").get();
        } catch (final Exception e) {
            return null;
        }
    }

    private void processSource(final long accountId, final String address) {
        final Document document = getDocument(address);
        if (document == null) {
            return;
        }

        if (StringUtils.containsIgnoreCase(address, "reddit.com")) {
            processRedditSource(accountId, document);
        } else if (StringUtils.containsIgnoreCase(address, "xkcd.com")) {
            processXKCDSource(accountId, document);
        }
    }

    private void processRedditSource(final long accountId, final Document document) {
        final Elements links = document.getElementsByClass("outbound");

        for (final Element link : links) {
            if (!link.hasClass("title")) {
                continue;
            }

            final String mediaAddress = link.attr("href");
            final String description = link.html();

            pushPost(accountId, mediaAddress, description);
        }

        final Element next = document.getElementsByAttributeValue("rel", "nofollow next").first();
        final String nextAddress = next.attr("href");

        pushSource(accountId, nextAddress);
    }

    private void processXKCDSource(final long accountId, final Document document) {
        final Element comic = document.getElementById("comic");
        final Element image = comic.getElementsByTag("img").first();

        final String mediaAddress = image.attr("src");
        final String description = image.attr("alt") + ":\r\n" + image.attr("title");

        pushPost(accountId, mediaAddress, description);

        final Element next = document.getElementsByAttributeValue("rel", "next").first();
        final String nextAddress = "http://xkcd.com" + next.attr("href");

        pushSource(accountId, nextAddress);
    }

    public void pushSource(final long accountId, final String address) {
        final IngestSourceEvent event = new IngestSourceEvent();
        event.setAccountId(accountId);
        event.setAddress(address);

        push(event);
    }

    private void pushPost(final long accountId, final String address, final String description) {
        final IngestPostEvent event = new IngestPostEvent();
        event.setAccountId(accountId);
        event.setAddress(address);
        event.setDescription(description);

        push(event);
    }

    private void processPost(final long accountId, final String address, final String description) {
        final String idempotentId = accountId + "-" + address;
        final Long media = getMedia(accountId, idempotentId, address, new HashSet<String>());

        if (media != null) {
            streamClient.postPost(idempotentId, accountId, media, description);
        }
    }

    private Long getMedia(final long accountId, final String idempotentId, final String address, final Set<String> stack) {
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
                final Document document = getDocument(address);
                final Element source = document.getElementsByTag("source").first();
                return getMedia(accountId, idempotentId, source.attr("src"), stack);
            } catch (final Exception e) {
                return null;
            }
        }

        final MediaType type = MediaType.getEnum(address.substring(address.lastIndexOf('.') + 1));
        if (type == null) {
            try {
                final String mediaAddress;
                final Document document = getDocument(address);
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

        final byte[] bytes;
        try {
            final URL url = new URL(address);
            try (final InputStream is = url.openStream()) {
                bytes = IOUtils.toByteArray(is);
            }
        } catch (final Exception e) {
            return null;
        }

        final String name = mediaClient.postMedia(accountId, idempotentId, type, bytes);
        if (name == null) {
            return null;
        }

        return ObfuscateUtil.unobfuscate(name);
    }
}