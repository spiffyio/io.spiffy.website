package io.spiffy.common.manager;

import io.spiffy.common.api.input.APIInput;
import io.spiffy.common.api.output.APIOutput;

import net.spy.memcached.MemcachedClient;

public abstract class APICacheManager<Key extends APIInput, Value extends APIOutput> extends CacheManager<Key, Value> {

    private static final String PREFIX = "api-";
    private static final int TIMEOUT = 60 * 5;

    public APICacheManager(final MemcachedClient client, final String prefix) {
        super(client, PREFIX + prefix + ":", TIMEOUT);
    }
}
