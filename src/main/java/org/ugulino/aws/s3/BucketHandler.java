package org.ugulino.aws.s3;

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

public class BucketHandler {

    private static S3Client s3;
    private static Region region;
    private static String BUCKET_NAME = "my-bucket";
    private static String HOST_LOCALSTACK = "http://localhost:4566";
    private static String FILE_PATH_UPLOAD = "src/test/resources/Teste.txt";

    public CreateBucketResponse createBucket() {
        region = Region.US_EAST_1;
        s3 = S3Client.builder()
                .region(region)
                .endpointOverride(URI.create(HOST_LOCALSTACK))
                .build();

        CreateBucketRequest.Builder builder = CreateBucketRequest.builder();
        CreateBucketResponse bucket = s3.createBucket(builder.bucket(BUCKET_NAME).build());
        return bucket;
    }

    public PutObjectResponse putObject(String fileName) throws URISyntaxException {
        PutObjectRequest.Builder req = PutObjectRequest.builder().key(fileName)
                .contentType("text/html; charset=ISO-8859-4")
                .bucket(BUCKET_NAME);
        Path path = Paths.get(FILE_PATH_UPLOAD);
        PutObjectResponse response = s3.putObject(req.build(), path);
        return response;
    }

    public String [] ListContent(String fileName) throws IOException {
        String[] proposals = null;
        ListBucketsRequest request = ListBucketsRequest
                .builder()
                .build();
        ListBucketsResponse response = s3.listBuckets(request);

        for (Bucket bucket : response.buckets()) {
            GetObjectRequest bucketRequest = GetObjectRequest.builder()
                    .bucket(bucket.name())
                    .key(fileName)
                    .build();
            ResponseInputStream<GetObjectResponse> stream = s3.getObject(bucketRequest);
            OutputStream output = new ByteArrayOutputStream();
            stream.transferTo(output);
            proposals = output.toString().split("\\r\\n");
        }
        return proposals;
    }
}
