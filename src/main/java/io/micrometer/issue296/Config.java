package io.micrometer.issue296;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor;
import jakarta.annotation.PostConstruct;

@Configuration
class Config {

    @Autowired
    ObservationRegistry registry;

    @PostConstruct
    void setup() {
        ObservationThreadLocalAccessor.getInstance().setObservationRegistry(registry);
    }


}
