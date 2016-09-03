package io.spiffy.common.util;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    public static LocalDateTime nowUTC() {
        return LocalDateTime.now(Clock.systemUTC());
    }

    private static Date convert(final LocalDateTime date) {
        return Date.from(date.atZone(ZoneId.of("UTC")).toInstant());
    }

    public static Date now() {
        return convert(nowUTC());
    }

    public static Date now(final long minutes) {
        return convert(nowUTC().plusMinutes(minutes));
    }

    public static boolean past(final Date date) {
        if (date == null) {
            return true;
        }
        return now().after(date);
    }

    public static long timeDifference(final Date date) {
        return timeDifference(date, now());
    }

    public static long timeDifference(final Date a, final Date b) {
        if (a == null && b == null) {
            return 0L;
        }

        if (a == null) {
            return 0 - b.getTime();
        }

        if (b == null) {
            return a.getTime();
        }

        return a.getTime() - b.getTime();
    }

    public static long seconds(final int seconds) {
        return 1000 * seconds;
    }

    public static long minutes(final int minutes) {
        return seconds(60 * minutes);
    }

    public static long hours(final int hours) {
        return minutes(60 * hours);
    }

    public static long days(final int days) {
        return hours(24 * days);
    }
}
