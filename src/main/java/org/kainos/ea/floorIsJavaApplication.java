package org.kainos.ea;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
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
        // TODO: application initialization
    }

    @Override
    public void run(final floorIsJavaConfiguration configuration,
                    final Environment environment) {
       environment.jersey().register(new TestController());
       environment.jersey().register(new JobController());
    }

}
