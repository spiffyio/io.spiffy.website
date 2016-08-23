package io.spiffy.common.api.media.dto;

public enum MediaType {
    GIF, JPG, PNG, MP4, WEBM;

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