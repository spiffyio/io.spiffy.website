package io.spiffy.website.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.spiffy.common.Controller;
import io.spiffy.common.api.email.client.EmailClient;
import io.spiffy.common.api.media.client.MediaClient;
import io.spiffy.common.api.media.dto.MediaType;
import io.spiffy.common.api.security.client.SecurityClient;
import io.spiffy.common.api.source.client.SourceClient;
import io.spiffy.common.api.stream.client.StreamClient;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.common.dto.Context;
import io.spiffy.email.manager.EmailManager;
import io.spiffy.user.service.CredentialService;
import io.spiffy.website.annotation.Csrf;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HomeController extends Controller {

    private final EmailClient emailClient;
    private final MediaClient mediaClient;
    private final SecurityClient securityClient;
    private final SourceClient sourceClient;
    private final StreamClient streamClient;
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

        // System.out.println(streamClient.getPosts(1000097L, 6));

        context.addAttribute("csrf", context.generateCsrfToken("home"));
        return home(context.getRequest().getLocale(), context.getModel());
    }

    @RequestMapping(value = "/xkcd", method = RequestMethod.GET)
    public String xkcd(final Context context) throws IOException {
        // final long accountId = userClient.registerAccount("xkcd", "xkcd@spiffy.io",
        // "password");
        // for (int i = 1; i < 100; i++) {
        // post(accountId, "" + i);
        // }

        context.addAttribute("csrf", context.generateCsrfToken("home"));
        return home(context.getRequest().getLocale(), context.getModel());
    }

    @Csrf("sign-up")
    @ResponseBody
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(final Context context, final @RequestParam String username, final @RequestParam String email,
            final @RequestParam String password) {
        userClient.registerAccount(username, email, password);
        return "{\"success\":true}";
    }

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(final Context context, @RequestParam(value = "file") final MultipartFile file) throws IOException {

        final long accountId = userClient.registerAccount("john", "john@spiffy.io", "password");
        final long mediaId = mediaClient.postMedia("test0001", MediaType.JPG, file.getBytes());
        streamClient.postPost("test0001", accountId, mediaId, "title", "description");

        return "{\"success\":true}";
    }

    protected String home(final Locale locale, final ModelMap model) {
        logger.info(String.format("Welcome home! The client locale is %s.", locale));

        final Date date = new Date();
        final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        final String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate);

        return "home";
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
