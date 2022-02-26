package com.springboot.training.spaceover.spacemission.manager.service;

import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.FRONT_SLASH_DELIMITER;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.SPACESHIPS;

import com.springboot.training.spaceover.spacemission.manager.domain.response.inbound.GetSpaceShipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class WebClientSpaceShipClient implements SpaceShipClient {

    private final WebClient webClient;

    @Override
    public GetSpaceShipResponse findBydId(Long id) {
        return webClient.get()
            .uri(String.join(FRONT_SLASH_DELIMITER, SPACESHIPS, String.valueOf(id)))
            .retrieve()
            .bodyToMono(GetSpaceShipResponse.class)
            .block(); //LT1.1-Implement SpaceShipClient using RestTemplate
    }
}
