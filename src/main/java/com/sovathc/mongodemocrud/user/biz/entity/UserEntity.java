package com.sovathc.mongodemocrud.user.biz.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document
public class UserEntity {
    private static final String COLLECTION_NAME = "users";

    @Id
    private String id;
    @Field("username")
    private String username;
    @Field("password")
    private String password;
}
