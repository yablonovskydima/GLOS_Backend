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
public class ChangePhoneNumberOperationAdapter extends AbstractOperationAdapter {

    private final UserAPIClient userClient;

    public ChangePhoneNumberOperationAdapter(UserAPIClient userClient) {
        this.userClient = userClient;
    }

    @PostConstruct
    public void init() {
        OperationAdapterRegister.appendAdapter(Action.CHANGE_PHONE_NUMBER, this);
    }

    @Override
    public void process(Operation operation) {
        final Map<String, String> data = operation.getData();
        final String username = data.get("username");
        final String oldPhoneNumber = data.get("oldPhoneNumber");
        final String newPhoneNumber = data.get("newPhoneNumber");
        userClient.changeProperty(username, "phoneNumber", new ChangeRequest(oldPhoneNumber, newPhoneNumber));
    }
}
