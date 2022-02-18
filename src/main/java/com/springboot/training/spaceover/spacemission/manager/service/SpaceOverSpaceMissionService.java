package com.springboot.training.spaceover.spacemission.manager.service;

import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.ENTITY_NOT_FOUND_MSG;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.SPACE_MISSION;

import com.springboot.training.spaceover.spacemission.manager.domain.model.SpaceMission;
import com.springboot.training.spaceover.spacemission.manager.repository.SpaceMissionRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpaceOverSpaceMissionService implements SpaceMissionService {

    private final SpaceMissionRepository spaceMissionRepository;

    private final SpaceShipClient spaceShipClient;

    @Override
    //LT3.3-Include request pagination
    //LT3.4-Include example matching
    public Page<SpaceMission> findAll(SpaceMission entitySample, Pageable pageRequest) {
        return null;
    }

    @Override
    public List<SpaceMission> findAll() {
        return spaceMissionRepository.findAll();
    }

    @Override
    public SpaceMission findBydId(Long id) {
        return spaceMissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MSG, SPACE_MISSION, id)));
    }

    @Override
    //LT3.2-Modify save method to be transactional
    public SpaceMission save(SpaceMission entity) {
        entity = spaceMissionRepository.save(entity);
        spaceShipClient.findBydId(entity.getSpaceShipId());
        return entity;
    }

    @Override
    public SpaceMission update(SpaceMission entity) {
        spaceShipClient.findBydId(entity.getSpaceShipId());
        return spaceMissionRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        spaceMissionRepository.deleteById(id);
    }
}
