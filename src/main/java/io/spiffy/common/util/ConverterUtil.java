package io.spiffy.common.util;

import lombok.Getter;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
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

    private static final String MP4_TEMPLATE = "ffmpeg -i \"%s\" -c:v h264 -profile:v baseline -c:a aac -movflags faststart -preset slow -crf 24 -pix_fmt yuv420p \"%s\"";
    private static final String WEBM_TEMPLATE = "ffmpeg -i \"%s\" -c:v libvpx -c:a libvorbis -quality good -cpu-used 0 -crf 24 \"%s\"";
    private static final String PNG_TEMPLATE = "ffmpeg -i \"%s\" -ss 00:00:00.000 -vframes 1 \"%s\"";

    private static Boolean initialized = true;

    private static void initialize() {
        if (initialized) {
            return;
        }

        synchronized (initialized) {
            final List<String> lines = run("ls");
            for (final String line : lines) {
                if (StringUtils.containsIgnoreCase(line, "ffmpeg")) {
                    initialized = true;
                    return;
                }
            }

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
            logger.warn("unable to save in: " + name);
            return null;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (final IOException e) {
                }
            }
        }

        if (in.getAbsolutePath() == null) {
            return null;
        }

        File out = null;
        try {
            out = File.createTempFile(name, "." + type.getExtension());
            if (out.exists()) {
                out.delete();
            }
            run(String.format(type.getTemplate(), in.getAbsolutePath(), out.getAbsolutePath()));
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

    private static final ProcessBuilder getProcessBuilder(final String command) {
        if ("WINDOWS".equalsIgnoreCase(AppConfig.getShell())) {
            return new ProcessBuilder("cmd.exe", "/c", command);
        }

        if ("MAC".equalsIgnoreCase(AppConfig.getShell())) {
            return new ProcessBuilder("/bin/bash", "-c", "/usr/local/bin/" + command);
        }

        return new ProcessBuilder("/bin/bash", "-c", "./" + command); // AMAZON LINUX
    }

    public static List<String> run(final String command) {
        final List<String> output = new ArrayList<>();

        Process process = null;
        BufferedReader reader = null;
        try {
            final ProcessBuilder pb = getProcessBuilder(command);
            pb.redirectErrorStream(true);

            process = pb.start();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.add(line);
            }

        } catch (final IOException e) {
        } finally {
            if (process != null) {
                process.destroy();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                }
            }
        }

        return output;
    }
}