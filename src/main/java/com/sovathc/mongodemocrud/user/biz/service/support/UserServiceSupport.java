package com.sovathc.mongodemocrud.user.biz.service.support;

import com.sovathc.mongodemocrud.user.biz.service.PdfGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceSupport {
    private final PdfGeneratorService pdfGeneratorService;

    public String testBean()
    {
        return "Hello, world";
    }
}
