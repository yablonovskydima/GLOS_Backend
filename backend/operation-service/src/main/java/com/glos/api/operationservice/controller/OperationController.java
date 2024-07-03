package com.glos.api.operationservice.controller;

import com.glos.api.operationservice.Operation;
import com.glos.api.operationservice.OperationService;
import com.glos.api.operationservice.dto.OperationCreateRequest;
import com.glos.api.operationservice.dto.OperationExecuteRequest;
import com.glos.api.operationservice.dto.OperationResponse;
import com.glos.api.operationservice.dto.mapper.OperationResponseMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/operations")
public class OperationController {

    private final OperationService operationService;
    private final OperationResponseMapper responseDtoMapper;


    public OperationController(
            OperationService operationService,
            OperationResponseMapper operationResponseMapper
    ) {
        this.operationService = operationService;
        this.responseDtoMapper = operationResponseMapper;
    }

    @PostMapping
    public ResponseEntity<OperationResponse> create(
            @Valid @RequestBody OperationCreateRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        Operation operation = operationService.create(request.getAction(), request.getData(), request.getExpired());
        OperationResponse response = responseDtoMapper.toDto(operation);
        return ResponseEntity
                .created(uriBuilder
                        .path("/v1/operations/execute")
                        .build().toUri())
                .body(response);
    }

    @PostMapping("/execute")
    public ResponseEntity<OperationResponse> execute(
            @Valid @RequestBody OperationExecuteRequest request
    ) {
        if (!operationService.execute(request.getCode())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

}
