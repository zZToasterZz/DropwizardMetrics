package main;

import com.codahale.metrics.MetricRegistry;
import com.izettle.metrics.influxdb.InfluxDbHttpSender;
import com.izettle.metrics.influxdb.InfluxDbReporter;
import com.izettle.metrics.influxdb.InfluxDbSender;
import controllers.HomeController;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.metrics.servlets.AdminServlet;

import java.util.concurrent.TimeUnit;

public class DropwizardMetrics extends Application<BaseConfiguration> {
    public static void main(String[] args) throws Exception {
        new DropwizardMetrics().run("server", "config.yml");
    }

    @Override
    public void run(final BaseConfiguration baseConfiguration, final Environment environment) throws Exception {

        final InfluxDbSender influxDbSender = getInfluxDbSender();
        final MetricRegistry metricRegistry = new MetricRegistry();
        final InfluxDbReporter influxDbReporter = InfluxDbReporter.forRegistry(metricRegistry).build(influxDbSender);

        environment.jersey().register(new HomeController(baseConfiguration.getTestConfigValue()));
        environment.jersey().register(AdminServlet.class);

        influxDbReporter.start(10, TimeUnit.SECONDS);
    }

    private static InfluxDbSender getInfluxDbSender() throws Exception {
        return new InfluxDbHttpSender("http", "localhost", 8086, "dropwizard_bucket",
                "admin:admin123", TimeUnit.MILLISECONDS, 3000, 3000, "");
    }

    @Override
    public void initialize(Bootstrap<BaseConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        super.initialize(bootstrap);
    }
}
