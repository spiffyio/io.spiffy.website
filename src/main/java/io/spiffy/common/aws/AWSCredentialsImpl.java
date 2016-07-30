package io.spiffy.common.aws;

import com.amazonaws.auth.AWSCredentials;

import io.spiffy.common.config.AppConfig;

public class AWSCredentialsImpl implements AWSCredentials {

    public String getAWSAccessKeyId() {
        return AppConfig.getAwsAccessKeyId();
    }

    public String getAWSSecretKey() {
        return AppConfig.getAwsSecretKey();
    }
}
