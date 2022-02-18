package com.springboot.training.spaceover.spacemission.manager.controller;

import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.SEMI_COLON_DELIMITER;
import static com.springboot.training.spaceover.spacemission.manager.utils.constants.SpaceMissionManagerConstant.WHITE_SPACE_DELIMITER;

import com.github.fge.jsonpatch.JsonPatchException;
import com.springboot.training.spaceover.spacemission.manager.domain.response.outbound.OperationErrorResponse;
import com.springboot.training.spaceover.spacemission.manager.error.InvalidResourceStatusException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;

//LT2.1-Include request validation
public class ExceptionHandlingController {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    //LT2.5-Include EntityNotFoundException class handler method map to 404 HTTP status
    public ResponseEntity<OperationErrorResponse> handleNotFoundError(Exception e) {
        return buildErrorMessageResponseEntity(e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    //LT2.2-Include MethodArgumentNotValidException class handler method map to 400 HTTP status
    public ResponseEntity<OperationErrorResponse> handleBadRequestMethodArgument(MethodArgumentNotValidException e) {
        List<String> fieldErrors = e.getBindingResult().getFieldErrors().stream()
                .map(f -> String.join(WHITE_SPACE_DELIMITER, f.getField(), f.getDefaultMessage()))
                .collect(Collectors.toList());
        return buildErrorMessageResponseEntity(String.join(SEMI_COLON_DELIMITER.concat(WHITE_SPACE_DELIMITER), fieldErrors),
            null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    //LT2.3-Include InvalidResourceStatusException class handler method map to 400 HTTP status
    public ResponseEntity<OperationErrorResponse> handleBadRequestInvalidResourceStatus(InvalidResourceStatusException e) {
        return buildErrorMessageResponseEntity(e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    //LT2.4-Include JsonPatchException class handler method map to 400 HTTP status
    public ResponseEntity<OperationErrorResponse> handleBadRequestInvalidJsonPatch(JsonPatchException e) {
        return buildErrorMessageResponseEntity(e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    //LT2.6-Include DataIntegrityViolationException class handler method map to 409 HTTP status
    public ResponseEntity<OperationErrorResponse> handleConflict(DataIntegrityViolationException e) {
        return buildErrorMessageResponseEntity(e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    //LT2.7-Include Exception class handler method map to 500 HTTP status
    public ResponseEntity<OperationErrorResponse> handleInternalError(Exception e) {
        return buildErrorMessageResponseEntity(e.getMessage(), null);
    }

    private ResponseEntity<OperationErrorResponse> buildErrorMessageResponseEntity(String msg, HttpStatus httpStatus) {
        return new ResponseEntity<>(
                OperationErrorResponse.builder()
                        .message(msg)
                        .code(httpStatus.value())
                        .reason(httpStatus.getReasonPhrase())
                        .status(httpStatus.value())
                        .build(),
                httpStatus);
    }

}