package com.sovathc.mongodemocrud.common.utils;


import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;
import com.itextpdf.licensing.base.LicenseKey;
import com.lowagie.text.DocumentException;
import com.sovathc.mongodemocrud.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class PdfTextGenerateUtils {
    @Autowired
    private TemplateEngine templateEngine;
    public void pdfConverter(String templateName) throws BusinessException, DocumentException, IOException {
        String pdfDirectory = "D:\\backend\\pdf\\user1.pdf";
        final PdfWriter writer = new PdfWriter(pdfDirectory);
        final PdfDocument pdfDocument = new PdfDocument(writer);

        Document document = new Document(pdfDocument);
        final FontSet set = new FontSet();
        set.addFont("bayon.ttf");
        document.setFontProvider(new FontProvider(set));
        document.add(new Paragraph("ក្រុម").setFontScript(Character.UnicodeScript.KHMER));
        document.close();
    }
}
