package com.sovathc.mongodemocrud.user.web.vo.request;

import lombok.Data;

@Data
public class UserCreatedRequest {
    private String username;
    private String password;
}
