package com.springboot.training.spaceover.spacemission.manager.service;

import com.springboot.training.spaceover.spacemission.manager.domain.response.inbound.GetSpaceShipResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OpenFeignFallbackSpaceShipClient implements OpenFeignSpaceShipClient{

	@Override
	public GetSpaceShipResponse findBydId(Long id) {
		log.error("Failed to retrieve spaceship with id {}", id);
		return null;
	}
}
