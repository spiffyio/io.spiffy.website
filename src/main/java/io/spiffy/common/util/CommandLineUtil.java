package io.spiffy.common.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import io.spiffy.common.config.AppConfig;

public class CommandLineUtil {

    private static final Logger logger = Logger.getLogger(CommandLineUtil.class);

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
            logger.warn("unable to run command: " + command, e);
        } finally {
            close(reader);
            if (process != null) {
                process.destroy();
            }
        }

        return output;
    }

    private static final ProcessBuilder getProcessBuilder(final String command) {
        if ("WINDOWS".equalsIgnoreCase(AppConfig.getShell())) {
            return new ProcessBuilder("cmd.exe", "/c", command);
        }

        if ("MAC".equalsIgnoreCase(AppConfig.getShell())) {
            return new ProcessBuilder("/bin/bash", "-c", "/usr/local/bin/" + command);
        }

        return new ProcessBuilder("/bin/bash", "-c", command); // AMAZON LINUX
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
