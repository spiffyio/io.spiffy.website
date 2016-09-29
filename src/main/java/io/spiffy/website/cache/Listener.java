package io.spiffy.website.cache;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Listener implements Serializable {
    private static final long serialVersionUID = 7397770425072264118L;

    private Map<String, Integer> hosts;

    public void addHost(final String host, final int hashCode) {
        if (hosts == null) {
            hosts = new HashMap<>();
        }
        synchronized (hosts) {
            hosts.put(host, hashCode);
        }
    }
}