package io.spiffy.invite.repository;

import javax.inject.Inject;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;

import io.spiffy.common.Repository;
import io.spiffy.common.config.AppConfig;
import io.spiffy.invite.entity.InviteEntity;

public class InviteRepository extends Repository<InviteEntity> {

    private final AmazonDynamoDBClient client;
    private final DynamoDBMapper mapper;

    @Inject
    public InviteRepository(final AmazonDynamoDBClient client) {
        this.client = client;
        this.client.setRegion(Region.getRegion(Regions.US_WEST_2));
        mapper = new DynamoDBMapper(client, new DynamoDBMapperConfig(new TableNameOverride("invites" + AppConfig.getSuffix())));
    }

    public void save(final InviteEntity entity) {
        mapper.save(entity);
    }

    public InviteEntity load(final String id) {
        return mapper.load(InviteEntity.class, id);
    }
}
