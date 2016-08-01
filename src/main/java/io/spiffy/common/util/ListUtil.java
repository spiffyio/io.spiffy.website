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

    public static <T> List<T> subList(final List<T> list, final int limit) {
        return subList(list, limit, 0);
    }

    public static <T> List<T> subList(final List<T> list, final int limit, final int offset) {
        if (list == null) {
            return list;
        }

        if (list.size() < offset) {
            return new ArrayList<T>();
        }

        final int fromIndex = offset;
        final int toIndex = list.size() < limit + offset ? list.size() : limit + offset;

        return list.subList(fromIndex, toIndex);
    }

}
