package io.spiffy.email.manager;

import lombok.RequiredArgsConstructor;

import java.io.StringWriter;
import java.util.Date;

import javax.inject.Inject;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import io.spiffy.common.Manager;
import io.spiffy.common.api.email.dto.EmailProperties;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class EmailManager extends Manager {

    private final JavaMailSender sender;
    private final VelocityEngine velocity;

    public void send(final String template, final String subject, final String emailAddress, final Date sentAt,
            final EmailProperties properties) {
        final MimeMessagePreparator prepator = message -> {
            final MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(emailAddress);
            helper.setSubject(subject);

            final String text = getString("text/" + template, properties);
            final String html = getString("html/" + template, properties);

            helper.setText(text, html);

            helper.setFrom("SPIFFY.io <mail@spiffy.io>");
            helper.setReplyTo("NO REPLY <mail@spiffy.io>");
            helper.setSentDate(sentAt);
        };

        sender.send(prepator);
    }

    private String getString(final String name, final EmailProperties properties) {
        final VelocityContext context = new VelocityContext();
        context.put("property", properties);
        final StringWriter writer = new StringWriter();

        final Template template = velocity.getTemplate(name + ".vm", "UTF-8");
        template.merge(context, writer);

        return writer.toString();
    }
}
