package io.spiffy.common.manager;

import java.io.Serializable;

import io.spiffy.common.Manager;

import net.spy.memcached.MemcachedClient;

public abstract class CacheManager<Key, Value extends Serializable> extends Manager {

    private final MemcachedClient client;
    private final int timeout;

    protected CacheManager(final MemcachedClient client, final int timeout) {
        this.client = client;
        this.timeout = timeout;
    }

    public void put(final Key key, final Value value) {
        client.set(key.toString(), timeout, value);
    }

    @SuppressWarnings("unchecked")
    public Value get(final Key key) {
        return (Value) client.get(key.toString());
    }

    public Value get(final Key key, final Value value) {
        final Value cached = get(key);
        if (cached != null) {
            return cached;
        }
        return value;
    }
}
