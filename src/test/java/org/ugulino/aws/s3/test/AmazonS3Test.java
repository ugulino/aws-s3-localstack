package org.ugulino.aws.s3.test;

import org.junit.jupiter.api.*;
import org.ugulino.aws.s3.BucketHandler;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AmazonS3Test {
    static BucketHandler bucketHandler;
    private static S3Client s3;
    private static Region region;
    private static String BUCKET_NAME = "my-bucket";
    private static String FILE_NAME_UPLOAD_S3 = "Test.txt";
    private static String FILE_PATH_UPLOAD = "src/test/resources/Teste.txt";
    private static String HOST_LOCALSTACK = "http://localhost:4566";

    @BeforeAll
    public static void setup() throws IOException {
        region = Region.US_EAST_1;
        s3 = S3Client.builder()
                .region(region)
                .endpointOverride(URI.create(HOST_LOCALSTACK))
                .build();

        bucketHandler = new BucketHandler();
    }

    @Test
    @Order(1)
    public void whenInitializingAWSS3Service_thenNotNull() {
        assertNotNull(s3);
    }

    @Test
    @Order(2)
    public void createBucket() {
        CreateBucketResponse result = bucketHandler.createBucket();
        assertNotNull(result);
    }

    @Test
    @Order(3)
    public void putObject() throws URISyntaxException {
        PutObjectResponse response = bucketHandler.putObject(FILE_NAME_UPLOAD_S3);
        assertTrue(response.sdkHttpResponse().isSuccessful());
    }

    @Test
    @Order(4)
    public void ListContent() throws IOException {
        String [] proposals = bucketHandler.ListContent(FILE_NAME_UPLOAD_S3);
        assertTrue(proposals.length == 5);
    }
}
