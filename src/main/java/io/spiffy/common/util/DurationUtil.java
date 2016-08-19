package io.spiffy.common.util;

import java.util.Date;
import java.util.regex.Pattern;

import org.ocpsoft.prettytime.PrettyTime;

public class DurationUtil {

    private static final PrettyTime pretty = new PrettyTime();

    private static final Pattern MOMENTS_PATTERN = Pattern.compile("^(?=.*moments).*");
    private static final String NOW = "now";

    public static String pretty(final Date date) {
        final String duration = pretty.format(date);
        if (MOMENTS_PATTERN.matcher(duration).matches()) {
            return NOW;
        }

        return pretty.format(date);
    }
}
