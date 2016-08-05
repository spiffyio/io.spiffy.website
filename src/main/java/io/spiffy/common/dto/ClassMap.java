package io.spiffy.common.dto;

import java.util.HashMap;
import java.util.Set;

public class ClassMap extends HashMap<Class<? extends Object>, Object> {

    private static final long serialVersionUID = -5053785215798506881L;

    public ClassMap(final Class<?>[] classes, final Object ... args) {
        if (args == null || args.length == 0) {
            return;
        }

        for (final Object arg : args) {
            if (arg == null) {
                continue;
            }
            put(arg.getClass(), arg);
        }

        if (classes != null) {
            for (final Class<?> clazz : classes) {
                if (containsKey(clazz)) {
                    continue;
                }
                put(clazz, null);
            }
        }
    }

    public ClassMap(final Object ... args) {
        this(null, args);
    }

    public <T> boolean containsKey(final Class<T> key) {
        if (super.containsKey(key)) {
            return true;
        }

        final Set<Class<? extends Object>> keys = keySet();
        for (final Class<? extends Object> aKey : keys) {
            if (key.isAssignableFrom(aKey)) {
                return true;
            }
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(final Class<T> key) {
        if (super.containsKey(key)) {
            return (T) super.get(key);
        }

        final Set<Class<? extends Object>> keys = keySet();
        for (final Class<? extends Object> aKey : keys) {
            if (key.isAssignableFrom(aKey)) {
                return (T) super.get(aKey);
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T remove(final Class<T> key) {
        if (super.containsKey(key)) {
            return (T) super.remove(key);
        }

        final Set<Class<? extends Object>> keys = keySet();
        for (final Class<? extends Object> aKey : keys) {
            if (key.isAssignableFrom(aKey)) {
                return (T) super.remove(aKey);
            }
        }

        return null;
    }
}
