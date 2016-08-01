package io.spiffy.email.manager;

import lombok.RequiredArgsConstructor;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import io.spiffy.common.Manager;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class EmailManager extends Manager {

    private final MailSender sender;

    public void send(final String emailAddress) {
        final SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("mail@spiffy.io");
        message.setTo(emailAddress);
        message.setSentDate(new Date());
        message.setSubject("foo");
        message.setText("bar");

        sender.send(message);
    }
}
