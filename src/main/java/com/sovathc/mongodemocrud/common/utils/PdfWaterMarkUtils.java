package com.sovathc.mongodemocrud.common.utils;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class PdfWaterMarkUtils {
    @Autowired
    private TemplateEngine templateEngine;

    public byte[] generatePDFFromHTML(String templateName) throws IOException {
        Context context = new Context();
        context.setVariables(null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String htmlContent = templateEngine.process(templateName, context);
        Document document = Jsoup.parse(htmlContent, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        try {
            FontProvider dfp = new DefaultFontProvider();
            dfp.addFont("src/main/resources/static/font/bayon.ttf");
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(dfp);
            converterProperties.setCharset(StandardCharsets.UTF_8.name());
            ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(byteArrayOutputStream1);
            PdfDocument pdfDocument = new PdfDocument(writer);

            Watermark watermark = new Watermark("Confidential");
            pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, watermark);
            try
            {
                HtmlConverter.convertToPdf(document.html(), pdfDocument, converterProperties);

            }catch (Exception e)
            {
                log.error("12");
            }
            pdfDocument.close();

            return byteArrayOutputStream1.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public ByteArrayOutputStream generateWatermark(ByteArrayOutputStream byteArrayOutputStream) {
        Context context = new Context();
        context.setVariables(null);
        String htmlContent = templateEngine.process("commercial-invoice-paid", context);
        Document document = Jsoup.parse(htmlContent, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        try {
            FontProvider dfp = new DefaultFontProvider();
            dfp.addFont("src/main/resources/static/font/bayon.ttf");
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(dfp);

            converterProperties.setCharset(StandardCharsets.UTF_8.name());
            HtmlConverter.convertToPdf(document.html(), byteArrayOutputStream, converterProperties);

            return byteArrayOutputStream;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public byte[] watermarkImage(ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        PdfWriter pdfwriter = new PdfWriter(byteArrayOutputStream);
        // Creating a PdfDocument object.
        // passing PdfWriter object constructor of
        // pdfDocument.
        PdfDocument pdfdocument = new PdfDocument(pdfwriter);

        // Creating a Document and passing pdfDocument
        // object
        com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfdocument);
        // Create an ImageData object
        String imageFile = "https://d3acsj8nrl7kd.cloudfront.net/market/dev-mkt/temporary/user/135/invoice-6ad1be43-cbbf-414c-886c-be5d22ed910a.png";
        ImageData data = ImageDataFactory.create(imageFile);
        // Creating an Image object
        Image image = new Image(data);

        image.scaleToFit(400, 700);
        // Creating template
        PdfFormXObject template = new PdfFormXObject(
                new Rectangle(image.getImageScaledWidth(),
                        image.getImageScaledHeight()));
        PdfCanvas pdfCanvas = new PdfCanvas(template, pdfdocument);
        PdfExtGState gstate = new PdfExtGState();
        gstate.setFillOpacity(.2f);
        pdfCanvas.setExtGState(gstate);
        Canvas canvas = new Canvas(pdfCanvas, null);


        String watermark = "GeeksForGeeks";
        canvas.setFontColor(DeviceGray.GRAY)
                .showTextAligned(watermark, 120, 300,
                        TextAlignment.CENTER);
        canvas.close();
        // adding template to document
//        Image imagew = new Image(template);
//        document.add(imagew);

        // closing the document
//        document.close();
        System.out.println("Applied the Water Mark successfully");

        return byteArrayOutputStream.toByteArray();
    }

    protected static class Watermark implements IEventHandler {

        String watermarkText;

        public Watermark(String watermarkText) {
            this.watermarkText = watermarkText;
        }

        @Override
        public void handleEvent(Event event) {
            //Retrieve document and
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;

            PdfDocument pdf = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(page.getLastContentStream(), page.getResources(), pdf);
            Canvas canvas = new Canvas(pdfCanvas, pageSize);
            PdfExtGState gstate = new PdfExtGState();
            gstate.setFillOpacity(1f);
            pdfCanvas.setExtGState(gstate);

            String imageFile = "https://d3acsj8nrl7kd.cloudfront.net/market/dev-mkt/temporary/user/135/invoice-6ad1be43-cbbf-414c-886c-be5d22ed910a.png";
            String wingMarket = "https://d3acsj8nrl7kd.cloudfront.net/market/dev-mkt/invoice/null/user/135//invoice-f296d5ca-44be-4587-bac4-86ade190b96c.png";
            ImageData data = null;
            ImageData wingMarketLogo = null;
            try {
                data = ImageDataFactory.create(imageFile);
                wingMarketLogo = ImageDataFactory.create(wingMarket);

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            // Creating an Image object
            Image image = new Image(data);
            image.setOpacity(.8f)
                    .setFixedPosition(page.getPageSize().getWidth()/2 - 130, page.getPageSize().getHeight()/2 - 140);
            //wing market
            Image image1 = new Image(wingMarketLogo);
                    image1
                            .setFixedPosition(36f, 10f);
            // Creating template
            PdfFormXObject template = new PdfFormXObject(
                    new Rectangle(image.getImageScaledWidth(),
                            image.getImageScaledHeight()));
            Border b1 = new SolidBorder( DeviceRgb.BLACK, 5);
            // adding template to document
            Image imagew = new Image(template);
            imagew.setFixedPosition(1, page.getPageSize().getHeight(), page.getPageSize().getWidth());

            Paragraph watermarkParagraph = new Paragraph("WINGMARKET PLATFORM")
                    .setFontSize(10f)
                    .setBold()
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setVerticalAlignment(VerticalAlignment.BOTTOM)
                    .setHeight(page.getPageSize().getHeight() - 15)
                    .setMarginRight(36)
                   ;
            Paragraph border = new Paragraph()
                    .setBorderBottom(new SolidBorder(DeviceRgb.BLACK, 1))
                    .setFixedPosition(36, 50f, page.getPageSize().getWidth() - 72);

            canvas.add(watermarkParagraph);
            canvas.showTextAligned(new Paragraph(String.format("Page %s", pdf.getPageNumber(page))).setFontSize(10f),
                    559, pageSize.getBottom() + 30, pdf.getPageNumber(page), TextAlignment.RIGHT, VerticalAlignment.BOTTOM, 0)
                    ;
            canvas.add(border);
            canvas.add(image1);

            canvas.close();
        }

    }


}
