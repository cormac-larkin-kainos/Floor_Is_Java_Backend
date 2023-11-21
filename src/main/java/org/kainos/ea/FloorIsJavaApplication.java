package org.kainos.ea;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.SwaggerDefinition;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.api.IAuthService;
import org.kainos.ea.auth.JwtGenerator;
import org.kainos.ea.auth.JwtValidator;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.resources.AuthorisationFilter;
import org.kainos.ea.resources.AuthController;
import org.kainos.ea.resources.JobController;

import java.security.NoSuchAlgorithmException;

@SwaggerDefinition(
    securityDefinition = @SecurityDefinition(
        apiKeyAuthDefinitions = {
            @ApiKeyAuthDefinition(
                key = "basicAuth",
                name = "Authorization",
                in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER)
        }
    )
)
public class FloorIsJavaApplication extends Application<FloorIsJavaConfiguration> {

    private final IAuthService authService = new AuthService(new AuthDao(), new JwtGenerator(),new JwtValidator());

    public static void main(final String[] args) throws Exception {
        new FloorIsJavaApplication().run(args);
    }

    @Override
    public String getName() {
        return "floorIsJava";
    }

    @Override
    public void initialize(final Bootstrap<FloorIsJavaConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<FloorIsJavaConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(FloorIsJavaConfiguration configuration) {
                return configuration.getSwagger();
            }
        });
    }

    @Override
    public void run(final FloorIsJavaConfiguration configuration,
                    final Environment environment) throws NoSuchAlgorithmException {
        environment.jersey().register(new AuthorisationFilter(authService));
        environment.jersey().register(new AuthController(authService));

        //All new services go here
        environment.jersey().register(new JobController());
    }

}
