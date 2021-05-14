package com.springboot.training.spaceover.spacemission.manager.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.springboot.training.spaceover.spacemission.manager.domain.model.SpaceMission;
import lombok.SneakyThrows;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public abstract class SpaceOverGenericController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    protected SpaceMission applyPatch(JsonPatch patch, SpaceMission targetSpaceMission) {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetSpaceMission, JsonNode.class));
        return objectMapper.treeToValue(patched, SpaceMission.class);
    }

    protected URI getResourceUri(Long id) {
        return URI.create(ServletUriComponentsBuilder.fromCurrentRequest().pathSegment(id.toString()).toUriString());
    }

}
