package io.spiffy.common.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class UserAgentUtil {

    public static final String CHROME_BROWSER = "Chrome";
    public static final String CHROMIUM_BROWSER = "Chromium";
    public static final String EDGE_BROWSER = "Edge";
    public static final String FIREFOX_BROWSER = "Firefox";
    public static final String IE_BROWSER = "IE";
    public static final String OPERA_BROWSER = "Opera";
    public static final String SAFARI_BROWSER = "Safari";
    public static final String SEAMONKEY_BROWSER = "Seamonkey";
    public static final String UNKNOWN_BROWSER = "?";

    private static final Pattern CHROME_PATTERN = Pattern.compile("^(?=.*Chrome/)(?!.*Chromium/)(?!.*Edge/).*");
    private static final Pattern CHROMIUM_PATTERN = Pattern.compile("^(?=.*Chromium/).*");
    private static final Pattern FIREFOX_PATTERN = Pattern.compile("^(?=.*Firefox/)(?!.*Seamonkey/).*");
    private static final Pattern IE_PATTERN = Pattern.compile("^(?=.*MSIE).*");
    private static final Pattern EDGE_PATTERN = Pattern.compile("^(?=.*Edge).*");
    private static final Pattern OPERA_PATTERN = Pattern.compile("^(?=.*OPR/).*");
    private static final Pattern SAFARI_PATTERN = Pattern.compile("^(?=.*Safari/)(?!.*Chrom).*");
    private static final Pattern SEAMONKEY_PATTERN = Pattern.compile("^(?=.*Seamonkey/).*");

    private static final Pattern MOBILE_PATTERN = Pattern.compile("^(?=.*Mobi).*");
    private static final Pattern TABLET_PATTERN = Pattern.compile("^(?=.*Tablet).*");

    public static final String WINDOWS_OS = "Windows";
    public static final String MAC_OS = "Mac";
    public static final String ANDROID_OS = "Android";
    public static final String IOS_OS = "iOS";
    public static final String LINUX_OS = "Linux";
    public static final String UNKNOWN_OS = "?";

    private static final Pattern WINDOWS_PATTERN = Pattern.compile("^(?=.*Windows).*");
    private static final Pattern MAC_PATTERN = Pattern.compile("^(?=.*Mac)(?!.*iPad)(?!.*iPhone).*");
    private static final Pattern ANDROID_PATTERN = Pattern.compile("^(?=.*Android).*");
    private static final Pattern IOS_PATTERN = Pattern.compile("^(?=.*iPad|iPhone).*");
    private static final Pattern LINUX_PATTERN = Pattern.compile("^(?=.*Linux)(?!.*Android).*");

    public static String getBrowser(final String userAgent) {
        if (StringUtils.isEmpty(userAgent)) {
            return UNKNOWN_BROWSER;
        }

        if (CHROME_PATTERN.matcher(userAgent).matches()) {
            return CHROME_BROWSER;
        }

        if (CHROMIUM_PATTERN.matcher(userAgent).matches()) {
            return CHROMIUM_BROWSER;
        }

        if (EDGE_PATTERN.matcher(userAgent).matches()) {
            return EDGE_BROWSER;
        }

        if (FIREFOX_PATTERN.matcher(userAgent).matches()) {
            return FIREFOX_BROWSER;
        }

        if (IE_PATTERN.matcher(userAgent).matches()) {
            return IE_BROWSER;
        }

        if (OPERA_PATTERN.matcher(userAgent).matches()) {
            return OPERA_BROWSER;
        }

        if (SAFARI_PATTERN.matcher(userAgent).matches()) {
            return SAFARI_BROWSER;
        }

        if (SEAMONKEY_PATTERN.matcher(userAgent).matches()) {
            return SEAMONKEY_BROWSER;
        }

        return UNKNOWN_BROWSER;
    }

    public static boolean isMobile(final String userAgent) {
        if (StringUtils.isEmpty(userAgent)) {
            return false;
        }

        if (MOBILE_PATTERN.matcher(userAgent).matches()) {
            return true;
        }

        if (TABLET_PATTERN.matcher(userAgent).matches()) {
            return true;
        }

        return false;
    }

    public static String getOS(final String userAgent) {
        if (StringUtils.isEmpty(userAgent)) {
            return UNKNOWN_OS;
        }

        if (WINDOWS_PATTERN.matcher(userAgent).matches()) {
            return WINDOWS_OS;
        }

        if (MAC_PATTERN.matcher(userAgent).matches()) {
            return MAC_OS;
        }

        if (ANDROID_PATTERN.matcher(userAgent).matches()) {
            return ANDROID_OS;
        }

        if (IOS_PATTERN.matcher(userAgent).matches()) {
            return IOS_OS;
        }

        if (LINUX_PATTERN.matcher(userAgent).matches()) {
            return LINUX_OS;
        }

        return UNKNOWN_BROWSER;
    }
}
