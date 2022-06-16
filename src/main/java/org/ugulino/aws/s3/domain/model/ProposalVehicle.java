package org.ugulino.aws.s3.domain.model;

public class ProposalVehicle {
    private String proposalCode;

    public ProposalVehicle(){

    }

    public ProposalVehicle(String proposalCode){
        this.proposalCode =proposalCode;
    }

    public String getProposalCode() {
        return this.proposalCode;
    }

    public void setProposalCode(String proposalCode) {
        this.proposalCode = proposalCode;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
