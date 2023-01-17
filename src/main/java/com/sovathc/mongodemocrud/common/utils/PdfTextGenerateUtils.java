package com.sovathc.mongodemocrud.common.utils;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class PdfTextGenerateUtils {
    public void pdfConverter() throws IOException {
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
