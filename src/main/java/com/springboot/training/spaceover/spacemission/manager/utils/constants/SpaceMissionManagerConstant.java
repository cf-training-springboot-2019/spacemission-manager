package com.springboot.training.spaceover.spacemission.manager.utils.constants;

public class SpaceMissionManagerConstant {

    /**
     * Operations
     */
    public static final String GET_SPACE_MISSIONS_SERVICE_OPERATION = "GetSpaceMissions";
    public static final String GET_SPACE_MISSION_SERVICE_OPERATION = "GetSpaceMission";
    public static final String CREATE_SPACE_MISSION_SERVICE_OPERATION = "CreateSpaceMission";
    public static final String PATCH_SPACE_MISSION_SERVICE_OPERATION = "PatchSpaceMission";
    public static final String PUT_SPACE_MISSION_SERVICE_OPERATION = "PutSpaceMission";
    public static final String DELETE_SPACE_MISSION_SERVICE_OPERATION = "DeleteSpaceMission";
    public static final String UNDEFINED_SERVICE_OPERATION = "Undefined";

    /**
     * MDC Keys
     */
    public static final String SERVICE_OPERATION = "operation";
    public static final String TRACE_ID = "trace-id";

    /**
     * Header Names
     */
    public static final String TRACE_ID_HEADER = "X-Trace-Id";
    public static final String SERVICE_OPERATION_HEADER = "Service-Operation";
    public static final String LINK_HEADER = "Link";
    public static final String PAGE_NUMBER_HEADER = "X-Page-Number";
    public static final String PAGE_SIZE_HEADER = "X-Page-Size";
    public static final String TOTAL_ELEMENTS_HEADER = "X-Total-Elements";
    public static final String TOTAL_PAGES_HEADER = "X-Total-Pages";

    /**
     * URIs
     */
    public static final String SPACE_MISSIONS_URI = "/missions";
    public static final String ID_URI = "/{id}";

    /**
     * Messages
     */
    public static final String GET_SPACE_MISSIONS_MSG = "Getting space missions";
    public static final String GET_SPACE_MISSIONS_COUNT_MSG = "Got {} space missions out of {}";
    public static final String GET_SPACE_MISSION_MSG = "Getting space mission";
    public static final String GET_SPACE_MISSION_RESULT_MSG = "Got space mission id::{}";
    public static final String CREATE_SPACE_MISSION_MSG = "Creating space mission";
    public static final String CREATE_SPACE_MISSION_RESULT_MSG = "Created space mission id::{}";
    public static final String UPDATE_SPACE_MISSION_MSG = "Updating space mission id::{}";
    public static final String UPDATE_SPACE_MISSION_RESULT_MSG = "Updated space mission id::{}";
    public static final String DELETE_SPACE_MISSION_MSG = "Deleting space mission id::{}";
    public static final String DELETE_SPACE_MISSION_RESULT_MSG = "Deleted space mission id::{}";
    public static final String RESTOCK_SPACE_MISSION_MSG = "Restocking space mission id::{}";
    public static final String RESTOCK_SPACE_MISSION_RESULT_MSG = "Restocked space mission id::{} by amount {}";
    public static final String DISPATCH_SPACE_MISSION_MSG = "Dispatching space mission id::{}";
    public static final String DISPATCH_SPACE_MISSION_RESULT_MSG = "Dispatched space mission id::{} by amount {}";
    public static final String LOGGING_HANDLER_INBOUND_MSG = "Received HTTP {} Request to {} at {}";
    public static final String LOGGING_HANDLER_OUTBOUND_MSG = "Responded with Status {} at {}";
    public static final String LOGGING_HANDLER_PROCESS_TIME_MSG = "Total processing time {} ms";
    public static final String INVALID_MARKET_FIELD_MSG = "market field should match ISO 3166-1 alpha-2 specification";
    public static final String INVALID_EMPTY_OR_BLANK_STRING_MSG = "cannot be empty or blank";
    public static final String ENTITY_NOT_FOUND_MSG = "Entity %s id::{%s} not found.";

    /**
     * Fields
     */
    public static final String NAME_FIELD = "name";
    public static final String STATUS_FIELD = "status";
    public static final String SPACESHIP_ID_FIELD = "spaceShipId";


    /**
     * Miscellaneous
     */
    public static final String FRONT_SLASH_DELIMITER = "/";
    public static final String COLON_WHITE_SPACE_DELIMITER = ", ";
    public static final String WHITE_SPACE_DELIMITER = " ";
    public static final String SEMI_COLON_DELIMITER = ";";
    public static final String SPACE_MISSION_API_DESCRIPTION = "A public Restful Api that allows to manage the various space mission resources.";
    public static final String ISO_3166_1_ALPHA_2_REGEX = "^[A-Z]{2}$";
    public static final String EMPTY_OR_BLANK_STRING_REGEX = "^(?!\\s*$).+";
    public static final String APPLICATION_JSON_PATCH = "application/json-patch+json";
    public static final String SPACE_MISSIONS = "space missions";
    public static final String SPACE_MISSION = "space mission";
    public static final String SPACESHIPS = "spaceships";
    public static final String SPACESHIP = "spaceship";


}
