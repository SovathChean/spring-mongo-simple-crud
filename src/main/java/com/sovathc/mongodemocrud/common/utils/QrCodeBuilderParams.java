package com.sovathc.mongodemocrud.common.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class QrCodeBuilderParams {
    private String qrCodeText;

    private int qrCodeMargin;

    private int qrCodeSize;

    private String qrCodeRgbColorBackground;

    private String qrCodeRgbColorForeground;

    private boolean qrCodeLogoEnabled;

    private String qrCodeLogoPath;

    private int qrCodeLogoSize;

    private int qrCodeLogoTransparency;
}
