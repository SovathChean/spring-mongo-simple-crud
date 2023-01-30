package com.sovathc.mongodemocrud.common.converter;

import javax.xml.bind.DatatypeConverter;
import java.io.*;

public class Base64ToImage {
    public static File getImageFromBase64(String base64String, String fileName) {
        String[] strings = base64String.split(",");
        String extension;
        switch (strings[0]) { // check image's extension
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            default: // should write cases for more images types
                extension = "jpg";
                break;
        }
        // convert base64 string to binary data
        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
        File file = new File(fileName + '.' + extension);


        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    public static InputStream imageFromBase63(String base64String, String fileName, String extension)
    {
        byte[] data = DatatypeConverter.parseBase64Binary(base64String);
        return new ByteArrayInputStream(data);
    }
}
