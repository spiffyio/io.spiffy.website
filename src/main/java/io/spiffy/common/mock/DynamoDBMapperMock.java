package io.spiffy.common.mock;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

import io.spiffy.common.util.JsonUtil;

public class DynamoDBMapperMock extends DynamoDBMapper {

    private final static Map<Object, String> data = new HashMap<>();

    static {
        data.put("apiKey", "{\"name\":\"apiKey\",\"value\":\"mock\"}");
        data.put("cdnKeyPair", "{\"name\":\"cdnKeyPair\",\"value\":\"mock\"}");
        data.put("amazonClientSecret", "{\"name\":\"amazonClientSecret\",\"value\":\"mock\"}");
        data.put("facebookClientSecret", "{\"name\":\"facebookClientSecret\",\"value\":\"mock\"}");
        data.put("googleClientSecret", "{\"name\":\"googleClientSecret\",\"value\":\"mock\"}");
        data.put("twitterClientSecret", "{\"name\":\"twitterClientSecret\",\"value\":\"mock\"}");
        data.put("cdnPrivateKey", "{\"name\":\"cdnPrivateKey\",\"value\":\"mock\"}");
    }

    public DynamoDBMapperMock(final AmazonDynamoDB dynamoDB, final DynamoDBMapperConfig config) {
        super(dynamoDB, config);
    }

    @Override
    public <T extends Object> void save(final T object) {
        final Object hashKey = getHashKey(object);
        final String json = JsonUtil.serialize(object);
        synchronized (data) {
            data.put(hashKey, json);
        }
    }

    @Override
    public <T extends Object> T load(final Class<T> clazz, final Object hashKey) {
        final String json;
        synchronized (data) {
            json = data.get(hashKey);
        }
        return JsonUtil.deserialize(clazz, json);
    }

    private <T extends Object> Object getHashKey(final T object) {
        final Class<?> clazz = object.getClass();
        final Field[] fields = clazz.getFields();
        for (final Field field : fields) {
            if (field.getAnnotation(DynamoDBHashKey.class) == null) {
                continue;
            }
            try {
                return field.get(object);
            } catch (final IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException("MOCK EXCEPTION: unknown dynamodbhashkey!", e);
            }
        }

        throw new RuntimeException("MOCK EXCEPTION: unknown dynamodbhashkey!");
    }
}
