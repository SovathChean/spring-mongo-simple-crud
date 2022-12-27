package com.sovathc.mongodemocrud.common.utils;


import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
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
import com.itextpdf.layout.properties.BaseDirection;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;
import com.lowagie.text.DocumentException;
import com.sovathc.mongodemocrud.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import java.awt.font.NumericShaper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


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
        PdfFont bf = PdfFontFactory.createFont("bayon.ttf", PdfEncodings.PDF_DOC_ENCODING, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
        document.setFontProvider(new FontProvider(set));
        document.setProperty(Property.FONT, new String[]{"Bayon"});
        Style khmer = new Style().setFont(bf);
        document.add(new Paragraph("ក្រុម").setFontScript(Character.UnicodeScript.KHMER).addStyle(khmer));
        document.close();
    }
}
