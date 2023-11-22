package org.kainos.ea.integration;

import freemarker.core.JSONOutputFormat;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.glassfish.jersey.jaxb.internal.XmlJaxbElementProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.FloorIsJavaApplication;
import org.kainos.ea.FloorIsJavaConfiguration;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.api.IAuthService;
import org.kainos.ea.auth.JwtGenerator;
import org.kainos.ea.auth.JwtValidator;
import org.kainos.ea.cli.Login;
import org.kainos.ea.cli.UserRole;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.IAuthSource;
import org.kainos.ea.resources.AuthorisationFilter;
import org.mockito.Mockito;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.beans.Transient;


@ExtendWith(DropwizardExtensionsSupport.class)
public class AuthorisationFilterIntegrationTest {

    static final DropwizardAppExtension<FloorIsJavaConfiguration> APP = new DropwizardAppExtension<>(
            FloorIsJavaApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    JwtGenerator generator = new JwtGenerator();

    private static final String TEST_VALID_USERNAME = System.getenv("TEST_VALID_USERNAME");
    private static final String TEST_VALID_PASSWORD = System.getenv("TEST_VALID_PASSWORD");


    private String getJWT() {
        System.out.println(TEST_VALID_PASSWORD);
        System.out.println(TEST_VALID_USERNAME);
        if(TEST_VALID_USERNAME == null || TEST_VALID_PASSWORD == null){
            throw new IllegalArgumentException("Test credential environment variables not set!");
        }
        Login credentials = new Login(TEST_VALID_USERNAME,TEST_VALID_PASSWORD);
        Response response = APP.client().target("http://localhost:8080/api/login").request().post(Entity.json(credentials));

        return response.readEntity(String.class);
    }

    @Test
    void shouldReturn401_whenNoAuthHeaderPassed() {
        Response response = APP.client().target("http://localhost:8080/api/jobs")
                .request()
                .get();

        Assertions.assertEquals(401,response.getStatus());
    }

    @Test
    void shouldReturn401_whenBadTokenPassed() {
        Response response = APP.client()
                .target("http://localhost:8080/api/jobs")
                .request()
                .header("Authorization","Bearer " + generator.generateJwt("Test", UserRole.User))
                .get();
        Assertions.assertEquals(400,response.getStatus());
    }

    @Test
    void shouldReturn401_whenNoTokenPassed() {
        Response response = APP.client()
                .target("http://localhost:8080/api/jobs")
                .request()
                .header("Authorization","")
                .get();
        Assertions.assertEquals(401,response.getStatus());
    }
}
