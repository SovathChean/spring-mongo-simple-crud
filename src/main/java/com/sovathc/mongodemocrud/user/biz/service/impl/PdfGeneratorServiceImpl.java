package com.sovathc.mongodemocrud.user.biz.service.impl;

import com.lowagie.text.DocumentException;
import com.sovathc.mongodemocrud.common.constants.SysHttpResultCode;
import com.sovathc.mongodemocrud.common.exception.BusinessException;
import com.sovathc.mongodemocrud.user.biz.service.PdfGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

@Service
public class PdfGeneratorServiceImpl implements PdfGeneratorService {
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @Override
    public void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) throws BusinessException {
        String pdfDirectory = "D:\\backend\\pdf\\";
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(pdfDirectory + pdfFileName);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(fileOutputStream, false);
            renderer.finishPDF();
        } catch (FileNotFoundException | DocumentException e) {
            throw new BusinessException(SysHttpResultCode.ERROR_500.getCode(), "Cannot convert pdf");
        }

    }
    @Override
    public void downloadPDF(Map<String, Object> data, HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        Context context = new Context();
        context.setVariables(data);
        String htmlContent = templateEngine.process("user", context);
        try {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            renderer.createPDF(baos, false);
            renderer.finishPDF();

            // setting some response headers
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control",
                    "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            // setting the content type
            response.setContentType("application/pdf");
            // the contentlength
            response.setContentLength(baos.size());
            // write ByteArrayOutputStream to the ServletOutputStream
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
        }
        catch(DocumentException e) {
            throw new IOException(e.getMessage());
        }
    }

}
