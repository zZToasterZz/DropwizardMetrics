package main;

import com.codahale.metrics.servlets.AdminServlet;
import controllers.HomeController;
import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import io.prometheus.client.exporter.MetricsServlet;

public class DropwizardMetrics extends Application<BaseConfiguration> {
    public static void main(String[] args) throws Exception {
        new DropwizardMetrics().run("server", "config.yml");
    }

    @Override
    public void run(final BaseConfiguration baseConfiguration, final Environment environment) {
        final CollectorRegistry collectorRegistry = new CollectorRegistry();
        collectorRegistry.register(new DropwizardExports(environment.metrics()));

        environment.jersey().register(new HomeController(baseConfiguration.getApplicationName()));
        environment.jersey().register(AdminServlet.class);

        environment.servlets().addServlet("PrometheusEndpoint", new MetricsServlet(collectorRegistry))
                .addMapping("/prometheus_metrics");
    }

    @Override
    public void initialize(Bootstrap<BaseConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        super.initialize(bootstrap);
    }
}
