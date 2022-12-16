package com.sovathc.mongodemocrud.user.web.vo.request;

import com.sovathc.mongodemocrud.common.request.PageableRequest;
import lombok.Data;

@Data
public class UserPagableRequest extends PageableRequest {
    private String keywords;
}
