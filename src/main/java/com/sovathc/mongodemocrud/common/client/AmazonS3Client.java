package com.sovathc.mongodemocrud.common.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Component
public class AmazonS3Client {
    @Autowired
    private S3Client s3Client;
    private String bucketName = "spring01-testing";

    public S3Response uploadFileToS3Bucket(byte[] inputFile, String extension, String entityType, String entityTypeId) throws IOException {
        String finalFileName = String.format("%s-%s", entityType, UUID.randomUUID().toString());
        String file = String.format("%s.%s", finalFileName, extension);
        String path = String.format("%s/%s/%s/%s/%s", "khqr", entityType, entityTypeId, "user", entityTypeId);
        String keyName = String.format("%s/%s", path, file);
        PutObjectRequest request = PutObjectRequest.builder().contentType("image/png").bucket(bucketName).key(keyName).build();
        s3Client.putObject(request, RequestBody.fromBytes(inputFile));
        GetUrlRequest requestURL = GetUrlRequest.builder().bucket(bucketName).key(keyName).build();
        URL url = s3Client.utilities().getUrl(requestURL);
        S3Response result = new S3Response();
        result.setPath(url.getPath().substring(1));

        return result;
    }
}
