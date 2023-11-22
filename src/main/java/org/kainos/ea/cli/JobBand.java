package org.kainos.ea.cli;

public class JobBand {

    private int jobBandID;
    private String jobBandName;

    public JobBand(int jobBandID, String jobBandName) {
        this.jobBandID = jobBandID;
        this.jobBandName = jobBandName;
    }

    public int getJobBandID() {
        return jobBandID;
    }

    public void setJobBandID(int jobBandID) {
        this.jobBandID = jobBandID;
    }

    public String getJobBandName() {
        return jobBandName;
    }

    public void setJobBandName(String jobBandName) {
        this.jobBandName = jobBandName;
    }

}
