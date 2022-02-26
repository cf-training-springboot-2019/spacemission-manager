package com.springboot.training.spaceover.spacemission.manager.configuration;

import com.springboot.training.spaceover.spacemission.manager.domain.model.SpaceMission;
import com.springboot.training.spaceover.spacemission.manager.domain.request.inbound.CreateSpaceMissionRequest;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(CreateSpaceMissionRequest.class, SpaceMission.class)
            .addMappings(mapper -> mapper.skip(SpaceMission::setId));
        return modelMapper;
    }

}
