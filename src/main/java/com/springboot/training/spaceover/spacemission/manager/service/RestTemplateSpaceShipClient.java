package com.springboot.training.spaceover.spacemission.manager.service;

import com.springboot.training.spaceover.spacemission.manager.domain.response.inbound.GetSpaceShipResponse;
import com.springboot.training.spaceover.spacemission.manager.utils.properties.SpaceMissionManagerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class RestTemplateSpaceShipClient implements SpaceShipClient {

    private final RestTemplate restTemplate;

    private final SpaceMissionManagerProperties spaceMissionManagerProperties;

    @Override
    public GetSpaceShipResponse findBydId(Long id) {
        return null; //LT1.1-Implement SpaceShipClient using RestTemplate
    }
}
