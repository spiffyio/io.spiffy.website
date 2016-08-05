package io.spiffy.website.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import io.spiffy.common.config.AppConfig;

public class ResourceTag extends SimpleTagSupport {

    private static final String CDN_ENDPOINT = AppConfig.getCdnEndpoint() + "/static/";

    private String file;

    @Override
    public void doTag() throws JspException, IOException {
        getJspContext().getOut().print(CDN_ENDPOINT + file);
    }

    public void setFile(final String file) {
        this.file = file;
    }
}
