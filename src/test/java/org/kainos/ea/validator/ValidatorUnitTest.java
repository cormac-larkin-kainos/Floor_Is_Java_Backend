package org.kainos.ea.validator;
import org.junit.jupiter.api.Test;
import org.kainos.ea.api.JobService;
import org.kainos.ea.cli.JobRequest;
import org.kainos.ea.core.JobValidator;

import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class ValidatorUnitTest {
        @Test
        public void testValidJobRequest() {
            JobRequest validJob = mock(JobRequest.class);
            when(validJob.getTitle()).thenReturn("Valid Title");
            when(validJob.getCapabilityID()).thenReturn(3);
            when(validJob.getJobSpec()).thenReturn("Valid Job Spec");
            when(validJob.getJobURL()).thenReturn("http://validurl.com");
            when(validJob.getJobBandID()).thenReturn(2);

            JobValidator validator = new JobValidator();
            assertNull(validator.isValid(validJob));
        }

    //Negative testing each property

    //JobTitle
    @Test
    public void testEmptyJobTitle() {
        JobRequest emptyJobTitle = mock(JobRequest.class);
        when(emptyJobTitle.getTitle()).thenReturn(""); // Empty title

        JobValidator validator = new JobValidator();
        assertEquals("No job title entered", validator.isValid(emptyJobTitle));
    }

    @Test
    public void testTooBigJobTitle() {
        JobRequest jobTitleTooBig = mock(JobRequest.class);
        when(jobTitleTooBig.getTitle()).thenReturn("MyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitleMyHugeJobTitle"); // Empty title

        JobValidator validator = new JobValidator();
        assertEquals("Job title is too long", validator.isValid(jobTitleTooBig));
    }

    //Capability
    @Test
    public void testCapabilityIDNegative() {
        JobRequest invalidCapabilityID = mock(JobRequest.class);

        when(invalidCapabilityID.getTitle()).thenReturn("Valid Title");
        when(invalidCapabilityID.getCapabilityID()).thenReturn(0);
        when(invalidCapabilityID.getJobSpec()).thenReturn("Valid Job Spec");
        when(invalidCapabilityID.getJobURL()).thenReturn("http://validurl.com");
        when(invalidCapabilityID.getJobBandID()).thenReturn(2);

        JobValidator validator = new JobValidator();
        assertEquals("CapabilityID is less than 1", validator.isValid(invalidCapabilityID));
    }

    @Test
    public void testCapabilityIDTooBig() {
        JobRequest invalidCapabilityID = mock(JobRequest.class);

        when(invalidCapabilityID.getTitle()).thenReturn("Valid Title");
        when(invalidCapabilityID.getCapabilityID()).thenReturn(66000);
        when(invalidCapabilityID.getJobSpec()).thenReturn("Valid Job Spec");
        when(invalidCapabilityID.getJobURL()).thenReturn("http://validurl.com");
        when(invalidCapabilityID.getJobBandID()).thenReturn(1);

        JobValidator validator = new JobValidator();
        assertEquals("CapabilityID is greater than 65,535", validator.isValid(invalidCapabilityID));
    }

    // Job Spec
    @Test
    public void testJobSpecTooLong() {
        JobRequest jobSpecTooLong = mock(JobRequest.class);

        when(jobSpecTooLong.getTitle()).thenReturn("Valid Title");
        when(jobSpecTooLong.getCapabilityID()).thenReturn(1);
        when(jobSpecTooLong.getJobSpec()).thenReturn("Invalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job Spec");
        when(jobSpecTooLong.getJobURL()).thenReturn("http://validurl.com");
        when(jobSpecTooLong.getJobBandID()).thenReturn(2);

        JobValidator validator = new JobValidator();
        assertEquals("Job spec summary is too long", validator.isValid(jobSpecTooLong));
    }

    @Test
    public void testJobSpecEmpty() {
        JobRequest jobSpecEmpty = mock(JobRequest.class);

        when(jobSpecEmpty.getTitle()).thenReturn("Valid Title");
        when(jobSpecEmpty.getCapabilityID()).thenReturn(1);
        when(jobSpecEmpty.getJobSpec()).thenReturn("");
        when(jobSpecEmpty.getJobURL()).thenReturn("http://validurl.com");
        when(jobSpecEmpty.getJobBandID()).thenReturn(2);

        JobValidator validator = new JobValidator();
        assertEquals("Job spec summary is null or less than 0 characters", validator.isValid(jobSpecEmpty));
    }

    // Job URL
    @Test
    public void testURLEmpty() {
        JobRequest jobURLEmpty = mock(JobRequest.class);

        when(jobURLEmpty.getTitle()).thenReturn("Valid Title");
        when(jobURLEmpty.getCapabilityID()).thenReturn(1);
        when(jobURLEmpty.getJobSpec()).thenReturn("Valid Job Spec");
        when(jobURLEmpty.getJobURL()).thenReturn("");
        when(jobURLEmpty.getJobBandID()).thenReturn(2);

        JobValidator validator = new JobValidator();
        assertEquals("No URL entered", validator.isValid(jobURLEmpty));
    }

    @Test
    public void testURLTooLong() {
        JobRequest jobURLTooLong = mock(JobRequest.class);

        when(jobURLTooLong.getTitle()).thenReturn("Valid Title");
        when(jobURLTooLong.getCapabilityID()).thenReturn(1);
        when(jobURLTooLong.getJobSpec()).thenReturn("Valid Job Spec");
        when(jobURLTooLong.getJobURL()).thenReturn("Invalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job SpecInvalid Job Spec");
        when(jobURLTooLong.getJobBandID()).thenReturn(2);

        JobValidator validator = new JobValidator();
        assertEquals("URL is too long", validator.isValid(jobURLTooLong));
    }

    @Test
    public void testJobBandIDNegative() {
        JobRequest invalidJobBandID = mock(JobRequest.class);

        when(invalidJobBandID.getTitle()).thenReturn("Valid Title");
        when(invalidJobBandID.getCapabilityID()).thenReturn(1);
        when(invalidJobBandID.getJobSpec()).thenReturn("Valid Job Spec");
        when(invalidJobBandID.getJobURL()).thenReturn("http://validurl.com");
        when(invalidJobBandID.getJobBandID()).thenReturn(0);

        JobValidator validator = new JobValidator();
        assertEquals("JobBandID is less than 1", validator.isValid(invalidJobBandID));
    }

    @Test
    public void testJobBandIDTooBig() {
        JobRequest invalidJobBandID = mock(JobRequest.class);

        when(invalidJobBandID.getTitle()).thenReturn("Valid Title");
        when(invalidJobBandID.getCapabilityID()).thenReturn(1);
        when(invalidJobBandID.getJobSpec()).thenReturn("Valid Job Spec");
        when(invalidJobBandID.getJobURL()).thenReturn("http://validurl.com");
        when(invalidJobBandID.getJobBandID()).thenReturn(66000);

        JobValidator validator = new JobValidator();
        assertEquals("JobBandID is greater than 65,535", validator.isValid(invalidJobBandID));
    }
}






