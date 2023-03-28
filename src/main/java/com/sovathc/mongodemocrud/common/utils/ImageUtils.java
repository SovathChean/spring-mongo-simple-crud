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
            Integer y) {
        // Frame
        byte[] famedata =
                DatatypeConverter.parseBase64Binary(frameImage.substring(frameImage.indexOf(",") + 1));
        BufferedImage frame = ImageIO.read(new ByteArrayInputStream(famedata));
        // Inner Image
        byte[] innerdata =
                DatatypeConverter.parseBase64Binary(innerImage.substring(innerImage.indexOf(",") + 1));
        BufferedImage inner = ImageIO.read(new ByteArrayInputStream(innerdata));
        Font font = new Font("Arial", Font.BOLD, 18);
        // New Image
        BufferedImage modified =
                new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) modified.getGraphics();
        graphics.drawImage(frame, 0, 0, frameWidth, frameHeight, null);
        graphics.drawImage(inner, x, y, innerWidth, innerHeight, null);
        graphics.setFont(font);
        graphics.setColor(Color.BLUE);
        graphics.drawString("User Name:", 30, 40);
        graphics.dispose();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(modified, "PNG", out);
        byte[] bytes = out.toByteArray();
        String base64bytes = Base64Utils.encodeToString(bytes);
        return "data:image/png;base64," + base64bytes;
    }
}
