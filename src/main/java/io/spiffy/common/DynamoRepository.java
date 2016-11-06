package io.spiffy.common;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import io.spiffy.common.config.AppConfig;

public abstract class DynamoRepository<E extends DynamoEntity> extends Repository<E> {

    private final Class<E> entityClass;
    private final DynamoDBMapper mapper;

    protected DynamoRepository(final Class<E> clazz, final AmazonDynamoDBClient client) {
        final DynamoDBTable annotation = clazz.getAnnotation(DynamoDBTable.class);
        final TableNameOverride table = new TableNameOverride(annotation.tableName() + AppConfig.getSuffix());
        final DynamoDBMapperConfig config = new DynamoDBMapperConfig(table);

        client.setRegion(Region.getRegion(Regions.US_WEST_2));

        this.entityClass = clazz;
        this.mapper = new DynamoDBMapper(client, config);
    }

    public void save(final E entity) {
        mapper.save(entity);
    }

    public E load(final String id) {
        return mapper.load(entityClass, id);
    }
}
