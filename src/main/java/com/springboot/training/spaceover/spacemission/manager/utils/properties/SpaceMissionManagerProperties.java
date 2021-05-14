package com.springboot.training.spaceover.spacemission.manager.utils.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SpaceMissionManagerProperties {

    @Value("${spring.application.name:spacemission-manager}")
    private String applicationName;

    @Value("${server.servlet.context-path:#{null}}")
    private String servletContextPath;

    @Value("${spaceship-manager.api.url:http://localhost:8080}")
    private String spaceshipManagerBaseUrl;

    @Value("${open-api.header.pagination.enabled:false}")
    private boolean openApiHeaderPaginationEnabled;

}
