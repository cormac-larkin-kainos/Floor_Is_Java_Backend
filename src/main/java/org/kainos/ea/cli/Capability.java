package org.kainos.ea.cli;

public class Capability {

    private int capabilityID;
    private String name;

    public Capability(int capabilityID, String name) {
        this.capabilityID = capabilityID;
        this.name = name;
    }

    public int getCapabilityID() {
        return capabilityID;
    }

    public void setCapabilityID(int capabilityID) {
        this.capabilityID = capabilityID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
