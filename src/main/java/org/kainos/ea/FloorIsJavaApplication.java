package org.kainos.ea;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.kainos.ea.api.JobService;
import org.kainos.ea.cli.Job;
import org.kainos.ea.core.JobValidator;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobDao;
import org.kainos.ea.resources.CapabilityController;
import org.kainos.ea.resources.JobBandController;
import org.kainos.ea.resources.JobController;

public class FloorIsJavaApplication extends Application<FloorIsJavaConfiguration> {

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
                    final Environment environment) {
        JobService jobService = new JobService(new JobDao(),new DatabaseConnector(),new JobValidator());
        environment.jersey().register(new JobController(jobService));
        environment.jersey().register(new CapabilityController());
        environment.jersey().register(new JobBandController());
    }

}
