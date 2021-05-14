package com.springboot.training.spaceover.spacemission.manager.configuration;

import com.springboot.training.spaceover.spacemission.manager.service.RestTemplateSpaceShipClient;
import com.springboot.training.spaceover.spacemission.manager.service.SpaceShipClient;
import com.springboot.training.spaceover.spacemission.manager.service.WebClientSpaceShipClient;
import com.springboot.training.spaceover.spacemission.manager.utils.properties.SpaceMissionManagerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SpaceShipClientConfiguration {

    @Bean
    @ConditionalOnProperty(name = "spaceship-manager.client.provider", havingValue = "rest-template", matchIfMissing = true)
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    @ConditionalOnProperty(name = "spaceship-manager.client.provider", havingValue = "rest-template", matchIfMissing = true)
    public SpaceShipClient restTemplateSpaceShipClient(final RestTemplate restTemplate, final SpaceMissionManagerProperties properties) {
        return new RestTemplateSpaceShipClient(restTemplate, properties);
    }

    @Bean
    @ConditionalOnProperty(name = "spaceship-manager.client.provider", havingValue = "web-client")
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    @ConditionalOnProperty(name = "spaceship-manager.client.provider", havingValue = "web-client")
    public SpaceShipClient webClientSpaceShipClient(final WebClient.Builder webClientBuilder, final SpaceMissionManagerProperties spaceMissionManagerProperties) {
        return new WebClientSpaceShipClient(webClientBuilder, spaceMissionManagerProperties);
    }

}
