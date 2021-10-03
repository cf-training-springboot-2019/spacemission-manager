package com.springboot.training.spaceover.spacemission.manager.service;

import com.springboot.training.spaceover.spacemission.manager.domain.model.SpaceShipTotalRevenue;
import com.springboot.training.spaceover.spacemission.manager.domain.model.SpaceMission;

import java.util.List;

public interface SpaceMissionService extends CrudService<SpaceMission> {

    List<SpaceShipTotalRevenue> calculateSpaceShipsTotalRevenue();
}