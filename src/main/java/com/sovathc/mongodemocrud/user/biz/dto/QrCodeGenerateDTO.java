package com.sovathc.mongodemocrud.user.biz.dto;

import lombok.Data;

@Data
public class QrCodeGenerateDTO {
    private String text;
    private String logoPath;
    private String foreColor;
    private String bgColor;
    private int qrCodeMargin;
    private String qrCodeLogoSize;
    private boolean qrCodeLogoEnabled;
    private float qrCodeLogoTransparency;
    private int qrCodeSize;
    private int logoSize;
}
