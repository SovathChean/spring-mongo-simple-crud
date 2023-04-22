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
        merchantNameStringShow(graphics, "SO PSP KHR Supplier CO,. LTD SO PSP KHR");
        amountStringShow(graphics, new Font("Inter",  Font.BOLD, 17*coefficient), "៛1,000,000,000,000,000,000", innerWidth*coefficient);
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
    protected static void merchantNameStringShow(Graphics2D graphics, String merchantName){
        String showString = StringUtils.removeStringWithLength(merchantName, 30) + "...";
        graphics.drawString(showString, 15*2, 55*2);
    }
    protected static void amountStringShow(Graphics2D graphics, Font font, String amountUsing, int limitWidth){
        FontMetrics fontMetrics = graphics.getFontMetrics(font);
        //support more than 12 digit
        if(fontMetrics.stringWidth(amountUsing) > limitWidth)
        {
            //support more than 12 digit
            Font fontSupport = new Font("Inter", Font.BOLD, 14*2);
            graphics.setFont(fontSupport);
            graphics.drawString(amountUsing, 15*2, 71*2);
        }
        else
        {
            graphics.setFont(font);
            graphics.drawString(amountUsing, 15*2, 71*2);
        }
    }
}
