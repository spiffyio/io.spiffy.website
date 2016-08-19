package io.spiffy.website.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import io.spiffy.common.util.ObfuscateUtil;

public class ObfuscateTag extends SimpleTagSupport {

    private Long id;

    @Override
    public void doTag() throws JspException, IOException {
        getJspContext().getOut().print(ObfuscateUtil.obfuscate(id));
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
