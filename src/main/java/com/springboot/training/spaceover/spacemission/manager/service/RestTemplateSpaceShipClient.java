package com.springboot.training.spaceover.spacemission.manager.service;

import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.FRONT_SLASH_DELIMITER;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.SPACESHIPS;

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
        String url = String.join(FRONT_SLASH_DELIMITER, spaceMissionManagerProperties.getSpaceshipManagerBaseUrl(),
            SPACESHIPS,
            String.valueOf(id));
        return restTemplate.getForEntity(url, GetSpaceShipResponse.class).getBody(); //LT1.1-Implement SpaceShipClient using RestTemplate
    }
}
