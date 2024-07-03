package com.glos.api.operationservice;

import com.glos.api.operationservice.exception.ExecutionOperationException;

public final class OperationExecutor {

    private static OperationExecutor instance;

    public static OperationExecutor getInstance() {
        if (instance == null)
            instance = new OperationExecutor();
        return instance;
    }

    private OperationExecutor() {}

    public boolean execute(Operation operation) {
        try {
            OperationAdapter adapter = OperationAdapterRegister
                    .getAdapter(operation.getAction());
            adapter.getConsumer().accept(operation);
        } catch (Exception e) {
            throw new ExecutionOperationException(e.getMessage());
        }
        return true;
    }

}
