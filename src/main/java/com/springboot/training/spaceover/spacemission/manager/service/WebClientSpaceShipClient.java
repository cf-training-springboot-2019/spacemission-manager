package com.springboot.training.spaceover.spacemission.manager.service;

import com.springboot.training.spaceover.spacemission.manager.domain.response.inbound.GetSpaceShipResponse;
import com.springboot.training.spaceover.spacemission.manager.utils.properties.SpaceMissionManagerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.SPACESHIPS;


@RequiredArgsConstructor
public class WebClientSpaceShipClient implements SpaceShipClient {

    private final WebClient.Builder webClientBuilder;

    private final SpaceMissionManagerProperties spaceMissionManagerProperties;


    @Override
    public GetSpaceShipResponse findBydId(Long id) {
        WebClient webClient = webClientBuilder.baseUrl(spaceMissionManagerProperties.getSpaceshipManagerBaseUrl())
                .build();

        return webClient.get()
                .uri(SPACESHIPS)
                .retrieve()
                .bodyToMono(GetSpaceShipResponse.class)
                .block();
    }
}
