package com.sovathc.mongodemocrud.common.client;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sovathc.mongodemocrud.common.converter.Base64ToImage;
import com.sovathc.mongodemocrud.user.biz.dto.UploadKhQrDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.File;

@Component
@Slf4j
public class AmazonClient {

    // @Autowired
    private AmazonS3 s3client;
    private static String defaultRegion = "ap-southeast-1";
    private String defaultEndpointUrl = "https://s3.ap-southeast-1.amazonaws.com";

    private String bucketName = "spring01-testing";
    private String accessKey = "AKIAVXVBNFWHCFS3I3VE";
    private String secretKey = "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";
    public String store(UploadKhQrDTO profileUploadBean) {
        String fileName = profileUploadBean.getName();
        String fileUrl = "";
        try {
            File file = Base64ToImage.getImageFromBase64(profileUploadBean.getImage(), fileName);
            buildAmazonS3Client(defaultRegion, defaultEndpointUrl);
            fileUrl = defaultEndpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
            log.info("File uploaded to S3 successfully");
        } catch (Exception e) {
            log.error("Error while uploadeding the file to S3" + e);
            throw e;
        }
        return fileUrl;
    }
    private void uploadFileTos3bucket(String fileName, File file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setCacheControl("51635000");
        metadata.setContentType("image/png");
        metadata.setCacheControl("public, max-age=31536000");
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead).withMetadata(metadata));
    }
    void buildAmazonS3Client(String defaultRegion, String endpoint) {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        AmazonS3ClientBuilder amazons3ClientBuilder = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, defaultRegion));
        this.s3client = amazons3ClientBuilder.build();
    }


}
