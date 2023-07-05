package main.metrics;

import com.codahale.metrics.health.HealthCheckRegistry;
import io.dropwizard.metrics.servlets.HealthCheckServlet;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class HealthCheckContextListener extends HealthCheckServlet.ContextListener {
    public static final HealthCheckRegistry HEALTH_CHECK_REGISTRY = new HealthCheckRegistry();
    @Override
    protected HealthCheckRegistry getHealthCheckRegistry() {
        return HEALTH_CHECK_REGISTRY;
    }
}
