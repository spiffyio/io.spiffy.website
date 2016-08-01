package io.spiffy.email.manager;

import lombok.RequiredArgsConstructor;

import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import io.spiffy.common.Manager;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class EmailManager extends Manager {

    private final JavaMailSender sender;
    private final VelocityEngine velocity;

    public void send(final String emailAddress) {
        final MimeMessagePreparator prepator = message -> {
            final MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(emailAddress);
            helper.setSubject("foo");

            final Map<String, Object> properties = new HashMap<>();

            final String text = getString("text/" + "template", properties);
            final String html = getString("html/" + "template", properties);

            helper.setText(text, html);

            helper.setFrom("SPIFFY.io <mail@spiffy.io>");
            helper.setReplyTo("NO REPLY <mail@spiffy.io>");
            helper.setSentDate(new Date());
        };

        sender.send(prepator);
    }

    private String getString(final String name, final Map<String, Object> properties) {
        final VelocityContext context = new VelocityContext(properties);
        final StringWriter writer = new StringWriter();

        final Template template = velocity.getTemplate(name + ".vm", "UTF-8");
        template.merge(context, writer);

        return writer.toString();
    }
}
