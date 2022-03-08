package com.springboot.training.spaceover.spacemission.manager.service;

import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.FRONT_SLASH_DELIMITER;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.SPACESHIPS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.springboot.training.spaceover.spacemission.manager.domain.response.inbound.GetSpaceShipResponse;
import com.springboot.training.spaceover.spacemission.manager.utils.properties.SpaceMissionManagerProperties;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class RestTemplateSpaceShipClientTest {

    @InjectMocks
    private RestTemplateSpaceShipClient restTemplateSpaceShipClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private SpaceMissionManagerProperties spaceMissionManagerProperties;

    @Test
    void findBydId() {
        //Arrange
        GetSpaceShipResponse spaceShipResponse = GetSpaceShipResponse.builder()
                .id(1L)
                .name("Millennium Falcon")
                .status("ACTIVE")
                .type("STAR_CRUISER")
                .maxOccupancy(BigInteger.TEN)
                .build();

        when(spaceMissionManagerProperties.getSpaceshipManagerBaseUrl()).thenReturn("http://localhost:8080");
        String uri = String.join(FRONT_SLASH_DELIMITER, "http://localhost:8080", SPACESHIPS, "1");
        when(restTemplate.getForEntity(uri, GetSpaceShipResponse.class)).thenReturn(ResponseEntity.ok(spaceShipResponse));

        //Act
        spaceShipResponse = restTemplateSpaceShipClient.findBydId(1L);
        //Assert
        assertNotNull(spaceShipResponse);
        assertEquals(1L, spaceShipResponse.getId());
        assertEquals("Millennium Falcon", spaceShipResponse.getName());
        assertEquals("ACTIVE", spaceShipResponse.getStatus());
        assertEquals("STAR_CRUISER", spaceShipResponse.getType());
        assertEquals(BigInteger.TEN, spaceShipResponse.getMaxOccupancy());

    }
}