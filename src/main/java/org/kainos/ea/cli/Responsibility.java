package org.kainos.ea.cli;

public class Responsibility {
    private int responsibilityId;
    private String responsibilityDesc;

    public Responsibility(int responsibilityId, String responsibilityDesc) {
        this.responsibilityId = responsibilityId;
        this.responsibilityDesc = responsibilityDesc;
    }

    public int getResponsibilityId() {
        return responsibilityId;
    }

    public void setResponsibilityId(int responsibilityId) {
        this.responsibilityId = responsibilityId;
    }

    public String getResponsibilityDesc() {
        return responsibilityDesc;
    }

    public void setResponsibilityDesc(String responsibilityDesc) {
        this.responsibilityDesc = responsibilityDesc;
    }
}

