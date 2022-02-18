package com.springboot.training.spaceover.spacemission.manager.controller;

import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.APPLICATION_JSON_PATCH;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.CREATE_SPACE_MISSION_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.CREATE_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.DELETE_SPACE_MISSION_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.DELETE_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.GET_SPACE_MISSIONS_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.GET_SPACE_MISSIONS_SERVICE_OPERATION_DESCRIPTION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.GET_SPACE_MISSION_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.GET_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.ID_URI;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.NAME_FIELD;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.PATCH_SPACE_MISSION_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.PATCH_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.PUT_SPACE_MISSION_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.PUT_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.SPACESHIP_ID_FIELD;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.SPACE_MISSIONS;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.SPACE_MISSIONS_URI;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.SPACE_MISSION_API_DESCRIPTION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.STATUS_FIELD;

import com.github.fge.jsonpatch.JsonPatch;
import com.springboot.training.spaceover.spacemission.manager.domain.model.SpaceMission;
import com.springboot.training.spaceover.spacemission.manager.domain.request.inbound.CreateSpaceMissionRequest;
import com.springboot.training.spaceover.spacemission.manager.domain.request.inbound.PutSpaceMissionRequest;
import com.springboot.training.spaceover.spacemission.manager.domain.response.outbound.GetSpaceMissionResponse;
import com.springboot.training.spaceover.spacemission.manager.domain.response.outbound.PatchSpaceMissionResponse;
import com.springboot.training.spaceover.spacemission.manager.domain.response.outbound.PutSpaceMissionResponse;
import com.springboot.training.spaceover.spacemission.manager.service.SpaceMissionService;
import com.springboot.training.spaceover.spacemission.manager.utils.annotatations.ServiceOperation;
import com.springboot.training.spaceover.spacemission.manager.utils.assemblers.PaginationModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(SPACE_MISSIONS_URI)
@Tag(name = SPACE_MISSIONS, description = SPACE_MISSION_API_DESCRIPTION)
public class SpaceOverSpaceMissionController extends SpaceOverGenericController implements SpaceMissionController {

    private final SpaceMissionService spaceMissionService;

    private final ModelMapper modelMapper;

    private final PagedResourcesAssembler<SpaceMission> pagedModelAssembler;

    private final PaginationModelAssembler modelAssembler;

    @Override
    @GetMapping
    @PageableAsQueryParam
    @ServiceOperation(GET_SPACE_MISSIONS_SERVICE_OPERATION)
    @Operation(summary = GET_SPACE_MISSIONS_SERVICE_OPERATION, description = GET_SPACE_MISSIONS_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<Page<GetSpaceMissionResponse>> getSpaceMissions(@Parameter(hidden = true) Pageable pageable,
                                                                                @RequestParam(name = NAME_FIELD, required = false) String name,
                                                                                @RequestParam(name = STATUS_FIELD, required = false) String status,
                                                                                @RequestParam(name = SPACESHIP_ID_FIELD, required = false) Long spaceShipId) {

        //LT3.3-Include request pagination
        //LT3.4-Include example matching
        Page<SpaceMission> spaceMissionPage = null;
        Page<GetSpaceMissionResponse> responsePage = null;
        return ResponseEntity.ok(responsePage);
    }

    @Override
    @GetMapping(ID_URI)
    @ServiceOperation(GET_SPACE_MISSION_SERVICE_OPERATION)
    @Operation(summary = GET_SPACE_MISSION_SERVICE_OPERATION, description = GET_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<GetSpaceMissionResponse> getSpaceMission(@PathVariable("id") Long id) {
        GetSpaceMissionResponse response = modelMapper.map(spaceMissionService.findBydId(id), GetSpaceMissionResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ServiceOperation(CREATE_SPACE_MISSION_SERVICE_OPERATION)
    @Operation(summary = CREATE_SPACE_MISSION_SERVICE_OPERATION, description = CREATE_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity createSpaceMission(@RequestBody CreateSpaceMissionRequest request) { // LT2.1-Include request validation
        SpaceMission spaceMission = spaceMissionService.save(modelMapper.map(request, SpaceMission.class));
        return ResponseEntity.created(getResourceUri(spaceMission.getId())).build();
    }

    @Override
    @PatchMapping(value = ID_URI, consumes = APPLICATION_JSON_PATCH)
    @ServiceOperation(PATCH_SPACE_MISSION_SERVICE_OPERATION)
    @Operation(summary = PATCH_SPACE_MISSION_SERVICE_OPERATION, description = PATCH_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<PatchSpaceMissionResponse> patchSpaceMission(@PathVariable("id") Long id, @RequestBody JsonPatch patch) {
        SpaceMission entity = spaceMissionService.findBydId(id);
        entity = spaceMissionService.update(applyPatch(patch, entity));
        return ResponseEntity.ok(modelMapper.map(entity, PatchSpaceMissionResponse.class));
    }

    @Override
    @PutMapping(ID_URI)
    @ServiceOperation(PUT_SPACE_MISSION_SERVICE_OPERATION)
    @Operation(summary = PUT_SPACE_MISSION_SERVICE_OPERATION, description = PUT_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<PutSpaceMissionResponse> putSpaceMission(@PathVariable("id") Long id, @RequestBody PutSpaceMissionRequest request) { // LT2.1-Include request validation
        request.setId(id);
        SpaceMission entity = spaceMissionService.update(modelMapper.map(request, SpaceMission.class));
        return ResponseEntity.ok(modelMapper.map(entity, PutSpaceMissionResponse.class));
    }

    @Override
    @DeleteMapping(ID_URI)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ServiceOperation(DELETE_SPACE_MISSION_SERVICE_OPERATION)
    @Operation(summary = DELETE_SPACE_MISSION_SERVICE_OPERATION, description = DELETE_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity deleteSpaceMission(@PathVariable("id") Long id) {
        spaceMissionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
