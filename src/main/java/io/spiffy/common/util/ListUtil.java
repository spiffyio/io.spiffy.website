package io.spiffy.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUtil {

    @SuppressWarnings("unchecked")
    public static <T> List<T> asList(final T ... args) {
        if (args == null) {
            return new ArrayList<T>();
        }
        return Arrays.asList(args);
    }

}
