package com.springboot.training.spaceover.spacemission.manager.repository;

import com.springboot.training.spaceover.spacemission.manager.domain.model.SpaceMission;
import com.springboot.training.spaceover.spacemission.manager.enums.SpaceMissionStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql("/scripts/import_space_missions.sql")
//LT10-2-Add integration test to the persistence layer
class SpaceMissionRepositoryTest {

    private SpaceMissionRepository spaceMissionRepository;

    @Test
    @DisplayName("Given no arguments, when invoking findAll method, then return SpaceMission List")
    void findAllList() {
        //No Arrange required
        //Act
        List<SpaceMission> spaceMissionList = spaceMissionRepository.findAll();
        //Assert
        //Assert collection


        //Assert first space mission

        //Assert second space mission

        //Assert third space mission

    }

    @Test
    @DisplayName("Given SpaceMission and Pageable arguments, when invoking findAll method, then return SpaceMission Page")
    void findAllPage() {
        //Arrange
        SpaceMission spaceMissionSample = SpaceMission.builder().build();

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("spaceShipId", ExampleMatcher.GenericPropertyMatchers.exact());

        Pageable pageRequest = PageRequest.of(1, 1);

        //Act
        Page<SpaceMission> spaceMissionPage = spaceMissionRepository
                .findAll(Example.of(spaceMissionSample, exampleMatcher), pageRequest);
        //Assert
        //Assert collection


        //Assert space mission

    }

    @Test
    @DisplayName("Given SpaceMission identifier, when invoking findById method, then return SpaceMission Optional")
    void findById() {
        //No Arrange required
        //Act
        Optional<SpaceMission> spaceMission = spaceMissionRepository.findById(1L);
        //Assert
        //Assert space mission

    }

    @Test
    @DisplayName("Given none-existent SpaceMission identifier, when invoking findById method, then return empty SpaceMission Optional")
    void findByIdReturnsOptional() {
        //No Arrange required
        //Act
        Optional<SpaceMission> spaceMission = spaceMissionRepository.findById(10L);
        //Assert
        //Assert space mission

    }

    @Test
    @DisplayName("Given SpaceMission, when invoking save method, then return SpaceMission")
    void save() {
        //No Arrange required
        //Act
        SpaceMission spaceMission = SpaceMission.builder()
                .id(4L)
                .name("Destroy the new Death Star")
                .details("Blow it up... Again!")
                .spaceShipId(1L)
                .revenue(BigDecimal.ZERO)
                .status(SpaceMissionStatus.NOT_IN_PROGRESS)
                .build();

        spaceMission = spaceMissionRepository.save(spaceMission);

        //Assert spaceship

        //Assert collection

    }

    @Test
    void saveUpdate() {
        //Arrange
        SpaceMission spaceMission = SpaceMission.builder()
                .id(1L)
                .name("Retrieve the Millennium Falcon")
                .details("We got it back")
                .spaceShipId(2L)
                .revenue(BigDecimal.valueOf(10000L))
                .status(SpaceMissionStatus.ACCOMPLISHED)
                .build();
        //Act
        spaceMission = spaceMissionRepository.save(spaceMission);
        //Assert
        //Assert spaceship

        //Assert collection
    }

    @Test
    @DisplayName("Given SpaceMission identifier, when invoking deleteById method, then expect reduced collection size")
    void delete() {
        //No Arrange required
        //Act
        spaceMissionRepository.deleteById(1L);
        //Assert
        //Assert collection

    }

}