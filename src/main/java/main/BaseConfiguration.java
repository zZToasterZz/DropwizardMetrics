package main;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class BaseConfiguration extends Configuration {
    @NotNull
    private final String applicationName;

    @JsonCreator
    public BaseConfiguration(@JsonProperty("applicationName") final String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationName() {
        return applicationName;
    }
}
