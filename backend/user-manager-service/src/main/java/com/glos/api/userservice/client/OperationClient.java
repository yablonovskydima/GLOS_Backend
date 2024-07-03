package com.glos.api.userservice.client;

import com.glos.api.userservice.responseDTO.OperationCreateRequest;
import com.glos.api.userservice.responseDTO.OperationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "operation")
public interface OperationClient {

    @PostMapping("/operations")
    ResponseEntity<OperationResponse> create(@RequestBody OperationCreateRequest request);


}
