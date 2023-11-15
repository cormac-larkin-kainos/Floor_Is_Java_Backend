package org.kainos.ea.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kainos.ea.FloorIsJavaApplication;
import org.kainos.ea.FloorIsJavaConfiguration;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.cli.Login;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@ExtendWith(DropwizardExtensionsSupport.class)
public class AuthControllerIntegrationTest {

    static final DropwizardAppExtension<FloorIsJavaConfiguration> APP = new DropwizardAppExtension<>(
            FloorIsJavaApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );
    @Test
    void loginWithValidLogin_ShouldReturnLogin() {
        Login testLogin = new Login("admin", "admin");

        Response response = APP.client().target("http://localhost:8080/api/login")
                .request()
                .post(Entity.entity(testLogin, MediaType.APPLICATION_JSON));

        Assertions.assertEquals(200, response.getStatus());
    }
    @Test
    void loginWithinValidLogin_ShouldReturnUnauthorisedError() {
        Login testLogin = new Login("notAdmin", "admin");

        Response response = APP.client().target("http://localhost:8080/api/login")
                .request()
                .post(Entity.entity(testLogin, MediaType.APPLICATION_JSON));

        Assertions.assertEquals(401, response.getStatus());
    }


}

