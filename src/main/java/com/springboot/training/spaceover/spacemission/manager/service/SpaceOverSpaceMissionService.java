package com.springboot.training.spaceover.spacemission.manager.service;

import com.springboot.training.spaceover.spacemission.manager.domain.model.SpaceMission;
import com.springboot.training.spaceover.spacemission.manager.repository.SpaceMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.ENTITY_NOT_FOUND_MSG;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.SPACE_MISSION;

@Service
@RequiredArgsConstructor
public class SpaceOverSpaceMissionService implements SpaceMissionService {

    private final SpaceMissionRepository spaceMissionRepository;

    private final SpaceShipClient spaceShipClient;

    @Override
    public Page<SpaceMission> findAll(SpaceMission entitySample, Pageable pageRequest) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("spaceShipId", ExampleMatcher.GenericPropertyMatchers.exact());
        return spaceMissionRepository.findAll(Example.of(entitySample, exampleMatcher), pageRequest);
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
    public SpaceMission save(SpaceMission entity) {
        spaceShipClient.findBydId(entity.getSpaceShipId());
        return spaceMissionRepository.save(entity);
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
