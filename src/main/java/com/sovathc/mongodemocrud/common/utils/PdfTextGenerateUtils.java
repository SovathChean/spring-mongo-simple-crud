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
import com.lowagie.text.DocumentException;
import com.sovathc.mongodemocrud.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class PdfTextGenerateUtils {
    @Autowired
    private TemplateEngine templateEngine;
    public void pdfConverter(String templateName) throws BusinessException, DocumentException, IOException {
        String pdfDirectory = "/Users/9neal/Documents/user.pdf";
        final PdfWriter writer = new PdfWriter(pdfDirectory);
        final PdfDocument pdfDocument = new PdfDocument(writer);

        Document document = new Document(pdfDocument);
        FontSet set = new FontSet();
        set.addFont("NotoSerifKhmer-Regular.ttf");
//        set.addFont("NotoSansTamil-Regular.ttf");
        PdfFont bf = PdfFontFactory.createFont("KhmerOSmuol.ttf", PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
        Style khmer = new Style().setFontScript(Character.UnicodeScript.KHMER).setFont(bf);

        document.add(new Paragraph("\n" +
                "ខ្ញុំចូលចិត្តអ្នកព្រោះអ្នកគឺល្អបំផុត ហាហា។ ឆ្នាំនេះគឺអំពីសាលានិងសាកលវិទ្យាល័យ").addStyle(khmer));
        document.close();
    }
}
