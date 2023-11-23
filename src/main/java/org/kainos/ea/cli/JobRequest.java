package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JobRequest {

    private String title;
    private int capabilityID;
    private String jobSpec;
    private String jobURL;
    private int jobBandID;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCapabilityID() {
        return capabilityID;
    }

    public void setCapabilityID(int capabilityID) {
        this.capabilityID = capabilityID;
    }

    public String getJobSpec() {
        return jobSpec;
    }

    public void setJobSpec(String jobSpec) {
        this.jobSpec = jobSpec;
    }

    public String getJobURL() {
        return jobURL;
    }

    public void setJobURL(String jobURL) {
        this.jobURL = jobURL;
    }

    public int getJobBandID() {
        return jobBandID;
    }

    public void setJobBandID(int jobBandID) {
        this.jobBandID = jobBandID;
    }

    @JsonCreator
    public JobRequest(
            @JsonProperty("title") String title,
            @JsonProperty("capabilityID") int capabilityID,
            @JsonProperty("jobSpec") String jobSpec,
            @JsonProperty("jobURL") String jobURL,
            @JsonProperty("jobBandID") int jobBandID) {

        this.title = title;
        this.capabilityID = capabilityID;
        this.jobSpec = jobSpec;
        this.jobURL = jobURL;
        this.jobBandID = jobBandID;
    }

}