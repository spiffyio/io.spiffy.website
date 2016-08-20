package io.spiffy.website.tag;

import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import io.spiffy.common.util.DurationUtil;

public class DurationTag extends SimpleTagSupport {

    private Date date;

    @Override
    public void doTag() throws JspException, IOException {
        getJspContext().getOut().print(DurationUtil.pretty(date));
    }

    public void setDate(final Date date) {
        this.date = date;
    }
}
