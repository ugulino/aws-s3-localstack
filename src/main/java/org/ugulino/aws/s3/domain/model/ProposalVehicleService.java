package org.ugulino.aws.s3.domain.model;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.ugulino.aws.s3.infrastructure.BucketS3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ProposalVehicleService {
    private static final BucketS3 bucket = new BucketS3();

    public ProposalVehicleService() {
    }

    public List<ProposalVehicle> loadProposalsVehicle(S3Event bucketEvent) {
        List<ProposalVehicle> proposals = new ArrayList<>();
        bucket.getObjectStreams(bucketEvent).forEach(
                inputStream -> {
                    try(final BufferedReader br = new BufferedReader(
                            new InputStreamReader(inputStream, StandardCharsets.UTF_8))){
                                br.lines()
                                        .forEach(proposalCode -> proposals
                                                .add(new ProposalVehicle(proposalCode)));
                    } catch (IOException e){
                        throw new AmazonS3Exception("Error occurred in Lambda:" + e.getMessage());
                    }
                }
        );
        return proposals;
    }
}