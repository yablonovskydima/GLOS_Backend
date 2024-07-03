package com.glos.api.operationservice.adapters;

import com.glos.api.operationservice.Action;
import com.glos.api.operationservice.Operation;
import com.glos.api.operationservice.OperationAdapterRegister;
import com.glos.api.operationservice.client.UserAPIClient;
import com.glos.api.operationservice.dto.ChangeRequest;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ChangeEmailOperationAdapter extends AbstractOperationAdapter {
    private final UserAPIClient userClient;

    public ChangeEmailOperationAdapter(UserAPIClient userClient) {
        this.userClient = userClient;
    }

    @PostConstruct
    public void init() {
        OperationAdapterRegister.appendAdapter(Action.CHANGE_EMAIL, this);
    }

    @Override
    public void process(Operation operation) {
        final Map<String, String> data = operation.getData();
        final String username = data.get("username");
        final String oldEmail = data.get("oldEmail");
        final String newEmail = data.get("newEmail");
        userClient.changeProperty(username, "email", new ChangeRequest(oldEmail, newEmail));
    }
}
