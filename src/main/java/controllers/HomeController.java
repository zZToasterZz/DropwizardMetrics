package controllers;

import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.ResponseMetered;
import com.codahale.metrics.annotation.Timed;
import io.prometheus.client.Counter;
import io.prometheus.client.Summary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/greet")
public class HomeController {
    private final String applicationName;

    private static final Counter httpRequestsTotal = Counter
            .build("http_requests_total", "Total number of HTTP requests")
            .labelNames("path")
            .register();
    private static final Summary requestDuration = Summary.build()
            .name("request_duration_summary")
            .help("Time for HTTP request.")
            .quantile(0.95, 0.01)
            .register();

    public HomeController(final String applicationName) {
        this.applicationName = applicationName;
    }

    @GET
    @Path("/sayhello")
    @Metered(name = "sayhello.Meter")
    @Timed(name = "sayhello.Time")
    @ResponseMetered(name = "sayhello.ResponseMeter")
    public Response sayHello() throws InterruptedException {
        final Summary.Timer timer = requestDuration.startTimer();
        httpRequestsTotal.labels("/sayhello.meter").inc();

        final Response response = Response.ok().entity("Hello from "+applicationName).build();
        Thread.sleep(3000);

        timer.observeDuration();
        return response;
    }

    @GET
    @Path("/saybye")
    @Metered(name = "saybye.Meter")
    @Timed(name = "saybye.Time")
    @ResponseMetered(name = "saybye.ResponseMeter")
    public Response sayBye() {
        final Summary.Timer timer = requestDuration.startTimer();
        httpRequestsTotal.labels("/saybye.meter").inc();

        final Response response = Response.ok().entity("Bye").build();

        timer.observeDuration();
        return response;
    }
}
