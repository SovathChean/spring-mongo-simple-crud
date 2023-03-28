package com.sovathc.mongodemocrud.user.web.vo.request;

import lombok.Data;

@Data
public class ImageConvertRequest {
    public String frameImage;
    public Integer frameWidth;
    public Integer frameHeight;
    public String innerImage;
    public Integer innerWidth;
    public Integer innerHeight;
    public Integer x;
    public Integer y;
}
