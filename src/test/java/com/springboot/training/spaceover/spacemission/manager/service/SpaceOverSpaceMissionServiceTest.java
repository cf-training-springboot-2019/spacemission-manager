package com.springboot.training.spaceover.spacemission.manager.service;

import com.springboot.training.spaceover.spacemission.manager.domain.model.SpaceMission;
import com.springboot.training.spaceover.spacemission.manager.enums.SpaceMissionStatus;
import com.springboot.training.spaceover.spacemission.manager.repository.SpaceMissionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.ENTITY_NOT_FOUND_MSG;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.SPACE_MISSION;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//LT10-2-Add unit test to the service layer
class SpaceOverSpaceMissionServiceTest {

    private SpaceOverSpaceMissionService spaceMissionService;

    private SpaceMissionRepository spaceMissionRepository;

    private SpaceShipClient spaceShipClient;

    @Test
    @DisplayName("Given no arguments, when invoking findAll method, then return SpaceMission List")
    void findAllList() {

        //Arrange
        SpaceMission spaceMissionOne = SpaceMission.builder()
                .id(1L)
                .name("Retrieve the Millennium Falcon")
                .spaceShipId(2L)
                .revenue(BigDecimal.valueOf(10000L))
                .status(SpaceMissionStatus.IN_PROGRESS)
                .build();

        SpaceMission spaceMissionTwo = SpaceMission.builder()
                .id(2L)
                .name("Retrieve the Red Fox")
                .spaceShipId(1L)
                .revenue(BigDecimal.valueOf(1000L))
                .status(SpaceMissionStatus.NOT_IN_PROGRESS)
                .build();

        SpaceMission spaceMissionThree = SpaceMission.builder()
                .id(3L)
                .name("Destroy the Death Star")
                .details("Blow it up!")
                .spaceShipId(1L)
                .revenue(BigDecimal.ZERO)
                .status(SpaceMissionStatus.ACCOMPLISHED)
                .build();

        when(spaceMissionRepository.findAll());

        //Act
        List<SpaceMission> spaceMissionList = spaceMissionService.findAll();

        //Assert
        //Assert collection


        //Assert first spaceship

        //Assert second spaceship

        //Assert third spaceship

    }

    @Test
    @DisplayName("Given a SpaceMission and Pageable arguments, when invoking findAll method, then return SpaceMission Page")
    void findAllPage() {

        //Arrange
        SpaceMission spaceMissionOne = SpaceMission.builder()
                .id(1L)
                .name("Retrieve the Millennium Falcon")
                .spaceShipId(2L)
                .revenue(BigDecimal.valueOf(10000L))
                .status(SpaceMissionStatus.IN_PROGRESS)
                .build();

        SpaceMission spaceMissionTwo = SpaceMission.builder()
                .id(2L)
                .name("Retrieve the Red Fox")
                .spaceShipId(1L)
                .revenue(BigDecimal.valueOf(1000L))
                .status(SpaceMissionStatus.NOT_IN_PROGRESS)
                .build();

        SpaceMission spaceMissionThree = SpaceMission.builder()
                .id(3L)
                .name("Destroy the Death Star")
                .details("Blow it up!")
                .spaceShipId(1L)
                .revenue(BigDecimal.ZERO)
                .status(SpaceMissionStatus.ACCOMPLISHED)
                .build();

        List<SpaceMission> spaceMissionList = Arrays.asList(spaceMissionOne, spaceMissionTwo, spaceMissionThree);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("spaceShipId", ExampleMatcher.GenericPropertyMatchers.exact());

        SpaceMission entitySample = SpaceMission.builder().build();
        Example<SpaceMission> spaceMissionExample = Example.of(entitySample, exampleMatcher);

        Pageable pageRequest = PageRequest.of(0, 3);

        //Act
        when(spaceMissionRepository.findAll(spaceMissionExample, pageRequest));

        Page<SpaceMission> spaceMissionPage = spaceMissionService.findAll(entitySample, pageRequest);

        //Assert
        //Assert collection

        //Assert first spaceship

        //Assert second spaceship

        //Assert third spaceship

    }

    @Test
    @DisplayName("Given a valid SpaceMission identifier argument, when invoking findById method, then return SpaceMission")
    void findBydId() {

        //Arrange
        SpaceMission spaceMission = SpaceMission.builder()
                .id(1L)
                .name("Retrieve the Millennium Falcon")
                .spaceShipId(2L)
                .revenue(BigDecimal.valueOf(10000L))
                .status(SpaceMissionStatus.IN_PROGRESS)
                .build();

        when(spaceMissionRepository.findById(1L));

        //Act
        spaceMission = spaceMissionService.findBydId(1L);

        //Assert
        //Assert spaceship

    }

    @Test
    @DisplayName("Given an invalid SpaceMission identifier argument, when invoking findById method, then throw EntityNotFoundException")
    void findBydIdThrowEntityNotFound() {

        //Arrange
        when(spaceMissionRepository.findById(1L));

        //Act & Assert
        //Assert exception
        assertThrows(null, () -> spaceMissionService.findBydId(1L), "null");

    }

    @Test
    @DisplayName("Given a SpaceMission, when invoking save method, then return SpaceMission")
    void save() {

        //Arrange
        SpaceMission spaceMission = SpaceMission.builder()
                .name("Retrieve the Millennium Falcon")
                .spaceShipId(2L)
                .revenue(BigDecimal.valueOf(10000L))
                .status(SpaceMissionStatus.IN_PROGRESS)
                .build();


        //Arrange
        SpaceMission persistedSpaceMission = SpaceMission.builder()
                .id(1L)
                .name("Retrieve the Millennium Falcon")
                .spaceShipId(2L)
                .revenue(BigDecimal.valueOf(10000L))
                .status(SpaceMissionStatus.IN_PROGRESS)
                .build();

        when(spaceMissionRepository.save(spaceMission));

        //Act
        spaceMission = spaceMissionService.save(spaceMission);

        //Assert

        //Assert spaceship


    }

    @Test
    @DisplayName("Given a SpaceMission, when invoking update method, then return SpaceMission")
    void update() {
        //Arrange
        SpaceMission spaceMission = SpaceMission.builder()
                .id(1L)
                .name("Retrieve the Millennium Falcon")
                .spaceShipId(2L)
                .revenue(BigDecimal.valueOf(10000L))
                .status(SpaceMissionStatus.IN_PROGRESS)
                .build();


        when(spaceMissionRepository.save(spaceMission)).thenReturn(spaceMission);
        //Act
        spaceMission = spaceMissionService.update(spaceMission);
        //Assert

        //Assert spaceship

    }

    @Test
    @DisplayName("Given a SpaceMission identifier, when invoking delete method, then invoke repository.deleteById")
    void deleteById() {
        //No arrange required
        //Act
        spaceMissionService.deleteById(1L);
        //Assert
        //verify method call count

    }
}