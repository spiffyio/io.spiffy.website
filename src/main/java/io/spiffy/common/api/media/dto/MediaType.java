package io.spiffy.common.api.media.dto;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
public enum MediaType {
    GIF("image", "gif", 3), //
    JPG("image", "jpg", 1), //
    PNG("image", "png", 1), //
    MP4("video", "mp4", 2), //
    WEBM("video", "webm", 1);

    private final String type;
    private final String subtype;
    private final String contentType;

    @JsonIgnore
    private final int priority;

    private MediaType(final String type, final String subtype, final int priority) {
        this.type = type;
        this.subtype = subtype;
        contentType = type + "/" + subtype;
        this.priority = priority;
    }

    public static MediaType getEnum(final String value) {
        if (value == null) {
            return null;
        }

        switch (value.toLowerCase()) {
            case "gif":
            case "image/gif":
                return GIF;
            case "jpg":
            case "jpeg":
            case "image/jpg":
            case "image/jpeg":
                return JPG;
            case "png":
            case "image/png":
                return PNG;
            case "mpeg":
            case "mpeg4":
            case "mp4":
            case "video/mpeg":
            case "video/mpeg4":
            case "video/mp4":
                return MP4;
            case "webm":
            case "video/webm":
                return WEBM;
        }

        return null;
    }
}