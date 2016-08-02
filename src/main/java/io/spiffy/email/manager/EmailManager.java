package io.spiffy.email.manager;

import lombok.RequiredArgsConstructor;

import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import io.spiffy.common.Manager;
import io.spiffy.common.config.AppConfig;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class EmailManager extends Manager {

    private final JavaMailSender sender;
    private final VelocityEngine velocity;

    public void send(final String emailAddress) {
        final MimeMessagePreparator prepator = message -> {
            final MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(emailAddress);
            helper.setSubject("foo");

            final Map<String, Object> properties = new HashMap<>();
            properties.put("name", "john");
            properties.put("endpoint", AppConfig.getEndpoint());
            properties.put("token", UUID.randomUUID().toString());

            final String text = getString("text/" + "verify", properties);
            final String html = getString("html/" + "verify", properties);

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
