package org.kainos.ea.cli;

public class Job {

    private int jobID;
    private String title;

    public Job(int jobID, String title) {
        this.jobID = jobID;
        this.title = title;
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
}
