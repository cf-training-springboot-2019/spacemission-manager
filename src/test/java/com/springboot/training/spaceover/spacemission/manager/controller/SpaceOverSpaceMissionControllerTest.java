package com.springboot.training.spaceover.spacemission.manager.controller;

import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.APPLICATION_JSON_PATCH;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.CREATE_SPACE_MISSION_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.DELETE_SPACE_MISSION_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.ENTITY_NOT_FOUND_MSG;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.GET_SPACE_MISSIONS_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.GET_SPACE_MISSION_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.PATCH_SPACE_MISSION_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.PUT_SPACE_MISSION_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.SERVICE_OPERATION_HEADER;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.SPACE_MISSION;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.TRACE_ID_HEADER;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityNotFoundException;
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

@WebMvcTest
@ActiveProfiles("test")
class SpaceOverSpaceMissionControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceMissionService spaceMissionService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PagedResourcesAssembler<SpaceMission> pagedModelAssembler;

    @MockBean
    private PaginationModelAssembler modelAssembler;

    @MockBean
    private SpaceMissionManagerProperties spaceMissionManagerProperties;

    @Autowired
    private HttpHeaderEnrichmentInterceptor httpHeaderEnrichmentInterceptor;

    @Autowired
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
        when(spaceMissionService.findAll(spaceMissionSample, pageRequest)).thenReturn(spaceMissionPage);
        when(pagedModelAssembler.toModel(eq(spaceMissionPage), any(PaginationModelAssembler.class))).thenReturn(response);

        //Act & Assert
        mockMvc.perform(get("/missions")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists(TRACE_ID_HEADER))
                .andExpect(header().string(SERVICE_OPERATION_HEADER, GET_SPACE_MISSIONS_SERVICE_OPERATION))
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$._embedded.getSpaceMissionResponses[0].id").value(1))
                .andExpect(jsonPath("$._embedded.getSpaceMissionResponses[0].name").value("Retrieve the Millennium Falcon"))
                .andExpect(jsonPath("$._embedded.getSpaceMissionResponses[0].status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$._embedded.getSpaceMissionResponses[0].spaceShipId").value(2))
                .andExpect(jsonPath("$._embedded.getSpaceMissionResponses[0].revenue").value(10000))
                .andExpect(jsonPath("$._embedded.getSpaceMissionResponses[1].id").value(2))
                .andExpect(jsonPath("$._embedded.getSpaceMissionResponses[1].name").value("Retrieve the Red Fox"))
                .andExpect(jsonPath("$._embedded.getSpaceMissionResponses[1].status").value("NOT_IN_PROGRESS"))
                .andExpect(jsonPath("$._embedded.getSpaceMissionResponses[1].spaceShipId").value(1))
                .andExpect(jsonPath("$._embedded.getSpaceMissionResponses[1].revenue").value(1000))
                .andExpect(jsonPath("$._embedded.getSpaceMissionResponses[2].id").value(3))
                .andExpect(jsonPath("$._embedded.getSpaceMissionResponses[2].name").value("Destroy the Death Star"))
                .andExpect(jsonPath("$._embedded.getSpaceMissionResponses[2].details").value("Blow it up!"))
                .andExpect(jsonPath("$._embedded.getSpaceMissionResponses[2].status").value("ACCOMPLISHED"))
                .andExpect(jsonPath("$._embedded.getSpaceMissionResponses[2].spaceShipId").value(1))
                .andExpect(jsonPath("$._embedded.getSpaceMissionResponses[2].revenue").value(0))
                .andExpect(jsonPath("$.page.number").value(0))
                .andExpect(jsonPath("$.page.size").value(20))
                .andExpect(jsonPath("$.page.totalElements").value(3))
                .andExpect(jsonPath("$.page.totalPages").value(1));

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

        when(spaceMissionService.findBydId(1L)).thenReturn(spaceMission);
        when(modelMapper.map(spaceMission, GetSpaceMissionResponse.class)).thenReturn(response);
        //Act & Assert
        mockMvc.perform(get("/missions/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists(TRACE_ID_HEADER))
                .andExpect(header().string(SERVICE_OPERATION_HEADER, GET_SPACE_MISSION_SERVICE_OPERATION))
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Retrieve the Millennium Falcon"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.spaceShipId").value(2))
                .andExpect(jsonPath("$.revenue").value(10000));

    }

    @Test
    @SneakyThrows
    @DisplayName("Given a consumer client, when invoking GET /missions/{id} with none-existent identifier, then reply 404 response")
    void getSpaceMissionNotFound() {
        //Arrange
        when(spaceMissionService.findBydId(1L))
                .thenThrow(new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MSG, SPACE_MISSION, 1L)));
        //Arrange
        mockMvc.perform(get("/missions/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists(TRACE_ID_HEADER))
                .andExpect(header().string(SERVICE_OPERATION_HEADER, GET_SPACE_MISSION_SERVICE_OPERATION))
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.reason").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value(String.format(ENTITY_NOT_FOUND_MSG, SPACE_MISSION, 1L)));

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

        when(modelMapper.map(request, SpaceMission.class)).thenReturn(spaceMission);
        when(spaceMissionService.save(spaceMission)).thenReturn(persistedSpaceMission);
        //Arrange
        mockMvc.perform(post("/missions")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(responseContent))
                .andExpect(status().isCreated())
                .andExpect(header().exists(TRACE_ID_HEADER))
                .andExpect(header().string(SERVICE_OPERATION_HEADER, CREATE_SPACE_MISSION_SERVICE_OPERATION))
                .andExpect(header().string(LOCATION, "http://localhost/missions/1"));

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
                .content(responseContent))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists(TRACE_ID_HEADER))
                .andExpect(header().string(SERVICE_OPERATION_HEADER, CREATE_SPACE_MISSION_SERVICE_OPERATION))
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.reason").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(containsString("name must not be null")))
                .andExpect(jsonPath("$.message").value(containsString("name must not be empty")));

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

        when(spaceMissionService.findBydId(1L)).thenReturn(spaceMission);
        when(spaceMissionService.update(patchedSpaceMission)).thenReturn(patchedSpaceMission);
        when(modelMapper.map(patchedSpaceMission, PatchSpaceMissionResponse.class)).thenReturn(response);
        //Act & Assert
        mockMvc.perform(patch("/missions/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(APPLICATION_JSON_PATCH)
                .content(responseContent))
                .andExpect(status().isOk())
                .andExpect(header().exists(TRACE_ID_HEADER))
                .andExpect(header().string(SERVICE_OPERATION_HEADER, PATCH_SPACE_MISSION_SERVICE_OPERATION))
                .andDo(print())
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Retrieve the Millennium Falcon"))
                .andExpect(jsonPath("$.status").value("ACCOMPLISHED"))
                .andExpect(jsonPath("$.spaceShipId").value(2))
                .andExpect(jsonPath("$.revenue").value(1));

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

        when(modelMapper.map(request, SpaceMission.class)).thenReturn(spaceMission);
        when(spaceMissionService.update(spaceMission)).thenReturn(spaceMission);
        when(modelMapper.map(spaceMission, PutSpaceMissionResponse.class)).thenReturn(response);
        //Act & Assert
        mockMvc.perform(put("/missions/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(responseContent))
                .andExpect(status().isOk())
                .andExpect(header().exists(TRACE_ID_HEADER))
                .andExpect(header().string(SERVICE_OPERATION_HEADER, PUT_SPACE_MISSION_SERVICE_OPERATION))
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Retrieve the Millennium Falcon"))
                .andExpect(jsonPath("$.status").value("ACCOMPLISHED"))
                .andExpect(jsonPath("$.spaceShipId").value(2))
                .andExpect(jsonPath("$.revenue").value(10000));

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
                .content(responseContent))
                .andExpect(status().isBadRequest())
                .andExpect(header().exists(TRACE_ID_HEADER))
                .andExpect(header().string(SERVICE_OPERATION_HEADER, PUT_SPACE_MISSION_SERVICE_OPERATION))
                .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.reason").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(containsString("name must not be empty")));
    }

    @Test
    @SneakyThrows
    @DisplayName("Given a consumer client, when invoking DELETE /missions/{id}, then reply 204 response")
    void deleteSpaceCrewMemberNoContent() {
        //No Arrange required
        //Act & Assert
        mockMvc.perform(delete("/missions/1"))
                .andExpect(status().isNoContent())
                .andExpect(header().exists(TRACE_ID_HEADER))
                .andExpect(header().string(SERVICE_OPERATION_HEADER, DELETE_SPACE_MISSION_SERVICE_OPERATION));

    }
}