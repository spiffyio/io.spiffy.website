package io.spiffy.common.mock;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class JavaMailSenderMock extends JavaMailSenderImpl {

    @Override
    public void send(final SimpleMailMessage ... simpleMessages) throws MailException {

    }

    @Override
    public void send(final MimeMessage ... mimeMessages) throws MailException {

    }

    @Override
    public void send(final MimeMessagePreparator ... mimeMessagePreparators) throws MailException {

    }
}