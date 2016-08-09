package io.spiffy.common.config;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.auth.AWSCredentials;

@Configuration
public class BeanConfig {

    @Bean(name = "awsCredentials")
    public AWSCredentials getAWSCredentials() {
        return new AWSCredentials() {
            public String getAWSAccessKeyId() {
                return AppConfig.getAwsAccessKeyId();
            }

            public String getAWSSecretKey() {
                return AppConfig.getAwsSecretKey();
            }
        };
    }

    @Bean
    public Client getClient() {
        return ClientBuilder.newClient();
    }

    @Primary
    @Bean(name = "webTarget")
    public WebTarget getWebTarget(final Client client) {
        return client.target(AppConfig.getRestEndpoint());
    }

    @Bean(name = "googleWebTarget")
    public WebTarget getGoogleWebTarget(final Client client) {
        return client.target("https://www.google.com/");
    }
}
