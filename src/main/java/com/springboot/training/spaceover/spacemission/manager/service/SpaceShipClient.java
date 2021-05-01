package com.springboot.training.spaceover.spacemission.manager.service;

import com.springboot.training.spaceover.spacemission.manager.domain.response.inbound.GetSpaceShipResponse;

public interface SpaceShipClient<T> {

	GetSpaceShipResponse findBydId(Long id);

}