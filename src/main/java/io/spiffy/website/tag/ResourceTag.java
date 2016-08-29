package io.spiffy.website.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import io.spiffy.common.config.AppConfig;

public class ResourceTag extends SimpleTagSupport {

    public enum Type {
        css("css", "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\">"), //
        js("js", "<script type=\"text/javascript\" src=\"%s\"></script>"), plain("", "%s");

        private final String extension;
        private final String template;

        private Type(final String extension, final String template) {
            this.extension = extension;
            this.template = template;
        }

        public String toString(final String file, final String version) {
            final StringBuilder builder = new StringBuilder();
            builder.append(RESOURCE_ENDPOINT);

            if (this != Type.plain) {
                builder.append(extension + "/");
            }

            builder.append(file);

            if (this != Type.plain) {
                builder.append(".min." + extension);
            }

            if (version != null) {
                builder.append("?v=" + version);
            }

            return String.format(template, builder.toString());
        }
    }

    private static final String RESOURCE_ENDPOINT = AppConfig.getResourceEndpoint();

    private String file;
    private Type type = Type.plain;
    private String version = "33";

    @Override
    public void doTag() throws JspException, IOException {
        getJspContext().getOut().print(type.toString(file, version));
    }

    public void setFile(final String file) {
        this.file = file;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    public void setVersion(final String version) {
        this.version = version;
    }
}
