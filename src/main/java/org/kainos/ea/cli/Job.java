package org.kainos.ea.cli;

public class Job {

    private int jobID;
    private String title;
    private String capability;
    private String jobSpec;
    private String jobURL;

    public Job(int jobID, String title, String capability, String jobSpec, String jobURL) {
        this.jobID = jobID;
        this.title = title;
        this.capability = capability;
        this.jobSpec = jobSpec;
        this.jobURL = jobURL;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCapability() {
        return capability;
    }

    public void setCapability(String capability) {
        this.capability = capability;
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



}
