package com.springboot.training.spaceover.spacemission.manager.controller;

import com.springboot.training.spaceover.spacemission.manager.domain.model.SpaceMission;
import com.springboot.training.spaceover.spacemission.manager.domain.request.inbound.CreateSpaceMissionRequest;
import com.springboot.training.spaceover.spacemission.manager.domain.request.inbound.PutSpaceMissionRequest;
import com.springboot.training.spaceover.spacemission.manager.domain.response.outbound.GetSpaceMissionResponse;
import com.springboot.training.spaceover.spacemission.manager.domain.response.outbound.PatchSpaceMissionResponse;
import com.springboot.training.spaceover.spacemission.manager.domain.response.outbound.PutSpaceMissionResponse;
import com.springboot.training.spaceover.spacemission.manager.enums.SpaceMissionStatus;
import com.springboot.training.spaceover.spacemission.manager.service.SpaceMissionService;
import com.springboot.training.spaceover.spacemission.manager.utils.assemblers.PaginationModelAssembler;
import com.springboot.training.spaceover.spacemission.manager.utils.interceptors.HttpHeaderEnrichmentInterceptor;
import com.springboot.training.spaceover.spacemission.manager.utils.interceptors.MdcInitInterceptor;
import com.springboot.training.spaceover.spacemission.manager.utils.properties.SpaceMissionManagerProperties;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.FileCopyUtils;

import javax.persistence.EntityNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//LT10-3-Add integration test to the exposure layer
@ActiveProfiles("test")
class SpaceOverSpaceMissionControllerTest {


    private MockMvc mockMvc;

    private SpaceMissionService spaceMissionService;

    private ModelMapper modelMapper;

    private PagedResourcesAssembler<SpaceMission> pagedModelAssembler;

    private PaginationModelAssembler modelAssembler;

    private SpaceMissionManagerProperties spaceMissionManagerProperties;

    private HttpHeaderEnrichmentInterceptor httpHeaderEnrichmentInterceptor;

    private MdcInitInterceptor mdcInitInterceptor;

    @Value("classpath:samples/requests/createSpaceMission201.json")
    private Resource createSpaceMission201Request;

    @Value("classpath:samples/requests/createSpaceMission400.json")
    private Resource createSpaceMission400Request;

    @Value("classpath:samples/requests/patchMission200.json")
    private Resource patchSpaceMission200Request;

    @Value("classpath:samples/requests/putSpaceMission200.json")
    private Resource putSpaceMission200Request;

    @Value("classpath:samples/requests/putSpaceMission400.json")
    private Resource putSpaceMission400Request;

    @Test
    @SneakyThrows
    @DisplayName("Given a consumer client, when invoking GET /missions, then reply 200 response")
    void getSpaceMissionsOk() {

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

        GetSpaceMissionResponse spaceMissionOneResponse = GetSpaceMissionResponse.builder()
                .id(1L)
                .name("Retrieve the Millennium Falcon")
                .spaceShipId(2L)
                .revenue(BigDecimal.valueOf(10000L))
                .status(SpaceMissionStatus.IN_PROGRESS)
                .build();

        GetSpaceMissionResponse spaceMissionTwoResponse = GetSpaceMissionResponse.builder()
                .id(2L)
                .name("Retrieve the Red Fox")
                .spaceShipId(1L)
                .revenue(BigDecimal.valueOf(1000L))
                .status(SpaceMissionStatus.NOT_IN_PROGRESS)
                .build();

        GetSpaceMissionResponse spaceMissionThreeResponse = GetSpaceMissionResponse.builder()
                .id(3L)
                .name("Destroy the Death Star")
                .details("Blow it up!")
                .spaceShipId(1L)
                .revenue(BigDecimal.ZERO)
                .status(SpaceMissionStatus.ACCOMPLISHED)
                .build();

        List<SpaceMission> spaceMissionList = Arrays.asList(spaceMissionOne, spaceMissionTwo, spaceMissionThree);
        List<GetSpaceMissionResponse> spaceMissionResponseList = Arrays
                .asList(spaceMissionOneResponse, spaceMissionTwoResponse, spaceMissionThreeResponse);

        SpaceMission spaceMissionSample = SpaceMission.builder().build();

        Pageable pageRequest = PageRequest.of(0, 20);

        Page<SpaceMission> spaceMissionPage = new PageImpl<>(spaceMissionList, pageRequest, 5);

        PagedModel<GetSpaceMissionResponse> response = PagedModel.of(spaceMissionResponseList, new PagedModel.PageMetadata(20, 0, 3, 1));
        when(spaceMissionService.findAll(spaceMissionSample, pageRequest));
        when(pagedModelAssembler.toModel(eq(spaceMissionPage), any(PaginationModelAssembler.class)));

        //Act & Assert
        mockMvc.perform(get("/missions")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    @SneakyThrows
    @DisplayName("Given a consumer client, when invoking GET /missions/{id} with existent identifier, then reply 200 response")
    void getSpaceMissionOk() {
        //Arrange
        SpaceMission spaceMission = SpaceMission.builder()
                .id(1L)
                .name("Retrieve the Millennium Falcon")
                .spaceShipId(2L)
                .revenue(BigDecimal.valueOf(10000L))
                .status(SpaceMissionStatus.IN_PROGRESS)
                .build();

        GetSpaceMissionResponse response = GetSpaceMissionResponse.builder()
                .id(1L)
                .name("Retrieve the Millennium Falcon")
                .spaceShipId(2L)
                .revenue(BigDecimal.valueOf(10000L))
                .status(SpaceMissionStatus.IN_PROGRESS)
                .build();

        when(spaceMissionService.findBydId(1L));
        when(modelMapper.map(spaceMission, GetSpaceMissionResponse.class));
        //Act & Assert
        mockMvc.perform(get("/missions/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    @SneakyThrows
    @DisplayName("Given a consumer client, when invoking GET /missions/{id} with none-existent identifier, then reply 404 response")
    void getSpaceMissionNotFound() {
        //Arrange
        when(spaceMissionService.findBydId(1L));
        //Arrange
        mockMvc.perform(get("/missions/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    @SneakyThrows
    @DisplayName("Given a consumer client, when invoking POST /missions with valid request, then reply 201 response")
    void createSpaceMissionCreated() {
        //Arrange
        CreateSpaceMissionRequest request = CreateSpaceMissionRequest.builder()
                .name("Retrieve the Millennium Falcon")
                .spaceShipId(2L)
                .status(SpaceMissionStatus.IN_PROGRESS)
                .revenue(BigDecimal.valueOf(10000))
                .build();

        SpaceMission spaceMission = SpaceMission.builder()
                .name("Retrieve the Millennium Falcon")
                .spaceShipId(2L)
                .status(SpaceMissionStatus.IN_PROGRESS)
                .revenue(BigDecimal.valueOf(10000))
                .build();

        SpaceMission persistedSpaceMission = SpaceMission.builder()
                .id(1L)
                .name("Retrieve the Millennium Falcon")
                .spaceShipId(2L)
                .status(SpaceMissionStatus.IN_PROGRESS)
                .revenue(BigDecimal.valueOf(10000))
                .build();

        String responseContent = FileCopyUtils.copyToString(new FileReader(createSpaceMission201Request.getFile()));

        when(modelMapper.map(request, SpaceMission.class));
        when(spaceMissionService.save(spaceMission));
        //Arrange
        mockMvc.perform(post("/missions")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(responseContent));

    }

    @Test
    @SneakyThrows
    @DisplayName("Given a consumer client, when invoking POST /missions with invalid request payload, then reply 400 response")
    void createSpaceMissionBadRequest() {
        //Arrange
        String responseContent = FileCopyUtils.copyToString(new FileReader(createSpaceMission400Request.getFile()));
        //Act & Assert
        mockMvc.perform(post("/missions")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(responseContent));

    }

    @Test
    @SneakyThrows
    @DisplayName("Given a consumer client, when invoking PATCH /missions/{id} with valid request, then reply 200 response")
    void patchSpaceMissionOk() {
        //Arrange
        SpaceMission spaceMission = SpaceMission.builder()
                .id(1L)
                .name("Retrieve the Millennium Falcon")
                .spaceShipId(2L)
                .status(SpaceMissionStatus.IN_PROGRESS)
                .revenue(BigDecimal.ONE)
                .build();

        SpaceMission patchedSpaceMission = SpaceMission.builder()
                .id(1L)
                .name("Retrieve the Millennium Falcon")
                .spaceShipId(2L)
                .status(SpaceMissionStatus.ACCOMPLISHED)
                .revenue(BigDecimal.ONE)
                .build();

        PatchSpaceMissionResponse response = PatchSpaceMissionResponse.builder()
                .id(1L)
                .name("Retrieve the Millennium Falcon")
                .spaceShipId(2L)
                .status(SpaceMissionStatus.ACCOMPLISHED)
                .revenue(BigDecimal.ONE)
                .build();

        String responseContent = FileCopyUtils.copyToString(new FileReader(patchSpaceMission200Request.getFile()));

        when(spaceMissionService.findBydId(1L));
        when(spaceMissionService.update(patchedSpaceMission));
        when(modelMapper.map(patchedSpaceMission, PatchSpaceMissionResponse.class));
        //Act & Assert
        mockMvc.perform(patch("/missions/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(APPLICATION_JSON_PATCH)
                .content(responseContent));

    }

    @Test
    @SneakyThrows
    @DisplayName("Given a consumer client, when invoking PUT /missions/{id} with valid request, then reply 200 response")
    void putSpaceMissionOk() {
        //Arrange
        PutSpaceMissionRequest request = PutSpaceMissionRequest.builder()
                .id(1L)
                .name("Retrieve the Millennium Falcon")
                .spaceShipId(2L)
                .status(SpaceMissionStatus.ACCOMPLISHED)
                .revenue(BigDecimal.valueOf(10000))
                .build();

        SpaceMission spaceMission = SpaceMission.builder()
                .id(1L)
                .name("Retrieve the Millennium Falcon")
                .spaceShipId(2L)
                .status(SpaceMissionStatus.ACCOMPLISHED)
                .revenue(BigDecimal.valueOf(10000))
                .build();

        PutSpaceMissionResponse response = PutSpaceMissionResponse.builder()
                .id(1L)
                .name("Retrieve the Millennium Falcon")
                .spaceShipId(2L)
                .status(SpaceMissionStatus.ACCOMPLISHED)
                .revenue(BigDecimal.valueOf(10000))
                .build();

        String responseContent = FileCopyUtils.copyToString(new FileReader(putSpaceMission200Request.getFile()));

        when(modelMapper.map(request, SpaceMission.class));
        when(spaceMissionService.update(spaceMission));
        when(modelMapper.map(spaceMission, PutSpaceMissionResponse.class));
        //Act & Assert
        mockMvc.perform(put("/missions/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(responseContent));

    }

    @Test
    @SneakyThrows
    @DisplayName("Given a consumer client, when invoking PUT /missions/{id} with invalid request body, then reply 400 response")
    void putSpaceMissionBadRequest() {
        //Arrange
        String responseContent = FileCopyUtils.copyToString(new FileReader(putSpaceMission400Request.getFile()));
        //Act & Assert
        mockMvc.perform(put("/missions/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(responseContent));
    }

    @Test
    @SneakyThrows
    @DisplayName("Given a consumer client, when invoking DELETE /missions/{id}, then reply 204 response")
    void deleteSpaceCrewMemberNoContent() {
        //No Arrange required
        //Act & Assert
        mockMvc.perform(delete("/missions/1"));

    }
}