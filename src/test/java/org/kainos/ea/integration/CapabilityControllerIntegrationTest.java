package org.kainos.ea.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.FloorIsJavaApplication;
import org.kainos.ea.FloorIsJavaConfiguration;
import org.kainos.ea.cli.Capability;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ExtendWith(DropwizardExtensionsSupport.class)
public class CapabilityControllerIntegrationTest {

    static final DropwizardAppExtension<FloorIsJavaConfiguration> APP = new DropwizardAppExtension<>(
            FloorIsJavaApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    void getAllCapabilities_ShouldReturnListOfCapabilities() {
        Response response = APP.client().target("http://localhost:8080/api/capabilities")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertNotNull(response.getEntity());

        // Check that the all capabilities in the response contain the correct properties
        List<Capability> allCapabilities = response.readEntity(List.class);

        Assertions.assertFalse(allCapabilities.isEmpty());
        }

    }

