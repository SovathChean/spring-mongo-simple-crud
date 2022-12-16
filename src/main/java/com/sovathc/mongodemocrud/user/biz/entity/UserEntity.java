package com.sovathc.mongodemocrud.user.biz.entity;

import com.sovathc.mongodemocrud.common.entity.AuditAutoGenerateEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document(collection = UserEntity.COLLECTION_NAME)
public class UserEntity extends AuditAutoGenerateEntity {
    public static final String COLLECTION_NAME = "users";

    @Id
    private String id;
    @Field("username")
    private String username;
    @Field("password")
    private String password;
}
