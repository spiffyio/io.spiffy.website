package io.spiffy.common.api.media.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Content {
    private ContentType type;

    private String name;

    private String thumbnail;

    private String file;
    private String medium;

    private String poster;
    private String mp4;
    private String webm;
    private String gif;

    public Content() {
    }

    public Content(final String name, final String file, final String medium, final String thumbnail) {
        type = ContentType.IMAGE;
        this.name = name;
        this.file = file;
        this.medium = medium;
        this.thumbnail = thumbnail;
    }

    public Content(final String name, final String poster, final String mp4, final String webm, final String gif,
            final String thumbnail) {
        type = ContentType.VIDEO;
        this.name = name;
        this.poster = poster;
        this.mp4 = mp4;
        this.webm = webm;
        this.gif = gif;
        this.thumbnail = thumbnail;
    }
}
