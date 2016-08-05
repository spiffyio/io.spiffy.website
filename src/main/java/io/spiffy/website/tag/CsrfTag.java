package io.spiffy.website.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import io.spiffy.common.dto.Context;

public class CsrfTag extends SimpleTagSupport {

    public enum Format {
        data("data-csrf-token=\"%s\""), input("<input type=\"hidden\" name=\"csrf\" value=\"%s\">"), plain("%s");

        private final String template;

        private Format(final String template) {
            this.template = template;
        }

        public String format(final Object ... args) {
            return String.format(template, args);
        }
    }

    private String name;
    private Format format = Format.data;

    @Override
    public void doTag() throws JspException, IOException {
        final PageContext pageContext = (PageContext) getJspContext();
        final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        final HttpServletResponse response = (HttpServletResponse) pageContext.getRequest();
        final Context context = new Context(request, response);

        getJspContext().getOut().print(format.format(context.generateCsrfToken(name)));
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setFormat(final Format format) {
        this.format = format;
    }
}
