package com.springboot.training.spaceover.spacemission.manager.repository;

import com.springboot.training.spaceover.spacemission.manager.domain.model.SpaceMission;
import com.springboot.training.spaceover.spacemission.manager.enums.SpaceMissionStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Sql("/scripts/import_space_missions.sql")
class SpaceMissionRepositoryTest {

    @Autowired
    private SpaceMissionRepository spaceMissionRepository;

    @Test
    @DisplayName("Given no arguments, when invoking findAll method, then return SpaceMission List")
    void findAllList() {
        //No Arrange required
        //Act
        List<SpaceMission> spaceMissionList = spaceMissionRepository.findAll();
        //Assert
        //Assert
        //Assert collection
        assertNotNull(spaceMissionList);
        assertEquals(3, spaceMissionList.size());

        //Assert first space mission
        assertNotNull(spaceMissionList.get(0));
        assertEquals(1L, spaceMissionList.get(0).getId());
        assertEquals("Retrieve the Millennium Falcon", spaceMissionList.get(0).getName());
        assertNull(spaceMissionList.get(0).getDetails());
        assertEquals(2L, spaceMissionList.get(0).getSpaceShipId());
        assertEquals(SpaceMissionStatus.IN_PROGRESS, spaceMissionList.get(0).getStatus());
        assertEquals(0, BigDecimal.valueOf(10000L).compareTo(spaceMissionList.get(0).getRevenue()));

        //Assert second space mission
        assertNotNull(spaceMissionList.get(1));
        assertEquals(2L, spaceMissionList.get(1).getId());
        assertEquals("Retrieve the Red Fox", spaceMissionList.get(1).getName());
        assertNull(spaceMissionList.get(1).getDetails());
        assertEquals(1L, spaceMissionList.get(1).getSpaceShipId());
        assertEquals(SpaceMissionStatus.NOT_IN_PROGRESS, spaceMissionList.get(1).getStatus());
        assertEquals(0, BigDecimal.valueOf(1000L).compareTo(spaceMissionList.get(1).getRevenue()));

        //Assert third space mission
        assertNotNull(spaceMissionList.get(2));
        assertEquals(3L, spaceMissionList.get(2).getId());
        assertEquals("Destroy the Death Star", spaceMissionList.get(2).getName());
        assertEquals("Blow it up!", spaceMissionList.get(2).getDetails());
        assertEquals(1L, spaceMissionList.get(2).getSpaceShipId());
        assertEquals(SpaceMissionStatus.ACCOMPLISHED, spaceMissionList.get(2).getStatus());
        assertEquals(0, BigDecimal.valueOf(0L).compareTo(spaceMissionList.get(2).getRevenue()));

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
        assertNotNull(spaceMissionPage);
        assertEquals(1, spaceMissionPage.getNumberOfElements());
        assertEquals(3, spaceMissionPage.getTotalElements());
        assertNotNull(spaceMissionPage.getContent());
        assertTrue(spaceMissionPage.hasPrevious());
        assertTrue(spaceMissionPage.hasNext());

        //Assert space mission
        assertNotNull(spaceMissionPage.getContent().get(0));
        assertEquals(2L, spaceMissionPage.getContent().get(0).getId());
        assertEquals("Retrieve the Red Fox", spaceMissionPage.getContent().get(0).getName());
        assertNull(spaceMissionPage.getContent().get(0).getDetails());
        assertEquals(1L, spaceMissionPage.getContent().get(0).getSpaceShipId());
        assertEquals(SpaceMissionStatus.NOT_IN_PROGRESS, spaceMissionPage.getContent().get(0).getStatus());
        assertEquals(0, BigDecimal.valueOf(1000L).compareTo(spaceMissionPage.getContent().get(0).getRevenue()));

    }

    @Test
    @DisplayName("Given SpaceMission identifier, when invoking findById method, then return SpaceMission Optional")
    void findById() {
        //No Arrange required
        //Act
        Optional<SpaceMission> spaceMission = spaceMissionRepository.findById(1L);
        //Assert
        //Assert space mission
        assertTrue(spaceMission.isPresent());
        assertNotNull(spaceMission.get());
        assertEquals(1L, spaceMission.get().getId());
        assertEquals("Retrieve the Millennium Falcon", spaceMission.get().getName());
        assertNull(spaceMission.get().getDetails());
        assertNull(spaceMission.get().getDetails());
        assertEquals(SpaceMissionStatus.IN_PROGRESS, spaceMission.get().getStatus());
        assertEquals(0, BigDecimal.valueOf(10000l).compareTo(spaceMission.get().getRevenue()));

    }

    @Test
    @DisplayName("Given none-existent SpaceMission identifier, when invoking findById method, then return empty SpaceMission Optional")
    void findByIdReturnsOptional() {
        //No Arrange required
        //Act
        Optional<SpaceMission> spaceMission = spaceMissionRepository.findById(10L);
        //Assert
        //Assert space mission
        assertFalse(spaceMission.isPresent());

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
        assertNotNull(spaceMission);
        assertEquals(4L, spaceMission.getId());
        assertEquals("Destroy the new Death Star", spaceMission.getName());
        assertEquals("Blow it up... Again!", spaceMission.getDetails());
        assertEquals(SpaceMissionStatus.NOT_IN_PROGRESS, spaceMission.getStatus());
        assertEquals(0, BigDecimal.ZERO.compareTo(spaceMission.getRevenue()));

        //Assert collection
        assertEquals(4, spaceMissionRepository.count());

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
        assertNotNull(spaceMission);
        assertEquals(1L, spaceMission.getId());
        assertEquals("Retrieve the Millennium Falcon", spaceMission.getName());
        assertEquals("We got it back", spaceMission.getDetails());
        assertEquals(SpaceMissionStatus.ACCOMPLISHED, spaceMission.getStatus());
        assertEquals(0, BigDecimal.valueOf(10000L).compareTo(spaceMission.getRevenue()));

        //Assert collection
        assertEquals(3, spaceMissionRepository.count());
    }

    @Test
    @DisplayName("Given SpaceMission identifier, when invoking deleteById method, then expect reduced collection size")
    void delete() {
        //No Arrange required
        //Act
        spaceMissionRepository.deleteById(1L);
        //Assert
        //Assert collection
        assertEquals(2, spaceMissionRepository.count());

    }

}