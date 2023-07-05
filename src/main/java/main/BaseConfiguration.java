package main;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;
import jakarta.validation.constraints.NotNull;

public class BaseConfiguration extends Configuration {
    @NotNull
    private final String testConfigValue;

    @JsonCreator
    public BaseConfiguration(@JsonProperty("testConfigValue") final String testConfigValue) {
        this.testConfigValue = testConfigValue;
    }

    public String getTestConfigValue() {
        return testConfigValue;
    }
}
