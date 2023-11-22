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
            JobRequest validJob = new JobRequest(
                    "Valid Title",
                    1,
                    "Valid Job Spec",
                    "http://validurl.com",
                    1);

            JobValidator validator = new JobValidator();
            assertNull(validator.isValid(validJob));
        }

    //Negative testing each property

    //JobTitle
    @Test
    public void testEmptyJobTitle() {
        JobRequest emptyJobTitle = new JobRequest(
                "",
                1,
                "Valid Job Spec",
                "http://validurl.com",
                1);

        JobValidator validator = new JobValidator();
        assertEquals("No job title entered", validator.isValid(emptyJobTitle));
    }

    @Test
    public void testTooBigJobTitle() {
        JobRequest jobTitleTooBig = new JobRequest(
                "TitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBigTitleTooBig",
                1,
                "Valid Job Spec",
                "http://validurl.com",
                1);

        JobValidator validator = new JobValidator();
        assertEquals("Job title is too long", validator.isValid(jobTitleTooBig));
    }

    //Capability
    @Test
    public void testCapabilityIDNegative() {
        JobRequest invalidCapabilityID = new JobRequest(
                "Valid Title",
                -1,
                "Valid Job Spec",
                "http://validurl.com",
                1);

        JobValidator validator = new JobValidator();
        assertEquals("CapabilityID is less than 1", validator.isValid(invalidCapabilityID));
    }

    @Test
    public void testCapabilityIDTooBig() {
        JobRequest invalidCapabilityID = new JobRequest(
                "Valid Title",
                66000,
                "Valid Job Spec",
                "http://validurl.com",
                1);

        JobValidator validator = new JobValidator();
        assertEquals("CapabilityID is greater than 65,535", validator.isValid(invalidCapabilityID));
    }

    // Job Spec
    @Test
    public void testJobSpecTooLong() {
        JobRequest jobSpecTooLong = new JobRequest(
                "Valid Title",
                1,
                "InvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpecInvalidJobSpec",
                "http://validurl.com",
                1);

        JobValidator validator = new JobValidator();
        assertEquals("Job spec summary is too long", validator.isValid(jobSpecTooLong));
    }

    @Test
    public void testJobSpecEmpty() {
        JobRequest jobSpecEmpty = new JobRequest(
                "Valid Title",
                1,
                "",
                "http://validurl.com",
                1);

        JobValidator validator = new JobValidator();
        assertEquals("Job spec summary is null or less than 0 characters", validator.isValid(jobSpecEmpty));
    }

    // Job URL
    @Test
    public void testURLEmpty() {
        JobRequest jobURLEmpty = new JobRequest(
                "Valid Title",
                1,
                "Valid Job Spec",
                "",
                1);

        JobValidator validator = new JobValidator();
        assertEquals("No URL entered", validator.isValid(jobURLEmpty));
    }

    @Test
    public void testURLTooLong() {
        JobRequest jobURLTooLong = new JobRequest(
                "Valid Title",
                1,
                "Valid Job Spec",
                "InvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.comInvalidURL@URL.com",
                1);

        JobValidator validator = new JobValidator();
        assertEquals("URL is too long", validator.isValid(jobURLTooLong));
    }

    @Test
    public void testJobBandIDNegative() {
        JobRequest invalidJobBandID = new JobRequest(
                "Valid Title",
                1,
                "ValidJObSpec",
                "http://validurl.com",
                -1);

        JobValidator validator = new JobValidator();
        assertEquals("JobBandID is less than 1", validator.isValid(invalidJobBandID));
    }

    @Test
    public void testJobBandIDTooBig() {
        JobRequest invalidJobBandID = new JobRequest(
                "Valid Title",
                1,
                "ValidJObSpec",
                "http://validurl.com",
                66000);

        JobValidator validator = new JobValidator();
        assertEquals("JobBandID is greater than 65,535", validator.isValid(invalidJobBandID));
    }
}






