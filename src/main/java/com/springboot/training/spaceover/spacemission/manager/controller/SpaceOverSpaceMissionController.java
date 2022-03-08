package com.springboot.training.spaceover.spacemission.manager.controller;

import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.APPLICATION_JSON_PATCH;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.CREATE_SPACE_MISSION_REQUEST_MSG;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.CREATE_SPACE_MISSION_RESULT_MSG;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.CREATE_SPACE_MISSION_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.CREATE_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.DELETE_SPACE_MISSION_REQUEST_MSG;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.DELETE_SPACE_MISSION_RESULT_MSG;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.DELETE_SPACE_MISSION_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.DELETE_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.GET_SPACE_MISSIONS_REQUEST_MSG;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.GET_SPACE_MISSIONS_RESULT_MSG;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.GET_SPACE_MISSIONS_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.GET_SPACE_MISSIONS_SERVICE_OPERATION_DESCRIPTION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.GET_SPACE_MISSION_REQUEST_MSG;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.GET_SPACE_MISSION_RESULT_MSG;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.GET_SPACE_MISSION_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.GET_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.ID_URI;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.NAME_FIELD;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.PATCH_SPACE_MISSION_REQUEST_MSG;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.PATCH_SPACE_MISSION_RESULT_MSG;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.PATCH_SPACE_MISSION_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.PATCH_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.PUT_SPACE_MISSION_REQUEST_MSG;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.PUT_SPACE_MISSION_RESULT_MSG;
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
import com.springboot.training.spaceover.spacemission.manager.enums.SpaceMissionStatus;
import com.springboot.training.spaceover.spacemission.manager.service.SpaceMissionService;
import com.springboot.training.spaceover.spacemission.manager.utils.annotatations.ServiceOperation;
import com.springboot.training.spaceover.spacemission.manager.utils.assemblers.PaginationModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

@Slf4j
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
    public ResponseEntity<PagedModel<GetSpaceMissionResponse>> getSpaceMissions(@Parameter(hidden = true) Pageable pageable,
                                                                                @RequestParam(name = NAME_FIELD, required = false) String name,
                                                                                @RequestParam(name = STATUS_FIELD, required = false) String status,
                                                                                @RequestParam(name = SPACESHIP_ID_FIELD, required = false) Long spaceShipId) {
        log.trace(GET_SPACE_MISSIONS_REQUEST_MSG);
        SpaceMission spaceMissionSample = SpaceMission.builder()
                .name(name)
                .status(SpaceMissionStatus.fromName(status))
                .spaceShipId(spaceShipId)
                .build();
        Page<SpaceMission> spaceMissionPage = spaceMissionService.findAll(spaceMissionSample, pageable);
        PagedModel<GetSpaceMissionResponse> response = pagedModelAssembler.toModel(spaceMissionPage, modelAssembler);
        log.info(GET_SPACE_MISSIONS_RESULT_MSG, spaceMissionPage.getNumberOfElements(), spaceMissionPage.getTotalElements());
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping(ID_URI)
    @ServiceOperation(GET_SPACE_MISSION_SERVICE_OPERATION)
    @Operation(summary = GET_SPACE_MISSION_SERVICE_OPERATION, description = GET_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<GetSpaceMissionResponse> getSpaceMission(@PathVariable("id") Long id) {
        log.trace(GET_SPACE_MISSION_REQUEST_MSG, id);
        GetSpaceMissionResponse response = modelMapper.map(spaceMissionService.findBydId(id), GetSpaceMissionResponse.class);
        log.info(GET_SPACE_MISSION_RESULT_MSG, id);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ServiceOperation(CREATE_SPACE_MISSION_SERVICE_OPERATION)
    @Operation(summary = CREATE_SPACE_MISSION_SERVICE_OPERATION, description = CREATE_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity createSpaceMission(@RequestBody @Valid CreateSpaceMissionRequest request) {
        log.trace(CREATE_SPACE_MISSION_REQUEST_MSG);
        SpaceMission spaceMission = spaceMissionService.save(modelMapper.map(request, SpaceMission.class));
        log.info(CREATE_SPACE_MISSION_RESULT_MSG, spaceMission.getId());
        return ResponseEntity.created(getResourceUri(spaceMission.getId())).build();
    }

    @Override
    @PatchMapping(value = ID_URI, consumes = APPLICATION_JSON_PATCH)
    @ServiceOperation(PATCH_SPACE_MISSION_SERVICE_OPERATION)
    @Operation(summary = PATCH_SPACE_MISSION_SERVICE_OPERATION, description = PATCH_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<PatchSpaceMissionResponse> patchSpaceMission(@PathVariable("id") Long id, @RequestBody JsonPatch patch) {
        log.trace(PATCH_SPACE_MISSION_REQUEST_MSG, id);
        SpaceMission entity = spaceMissionService.findBydId(id);
        entity = spaceMissionService.update(applyPatch(patch, entity));
        log.info(PATCH_SPACE_MISSION_RESULT_MSG, id);
        return ResponseEntity.ok(modelMapper.map(entity, PatchSpaceMissionResponse.class));
    }

    @Override
    @PutMapping(ID_URI)
    @ServiceOperation(PUT_SPACE_MISSION_SERVICE_OPERATION)
    @Operation(summary = PUT_SPACE_MISSION_SERVICE_OPERATION, description = PUT_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<PutSpaceMissionResponse> putSpaceMission(@PathVariable("id") Long id, @RequestBody @Valid PutSpaceMissionRequest request) {
        log.trace(PUT_SPACE_MISSION_REQUEST_MSG, id);
        request.setId(id);
        SpaceMission entity = spaceMissionService.update(modelMapper.map(request, SpaceMission.class));
        log.info(PUT_SPACE_MISSION_RESULT_MSG, id);
        return ResponseEntity.ok(modelMapper.map(entity, PutSpaceMissionResponse.class));
    }

    @Override
    @DeleteMapping(ID_URI)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ServiceOperation(DELETE_SPACE_MISSION_SERVICE_OPERATION)
    @Operation(summary = DELETE_SPACE_MISSION_SERVICE_OPERATION, description = DELETE_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity deleteSpaceMission(@PathVariable("id") Long id) {
        log.trace(DELETE_SPACE_MISSION_REQUEST_MSG, id);
        spaceMissionService.deleteById(id);
        log.info(DELETE_SPACE_MISSION_RESULT_MSG, id);
        return ResponseEntity.noContent().build();
    }


}
