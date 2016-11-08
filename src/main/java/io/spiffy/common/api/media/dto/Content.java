package io.spiffy.common.api.media.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@AllArgsConstructor
public class Content {
    private ContentType type;

    private String name;

    private String file;
    private String medium;
    private String thumbnail;

    private Content poster;
    private String mp4;
    private String webm;
    private String gif;

    @JsonIgnore
    public String getImage() {
        if (ContentType.IMAGE.equals(type)) {
            return file;
        }

        if (ContentType.VIDEO.equals(type)) {
            return poster.getFile();
        }

        return null;
    }

    public Content() {
    }

    public Content(final String name, final String file, final String medium, final String thumbnail) {
        type = ContentType.IMAGE;
        this.name = name;
        this.file = file;
        this.medium = medium;
        this.thumbnail = thumbnail;
    }

    public Content(final String name, final Content poster, final String mp4, final String webm, final String gif) {
        type = ContentType.VIDEO;
        this.name = name;
        this.poster = poster;
        this.mp4 = mp4;
        this.webm = webm;
        this.gif = gif;
    }
}
