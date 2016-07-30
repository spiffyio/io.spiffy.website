package io.spiffy.invite.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@Data
@NoArgsConstructor
@DynamoDBTable(tableName = "invites")
public class InviteEntity {

    @DynamoDBHashKey
    private String email;
}
