package com.sovathc.mongodemocrud.user.web.vo.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KhQrCallbackRequestVO {
    private String transactionDate;
    private String transactionId;
    private String amount;
    private String account;
    private String billerName;
    private String fee;
    private String feeCurrency;
    private String totalAmount;
    private String psTrnId;
    private String fcTrnId;
    private String currency;
    private String customerName;
    @NotNull
    private String orderReferenceNo;
    private String bankName;
    private String bankAccount;
    private String bankAccountName;
}
