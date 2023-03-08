package com.sovathc.mongodemocrud.user.web.vo.request;

import lombok.Data;

@Data
public class CrcRequest {
    private String orderReferenceNo;
    private String currency;
    private String total;
    private String transactionDate;
    private String transactionId;
    private String crc;
    private String salt;
}
