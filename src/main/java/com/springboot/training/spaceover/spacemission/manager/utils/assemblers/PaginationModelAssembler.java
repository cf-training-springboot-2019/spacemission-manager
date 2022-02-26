package com.springboot.training.spaceover.spacemission.manager.utils.assemblers;

import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.FRONT_SLASH_DELIMITER;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.SPACESHIPS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.springboot.training.spaceover.spacemission.manager.controller.SpaceOverSpaceMissionController;
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
        //LT4.2-Add selfref link to GetSpaceMissionResponse
        getSpaceMissionResponse.add(linkTo(methodOn(SpaceOverSpaceMissionController.class).getSpaceMission(entity.getId())).withSelfRel());
        String spaceShipUrl = String.join(FRONT_SLASH_DELIMITER, spaceMissionManagerProperties.getSpaceshipManagerBaseUrl(),
                SPACESHIPS,
                String.valueOf(entity.getSpaceShipId()));
        //Add spaceship details link to GetSpaceMissionResponse
        getSpaceMissionResponse.add(Link.of(spaceShipUrl, "spaceship"));
        return getSpaceMissionResponse;
    }

    @Override
    public CollectionModel<GetSpaceMissionResponse> toCollectionModel(Iterable<? extends SpaceMission> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}