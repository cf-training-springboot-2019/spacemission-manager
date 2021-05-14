package com.springboot.training.spaceover.spacemission.manager.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.springboot.training.spaceover.spacemission.manager.domain.request.inbound.CreateSpaceMissionRequest;
import com.springboot.training.spaceover.spacemission.manager.domain.request.inbound.PutSpaceMissionRequest;
import com.springboot.training.spaceover.spacemission.manager.domain.response.outbound.GetSpaceMissionResponse;
import com.springboot.training.spaceover.spacemission.manager.domain.response.outbound.PatchSpaceMissionResponse;
import com.springboot.training.spaceover.spacemission.manager.domain.response.outbound.PutSpaceMissionResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

public interface SpaceMissionController {

    ResponseEntity<PagedModel<GetSpaceMissionResponse>> getSpaceMissions(Pageable pageable, String name, String status, Long spaceShipId);

    ResponseEntity<GetSpaceMissionResponse> getSpaceMission(Long id);

    ResponseEntity createSpaceMission(CreateSpaceMissionRequest request);

    ResponseEntity<PatchSpaceMissionResponse> patchSpaceMission(Long id, JsonPatch request);

    ResponseEntity<PutSpaceMissionResponse> putSpaceMission(Long id, PutSpaceMissionRequest request);

    ResponseEntity deleteSpaceMission(Long id);

}
