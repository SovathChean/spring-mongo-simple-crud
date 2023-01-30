package com.sovathc.mongodemocrud.user.biz.dto;

import lombok.Data;

@Data
public class UploadKhQrDTO {
    private String base64Img;
    private String name;
    private String image;
}
