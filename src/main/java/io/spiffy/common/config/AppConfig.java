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
    private static final String awsAccessKeyId;

    @Getter
    private static final String awsSecretKey;

    @Getter
    private static final String endpoint;

    @Getter
    private static final String restEndpoint;

    @Getter
    private static final String suffix;

    static {
        stage = System.getProperty("stage");
        awsAccessKeyId = System.getProperty("AWS_ACCESS_KEY_ID");
        awsSecretKey = System.getProperty("AWS_SECRET_KEY");

        if (LOCAL.equalsIgnoreCase(stage)) {
            endpoint = "http://localhost:1280";
            restEndpoint = endpoint + "/api/";
            suffix = "-beta";
        } else if (BETA.equalsIgnoreCase(stage)) {
            endpoint = "https://beta.spiffy.io";
            restEndpoint = endpoint + "/api/";
            suffix = "-beta";
        } else if (PROD.equalsIgnoreCase(stage)) {
            endpoint = "https://spiffy.io";
            restEndpoint = endpoint + "/api/";
            suffix = "";
        } else {
            throw new InvalidParameterException(String.format("unknown AppStage: %s", stage));
        }
    }

}
