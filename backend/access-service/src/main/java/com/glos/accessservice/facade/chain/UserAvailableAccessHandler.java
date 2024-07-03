package com.glos.accessservice.facade.chain;

import com.accesstools.AccessNode;
import com.accesstools.AccessNodeType;
import com.accesstools.AccessUtils;
import com.glos.accessservice.clients.AccessTypeApiClient;
import com.glos.accessservice.entities.AccessType;
import com.glos.accessservice.entities.User;
import com.glos.accessservice.exeptions.UserAccessDeniedException;
import com.glos.accessservice.facade.chain.base.AccessHandler;
import com.glos.accessservice.facade.chain.base.AccessRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserAvailableAccessHandler extends AccessHandler {
    private final AccessTypeApiClient accessTypeApiClient;

    public UserAvailableAccessHandler(AccessTypeApiClient accessTypeApiClient) {
        this.accessTypeApiClient = accessTypeApiClient;
    }

    @Override
    public boolean check(AccessRequest request) throws UserAccessDeniedException {
        super.check(request);
        final Map<String, Object> data = request.getData();
        final User owner = (User) data.get("owner");
        final User user = (User) data.get("user");

        final Set<AccessNode> nodes = (Set<AccessNode>) data.get("accessNodes");

        if (nodes == null || nodes.isEmpty()) {
            final List<AccessType> accessTypes = (List<AccessType>) data.get("accessTypes");
            if (accessTypes != null || !accessTypes.isEmpty()) {
                final Set<AccessNode> accessNodesWithUser =  accessTypes.stream()
                        .map(x -> AccessNode.builder(x.getName()).build())
                        .filter(x -> AccessUtils.isType(x, AccessNodeType.USER))
                        .filter(x -> AccessUtils.checkAndReadOnly(x, request.getUsername(), request.isReadOnly()))
                        .collect(Collectors.toSet());
                data.put("accessNodes", accessNodesWithUser);
            }
        }

        return checkNext(request);
    }

    private void throwUserAccessDenied(String message, Map<String, Object> data) throws UserAccessDeniedException {
        throw new UserAccessDeniedException(message, (User)data.get("user"));
    }
}
