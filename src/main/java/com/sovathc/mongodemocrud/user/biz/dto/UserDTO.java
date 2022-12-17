package com.sovathc.mongodemocrud.user.biz.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String id;
    private String username;
    private String keywords;
    private String filter;
}
