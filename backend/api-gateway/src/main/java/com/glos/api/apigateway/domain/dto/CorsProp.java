package com.glos.api.apigateway.domain.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "cors")
public class CorsProp {

    private List<String> origins;

    public CorsProp() {
    }

    public CorsProp(List<String> origins) {
        this.origins = origins;
    }

    public List<String> getOrigins() {
        return origins;
    }

    public void setOrigins(List<String> origins) {
        this.origins = origins;
    }
}
