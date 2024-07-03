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
public class ChangeUsernameOperationAdapter extends AbstractOperationAdapter {

    private final UserAPIClient userClient;

    public ChangeUsernameOperationAdapter(UserAPIClient userClient) {
        this.userClient = userClient;
    }

    @PostConstruct
    public void init() {
        OperationAdapterRegister.appendAdapter(Action.CHANGE_USERNAME, this);
    }

    @Override
    public void process(Operation operation) {
        final Map<String, String> data = operation.getData();
        final ChangeRequest request = new ChangeRequest();
        final String username = data.get("username");
        request.setOldValue(username);
        request.setNewValue(data.get("newUsername"));
        userClient.changeProperty(username,"username", request);
    }
}
