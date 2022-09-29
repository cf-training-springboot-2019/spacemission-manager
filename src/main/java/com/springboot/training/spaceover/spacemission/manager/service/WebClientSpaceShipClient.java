package com.springboot.training.spaceover.spacemission.manager.service;

import com.springboot.training.spaceover.spacemission.manager.domain.response.inbound.GetSpaceShipResponse;
import com.springboot.training.spaceover.spacemission.manager.utils.properties.SpaceMissionManagerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;


@RequiredArgsConstructor
public class WebClientSpaceShipClient implements SpaceShipClient {

    private final WebClient webClient;

    private final SpaceMissionManagerProperties spaceMissionManagerProperties;

    @Override
    public GetSpaceShipResponse findBydId(Long id) {
        return null; //LT1.1-Implement SpaceShipClient using RestTemplate
    }
}
