package com.sovathc.mongodemocrud.common.utils;


import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.sovathc.mongodemocrud.common.exception.BusinessException;
import gui.ava.html.image.generator.HtmlImageGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.awt.*;
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
    private String invoice_template = "reports/report.jrxml";

    public byte[] downloadPDF(String templateName, Map<String, Object> data) throws BusinessException {
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);
        Document document = Jsoup.parse(htmlContent, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
//        LicenseKey.loadLicenseFile(new File("license.json"));
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
            String fileType = "png";
            HtmlImageGenerator originalGenerator = new HtmlImageGenerator();
            originalGenerator.loadHtml(document.html());
            originalGenerator.saveAsImage(pdfDirectory+ "user" + "." + fileType);

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
        String pdfDirectory = "D:\\backend\\pdf\\user3.pdf";
        final PdfWriter writer = new PdfWriter(pdfDirectory);
        final PdfDocument pdfDocument = new PdfDocument(writer);
        Context context = new Context();
        context.setVariables(null);

        String htmlContent = templateEngine.process("commercial-invoice", context);
        Document document = Jsoup.parse(htmlContent, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        File pdfDest = new File(pdfDirectory);
        FontProvider dfp = new DefaultFontProvider();
        dfp.addFont("bayon.ttf");

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setFontProvider(dfp);
        converterProperties.setCharset(StandardCharsets.UTF_8.name());

        HtmlConverter.convertToPdf(document.html(), new FileOutputStream(pdfDest), converterProperties);
    }
    public byte[] html2PdfByte() throws FileNotFoundException {
        String pdfDirectory = "invoice.pdf";
        final PdfWriter writer = new PdfWriter(pdfDirectory);
        final PdfDocument pdfDocument = new PdfDocument(writer);
        Context context = new Context();
        context.setVariables(null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        String htmlContent = templateEngine.process("commercial-invoice", context);
        Document document = Jsoup.parse(htmlContent, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        File pdfDest = new File(pdfDirectory);
        FontProvider dfp = new DefaultFontProvider();
        dfp.addFont("src/main/resources/static/font/bayon.ttf");

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setFontProvider(dfp);
        converterProperties.setCharset(StandardCharsets.UTF_8.name());

        HtmlConverter.convertToPdf(document.html(), byteArrayOutputStream, converterProperties);

        return byteArrayOutputStream.toByteArray();
    }
    private JasperReport loadTemplate() throws JRException, FileNotFoundException {

        File reportInputStream = ResourceUtils.getFile("classpath:reports/report.jrxml");
        JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);

        return JasperCompileManager.compileReport(jasperDesign);
    }
    public byte[] generateInvoiceFor() throws IOException {
        String pdfDirectory = "D:\\backend\\pdf\\user";
        File pdfFile = File.createTempFile(pdfDirectory, ".pdf");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try
        {
            // Load invoice JRXML template.
            final JasperReport report = loadTemplate();

            // Fill parameters map.
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("data", "user");

            // Create an empty datasource.
            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());

            JasperExportManager.exportReportToPdfStream(print, byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();
        }
        catch (final Exception e)
        {
            throw new RuntimeException(e);
        }
    }


}
