package com.springboot.training.spaceover.spacemission.manager.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.springboot.training.spaceover.spacemission.manager.domain.model.SpaceMission;
import com.springboot.training.spaceover.spacemission.manager.domain.request.inbound.CreateSpaceMissionRequest;
import com.springboot.training.spaceover.spacemission.manager.domain.request.inbound.PutSpaceMissionRequest;
import com.springboot.training.spaceover.spacemission.manager.domain.response.outbound.GetSpaceMissionResponse;
import com.springboot.training.spaceover.spacemission.manager.domain.response.outbound.PatchSpaceMissionResponse;
import com.springboot.training.spaceover.spacemission.manager.domain.response.outbound.PutSpaceMissionResponse;
import com.springboot.training.spaceover.spacemission.manager.enums.SpaceMissionStatus;
import com.springboot.training.spaceover.spacemission.manager.service.SpaceMissionService;
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
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.*;

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
    @Operation(summary = GET_SPACE_MISSIONS_SERVICE_OPERATION, description = GET_SPACE_MISSIONS_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<PagedModel<GetSpaceMissionResponse>> getSpaceMissions(@Parameter(hidden = true) Pageable pageable,
                                                                                @RequestParam(name = NAME_FIELD, required = false) String name,
                                                                                @RequestParam(name = STATUS_FIELD, required = false) String status,
                                                                                @RequestParam(name = SPACESHIP_ID_FIELD, required = false) Long spaceShipId) {
        SpaceMission spaceMissionSample = SpaceMission.builder()
                .name(name)
                .status(SpaceMissionStatus.fromName(status))
                .spaceShipId(spaceShipId)
                .build();
        Page<SpaceMission> spaceMissionPage = spaceMissionService.findAll(spaceMissionSample, pageable);
        PagedModel<GetSpaceMissionResponse> response = pagedModelAssembler.toModel(spaceMissionPage, modelAssembler);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping(ID_URI)
    @Operation(summary = GET_SPACE_MISSION_SERVICE_OPERATION, description = GET_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<GetSpaceMissionResponse> getSpaceMission(@PathVariable("id") Long id) {
        GetSpaceMissionResponse response = modelMapper.map(spaceMissionService.findBydId(id), GetSpaceMissionResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = CREATE_SPACE_MISSION_SERVICE_OPERATION, description = CREATE_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity createSpaceMission(@RequestBody @Valid CreateSpaceMissionRequest request) {
        SpaceMission spaceMission = spaceMissionService.save(modelMapper.map(request, SpaceMission.class));
        return ResponseEntity.created(getResourceUri(spaceMission.getId())).build();
    }

    @Override
    @PatchMapping(value = ID_URI, consumes = APPLICATION_JSON_PATCH)
    @Operation(summary = PATCH_SPACE_MISSION_SERVICE_OPERATION, description = PATCH_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<PatchSpaceMissionResponse> patchSpaceMission(@PathVariable("id") Long id, @RequestBody JsonPatch patch) {
        SpaceMission entity = spaceMissionService.findBydId(id);
        entity = spaceMissionService.update(applyPatch(patch, entity));
        return ResponseEntity.ok(modelMapper.map(entity, PatchSpaceMissionResponse.class));
    }

    @Override
    @PutMapping(ID_URI)
    @Operation(summary = PUT_SPACE_MISSION_SERVICE_OPERATION, description = PUT_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<PutSpaceMissionResponse> putSpaceMission(@PathVariable("id") Long id, @RequestBody @Valid PutSpaceMissionRequest request) {
        request.setId(id);
        SpaceMission entity = spaceMissionService.update(modelMapper.map(request, SpaceMission.class));
        return ResponseEntity.ok(modelMapper.map(entity, PutSpaceMissionResponse.class));
    }

    @Override
    @DeleteMapping(ID_URI)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = DELETE_SPACE_MISSION_SERVICE_OPERATION, description = DELETE_SPACE_MISSION_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity deleteSpaceMission(@PathVariable("id") Long id) {
        spaceMissionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
