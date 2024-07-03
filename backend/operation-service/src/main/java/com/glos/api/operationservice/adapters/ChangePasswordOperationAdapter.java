package com.glos.api.operationservice.adapters;

import com.glos.api.operationservice.Action;
import com.glos.api.operationservice.Operation;
import com.glos.api.operationservice.OperationAdapterRegister;
import com.glos.api.operationservice.client.AuthClient;
import com.glos.api.operationservice.client.UserAPIClient;
import com.glos.api.operationservice.dto.ChangeRequest;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ChangePasswordOperationAdapter extends AbstractOperationAdapter {

    private final UserAPIClient userClient;
    private final AuthClient authClient;

    public ChangePasswordOperationAdapter(
            UserAPIClient userClient,
            AuthClient authClient) {
        this.userClient = userClient;
        this.authClient = authClient;
    }

    @PostConstruct
    public void init() {
        OperationAdapterRegister.appendAdapter(Action.CHANGE_PASSWORD, this);
    }

    @Override
    public void process(Operation operation) {
        final Map<String, String> data = operation.getData();
        final String username = data.get("username");
        final String oldPassword = data.get("oldPassword");
        final String newPassword = data.get("newPassword");
        authClient.changePassword(username, new ChangeRequest(oldPassword, newPassword));
    }
}
