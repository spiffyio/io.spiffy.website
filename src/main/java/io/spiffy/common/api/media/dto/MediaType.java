package io.spiffy.common.api.media.dto;

import lombok.Getter;

@Getter
public enum MediaType {
    GIF("image", "gif"), //
    JPG("image", "jpg"), //
    PNG("image", "png"), //
    MOV("video", "mov"), //
    MP4("video", "mp4"), //
    WEBM("video", "webm");

    private final String type;
    private final String subtype;
    private final String contentType;

    private MediaType(final String type, final String subtype) {
        this.type = type;
        this.subtype = subtype;
        contentType = type + "/" + subtype;
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
            case "mov":
            case "video/mov":
                return MOV;
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