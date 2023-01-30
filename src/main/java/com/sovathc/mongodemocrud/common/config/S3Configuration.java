package com.sovathc.mongodemocrud.common.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Data
@Configuration
public class S3Configuration {
    private String awsAccessKeyId = "AKIAVXVBNFWHCFS3I3VE";
    private String awsSecretKey = "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";
    private String awsRegionStatic = "ap-southeast-1";
    private String bucketName =  "spring01-testing";
    private String awsCdn = "https://s3.ap-southeast-1.amazonaws.com";

    @Bean
    public S3Client getAmazonS3Client() {
        AwsCredentials credentials = AwsBasicCredentials.create(awsAccessKeyId, awsSecretKey);
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);
        return S3Client.builder().credentialsProvider(credentialsProvider).region(Region.of(awsRegionStatic)).build();
    }

}
