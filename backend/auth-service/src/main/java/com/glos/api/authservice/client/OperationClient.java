package com.glos.api.authservice.client;

import com.glos.api.authservice.dto.OperationCreateRequest;
import com.glos.api.authservice.dto.OperationExecuteRequest;
import com.glos.api.authservice.dto.OperationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "operation")
public interface OperationClient {

    @PostMapping("/operations")
    ResponseEntity<OperationResponse> create(@RequestBody OperationCreateRequest request);

    @PostMapping("/operations/execute")
    ResponseEntity<?> execute(@RequestBody OperationExecuteRequest request);

}
