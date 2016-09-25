package io.spiffy.common.config;

import lombok.Getter;

import io.spiffy.common.util.CommandLineUtil;

public class HostConfig {

    @Getter
    private static final String privateEndpoint;

    static {
        privateEndpoint = CommandLineUtil.run("curl -L http://instance-data/latest/meta-data/local-ipv4 -s").get(0);
    }
}
