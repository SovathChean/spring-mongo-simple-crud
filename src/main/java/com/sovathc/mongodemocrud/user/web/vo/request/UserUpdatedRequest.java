package com.sovathc.mongodemocrud.user.web.vo.request;

import lombok.Data;

@Data
public class UserUpdatedRequest {
    private String id;
    private String username;
    private String password;
}
