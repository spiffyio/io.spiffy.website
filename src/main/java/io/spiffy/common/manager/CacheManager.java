package io.spiffy.common.manager;

import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import io.spiffy.common.Manager;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.util.ThreadUtil;
import io.spiffy.website.cache.Listener;

import net.spy.memcached.MemcachedClient;

public abstract class CacheManager<Key, Value> extends Manager {

    private static final String LISTENER_PREFIX = "Listener:";
    private static final int DEFAULT_HASH_CODE = 0;

    private final MemcachedClient client;
    private final Cache<Key, Value> cache;
    private final String prefix;
    private final int timeout;

    protected CacheManager(final MemcachedClient client, final String prefix, final int timeout) {
        this.client = client;
        this.prefix = prefix;
        this.timeout = timeout;
        this.cache = CacheBuilder //
                .newBuilder() //
                .expireAfterAccess(timeout, TimeUnit.SECONDS) //
                .build();
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

        ThreadUtil.run(() -> {
            notifyListeners(key, value.hashCode());
            cache.put(key, value);
            client.set(prefix + key, timeout, value);
        });

        return value;
    }

    @SuppressWarnings("unchecked")
    public Value get(final Key key) {
        final String keyString = prefix + key;
        ThreadUtil.run(() -> client.touch(keyString, timeout));

        final Value local = cache.getIfPresent(key);
        if (local != null) {
            return local;
        }

        final Value distributed = (Value) client.get(keyString);
        if (distributed != null) {
            put(key, distributed);
        }

        return distributed;
    }

    public Value get(final Key key, final Value value) {
        return get(key, () -> value);
    }

    public Value get(final Key key, final Callable<Value> callable) {
        return get(key, callable, DEFAULT_HASH_CODE);
    }

    public Value get(final Key key, final Callable<Value> callable, final int hashCode) {
        final Value cached = get(key);
        if (cached != null && cached.hashCode() != hashCode) {
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

        if (value.hashCode() == hashCode) {
            registerListener(key, value.hashCode());

            synchronized (value) {
                try {
                    value.wait(timeout * 1000);
                } catch (final InterruptedException e) {
                    logger.warn("unable to wait...", e);
                    return value;
                }
            }

            return get(key);
        }

        return value;
    }

    private void notifyListeners(final Key key, final int hashCode) {
        final String listenerKey = LISTENER_PREFIX + prefix + key;

        final Listener listener = (Listener) client.get(listenerKey);
        if (listener == null) {
            return;
        }

        if (listener.getHosts() == null) {
            return;
        }

        for (final Entry<String, Integer> host : listener.getHosts().entrySet()) {
            if (host.getValue() != hashCode) {
                notifyHost(host.getKey(), key);
            }
        }
    }

    private void notifyHost(final String host, final Key key) {
        if (AppConfig.getInstanceId().equals(host)) {
            final Value value = cache.getIfPresent(key);
            if (value != null) {
                synchronized (value) {
                    value.notifyAll();
                }
            }
        }
    }

    private void registerListener(final Key key, final int hashCode) {
        final String listenerKey = LISTENER_PREFIX + prefix + key;

        Listener listener = (Listener) client.get(listenerKey);
        if (listener == null) {
            listener = new Listener();
        }

        listener.addHost(AppConfig.getInstanceId(), hashCode);
        client.add(listenerKey, timeout, listener);
    }
}
