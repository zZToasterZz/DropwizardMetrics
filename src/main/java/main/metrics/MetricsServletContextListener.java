package main.metrics;

import com.codahale.metrics.MetricRegistry;
import io.dropwizard.metrics.servlets.MetricsServlet;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class MetricsServletContextListener extends MetricsServlet.ContextListener {
    public static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();
    @Override
    protected MetricRegistry getMetricRegistry() {
        return METRIC_REGISTRY;
    }
}
