package io.spiffy.common.manager;

import java.util.concurrent.Callable;

import io.spiffy.common.Manager;
import io.spiffy.common.util.ThreadUtil;

import net.spy.memcached.MemcachedClient;

public abstract class CacheManager<Key, Value> extends Manager {

    private final MemcachedClient client;
    private final String prefix;
    private final int timeout;

    protected CacheManager(final MemcachedClient client, final String prefix, final int timeout) {
        this.client = client;
        this.prefix = prefix;
        this.timeout = timeout;
    }

    public Value put(final Key key, final Value value) {
        return put(key, () -> value);
    }

    public Value put(final Key key, final Callable<Value> callable) {
        final Value value;
        try {
            value = callable.call();
        } catch (final Exception e) {
            logger.warn("unable to call callable...", e);
            return null;
        }

        if (value == null) {
            return value;
        }

        ThreadUtil.run(() -> client.set(prefix + key, timeout, value));

        return value;
    }

    @SuppressWarnings("unchecked")
    public Value get(final Key key) {
        final String keyString = prefix + key;
        ThreadUtil.run(() -> client.touch(keyString, timeout));

        return (Value) client.get(keyString);
    }

    public Value get(final Key key, final Value value) {
        return get(key, () -> value);
    }

    public Value get(final Key key, final Callable<Value> callable) {
        final Value cached = get(key);
        if (cached != null) {
            return cached;
        }

        final Value value;
        try {
            value = callable.call();
        } catch (final Exception e) {
            logger.warn("unable to call callable...", e);
            return null;
        }

        if (value == null) {
            return null;
        }

        put(key, value);
        return value;
    }
}
