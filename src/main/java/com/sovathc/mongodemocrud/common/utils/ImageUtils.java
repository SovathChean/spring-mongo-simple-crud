package com.sovathc.mongodemocrud.common.utils;

import lombok.SneakyThrows;
import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ImageUtils {

    @SneakyThrows
    public static final String decorate(
            String frameImage,
            Integer frameWidth,
            Integer frameHeight,
            String innerImage,
            Integer innerWidth,
            Integer innerHeight,
            Integer x,
            Integer y,
            String extension,
            Integer coefficient) {
        // Frame
        byte[] famedata =
                DatatypeConverter.parseBase64Binary(frameImage.substring(frameImage.indexOf(",") + 1));
        BufferedImage frame = ImageIO.read(new ByteArrayInputStream(famedata));
        // Inner Image
        byte[] innerdata =
                DatatypeConverter.parseBase64Binary(innerImage.substring(innerImage.indexOf(",") + 1));
        BufferedImage inner = ImageIO.read(new ByteArrayInputStream(innerdata));
        Font font = new Font("Inter",  Font.PLAIN, 12*coefficient);
        int type = BufferedImage.TYPE_INT_RGB;
        switch (extension) {
            case "png": type = BufferedImage.TYPE_INT_ARGB;
                break;
            default: break;
        }
        // New Image
        BufferedImage modified =
                new BufferedImage(frameWidth*2, frameHeight*2, type);
        Graphics2D graphics = (Graphics2D) modified.getGraphics();
        graphics.drawImage(frame, 0, 0, frameWidth*coefficient, frameHeight*coefficient, null);
        graphics.drawImage(inner, x*coefficient, y*coefficient, innerWidth*coefficient, innerHeight*coefficient, null);
        graphics.setFont(font);
        graphics.setColor(Color.BLACK);
        graphics.drawString("SO PSP KHR Supplier CO,. LTD SO PSP KHR Supplier CO,. LTD ", 30*2, 66*2);
        graphics.setFont(new Font("Inter", Font.BOLD, 21*coefficient));
        graphics.drawString("$ 1,000,000,000,000", 30*coefficient, 96*coefficient);
        graphics.dispose();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(modified, extension, out);
        byte[] bytes = out.toByteArray();
        return Base64Utils.encodeToString(bytes);
    }
    @SneakyThrows
    public static final String decorate(
            String frameImage,
            Integer frameWidth,
            Integer frameHeight,
            Integer headerHeight,
            String innerImage,
            String extension) {
        // Frame
        byte[] famedata =
                DatatypeConverter.parseBase64Binary(frameImage.substring(frameImage.indexOf(",") + 1));
        BufferedImage frame = ImageIO.read(new ByteArrayInputStream(famedata));
        // Inner Image
        byte[] innerdata =
                DatatypeConverter.parseBase64Binary(innerImage.substring(innerImage.indexOf(",") + 1));
        BufferedImage inner = ImageIO.read(new ByteArrayInputStream(innerdata));
        // New Image
        int type = BufferedImage.TYPE_INT_RGB;
        switch (extension) {
            case "png": type = BufferedImage.TYPE_INT_ARGB;
                break;
            default: break;
        }
        BufferedImage modified =
                new BufferedImage(frameWidth, frameHeight, type);
        Graphics2D graphics = (Graphics2D) modified.getGraphics();
        graphics.drawImage(inner, 0, frameHeight - frameWidth, frameWidth, frameWidth, Color.WHITE, null);
        graphics.drawImage(frame, 0, 0, frameWidth, frameHeight, Color.WHITE, null);
        graphics.dispose();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(modified, extension, out);
        byte[] bytes = out.toByteArray();
        return Base64Utils.encodeToString(bytes);
    }
}
