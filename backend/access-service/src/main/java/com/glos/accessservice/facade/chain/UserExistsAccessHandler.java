package com.glos.accessservice.facade.chain;

import com.glos.accessservice.clients.UserClient;
import com.glos.accessservice.entities.User;
import com.glos.accessservice.exeptions.HttpStatusCodeImplException;
import com.glos.accessservice.exeptions.UserAccessDeniedException;
import com.glos.accessservice.exeptions.UserNotFoundException;
import com.glos.accessservice.facade.chain.base.AccessHandler;
import com.glos.accessservice.facade.chain.base.AccessRequest;
import com.pathtools.Path;
import feign.FeignException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class UserExistsAccessHandler extends AccessHandler {

    private UserClient userClient;

    public UserExistsAccessHandler(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public boolean check(AccessRequest request) {
        super.check(request);
        final Map<String, Object> data = request.getData();
        User user = getUser(request.getUsername());
        data.put("user", user);
        final Path path = (Path)data.get("path");
        final String rootUsername = path.getFirst().getSimpleName();

        User owner = getUser(rootUsername);
        data.put("owner", owner);

        if (rootUsername.equals(request.getUsername()))
            return true;

        return checkNext(request);
    }

    private User getUser(String username) {
        try {
            return userClient.getByUsername(username).getBody();
        } catch (FeignException ex) {
            if (ex.status() == 404) {
                throw new UserNotFoundException("user not found");
            }
            throw new HttpStatusCodeImplException(HttpStatusCode.valueOf(ex.status()), ex.getMessage());
        }
    }

}
