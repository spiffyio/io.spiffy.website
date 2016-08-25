package io.spiffy.common.util;

import java.io.*;
import java.nio.file.Files;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.log4j.Logger;

public class ExifUtil {

    private static final Logger logger = Logger.getLogger(ExifUtil.class);

    public static byte[] removeExif(final byte[] bytes) {
        final String name = UIDUtil.generateIdempotentId();

        File out = null;
        OutputStream os = null;
        try {
            out = File.createTempFile(name, ".jpg");
            if (out.exists()) {
                out.delete();
            }
            os = new FileOutputStream(out);
            os = new BufferedOutputStream(os);

            new ExifRewriter().removeExifMetadata(bytes, os);
            return Files.readAllBytes(out.toPath());
        } catch (final IOException | ImageReadException | ImageWriteException e) {
            logger.warn("unable to save out: " + name, e);
            return null;
        } finally {
            if (out != null && out.exists()) {
                out.delete();
            }
        }
    }
}