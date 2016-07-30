package io.spiffy.common.config;

import lombok.Getter;

import java.security.InvalidParameterException;

public class AppConfig {

    private static final String LOCAL = "local";
    private static final String BETA = "beta";
    private static final String PROD = "prod";

    @Getter
    private static final String stage;

    @Getter
    private static final String endpoint;

    @Getter
    private static final String suffix;

    static {
        stage = System.getProperty("stage");

        if (LOCAL.equalsIgnoreCase(stage)) {
            endpoint = "http://localhost:1280";
            suffix = "-beta";
        } else if (BETA.equalsIgnoreCase(stage)) {
            endpoint = "https://beta.spiffy.io";
            suffix = "-beta";
        } else if (PROD.equalsIgnoreCase(stage)) {
            endpoint = "https://spiffy.io";
            suffix = "";
        } else {
            throw new InvalidParameterException(String.format("unknown AppStage: %s", stage));
        }
    }

}
