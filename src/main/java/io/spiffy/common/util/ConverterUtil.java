package io.spiffy.common.util;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import io.spiffy.common.config.AppConfig;

public class ConverterUtil {

    private static final Logger logger = Logger.getLogger(ConverterUtil.class);

    private ConverterUtil() {
    }

    private static final String FLV_TEMPLATE = "ffmpeg -i %s.mp4 -c:v libx264 -ar 22050 -crf 28 -s 640x360 \"%s.flv\"";
    private static final String WEBM_TEMPLATE = "ffmpeg -i \"%s\" -c:v libvpx -crf 10 -b:v 1M -c:a libvorbis \"%s\"";

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

            run("wget \"https:" + AppConfig.getCdnEndpoint() + "/static/zip/ffmpeg.zip\" -O \"ffmpeg.zip\"");
            run("unzip \"ffmpeg.zip\"");
            run("rm -f \"ffmpeg.zip\"");
            initialized = true;
        }
    }

    public static boolean convertToFLV(final String mp4) {
        initialize();

        run(String.format(FLV_TEMPLATE, mp4, mp4));
        return true;
    }

    public static byte[] convertToWebM(final byte[] bytes, final String name) {
        initialize();

        File mp4File = null;
        FileOutputStream fos = null;
        try {
            mp4File = File.createTempFile(name, ".mp4");
            if (mp4File.exists()) {
                mp4File.delete();
            }
            fos = new FileOutputStream(mp4File);
            fos.write(bytes);
        } catch (final IOException e) {
            logger.warn("unable to save mp4: " + name);
            return null;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (final IOException e) {
                }
            }
        }

        if (mp4File.getAbsolutePath() == null) {
            return null;
        }

        File webmFile = null;
        try {
            webmFile = File.createTempFile(name, ".webm");
            if (webmFile.exists()) {
                webmFile.delete();
            }
            run(String.format(WEBM_TEMPLATE, mp4File.getAbsolutePath(), webmFile.getAbsolutePath()));
            return Files.readAllBytes(webmFile.toPath());
        } catch (final IOException e) {
            logger.warn("unable to save webm: " + name);
            return null;
        } finally {
            if (mp4File != null && mp4File.exists()) {
                mp4File.delete();
            }
            if (webmFile != null && webmFile.exists()) {
                webmFile.delete();
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
                System.out.println(line);
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