package com.sovathc.mongodemocrud.common.utils;

import com.lowagie.text.DocumentException;
import com.sovathc.mongodemocrud.common.constants.SysHttpResultCode;
import com.sovathc.mongodemocrud.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class PdfGeneratorUtils {
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    public byte[] downloadPDF(String templateName, Map<String, Object> data) throws BusinessException {
        Context context = new Context();
        context.setVariables(data);
        String htmlContent = templateEngine.process(templateName, context);
        try {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            renderer.createPDF(byteArrayOutputStream, false);
            renderer.finishPDF();
            return byteArrayOutputStream.toByteArray();
        }
        catch(DocumentException e) {
            log.error("Failed download pdf: error: {}", e.getMessage());
            throw new BusinessException(SysHttpResultCode.ERROR_500.getCode(), "Download pdf failed");
        }
    }
}
