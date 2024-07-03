package com.glos.api.operationservice.dto.mapper;

import com.glos.api.operationservice.Operation;
import com.glos.api.operationservice.dto.OperationRequest;
import com.glos.api.operationservice.dto.OperationResponse;
import com.glos.api.operationservice.dto.mapper.base.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class OperationResponseMapper
        extends AbstractMapper<Operation, OperationResponse> {

}
