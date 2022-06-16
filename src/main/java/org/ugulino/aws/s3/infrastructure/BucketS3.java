package org.ugulino.aws.s3.infrastructure;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.util.ArrayList;
import java.util.List;

public class BucketS3 {
    private static final AmazonS3 s3Client = AmazonS3Client.builder()
            .withCredentials(new DefaultAWSCredentialsProviderChain())
            .build();

    public BucketS3() {

    }

    public List<S3ObjectInputStream> getObjectStreams(S3Event bucketEvent) {
        List<S3ObjectInputStream> objectInputStreams = new ArrayList<>();
        for(S3EventNotification.S3EventNotificationRecord record: bucketEvent.getRecords()){
            String bucketName = record.getS3().getBucket().getName();
            String objectKey = record.getS3().getObject().getKey();

            S3Object s3Object = s3Client.getObject(bucketName, objectKey);
            S3ObjectInputStream inputStream = s3Object.getObjectContent();
            objectInputStreams.add(inputStream);
        }
        return objectInputStreams;
    }
}