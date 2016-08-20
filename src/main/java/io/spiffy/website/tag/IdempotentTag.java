package io.spiffy.website.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import io.spiffy.common.util.UIDUtil;

public class IdempotentTag extends SimpleTagSupport {

    public enum Format {
        data("data-idempotent-id=\"%s\""), input("<input type=\"hidden\" name=\"idempotentId\" value=\"%s\">"), plain("%s");

        private final String template;

        private Format(final String template) {
            this.template = template;
        }

        public String format(final Object ... args) {
            return String.format(template, args);
        }
    }

    private Format format = Format.input;

    @Override
    public void doTag() throws JspException, IOException {
        getJspContext().getOut().print(format.format(UIDUtil.generateIdempotentId()));
    }

    public void setFormat(final Format format) {
        this.format = format;
    }
}
