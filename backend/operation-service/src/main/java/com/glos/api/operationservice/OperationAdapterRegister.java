package com.glos.api.operationservice;

import java.util.HashMap;
import java.util.Map;

public class OperationAdapterRegister {
    private static final Map<String, OperationAdapter> adapters = new HashMap<>();

    public static void appendAdapter(Action action, OperationAdapter adapter) {
        adapters.put(action.name(), adapter);
    }

    public static OperationAdapter getAdapter(String action) {
        Action actionObj = Action.valueOfIgnoreCase(action);
        return adapters.get(actionObj.name());
    }
}
