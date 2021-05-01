package com.springboot.training.spaceover.spacemission.manager.controller;

import com.springboot.training.spaceover.spacemission.manager.domain.request.inbound.CreateSpaceMissionRequest;
import com.springboot.training.spaceover.spacemission.manager.domain.request.inbound.UpdateSpaceMissionRequest;
import com.springboot.training.spaceover.spacemission.manager.domain.response.outbound.GetSpaceMissionResponse;
import com.springboot.training.spaceover.spacemission.manager.domain.response.outbound.UpdateSpaceMissionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SpaceMissionController {

    ResponseEntity<Page<GetSpaceMissionResponse>> getSpaceCrewMembers(Pageable page, String name, String status, List<Long> ids);

    ResponseEntity<GetSpaceMissionResponse> getSpaceCrewMember(Long id);

    ResponseEntity createSpaceCrewMember(CreateSpaceMissionRequest request);

    ResponseEntity<UpdateSpaceMissionResponse>  UpdateSpaceCrewMember(UpdateSpaceMissionRequest request);

    ResponseEntity deleteSpaceCrewMember(Long id);

}
