package com.glos.api.operationservice.adapters;

import com.glos.api.operationservice.Operation;
import com.glos.api.operationservice.OperationAdapter;

import java.util.function.Consumer;

public abstract class AbstractOperationAdapter implements OperationAdapter {

    @Override
    public final Consumer<Operation> getConsumer() {
        return this::process;
    }

    public abstract void process(Operation operation);
}
