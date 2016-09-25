package io.spiffy.common.util;

import lombok.Getter;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import io.spiffy.common.config.AppConfig;

public class ConverterUtil {

    private static final Logger logger = Logger.getLogger(ConverterUtil.class);

    @Getter
    private enum Type {
        MP4("mp4", MP4_TEMPLATE), WEBM("webm", WEBM_TEMPLATE), PNG("png", PNG_TEMPLATE);

        private final String extension;
        private final String template;

        private Type(final String extension, final String template) {
            this.extension = extension;
            this.template = template;
        }
    }

    private static final String MP4_TEMPLATE = AppConfig.getFfmpegPrefix()
            + "ffmpeg -i \"%s\" -c:v h264 -profile:v baseline -c:a aac -movflags faststart -preset slow -crf 24 -pix_fmt yuv420p \"%s\"";
    private static final String WEBM_TEMPLATE = AppConfig.getFfmpegPrefix()
            + "ffmpeg -i \"%s\" -c:v libvpx -c:a libvorbis -quality good -cpu-used 0 -crf 24 \"%s\"";
    private static final String PNG_TEMPLATE = AppConfig.getFfmpegPrefix()
            + "ffmpeg -i \"%s\" -ss 00:00:00.000 -vframes 1 \"%s\"";

    private static Boolean initialized = AppConfig.isFfmpegInitialized();

    private static void initialize() {
        if (initialized) {
            return;
        }

        synchronized (initialized) {
            final List<String> lines = CommandLineUtil.run("ls");
            for (final String line : lines) {
                if (StringUtils.containsIgnoreCase(line, "ffmpeg")) {
                    initialized = true;
                    return;
                }
            }

            CommandLineUtil.run("wget \"https://cdn.spiffy.io/static/ffmpeg/linux.zip\" -O \"ffmpeg.zip\"");
            CommandLineUtil.run("unzip ffmpeg.zip");
            CommandLineUtil.run("rm -f ffmpeg.zip");
            CommandLineUtil.run("chmod u+x ffprobe");
            CommandLineUtil.run("chmod u+x ffmpeg");
            initialized = true;
        }
    }

    public static byte[] convertToMP4(final byte[] bytes, final String name) {
        return convert(Type.MP4, bytes, name);
    }

    public static byte[] convertToWebM(final byte[] bytes, final String name) {
        return convert(Type.WEBM, bytes, name);
    }

    public static byte[] convertToPNG(final byte[] bytes, final String name) {
        return convert(Type.PNG, bytes, name);
    }

    private static byte[] convert(final Type type, final byte[] bytes, final String name) {
        initialize();

        File in = null;
        FileOutputStream fos = null;
        try {
            in = File.createTempFile(name, ".in");
            if (in.exists()) {
                in.delete();
            }
            fos = new FileOutputStream(in);
            fos.write(bytes);
        } catch (final IOException e) {
            logger.warn("unable to save in: " + name, e);
            return null;
        } finally {
            close(fos);
        }

        if (in.getAbsolutePath() == null) {
            logger.warn("unable to read in: " + name);
            return null;
        }

        File out = null;
        try {
            out = File.createTempFile(name, "." + type.getExtension());
            if (out.exists()) {
                out.delete();
            }
            CommandLineUtil.run(String.format(type.getTemplate(), in.getAbsolutePath(), out.getAbsolutePath()));
            return Files.readAllBytes(out.toPath());
        } catch (final IOException e) {
            logger.warn("unable to save out: " + name, e);
            return null;
        } finally {
            if (in != null && in.exists()) {
                in.delete();
            }
            if (out != null && out.exists()) {
                out.delete();
            }
        }
    }

    private static void close(final Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (final IOException e) {
                logger.warn("unable to close closeable", e);
            }
        }
    }
}