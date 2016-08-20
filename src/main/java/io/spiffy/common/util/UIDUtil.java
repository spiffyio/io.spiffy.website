package io.spiffy.common.util;

import java.util.UUID;

public class UIDUtil {

    public static String generateIdempotentId() {
        return UUID.randomUUID().toString() + "-" + ObfuscateUtil.obfuscate(DateUtil.now().getTime());
    }
}
