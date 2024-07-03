package com.glos.api.operationservice;

import java.util.function.Consumer;

@FunctionalInterface
public interface OperationAdapter {

    Consumer<Operation> getConsumer();

}
