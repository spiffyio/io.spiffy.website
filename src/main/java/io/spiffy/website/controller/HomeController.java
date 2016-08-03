package io.spiffy.website.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.spiffy.common.Controller;
import io.spiffy.common.api.email.client.EmailClient;
import io.spiffy.common.api.media.client.MediaClient;
import io.spiffy.common.api.media.dto.MediaType;
import io.spiffy.common.api.security.client.SecurityClient;
import io.spiffy.common.api.source.client.SourceClient;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.common.dto.Context;
import io.spiffy.email.manager.EmailManager;
import io.spiffy.user.service.CredentialService;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HomeController extends Controller {

    private final EmailClient emailClient;
    private final MediaClient mediaClient;
    private final SecurityClient securityClient;
    private final SourceClient sourceClient;
    private final UserClient userClient;

    private final EmailManager emailManager;
    private final CredentialService credentialService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(final Context context) throws IOException {
        // System.out.println(emailClient.postEmailAddress("me@spiffy.io"));
        // System.out.println(securityClient.encryptString("john@spiffy.io"));
        // System.out.println(securityClient.decryptString(1000001L));
        // System.out.println(userClient.postAccount("john", "me@spiffy.io"));

        // emailManager.send("john <john@spiffy.io>");
        // emailClient.sendEmailCall(EmailType.Verify, "john@spiffy.io", "registration", 1L, new
        // EmailProperties());

        // userClient.registerAccount("john", "john@spiffy.io", "password");
        // credentialService.post(1000000L, "password");

        final XKCDPost post = getXKCDPost("303");
        final URL url = new URL(post.getImage());
        InputStream is = null;
        try {
            is = url.openStream();
            final byte[] imageBytes = IOUtils.toByteArray(is);

            final String idempotentId = "url:" + sourceClient.postUrl(post.getImage(), "imgs.xkcd.com", "303", "xkcd");

            mediaClient.postMedia(idempotentId, MediaType.PNG, imageBytes);
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }

        context.addAttribute("csrf", context.generateCsrfToken("home"));
        return home(context.getRequest().getLocale(), context.getModel());
    }

    protected String home(final Locale locale, final ModelMap model) {
        logger.info(String.format("Welcome home! The client locale is %s.", locale));

        final Date date = new Date();
        final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        final String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate);

        return "home";
    }

    private XKCDPost getXKCDPost(final String id) {
        try {
            final Document document = Jsoup.connect("http://xkcd.com/" + id).get();
            final Element comic = document.getElementById("comic");
            final Element image = comic.getElementsByTag("img").first();

            return new XKCDPost(id, image.attr("alt"), "https:" + image.attr("src"), image.attr("title"));
        } catch (final Exception e) {
            return null;
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
}
