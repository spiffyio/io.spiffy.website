package io.spiffy.website.tag;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import io.spiffy.common.dto.Context;
import io.spiffy.common.util.DurationUtil;

public class DurationTag extends SimpleTagSupport {

    private Date date;

    @Override
    public void doTag() throws JspException, IOException {
        final PageContext pageContext = (PageContext) getJspContext();
        final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        final HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        final Context context = new Context(request, response);

        getJspContext().getOut().print(DurationUtil.pretty(date));
    }

    public void setDate(final Date date) {
        this.date = date;
    }
}
