package io.spiffy.common.manager;

import io.spiffy.common.Manager;

import net.spy.memcached.MemcachedClient;

public class CacheManager extends Manager {

    private final MemcachedClient client;

    public CacheManager(final MemcachedClient client) {
        this.client = client;
    }

    public void put(final String key, final int timeout, final Object value) {
        client.set(key, timeout, value);
    }

    public Object get(final String key) {
        return client.get(key);
    }
}
