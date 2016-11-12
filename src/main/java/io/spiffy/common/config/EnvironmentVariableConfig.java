package io.spiffy.common.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.security.spec.InvalidKeySpecException;

import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.RSA;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import io.spiffy.common.DynamoEntity;
import io.spiffy.common.DynamoRepository;

@Configuration
public class EnvironmentVariableConfig {

    @Bean
    public Initialized init(final EnvironmentVariableRepository repository) {
        AppConfig.setApiKey(repository.getValue("apiKey"));
        AppConfig.setCdnKeyPair(repository.getValue("cdnKeyPair"));
        AppConfig.setFacebookClientSecret(repository.getValue("facebookClientSecret"));
        AppConfig.setGoogleClientSecret(repository.getValue("googleClientSecret"));

        try {
            AppConfig.setCdnPrivateKey(RSA.privateKeyFromPKCS8(Base64.decodeBase64(repository.getValue("cdnPrivateKey"))));
        } catch (final InvalidKeySpecException e) {
            throw new RuntimeException();
        }

        return new Initialized();
    }

    public static class Initialized {
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    @DynamoDBTable(tableName = "environment-variables")
    public static class EnvironmentVariableEntity extends DynamoEntity {

        @DynamoDBHashKey
        private String name;
        private String value;
    }

    public static class EnvironmentVariableRepository extends DynamoRepository<EnvironmentVariableEntity> {

        @Inject
        protected EnvironmentVariableRepository(final AmazonDynamoDBClient client) {
            super(EnvironmentVariableEntity.class, client);
        }

        public String getValue(final String name) {
            final EnvironmentVariableEntity entity = load(name);
            return entity.getValue();
        }
    }
}
