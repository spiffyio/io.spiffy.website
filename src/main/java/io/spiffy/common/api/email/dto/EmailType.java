package io.spiffy.common.api.email.dto;

import lombok.Getter;

@Getter
public enum EmailType {
    RECOVER("recovery", "Recover Account"), //
    VERIFICATION("verify", "Verify Email Address");

    private String template;
    private String subject;

    private EmailType(final String template, final String subject) {
        this.template = template;
        this.subject = subject;
    }
}
