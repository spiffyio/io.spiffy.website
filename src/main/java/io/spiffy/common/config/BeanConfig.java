package io.spiffy.common.config;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Link;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

import net.spy.memcached.MemcachedClient;

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

    @Bean(name = "amazonRegion")
    public Region getAmazonRegion() {
        return Region.getRegion(Regions.US_WEST_2);
    }

    @Bean
    public MemcachedClient getMemcachedClient() throws IOException {
        final String hostname = AppConfig.getCacheEndpoint();
        return new MemcachedClient(new InetSocketAddress(hostname, 11211));
    }

    @Bean
    public Client getClient() {
        return ClientBuilder.newClient();
    }

    @Primary
    @Bean(name = "webTarget")
    public WebTarget getWebTarget(final Client client) {
        return client.target(Link.fromPath(AppConfig.getRestEndpoint()).build());
    }

    @Bean(name = "googleWebTarget")
    public WebTarget getGoogleWebTarget(final Client client) {
        return client.target("https://www.google.com/");
    }
}
