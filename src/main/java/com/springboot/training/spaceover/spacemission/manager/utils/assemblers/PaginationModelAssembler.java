package com.springboot.training.spaceover.spacemission.manager.utils.assemblers;

import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.FRONT_SLASH_DELIMITER;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.SPACESHIP;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.SPACESHIPS;

import com.springboot.training.spaceover.spacemission.manager.domain.model.SpaceMission;
import com.springboot.training.spaceover.spacemission.manager.domain.response.outbound.GetSpaceMissionResponse;
import com.springboot.training.spaceover.spacemission.manager.utils.properties.SpaceMissionManagerProperties;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaginationModelAssembler implements RepresentationModelAssembler<SpaceMission, GetSpaceMissionResponse> {

    private final ModelMapper modelMapper;

    private final SpaceMissionManagerProperties spaceMissionManagerProperties;


    @Override
    public GetSpaceMissionResponse toModel(SpaceMission entity) {
        GetSpaceMissionResponse getSpaceMissionResponse = modelMapper.map(entity, GetSpaceMissionResponse.class);
        //
        String spaceShipUrl = String.join(FRONT_SLASH_DELIMITER, spaceMissionManagerProperties.getSpaceshipManagerBaseUrl(),
                SPACESHIPS,
                String.valueOf(entity.getSpaceShipId()));
        getSpaceMissionResponse.add(Link.of(spaceShipUrl, SPACESHIP));
        return getSpaceMissionResponse;
    }

    @Override
    public CollectionModel<GetSpaceMissionResponse> toCollectionModel(Iterable<? extends SpaceMission> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}