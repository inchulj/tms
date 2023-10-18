package com.tms.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@DynamoDBTable(tableName = "BRANCH")
public class Branch {

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String id;

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private String website;

    @DynamoDBAttribute
    private String contact;

    @DynamoDBAttribute
    private String email;

    @DynamoDBAttribute
//    private String tariffId;
    private Tariff tariff;

    @DynamoDBAttribute//    private LocalDateTime dateTime;
    private String dateTime;

    public Branch () {}

    public Branch(String name, String website, String contact, String email, Tariff tariff, String dateTime) {
        this.name = name;
        this.website = website;
        this.contact = contact;
        this.email = email;
        this.tariff = tariff;
        this.dateTime = dateTime;
    }
}
