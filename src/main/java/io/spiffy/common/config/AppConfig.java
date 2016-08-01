package io.spiffy.common.config;

import lombok.Getter;

import java.security.InvalidParameterException;

public class AppConfig {

    private static final String LOCAL = "local";
    private static final String BETA = "beta";
    private static final String PROD = "prod";

    private static final String TRUE = "true";

    @Getter
    private static final String stage;

    @Getter
    private static final String awsAccessKeyId;

    @Getter
    private static final String awsSecretKey;

    @Getter
    private static final String encryptionKey;

    @Getter
    private static final String encryptionIV;

    @Getter
    private static final boolean secure;

    @Getter
    private static final boolean forwardToProd;

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
        encryptionKey = System.getProperty("encryption.key");
        encryptionIV = System.getProperty("encryption.init_vector");
        secure = TRUE.equalsIgnoreCase(System.getProperty("secure"));

        if (LOCAL.equalsIgnoreCase(stage)) {
            endpoint = "http://localhost:1280";
            restEndpoint = endpoint + "/api/";
            forwardToProd = false;
            suffix = "-beta";
        } else if (BETA.equalsIgnoreCase(stage)) {
            endpoint = "https://beta.spiffy.io";
            restEndpoint = endpoint + "/api/";
            forwardToProd = true;
            suffix = "-beta";
        } else if (PROD.equalsIgnoreCase(stage)) {
            endpoint = "https://spiffy.io";
            restEndpoint = endpoint + "/api/";
            forwardToProd = false;
            suffix = "";
        } else {
            throw new InvalidParameterException(String.format("unknown AppStage: %s", stage));
        }
    }

}
