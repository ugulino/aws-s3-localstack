package org.ugulino.aws.s3.application;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import org.ugulino.aws.s3.infrastructure.QueueSQS;
import org.ugulino.aws.s3.domain.model.ProposalVehicleService;

public class ProposalFacade {
    private static final QueueSQS queue = new QueueSQS();

    public ProposalFacade() {

    }

    public void transferProposalVehicles(S3Event input, String queueName) throws Exception {
        if(input.getRecords().isEmpty()){
            throw new Exception("Nor record found");
        }

        ProposalVehicleService vehicleBucket = new ProposalVehicleService();
        try {
            vehicleBucket.loadProposalsVehicle(input).forEach(
                proposalVehicle -> {
                    queue.sentToQueue(proposalVehicle.getProposalCode(), queueName);
                }
            );

        } catch (Exception e) {
            throw e;
        }
    }
}
