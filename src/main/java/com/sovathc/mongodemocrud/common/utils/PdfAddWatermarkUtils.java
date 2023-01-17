package com.sovathc.mongodemocrud.common.utils;

import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;

import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

@Component
public class PdfAddWatermarkUtils {
    public void addWaterMark() throws MalformedURLException, FileNotFoundException {
        String path = "D:\\backend\\pdf\\user.pdf";


        PdfWriter pdfwriter = new PdfWriter(path);

        // Creating a PdfDocument object.
        // passing PdfWriter object constructor of
        // pdfDocument.
        PdfDocument pdfdocument
                = new PdfDocument(pdfwriter);

        // Creating a Document and passing pdfDocument
        // object
        Document document = new Document(pdfdocument);

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
        Canvas canvas
                = new Canvas(template, pdfdocument).add(image);
        String watermark = "GeeksForGeeks";
        canvas.setFontColor(DeviceGray.GRAY)
                .showTextAligned(watermark, 120, 300,
                        TextAlignment.CENTER);

        // adding template to document
        Image imagew = new Image(template);
        document.add(imagew);

        // closing the document
        document.close();
        System.out.println(
                "Applied the Water Mark successfully");
    }
}
