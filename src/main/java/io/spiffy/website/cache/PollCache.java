package io.spiffy.website.cache;

import javax.inject.Inject;

import io.spiffy.common.manager.CacheManager;

import net.spy.memcached.MemcachedClient;

public class PollCache extends CacheManager<Long, Poll> {

    private static final String PREFIX = "Poll:";
    private static final int TIMEOUT = 300;

    @Inject
    protected PollCache(final MemcachedClient client) {
        super(client, PREFIX, TIMEOUT);
    }
}
