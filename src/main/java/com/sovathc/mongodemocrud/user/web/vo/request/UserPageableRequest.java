package com.sovathc.mongodemocrud.user.web.vo.request;

import com.sovathc.mongodemocrud.common.request.PageableRequest;
import lombok.Data;

@Data
public class UserPageableRequest extends PageableRequest {
    private String keywords;
    private String filter;
    private String templateName;
}
