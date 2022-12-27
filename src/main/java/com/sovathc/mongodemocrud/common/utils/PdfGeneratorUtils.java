package com.sovathc.mongodemocrud.common.utils;


import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.sovathc.mongodemocrud.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.ITextTextRenderer;

import java.awt.font.NumericShaper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class PdfGeneratorUtils {
    @Autowired
    private TemplateEngine templateEngine;

    public byte[] downloadPDF(String templateName, Map<String, Object> data) throws BusinessException {
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);
        Document document = Jsoup.parse(htmlContent, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.getFontResolver().addFont("bayon.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.setDocumentFromString(document.html());
            renderer.layout();
            renderer.createPDF(byteArrayOutputStream);
            renderer.finishPDF();

            return byteArrayOutputStream.toByteArray();
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void generatePdfFile(String templateName, Map<String, Object> data) {

        String pdfDirectory = "D:\\backend\\pdf\\";
        Context context = new Context();
        Map<String, Object> data1 = new HashMap<>();
        data1.put("data", "ក្រុម");
        context.setVariables(data1);

        String htmlContent = templateEngine.process(templateName, context);

        Document document = Jsoup.parse(htmlContent);
        document.charset( StandardCharsets.UTF_8);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(pdfDirectory + "user.png");
            PdfRendererBuilder builder = new PdfRendererBuilder();

            builder.useFont(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("static/font/Siemreap-Regular.ttf")).getFile()),
                    "Bayon");
//            String fileType = "png";
//            HtmlImageGenerator originalGenerator = new HtmlImageGenerator();
//            originalGenerator.loadHtml(document.html());
//            originalGenerator.saveAsImage(pdfDirectory+ "user" + "." + fileType);

            builder.toStream(fileOutputStream);
            builder.withHtmlContent(document.html(), "/");
            builder.run();

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    public byte[] generatePdfFileJasper(String templateName, Map<String, Object> data) throws IOException {

        String pdfDirectory = "D:\\backend\\pdf\\";
        Context context = new Context();
        context.setVariables(data);
        String uuid = UUID.randomUUID().toString();
        Path pathHtml = Paths.get("D:\\backend\\pdf\\" + uuid + ".html");
        Path pathPdf = Paths.get("D:\\backend\\pdf\\" + uuid + ".pdf");

        String htmlContent = templateEngine.process(templateName, context);
        Document document = Jsoup.parse(htmlContent, "UTF-8");
        try {
            // write to html
            Files.write(pathHtml, document.html().getBytes());

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            Process generateToPdf = Runtime.getRuntime().exec("wkhtmltopdf " + pathHtml.toString() + " " + pathPdf.toString() );
            generateToPdf.waitFor();

            return Files.readAllBytes(pathPdf);

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            // delete temp files
            Files.delete(pathHtml);
            Files.delete(pathPdf);
        }

    }
    public void html2Pdf() throws IOException {
        String pdfDirectory = "D:\\backend\\pdf\\user.pdf";
        final PdfWriter writer = new PdfWriter(pdfDirectory);
        final PdfDocument pdfDocument = new PdfDocument(writer);
        File htmlSource = new File("commercial-invoice.html");
        File pdfDest = new File(pdfDirectory + "output.pdf");
        FontProvider dfp = new DefaultFontProvider(true, false, false);
        dfp.addFont("BayonRegular.ttf");

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setFontProvider(dfp);
        converterProperties.setMediaDeviceDescription(new MediaDeviceDescription(MediaType.PRINT));
        converterProperties.setCharset(StandardCharsets.UTF_8.name());

        HtmlConverter.convertToPdf(new FileInputStream(htmlSource), new FileOutputStream(pdfDest), converterProperties);
    }
}
