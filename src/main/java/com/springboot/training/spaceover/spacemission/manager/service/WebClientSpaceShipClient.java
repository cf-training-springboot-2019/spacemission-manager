package com.springboot.training.spaceover.spacemission.manager.service;

import com.springboot.training.spaceover.spacemission.manager.domain.response.inbound.GetSpaceShipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.SPACESHIPS;


@RequiredArgsConstructor
public class WebClientSpaceShipClient implements SpaceShipClient {

    private final WebClient webClient;

    @Override
    public GetSpaceShipResponse findBydId(Long id) {
        return webClient.get()
                .uri(SPACESHIPS)
                .retrieve()
                .bodyToMono(GetSpaceShipResponse.class)
                .block();
    }
}
