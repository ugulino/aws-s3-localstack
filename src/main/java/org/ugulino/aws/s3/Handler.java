package org.ugulino.aws.s3;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import org.ugulino.aws.s3.application.ProposalFacade;

public class Handler implements RequestHandler<S3Event, Boolean> {
    private static final String QUEUE_NAME = "sqs_vehicle.fifo";
    private static final ProposalFacade proposalFacade = new ProposalFacade();

    @Override
    public Boolean handleRequest(S3Event input, Context context) {
        final LambdaLogger logger = context.getLogger();

        try {
            proposalFacade.transferProposalVehicles(input, QUEUE_NAME);
            return true;
        } catch (Exception e) {
            logger.log("Error occurred in Lambda:" + e.getMessage());
            return false;
        }
    }
}
