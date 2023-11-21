package org.kainos.ea.core;

import org.kainos.ea.cli.JobRequest;

public class JobValidator {

    public String isValid(JobRequest job){


        //JobTitle
        if (job.getTitle().length() > 255){
            return "Job title is too long";
        }

        if (job.getTitle().isEmpty()){
            return "No job title entered";
        }


        //Capability - Need to pass in ID?
        if(job.getCapabilityID() < 1)
        {
            return "CapabilityID is less than 1";
        }
        if (job.getCapabilityID()>=1)
        {
            return null;
        }


        //Job Spec
        if (job.getJobSpec().length() > 255){
            return "Job spec summary is too long";
        }
        if (job.getJobSpec().length() <= 0){
            return "Job spec summary is null or less than 0 characters";
        }


        //Job URL
        if (job.getJobURL().isEmpty()){
            return "No URL entered";
        }
        if (job.getJobURL().length() > 255){
            return "URL is too long";
        }

        //Job band
        if (job.getJobBandID() < 1){
            return "JobBandID is less than 1";
        }
        if (job.getJobBandID()>=1){
            return null;
        }


        return null;
    }

}
