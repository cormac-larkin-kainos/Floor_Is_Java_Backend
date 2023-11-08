package org.kainos.ea;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.kainos.ea.api.JobService;
import org.kainos.ea.resources.JobController;
import org.kainos.ea.resources.TestController;

public class floorIsJavaApplication extends Application<floorIsJavaConfiguration> {

    public static void main(final String[] args) throws Exception {
        new floorIsJavaApplication().run(args);
    }

    @Override
    public String getName() {
        return "floorIsJava";
    }

    @Override
    public void initialize(final Bootstrap<floorIsJavaConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<floorIsJavaConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(floorIsJavaConfiguration configuration) {
                return configuration.getSwagger();
            }
        });
    }

    @Override
    public void run(final floorIsJavaConfiguration configuration,
                    final Environment environment) {
       environment.jersey().register(new TestController());
       environment.jersey().register(new JobController());
    }

}
