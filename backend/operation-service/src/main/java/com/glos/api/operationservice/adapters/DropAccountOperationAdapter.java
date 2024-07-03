package com.glos.api.operationservice.adapters;

import com.glos.api.operationservice.Action;
import com.glos.api.operationservice.Operation;
import com.glos.api.operationservice.OperationAdapterRegister;
import com.glos.api.operationservice.client.UserAPIClient;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DropAccountOperationAdapter extends AbstractOperationAdapter {

    private final UserAPIClient userClient;

    public DropAccountOperationAdapter(UserAPIClient userClient) {
        this.userClient = userClient;
    }

    @PostConstruct
    public void init() {
        OperationAdapterRegister.appendAdapter(Action.DROP_ACCOUNT, this);
    }

    @Override
    public void process(Operation operation) {
        final Map<String, String> data = operation.getData();
        final String username = data.get("username");
        userClient.deleteUser(username);
    }
}
