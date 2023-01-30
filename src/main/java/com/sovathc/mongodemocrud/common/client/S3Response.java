package com.sovathc.mongodemocrud.common.client;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class S3Response {
    private String path;
    private String host;
    private String name;
    private String mimeType;
    private String width;
    private String height;
    private String entityType;
}
