package io.spiffy.common.config;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean
    public WebTarget getWebTarget(final Client client) {
        return client.target("http://localhost:1280/api/");
    }
}
