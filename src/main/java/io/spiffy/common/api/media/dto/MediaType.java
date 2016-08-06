package io.spiffy.common.api.media.dto;

public enum MediaType {
    GIF, JPG, PNG;

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
        }

        return null;
    }
}