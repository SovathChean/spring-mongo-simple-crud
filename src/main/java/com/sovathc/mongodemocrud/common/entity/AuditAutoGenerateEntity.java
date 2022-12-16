package com.sovathc.mongodemocrud.common.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Getter
@Setter
public class AuditAutoGenerateEntity {
    @Field(name = "created_by")
    protected String createdBy;
    @Field(name = "created_date")
    protected Date createdDate = new Date();
    @Field(name = "updated_by")
    protected String updatedBy;
    @Field(name = "updated_date")
    protected Date updatedDate;
}
