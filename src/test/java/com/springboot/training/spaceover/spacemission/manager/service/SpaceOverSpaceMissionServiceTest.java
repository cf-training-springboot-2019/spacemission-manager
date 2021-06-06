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

@ExtendWith(MockitoExtension.class)
class SpaceOverSpaceMissionServiceTest {

    @InjectMocks
    private SpaceOverSpaceMissionService spaceMissionService;

    @Mock
    private SpaceMissionRepository spaceMissionRepository;

    @Mock
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

        when(spaceMissionRepository.findAll()).thenReturn(Arrays.asList(spaceMissionOne, spaceMissionTwo, spaceMissionThree));

        //Act
        List<SpaceMission> spaceMissionList = spaceMissionService.findAll();

        //Assert
        //Assert collection
        assertNotNull(spaceMissionList);
        assertEquals(3, spaceMissionList.size());

        //Assert first spaceship
        assertNotNull(spaceMissionList.get(0));
        assertEquals(1L, spaceMissionList.get(0).getId());
        assertEquals("Retrieve the Millennium Falcon", spaceMissionList.get(0).getName());
        assertNull(spaceMissionList.get(0).getDetails());
        assertEquals(2L, spaceMissionList.get(0).getSpaceShipId());
        assertEquals(SpaceMissionStatus.IN_PROGRESS, spaceMissionList.get(0).getStatus());
        assertEquals(0, BigDecimal.valueOf(10000L).compareTo(spaceMissionList.get(0).getRevenue()));

        //Assert second spaceship
        assertNotNull(spaceMissionList.get(1));
        assertEquals(2L, spaceMissionList.get(1).getId());
        assertEquals("Retrieve the Red Fox", spaceMissionList.get(1).getName());
        assertNull(spaceMissionList.get(1).getDetails());
        assertEquals(1L, spaceMissionList.get(1).getSpaceShipId());
        assertEquals(SpaceMissionStatus.NOT_IN_PROGRESS, spaceMissionList.get(1).getStatus());
        assertEquals(0, BigDecimal.valueOf(1000L).compareTo(spaceMissionList.get(1).getRevenue()));

        //Assert third spaceship
        assertNotNull(spaceMissionList.get(2));
        assertEquals(3L, spaceMissionList.get(2).getId());
        assertEquals("Destroy the Death Star", spaceMissionList.get(2).getName());
        assertEquals("Blow it up!", spaceMissionList.get(2).getDetails());
        assertEquals(1L, spaceMissionList.get(2).getSpaceShipId());
        assertEquals(SpaceMissionStatus.ACCOMPLISHED, spaceMissionList.get(2).getStatus());
        assertEquals(0, BigDecimal.valueOf(0L).compareTo(spaceMissionList.get(2).getRevenue()));
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
        when(spaceMissionRepository.findAll(spaceMissionExample, pageRequest))
                .thenReturn(new PageImpl<>(spaceMissionList, pageRequest, 5));

        Page<SpaceMission> spaceMissionPage = spaceMissionService.findAll(entitySample, pageRequest);

        //Assert
        //Assert collection
        assertNotNull(spaceMissionPage);
        assertEquals(3, spaceMissionPage.getNumberOfElements());
        assertEquals(5, spaceMissionPage.getTotalElements());
        assertNotNull(spaceMissionPage.getContent());

        //Assert first spaceship
        assertNotNull(spaceMissionPage.getContent().get(0));
        assertNotNull(spaceMissionPage.getContent().get(0));
        assertEquals(1L, spaceMissionPage.getContent().get(0).getId());
        assertEquals("Retrieve the Millennium Falcon", spaceMissionPage.getContent().get(0).getName());
        assertNull(spaceMissionPage.getContent().get(0).getDetails());
        assertEquals(2L, spaceMissionPage.getContent().get(0).getSpaceShipId());
        assertEquals(SpaceMissionStatus.IN_PROGRESS, spaceMissionPage.getContent().get(0).getStatus());
        assertEquals(0, BigDecimal.valueOf(10000L).compareTo(spaceMissionPage.getContent().get(0).getRevenue()));

        //Assert second spaceship
        assertNotNull(spaceMissionPage.getContent().get(1));
        assertEquals(2L, spaceMissionPage.getContent().get(1).getId());
        assertEquals("Retrieve the Red Fox", spaceMissionPage.getContent().get(1).getName());
        assertNull(spaceMissionPage.getContent().get(1).getDetails());
        assertEquals(1L, spaceMissionPage.getContent().get(1).getSpaceShipId());
        assertEquals(SpaceMissionStatus.NOT_IN_PROGRESS, spaceMissionPage.getContent().get(1).getStatus());
        assertEquals(0, BigDecimal.valueOf(1000L).compareTo(spaceMissionPage.getContent().get(1).getRevenue()));

        //Assert third spaceship
        assertNotNull(spaceMissionPage.getContent().get(2));
        assertEquals(3L, spaceMissionPage.getContent().get(2).getId());
        assertEquals("Destroy the Death Star", spaceMissionPage.getContent().get(2).getName());
        assertEquals("Blow it up!", spaceMissionPage.getContent().get(2).getDetails());
        assertEquals(1L, spaceMissionPage.getContent().get(2).getSpaceShipId());
        assertEquals(SpaceMissionStatus.ACCOMPLISHED, spaceMissionPage.getContent().get(2).getStatus());
        assertEquals(0, BigDecimal.valueOf(0L).compareTo(spaceMissionPage.getContent().get(2).getRevenue()));

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

        when(spaceMissionRepository.findById(1L)).thenReturn(Optional.of(spaceMission));

        //Act
        spaceMission = spaceMissionService.findBydId(1L);

        //Assert
        //Assert spaceship
        assertNotNull(spaceMission);
        assertEquals(1L, spaceMission.getId());
        assertEquals("Retrieve the Millennium Falcon", spaceMission.getName());
        assertNull(spaceMission.getDetails());
        assertEquals(2L, spaceMission.getSpaceShipId());
        assertEquals(SpaceMissionStatus.IN_PROGRESS, spaceMission.getStatus());
        assertEquals(0, BigDecimal.valueOf(10000L).compareTo(spaceMission.getRevenue()));

    }

    @Test
    @DisplayName("Given an invalid SpaceMission identifier argument, when invoking findById method, then throw EntityNotFoundException")
    void findBydIdThrowEntityNotFound() {

        //Arrange
        when(spaceMissionRepository.findById(1L)).thenReturn(Optional.empty());

        //Act & Assert
        //Assert exception
        assertThrows(EntityNotFoundException.class, () -> spaceMissionService.findBydId(1L), String.format(ENTITY_NOT_FOUND_MSG, SPACE_MISSION, "1"));

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

        when(spaceMissionRepository.save(spaceMission)).thenReturn(persistedSpaceMission);

        //Act
        spaceMission = spaceMissionService.save(spaceMission);

        //Assert
        verify(spaceShipClient).findBydId(2L);

        //Assert spaceship
        assertNotNull(spaceMission);
        assertEquals(1L, spaceMission.getId());
        assertEquals("Retrieve the Millennium Falcon", spaceMission.getName());
        assertNull(spaceMission.getDetails());
        assertEquals(2L, spaceMission.getSpaceShipId());
        assertEquals(SpaceMissionStatus.IN_PROGRESS, spaceMission.getStatus());
        assertEquals(0, BigDecimal.valueOf(10000L).compareTo(spaceMission.getRevenue()));

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
        verify(spaceShipClient).findBydId(2L);

        //Assert spaceship
        assertNotNull(spaceMission);
        assertEquals(1L, spaceMission.getId());
        assertEquals("Retrieve the Millennium Falcon", spaceMission.getName());
        assertNull(spaceMission.getDetails());
        assertEquals(2L, spaceMission.getSpaceShipId());
        assertEquals(SpaceMissionStatus.IN_PROGRESS, spaceMission.getStatus());
        assertEquals(0, BigDecimal.valueOf(10000L).compareTo(spaceMission.getRevenue()));
    }

    @Test
    @DisplayName("Given a SpaceMission identifier, when invoking delete method, then invoke repository.deleteById")
    void deleteById() {
        //No arrange required
        //Act
        spaceMissionService.deleteById(1L);
        //Assert
        //verify method call count
        verify(spaceMissionRepository, times(1)).deleteById(1L);

    }
}