package controllers;

import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.ResponseMetered;
import com.codahale.metrics.annotation.Timed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/greet")
public class HomeController {
    private final String value;

    public HomeController(final String value) {
        this.value = value;
    }

    @GET
    @Metered(name = "sayHello.Meter")
    @Timed(name = "sayHello.Time")
    @ResponseMetered(name = "sayHello.ResponseMeter")
    public Response sayHello() throws InterruptedException {
        final Response response = Response.ok().entity(value).build();
        Thread.sleep(3000);
        return response;
    }
}
